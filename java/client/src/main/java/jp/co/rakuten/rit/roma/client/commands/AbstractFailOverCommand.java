package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jp.co.rakuten.rit.roma.client.BadRoutingTableFormatException;
import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Config;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.ConnectionPool;
import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.command.AbstractCommand;
import jp.co.rakuten.rit.roma.client.command.Command;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;

public abstract class AbstractFailOverCommand extends AbstractCommand {

    public AbstractFailOverCommand(Command command) {
	super(command);
    }

    public static long sleepPeriod = Long
	    .parseLong(Config.DEFAULT_RETRY_SLEEP_TIME);
    public static int retryThreshold = Integer
	    .parseInt(Config.DEFAULT_RETRY_THRESHOLD);

    public abstract Node selectNode(RoutingTable routingTable, String key,
	    BigInteger hash, int rertyCount);

    private void incrFailCount(CommandContext context, Throwable t) {
	RoutingTable routingTable = (RoutingTable) context
		.get(CommandContext.ROUTING_TABLE);
	Node node = (Node) context.get(CommandContext.NODE);
	Connection conn = (Connection) context.get(CommandContext.CONNECTION);
	ConnectionPool connPool = (ConnectionPool) context
		.get(CommandContext.CONNECTION_POOL);
	if (t instanceof IOException || t instanceof TimeoutException) {
	    routingTable.incrFailCount(node);
	} else if (t instanceof ClientException
		&& t.getCause() instanceof IOException) {
	    routingTable.incrFailCount(node);
	}
	connPool.delete(node, conn);
    }

    private void handleErrorMessage(List<String> errorList, Exception e,
	    Throwable t) {
	String errorMessage = null;
	t = e.getCause();
	if (t != null) {
	    errorMessage = e.getMessage() + ":" + t.toString() + ":"
		    + t.getMessage();
	} else {
	    errorMessage = e.getMessage() + "null";
	}
	errorList.add(errorMessage);
    }

    @Override
    public boolean execute(CommandContext context) throws CommandException {
	RoutingTable routingTable = (RoutingTable) context
		.get(CommandContext.ROUTING_TABLE);
	if (routingTable == null) {
	    throw new CommandException(new BadRoutingTableFormatException(
		    "routing table is null."));
	}
	String key = (String) context.get(CommandContext.KEY);
	BigInteger hash = routingTable.getHash(key);
	if (hash == null) {
	    throw new CommandException(new BadRoutingTableFormatException(
		    "hash is null."));
	}

	int retryCount = 0;
	List<String> errorList = new ArrayList<String>();
	Throwable t = null;

	// start to run next command
	while (true) {
	    try {
		Node node = selectNode(routingTable, key, hash, retryCount);
		context.put(CommandContext.HASH, hash);
		context.put(CommandContext.NODE, node);
		return next.execute(context);
	    } catch (CommandException e) {
		System.out.println("###: Handle error message!");
		handleErrorMessage(errorList, e, t);
	    }

	    // TODO ### delete 3 lines below
	    if (t == null) {
		System.out.println("###: t is null!! t:" + t);
	    }

	    // re-try message-passing or handle an error
	    if (t != null) {
		System.out.println("###: t is NOT null!! t:" + t);
		try {
		    System.out.println("###: incrFailCount called");
		    incrFailCount(context, t);
		    Thread.sleep(sleepPeriod);
		} catch (InterruptedException e) { // ignore
		}
		if (retryCount < retryThreshold) {
		    System.out.println("###: retryCount incremented");
		    retryCount++;
		} else {
		    CommandException e = new CommandException(
			    new RetryOutException(errorList.toString()));
		    errorList.clear();
		    throw e;
		}
	    }
	}
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
	throw new UnsupportedOperationException();
    }

    protected void create(CommandContext context) throws ClientException {
	throw new UnsupportedOperationException();
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	throw new UnsupportedOperationException();
    }

}
