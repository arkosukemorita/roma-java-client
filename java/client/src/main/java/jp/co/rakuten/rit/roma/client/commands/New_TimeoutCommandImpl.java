package jp.co.rakuten.rit.roma.client.commands;

import java.util.concurrent.ExecutorService;

public class New_TimeoutCommandImpl extends New_AbstractCommandImpl {

	private static ExecutorService executor;

	public New_TimeoutCommandImpl(New_Command command) {
		super(command);
	}

	@Override
	public boolean execute(New_CommandContext context) {
		// TODO Auto-generated method stub
		// invoke the body of a TimeoutFilter method
		return false;
	}

}
