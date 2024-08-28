package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.lang3.StringUtils;
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
public class VerifyPmLoadingTagsSteps {

	private static Logger logger = LoggerFactory.getLogger(VerifyPmLoadingTagsSteps.class);
	
	private static final String PM_SERVER_PATH = "/eniq/data/pmdata/eniq_oss_1/dsc/dir1";
	private static final String SAMPLE_PM_FILE_NAME = "A20190321.1015+0100-1030+0000_DSC01.xml";

	private static final String GET_DEFAULT_TAGS = "pm/pmloadingtags.sql";
	private static final String VERIFY_TABLE_LOADED_WITH_MOID = "pm/pmloadingverify.sql";
	private static final String FIND_INPUT_XML = "ls -rt /eniq/data/pmdata/eniq_oss_1/dsc/dir1 | grep -i '.xml' | tail -n 1";
	private static final String GET_TAGIDS_AND_MOIDS = "cd /eniq/data/pmdata/eniq_oss_1/dsc/dir1/; grep -i  '\\''measInfo measInfoId\\|measValue measObjLdn'\\'' *.1015+0100-1030+0000_DSC01.xml | sed '\\''s/.*<//'\\'' | sed '\\''s/>.*//'\\''";
	private static final String REMOVE_INPUT_FILE_FROM_SERVER = "cd " + PM_SERVER_PATH + "; rm -f *.xml";
	private static final String EXECUTE_ENGINE_COMMAND = "cd /eniq/data/pmdata/eniq_oss_1/dsc/dir1;engine -e startAndWaitSet \'INTF_DC_E_DSC-eniq_oss_1\' \'Adapter_INTF_DC_E_DSC_3gpp32435\'";

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
	 * @throws InterruptedException 
	 * @DESCRIPTION This test case covers verification of loading.
	 * @PRE EPFG Files
	 */
	@TestStep(id = StepIds.VERIFY_PM_LOADING_TAGS)
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
		
		
		
		// Stores the tablename for tagids with tagid as key.
		Map<String, String> tagToTableMap = new HashMap<String, String>();
		
		// stores the moids under a tagid with tagid as key.
		Map<String, List<String>> tags = new HashMap<String, List<String>>();

		// Get all tags and corresponding tablenames from the DefaultTags Table.
		List<Object[]> queryResults = repdbOperator.ExecuteQueryFile(GET_DEFAULT_TAGS);
		for (Object[] queryResult : queryResults) {
			String tag = queryResult[0].toString();
			String tablename = extractTableName(queryResult[1].toString());
			
			// Remove unwanted trailing characters = , and spaces
			while ("=, ".indexOf(tag.charAt(tag.length() - 1)) != -1)
				tag = StringUtils.chop(tag);
			
			// Trim unwanted starting characters
			while (tag.charAt(0) == ',')
				tag = tag.substring(1);
			
			// Add the tag to the map
			tagToTableMap.put(tag, tablename);
		}

		// create input dir if it does not exist
		logger.info("Checking if the server path exists and create it if it does not exist");
		loadingOperator.createDirectory(PM_SERVER_PATH);
		
		logger.info("Copying the nput xml file to the server");
		loadingOperator.copyFileToServer(SAMPLE_PM_FILE_NAME, PM_SERVER_PATH);
		
		// Find yesterdays date
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		Date yesterday = cal.getTime();
		
		String NEW_FILENAME = SAMPLE_PM_FILE_NAME.replace("20190321", (new SimpleDateFormat("YYYYMMdd")).format(yesterday));
		String NEW_DATE = (new SimpleDateFormat("YYYY-MM-dd")).format(yesterday);
		
		logger.info("Changing date from to " + NEW_DATE + " in the input xml file");
		String output = operator.executeCommand("cd /eniq/data/pmdata/eniq_oss_1/dsc/dir1; sed -i 's/2019-03-21/" + NEW_DATE + "/g' *.xml");
		assertTrue(output.isEmpty(), "Error while changing date in input file name");
		

		logger.info("Changing filename from '" + SAMPLE_PM_FILE_NAME + "' to '" + NEW_FILENAME);
		output = operator.executeCommand("cd /eniq/data/pmdata/eniq_oss_1/dsc/dir1; mv \"" + SAMPLE_PM_FILE_NAME + "\" \"" + NEW_FILENAME + "\"");
		assertTrue(output.isEmpty(), "Error while changing input file name");

		logger.info("Changing file permissions to dcuser");
		output = operator.executeCommand("chown -R dcuser:dc5000 /eniq/data/pmdata/eniq_oss_1/dsc; chown -R dcuser:dc5000 /eniq/data/pmdata/eniq_oss_1/dsc/*");
		logger.info("Output is '"+output+"'");
		
		logger.info("Executing engine command");
		loadingOperator.executeCommand(EXECUTE_ENGINE_COMMAND);
		loadingOperator.copyFileToServer(SAMPLE_PM_FILE_NAME, PM_SERVER_PATH);
		
		// Find the input xml file
		logger.info("Finding input xml file...");
		String[] xmlInputFiles = loadingOperator.executeCommand(FIND_INPUT_XML).split("\n");
		assertTrue(xmlInputFiles.length == 1, "XML input file not found");
		assertTrue(xmlInputFiles[0].length() > 0, "XML input file not found");
		
		// Get tagids and corresponding MOIDs from xml file
		logger.info("Finding tagids and corresponding MOIDs from xml file");
		String lastMeasInfo = null;
		for (String line: loadingOperator.executeCommand(GET_TAGIDS_AND_MOIDS).split("\n")) {
			String[] words = line.split(" ");
			
			// Extract tagid from measinfo element
			if (words[0].equalsIgnoreCase("measInfo")) {
				String measInfo = words[1].substring(words[1].indexOf('"') + 1, words[1].lastIndexOf('"'));
				lastMeasInfo = measInfo;
				assertFalse(tags.containsKey(measInfo), "Duplicate tagid found - " + line);
				tags.put(measInfo, new ArrayList<String>());
			}
			// extract MOIDs from measValue element
			else if (words[0].equalsIgnoreCase("measValue")) {
				String measObjLdn = words[1].substring(words[1].indexOf('"') + 1, words[1].lastIndexOf('"'));
				assertTrue(lastMeasInfo != null, "Unable to find the enclosing measInfo tag for measvalue tag - " + line);
				tags.get(lastMeasInfo).add(measObjLdn);
			}
		}
		
		// Log the generated value to console
		String result = "";
		for (Map.Entry mapElement: tags.entrySet()) {
			String key = (String) mapElement.getKey();
			List<String> value = (List<String>) mapElement.getValue();
			result = result + key + " : ";
			for (String item: value) {
				result = result + item + " , ";
			}		
			result = result + "\n";
		}
		logger.info("Generated map of tagid and moid :\n" + result);
		
		Thread.sleep(10000);
		
		// Verify whether data is loaded for all the tagids from xml file
		Thread.sleep(5000);
		logger.info("Verify whether data is loaded for all the tagids from xml file");
		final DwhdbOperator dwhdbOperator = dwhdbprovider.get();
		Set<String> nonLoadedTables = new HashSet<String>();
		int tagidCount = 0;
		int moidCount = 0;
		for (String tagid: tags.keySet()) {
			tagidCount++;
			String tableName = tagToTableMap.get(tagid);
			assertTrue(tableName != null, "Tablename not found for tagid " + tagid);
			
			for (String moid: tags.get(tagid)) {
				moidCount++;
				Map<String, String> valuesMap = new HashMap<String, String>();
				valuesMap.put("tablename", tableName);
				valuesMap.put("moid", moid);
				
				List<Object[]> results = dwhdbOperator.ExecuteQueryFile(VERIFY_TABLE_LOADED_WITH_MOID, valuesMap);
				
				if (results.size() == 0) {
					nonLoadedTables.add(tagid);
				}
			}
		}
		
		logger.info("Removing xml file from server");
		loadingOperator.executeCommand(REMOVE_INPUT_FILE_FROM_SERVER);
		
		logger.info("Processed " + tagidCount + " TagIDs and " + moidCount + " MOIDs");
		logger.info("Number of tables loaded successfully : " + tagidCount);
		
		// Check whether there are tables which are not loaded
		assertEquals(nonLoadedTables.size(), 0, "" + nonLoadedTables.size() + " tag ids are not loaded :\n" + nonLoadedTables);	
		
	}

	public static class StepIds {
		public static final String VERIFY_PM_LOADING_TAGS = "To verify PM loading for all tags in input xml file";

		private StepIds() {
		}
	}
}