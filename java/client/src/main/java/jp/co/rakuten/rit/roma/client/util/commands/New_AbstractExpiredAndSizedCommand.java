package jp.co.rakuten.rit.roma.client.util.commands;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class New_AbstractExpiredAndSizedCommand extends New_UpdateCommand {

    public New_AbstractExpiredAndSizedCommand() {
	super(null);
    }

    protected String getCommand() throws ClientException {
	throw new ClientException("Fatal error: abstract command instanciation");
    }

    @Override
    protected void create(CommandContext context) throws ClientException {
	// alist_expired_swap_and_sized_insert/push <key> <expire-time>
	// <array-size>
	// <bytes>\r\n
	// <value>\r\n
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append(getCommand()).append(ListCommandID.STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(
		ListCommandID.STR_WHITE_SPACE).append(
		context.get(New_UpdateCommand.EXPIRY)).append(
		ListCommandID.STR_WHITE_SPACE).append(
		context.get(New_UpdateCommand.ARRAY_SIZE)).append(
		ListCommandID.STR_WHITE_SPACE).append(
		((byte[]) context.get(CommandContext.VALUE)).length).append(
		ListCommandID.STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }
}
