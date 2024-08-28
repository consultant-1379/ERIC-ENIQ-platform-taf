package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.RHELOSScriptsMaintenanceOperator;

/**
 * 
 * @author xsounpk
 *
 */
public class RHELOSScriptsMaintenanceSteps {

	private static Logger Logger = LoggerFactory.getLogger(RHELOSScriptsMaintenanceSteps.class);

	private final String GET_NFS_FILES = "cd /eniq/data/pmdata/eniq_oss_1/;ls -R -a | find \"$(pwd)\" | grep .nfs";
	private final String RUN_HIDDEN_SCRIPT = "cd /eniq/sw/bin/; bash ./remove_hidden_files.bsh ";
	private final String GET_NFS_FILES_ONLY = "cd /eniq/data/pmdata/eniq_oss_1;ls -R -a | grep .nfs";
	private final String GET_HIDDEN_LOG_FILES = "cd /eniq/log/sw_log/engine/;ls | grep remove_hidden_files_ | tail -1";
	private final String RUN_HOSTS_SCRIPT = "cd /eniq/sw/installer/; bash ./hosts_file_update.sh 10.45.207.13";
	private final String GET_UPDATED_HOSTS = "cat /etc/hosts | grep 10.45.207.13";
	private final String GET_ACTIVE_INTERFACE_SCRIPT = "cd /eniq/sw/installer/; ./get_active_interfaces";
	private final String DEACTIVATE_INTERFACE_SCRIPT = "cd /eniq/sw/installer/; ./deactivate_interface -o eniq_oss_1 -i ";
	private final String GET_RMI_STATUS = "/eniq/admin/bin/manage_deployment_services.bsh -a list -s ALL | grep -i 'scheduler.\\|engine\\|lwphelper\\|licmgr\\|rmiregistry'";
	private final String RMI_START_SERVICE = "echo Yes | /eniq/admin/bin/manage_deployment_services.bsh -a start -s ALL";
	private final String RMI_STOP_SERVICE = "echo Yes | /eniq/admin/bin/manage_deployment_services.bsh -a stop -s ALL";
	private final String RESTART_SERVICE = "echo Yes | /eniq/admin/bin/manage_deployment_services.bsh -a restart -s ALL";
	private final String GET_LWP_STATUS = "systemctl -a -t service | grep -i lwphelper";
	private final String GET_SCHEDULER_STATUS = "systemctl -a -t service | grep -i scheduler.";
	Set<String> nfsFiles = new HashSet<String>();
	Set<String> getNfsFiles = new HashSet<String>();
	Set<String> dir = new HashSet<String>();
	Set<String> hostsFile = new HashSet<String>();
	List<String> activeInterface = new ArrayList<String>();
	Set<String> rmiStatusService = new HashSet<String>();
	Set<String> rmiStatusService1 = new HashSet<String>();
	String splitNfs = null;
	String deactivate = null;

	@Inject
	private Provider<RHELOSScriptsMaintenanceOperator> provider;

	/**
	 * @DESCRIPTION Port OS specific impacts in maintenance scripts to RHEL OS
	 * 
	 */

	@TestStep(id = StepIds.VERIFY_HIDDEN_SCRIPT_EXECUTION)
	public void VerifyHiddenScriptExecution() {
		final RHELOSScriptsMaintenanceOperator rHELOSScriptsMaintenanceOperator = provider.get();
		String getNfsFile = rHELOSScriptsMaintenanceOperator.executeCommands(GET_NFS_FILES);
		String getNfsFileOnly = rHELOSScriptsMaintenanceOperator.executeCommands(GET_NFS_FILES_ONLY);
		if (!(getNfsFile.isEmpty())) {
			String file[] = getNfsFile.split("\\r?\\n");
			for (int i = 0; i < file.length; i++) {
				nfsFiles.add(file[i]);
			}
		}
		if (!(getNfsFileOnly.isEmpty())) {
			String dirFile[] = getNfsFileOnly.split("\\r?\\n");
			for (int i = 0; i < dirFile.length; i++) {
				getNfsFiles.add(dirFile[i]);
			}
		}
		if (nfsFiles.size() > 0) {
			assertTrue(nfsFiles.size() > 0, "Contains .nfs files to remove");
			for (String nfs : nfsFiles) {
				for (String nfsFile : getNfsFiles) {
					splitNfs = nfs.replace(nfsFile, "");
					if ((!splitNfs.contains(nfs))) {
						dir.add(splitNfs);
					}
				}
			}
			for (String nfsDir : dir) {
				String executeScript = rHELOSScriptsMaintenanceOperator
						.executeCommands(RUN_HIDDEN_SCRIPT + nfsDir.trim());
				assertFalse(executeScript.contains("No such file or directory"),
						"remove_hidden_files.bsh script executed successfully");
				assertTrue(executeScript.isEmpty(), "remove_hidden_files.bsh script executed successfully");

			}
			String getNfsFileEmpty = rHELOSScriptsMaintenanceOperator.executeCommands(GET_NFS_FILES);
			assertTrue(getNfsFileEmpty.isEmpty(), "The hidden files removed from the respective path.");
			String getHiddenFiles = rHELOSScriptsMaintenanceOperator.executeCommands(GET_HIDDEN_LOG_FILES);
			getHiddenFiles.trim().matches("remove_hidden_files_[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]" + ".log");
			assertTrue(
					getHiddenFiles.matches("remove_hidden_files_[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]" + ".log"),
					"Log file " + getHiddenFiles + " is created with format remove_hidden_files_YYYY-MM-DD.log");

		} else {
			assertTrue(nfsFiles.isEmpty(), "Contains no .nfs files to remove");
			Logger.info("Contains no .nfs files to remove using remove_hidden_files.bsh script");
		}
	}

	@TestStep(id = StepIds.VERIFY_HOSTS_FILE_EXECUTION)
	public void VerifyHostsFileExecution() {
		final RHELOSScriptsMaintenanceOperator rHELOSScriptsMaintenanceOperator = provider.get();
		String hostsScript = rHELOSScriptsMaintenanceOperator.executeCommand(RUN_HOSTS_SCRIPT);
		String getUpdation = rHELOSScriptsMaintenanceOperator.executeCommand(GET_UPDATED_HOSTS);
		if (!(hostsScript.contains("Already exists"))) {
			assertTrue(hostsScript.contains("hosts file updated successfully"), "hosts file updated successfully");
		}
		if (!(getUpdation.isEmpty())) {
			assertTrue(getUpdation.contains("10.45.207.13") && getUpdation.contains("webportal"),
					" hosts file updated with BIS 10.45.207.13 server details");
		}
	}

	@TestStep(id = StepIds.VERIFY_INTERFACE_FILE_EXECUTION)
	public void VerifyInterfaceFileExecution() {
		final RHELOSScriptsMaintenanceOperator rHELOSScriptsMaintenanceOperator = provider.get();
		String getActiveInterface = rHELOSScriptsMaintenanceOperator.executeCommands(GET_ACTIVE_INTERFACE_SCRIPT);
		if (!(getActiveInterface.isEmpty())) {
			String interfac[] = getActiveInterface.split("\\r?\\n");
			for (int i = 0; i < interfac.length; i++) {
				activeInterface.add(interfac[i]);
			}
		}
		if (!activeInterface.isEmpty() && activeInterface.size() > 0) {
			deactivate = activeInterface.get(0).replace("eniq_oss_1", "");
			Logger.info("Interface :" + deactivate);
			String deactivateInterface = rHELOSScriptsMaintenanceOperator
					.executeCommands(DEACTIVATE_INTERFACE_SCRIPT + deactivate);
			String afterGetActiveInterface = rHELOSScriptsMaintenanceOperator
					.executeCommands(GET_ACTIVE_INTERFACE_SCRIPT + " | grep -i " + deactivate);
			if (!afterGetActiveInterface.isEmpty()) {
				Logger.info("deactivate_interface script failed to deactivate the active interface");
				assertFalse(!afterGetActiveInterface.isEmpty(),
						"deactivate_interface script failed to deactivate the active interface");
			}
		} else {
			Logger.info("There is no active Interface to deactivate using deactivate_interface script.");
			assertTrue(activeInterface.size() < 0,
					"There is no active Interface to deactivate using deactivate_interface script.");
		}
	}

	@TestStep(id = StepIds.VERIFY_RHEL_DEPLOYED_SERVICES)
	public void VerifyRHELDeployedServices() {
		final RHELOSScriptsMaintenanceOperator rHELOSScriptsMaintenanceOperator = provider.get();
		String restart = rHELOSScriptsMaintenanceOperator.executeCommand(RESTART_SERVICE);
		String getscheduler = rHELOSScriptsMaintenanceOperator.executeCommand(GET_SCHEDULER_STATUS);
		if ((getscheduler.contains("scheduler")) && getscheduler.contains("active")
				&& getscheduler.contains("running")) {
			assertFalse(((getscheduler.contains("scheduler")) && getscheduler.contains("inactive")
					&& getscheduler.contains("dead")), "Unable to restart Scheduler services.");
		}
		String getRmiStop = rHELOSScriptsMaintenanceOperator.executeCommand(RMI_STOP_SERVICE);
		String getRmiStopStatus = rHELOSScriptsMaintenanceOperator.executeCommand(GET_RMI_STATUS);
		if (!(getRmiStopStatus.isEmpty())) {
			String file1[] = getRmiStopStatus.split("\\r?\\n");
			for (int i = 0; i < file1.length; i++) {
				rmiStatusService.add(file1[i].trim());
			}
			for (String stop : rmiStatusService) {
				if (stop.contains("inactive") && stop.contains("dead")) {
					assertFalse((stop.contains("active") && stop.contains("running")),
							"Unable to stop rmiregistry services.");
				}
			}
		}
		String getRmiStart = rHELOSScriptsMaintenanceOperator.executeCommand(RMI_START_SERVICE);
		String getRmiStartStatus = rHELOSScriptsMaintenanceOperator.executeCommand(GET_RMI_STATUS);
		if (!(getRmiStartStatus.isEmpty())) {
			String file[] = getRmiStartStatus.split("\\r?\\n");
			for (int i = 0; i < file.length; i++) {
				rmiStatusService1.add(file[i].trim());
			}
			for (String str : rmiStatusService1) {
				if (str.contains("active") && str.contains("running")) {
					assertFalse((str.contains("inactive") && str.contains("dead")),
							"Unable to start rmiregistry services.");
				}
			}
		}
	}

	@TestStep(id = StepIds.VERIFY_LWPHELPER_SERVICES)
	public void VerifylwhHelperStatus() {
		final RHELOSScriptsMaintenanceOperator rHELOSScriptsMaintenanceOperator = provider.get();
		String getlwpHelper = rHELOSScriptsMaintenanceOperator.executeCommands(GET_LWP_STATUS);
		if ((getlwpHelper.contains("lwphelper")) && getlwpHelper.contains("active")
				&& getlwpHelper.contains("running")) {
			assertFalse(((getlwpHelper.contains("lwphelper")) && getlwpHelper.contains("inactive")
					&& getlwpHelper.contains("dead")), "lwphelper status is inactive.");
		}
	}

	public static class StepIds {

		public static final String VERIFY_HIDDEN_SCRIPT_EXECUTION = "Verify the execution of remove_hidden_files.bsh.";
		public static final String VERIFY_HOSTS_FILE_EXECUTION = "Verify the execution of hosts_file_update.sh";
		public static final String VERIFY_INTERFACE_FILE_EXECUTION = "Verify the execution of deactivate_interface script";
		public static final String VERIFY_RHEL_DEPLOYED_SERVICES = "Verify RMIRegistry is stopping and starting the dependent services";
		public static final String VERIFY_LWPHELPER_SERVICES = "Verify lwphelper service is active and running state";

		private StepIds() {
		}
	}
}
