package jp.co.rakuten.rit.roma.client.commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class New_CommandFactoryImpl implements New_CommandFactory {

    protected Map<Integer, New_Command> commandSet = new ConcurrentHashMap<Integer, New_Command>();

    public New_CommandFactoryImpl() {
	addCommands();
    }
    
    protected void addCommands() {
	
	// get command
	New_Command getCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_GetCommand()));
	addCommand(CommandID.GET, getCommand);
	
	// gets command
	New_Command getsCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_GetsCommand()));
	addCommand(CommandID.GETS, getsCommand);
	
	// gets_opt command
	New_Command getsOptCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_GetsOptCommand()));
	addCommand(CommandID.GETS_OPT, getsOptCommand);
	
	// gets_with_casID command
	New_Command getsWithCasIDCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_GetsWithCasIDCommand()));
	addCommand(CommandID.GETS_WITH_CASID, getsWithCasIDCommand);
	
	// gets_with_casID_opt command
	New_Command getsWithCasIDOptCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_GetsWithCasIDOptCommand()));
	addCommand(CommandID.GETS_WITH_CASID_OPT, getsWithCasIDOptCommand);
	
	// set command
	New_Command setCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_SetCommand()));
	addCommand(CommandID.SET, setCommand);
	
	// append command
	New_Command appendCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_AppendCommand()));
	addCommand(CommandID.APPEND, appendCommand);

	// prepend command
	New_Command prependCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_PrependCommand()));
	addCommand(CommandID.PREPEND, prependCommand);

	// delete command
	New_Command deleteCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_DeleteCommand()));
	addCommand(CommandID.DELETE, deleteCommand);

	// increment command
	New_Command incrCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_IncrCommand()));
	addCommand(CommandID.INCREMENT, incrCommand);

	// decrement command
	New_Command decrCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_DecrCommand()));
	addCommand(CommandID.DECREMENT, decrCommand);

	// cas command
	New_Command casCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_CasCommand()));
	addCommand(CommandID.CAS, casCommand);

	// expire command
	New_Command expireCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_ExpireCommand()));
	addCommand(CommandID.EXPIRE, expireCommand);

	// routing_dump command
	New_Command routingdumpCommand = new New_TimeoutCommand(
		new New_RoutingdumpCommand(null));
	addCommand(CommandID.ROUTING_DUMP, routingdumpCommand);

	// routing_mklhash command
	New_Command routingmhtCommand = new New_TimeoutCommand(
		new New_RoutingmhtCommand(null));
	addCommand(CommandID.ROUTING_MKLHASH, routingmhtCommand);

	// add command
	New_Command addCommand = new New_FailoverCommand(
		new New_TimeoutCommand(new New_AddCommand()));
	addCommand(CommandID.ADD, addCommand);
    }

    public void addCommand(int commandID, New_Command command) {
        if (command == null) {
            throw new NullPointerException(
                    "the specified command is a null object");
        }
        commandSet.put(commandID, command);
    }

    public New_Command getCommand(int commandID) {
	New_Command command = commandSet.get(new Integer(commandID));
	if (command == null) {
	    throw new NullPointerException("command is not defined: #"
		    + commandID);
	}
	return command;
    }

}
