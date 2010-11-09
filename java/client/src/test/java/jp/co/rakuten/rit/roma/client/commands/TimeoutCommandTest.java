package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.AbstractCommand;
import jp.co.rakuten.rit.roma.client.command.Command;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;
import jp.co.rakuten.rit.roma.client.command.CommandFactory;
import junit.framework.TestCase;

public class TimeoutCommandTest extends TestCase {
    private static long PERIOD_OF_SLEEP;

    private static int NUM_OF_THREADS = 10;

    public static class TestCommand extends AbstractCommand {

	public TestCommand() {
	    super(null);
	}

	@Override
	public boolean execute(CommandContext context) {
	    try {
		Thread.sleep(PERIOD_OF_SLEEP);
		return true;
	    } catch (InterruptedException e) { // ignore
	    }
	    return false;
	}

	@Override
	protected void create(CommandContext context) throws ClientException {
	}

	@Override
	protected boolean parseResult(CommandContext context)
		throws ClientException {
	    return false;
	}

	@Override
	protected void sendAndReceive(CommandContext context)
		throws IOException, ClientException {
	}
    }

    public void testAroundExecute01() throws Exception {
	TimeoutCommand.timeout = 100;
	TimeoutCommandTest.PERIOD_OF_SLEEP = 1;
	int commandID = 1000;
	CommandContext context = new CommandContext();
	context.put(CommandContext.CONNECTION_POOL, new MockConnectionPool());
	context.put(CommandContext.COMMAND_ID, commandID);
	CommandFactory commandFactory = new CommandFactoryImpl();
	commandFactory.addCommand(commandID, new TimeoutCommand(
		new TestCommand()));
	Command command = commandFactory.getCommand(commandID);
	command.execute(context);
    }

    public void testAroundExecute02() throws Exception {
	TimeoutCommand.timeout = 100;
	TimeoutCommandTest.PERIOD_OF_SLEEP = 1000;
	int commandID = 1001;
	CommandContext context = new CommandContext();
	context.put(CommandContext.CONNECTION_POOL, new MockConnectionPool());
	context.put(CommandContext.COMMAND_ID, commandID);
	CommandFactory commandFactory = new CommandFactoryImpl();
	commandFactory.addCommand(commandID, new TimeoutCommand(
		new TestCommand()));
	Command command = commandFactory.getCommand(commandID);
	try {
	    command.execute(context);
	    fail();
	} catch (Exception e) {
	    assertTrue(e instanceof CommandException);
	    Throwable t = e.getCause();
	    assertTrue(t instanceof TimeoutException);
	}
    }

    public void testAroundExecute03() throws Exception {
	TimeoutCommand.timeout = 100;
	TimeoutCommandTest.PERIOD_OF_SLEEP = 1000;
	final int commandID = 1002;
	final CommandFactory commandFactory = new CommandFactoryImpl();
	commandFactory.addCommand(commandID, new TimeoutCommand(
		new TestCommand()));

	Thread[] threads = new Thread[NUM_OF_THREADS];
	for (int i = 0; i < threads.length; ++i) {
	    threads[i] = new Thread() {
		@Override
		public void run() {
		    try {
			CommandContext context = new CommandContext();
			context.put(CommandContext.COMMAND_ID, commandID);
			context.put(CommandContext.CONNECTION_POOL,
				new MockConnectionPool());
			Command command = commandFactory.getCommand(commandID);
			assert0(command, context);
		    } catch (Exception e) {
		    }
		}
	    };
	}
	for (int i = 0; i < threads.length; ++i) {
	    threads[i].start();
	}
    }

    static boolean assert0(Command command, CommandContext context) {
	try {
	    return command.execute(context);
	} catch (Exception e) {
	    assertTrue(e instanceof CommandException);
	    Throwable t = e.getCause();
	    assertTrue(t instanceof TimeoutException);
	}
	return false;
    }
}
