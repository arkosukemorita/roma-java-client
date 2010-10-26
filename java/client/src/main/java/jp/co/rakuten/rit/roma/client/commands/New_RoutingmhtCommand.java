package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class New_RoutingmhtCommand extends New_AbstractCommand {

    public New_RoutingmhtCommand(New_Command command) {
	super(command);
    }

    @Override
    protected void create(CommandContext context) throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append("mklhash 0\r\n");
	context.put(CommandContext.STRING_DATA, sb);
    }

    @Override
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

    @Override
    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	context.put(CommandContext.RESULT, sb.toString());
	return true;
    }

}
