package jp.co.rakuten.rit.roma.client.util.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.AbstractCommand;
import jp.co.rakuten.rit.roma.client.command.CommandContext;

public class AbstractJoinCommand extends AbstractCommand {

    public AbstractJoinCommand() {
	super(null);
    }

    public static final String SEP = "_$$_";
    public static final String NULL = "";
    public static final String RANGE = "..";

    protected void create(CommandContext context) throws ClientException {
	// alist_join key <bytes>\r\n
	// value\r\n
	StringBuilder sb = new StringBuilder();
	sb.append(getCommand()).append(ListCommandID.STR_WHITE_SPACE).append(
		context.get(CommandContext.KEY)).append(
		ListCommandID.STR_WHITE_SPACE).append(SEP.getBytes().length);
	String range = (String) context.get(CommandContext.VALUE);
	if (!range.equals(NULL)) {
	    sb.append(ListCommandID.STR_WHITE_SPACE).append((String) range);
	}
	sb.append(ListCommandID.STR_CRLF).append(SEP).append(
		ListCommandID.STR_CRLF);
	context.put(CommandContext.STRING_DATA, sb);
    }

    protected void sendAndReceive(CommandContext context) throws IOException,
	    ClientException {
    }

    protected boolean parseResult(CommandContext context)
	    throws ClientException {
	throw new ClientException("Fatal error: abstract command instanciation");
    }

    protected String getCommand() throws ClientException {
	throw new ClientException("Fatal error: abstract command instanciation");
    }

}
