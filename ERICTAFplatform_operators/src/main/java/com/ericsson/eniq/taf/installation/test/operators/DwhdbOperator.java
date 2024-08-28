package com.ericsson.eniq.taf.installation.test.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.eniq.taf.db.EniqDBOperator;
import com.google.inject.Singleton;

@Singleton
public class DwhdbOperator extends EniqDBOperator {
	Logger logger = LoggerFactory.getLogger(RepdbOperator.class);

	public DwhdbOperator() {
		logger.info("Setting up Repdb ");
		setupDWHDB();
	}
	
	public List<Object[]> ExecuteQueryFile(String queryfile) {
		return ExecuteQueryFile(queryfile, new HashMap<String, String>());
	}

	/**
	 * Executes the Database query
	 *
	 * @param queryfile
	 *         the sql query file to execute
	 * @return result of query as List of Object arrays(for each row)
	 */
	public List<Object[]> ExecuteQueryFile(String queryfile, Map<String, String> sqlQueryParameters) {
		List<Object[]> resultList = new ArrayList<Object[]>();
		
		logger.info("Executing Database Query File : " + queryfile);

		// Execute the query and get result
		TestDataSource<DataRecord> resultOutput = executeQuery(queryfile, sqlQueryParameters);
		
		// Store the results in a list
		if (resultOutput.iterator().hasNext()) {
			for (DataRecord record: resultOutput) {
				resultList.add(record.getAllFields().values().toArray());
				logger.info("RESULT : " + record.getAllFields());
			}
		}
		
		return resultList;
	}		
}
