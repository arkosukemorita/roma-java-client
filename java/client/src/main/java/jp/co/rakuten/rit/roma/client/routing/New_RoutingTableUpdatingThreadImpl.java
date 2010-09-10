package jp.co.rakuten.rit.roma.client.routing;

import jp.co.rakuten.rit.roma.client.commands.New_CommandFactory;

public class New_RoutingTableUpdatingThreadImpl implements
		New_RoutingTableUpdatingThread {

	private boolean enabledRunning;

	private New_CommandFactory commandFactory;

	private New_RoutingTable routingTable;

	private Thread thread;

	public New_RoutingTableUpdatingThreadImpl(
			New_CommandFactory commandFactory, New_RoutingTable routingTable) {
		// TODO
		this.enabledRunning = false;
		this.commandFactory = commandFactory;
		this.routingTable = routingTable;
	}

	public boolean enabledRunning() {
		return this.enabledRunning;
	}

	public void doStart() {
		// TODO Auto-generated method stub
		thread = new Thread() {
			@Override
			public void run() {
				// TODO
				// while loop
				// send routingmht and outingdump commands
				// updating the routing table
			}
		};
		thread.start();
	}

	public void doStop() {
		// TODO Auto-generated method stub

	}
}
