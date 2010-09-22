package jp.co.rakuten.rit.roma.client.routing;

public class New_RoutingInfoUpdatingThread extends Thread {

    private New_RoutingTable routingTable;
    public boolean stopped = false;
    private int pollingPeriod;

    public New_RoutingInfoUpdatingThread(New_RoutingTable routingTable) {
        this.routingTable = routingTable;
        pollingPeriod = routingTable.interval;
    }

    public void doStart() {
        start();
    }

    public void doStop() {
        stopped = true;
    }

    @Override
    public void run() {
        while (routingTable.enableLoop()) {
            if (stopped) {
                break;
            }
            try {
                Thread.sleep(pollingPeriod);
                routingTable.update();
            } catch (InterruptedException e) {
            } // ignore
        }
    }
}
