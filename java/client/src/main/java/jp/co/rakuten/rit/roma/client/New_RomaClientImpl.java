package jp.co.rakuten.rit.roma.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class New_RomaClientImpl extends New_AbstractRomaClientImpl {

	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	public void open(Node node) throws ClientException {
		// TODO Auto-generated method stub

	}

	public void open(List<Node> nodes) throws ClientException {
		// TODO Auto-generated method stub

	}
	public void close() throws ClientException {
		// TODO Auto-generated method stub

	}

	public boolean put(String key, byte[] value) throws ClientException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean put(String key, byte[] value, long expiry)
			throws ClientException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean put(String key, byte[] value, Date expiry)
			throws ClientException {
		// TODO Auto-generated method stub
		return false;
	}

	public byte[] get(String key) throws ClientException {
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		Map<String, byte[]> ret = multiget(keys);
		return ret.get(key);
	}

	public Map<String, byte[]> multiget(List<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

}
