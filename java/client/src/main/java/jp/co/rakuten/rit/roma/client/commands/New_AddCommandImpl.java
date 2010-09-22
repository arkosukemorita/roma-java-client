package jp.co.rakuten.rit.roma.client.commands;


public class New_AddCommandImpl extends New_StoreCommand {

    @Override
    public String getCommand() throws BadCommandException {
        return STR_ADD;
    }
}
