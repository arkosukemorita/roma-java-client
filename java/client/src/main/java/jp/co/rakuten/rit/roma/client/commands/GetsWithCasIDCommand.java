package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import jp.co.rakuten.rit.roma.client.CasValue;
import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.Connection;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

public class GetsWithCasIDCommand extends AbstractGetsCommand implements
	CommandID {

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	try {
	    StringBuilder sb = (StringBuilder) context
		    .get(CommandContext.STRING_DATA);
	    Connection conn = (Connection) context
		    .get(CommandContext.CONNECTION);
	    conn.out.write(sb.toString().getBytes());
	    conn.out.flush();

	    // VALUE key 0 <valueLen>\r\n<value>\r\n or END\r\n
	    Map<String, CasValue> values = null;
	    String s;
	    s = conn.in.readLine();
	    if (s.startsWith("VALUE")) {
		values = new HashMap<String, CasValue>();
	    } else if (s.startsWith("END")) {
		return false;
	    } else if (s.startsWith("SERVER_ERROR")
		    || s.startsWith("CLIENT_ERROR") || s.startsWith("ERROR")) {
		throw new CommandException(s);
	    } else {
		throw new CommandException("Not supported yet.");
	    }

	    do {
		StringTokenizer t = new StringTokenizer(s);
		t.nextToken(); // VALUE
		String key = t.nextToken(); // key
		t.nextToken(); // 0
		int valueLen = Integer.parseInt(t.nextToken()); // len
		long cas = Long.parseLong(t.nextToken()); // cas ID

		// value
		byte[] value = new byte[valueLen];
		int offset = 0;
		int size = 0;
		while (offset < valueLen) {
		    size = conn.in.read(value, offset, valueLen - offset);
		    offset = offset + size;
		}
		conn.in.read(2); // "\r\n"
		values.put(key, new CasValue(cas, value));

		s = conn.in.readLine();
	    } while (!s.equals("END"));

	    context.put(CommandContext.RESULT, values);
	    return true;
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }
}
