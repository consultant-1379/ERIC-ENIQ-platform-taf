package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.GeneralOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class SecurityFeaturesSteps {

	private static Logger logger = LoggerFactory.getLogger(SecurityFeaturesSteps.class);
	private final String RELEASE = DataHandler.getAttribute("platform.release.version").toString();
	private final String CD = "cd";
	private final String SPACE = " ";
	private final String SEMI_CO = ";";

	@Inject
	private Provider<GeneralOperator> generalOperatorProvider;

	@TestStep(id = StepIds.SECURITY_FEATURES)
	public void closeFirefoxProcess() throws InterruptedException {
		final GeneralOperator generalOperator = generalOperatorProvider.get();

		logger.info("\nChecking Security Features for : Session-Config\n");
		String sessionConfigOutput = generalOperator.executeCommand(
				"cat /eniq/sw/runtime/tomcat/conf/web.xml | grep '<cookie-config>\\|<secure>true</secure>\\|</cookie-config>'");
		assertTrue(sessionConfigOutput.contains("<secure>true</secure>"),
				"Exepected : '<secure>true</secure>' but found : " + sessionConfigOutput);

		logger.info("\nChecking Security Features for : Autocomplete\n");
		String autocompleteOutput1 = generalOperator
				.executeCommand("cat /eniq/sw/runtime/tomcat/webapps/adminui/login.jsp | grep 'autocomplete=\"off\"'");
		assertTrue(autocompleteOutput1.contains("autocomplete=\"off\""),
				"Exepected : 'autocomplete=\"off\"' but found : " + autocompleteOutput1);

		String autocompleteOutput2 = generalOperator.executeCommand(
				"cat /eniq/sw/runtime/tomcat/webapps/adminui/WEB-INF/templates/feature_availability.vm | grep 'autocomplete=\"off\"'");
		assertTrue(autocompleteOutput2.contains("autocomplete=\"off\""),
				"Exepected : 'autocomplete=\"off\"' but found : " + autocompleteOutput2);

		String autocompleteOutput3 = generalOperator.executeCommand(
				"cat /eniq/sw/runtime/tomcat/webapps/adminui/WEB-INF/templates/feature_availability_install.vm | grep 'autocomplete=\"off\"'");
		assertTrue(autocompleteOutput3.contains("autocomplete=\"off\""),
				"Exepected : 'autocomplete=\"off\"' but found : " + autocompleteOutput3);

		String autocompleteOutput4 = generalOperator.executeCommand(
				"cat /eniq/sw/runtime/tomcat/webapps/adminui/WEB-INF/templates/feature_availability_update.vm | grep 'autocomplete=\"off\"'");
		assertTrue(autocompleteOutput4.contains("autocomplete=\"off\""),
				"Exepected : 'autocomplete=\"off\"' but found : " + autocompleteOutput4);

		logger.info("\nChecking Security Features for : X-FRAME-OPTIONS (antiClickJacking)\n");
		String antiClickJacking1 = generalOperator.executeCommand(
				"sed -n '/<filter/,/<\\/filter/p' /eniq/sw/runtime/tomcat/webapps/adminui/WEB-INF/web.xml");
		assertTrue(antiClickJacking1.contains("antiClickJackingOption"),
				"Exepected : 'antiClickJackingOption' but found : " + antiClickJacking1);

		logger.info("\nChecking Security Features for : X-FRAME-OPTIONS (antiClickJacking)\n");
		String antiClickJacking2 = generalOperator.executeCommand(
				"sed -n '/<filter-mapping/,/<\\/filter-mapping/p' /eniq/sw/runtime/tomcat/webapps/adminui/WEB-INF/web.xml");
		assertTrue(antiClickJacking2.contains("httpHeaderSecurity"),
				"Exepected : 'antiClickJackingOption' but found : " + antiClickJacking2);

		logger.info("\nChecking Security Features for : Strict Transport Security (HSTS)\n");
		String hstsOutput = generalOperator
				.executeCommand("sed -n '/<init-param>/,/<\\/init-param>/p' /eniq/sw/runtime/tomcat/conf/web.xml");
		assertTrue(hstsOutput.contains("hstsEnabled") && hstsOutput.contains("<param-value>true</param-value>"),
				"Exepected : 'hstsEnabled' &  '<param-value>true</param-value>' but found : " + hstsOutput);

		logger.info("\nChecking Security Features for : SHUTDOWN port \n");
		String shutdownPortOutput = generalOperator.executeCommand(
				"cat /eniq/sw/runtime/tomcat/conf/server.xml | grep '<Server port=\"-1\" command=\"SHUTDOWN\">'");
		assertTrue(shutdownPortOutput.contains("<Server port=\"-1\" command=\"SHUTDOWN\">"),
				"Exepected : '<Server port=\"-1\" command=\"SHUTDOWN\">' but found : " + shutdownPortOutput);

		logger.info("\nChecking Security Features for : Automatic deployment\n");
		String autoDeployment = generalOperator.executeCommand(
				"sed -n '/<Host/,/<\\/Host>/p' /eniq/sw/runtime/tomcat/conf/server.xml | grep 'autoDeploy=\"false\"'");
		assertTrue(autoDeployment.contains("autoDeploy=\"false\""),
				"Exepected : 'autoDeploy=\"false\"' but found : " + autoDeployment);

		logger.info("\nChecking Security Features for : Tomcat lockout\n");
		String TomcatLockoutOutput = generalOperator.executeCommand(
				"sed -n '/<Realm/,/<\\/Realm>/p' /eniq/sw/runtime/tomcat/conf/server.xml | grep 'failureCount=\"3\" lockOutTime=\"3600\"'");
		assertTrue(TomcatLockoutOutput.contains("failureCount=\"3\" lockOutTime=\"3600\""),
				"Exepected : 'failureCount=\"3\" lockOutTime=\"3600\"' but found : " + TomcatLockoutOutput);

		logger.info("\nChecking Security Features for : Cipher properties \n");
		String CipherOutput = generalOperator.executeCommand(
				"sed -n '/<Connector/,/<\\/Connector>/p' /eniq/sw/runtime/tomcat/conf/server.xml | grep  'SSLCipherSuite=\"HIGH:!SHA\"'");
		assertTrue(CipherOutput.contains("SSLCipherSuite=\"HIGH:!SHA\""),
				"Exepected : 'SSLCipherSuite=\"HIGH:!SHA\"' but found : " + CipherOutput);

		logger.info("\nChecking Security Features for : Cipher properties \n");
		String CipherPropertiesOutput = generalOperator.executeCommand(
				"sed -n '/<Connector/,/<\\/Connector>/p' /eniq/sw/runtime/tomcat/conf/server.xml | grep  'SSLHonorCipherOrder=\"true\"'");
		assertTrue(CipherPropertiesOutput.contains("SSLHonorCipherOrder=\"true\""),
				"Exepected : 'SSLHonorCipherOrder=\"true\"' but found : " + CipherPropertiesOutput);
	}

	public static class StepIds {
		public static final String SECURITY_FEATURES = "Selective rollback procedure for gui node hardening";

		private StepIds() {
		}
	}
}
