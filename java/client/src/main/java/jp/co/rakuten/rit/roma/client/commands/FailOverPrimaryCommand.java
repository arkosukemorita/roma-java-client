package jp.co.rakuten.rit.roma.client.commands;

import java.math.BigInteger;
import java.util.List;

import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.command.Command;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;

public class FailOverPrimaryCommand extends AbstractFailOverCommand {

    public FailOverPrimaryCommand(Command command) {
	super(command);
    }

    public Node selectNode(RoutingTable routingTable, String key,
	    BigInteger hash, int retryCount) {
	List<Node> nodeList = routingTable.searchNodes(key, hash);
	System.out.println("###: nodes: " + nodeList);
	return nodeList.get(0); // returns Primary Node only
    }

}
