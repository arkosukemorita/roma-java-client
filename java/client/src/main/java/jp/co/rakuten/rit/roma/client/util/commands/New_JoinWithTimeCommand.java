package jp.co.rakuten.rit.roma.client.util.commands;

import java.io.IOException;
import java.util.StringTokenizer;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

/**
 * 
 */
public class New_JoinWithTimeCommand extends New_AbstractJoinCommand {

    protected String getCommand() throws ClientException {
	return ListCommandID.STR_ALIST_JOIN_WITH_TIME;
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	StringBuilder sb = (StringBuilder) context
		.get(CommandContext.STRING_DATA);
	try {
	    Connection conn = (Connection) context
		    .get(CommandContext.CONNECTION);
	    conn.out.write(sb.toString().getBytes());
	    conn.out.flush();

	    // VALUE <key> 0 <len>\r\n
	    // <size>\r\n
	    // VALUE <key> 0 <byte_len>\r\n
	    // <values>\r\n
	    // VALUE <key> 0 <byte_len>\r\n
	    // <times>\r\n
	    // END\r\n
	    // or SERVER_ERROR ... \r\n
	    String s;
	    s = conn.in.readLine();
	    if (s.startsWith("VALUE")) {
		;
	    } else if (s.startsWith("END")) {
		// return null;
		return false;
	    } else if (s.startsWith("SERVER_ERROR")
		    || s.startsWith("CLIENT_ERROR") || s.startsWith("ERROR")) {
		throw new CommandException(s);
	    } else {
		throw new CommandException("Not supported yet.");
	    }

	    // value
	    conn.in.readLine(); // <size>\r\n
	    s = conn.in.readLine(); // VALUE <key> 0 <byte len>\r\n
	    StringTokenizer t = new StringTokenizer(s);
	    t.nextToken(); // "VALUE"
	    t.nextToken(); // key
	    t.nextToken(); // 0
	    int valueLen = Integer.parseInt(t.nextToken()); // byte len

	    byte[] value = new byte[valueLen];
	    int offset = 0;
	    int size = 0;
	    while (offset < valueLen) {
		size = conn.in.read(value, offset, valueLen - offset);
		offset = offset + size;
	    }
	    conn.in.read(2); // "\r\n"
	    context.put(CommandContext.RESULT, value);

	    // time stump
	    s = conn.in.readLine(); // VALUE <key> 0 <byte_len>\r\n
	    t = new StringTokenizer(s);
	    t.nextToken(); // "VALUE"
	    t.nextToken(); // key
	    t.nextToken(); // "0"
	    int timeLen = Integer.parseInt(t.nextToken()); // time_len

	    byte[] value2 = new byte[timeLen];
	    offset = 0;
	    size = 0;
	    while (offset < timeLen) {
		size = conn.in.read(value2, offset, timeLen - offset);
		offset = offset + size;
	    }
	    context.put("RESULT2", value2);
	    conn.in.read(2); // "\r\n"
	    conn.in.readLine(); // "END\r\n"

	    // return value;
	    return true;
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }
}
