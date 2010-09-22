package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.util.Date;

import jp.co.rakuten.rit.roma.client.CasResponse;
import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

public class New_CasCommandImpl extends New_AbstractCommandImpl implements CommandID {

    public New_CasCommandImpl() {
	super(null);
    }

    public boolean execute(CommandContext context) throws CommandException {
	try {
	    StringBuilder sb = new StringBuilder();
	    context.put(CommandContext.STRING_DATA, sb);
	    create(context);
	    sendAndReceive(context);
	    return parseResult(context);
	} catch (ClientException e) {
	    throw new CommandException(e);
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }

    protected void create(CommandContext context) throws BadCommandException {
	StringBuilder sb =
	    (StringBuilder) context.get(CommandContext.STRING_DATA);
	sb.append(STR_CAS)
	.append(STR_WHITE_SPACE)
	.append(context.get(CommandContext.KEY))
	.append(STR_ESC)
	.append(context.get(CommandContext.HASH_NAME))
	.append(STR_WHITE_SPACE)
	.append(context.get(CommandContext.HASH))
	.append(STR_WHITE_SPACE).append(
		((Date) context.get(CommandContext.EXPIRY)).getTime() / 1000)
		.append(STR_WHITE_SPACE)
		.append(((byte[]) context.get(CommandContext.VALUE)).length)
		.append(STR_WHITE_SPACE)
		.append(context.get(CommandContext.CAS_ID))
		.append(STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException {
	StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
	Connection conn = (Connection) context.get(CommandContext.CONNECTION);
	conn.out.write(sb.toString().getBytes());
	conn.out.write(((byte[]) context.get(CommandContext.VALUE)));
	conn.out.write(STR_CRLF.getBytes());
	conn.out.flush();
	sb = new StringBuilder();
	sb.append(conn.in.readLine());
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected boolean parseResult(CommandContext context)
    throws ClientException {
	StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
	String ret = sb.toString();
	if (ret.startsWith("STORED")) {
	    context.put(CommandContext.RESULT, CasResponse.OK);
	    return true;
	} else if (ret.startsWith("EXISTS")) {
	    context.put(CommandContext.RESULT, CasResponse.EXISTS);
	    return true;
	} else if (ret.startsWith("NOT_FOUND")) {
	    context.put(CommandContext.RESULT, CasResponse.NOT_FOUND);
	    return true;
	} else if (ret.startsWith("SERVER_ERROR")
		|| ret.startsWith("CLENT_ERROR")
		|| ret.startsWith("ERROR")) {
	    throw new ClientException(ret);
	}
	return false;
    }
}