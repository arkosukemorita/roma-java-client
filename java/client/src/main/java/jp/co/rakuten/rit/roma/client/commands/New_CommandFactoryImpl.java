package jp.co.rakuten.rit.roma.client.commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class New_CommandFactoryImpl implements New_CommandFactory {

	protected Map<Integer, New_Command> commandSet = new ConcurrentHashMap<Integer, New_Command>();

	public New_CommandFactoryImpl() {
		addCommands();
	}
	
	protected void addCommands() {
		// TODO
		// get command
		New_Command getCommand = new New_FailoverCommandImpl(
				new New_TimeoutCommandImpl(new New_GetCommandImpl()));
		addCommand(CommandID.GET, getCommand);
		// set command
		New_Command setCommand = new New_FailoverCommandImpl(
				new New_TimeoutCommandImpl(new New_SetCommandImpl(null)));
		addCommand(CommandID.SET, setCommand);
		// routingdump command
		New_Command routingdumpCommand = new New_TimeoutCommandImpl(
				new New_RoutingdumpCommandImpl(null));
		addCommand(CommandID.ROUTING_DUMP, routingdumpCommand);
		// and so on
	}

	public void addCommand(int commandID, New_Command command) {
		// TODO Auto-generated method stub
		if (command == null) {
			throw new NullPointerException(
					"the specified command is a null object");
		}
		commandSet.put(commandID, command);
	}

	public New_Command getCommand(int commandID) {
		// TODO Auto-generated method stub
		return commandSet.get(commandID);
	}

}
