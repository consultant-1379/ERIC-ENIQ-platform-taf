package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.cifwk.taf.datasource.TestDataSourceFormatter;
import com.ericsson.eniq.taf.db.EniqDBOperator;
import com.google.inject.Singleton;

@Singleton
public class InterfaceActivationDBOperator extends EniqDBOperator {

	Logger logger = LoggerFactory.getLogger(InterfaceActivationDBOperator.class);
	private static final List<String> dir = new ArrayList<>();
	private static final List<String> dir2 = new ArrayList<>();

	public InterfaceActivationDBOperator() {
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
	public List<String> verifyDBCommand(String query) {
		logger.info("Executing Database Query : " + query);
		TestDataSource<DataRecord> resultOutput = executeQuery(query);
		if (resultOutput.iterator().hasNext()) {
			// logger.info("Output of Query : " + TestDataSourceFormatter.format(resultOutput));
			for (String output : TestDataSourceFormatter.format(resultOutput).trim().split("\\n")) {
				dir.add(output.trim());
			}
		}
		return dir;
	}	
	
	
	
	public List<String> verifyDBCommand2(String query) {
		logger.info("Executing Database Query : " + query);
		TestDataSource<DataRecord> resultOutput = executeQuery(query);
		if (resultOutput.iterator().hasNext()) {
			// logger.info("Output of Query : " + TestDataSourceFormatter.format(resultOutput));
			for (String output : TestDataSourceFormatter.format(resultOutput).trim().split("\n")) {
				dir2.add(output.trim());
			}
		}
		return dir2;
	}	
}
