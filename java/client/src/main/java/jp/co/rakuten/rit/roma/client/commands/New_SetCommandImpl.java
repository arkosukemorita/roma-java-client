package jp.co.rakuten.rit.roma.client.commands;

public class New_SetCommandImpl extends New_StoreCommand {

    @Override
    public String getCommand() throws BadCommandException {
	return STR_SET;
    }
}
