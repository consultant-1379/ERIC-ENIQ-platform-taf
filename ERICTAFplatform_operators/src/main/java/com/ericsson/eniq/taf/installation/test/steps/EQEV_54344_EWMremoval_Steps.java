package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.EQEV_54344_EWMremoval_Operators;

public class EQEV_54344_EWMremoval_Steps {

	private static Logger logger = LoggerFactory.getLogger(EQEV_54344_EWMremoval_Steps.class);

	private static final String CRONTAB_ENTRIES = "crontab -l | grep -i wifi";
	private static final String WIFI_SCRIPT_CHECK_INSTALL = "cd /eniq/sw/installer; ls | grep -i wifi";
	private static final String WIFI_SCRIPT_CHECK_BIN = "cd /eniq/admin/bin ls | grep -i wifi";

	@Inject
	private Provider<EQEV_54344_EWMremoval_Operators> provider;

	/**
	 * @DESCRIPTION Verify crontab entries on the server both root/dcuser
	 */
	@TestStep(id = StepIds.EQEV54344_EWM_removal_06_STEP)
	public void verifyCrontabEntries() {
		final EQEV_54344_EWMremoval_Operators operator = provider.get();
		String crontab_root = operator.executeCommand(CRONTAB_ENTRIES);
		String crontab_dcuser = operator.executeCommandDcuser(CRONTAB_ENTRIES);
		assertFalse(crontab_root.contains("wifipoller.bash"),
				"wifipoller.bash script is present in crontab entries under root");
		assertFalse(crontab_dcuser.contains("wifipoller.bash"),
				"wifipoller.bash script is present in crontab entries under dcuser");
	}

	/**
	 * @DESCRIPTION Verify whether all related scripts removed from
	 *              /eniq/sw/installer and /eniq/admin/bin
	 */
	@TestStep(id = StepIds.EQEV54344_EWM_removal_09_STEP)
	public void verifyWifiScriptsRemoved() {
		final EQEV_54344_EWMremoval_Operators operator = provider.get();
		String wifi_script_install_check = operator.executeCommand(WIFI_SCRIPT_CHECK_INSTALL);
		String wifi_script_bin_check = operator.executeCommand(WIFI_SCRIPT_CHECK_BIN);
		assertFalse(wifi_script_install_check.contains("wifi"),
				"wifi related scripts are removed from /eniq/sw/installer");
		assertFalse(wifi_script_bin_check.contains("wifi"), "wifi related scripts are removed from /eniq/admin/bin");
	}

	public static class StepIds {
		public static final String EQEV54344_EWM_removal_06_STEP = "Verify crontab entries on the server both root/dcuser";
		public static final String EQEV54344_EWM_removal_09_STEP = "Verify whether all related scripts removed from /eniq/sw/installer and /eniq/admin/bin";

		private StepIds() {
		}
	}

}
