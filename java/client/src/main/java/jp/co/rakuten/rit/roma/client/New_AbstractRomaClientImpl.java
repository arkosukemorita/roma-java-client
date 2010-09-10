package jp.co.rakuten.rit.roma.client;

import jp.co.rakuten.rit.roma.client.commands.New_CommandFactory;
import jp.co.rakuten.rit.roma.client.routing.New_RoutingTable;

public abstract class New_AbstractRomaClientImpl implements New_RomaClient {
	protected ConnectionPool connPool;
	protected New_RoutingTable routingTable;
	protected New_CommandFactory commandFactory;
	protected String hashName;
}
