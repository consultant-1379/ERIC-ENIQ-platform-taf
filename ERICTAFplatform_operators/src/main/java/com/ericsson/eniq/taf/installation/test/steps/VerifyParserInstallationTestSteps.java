package com.ericsson.eniq.taf.installation.test.steps;

import static com.ericsson.eniq.taf.cli.CLIOperator.getParserRstates;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.Input;
import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.VerifyParserInstallationOperator;

/**
 * 
 * @author zvaddee
 *
 */
public class VerifyParserInstallationTestSteps {

    private static Logger LOGGER = LoggerFactory.getLogger(VerifyParserInstallationTestSteps.class);

    private List<String> logFiles = new ArrayList<>();

    @Inject
    private Provider<VerifyParserInstallationOperator> provider;

    /**
     * 
     * @param moduleName moduleName
     */
    @TestStep(id = StepIds.VERIFY_PARSER_MODULES_LOGS)
    public void verifyPlatFormLogFile(@Input(Parameters.PARSER_MODULE) String moduleName) {

        final VerifyParserInstallationOperator parserOperator = provider.get();
        parserOperator.initialise();
        if (logFiles.size() == 0) {
            logFiles = parserOperator.listParserLogFiles();
        }
        String fileName = null;
        for (final String f : logFiles) {
            if (f.startsWith(moduleName + "_" + getRstate(moduleName)) && f.endsWith(".log")) {
                fileName = f;
                break;
            }
        }
        LOGGER.info(moduleName + "- Verify log contents" + fileName);
        if (fileName != null) {
            final String fileInfo = parserOperator.getParserLogContent(fileName);
            assertFalse(fileInfo.length() > 0, "Log file for module " + moduleName + "has Exceptions" + fileInfo);
        } else {
            assertFalse(true, "Log file for module " + moduleName + " does not exists with RState " + getRstate(moduleName));
        }
        // assertTrue(true);
    }

    /**
     * 
     * @param moduleName moduleName
     */
    @TestStep(id = StepIds.VERIFY_PARSER_VERSION_DB_PROPERTIES)
    public void verifyVersionDBPropetiesUpdated(@Input(Parameters.PARSER_MODULE) String moduleName) {

        LOGGER.info(moduleName + "- Verify verifyVersionDBPropetiesUpdated");
        final VerifyParserInstallationOperator parserOperator = provider.get();
        parserOperator.initialise();
        final String output = parserOperator.versionDBModuleUpdated(moduleName);
        assertTrue(output.contains("module." + moduleName + "=" + getRstate(moduleName)),
                "versiondb.properties is not updated for module::" + moduleName + "=" + getRstate(moduleName) + "Actual output" + output);
        // assertTrue(true);
    }

    /**
     * 
     * @param moduleName moduleName
     */
    @TestStep(id = StepIds.VERIY_PARSER_MODULE_FOLDERS_EXISTS)
    public void verifyModulesExtracted(@Input(Parameters.PARSER_MODULE) String moduleName) {

        LOGGER.info(moduleName + "- Verify verifyModulesExtracted");
        final VerifyParserInstallationOperator platformLogOperator = provider.get();
        platformLogOperator.initialise();
        final String output = platformLogOperator.modulesExtracted(moduleName);
        assertTrue(output.contains(moduleName + "-" + getRstate(moduleName)),
                moduleName + getRstate(moduleName) + " is not present under /eniq/sw/platform ");
        // assertTrue(true);
    }

    /**
     * 
     * @author zvaddee
     *
     */
    public static class StepIds {
        public static final String VERIY_PARSER_MODULE_FOLDERS_EXISTS = "Verify installed parser modules folders are available.";
        public static final String VERIFY_PARSER_VERSION_DB_PROPERTIES = "Verify Installed parser module version is updated in versiondb.properties file.";
        public static final String VERIFY_PARSER_MODULES_LOGS = "Verify the parser module logs for any errors.";

        private StepIds() {
        }
    }

    /**
     * 
     * @author zvaddee
     *
     */
    public static class Parameters {
        public static final String PARSER_MODULE = "module";

        private Parameters() {
        }

    }

    private String getRstate(String moduleName) {
        return getParserRstates().get(moduleName) == null ? "" : getParserRstates().get(moduleName);

    }
}
