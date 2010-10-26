package jp.co.rakuten.rit.roma.client.commands;

import jp.co.rakuten.rit.roma.client.ClientException;


public class New_AddCommand extends New_AbstractStoreCommand {

    @Override
    public String getCommand() throws ClientException {
        return STR_ADD;
    }
}
