package jp.co.rakuten.rit.roma.client.commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jp.co.rakuten.rit.roma.client.Node;
import jp.co.rakuten.rit.roma.client.routing.New_RoutingTable;

public class New_FailoverCommandImpl extends New_AbstractCommandImpl {

	private static Map<Node, Integer> failCounts = new ConcurrentHashMap<Node, Integer>();
	
	private int threshold;

	public New_FailoverCommandImpl(New_Command command) {
		super(command);
	}

	@Override
	public boolean execute(New_CommandContext context) {
		// TODO Auto-generated method stub
		// this body is similar to the body of FailOverFilterb#aroundExecute()
		// method
		return false;
	}

}
