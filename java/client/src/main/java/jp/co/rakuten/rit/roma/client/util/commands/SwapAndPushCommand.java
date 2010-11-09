package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;

public class SwapAndPushCommand extends AbstractSwapCommand {

    protected String getCommand() throws ClientException {
	return ListCommandID.STR_ALIST_SWAP_AND_PUSH;
    }
}
