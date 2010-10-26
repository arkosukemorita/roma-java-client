package jp.co.rakuten.rit.roma.client.util.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

/**
 * 
 */
public class New_DeleteCommand extends New_AbstractDeleteCommand {

    protected void create(CommandContext context) throws ClientException {
	// alist_delete <key> <bytes>\r\n
	// <element>\r\n
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append(ListCommandID.STR_ALIST_DELETE).append(
		ListCommandID.STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(
		ListCommandID.STR_WHITE_SPACE).append(
		((byte[]) context.get(CommandContext.VALUE)).length).append(
		ListCommandID.STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	Connection conn = (Connection) context.get(CommandContext.CONNECTION);
	conn.out.write(sb.toString().getBytes());
	conn.out.write((byte[]) context.get(CommandContext.VALUE));
	conn.out.write(ListCommandID.STR_CRLF.getBytes());
	conn.out.flush();
	sb = new StringBuilder();
	sb.append(conn.in.readLine());
	context.put(CommandContext.STRING_DATA, sb);
    }

}
