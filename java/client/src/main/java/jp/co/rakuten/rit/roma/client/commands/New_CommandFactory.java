package jp.co.rakuten.rit.roma.client.commands;

public interface New_CommandFactory {

	void addCommand(int commandID, New_Command command);

	New_Command getCommand(int commandID);
}
