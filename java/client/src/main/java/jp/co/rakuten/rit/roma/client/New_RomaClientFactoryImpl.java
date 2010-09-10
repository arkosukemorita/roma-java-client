package jp.co.rakuten.rit.roma.client;

import java.util.Properties;

import jp.co.rakuten.rit.roma.client.commands.New_CommandFactory;
import jp.co.rakuten.rit.roma.client.commands.New_CommandFactoryImpl;
import jp.co.rakuten.rit.roma.client.routing.New_RoutingTable;

/**
 * Factory for creating an instance of ROMA client.  
 * Sample code might look like:
 * 
 * <blockquote><pre>
 * public static void main(String[] args) throws Exception {
 *     RomaClientFactory factory = new RomaClientFactoryImpl();
 *     RomaClient client = factory.newRomaClient(new Properties());
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
 * </pre></blockquote>
 * 
 * @version 0.3.5
 * 
 */
public class New_RomaClientFactoryImpl implements New_RomaClientFactory {

	protected New_RomaClient instance = null;

	public New_RomaClientFactoryImpl() {
	}

	protected New_RoutingTable createRoutingTable() {
		// TODO
		return null;
	}

	protected New_CommandFactory createCommandFactory() {
		// TODO
		New_CommandFactory commandFactory = new New_CommandFactoryImpl();
		return commandFactory;
	}

	protected ConnectionPool createConnectionPool() {
		// TODO
		return new JakartaConnectionPoolImpl(10);
	}

	public New_RomaClient newRomaClient() {
		return newRomaClient(new Properties());
	}

	public New_RomaClient newRomaClient(Properties props) {
		if (instance == null) {
			instance = new New_RomaClientImpl();
			
			// TODO Auto-generated method stub
			// like RomaClientFactory#newRomaClient(Properties)
			// this method invoke createConnectionPool(),
			// createCommandFactory(),
			// and createRoutingTable()
		}
		return instance;
	}
}
