package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyLicenseManagerOperator;

/**
 * 
 * @author zvaddee
 *
 */
public class VerifyLicenseManagerTestSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(VerifyLicenseManagerTestSteps.class);

	@Inject
	private Provider<VerifyLicenseManagerOperator> provider;

	/**
	 * initialise
	 */
	@TestStep(id = StepIds.CLI_INITIALIZE)
	public void cliInitialize() {
		final VerifyLicenseManagerOperator processOperator = provider.get();
		processOperator.initialise();
		assertTrue(true);
	}

	/**
	 * VERIFY_LM_LOG
	 */
	@TestStep(id = StepIds.VERIFY_LM_LOG)
	public void veriyLicenseManagerLog() {

		LOGGER.info("veriyLicenseManagerLog");

		// final String calcVersion = DataHandler.getAttribute("CALCVER").toString();

		final VerifyLicenseManagerOperator licenseMgrOperator = provider.get();
		final String op = licenseMgrOperator.getLicenseManagerLogContent();
		LOGGER.info("log output:::" + op);
		assertTrue(op.contains("License manager is running OK"), "LiceneManager log error: license in not VALID");
	}

	/**
	 * 
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_SERVER_STATUS)
	public void veriyLicenseServerStatus() {

		LOGGER.info("veriyLicenseServerStatus");

		// final String calcVersion = DataHandler.getAttribute("CALCVER").toString();

		final VerifyLicenseManagerOperator licenseMgrOperator = provider.get();
		final String op = licenseMgrOperator.licenseServerStatus();
		LOGGER.info("veriyLicenseServerStatus output:::" + op);
		assertTrue(op.contains("Server:") && op.contains("is online"), "Licene sever status is not valid: " + op);
	}

	/**
	 * 
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_MANAGER_STATUS)
	public void veriyLicenseManagerStatus() {
		LOGGER.info("veriyLicenseManagerStatus");
		VerifyLicenseManagerOperator licenseMgrOperator = provider.get();
		final String op = licenseMgrOperator.licenseMgrStatus();
		LOGGER.info("veriyLicenseManagerStatus output:::" + op);
		assertTrue(op.contains("License manager is running OK"), "Licene manager status is not valid: " + op);
	}

	/**
	 * feature Mapping info
	 */
	@TestStep(id = StepIds.VERIFY_FEATURE_MAPPING_INFO)
	public void veriyFeatureMappingInfo(@Input(Parameters.FEATURES) String features) {
		LOGGER.info("Verifying Feature Mapping Information : " + features);
		VerifyLicenseManagerOperator licenseMgrOperator = provider.get();
		final String result = licenseMgrOperator.verifyFeatureMappingInfo(features);
		assertTrue(!result.contains("No mappings found"),
				"No mappings found for : '" + features + "' ERROR : " + result);
	}

	/**
	 * License is valid or not for features
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_VALID_OR_NOT)
	public void validateLicenseManager(@Input(Parameters.FEATURES) String features) {
		LOGGER.info("Verifying Feature Mapping Information : " + features);
		VerifyLicenseManagerOperator licenseMgrOperator = provider.get();
		final String result = licenseMgrOperator.validateLicenseManager(features);
		assertTrue(result.contains("is valid"), result);
	}

	/**
	 * Verify License Manager Operations
	 */
	@TestStep(id = StepIds.VERIFY_LICENSE_OPERATIONS_STATUS)
	public void validateLicenseManagerOperations(@Input(Parameters.OPERATIONS) String operations) {
		LOGGER.info("Verifying License Manager Operation for  : '" + operations + "' \n");
		VerifyLicenseManagerOperator licenseMgrOperator = provider.get();
		if (operations.contains("-start")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("Service enabling eniq-licmgr"), result);
		}
		if (operations.contains("-stop")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("Service stopping eniq-licmgr"), result);
		}
		if (operations.contains("-status")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("License manager is running OK"), result);
		}
		if (operations.contains("-restart")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(
					result.contains("Service stopping eniq-licmgr") && result.contains("Service enabling eniq-licmgr"),
					result);
		}
		if (operations.contains("-serverstatus")) {
			final String result = licenseMgrOperator.validateLicenseManagerOperations(operations);
			assertTrue(result.contains("is online"), result);
		}

	}

	/**
	 * 
	 * @author zvaddee
	 *
	 */
	public static class StepIds {
		public static final String CLI_INITIALIZE = "INITIALIZE CLI";
		public static final String VERIFY_LM_LOG = "Verify License Manager Logs";
		public static final String VERIFY_LICENSE_SERVER_STATUS = "Verify License Server status";
		public static final String VERIFY_LICENSE_MANAGER_STATUS = "Verify License Manager status";
		public static final String VERIFY_FEATURE_MAPPING_INFO = "Verify Viewing Feature Mapping Information using licmgr";
		public static final String VERIFY_LICENSE_VALID_OR_NOT = "Verify License is valid or not for features";
		public static final String VERIFY_LICENSE_OPERATIONS_STATUS = "Verify License Manager Operation Status information";

		private StepIds() {
		}
	}

	public static class Parameters {
		public static final String FEATURES = "cxcNumbers";
		public static final String OPERATIONS = "operations";

		private Parameters() {
		}
	}
}
