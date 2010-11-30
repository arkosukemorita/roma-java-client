package jp.co.rakuten.rit.roma.client.commands;

import java.util.Properties;

import jp.co.rakuten.rit.roma.client.AllTests;
import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.RomaClient;
import jp.co.rakuten.rit.roma.client.RomaClientFactory;
import jp.co.rakuten.rit.roma.client.RomaClientFactoryImpl;
import junit.framework.TestCase;

public class FailOverCommandPerfTest extends TestCase {

    private static Integer count = 0;
    private static String bar="bar bar";
    // private static int COUNT_THRESHOLD = 10000;
    private static String KEY;
    // private static byte[] VALUE;
    private static String NODE_ID = "10.184.17.7_11211";// AllTests.NODE_ID;
    private static RomaClient CLIENT = null;

    @Override
    public void setUp() throws Exception {
	RomaClientFactory factory = RomaClientFactoryImpl.getInstance();
	CLIENT = factory.newRomaClient(new Properties());
	CLIENT.setNumOfThreads(5);
	CLIENT.open(Node.create(NODE_ID));
	TimeoutCommand.timeout = 100 * 1000;
    }

    @Override
    public void tearDown() throws Exception {
	CLIENT.delete(KEY);
	CLIENT.close();
	CLIENT = null;
	KEY = null;
    }

    public static void testLoop() {
	// while (count<COUNT_THRESHOLD){
	while (true) {
	    KEY = "foo";
//	    loopPUT();
	    loopGET();
	}
    }

    public static void loopPUT() {
	try {
	    while (true) {
		System.out.println("###: test ends with: "
			+ CLIENT.put(KEY, count.toString().getBytes()));
		// assertTrue(CLIENT.put(KEY, count.toString().getBytes()));
		// assertEquals(count.toString(), new String(CLIENT.get(KEY)));
		++count;
		try {
		    Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		System.out.println("###: count: " + count);
	    }
	} catch (ClientException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void loopGET() {
	try {
	    assertTrue(CLIENT.put(KEY, bar.getBytes()));
	    while (true) {
		assertEquals(bar, new String(CLIENT.get(KEY)));
		try {
		    Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
	    }
	} catch (ClientException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
