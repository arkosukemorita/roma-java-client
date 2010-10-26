package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class New_AbstractGetsCommand extends New_AbstractCommand implements
	CommandID {

    public New_AbstractGetsCommand() {
	super(null);
    }

    protected void create(CommandContext context) throws ClientException {
	// "gets <key>*\r\n"
	StringBuilder sb = new StringBuilder();
	List<String> keys = (List<String>) context.get(CommandContext.KEYS);

	sb.append(STR_GETS);
	for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
	    sb.append(STR_WHITE_SPACE).append(iter.next()).append(STR_ESC)
		    .append(context.get(CommandContext.HASH_NAME));
	}
	sb.append(STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
	// do nothing
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	throw new ClientException("Fatal error: abstract command instanciation");
    }
}