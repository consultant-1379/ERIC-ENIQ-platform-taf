package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.installation.test.operators.PF_SecuritySelfAssessmentOperator;

/**
 *
 * @author XARUNHA
 *
 */
public class PF_SecuritySelfAssessmentSteps {

	private static Logger LOGGER = LoggerFactory.getLogger(PF_SecuritySelfAssessmentSteps.class);
	private final static String ENIQ_SW_INSTALLER_PATH = DataHandler.getAttribute("platform.install.versionDBDir")
			.toString();
	private static final String CD = "cd";
	private static final String SPACE = " ";
	private static final String INSTALLED_TP = "./installed_techpacks";
	private static final String OPTION_S = "-s";
	private static final String OPTION_P = "-p";
	private static final String OPTION_V = "-v";
	private static final String OPTION_D = "-d";
	private static final String OPTION_F = "-f";

	@Inject
	private Provider<PF_SecuritySelfAssessmentOperator> provider;

	/**
	 * @DESCRIPTION ES PF Security Self Assessment: Defense in Depth and use of
	 *              Choke Points
	 * @PRE Log Verification
	 */

	@TestStep(id = StepIds.VERIFY_PF_SECURITY_OPTION_D)
	public void verifyinstalledTPwithOptionD() {
		final PF_SecuritySelfAssessmentOperator pfSecurityOperator = provider.get();
		final List<String> output = pfSecurityOperator
				.executeCommands(CD + SPACE + ENIQ_SW_INSTALLER_PATH + ";" + SPACE + INSTALLED_TP + SPACE + OPTION_D);

		assertTrue(output.size() > 0, " '" + output + "' Length is 0 or Empty ");
	}

	@TestStep(id = StepIds.VERIFY_PF_SECURITY_OPTION_S)
	public void verifyinstalledTPwithOptionS() {
		final PF_SecuritySelfAssessmentOperator pfSecurityOperator = provider.get();
		final List<String> output = pfSecurityOperator
				.executeCommands(CD + SPACE + ENIQ_SW_INSTALLER_PATH + ";" + SPACE + INSTALLED_TP + SPACE + OPTION_S);
		assertTrue(output.size() > 0, " Size of the command output either 0 or less than 0  " + output);
	}

	@TestStep(id = StepIds.VERIFY_PF_SECURITY_OPTION_P)
	public void verifyinstalledTPwithOptionP() {
		final PF_SecuritySelfAssessmentOperator pfSecurityOperator = provider.get();
		final List<String> output = pfSecurityOperator
				.executeCommands(CD + SPACE + ENIQ_SW_INSTALLER_PATH + ";" + SPACE + INSTALLED_TP + SPACE + OPTION_P);
		for (String str : output) {

			assertTrue(str.length() > 0, "'" + str + "' lenght is 0 or less than 0");
		}
	}

	@TestStep(id = StepIds.VERIFY_PF_SECURITY_OPTION_V)
	public void verifyinstalledTPwithOptionV() {
		final PF_SecuritySelfAssessmentOperator pfSecurityOperator = provider.get();
		final List<String> output = pfSecurityOperator
				.executeCommands(CD + SPACE + ENIQ_SW_INSTALLER_PATH + ";" + SPACE + INSTALLED_TP + SPACE + OPTION_V);
		for (String str : output) {

			assertTrue(str.startsWith("R"), " Command Output doesn't start 'R' : Command output : " + str);
		}
	}

	@TestStep(id = StepIds.VERIFY_PF_SECURITY_OPTION_F)
	public void verifyinstalledTPwithOptionF() {
		final PF_SecuritySelfAssessmentOperator pfSecurityOperator = provider.get();
		final List<String> output = pfSecurityOperator
				.executeCommands(CD + SPACE + ENIQ_SW_INSTALLER_PATH + ";" + SPACE + INSTALLED_TP + SPACE + OPTION_F);
		for (String str : output) {

			assertTrue(str.startsWith("CXC"), " Command Output doesn't start 'CXC' : Command output : " + str);
		}
	}

	public static class StepIds {

		public static final String VERIFY_PF_SECURITY_OPTION_D = "Verify installed_techpacks with Option -d";
		public static final String VERIFY_PF_SECURITY_OPTION_S = "Verify installed_techpacks with Option -s";
		public static final String VERIFY_PF_SECURITY_OPTION_P = "Verify installed_techpacks with Option -p";
		public static final String VERIFY_PF_SECURITY_OPTION_V = "Verify installed_techpacks with Option -v";
		public static final String VERIFY_PF_SECURITY_OPTION_F = "Verify installed_techpacks with Option -f";

		private StepIds() {
		}
	}

	public static class Parameters {

		private Parameters() {
		}
	}
}
