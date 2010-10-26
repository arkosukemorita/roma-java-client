package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.util.StringTokenizer;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

public class New_GetCommand extends New_AbstractCommand implements
	CommandID {

    public New_GetCommand() {
	super(null);
    }

    protected void create(CommandContext context) throws ClientException {
	// "get key\r\n"
	StringBuilder sb = new StringBuilder();
	sb.append(STR_GET).append(STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(STR_ESC).append(
		context.get(CommandContext.HASH_NAME)).append(STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
	// do nothing
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	try {
	    StringBuilder sb = (StringBuilder) context
		    .get(CommandContext.STRING_DATA);
	    Connection conn = (Connection) context
		    .get(CommandContext.CONNECTION);
	    conn.out.write(sb.toString().getBytes());
	    conn.out.flush();
	    // VALUE key 0 <valueLen>\r\n<value>\r\nEND\r\n or END\r\n
	    String s;
	    s = conn.in.readLine();
	    if (s.startsWith("VALUE")) {
		;
	    } else if (s.startsWith("END")) {
		return false;
	    } else if (s.startsWith("SERVER_ERROR")
		    || s.startsWith("CLIENT_ERROR") || s.startsWith("ERROR")) {
		throw new CommandException(s);
	    } else {
		throw new CommandException("Not supported yet. s: " + s);
	    }

	    StringTokenizer t = new StringTokenizer(s);
	    t.nextToken(); // VALUE
	    t.nextToken(); // key
	    t.nextToken(); // 0

	    // BigInteger valueLen = new BigInteger(t.nextToken()); // len
	    int valueLen = Integer.parseInt(t.nextToken()); // len

	    // value
	    byte[] value = new byte[valueLen];
	    int offset = 0;
	    int size = 0;
	    while (offset < valueLen) {
		size = conn.in.read(value, offset, valueLen - offset);
		offset = offset + size;
	    }
	    context.put(CommandContext.RESULT, value);

	    conn.in.read(2); // "\r\n"
	    conn.in.readLine(); // "END\r\n"
	    return true;
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }

}
