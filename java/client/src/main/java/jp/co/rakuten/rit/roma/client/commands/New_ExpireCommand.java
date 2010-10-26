package jp.co.rakuten.rit.roma.client.commands;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class New_ExpireCommand extends New_AbstractStoreCommand {

    @Override
    public void create(CommandContext context) throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append(getCommand()).append(STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(STR_ESC).append(
		context.get(CommandContext.HASH_NAME)).append(STR_WHITE_SPACE)
		.append(context.get(CommandContext.EXPIRY)).append(STR_CRLF);
	// FIXME
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected String getCommand() throws ClientException {
	return STR_EXPIRE;
    }
}