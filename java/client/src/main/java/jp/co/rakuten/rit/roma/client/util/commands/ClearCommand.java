package jp.co.rakuten.rit.roma.client.util.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.AbstractCommand;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

public class ClearCommand extends AbstractCommand {

    public ClearCommand() {
	super(null);
    }

    protected void create(CommandContext context) throws ClientException {
	// alist_clear <key>\r\n
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append(ListCommandID.STR_ALIST_CLEAR).append(
		ListCommandID.STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(ListCommandID.STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	Connection conn = (Connection) context.get(CommandContext.CONNECTION);
	conn.out.write(sb.toString().getBytes());
	conn.out.flush();
	sb = new StringBuilder();
	sb.append(conn.in.readLine());
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	String ret = sb.toString();
	// CLEARED | NOT_CLEARED | NOT_FOUND? | SERVER_ERROR
	if (ret.startsWith("CLEARED")) {
	    return true;
	} else if (ret.startsWith("NOT_CLEARED")) {
	    return false;
	} else if (ret.startsWith("NOT_FOUND")) {
	    return false;
	    // throw new ClientException("Not found");
	} else if (ret.startsWith("SERVER_ERROR")
		|| ret.startsWith("CLIENT_ERROR") || ret.startsWith("ERROR")) {
	    throw new CommandException(ret);
	} else {
	    return false;
	}
    }
}
