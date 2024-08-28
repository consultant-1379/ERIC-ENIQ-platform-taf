package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.DwhdbOperator;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.LoadingOperator;
import com.ericsson.eniq.taf.installation.test.operators.RepdbOperator;

/**
 * @author ZJSOLEA
 */
public class VerifyLoadingCountersSteps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);
	
	private static final String PM_SERVER_PATH = "/eniq/data/pmdata/eniq_oss_1/lterbs/dir1";
	private static final String SAMPLE_PM_FILE_NAME = "A20190411.1000+0100-1015+0100_SubNetwork=ONRM_ROOT_MO,SubNetwork=ERBS-SUBNW-1,MeContext=ERBS1_statsfile.xml";
	//private static final String SAMPLE_PM_FILE_NAME = "A20210127.0815+0100-0830+0100_SubNetwork=ONRM_ROOT_MO,SubNetwork=ERBS-SUBNW-1,MeContext=ERBS1_statsfile.xml";
	private static final String GET_DEFAULT_TAGS = "pm/pmloadingtags.sql";
	private static final String VERIFY_TABLE_LOADED_WITH_MOID = "pm/pmloadingverify.sql";
	private static final String FIND_INPUT_XML = "ls -rt /eniq/data/pmdata/eniq_oss_1/lterbs/dir1 | grep -i '.xml' | tail -n 1";
	private static final String GET_TAGIDS_AND_MOIDS = "cd /eniq/data/pmdata/eniq_oss_1/lterbs/dir1; grep -i  '\\''<r\\|<mt>\\|<moid'\\'' " + SAMPLE_PM_FILE_NAME + " | sed '\\''s/<\\/.*//'\\''";
	private static final String REMOVE_INPUT_FILE_FROM_SERVER = "cd " + PM_SERVER_PATH + "; rm -f " + SAMPLE_PM_FILE_NAME;
	private static final String EXECUTE_ENGINE_COMMAND = "cd /eniq/data/pmdata/eniq_oss_1/lterbs/dir1;engine -e startAndWaitSet \'INTF_DC_E_ERBS-eniq_oss_1\' \'Adapter_INTF_DC_E_ERBS_mdc\'";
	private static final String CHANGE_FILE_PERMISSIONS = "echo shroot12 | su - root -c \"chown -R dcuser:dc5000 /eniq/data/pmdata/eniq_oss_1/lterbs; chown dcuser:dc5000 /eniq/data/pmdata/eniq_oss_1/lterbs/dir1/*;\"";

	private static final String GET_TABLE_NAME_FROM_TAG = "pm/gettablenamefromtag.sql";
	private static final String GET_COLUMN_FROM_COUNTER = "pm/getcolumnfromcounter.sql";
	private static final String GET_VALUE_FROM_DB = "pm/getvaluefromdb.sql";
	private static final String GET_COLUMN_FROM_KEY = "pm/getcolumnfromkey.sql";
	
	@Inject
	private Provider<LoadingOperator> provider;

	@Inject
	private Provider<DwhdbOperator> dwhdbprovider;
	
	@Inject
	private Provider<RepdbOperator> repdbprovider;

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;
	
	/**
	 * Gets tablename from the dataformatid from the DefaultTags table
	 * 
	 * @param dataFormatId
	 * @return table name constructed by appending _RAW to 3rd part in dataformatid
	 */
	private String extractTableName(String dataFormatId) {
		String tableName = dataFormatId.split(":")[2] + "_RAW";
		return tableName;
	}
	
	/**
	 * Extract the tagid from measObjLdn value
	 * 
	 * @param measObjLdn 
	 * @return returns the tagid
	 */
	private String extractTagId(String measObjLdn) {
		String[] parts = measObjLdn.split(",");
		String tagid = parts[parts.length - 1].split("=")[0];
		return tagid;
	}
	
	/**
	 * Verify the data in db with the expected values
	 * 
	 * @param table
	 * @param column
	 * @param expectedValues
	 * @param moId
	 * @return true if the values match.
	 */
	public boolean verifyCounters(String table, String column, String[] expectedValues, String moId) {
		List<String> actualCounterValues = null;
		try {
			actualCounterValues = getValueFromDb(table, column, moId);
		} catch(Exception e) {
			return false;
		}
		
		//assertTrue(expectedCounterValues.length > actualCounterValues.size(), "All counter values are not loaded for counter : " + counterName);
		if (expectedValues.length > actualCounterValues.size()) {
			return false;
		}
		
		//assertTrue((new HashSet<String>(actualCounterValues)).containsAll(Arrays.asList(expectedCounterValues)), "The counter values are incorrect for the counter " + counterName + "\nEXPECTED: "+ Arrays.asList(expectedCounterValues) +"\nACTUAL:" + actualCounterValues);
		if (compareLists(Arrays.asList(expectedValues), actualCounterValues)) {
			return false;
		}	
		
		return true;
	}
	
	/**
	 * @throws InterruptedException 
	 * @DESCRIPTION This test case covers verification of loading.
	 * @PRE EPFG Files
	 */
	@TestStep(id = StepIds.VERIFY_LOADING_COUNTERS)
	public void verifyPmLoading() throws InterruptedException {
		// get operators from providers
		final GeneralOperator operator = generalOperatorProvider.get();
		final LoadingOperator loadingOperator = provider.get();
		final RepdbOperator repdbOperator = repdbprovider.get();
		
		/* DB and service checks to check and start engine and dbs. */
		
		if (operator.executeCommandDcuser("scheduler status").contains("not running"))
			operator.executeCommandDcuser("scheduler start");
		if (operator.executeCommandDcuser("repdb status").contains("is not running"))
			operator.executeCommandDcuser("repdb start");
		if (operator.executeCommandDcuser("dwhdb status").contains("not running"))
			operator.executeCommandDcuser("dwhdb start");
		if (operator.executeCommandDcuser("engine status").contains("not running"))
			operator.executeCommandDcuser("engine start");
		
		/* the above block can be removed if not needed.*/

		loadingOperator.createDirectory(PM_SERVER_PATH);
		loadingOperator.copyFileToServer(SAMPLE_PM_FILE_NAME, PM_SERVER_PATH);

		// Find yesterdays date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		Date yesterday = cal.getTime();
		
		String NEW_FILENAME = SAMPLE_PM_FILE_NAME.replace("20190411", (new SimpleDateFormat("YYYYMMdd")).format(yesterday));
		String NEW_DATE = (new SimpleDateFormat("YYYY-MM-dd")).format(yesterday);
		
		logger.info("Changing date from to " + NEW_DATE + " in the input xml file");
		String output = operator.executeCommand("cd /eniq/data/pmdata/eniq_oss_1/lterbs/dir1; sed -i 's/2019-04-11/" + NEW_DATE + "/g' *.xml");
		assertTrue(output.isEmpty(), "Error while changing date in input file name");
		

		logger.info("Changing filename from '" + SAMPLE_PM_FILE_NAME + "' to '" + NEW_FILENAME);
		output = operator.executeCommand("cd /eniq/data/pmdata/eniq_oss_1/lterbs/dir1; mv \"" + SAMPLE_PM_FILE_NAME + "\" \"" + NEW_FILENAME + "\"");
		assertTrue(output.isEmpty(), "Error while changing input file name");

		logger.info("Changing file permissions to dcuser");
		output = operator.executeCommand("chown -R dcuser:dc5000 /eniq/data/pmdata/eniq_oss_1/lterbs/; chown -R dcuser:dc5000 /eniq/data/pmdata/eniq_oss_1/lterbs/dir1/*");
		logger.info("Output is '"+output+"'");
		
		//loadingOperator.executeCommand(CHANGE_FILE_PERMISSIONS);
		loadingOperator.executeCommand(EXECUTE_ENGINE_COMMAND);
		loadingOperator.copyFileToServer(SAMPLE_PM_FILE_NAME, PM_SERVER_PATH);
		
		// Find the input xml file
		String[] xmlInputFiles = loadingOperator.executeCommand(FIND_INPUT_XML).split("\n");
		assertTrue(xmlInputFiles.length == 1, "XML input file not found");
		assertTrue(xmlInputFiles[0].length() > 0, "XML input file not found");
		
		
		Map<String, Map<String, String>> moMap = new HashMap<String, Map<String, String>>();

		// Retrieve all counter values from xml file into a hashMap
		String moid = null;
		List<String> counterNames = new LinkedList<String>();
		List<String> counterValues = new LinkedList<String>();
		for (String line: loadingOperator.executeCommand(GET_TAGIDS_AND_MOIDS).split("\n")) {
			String[] words = line.split(">");
			assertEquals(words.length, 2, "Error parsing xml file line : " + line);
			
			if (line.startsWith("<mt")) {
				counterNames.add(words[1]);
			} else if (line.startsWith("<moid")) {
				moid = words[1];
			} else if (line.startsWith("<r")) {
				counterValues.add(words[1]);
				if (counterNames.size() == counterValues.size()) {
					//String tagid = extractTagId(moid);
					Map<String, String> counterMap = new HashMap<String, String>();
					
					for (int i = 0; i < counterNames.size(); i++) {
						counterMap.put(counterNames.get(i).trim(), counterValues.get(i).trim());
					}
					
					moMap.put(moid.trim(), counterMap);
					
					counterNames.clear();
					counterValues.clear();
					moid = null;
				}
			}
		}

		Thread.sleep(10000);
		
		// Verify the counter values from DB
		long tagCount = 0, counterCount = 0;
		long verifiedTags = 0, verifiedCounters = 0;
		List<String> failedCounters = new LinkedList<String>();
		for (String moId: moMap.keySet()) {
			tagCount++;
			
			Map<String, String> counterMap = moMap.get(moId);
			
			counterCount = 0;
			verifiedCounters = 0;
			for (String counterName: counterMap.keySet()) {
				counterCount++;
				
				String[][] results = getColumnNameFromCounterName(counterName);

				if (results == null || results.length < 1) {
					results = getColumnNameFromKeyName(counterName);
				}
				
				if (results == null || results.length < 1) {
					logger.info("Failed to find table and column name for counter " + counterName);
					continue;
				}
				
				boolean verified = false;
				for (String[] row: results) {
					
					String tableName = row[1].split(":")[2] + "_RAW";
					String columnName = row[0];
					String[] expectedCounterValues = counterMap.get(counterName).split(",");
					
					if (verifyCounters(tableName, columnName, expectedCounterValues, moId)) {
						verified = true;
						break;
					}
				}
				
				if (!verified) {
					logger.info("Failed to Verify counter " + counterName + " for MO " + moId);
					failedCounters.add(counterName + " " + moId);
				} else {
					logger.info("Successfully Verified counter " + counterName + " for MO " + moId);
					verifiedCounters++;
				}
			}
			
			if (verifiedCounters != counterCount)
				logger.info("Failed to verify all counters for mo " + moId + " : " + verifiedCounters + "/" + counterCount);
			else
				logger.info("Successfully verified all counters for mo " + moId + " : " + verifiedCounters + "/" + counterCount);
			
			verifiedTags++;
		}
		
		assertEquals(failedCounters.size(), 0, "Failed to verify " + failedCounters.size() + " counters.\n" + failedCounters);
		logger.info("Successfully verified all counter values for " + verifiedTags + " MOs");
	}
	
	/**
	 * Comapres the corresponding elements in two lists. 
	 * If lists have different size, then the first n values will be checked where n is the length of the smallest list.
	 * 
	 * @param asList
	 * @param actualCounterValues
	 * @return true if the lists match
	 */
	private boolean compareLists(List<String> asList, List<String> actualCounterValues) {
		int size = asList.size();
		if (actualCounterValues.size() < size) size = actualCounterValues.size();
		for (int i = 0;i<size;i++)
			if (asList.get(i) != actualCounterValues.get(i))
				return false;
		return true;
	}

	/**
	 * Extracts values from db
	 * 
	 * @param table
	 * @param column
	 * @param moId
	 * @return
	 */
	List<String> getValueFromDb(String table, String column, String moId) {
		final DwhdbOperator dwhdbOperator = dwhdbprovider.get();
		
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("table", table);
		valuesMap.put("column", column);
		valuesMap.put("moid", moId);
		
		List<Object[]> results = dwhdbOperator.ExecuteQueryFile(GET_VALUE_FROM_DB, valuesMap);
		if (results.size() == 0)
			return new LinkedList<String>();
		
		List<String> resultList = new LinkedList<String>();
		for (Object[] obj: results) {
			if (obj != null && obj.length >= 1 && obj[0] != null) {
				resultList.add(obj[0].toString());
			}
		}
		return resultList;	
	}
	
	/**
	 * finds the typeid and column for a counter. Tablename can be extracted from typeid.
	 * 
	 * @param counterName
	 * @return 2d array of all possible typeid and column combinations
	 */
	String[][] getColumnNameFromCounterName(String counterName) {
		final RepdbOperator repdbOperator = repdbprovider.get();
		
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("counter", counterName);
		
		List<Object[]> results = repdbOperator.ExecuteQueryFile(GET_COLUMN_FROM_COUNTER, valuesMap);
		if (results.size() == 0)
			return null;

		String[][] ret = new String[results.size()][2];
		for(int i = 0; i < results.size(); i++) {
			ret[i][0] = results.get(i)[0].toString();
			ret[i][1] = results.get(i)[1].toString();
		}

		return ret;
	}
	
	/**
	 * finds the typeid and column for a key. Tablename can be extracted from typeid.
	 * 
	 * @param counterName
	 * @return 2d array of all possible typeid and column combinations
	 */
	String[][] getColumnNameFromKeyName(String counterName) {
		final RepdbOperator repdbOperator = repdbprovider.get();
		
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("key", counterName);
		
		List<Object[]> results = repdbOperator.ExecuteQueryFile(GET_COLUMN_FROM_KEY, valuesMap);
		if (results.size() == 0)
			return null;

		String[][] ret = new String[results.size()][2];
		for(int i = 0; i < results.size(); i++) {
			ret[i][0] = results.get(i)[0].toString();
			ret[i][1] = results.get(i)[1].toString();
		}

		return ret;
	}

	public static class StepIds {
		public static final String VERIFY_LOADING_COUNTERS = "To verify PM loading for all tags in input xml file";

		private StepIds() {
		}
	}
}