package jp.co.rakuten.rit.roma.client;

import java.util.Date;
import java.util.List;

/**
 * This interface is provided as an interface for intaracting with ROMA.
 * 
 * The basic usage is written on
 * {@link jp.co.rakuten.rit.roma.client.RomaClientFactory}.
 * 
 * @version 0.3.5
 */
public interface New_RomaClient {

	boolean isOpen();

	/**
	 * Open a connection with a ROMA using the <code>Node</code> object.
	 * 
	 * @param node
	 * @throws jp.co.rakuten.rit.roma.client.ClientException
	 */
	void open(Node node) throws ClientException;

	/**
	 * Open a connnection with a ROMA using the <code>Node</code> objects.
	 * 
	 * @param nodes
	 * @throws jp.co.rakuten.rit.roma.client.ClientException
	 * @see #open(jp.co.rakuten.rit.roma.client.Node)
	 */
	void open(List<Node> nodes) throws ClientException;

	/**
	 * Close the connection.
	 * 
	 * @throws jp.co.rakuten.rit.roma.client.ClientException
	 * @see #open(java.lang.String)
	 */
	void close() throws ClientException;

	boolean put(String key, byte[] value) throws ClientException;

	boolean put(String key, byte[] value, long expiry) throws ClientException;

	/**
	 * Store the value in ROMA.
	 * 
	 * @param key
	 *            key to store value
	 * @param value
	 *            value to store
	 * @param expiry
	 *            expire time
	 * @return true, if the value was successfully stored
	 * @throws ClientException
	 */
	boolean put(String key, byte[] value, Date expiry) throws ClientException;

	/**
	 * Get a value with a key.
	 * 
	 * @param key
	 * @return
	 * @throws ClientException
	 */
	byte[] get(String key) throws ClientException;

}
