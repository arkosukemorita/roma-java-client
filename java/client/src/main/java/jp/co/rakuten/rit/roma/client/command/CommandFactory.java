package jp.co.rakuten.rit.roma.client.command;


public interface CommandFactory {

    void addCommand(int commandID, Command command);

    Command getCommand(int commandID);
}
