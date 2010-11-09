package jp.co.rakuten.rit.roma.client.util.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.AbstractCommand;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class AbstractGetsCommand extends AbstractCommand {

    public AbstractGetsCommand() {
	super(null);
    }

    protected String getCommand() throws ClientException {
	throw new ClientException("Fatal error: abstract command instanciation");
    }

    protected void create(CommandContext context) throws ClientException {
	// alist_gets/gets_with_time <key> [index|range] [forward]\r\n #
	StringBuilder sb = new StringBuilder();
	sb.append(getCommand()).append(ListCommandID.STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY));
	String range = (String) context.get(CommandContext.VALUE);
	if (!range.equals(JoinCommand.NULL)) {
	    sb.append(ListCommandID.STR_WHITE_SPACE).append(range);
	}
	sb.append(ListCommandID.STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	throw new UnsupportedOperationException();
    }
}
