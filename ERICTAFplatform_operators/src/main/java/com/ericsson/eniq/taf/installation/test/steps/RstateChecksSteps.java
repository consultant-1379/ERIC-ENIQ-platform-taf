package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * Get the versions of platform modules currently installed from versiondb
 * and then check them with the hard coded mws paths to make sure that the
 * installed versions are either higher than or equal to those from mws paths.
 * 
 * @author ZJSOLEA
 */
public class RstateChecksSteps {
	private static Logger logger = LoggerFactory.getLogger(RstateChecksSteps.class);

	@Inject
	private Provider<GeneralOperator> provider;
	
	/**
	 * Compares two strings of letters
	 * 
	 * @param a
	 * @param b
	 * @return int 1 if the first string is the highest, 0 if equal, -1 if lower
	 */
	public int compareLetters(String a, String b) {
		if (a.length() > b.length())
			return 1;
		else if (a.length() < b.length())
			return -1;
		else if (a.compareTo(b) > 0)
			return 1;
		else if (a.compareTo(b) < 0)
			return -1;
		else 
			return 0;
	}
	
	/**
	 * Splits rstate into seperate parts
	 * 
	 * @param version rstate to split
	 * @return list of rstate parts
	 */
	public List<String> splitVersion(String version) {
		List<String> list = new LinkedList<String>();
		StringBuilder sb = new StringBuilder();
		boolean isNumber = false;
		
		for (int i = 0; i < version.length(); i++) {
			char c = version.charAt(i);
			
			if ((isNumber && c >= '0' && c <= '9') 
				|| (!isNumber && !(c >= '0' && c <= '9'))) {
				sb.append(c);
			} else {
				list.add(sb.toString()); // add the string to list
				sb.setLength(0); // clear the string builder
				isNumber = !isNumber;
				i--;
			}
		}
		
		// add the final part into list
		list.add(sb.toString());
		
		return list;
	}
	
	/**
	 * Compares two rstates and returns false if version 1 is not higher than version 2
	 * 
	 * @param v1
	 * @param v2
	 * @return boolean
	 */
	public boolean ensureHigherVersions(String v1, String v2) {
		List<String> ver1 = splitVersion(v1.toLowerCase());
		assertTrue(ver1.size() == 4 
					|| ver1.size() == 8 
					|| ver1.size() == 6, "Version is not in the expected format : " + v1);
		
		List<String> ver2 = splitVersion(v2.toLowerCase());
		assertTrue(ver2.size() == 4 
					|| ver2.size() == 8 
					|| ver2.size() == 6, "Version is not in the expected format : " + v2);
		
		switch (compareLetters(ver1.get(0), ver2.get(0))) {
			case 1:
				return true;
			case -1:
				logger.warn("Version " + v1 + " is not higher than " + v2 + " (" + ver1.get(0) + " < " + ver2.get(0) + ")");
				return false;
		}
		
//		
//		if (!compareLetters(ver1.get(0), ver2.get(0))) {
//			logger.warn("Version " + v1 + " is not higher than " + v2
//						+ " (" + ver1.get(0) + " < " + ver2.get(0) + ")");
//			return false;
//		}
		
		
		if (Integer.parseInt(ver1.get(1)) < Integer.parseInt(ver2.get(1))) {
			logger.warn("Version " + v1 + " is not higher than " + v2
						+ " (" + ver1.get(1) + " < " + ver2.get(1) + ")");
			return false;			
		} else if (Integer.parseInt(ver1.get(1)) > Integer.parseInt(ver2.get(1))) {
			return true;
		}
		
		switch (compareLetters(ver1.get(2), ver2.get(2))) {
			case 1:
				return true;
			case -1:
				logger.warn("Version " + v1 + " is not higher than " + v2 + " (" + ver1.get(2) + " < " + ver2.get(2) + ")");
				return false;
		}
//		if (ver1.get(2).equals(ver1.get(2))) {
//		} else if (!compareLetters(ver1.get(2), ver2.get(2))) {
//			logger.warn("Version " + v1 + " is not higher than " + v2
//						+ " (" + ver1.get(2) + " < " + ver2.get(2) + ")");
//			return false;
//		} else {
//			return true;
//		}
		
		if (Integer.parseInt(ver1.get(3)) < Integer.parseInt(ver2.get(3))) {
			logger.warn("Version " + v1 + " is not higher than " + v2 
						+ " (" + ver1.get(3) + " < " + ver2.get(3) + ")");
			return false;			
		} else if (Integer.parseInt(ver1.get(3)) > Integer.parseInt(ver2.get(3))) {
			return true;
		}
		
		if (ver2.size() == 4) 
			return true;
		if (ver1.size() == 4) 
			return false;
		
		// compare ec versions
		String ec1 = ((ver1.get(4).equals("_ec")) ? ver1.get(5) : "");
		String ec2 = ((ver2.get(4).equals("_ec")) ? ver2.get(5) : "");
		if (ec2.isEmpty()) 
			return true;
		if (ec1.isEmpty())
			return false;
		if (Integer.parseInt(ec1) < Integer.parseInt(ec2))
			return false;
	
		// compare build numbers
		String b1 = "";
		if (!ver1.get(ver1.size() - 2).equals("_ec"))
			b1 = ver1.get(ver1.size() - 1);

		String b2 = "";
		if (!ver2.get(ver2.size() - 2).equals("_ec"))
			b2 = ver2.get(ver2.size() - 1);
		
		if (b2.isEmpty()) 
			return true;
		if (b1.isEmpty()) 
			return false;
		if (Integer.parseInt(b1) < Integer.parseInt(b2)) 
			return false;
		
		return true;
	}
	
	/**
	 * Get the platform modules and versions from versiondb.properties
	 * 
	 * @return
	 */
	public Map<String, String> getInstalledVersions() {
		final GeneralOperator operator = provider.get();
		
		Map<String, String> versiondbMap = new HashMap<String, String>();
		String[] versiondb = operator.executeCommandDcuser("cat /eniq/sw/installer/versiondb.properties")
									 .split("\n");
		
		for (String versionEntry: versiondb) {
			versionEntry = versionEntry.toLowerCase().trim();
			if (!versionEntry.startsWith("module."))
				continue;
			
			String key = versionEntry.substring(versionEntry.indexOf('.') + 1, versionEntry.indexOf('='));
			String value = versionEntry.substring(versionEntry.indexOf('=') + 1);
			
			versiondbMap.put(key, value);
		}
		
		return versiondbMap;
	}
	
	/**
	 * get platform modules and their versions from the given MWS path
	 * 
	 * @param mwsPath
	 * @return
	 */
	public Map<String, String> getMwsVersions(String mwsPath) {
		final GeneralOperator operator = provider.get();
		
		String[] packageList = operator.executeCommandDcuser("ls " + mwsPath + " | cat")
									   .split("\n");
		
		Map<String, String> packageMap = new HashMap<String, String>();
		for (String s: packageList) {
			s = s.toLowerCase().trim();
			if (!s.endsWith(".zip"))
				continue;

			s = s.substring(0, s.lastIndexOf('.')).toLowerCase();
			packageMap.put(s.substring(0, s.lastIndexOf("_r")), s.substring(s.lastIndexOf("_r") + 1));
		}
		
		return packageMap;
	}
	
	/**
	 * Checks whether all the packages in the second map are less than or equal to that in the first
	 * 
	 * @param higher
	 * @param lower
	 * @return
	 */
	public List<String> compareVersionMaps(Map<String, String> higher, Map<String, String> lower) {
		List<String> errorPackages = new LinkedList<String>();
		
		for (String key: lower.keySet()) {
			if (!higher.containsKey(key)) {
				continue;
			}
			if (!ensureHigherVersions(higher.get(key), lower.get(key))) {
				logger.warn("Expected higher version for package " + key + " " 
							+ higher.get(key) + " < " + lower.get(key));
				errorPackages.add(key);
			}
		}
		
		return errorPackages;
	}
	
	/**
	 * @DESCRIPTION Rstate checks
	 */
	@TestStep(id = StepIds.RSTATE_CHECKS_STEP)
	public void verify() {
		// get operators from providers
		final GeneralOperator operator = provider.get();
		
		// get current mws path
		String MWS_PARSER_PATH = DataHandler.getAttribute("platform.parser.mws.path").toString();
		String MWS_FEATURE_PATH = MWS_PARSER_PATH.substring(0, MWS_PARSER_PATH.lastIndexOf('/'));
		logger.info("Mws feature path from properties : " + MWS_FEATURE_PATH);
		
		String shipment = operator.executeCommandDcuser("cat /eniq/admin/version/eniq_status | grep Shipment |cut -d \" \" -f2 | cut -d '_' -f4-");
		String MWS_PATH = MWS_FEATURE_PATH.substring(0, MWS_FEATURE_PATH.lastIndexOf('/')) + "/" + shipment + "/";
		logger.info("MWS path : " + MWS_PATH);

		String[] mwsPaths = {
				//"/net/10.45.192.134/JUMP/ENIQ_STATS/ENIQ_STATS/18.2.8.EU16/eniq_base_sw/eniq_sw/",
				//"/net/10.45.192.153/JUMP/ENIQ_STATS/ENIQ_STATS/19.4.9.EU4_Linux/eniq_base_sw/eniq_sw/",
				//"/net/10.45.192.153/JUMP/ENIQ_STATS/ENIQ_STATS/19.2.13.EU9_Linux/eniq_base_sw/eniq_sw/",
				//"/net/10.45.192.153/JUMP/ENIQ_STATS/ENIQ_STATS/20.2.4_Linux/eniq_base_sw/eniq_sw/",
				//"/net/10.45.192.134/JUMP/ENIQ_STATS/ENIQ_STATS/Features_18B_18.2.8.EU9-1/eniq_parsers",
				MWS_PARSER_PATH,
				MWS_FEATURE_PATH,
		};

		// get Installed versions
		Map<String, String> versiondbMap = getInstalledVersions();
		logger.info("VERSIONDB versions : " + versiondbMap.toString());
		
		for (String mwsPath: mwsPaths) {
			logger.info("Comparing installed versions with the ones from " + mwsPath);
			
			// get mws versions
			Map<String, String> mwsMap = getMwsVersions(mwsPath);		
			logger.info("MWS versions ( " + mwsPath + " ) : " + mwsMap.toString());
			
			// compare
			List<String> errorPackages = compareVersionMaps(versiondbMap, mwsMap);
			
			// lst all the packages with lesser versions
			logger.info("LIST OF PACKAGES WITH LESSER VERSION THAN IN MWS PATH ("+ errorPackages.size() +"): ");
			for (String s: errorPackages) {
				logger.info("PACKAGE : " + s + " - " + versiondbMap.get(s) + " - " + mwsMap.get(s));
			}
			
			assertTrue(errorPackages.size() == 0, "Versions are lower for the below packages in (" 
						+ mwsPath + ") : \n" + errorPackages.toString());
		}
		
		return;
	}

	public static class StepIds {
		public static final String RSTATE_CHECKS_STEP = "rstate checks";

		private StepIds() {
		}
	}
}