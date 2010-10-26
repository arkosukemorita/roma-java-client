package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import net.arnx.jsonic.JSON;

public class New_RoutingdumpCommand extends New_AbstractCommand {

    public New_RoutingdumpCommand(New_Command command) {
	super(command);
    }

    @Override
    protected void create(CommandContext context) throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	sb.append("routingdump json\r\n");
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
	String s;
	while (!(s = conn.in.readLine()).startsWith("END")) {
	    sb.append(s);
	}
	context.put(CommandContext.STRING_DATA, sb);
    }

    @Override
    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	Object obj = JSON.decode(sb.toString());
	// Object obj = Yaml.load(new String(c));
	context.put(CommandContext.RESULT, obj);
	return true;
    }

}