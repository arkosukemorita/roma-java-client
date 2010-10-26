package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.math.BigInteger;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

public class New_AbstractIncrAndDecrCommand extends New_AbstractCommand implements CommandID{

    public New_AbstractIncrAndDecrCommand() {
	super(null);
    }

    public New_AbstractIncrAndDecrCommand(New_Command command) {
	super(command);
    }

    protected void create(CommandContext context) throws ClientException {
	StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
	sb.append(getCommand()).append(STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY))
		.append(STR_ESC)
		.append(context.get(CommandContext.HASH_NAME))
		.append(STR_WHITE_SPACE)
		.append(context.get(CommandContext.VALUE))
		.append(STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected String getCommand() throws ClientException {
	throw new ClientException();
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
    ClientException {
	StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
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
	if (ret.startsWith("NOT_FOUND")) {
	    return false;
	} else if (ret.startsWith("SERVER_ERROR")
		|| ret.startsWith("CLIENT_ERROR")
		|| ret.startsWith("ERROR")) {
	    throw new CommandException(ret);
	} else { // big integer
	    context.put(CommandContext.RESULT, new BigInteger(ret));
	    return true;
	}
    }
}