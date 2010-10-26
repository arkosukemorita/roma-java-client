package jp.co.rakuten.rit.roma.client.util.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;
import jp.co.rakuten.rit.roma.client.commands.New_AbstractCommand;

/**
 * 
 */
public class New_LengthCommand extends New_AbstractCommand {

    public New_LengthCommand() {
	super(null);
    }

    protected void create(CommandContext context) throws ClientException {
	// alist_length <key>\r\n
	StringBuilder sb = new StringBuilder();
	sb.append(ListCommandID.STR_ALIST_LENGTH).append(
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
	StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
	String ret = sb.toString();
	// length | NOT_FOUND | SERVER_ERROR
	if (ret.startsWith("SERVER_ERROR") || ret.startsWith("CLIENT_ERROR")
		|| ret.startsWith("ERROR")) {
	    throw new CommandException(ret);
	} else if (ret.startsWith("NOT_FOUND")) {
	    context.put(CommandContext.RESULT, new Integer(-1));
	    return false;
	    // throw new ClientException("Not found");
	} else {
	    context.put(CommandContext.RESULT, new Integer(ret));
	    return true;
	}
    }
}
