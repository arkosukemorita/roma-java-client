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
	New_Command getCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_GetCommandImpl()));
	addCommand(CommandID.GET, getCommand);
	
	// gets command
	New_Command getsCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_GetsCommandImpl()));
	addCommand(CommandID.GETS, getsCommand);
	
	// gets_opt command
	New_Command getsOptCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_GetsOptCommandImpl()));
	addCommand(CommandID.GETS_OPT, getsOptCommand);
	
	// gets_with_casID command
	New_Command getsWithCasIDCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_GetsWithCasIDCommandImpl()));
	addCommand(CommandID.GETS_WITH_CASID, getsWithCasIDCommand);
	
	// gets_with_casID_opt command
	New_Command getsWithCasIDOptCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_GetsWithCasIDOptCommandImpl()));
	addCommand(CommandID.GETS_WITH_CASID_OPT, getsWithCasIDOptCommand);
	
	// set command
	New_Command setCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_SetCommandImpl()));
	addCommand(CommandID.SET, setCommand);
	
	// append command
	New_Command appendCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_AppendCommandImpl()));
	addCommand(CommandID.APPEND, appendCommand);

	// prepend command
	New_Command prependCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_PrependCommandImpl()));
	addCommand(CommandID.PREPEND, prependCommand);

	// delete command
	New_Command deleteCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_DeleteCommandImpl()));
	addCommand(CommandID.DELETE, deleteCommand);

	// increment command
	New_Command incrCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_IncrCommandImpl()));
	addCommand(CommandID.INCREMENT, incrCommand);

	// decrement command
	New_Command decrCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_DecrCommandImpl()));
	addCommand(CommandID.DECREMENT, decrCommand);

	// cas command
	New_Command casCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_CasCommandImpl()));
	addCommand(CommandID.CAS, casCommand);

	// expire command
	New_Command expireCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_ExpireCommandImpl()));
	addCommand(CommandID.EXPIRE, expireCommand);

	// routing_dump command
	New_Command routingdumpCommand = new New_TimeoutCommandImpl(
		new New_RoutingdumpCommandImpl(null));
	addCommand(CommandID.ROUTING_DUMP, routingdumpCommand);

	// routing_mklhash command
	New_Command routingmhtCommand = new New_TimeoutCommandImpl(
		new New_RoutingmhtCommandImpl(null));
	addCommand(CommandID.ROUTING_MKLHASH, routingmhtCommand);

	// add command
	New_Command addCommand = new New_FailoverCommandImpl(
		new New_TimeoutCommandImpl(new New_AddCommandImpl()));
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
