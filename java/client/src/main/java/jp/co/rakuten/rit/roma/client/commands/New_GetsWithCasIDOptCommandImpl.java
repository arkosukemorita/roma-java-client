package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jp.co.rakuten.rit.roma.client.BadRoutingTableFormatException;
import jp.co.rakuten.rit.roma.client.CasValue;
import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Config;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.ConnectionPool;
import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;
import jp.co.rakuten.rit.roma.client.commands.GetsWithCasIDOptCommand.CallableImpl;
import jp.co.rakuten.rit.roma.client.routing.New_RoutingTable;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;

public class New_GetsWithCasIDOptCommandImpl extends New_AbstractCommandImpl
	implements CommandID {

    public New_GetsWithCasIDOptCommandImpl() {
	super(null);
    }

    private static ExecutorService executor;

    public static int numOfThreads = Integer
	    .parseInt(Config.DEFAULT_NUM_OF_THREADS2);

    public static void shutdown() {
	if (executor != null) {
	    // executor.shutdown();
	    executor.shutdownNow();
	}
	executor = null;
    }

    public static class CallableImpl implements Callable<Map<String, CasValue>> {

	private CommandContext context;

	private Node node;

	private List<String> keys;

	public CallableImpl(CommandContext context, Node node, List<String> keys) {
	    this.context = context;
	    this.node = node;
	    this.keys = keys;
	}

	public Map<String, CasValue> call() throws Exception {
	    StringBuilder sb = new StringBuilder();
	    sb.append(STR_GETS);
	    for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
		sb.append(STR_WHITE_SPACE).append(iter.next()).append(STR_ESC)
			.append(context.get(CommandContext.HASH_NAME));
	    }
	    sb.append(STR_CRLF);
	    ConnectionPool pool = (ConnectionPool) context
		    .get(CommandContext.CONNECTION_POOL);
	    Connection conn = pool.get(node);
	    conn.out.write(sb.toString().getBytes());
	    conn.out.flush();

	    HashMap<String, CasValue> values = null;
	    String s;
	    s = conn.in.readLine();
	    if (s.startsWith("VALUE")) {
		if (values == null) {
		    values = new HashMap<String, CasValue>();
		}
	    } else if (s.startsWith("END")) {
		return values;
	    } else if (s.startsWith("SERVER_ERROR")
		    || s.startsWith("CLIENT_ERROR") || s.startsWith("ERROR")) {
		throw new CommandException(s);
	    } else {
		throw new CommandException("Not supported yet.");
	    }

	    do {
		StringTokenizer t = new StringTokenizer(s);
		t.nextToken(); // VALUE
		String key = t.nextToken(); // key
		t.nextToken(); // 0
		int valueLen = Integer.parseInt(t.nextToken()); // len
		long cas = Long.parseLong(t.nextToken()); // cas ID

		// value
		byte[] value = new byte[valueLen];
		int offset = 0;
		int size = 0;
		while (offset < valueLen) {
		    size = conn.in.read(value, offset, valueLen - offset);
		    offset = offset + size;
		}
		conn.in.read(2); // "\r\n"
		values.put(key, new CasValue(cas, value));

		s = conn.in.readLine();
	    } while (!s.equals("END"));

	    return values;
	}
    }

    public boolean execute(CommandContext context) throws CommandException {
System.out.println("GETSWITHCASOPT EXECUTED!");
	// "gets <key>*\r\n"
	List<String> keys = (List<String>) context.get(CommandContext.KEYS);

	New_RoutingTable routingTable = (New_RoutingTable) context
		.get(CommandContext.ROUTING_TABLE);
	if (routingTable == null) {
	    throw new CommandException(new BadRoutingTableFormatException(
		    "routing table is null."));
	}

	Map<Node, List<String>> getsMap = new HashMap<Node, List<String>>();
	for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
	    String key = iter.next();
	    BigInteger hash = routingTable.getHash(key);
	    if (hash == null) {
		throw new CommandException(new BadRoutingTableFormatException(
			"hash is null."));
	    }
	    Node node = routingTable.searchNode(key, hash);
	    List<String> list = getsMap.get(node);
	    if (list == null) {
		list = new ArrayList<String>();
	    }
	    list.add(key);
	    getsMap.put(node, list);
	}

	if (executor == null) {
	    if (numOfThreads > 0) {
		executor = Executors.newFixedThreadPool(numOfThreads);
	    } else {
		executor = Executors.newCachedThreadPool();
	    }
	    // executor = Executors.newSingleThreadExecutor();
	    // executor = Executors.newCachedThreadPool();
	}

	Future<Map<String, CasValue>>[] futures = new Future[getsMap.size()];
	int i = 0;
	for (Iterator<Node> iter = getsMap.keySet().iterator(); iter.hasNext();) {
	    Node node = iter.next();
	    List<String> keys0 = getsMap.get(node);

	    CallableImpl c = new CallableImpl(context, node, keys0);
	    futures[i] = executor.submit(c);
	    i++;
	}

	Throwable t = null;
	try {
	    Map<String, CasValue> values = new HashMap<String, CasValue>();
	    for (int j = 0; j < futures.length; ++j) {
		Map<String, CasValue> ret = futures[j].get();
		for (Iterator<String> iter = ret.keySet().iterator(); iter
			.hasNext();) {
		    String k = iter.next();
		    CasValue b = ret.get(k);
		    values.put(k, b);
		}
	    }
	    context.put(CommandContext.RESULT, values);
	    return true;
	} catch (CancellationException e) {
	    t = e;
	} catch (ExecutionException e) {
	    t = e.getCause();
	} catch (InterruptedException e) {
	} // ignore

	// error handling
	if (t != null) {
	    throw new CommandException(t);
	}
	return false;
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
	throw new UnsupportedOperationException();
    }

    protected void create(CommandContext context) throws BadCommandException {
	throw new UnsupportedOperationException();
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	throw new UnsupportedOperationException();
    }
}