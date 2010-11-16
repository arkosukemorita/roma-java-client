package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.RomaClientImpl;
import jp.co.rakuten.rit.roma.client.command.AbstractCommand;
import jp.co.rakuten.rit.roma.client.command.Command;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;
import jp.co.rakuten.rit.roma.client.routing.RoutingTable;
import junit.framework.TestCase;

public class CommandFactoryImplTest extends TestCase {

    public void testDummy() {
	assertTrue(true);
    }

    public void testCreateCommand1() throws Exception {
	CommandFactoryImpl commandFactory = new CommandFactoryImpl();
	commandFactory.addCommand(1, new TestCommand());
	Command command = commandFactory.getCommand(1);
	command.execute(new CommandContext());
    }

    public void testCreateCommand2() throws Exception {
	TimeoutCommand.timeout = 10;
	CommandContext context = new CommandContext();
	context.put(CommandContext.CONNECTION_POOL, new MockConnectionPool());
	context.put(CommandContext.ROUTING_TABLE, new RoutingTable(
		new RomaClientImpl()));
	context.put(CommandContext.COMMAND_ID, 1);
	CommandFactoryImpl commandFactory = new CommandFactoryImpl();
	commandFactory.addCommand(1, new TimeoutCommand(new TestCommand()));
	Command command = commandFactory.getCommand(1);
	command.execute(context);
    }

    public void testCreateCommand3() throws Exception {
	TimeoutCommand.timeout = 10;
	CommandContext context = new CommandContext();
	context.put(CommandContext.CONNECTION_POOL, new MockConnectionPool());
	context.put(CommandContext.ROUTING_TABLE, new RoutingTable(
		new RomaClientImpl()));
	context.put(CommandContext.COMMAND_ID, 1);
	CommandFactoryImpl commandFactory = new CommandFactoryImpl();
	commandFactory.addCommand(1, new FailOverDefaultCommand(new TimeoutCommand(
		new TestCommand())));
	Command command = commandFactory.getCommand(1);
	command.execute(context);
    }

    public static class TestCommand extends AbstractCommand {

	public TestCommand() {
	    super(null);
	}

	@Override
	public boolean execute(CommandContext context) throws CommandException {
	    try {
		Thread.sleep(100);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    System.out.println("execute");
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
}