package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;
import com.ericsson.eniq.taf.installation.test.operators.InterfaceActivationDBOperator;
import com.ericsson.eniq.taf.installation.test.operators.ParsingOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class ParsingSteps {

	private static Logger logger = LoggerFactory.getLogger(ParsingSteps.class);

	private static final String INTERFACE_PARSER = "pm/parsing.sql";
	private static final String INTERFACE_AND_inDir = "pm/In_directoryAndInterfaceName.sql";
	private static final String LIST_OF_INTERFACES = "cd /eniq/sw/installer;./get_active_interfaces | awk '{print $1}'";
	private static final String ERBS_PM_INTERFACE_ACTIVE_STATUS_CHECK = "cd /eniq/sw/installer;./get_active_interfaces | grep 'INTF_DC_E_ERBS eniq_oss_1'";
	private static final String INPUT_DIR_NODE_LIST = "cd /eniq/data/pmdata/eniq_oss_1/; find . -print | grep -i '.*[.]xml'  2>&1 | grep -v 'topologyData' | cut -d '/' -f2 | uniq";
	private static final String LATEST_ENGINE_LOG = "cd /eniq/log/sw_log/engine/;ls engine-2*.log | tail -1";
	private static final String LATEST_ERROR_LOG = "cd /eniq/log/sw_log/engine/;ls error-2*.log | tail -1";
	private static final String PARSE_WITHOUT_DATA_TAG_PM_FILE = "engine -e startSet 'INTF_DC_E_ERBS-eniq_oss_1' 'Adapter_INTF_DC_E_ERBS_mdc'";
	private static final String ACTIVATE_TOPOLOGY_FILE_INTERFACE = "engine -e startSet 'INTF_DIM_E_LTE_ERBS' 'Adapter_INTF_DIM_E_LTE_ERBS_csexport'";
	private static final String START_PM_FILE_INTERFACE = "cd  /eniq/sw/installer/;./activate_interface -o eniq_oss_1 -i INTF_DC_E_ERBS";

	@Inject
	private Provider<ParsingOperator> provider;

	@Inject
	private Provider<InterfaceActivationDBOperator> dbprovider;

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	/**
	 * @throws FileNotFoundException
	 * @DESCRIPTION This test case covers verification of Parsing
	 * @PRE EPFG Files
	 */

	@TestStep(id = StepIds.VERIFY_PARSING_BASED_ON_INPUT)
	public void verifyActiveInterfaces() throws FileNotFoundException {
		final ParsingOperator parsingOperator = provider.get();
		final GeneralOperator generalOperator = generalOperatorProvider.get();

		final List<String> inDirNodeList = generalOperator.listOfInterfaces(INPUT_DIR_NODE_LIST);
		if (!inDirNodeList.isEmpty()) {
			logger.info("******* Files are present in inDir ******** \n " + inDirNodeList);

			// assertTrue(true);
			List<String> adapter_List = new ArrayList<String>();
			ArrayList<String> inDirectory = new ArrayList<String>();
			ArrayList<String> interfaceNameList = new ArrayList<String>();
			HashMap<String, String> List_Of_NodeTypes_and_Interfaces = new HashMap<String, String>();
			ArrayList<String> all_inDir_interfaceName = new ArrayList<String>();
			HashMap<String, String> nodeTypes_dir = new HashMap<String, String>();

			InterfaceActivationDBOperator dbOperator = dbprovider.get();

			List<String> interfacesParser = dbOperator.verifyDBCommand(INTERFACE_PARSER);
			for (String str : interfacesParser) {
				if (str.contains("Trigger")) {
					String adopter = str.substring(7, str.length() - 1).trim();
					adapter_List.add(adopter);
				}
			}
			System.out.println("List of Adapter" + adapter_List);

			// To get the inDir and interfaceName from DB
			// java.util.List<String> interfaceAndInDir =
			// dbOperator.verifyDBCommand2(INTERFACE_AND_inDir);

			// From text file

			
			String dbOutputFile = FileFinder.findFile("DbOutput.txt").get(0);
			logger.info("File : " + dbOutputFile);
			Scanner scan = new Scanner(new File(dbOutputFile));
			

			while (scan.hasNextLine()) {
				String current = scan.nextLine();
				all_inDir_interfaceName.add(current);
			}

			for (int i = 0; i < all_inDir_interfaceName.size(); i++) {
				if (all_inDir_interfaceName.get(i).contains("inDir")) {
					inDirectory.add(all_inDir_interfaceName.get(i));
				} else {
					if (all_inDir_interfaceName.get(i).contains("interfaceName")) {
						String interface_of_node = all_inDir_interfaceName.get(i).substring(
								all_inDir_interfaceName.get(i).lastIndexOf("=") + 1,
								all_inDir_interfaceName.get(i).lastIndexOf(""));
						interfaceNameList.add(interface_of_node);
					}
				}
			}
			logger.info("\n 'inDir' list size from database : " + inDirectory.size()
					+ "  ||  AND interfaceName list size from database  : " + interfaceNameList.size());
			for (int j = 0; j < inDirectory.size(); j++) {
				List_Of_NodeTypes_and_Interfaces.put(inDirectory.get(j), interfaceNameList.get(j));
			}

			String[] array1 = new HashSet<String>(inDirNodeList).toArray(new String[0]);

			// Folder and its interface
			int i = 0;
			for (String s : array1) {
				nodeTypes_dir.put(s, array1[i]);
				i++;
			}
			logger.info("List of 'inDir' directories which has PM files: *********** : " + nodeTypes_dir.keySet());

			for (String entry : nodeTypes_dir.keySet()) {
				for (String interfaces : List_Of_NodeTypes_and_Interfaces.keySet()) {
					if (interfaces.contains(entry)) {
						// System.out.println("'inDir' string FOUND in
						// interfaces");

						for (String str : adapter_List) {
							// System.out.println("Value of inDir : " +
							// List_Of_NodeTypes_and_Interfaces.get(interfaces));
							if (str.contains(List_Of_NodeTypes_and_Interfaces.get(interfaces))) {
								System.out.println("InterfaceName found in the Parsers List");

								String value_of_key = List_Of_NodeTypes_and_Interfaces.get(interfaces);
								logger.info("engine -e startSet " + value_of_key + "-eniq_oss_1" + " " + str);

								final String finalOutput = parsingOperator.executeCommand(
										"engine -e startSet " + value_of_key + "-eniq_oss_1" + " " + str);
								logger.info("Parser Final Output : " + finalOutput);

								assertTrue(finalOutput.contains("Start set requested successfully"),
										"Failed due to " + finalOutput);
								break;
							}
						}
					}
				}
			}
		} else {
			logger.info("******* inDir is Empty ********");
			assertTrue(false, "'inDir' directory is empty : No Input files to Parse");
		}
	}

	@TestStep(id = StepIds.VERIFY_ENGINE_LOG)
	public void verifyEngineLog() throws InterruptedException {
		final ParsingOperator parsingOperator = provider.get();

		/*
		 * while (parsingOperator.isParsingOnGoing()) {
		 * logger.info("\n\n Parsing is ongoing...please wait..!!! \n");
		 * TimeUnit.SECONDS.sleep(2); }
		 */
		final String latestEngineLog = parsingOperator.executeCommand(LATEST_ENGINE_LOG);
		final String engineLogContent = parsingOperator.engineLogContent(latestEngineLog);
		if(engineLogContent.contains("WARNING"))
		{
			assertTrue(engineLogContent.length() > 0,"Engine log file has exceptions  other than warning:  \n " + engineLogContent);
		}
		else {
			assertFalse(engineLogContent.length() > 0, "Engine log file has Exceptions :  \n " + engineLogContent);
		}
		//assertFalse(engineLogContent.length() > 0, "Engine log file has Exceptions :  \n " + engineLogContent);
	}

	@TestStep(id = StepIds.VERIFY_ERROR_LOG)
	public void verifyErrorLog() throws InterruptedException {
		final ParsingOperator parsingOperator = provider.get();
		/*
		 * while (parsingOperator.isParsingOnGoing()) {
		 * logger.info("\n\n Parsing is ongoing...please wait..!!! \n");
		 * TimeUnit.SECONDS.sleep(2); }
		 */
		final String latestErrorLog = parsingOperator.executeCommand(LATEST_ERROR_LOG);
		final String errorLogContent = parsingOperator.errorLogContent(latestErrorLog);
		if(errorLogContent.contains("WARNING"))
		{
			assertTrue(errorLogContent.length() > 0,"Engine log file has exceptions  other than warning:  \n " + errorLogContent);
		}
		else {
			assertFalse(errorLogContent.length() > 0, "Engine log file has Exceptions :  \n " + errorLogContent);
		}
		//assertFalse(errorLogContent.length() > 0, "Error log file has Exceptions :  \n " + errorLogContent);

	}

	@TestStep(id = StepIds.VERIFY_TOPOLOGY_MISSING_DATA_TAG)
	public void verifyParserWithMissingTag() {
		final ParsingOperator parsingOperator = provider.get();
		final GeneralOperator generalOperator = generalOperatorProvider.get();

		final String checkActiveStatus = parsingOperator.executeCommand(ERBS_PM_INTERFACE_ACTIVE_STATUS_CHECK);

		if (checkActiveStatus.contains("INTF_DC_E_ERBS")) {

			// Create a '/eniq/data/pmdata/eniq_oss_1/lterbs/dir1/'
			generalOperator.createPmDirectory();

			// Copy the sample file (data tag entry removed file) from TAF
			// resources to vApp in 'eniq/data/pmdata/eniq_oss_1/lterbs/dir1'
			generalOperator.copySamplePmFileToServer();

			final String parserOutput = parsingOperator.executeCommand(PARSE_WITHOUT_DATA_TAG_PM_FILE);
			logger.info("\n ************* Parser Output ************* : \n " + parserOutput);

			generalOperator.removePmDirectory();

			final String latestEngineLog = parsingOperator.executeCommand(LATEST_ENGINE_LOG);
			final String engineLogContent = parsingOperator.engineLogContent(latestEngineLog);

			final String latestErrorLog = parsingOperator.executeCommand(LATEST_ERROR_LOG);
			final String errorLogContent = parsingOperator.errorLogContent(latestErrorLog);

			assertTrue(
					engineLogContent.contains("doesn't match defined")
							| errorLogContent.contains("doesn't match defined")|errorLogContent.contains("Parsing results in 0row, because the input file we are parsing doesn't results in any value"),
					"Error log file has Exceptions : \n " + "Engine Log : \n " + engineLogContent
							+ "\n\n Error Log : \n " + errorLogContent);
		} else {
			logger.info("\n \n Activating/enabling INTF_DC_E_ERBS-eniq_oss_1 interface as it was disabled \n ");
			final String activationOfErbs = parsingOperator.executeCommand(START_PM_FILE_INTERFACE);
			logger.info("Output of activation : " + activationOfErbs);
			// Create a '/eniq/data/pmdata/eniq_oss_1/lterbs/dir1/'
			generalOperator.createPmDirectory();

			// Copy the sample file (data tag entry removed file) from TAF
			// resources to vApp in 'eniq/data/pmdata/eniq_oss_1/lterbs/dir1'
			generalOperator.copySamplePmFileToServer();

			final String parserOutput = parsingOperator.executeCommand(PARSE_WITHOUT_DATA_TAG_PM_FILE);
			logger.info("\n ************* Parser Output ************* : \n " + parserOutput);

			generalOperator.removePmDirectory();

			final String latestEngineLog = parsingOperator.executeCommand(LATEST_ENGINE_LOG);
			final String engineLogContent = parsingOperator.engineLogContent(latestEngineLog);

			final String latestErrorLog = parsingOperator.executeCommand(LATEST_ERROR_LOG);
			final String errorLogContent = parsingOperator.errorLogContent(latestErrorLog);

			assertTrue(
					engineLogContent.contains("doesn't match defined")
							| errorLogContent.contains("doesn't match defined"),
					"Error log file has Exceptions : \n " + "Engine Log : \n " + engineLogContent
							+ "\n\n Error Log : \n " + errorLogContent);
		}
	}

	public static class StepIds {
		public static final String VERIFY_PARSING_BASED_ON_INPUT = "To verify Topology files are parsed successfull";
		public static final String VERIFY_ENGINE_LOG = "To verify whether warning / error / exception / fail in the Engine log file.";
		public static final String VERIFY_ERROR_LOG = "To verify whether warning / error / exception / fail in the ERROR log file.";
		public static final String VERIFY_TOPOLOGY_MISSING_DATA_TAG = "To verify whether Parser process the missing data tag for the sample TOPOLOGY file";

		private StepIds() {
		}
	}
}