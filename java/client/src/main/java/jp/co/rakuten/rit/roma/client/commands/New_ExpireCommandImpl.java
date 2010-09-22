package jp.co.rakuten.rit.roma.client.commands;

import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class New_ExpireCommandImpl extends New_StoreCommand {

    public void create(CommandContext context) throws BadCommandException {
        StringBuilder sb =
                (StringBuilder) context.get(CommandContext.STRING_DATA);
        sb.append(getCommand())
                .append(STR_WHITE_SPACE)
                .append(context.get(CommandContext.KEY))
                .append(STR_ESC)
                .append(context.get(CommandContext.HASH_NAME))
                .append(STR_WHITE_SPACE)
                .append(context.get(CommandContext.EXPIRY))
                .append(STR_CRLF);
        context.put(CommandContext.STRING_DATA, sb);
    }

    protected String getCommand() throws BadCommandException {
        return STR_EXPIRE;
    }
}