package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class New_ExpiredSwapAndSizedPushCommand extends New_AbstractExpiredAndSizedCommand {

    protected String getCommand() throws ClientException {
	return ListCommandID.STR_ALIST_EXPIRED_SWAP_AND_SIZED_PUSH;
    }

    @Override
    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	String s = sb.toString();
	// STORED | NOT_STORED | SERVER_ERROR
	if (s.startsWith("STORED")) {
	    return true;
	} else if (s.startsWith("NOT_STORED")) {
	    return false;
	} else if (s.startsWith("NOT_PUSHED")) {
	    return false;
	} else if (s.startsWith("SERVER_ERROR") || s.startsWith("CLIENT_ERROR")
		|| s.startsWith("ERROR")) {
	    throw new ClientException(s);
	} else {
	    return false;
	}
    }
}
