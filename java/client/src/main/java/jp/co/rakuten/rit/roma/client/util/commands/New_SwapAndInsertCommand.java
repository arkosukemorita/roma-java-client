package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;

/**
 * 
 */
public class New_SwapAndInsertCommand extends New_AbstractSwapCommand {

    protected String getCommand() throws ClientException {
	return ListCommandID.STR_ALIST_SWAP_AND_INSERT;
    }
}
