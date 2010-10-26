package jp.co.rakuten.rit.roma.client.commands;

import jp.co.rakuten.rit.roma.client.ClientException;

public class New_DecrCommand extends New_AbstractIncrAndDecrCommand {

    @Override
    public String getCommand() throws ClientException {
	return STR_DECREMENT;
    }
}
