package jp.co.rakuten.rit.roma.client;

import java.util.Properties;

public interface New_RomaClientFactory {

	New_RomaClient newRomaClient();

	New_RomaClient newRomaClient(Properties props);
}
