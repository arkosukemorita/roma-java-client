package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.util.Date;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class New_AbstractStoreCommand extends New_AbstractCommand implements
	CommandID {

    public New_AbstractStoreCommand() {
	super(null);
    }

    public void create(CommandContext context) throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append(getCommand()).append(STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(STR_ESC).append(
		context.get(CommandContext.HASH_NAME)).append(STR_WHITE_SPACE)
		.append(context.get(CommandContext.HASH)).append(
			STR_WHITE_SPACE).append(
			context.get(CommandContext.EXPIRY)).append(
			STR_WHITE_SPACE).append(
			((byte[]) context.get(CommandContext.VALUE)).length)
		.append(STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    public static long toLong(Object expiry) {
	if (expiry instanceof Date) {
	    return ((Date) expiry).getTime() / 1000;
	} else { // Long
	    return (Long) expiry;
	}
    }

    protected String getCommand() throws ClientException {
	throw new ClientException("Fatal error: abstract command instanciation");
    }

    public void sendAndReceive(CommandContext context) throws IOException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	Connection conn = (Connection) context.get(CommandContext.CONNECTION);
	conn.out.write(sb.toString().getBytes());
	conn.out.write(((byte[]) context.get(CommandContext.VALUE)));
	conn.out.write(STR_CRLF.getBytes());
	conn.out.flush();
	sb = new StringBuilder();
	sb.append(conn.in.readLine());
	context.put(CommandContext.STRING_DATA, sb);
    }

    public boolean parseResult(CommandContext context) throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	String ret = sb.toString();
	if (ret.startsWith("STORED")) {
	    return true;
	} else if (ret.startsWith("NOT_STORED")) {
	    return false;
	} else if (ret.startsWith("SERVER_ERROR")
		|| ret.startsWith("CLIENT_ERROR") || ret.startsWith("ERROR")) {
	    throw new ClientException(ret);
	}
	return false;
    }
}