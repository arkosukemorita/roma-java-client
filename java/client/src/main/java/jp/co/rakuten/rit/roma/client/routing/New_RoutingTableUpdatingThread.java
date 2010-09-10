package jp.co.rakuten.rit.roma.client.routing;

public interface New_RoutingTableUpdatingThread {

	boolean enabledRunning();

	void doStart();

	void doStop();
}
