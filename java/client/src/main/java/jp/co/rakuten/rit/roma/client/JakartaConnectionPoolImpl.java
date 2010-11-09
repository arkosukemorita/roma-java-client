package jp.co.rakuten.rit.roma.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class JakartaConnectionPoolImpl implements ConnectionPool {

    protected int size;
    private HashMap<Node, SocketPool> pool = new HashMap<Node, SocketPool>();

    public JakartaConnectionPoolImpl(final int size) {
	this.size = size;
    }

    public synchronized Connection get(Node node) throws IOException {
	SocketPool spool = pool.get(node);
	if (spool == null) {
	    spool = new SocketPool(node.host, node.port, size);
	    pool.put(node, spool);
	}
	Socket socket = null;
	try {
	    socket = spool.get();
	} catch (NoSuchElementException e) {
	    throw new IOException(e.getMessage());
	} catch (IllegalStateException e) {
	    throw new IOException(e.getMessage());
	} catch (ConnectException e) {
	    throw e;
	} catch (Exception e) {
	    throw new IOException(e.getMessage());
	}
	return new Connection(socket);
    }

    public synchronized void put(Node node, Connection conn) throws IOException {
	if (node == null || conn == null) {
	    return;
	}

	SocketPool spool = pool.get(node);
	try {
	    if (spool == null) {
		conn.close();
		return;
	    }
	    spool.put(conn.sock);
	} catch (Exception e) {
	    throw new IOException(e.getMessage());
	}
    }

    public synchronized void deleteAll(Node node) {
	SocketPool spool = pool.remove(node);
	try {
	    if (spool != null) {
		spool.close();
	    }
	} catch (IOException e) { // ignore
	}
    }

    public synchronized void delete(Node node, Connection conn) {
	SocketPool spool = pool.get(node);
	try {
	    if (conn != null) {
		if (spool != null) {
		    spool.remove(conn.sock);
		} else {
		    conn.close();
		}
	    }
	} catch (Exception e) { // ignore
	}
    }

    public synchronized void closeAll() {
	for (SocketPool spool : pool.values()) {
	    try {
		spool.close();
	    } catch (Exception e) { // ignore
		// throw new IOException(e.getMessage());
	    }
	}
	pool.clear();
    }
}
