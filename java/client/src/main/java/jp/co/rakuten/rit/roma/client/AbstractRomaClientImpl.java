package jp.co.rakuten.rit.roma.client;

import jp.co.rakuten.rit.roma.client.command.CommandFactory;
import jp.co.rakuten.rit.roma.client.commands.FailOverCommand;
import jp.co.rakuten.rit.roma.client.commands.TimeoutCommand;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;

public abstract class AbstractRomaClientImpl implements RomaClient {

    protected ConnectionPool connPool;
    protected RoutingTable routingTable;
    protected CommandFactory commandFactory;
    protected String hashName;

    public final void setConnectionPool(ConnectionPool pool) {
	this.connPool = pool;
    }

    public final ConnectionPool getConnectionPool() {
	return connPool;
    }

    public final void setRoutingTable(RoutingTable routingTable) {
	this.routingTable = routingTable;
    }

    public final RoutingTable getRoutingTable() {
	return routingTable;
    }

    public final void setCommandFactory(CommandFactory commandFactory) {
	this.commandFactory = commandFactory;
    }

    public final CommandFactory getCommandFactory() {
	return commandFactory;
    }

    public final void setTimeout(long timeout) {
	TimeoutCommand.timeout = timeout;
    }

    public final long getTimeout() {
	return TimeoutCommand.timeout;
    }

    public final void setNumOfThreads(int num) {
	TimeoutCommand.numOfThreads = num;
    }

    public final int getNumOfThreads() {
	return TimeoutCommand.numOfThreads;
    }

    public void setRetryCount(int retryCount) {
	FailOverCommand.retryThreshold = retryCount;
    }

    public int getRetryCount() {
	return FailOverCommand.retryThreshold;
    }

    public void setRetrySleepTime(long sleepTime) {
	FailOverCommand.sleepPeriod = sleepTime;
    }

    public long getRetrySleepTime() {
	return FailOverCommand.sleepPeriod;
    }

    public void setHashName(String hashName) {
	this.hashName = hashName;
    }

    public String getHashName() {
	return hashName;
    }

}
