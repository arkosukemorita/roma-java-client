package jp.co.rakuten.rit.roma.client.routing;

import java.util.List;

import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.commands.New_CommandFactory;

public class New_RoutingTableImpl implements New_RoutingTable {

	private New_RoutingTableUpdatingThread thread;

	public New_RoutingTableImpl(New_CommandFactory commandFactory) {
		// TODO
		thread = new New_RoutingTableUpdatingThreadImpl(commandFactory, this);
	}

	public void create() {
		// TODO
		if (!thread.enabledRunning()) {
			throw new RuntimeException(
					"a thread for checking the routing table has already created.");
		}
		thread.doStart();
	}

	public void destroy() {
		thread.doStop();
		// TODO Auto-generated method stub
	}

	public List<Node> getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Node> searchNodes(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
