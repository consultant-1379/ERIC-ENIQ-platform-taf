package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.cifwk.taf.datasource.TestDataSourceFormatter;
import com.ericsson.eniq.taf.db.EniqDBOperator;
import com.google.inject.Singleton;

@Singleton
public class DWH_Operator extends EniqDBOperator {

	Logger logger = LoggerFactory.getLogger(DWH_Operator.class);
	private static final List<String> db_output = new ArrayList<>();

	private static final HashMap<String, String> dir3 = new HashMap<String, String>();

	private final String USER = DataHandler.getAttribute("platform.user.dcuser").toString();
	private final String PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
	private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();

	public DWH_Operator() {
		setupETLREP();
	}

	/**
	 * Executes the Database query
	 *
	 * @param xvalue
	 * @param yvalue
	 * @param operator
	 * @return {@link CalcResponse}
	 */
	public List<String> executeDBQuery(String query) {
		logger.info("Executing Database Query : " + query);
		TestDataSource<DataRecord> resultOutput = executeQuery(query);
		if (resultOutput.iterator().hasNext()) {
			logger.info("Query Output: " + TestDataSourceFormatter.format(resultOutput));
			for (String output : TestDataSourceFormatter.format(resultOutput).trim().split("\\n")) {
				db_output.add(output.trim());
			}
		}
		return db_output;
	}

	public HashMap<String, String> verifyDBCommand3(String query, Map url) {
		logger.info("Executing Database Query : " + query);

		TestDataSource<DataRecord> resultOutput = executeQuery(query, url);
		if (resultOutput.iterator().hasNext()) {
			for (String output : TestDataSourceFormatter.format(resultOutput).trim().split("\n")) {
				if (!output.trim().contains("*")) {
					dir3.put(url.values().toString(), output.trim());
				}
			}
		}
		return dir3;
	}
}
