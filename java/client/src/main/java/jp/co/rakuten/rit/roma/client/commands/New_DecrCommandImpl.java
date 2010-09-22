package jp.co.rakuten.rit.roma.client.commands;

public class New_DecrCommandImpl extends New_IncrAndDecrCommand {

    @Override
    public String getCommand() throws BadCommandException {
        return STR_DECREMENT;
    }
}
