package jp.co.rakuten.rit.roma.client.commands;

import jp.co.rakuten.rit.roma.client.command.CommandContext;
import jp.co.rakuten.rit.roma.client.command.CommandException;

public interface New_Command {

    boolean execute(CommandContext context) throws CommandException;
}
