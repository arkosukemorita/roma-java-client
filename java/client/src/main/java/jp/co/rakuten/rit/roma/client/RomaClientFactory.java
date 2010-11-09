package jp.co.rakuten.rit.roma.client;

import java.util.Properties;

public interface RomaClientFactory {

	RomaClient newRomaClient() throws ClientException;

	RomaClient newRomaClient(Properties props) throws ClientException;
}
