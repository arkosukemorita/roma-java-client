package jp.co.rakuten.rit.roma.client.commands;

import java.math.BigInteger;

import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.command.Command;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;

public class FailOverCommand extends AbstractFailOverCommand {

    public FailOverCommand(Command command) {
	super(command);
    }

    public Node selectNode(RoutingTable routingTable, String key,
	    BigInteger hash, int retryCount) {
	return routingTable.searchNode(key, hash);
    }

}
