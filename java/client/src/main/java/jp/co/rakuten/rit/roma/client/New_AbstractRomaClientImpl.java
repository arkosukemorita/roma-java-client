package jp.co.rakuten.rit.roma.client;

import jp.co.rakuten.rit.roma.client.commands.FailOverFilter;
import jp.co.rakuten.rit.roma.client.commands.New_CommandFactory;
import jp.co.rakuten.rit.roma.client.commands.TimeoutFilter;
import jp.co.rakuten.rit.roma.client.routing.New_RoutingTable;

public abstract class New_AbstractRomaClientImpl implements New_RomaClient {

    protected ConnectionPool connPool;
    protected New_RoutingTable routingTable;
    protected New_CommandFactory commandFactory;
    protected String hashName;

    public final void setConnectionPool(ConnectionPool pool) {
        this.connPool = pool;
    }

    public final ConnectionPool getConnectionPool() {
        return connPool;
    }

    public final void setRoutingTable(New_RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    public final New_RoutingTable getRoutingTable() {
        return routingTable;
    }

    public final void setCommandFactory(New_CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public final New_CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public final void setTimeout(long timeout) {
        TimeoutFilter.timeout = timeout;
    }

    public final long getTimeout() {
        return TimeoutFilter.timeout;
    }

    public final void setNumOfThreads(int num) {
        TimeoutFilter.numOfThreads = num;
    }

    public final int getNumOfThreads() {
        return TimeoutFilter.numOfThreads;
    }

    public void setRetryCount(int retryCount) {
        FailOverFilter.retryThreshold = retryCount;
    }

    public int getRetryCount() {
        return FailOverFilter.retryThreshold;
    }

    public void setRetrySleepTime(long sleepTime) {
        FailOverFilter.sleepPeriod = sleepTime;
    }

    public long getRetrySleepTime() {
        return FailOverFilter.sleepPeriod;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public String getHashName() {
        return hashName;
    }

}
