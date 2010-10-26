package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;

/**
 * 
 */
public class New_SizedInsertCommand extends
	New_AbstractSizedCommand {

    protected String getCommand() throws ClientException {
	return ListCommandID.STR_ALIST_SIZED_INSERT;
    }

}
