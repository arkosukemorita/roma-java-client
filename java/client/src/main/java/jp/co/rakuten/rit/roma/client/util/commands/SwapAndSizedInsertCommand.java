package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;

public class SwapAndSizedInsertCommand extends AbstractSizedCommand {

    protected String getCommand() throws ClientException {
	return ListCommandID.STR_ALIST_SWAP_AND_SIZED_INSERT;
    }

}
