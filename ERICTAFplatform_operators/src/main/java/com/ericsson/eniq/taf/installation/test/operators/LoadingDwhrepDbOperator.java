package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.cifwk.taf.datasource.TestDataSourceFormatter;
import com.ericsson.eniq.taf.db.EniqDBOperator;
import com.google.inject.Singleton;

@Singleton
public class LoadingDwhrepDbOperator extends EniqDBOperator {

	private static Object String;
	Logger logger = LoggerFactory.getLogger(LoadingDwhrepDbOperator.class);
	private static final List<String> dir = new ArrayList<>();
	private static final HashMap<String, String> dir3 = new HashMap<String, String>();
	private static final HashMap<String, String> dir2 = new HashMap<String, String>();

	public LoadingDwhrepDbOperator() {
		setupDWHREP();
	}

	/**
	 * Executes the Database query
	 *
	 * @param xvalue
	 * @param yvalue
	 * @param operator
	 * @return {@link CalcResponse}
	 */
	public List<String> verifyDBCommand(String query) {
		logger.info("Executing Database Query : " + query);
		TestDataSource<DataRecord> resultOutput = executeQuery(query);
		if (resultOutput.iterator().hasNext()) {
			// logger.info("Output of Query : " +
			// TestDataSourceFormatter.format(resultOutput));
			for (String output : TestDataSourceFormatter.format(resultOutput).trim().split("\\n")) {
				dir.add(output.trim());
			}
		}
		return dir;
	}

	public HashMap<String, String> verifyDBCommand2(String query) {
		logger.info("Executing Database Query : " + query);
		TestDataSource<DataRecord> resultOutput = executeQuery(query);
		if (resultOutput.iterator().hasNext()) {
			// logger.info("Output of Query : " +
			// TestDataSourceFormatter.format(resultOutput));
			for (String output : TestDataSourceFormatter.format(resultOutput).trim().split("\n")) {
				dir2.put(output.trim(), output.trim());
			}
		}
		return dir2;
	}

	public HashMap<String, String> verifyDBCommand3(String query, Map url) {
		logger.info("Executing Database Query : " + query);

		TestDataSource<DataRecord> resultOutput = executeQuery(query, url);
		if (resultOutput.iterator().hasNext()) {
			for (String output : TestDataSourceFormatter.format(resultOutput).trim().split("\n")) {
				dir3.put(null, output.trim());
			}
		}
		return dir3;
	}
}
