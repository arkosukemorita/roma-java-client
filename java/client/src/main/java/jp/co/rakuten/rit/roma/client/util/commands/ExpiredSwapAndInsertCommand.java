package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;

public class ExpiredSwapAndInsertCommand extends AbstractExpiredCommand {

    protected String getCommand() throws ClientException {
	return ListCommandID.STR_ALIST_EXPIRED_SWAP_AND_INSERT;
    }

}
