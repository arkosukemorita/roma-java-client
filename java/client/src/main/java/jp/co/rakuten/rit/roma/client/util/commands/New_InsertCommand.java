package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

/**
 * 
 */
public class New_InsertCommand extends New_UpdateCommand {

    public New_InsertCommand() {
	super(null);
    }

    protected void create(CommandContext context) throws ClientException {
	// alist_insert <key> <index> <bytes>\r\n
	// <value>\r\n
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append(ListCommandID.STR_ALIST_INSERT).append(
		ListCommandID.STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(
		ListCommandID.STR_WHITE_SPACE).append(
		context.get(New_UpdateCommand.INDEX)).append(
		ListCommandID.STR_WHITE_SPACE).append(
		((byte[]) context.get(CommandContext.VALUE)).length).append(
		ListCommandID.STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }
}
