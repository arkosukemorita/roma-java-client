package jp.co.rakuten.rit.roma.client.util;

import java.util.List;
import java.util.Properties;

import jp.co.rakuten.rit.roma.client.New_AllTests;
import jp.co.rakuten.rit.roma.client.New_RomaClient;
import jp.co.rakuten.rit.roma.client.New_RomaClientFactory;
import jp.co.rakuten.rit.roma.client.New_RomaClientFactoryImpl;
import jp.co.rakuten.rit.roma.client.Node;
import junit.framework.TestCase;

public class New_ListWrapper2Test extends TestCase {
    private static String NODE_ID = New_AllTests.NODE_ID;

    private static String KEY_PREFIX = New_ListWrapper2Test.class.getName();

    private static New_RomaClient CLIENT = null;

    private static New_ListWrapper LISTUTIL = null;

    private static String KEY = null;

    public New_ListWrapper2Test() {
	super();
    }

    @Override
    public void setUp() throws Exception {
	New_RomaClientFactory factory = New_RomaClientFactoryImpl.getInstance();
	CLIENT = factory.newRomaClient(new Properties());
	LISTUTIL = new New_ListWrapper(CLIENT);
	CLIENT.open(Node.create(NODE_ID));
    }

    @Override
    public void tearDown() throws Exception {
	LISTUTIL.deleteList(KEY);
	LISTUTIL = null;
	CLIENT.close();
	CLIENT = null;
	KEY = null;
    }

    public void testGet01() throws Exception {
	KEY = KEY_PREFIX + "testGet01";
	assertTrue(LISTUTIL.append(KEY, "01".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "02".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "03".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "04".getBytes()));
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY);
	assertEquals(4, list.size());
	assertEquals("01", new String(((New_ListWrapper.Entry) list.get(0))
		.getValue()));
	assertEquals("02", new String(((New_ListWrapper.Entry) list.get(1))
		.getValue()));
	assertEquals("03", new String(((New_ListWrapper.Entry) list.get(2))
		.getValue()));
	assertEquals("04", new String(((New_ListWrapper.Entry) list.get(3))
		.getValue()));
    }

    public void testGet02() throws Exception {
	KEY = KEY_PREFIX + "testGet02";
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY);
	assertTrue(list != null);
	assertTrue(list.size() == 0);
    }

    public void testGet03() throws Exception {
	KEY = KEY_PREFIX + "testGet03";
	assertTrue(LISTUTIL.append(KEY, "01".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "02".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "03".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "04".getBytes()));
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY, 1, 2);
	assertEquals(2, list.size());
	assertEquals("02", new String(((New_ListWrapper.Entry) list.get(0))
		.getValue()));
	assertEquals("03", new String(((New_ListWrapper.Entry) list.get(1))
		.getValue()));
    }

    public void testGet04() throws Exception {
	KEY = KEY_PREFIX + "testGet04";
	assertTrue(LISTUTIL.append(KEY, "01".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "02".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "03".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "04".getBytes()));
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY, 2, 5);
	assertEquals(2, list.size());
	assertEquals("03", new String(((New_ListWrapper.Entry) list.get(0))
		.getValue()));
	assertEquals("04", new String(((New_ListWrapper.Entry) list.get(1))
		.getValue()));
    }

    public void testGet05() throws Exception {
	KEY = KEY_PREFIX + "testGet05";
	try {
	    LISTUTIL.getEntries(KEY, -1, -1);
	    fail();
	} catch (Exception e) {
	    // assertTrue(e instanceof IndexOutOfBoundsException);
	    assertTrue(e instanceof IllegalArgumentException);
	}
    }

    public void testGet06() throws Exception {
	LISTUTIL = new New_ListWrapper(CLIENT, 3);
	KEY = KEY_PREFIX + "testGet06";
	assertTrue(LISTUTIL.append(KEY, "01".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "02".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "03".getBytes()));
	assertFalse(LISTUTIL.append(KEY, "04".getBytes()));
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY);
	assertEquals(3, list.size());
	assertEquals("01", new String(((New_ListWrapper.Entry) list.get(0))
		.getValue()));
	assertEquals("02", new String(((New_ListWrapper.Entry) list.get(1))
		.getValue()));
	assertEquals("03", new String(((New_ListWrapper.Entry) list.get(2))
		.getValue()));
    }

    public void testGet07() throws Exception {
	LISTUTIL = new New_ListWrapper(CLIENT, 3);
	KEY = KEY_PREFIX + "testGet07";
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY);
	assertTrue(list != null);
	assertEquals(0, list.size());
    }

    public void testGet08() throws Exception {
	LISTUTIL = new New_ListWrapper(CLIENT, 3);
	KEY = KEY_PREFIX + "testGet08";
	assertTrue(LISTUTIL.append(KEY, "01".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "02".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "03".getBytes()));
	assertFalse(LISTUTIL.append(KEY, "04".getBytes()));
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY, 1, 2);
	assertEquals(2, list.size());
	assertEquals("02", new String(((New_ListWrapper.Entry) list.get(0))
		.getValue()));
	assertEquals("03", new String(((New_ListWrapper.Entry) list.get(1))
		.getValue()));
    }

    public void testGet09() throws Exception {
	LISTUTIL = new New_ListWrapper(CLIENT, 3);
	KEY = KEY_PREFIX + "testGet09";
	assertTrue(LISTUTIL.append(KEY, "01".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "02".getBytes()));
	assertTrue(LISTUTIL.append(KEY, "03".getBytes()));
	assertFalse(LISTUTIL.append(KEY, "04".getBytes()));
	List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY, 2, 5);
	assertEquals(1, list.size());
	assertEquals("03", new String(((New_ListWrapper.Entry) list.get(0))
		.getValue()));
    }

    public void testGet10() throws Exception {
	LISTUTIL = new New_ListWrapper(CLIENT, 3);
	KEY = KEY_PREFIX + "testGet10";
	try {
	    LISTUTIL.getEntries(KEY, -1, -1);
	    fail();
	} catch (Exception e) {
	    // assertTrue(e instanceof IndexOutOfBoundsException);
	    assertTrue(e instanceof IllegalArgumentException);
	}
    }

    public void testExpire01() throws Exception {
	LISTUTIL = new New_ListWrapper(CLIENT, 3);
	KEY = KEY_PREFIX + "testExpire01";
	try {
	    assertTrue(LISTUTIL.append(KEY, "01".getBytes()));
	    assertTrue(LISTUTIL.append(KEY, "02".getBytes()));
	    assertTrue(LISTUTIL.append(KEY, "03".getBytes()));
	    
	    long d = 2; // ***fixed***
	    CLIENT.expire(KEY, d);
	    
	    List<New_ListWrapper.Entry> list = LISTUTIL.getEntries(KEY);
	    assertEquals(3, list.size());
	    assertEquals("01", new String(((New_ListWrapper.Entry) list.get(0)).getValue()));
	    assertEquals("02", new String(((New_ListWrapper.Entry) list.get(1)).getValue()));
	    assertEquals("03", new String(((New_ListWrapper.Entry) list.get(2)).getValue()));
	    
	    Thread.sleep(3000);
	    
	    list = LISTUTIL.getEntries(KEY);
	    assertEquals(0, list.size());
	    
	    list = LISTUTIL.getEntries(KEY, 2, 5);
	    assertEquals(0, list.size());

	    assertTrue(LISTUTIL.append(KEY, "04".getBytes()));
	    assertTrue(LISTUTIL.append(KEY, "05".getBytes()));
	    assertTrue(LISTUTIL.append(KEY, "06".getBytes()));
	    
	    list = LISTUTIL.getEntries(KEY);
	    assertEquals(3, list.size());
	    assertEquals("04", new String(((New_ListWrapper.Entry) list.get(0)).getValue()));
	    assertEquals("05", new String(((New_ListWrapper.Entry) list.get(1)).getValue()));
	    assertEquals("06", new String(((New_ListWrapper.Entry) list.get(2)).getValue()));
	} finally {
	    CLIENT.delete(KEY);
	}
    }
}
