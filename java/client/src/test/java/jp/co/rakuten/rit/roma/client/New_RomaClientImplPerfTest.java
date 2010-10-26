package jp.co.rakuten.rit.roma.client;

import java.util.Properties;

import jp.co.rakuten.rit.roma.client.commands.TimeoutFilter;
import junit.framework.TestCase;

public class New_RomaClientImplPerfTest extends TestCase {
    private static String NODE_ID = New_AllTests.NODE_ID;

    private static String KEY_PREFIX = New_RomaClientImplPerfTest.class.getName();

    private static New_RomaClient CLIENT = null;

    public static int BIG_LOOP_COUNT = 100;

    public static int SMALL_LOOP_COUNT = 1000;

    public static int SIZE_OF_DATA = 100;

    public static int NUM_OF_THREADS = 5;

    public static long PERIOD_OF_SLEEP = 1;

    public static long PERIOD_OF_TIMEOUT = 300;

    public New_RomaClientImplPerfTest() {
	super();
    }

    @Override
    public void setUp() throws Exception {
	New_RomaClientFactory factory = New_RomaClientFactoryImpl.getInstance();
	CLIENT = factory.newRomaClient(new Properties());
	CLIENT.open(Node.create(NODE_ID));
	TimeoutFilter.timeout = 100 * 1000;
    }

    @Override
    public void tearDown() throws Exception {
	CLIENT.close();
	CLIENT = null;
    }

    public void testDummy() {
	assertTrue(true);
    }

    public void XtestPutLoop01() throws Exception {
	big_loop();
	assertTrue(true);
    }

    public void XtestPutLoop02() throws Exception {
	Thread[] threads = new Thread[NUM_OF_THREADS];
	for (int i = 0; i < threads.length; ++i) {
	    threads[i] = new Thread() {
		@Override
		public void run() {
		    try {
			big_loop();
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    };
	}
	for (int i = 0; i < threads.length; ++i) {
	    threads[i].start();
	}

	while (true) {
	    Thread.sleep(1000);
	}
    }

    private void big_loop() throws Exception {
	int count = 0;
	while (count < BIG_LOOP_COUNT) {
	    small_loop(count);
	    count++;
	}
    }

    private void small_loop(int big_count) throws Exception {
	int count = 0;
	int count_threshold = 0;
	String dummy_prefix = makeDummyPrefix();
	long time0 = System.currentTimeMillis();
	while (count < SMALL_LOOP_COUNT) {
	    if (count % 1000 == 0) {
		System.out.println("count: " + count);
	    }
	    try {
		long time = System.currentTimeMillis();
		CLIENT.put(KEY_PREFIX + count, (dummy_prefix + count)
			.getBytes());
		//CLIENT.get(KEY_PREFIX + count);
		time = System.currentTimeMillis() - time;
		if (time > PERIOD_OF_TIMEOUT) {
		    count_threshold++;
		}
	    } catch (Exception e) {
		e.printStackTrace();
		throw e;
	    } finally {
		Thread.sleep(PERIOD_OF_SLEEP);
		count++;
	    }
	}
	time0 = System.currentTimeMillis() - time0;

	StringBuilder sb = new StringBuilder();
	sb.append("qps: ").append(
		(int) (((double) (SMALL_LOOP_COUNT * 1000)) / time0)).append(
		" ").append("(timeout count: ").append(count_threshold).append(
		")");
	System.out.println(sb.toString());
    }

    private static final char A = 'a';

    private static String makeDummyPrefix() {
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < SIZE_OF_DATA; ++i) {
	    sb.append(A);
	}
	sb.append("::");
	return sb.toString();
    }
}
