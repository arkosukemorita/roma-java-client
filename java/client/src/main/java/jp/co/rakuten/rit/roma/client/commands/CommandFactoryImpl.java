package jp.co.rakuten.rit.roma.client.commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jp.co.rakuten.rit.roma.client.command.Command;
import jp.co.rakuten.rit.roma.client.command.CommandFactory;

public class CommandFactoryImpl implements CommandFactory {

    protected Map<Integer, Command> commandSet = new ConcurrentHashMap<Integer, Command>();

    public CommandFactoryImpl() {
	addCommands();
    }

    protected void addCommands() {

	// get command
	Command getCommand = new FailOverCommand(new TimeoutCommand(
		new GetCommand()));
	addCommand(CommandID.GET, getCommand);

	// gets command
	Command getsCommand = new FailOverCommand(new TimeoutCommand(
		new GetsCommand()));
	addCommand(CommandID.GETS, getsCommand);

	// gets_opt command
	Command getsOptCommand = new FailOverCommand(new TimeoutCommand(
		new GetsOptCommand()));
	addCommand(CommandID.GETS_OPT, getsOptCommand);

	// gets_with_casID command
	Command getsWithCasIDCommand = new FailOverCommand(new TimeoutCommand(
		new GetsWithCasIDCommand()));
	addCommand(CommandID.GETS_WITH_CASID, getsWithCasIDCommand);

	// gets_with_casID_opt command
	Command getsWithCasIDOptCommand = new FailOverCommand(
		new TimeoutCommand(new GetsWithCasIDOptCommand()));
	addCommand(CommandID.GETS_WITH_CASID_OPT, getsWithCasIDOptCommand);

	// set command
	Command setCommand = new FailOverCommand(new TimeoutCommand(
		new SetCommand()));
	addCommand(CommandID.SET, setCommand);

	// append command
	Command appendCommand = new FailOverCommand(new TimeoutCommand(
		new AppendCommand()));
	addCommand(CommandID.APPEND, appendCommand);

	// prepend command
	Command prependCommand = new FailOverCommand(new TimeoutCommand(
		new PrependCommand()));
	addCommand(CommandID.PREPEND, prependCommand);

	// delete command
	Command deleteCommand = new FailOverCommand(new TimeoutCommand(
		new DeleteCommand()));
	addCommand(CommandID.DELETE, deleteCommand);

	// increment command
	Command incrCommand = new FailOverCommand(new TimeoutCommand(
		new IncrCommand()));
	addCommand(CommandID.INCREMENT, incrCommand);

	// decrement command
	Command decrCommand = new FailOverCommand(new TimeoutCommand(
		new DecrCommand()));
	addCommand(CommandID.DECREMENT, decrCommand);

	// cas command
	Command casCommand = new FailOverCommand(new TimeoutCommand(
		new CasCommand()));
	addCommand(CommandID.CAS, casCommand);

	// expire command
	Command expireCommand = new FailOverCommand(new TimeoutCommand(
		new ExpireCommand()));
	addCommand(CommandID.EXPIRE, expireCommand);

	// routing_dump command
	Command routingdumpCommand = new TimeoutCommand(new RoutingdumpCommand(
		null));
	addCommand(CommandID.ROUTING_DUMP, routingdumpCommand);

	// routing_mklhash command
	Command routingmhtCommand = new TimeoutCommand(new RoutingmhtCommand(
		null));
	addCommand(CommandID.ROUTING_MKLHASH, routingmhtCommand);

	// add command
	Command addCommand = new FailOverCommand(new TimeoutCommand(
		new AddCommand()));
	addCommand(CommandID.ADD, addCommand);
    }

    public void addCommand(int commandID, Command command) {
	if (command == null) {
	    throw new NullPointerException(
		    "the specified command is a null object");
	}
	commandSet.put(commandID, command);
    }

    public Command getCommand(int commandID) {
	Command command = commandSet.get(new Integer(commandID));
	if (command == null) {
	    throw new NullPointerException("command is not defined: #"
		    + commandID);
	}
	return command;
    }

}
