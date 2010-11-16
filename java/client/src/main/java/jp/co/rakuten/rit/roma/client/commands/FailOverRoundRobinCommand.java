package jp.co.rakuten.rit.roma.client.commands;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.command.Command;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;

public class FailOverRoundRobinCommand extends AbstractFailOverCommand {

    public FailOverRoundRobinCommand(Command command) {
	super(command);
    }

    public Node selectNode(RoutingTable routingTable, String key,
	    BigInteger hash, int retryCount) {
	Node node = null;
	List<Node> nodeList = routingTable.searchNodes(key, hash);
	Iterator<Node> nodes;
	nodes = nodeList.iterator();

	for (int i = 0; i < retryCount; ++i) {
	    node = nodes.next();
	}
	return node;
    }

}
