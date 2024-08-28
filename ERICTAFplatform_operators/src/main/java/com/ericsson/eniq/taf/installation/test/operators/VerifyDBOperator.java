package com.ericsson.eniq.taf.installation.test.operators;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.eniq.taf.cli.CLIOperator;
import com.google.inject.Singleton;

@Singleton
public class VerifyDBOperator extends CLIOperator {

    private String verifyDBCommand;

    Logger logger = LoggerFactory.getLogger(VerifyDBOperator.class);

    private static Pattern PATTERN_VERSION_DESCRIPTION = Pattern.compile("(Version\\s*\\S*)");

    private static Pattern PATTERN_OPERATOR_DESCRIPTION = Pattern.compile("(Performing\\s*\\S*)");

    public VerifyDBOperator() {
        super();
        verifyDBCommand = DataHandler.getAttribute("CATDBCMD").toString();
    }

    /**
     * Executes the Calc command and returns the result
     *
     * @param xvalue
     * @param yvalue
     * @param operator
     * @return {@link CalcResponse}
     */
    public String verifyDBCommand() {
        logger.info("verifyDBCommand::" + verifyDBCommand);
        String resultOutput = executeCommand(verifyDBCommand);
        return resultOutput;

    }

}
