package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;
import net.arnx.jsonic.JSON;

public class New_RoutingdumpCommandImpl extends New_AbstractCommandImpl {

    public New_RoutingdumpCommandImpl(New_Command command) {
        super(command);
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
        StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
        sb.append("routingdump json\r\n");
        context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
            ClientException {
        StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
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

    protected boolean parseResult(CommandContext context)
            throws ClientException {
        StringBuilder sb = (StringBuilder) context.get(CommandContext.STRING_DATA);
        Object obj = JSON.decode(sb.toString());
        // Object obj = Yaml.load(new String(c));
        context.put(CommandContext.RESULT, obj);
        return true;
    }

}