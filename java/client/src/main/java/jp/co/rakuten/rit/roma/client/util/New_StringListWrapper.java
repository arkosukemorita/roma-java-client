package jp.co.rakuten.rit.roma.client.util;

import java.util.List;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.New_RomaClient;

/**
 * 
 * Sample code might look like:
 * 
 * <blockquote>
 * 
 * <pre>
 * public static void main(String[] args) throws Exception {
 *     List&lt;Node&gt; initNodes = new ArrayList&lt;Node&gt;();
 *     initNodes.add(Node.create(&quot;localhost_11211&quot;));
 *     initNodes.add(Node.create(&quot;localhost_11212&quot;));
 *     // create and initialize the instance of ROMA client
 *     RomaClientFactory fact = RomaClientFactory.getInstance();
 *     RomaClient client = fact.newRomaClient();
 * 
 *     // create and initialize ROMA client's wrapper for List API
 *     StringListWrapper listWrapper = new StringListWrapper(client, true, 3);
 *     List&lt;String&gt; ret = null;
 *     // open connections with ROMA
 *     client.open(initNodes);
 *     // prepend
 *     listWrapper.prepend(&quot;muga&quot;, &quot;v1&quot;);
 *     listWrapper.prepend(&quot;muga&quot;, &quot;v2&quot;);
 *     listWrapper.prepend(&quot;muga&quot;, &quot;v3&quot;);
 *     listWrapper.prepend(&quot;muga&quot;, &quot;v4&quot;);
 *     // ret is [ &quot;v4&quot;, &quot;v3&quot;, &quot;v2&quot; ]
 *     ret = listWrapper.get(&quot;muga&quot;);
 *     // deleteAndPrepend 
 *     listWrapper.deleteAndPrepend(&quot;muga&quot;, v2);
 *     ret = listWrapper.get(&quot;muga&quot;);
 *     // ret is [ &quot;v2&quot;, &quot;v4&quot;, &quot;v3&quot; ]
 *     // close the connection
 *     client.close();
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * 
 */
public class New_StringListWrapper {

    public static class Entry {

        private String value;
        private long time;

        Entry(byte[] value, long time) {
            this(new String(value), time);
        }

        Entry(String value, long time) {
            this.value = value;
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public long getTime() {
            return time;
        }

        @Override
        public String toString() {
            return value + "_" + time;
        }
    }
    protected New_StringListAdaptor adaptor;
    private boolean useCommands;

    public New_StringListWrapper(New_RomaClient client) throws ClientException {
        this(new New_StringWrapper(client), false, 0, 0);
    }

    public New_StringListWrapper(New_RomaClient client, int listSize)
            throws ClientException {
        this(new New_StringWrapper(client), false, listSize, 0);
    }

    public New_StringListWrapper(New_RomaClient client, long expiry)
            throws ClientException {
        this(new New_StringWrapper(client), false, 0, expiry);
    }

    public New_StringListWrapper(New_StringWrapper appender) throws ClientException {
        this(appender, false, 0, 0);
    }

    public New_StringListWrapper(New_StringWrapper wrapper, int listSize)
            throws ClientException {
        this(wrapper, false, listSize, 0);
    }

    public New_StringListWrapper(New_StringWrapper wrapper, long expiry)
            throws ClientException {
        this(wrapper, false, 0, expiry);
    }

    public New_StringListWrapper(New_RomaClient client, boolean useCommands)
            throws ClientException {
        this(new New_StringWrapper(client), useCommands, 0, 0);
    }

    public New_StringListWrapper(New_RomaClient client, boolean useCommands,
            int listSize) throws ClientException {
        this(new New_StringWrapper(client), useCommands, listSize, 0);
    }

    public New_StringListWrapper(New_RomaClient client, boolean useCommands,
            long expiry) throws ClientException {
        this(new New_StringWrapper(client), useCommands, 0, expiry);
    }

    public New_StringListWrapper(New_RomaClient client, boolean useCommands,
            int listSize, long expiry) throws ClientException {
        this(new New_StringWrapper(client), useCommands, listSize, expiry);
    }

    public New_StringListWrapper(New_StringWrapper wrapper, boolean useCommands)
            throws ClientException {
        this(wrapper, useCommands, 0, 0);
    }

    public New_StringListWrapper(New_StringWrapper wrapper, boolean useCommands,
            int listSize) throws ClientException {
        this(wrapper, useCommands, listSize, 0);
    }

    public New_StringListWrapper(New_StringWrapper wrapper, boolean useCommands,
            long expiry) throws ClientException {
        this(wrapper, useCommands, 0, expiry);
    }

    public New_StringListWrapper(New_StringWrapper wrapper, boolean useCommands,
            int listSize, long expiry) throws ClientException {
        this.useCommands = useCommands;
        if (this.useCommands) {
            adaptor = (New_StringListAdaptor) new New_StringListAdaptor2Impl(wrapper);
        } else {
            adaptor = (New_StringListAdaptor) new New_StringListAdaptor1Impl(wrapper);
        }
        adaptor.setListSize(listSize);
        adaptor.setExpiry(expiry);
    }

    public boolean usedCommands() {
        return adaptor instanceof New_StringListAdaptor2Impl;
    }

    public int getListSize() {
        return adaptor.getListSize();
    }

    public long getExpiry() {
        return adaptor.getExpiry();
    }

    public New_StringWrapper getStringAppender() {
        return adaptor.getStringWrapper();
    }

    public boolean append(String key, String value) throws ClientException {
        return adaptor.append(key, value);
    }

    public void deleteList(String key) throws ClientException {
        adaptor.deleteList(key);
    }

    public boolean delete(String key, int index) throws ClientException {
        return adaptor.delete(key, index);
    }

    public boolean delete(String key, String value) throws ClientException {
        return adaptor.delete(key, value);
    }

    public boolean deleteAndAppend(String key, String value)
            throws ClientException {
        return adaptor.deleteAndAppend(key, value);
    }

    public boolean deleteAndPrepend(String key, String value)
            throws ClientException {
        return adaptor.deleteAndPrepend(key, value);
    }

    public List<String> get(String key) throws ClientException {
        return adaptor.get(key);
    }

    public List<Entry> getEntries(String key) throws ClientException {
        return adaptor.getEntries(key);
    }

    public List<String> get(String key, int begin, int len)
            throws ClientException {
        return adaptor.get(key, begin, len);
    }

    public List<Entry> getEntries(String key, int begin, int len)
            throws ClientException {
        return adaptor.getEntries(key, begin, len);
    }

    public boolean prepend(String key, String value) throws ClientException {
        return adaptor.prepend(key, value);
    }

    public int size(String key) throws ClientException {
        return adaptor.size(key);
    }
}
