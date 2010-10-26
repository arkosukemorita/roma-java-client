package jp.co.rakuten.rit.roma.client.util.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;
import jp.co.rakuten.rit.roma.client.commands.New_AbstractCommand;

/**
 * 
 */
public class New_AbstractDeleteCommand extends New_AbstractCommand {

    public New_AbstractDeleteCommand() {
	super(null);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
	throw new UnsupportedOperationException();
    }

    protected void create(CommandContext context) throws ClientException {
	throw new UnsupportedOperationException();
    }


    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	// DELETED | NOT_DELETED | NOT_FOUND | SERVER_ERROR
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	String ret = sb.toString();
	if (ret.startsWith("DELETED")) {
	    return true;
	} else if (ret.startsWith("NOT_DELETED")) {
	    return false;
	} else if (ret.startsWith("NOT_FOUND")) {
	    return false;
	} else if (ret.startsWith("SERVER_ERROR")
		|| ret.startsWith("CLIENT_ERROR") || ret.startsWith("ERROR")) {
	    throw new CommandException(ret);
	} else {
	    return false;
	}

    }

}
