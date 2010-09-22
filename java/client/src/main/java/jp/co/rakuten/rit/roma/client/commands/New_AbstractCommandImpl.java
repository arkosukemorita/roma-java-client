package jp.co.rakuten.rit.roma.client.commands;

import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

public abstract class New_AbstractCommandImpl implements New_Command {

    protected New_Command next;

    public New_AbstractCommandImpl(New_Command command) {
        this.next = command;
    }

    public abstract boolean execute(CommandContext context) throws CommandException;

}
