package jp.co.rakuten.rit.roma.client.routing;

import java.util.List;

import jp.co.rakuten.rit.roma.client.Node;

public interface New_RoutingTable {

	void create();

	void destroy();

	List<Node> getNodes();

	List<Node> searchNodes(String key);
}
