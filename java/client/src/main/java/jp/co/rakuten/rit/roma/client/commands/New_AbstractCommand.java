package jp.co.rakuten.rit.roma.client.commands;

import java.io.IOException;

import jp.co.rakuten.rit.roma.client.ClientException;
import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

/**
 * 
 */
public abstract class New_AbstractCommand implements New_Command {

    protected New_Command next;

    public New_AbstractCommand(New_Command command) {
	this.next = command;
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

    protected abstract void create(CommandContext context)
	    throws ClientException;

    protected abstract void sendAndReceive(CommandContext context)
	    throws IOException, ClientException;

    protected abstract boolean parseResult(CommandContext context)
	    throws ClientException;
}
