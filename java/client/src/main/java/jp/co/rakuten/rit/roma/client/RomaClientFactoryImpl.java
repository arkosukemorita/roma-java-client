package jp.co.rakuten.rit.roma.client;

import java.util.Properties;

import jp.co.rakuten.rit.roma.client.command.CommandFactory;
import jp.co.rakuten.rit.roma.client.commands.CommandFactoryImpl;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;

/**
 * Factory for creating an instance of ROMA client. Sample code might look like:
 * 
 * <blockquote>
 * 
 * <pre>
 * public static void main(String[] args) throws Exception {
 *     New_RomaClientFactoryImpl factory = New_RomaClientFactoryImpl.getInstance();
 *     New_RomaClient client = factory.newRomaClient(new Properties());
 *     // initial nodes
 *     List&lt;Node&gt; initNodes = new ArrayList&lt;Node&gt;();
 *     initNodes.add(Node.create(&quot;localhost_11211&quot;));
 *     initNodes.add(Node.create(&quot;localhost_11212&quot;));
 *     // open a connection
 *     client.open(nodes);
 *     // put a pair of a key and a value
 *     String key = &quot;key01&quot;;
 *     client.put(key, &quot;value01&quot;.getBytes());
 *     key = &quot;key02&quot;;
 *     client.put(key, &quot;value02&quot;.getBytes());
 *     // get the stored value
 *     byte[] b = client.get(&quot;key01&quot;);
 *     // close the connection
 *     client.close();
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * @version 0.3.5
 * 
 */
public class RomaClientFactoryImpl implements RomaClientFactory {

    private static RomaClientFactory INSTANCE = null;

    public static RomaClientFactory getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new RomaClientFactoryImpl();
	}
	return INSTANCE;
    }

    protected RoutingTable routingTable = null;
    protected ConnectionPool connPool = null;
    protected CommandFactory commandFactory = null;

    protected RomaClientFactoryImpl() {
    }

    public void setRoutingTable(RoutingTable routingTable) {
	this.routingTable = routingTable;
    }

    public void setConnectionPool(ConnectionPool connPool) {
	this.connPool = connPool;
    }

    public void setCommandFactory(CommandFactory commandFactory) {
	this.commandFactory = commandFactory;
    }

    /**
     * Create a new instance of ROMA client.
     * 
     * @return
     * @throws ClientException
     */
    public RomaClient newRomaClient() throws ClientException {
	return newRomaClient(new Properties());
    }

    /**
     * Create a new instance of ROMA client.
     * 
     * @param props
     * @return
     * @throws ClientException
     */
    public RomaClient newRomaClient(Properties props) throws ClientException {
	RomaClient client = new RomaClientImpl();

	// routing table
	if (routingTable == null) {
	    routingTable = new RoutingTable(client);
	}
	client.setRoutingTable(routingTable);

	// connection pool
	if (connPool == null) {
	    String size0 = props.getProperty(Config.CONNECTION_POOL_SIZE,
		    Config.DEFAULT_CONNECTION_POOL_SIZE);
	    try {
		int size = Integer.parseInt(size0);
		connPool = new JakartaConnectionPoolImpl(size);
		// connPool = new HashMapConnectionPoolImpl(size);
	    } catch (NumberFormatException e) {
		throw new ClientException(e);
	    }
	}
	client.setConnectionPool(connPool);

	// timeout & timeout threadnum
	try {
	    String period0 = props.getProperty(Config.TIMEOUT_PERIOD,
		    Config.DEFAULT_TIMEOUT_PERIOD);
	    long period = Long.parseLong(period0);
	    client.setTimeout(period);

	    String num0 = props.getProperty(Config.NUM_OF_THREADS,
		    Config.DEFAULT_NUM_OF_THREADS);
	    int num = Integer.parseInt(num0);
	    client.setNumOfThreads(num);

	    String retryCount0 = props.getProperty(Config.RETRY_THRESHOLD,
		    Config.DEFAULT_RETRY_THRESHOLD);
	    int retryCount = Integer.parseInt(retryCount0);
	    client.setRetryCount(retryCount);

	    String retrySleepTime0 = props.getProperty(Config.RETRY_SLEEP_TIME,
		    Config.DEFAULT_RETRY_SLEEP_TIME);
	    long sleepTime = Long.parseLong(retrySleepTime0);
	    client.setRetrySleepTime(sleepTime);
	} catch (NumberFormatException e) {
	    throw new ClientException(e);
	}

	// command generator
	if (commandFactory == null) {
	    commandFactory = new CommandFactoryImpl();
	}
	client.setCommandFactory(commandFactory);

	// hash name
	String hashName = props.getProperty(Config.HASH_NAME,
		Config.DEFAULT_HASH_NAME);
	client.setHashName(hashName);

	return (RomaClient) client;
    }
}
