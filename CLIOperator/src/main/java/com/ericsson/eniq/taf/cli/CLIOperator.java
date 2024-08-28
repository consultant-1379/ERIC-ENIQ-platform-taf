package com.ericsson.eniq.taf.cli;

import static com.google.common.truth.Truth.assertThat;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.tools.cli.CLI;
import com.ericsson.cifwk.taf.tools.cli.CLICommandHelper;
import com.ericsson.cifwk.taf.tools.cli.Shell;
import com.ericsson.cifwk.taf.tools.cli.TimeoutException;
import com.ericsson.cifwk.taf.tools.cli.handlers.impl.RemoteObjectHandler;
import com.ericsson.cifwk.taf.utils.FileFinder;

/**
 * @author zvaddee
 *
 */
@SuppressWarnings("deprecation")
public class CLIOperator implements GenericOperator {

    /**
     * logger
     */

    private static Logger logger = LoggerFactory.getLogger(CLIOperator.class);
    /**
     * to hold module rstates
     */
    private static Map<String, String> moduleRstates;

    /**
     * to hold parser rstates
     */
    private static Map<String, String> parserRstates;


    /**
     * modules mws path
     */
    private static final String MWS_PATH = DataHandler.getAttribute("platform.mws.path").toString();

    /**
     * Text File: list of latest modules
     */
    private static final String LATEST_PKGS_TEXT_FILE = DataHandler.getAttribute("platform.latest.pkgs").toString();
    private static final String LATEST_PARSER_PKGS_TEXT_FILE = DataHandler.getAttribute("platform.latest.parsers.pkgs").toString();
    
    /**
     * feature parsser mws path
     */
    private static final String PARSER_MWS_PATH = DataHandler.getAttribute("platform.parser.mws.path").toString();

    /**
     * incase of running in vap this is required
     */
    private static String sshCommand = "";

    /**
     * File extension zip
     */
    private static String zipFileExtn = ".zip";

    /**
     * Taf cli
     */
    private CLI cli;

    /**
     * taf shell
     */
    private Shell shell;


        private final String DCUSER_USER = DataHandler.getAttribute("platform.user.dcuser").toString();
        private final String DCUSER_PASSWORD = DataHandler.getAttribute("platform.password.dcuser").toString();
        private final String ROOT_USER = DataHandler.getAttribute("platform.user.root").toString();
        private final String ROOT_PASSWORD = DataHandler.getAttribute("platform.password.root").toString();
        private final String PORT = DataHandler.getAttribute("platform.vApp.port").toString();
    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#getCommand(java.lang.String)
     */
    @Override
    public String getCommand(final String command) {
        return DataHandler.getAttribute(cliCommandPropertyPrefix + command).toString();
    }

    
	private CLICommandHelper handler;
	private Host eniqshost;
    /**
     * initialise rstates and user.
     */
    public void initialise() {
    
    	
    	eniqshost = DataHandler.getHostByType(HostType.RC);
        initializeShell(eniqshost, eniqshost.getDefaultUser());
        //populate modue rstates
        if (moduleRstates == null || moduleRstates.isEmpty()) {
            loadModuleRstates();
        }
        //populate parser rstates
        if (parserRstates == null || parserRstates.isEmpty()) {
            loadParserRstates();
        }
        sshCommand = "ssh dcuser@linux1 ";
    }

    /**
     * initialize root user.
     */
    public void initialiseRoot() {
        //set host
    	eniqshost = DataHandler.getHostByType(HostType.RC);
        initializeShell(eniqshost, eniqshost.getDefaultUser());
        //populate modue rstates
        if (moduleRstates == null || moduleRstates.isEmpty()) {
            loadModuleRstates();
        }
        //populate parser rstates
        if (parserRstates == null || parserRstates.isEmpty()) {
            loadParserRstates();
        }
        sshCommand = "ssh dcuser@linux1 ";
    }
/*
    *//**
     *
     *//*
    public void loadModuleRstates() {
        //fetch rstates rom mws
        final String completeOutput = executeWithoutSSHCommand("cd " + MWS_PATH + ";" + " ls -t -1");
        final List<String> mwsModules = new ArrayList<String>();
        for (final String output : completeOutput.split("\\n")) {
            if (output.trim().endsWith(zipFileExtn)) {
                mwsModules.add(output.trim().split(zipFileExtn)[0]);
            }
        }
        moduleRstates = new HashMap<String, String>();
        //populate rstates
        for (final String module : mwsModules) {
            final String[] rStates = module.split("_R");
            moduleRstates.put(rStates[0], "R" + rStates[1]);
        }
        logger.info("moduleRstates::" + moduleRstates);
    }
*/
    /**
    *
    */
   public void loadModuleRstates() {
       //fetch rstates text file 
       final String completeOutput = executeWithoutSSHCommand("cat " + LATEST_PKGS_TEXT_FILE);
       final List<String> mwsModules = new ArrayList<String>();
       for (final String output : completeOutput.split("\\n")) {
           if (output.trim().endsWith(zipFileExtn)) {
               mwsModules.add(output.trim().split(zipFileExtn)[0]);
           }
       }
       moduleRstates = new HashMap<String, String>();
       //populate rstates
       for (final String module : mwsModules) {
           final String[] rStates = module.split("_R");
           moduleRstates.put(rStates[0], "R" + rStates[1]);
       }
       logger.info("moduleRstates::" + moduleRstates);
   }

/*
    *//**
     *
     *//*
    public void loadParserRstates() {
        final String completeOutput = executeWithoutSSHCommand("cd " + PARSER_MWS_PATH + ";" + " ls -t -1");
        final List<String> mwsModules = new ArrayList<String>();
        for (final String output : completeOutput.split("\\n")) {
            if (output.trim().endsWith(zipFileExtn)) {
                mwsModules.add(output.trim().split(zipFileExtn)[0]);
            }
        }
        //populate rstates
        parserRstates = new HashMap<String, String>();
        for (final String module : mwsModules) {
            final String[] rStates = module.split("_R");
            parserRstates.put(rStates[0], "R" + rStates[1]);
        }
        logger.info("parserRstates::" + parserRstates);
    }
*/
    
    /**
    *
    */
   public void loadParserRstates() {
       final String completeOutput = executeWithoutSSHCommand("cat " + LATEST_PARSER_PKGS_TEXT_FILE);
       final List<String> mwsModules = new ArrayList<String>();
       for (final String output : completeOutput.split("\\n")) {
           if (output.trim().endsWith(zipFileExtn)) {
               mwsModules.add(output.trim().split(zipFileExtn)[0]);
           }
       }
       //populate rstates
       parserRstates = new HashMap<String, String>();
       for (final String module : mwsModules) {
           final String[] rStates = module.split("_R");
           parserRstates.put(rStates[0], "R" + rStates[1]);
       }
       logger.info("parserRstates::" + parserRstates);
   }
    
    
    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#initializeShell(com.ericsson.cifwk.taf.data.Host, com.ericsson.cifwk.taf.data.User)
     */
    @Override
    public void initializeShell(final Host host, final User user) {
        cli = new CLI(host, user);
        if (shell == null) {
            shell = cli.openShell();
            logger.debug("Creating new shell instance");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#writeln(java.lang.String, java.lang.String)
     */
    @Override
    public void writeln(final String command, final String args) {
        final String cmd = getCommand(command);
        logger.trace("Writing " + cmd + " " + args + " to standard input");
        logger.info("Executing commmand " + cmd + " with args " + args);
        shell.writeln(cmd + " " + args);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#writeln(java.lang.String)
     */
    @Override
    public void writeln(final String command) {
        final String cmd = getCommand(command);
        logger.trace("Writing " + cmd + " to standard input");
        logger.info("Executing commmand " + cmd);
        shell.writeln(cmd);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#getExitValue()
     */
    @Override
    public int getExitValue() {
        final int exitValue = shell.getExitValue();
        logger.debug("Getting exit value from shell, exit value is :" + exitValue);
        return exitValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#expect(java.lang.String)
     */
    @Override
    public String expect(final String expectedText) throws TimeoutException {
        logger.debug("Expected return is " + expectedText);
        final String found = shell.expect(expectedText);
        return found;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#expectClose(int)
     */
    @Override
    public void expectClose(final int timeout) throws TimeoutException {
        shell.expectClose(timeout);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#isClosed()
     */
    @Override
    public boolean isClosed() throws TimeoutException {
        return shell.isClosed();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#checkForNullError(java.lang.String)
     */
    @Override
    public String checkForNullError(String error) {
        if (error == null) {
            error = "";
        }
        return error;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#getStdOut()
     */
    @Override
    public String getStdOut() {
        final String result = shell.read();
        logger.debug("Standard out: " + result);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#disconnect()
     */
    @Override
    public void disconnect() {
        logger.info("Disconnecting from shell");
        shell.disconnect();
        shell = null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#sendFileRemotely(com.ericsson.cifwk.taf.data.Host, java.lang.String, java.lang.String)
     */
    @Override
    public void sendFileRemotely(final Host host, final String fileName
                                 , final String fileServerLocation) throws FileNotFoundException {

        final RemoteObjectHandler remoteObjectHandler = new RemoteObjectHandler(host);
        final List<String> fileLocation = FileFinder.findFile(fileName);
        final String remoteFileLocation = fileServerLocation; // unix address
        remoteObjectHandler.copyLocalFileToRemote(fileLocation.get(0), remoteFileLocation);
        logger.debug("Copying " + fileName + " to " + remoteFileLocation + " on remote host");

    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#deleteRemoteFile(com.ericsson.cifwk.taf.data.Host, java.lang.String, java.lang.String)
     */
    @Override
    public void deleteRemoteFile(final Host host, final String fileName
                                 , final String fileServerLocation) throws FileNotFoundException {

        final RemoteObjectHandler remoteObjectHandler = new RemoteObjectHandler(host);
        final String remoteFileLocation = fileServerLocation;
        remoteObjectHandler.deleteRemoteFile(remoteFileLocation + fileName);
        logger.debug("deleting " + fileName + " at location " + remoteFileLocation + " on remote host");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#scriptInput(java.lang.String)
     */
    @Override
    public void scriptInput(final String message) {
        logger.info("Writing " + message + " to standard in");
        shell.writeln(message);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#executeCommand(java.lang.String)
     */
    @Override
    public String executeCommand(final String command) {
        logger.info("Executing command " + sshCommand + '"' + command + '"');
        String resultOutput = null;
        final Shell shell = cli.executeCommand(sshCommand + '"' + command + '"');
        try {
            resultOutput = shell.read();
        } finally {
            if (null != shell) {
                shell.disconnect();
            }
        }
        if (resultOutput.contains("Warning: Permanently added 'linux1,192.168.0.219' (RSA) to the list of known hosts.")) {
            resultOutput = resultOutput.replace("Warning: Permanently added 'linux1,192.168.0.219' (RSA) to the list of known hosts.", "");
        }
        return resultOutput.trim();

		    }

    /*
     * (non-Javadoc)
     *
     * @see com.ericsson.eniq.taf.cli.GenericOperator#executeWithoutSSHCommand(java.lang.String)
     */
    @Override
    public String executeWithoutSSHCommand(final String command) {
        logger.info("Executing without ssh command " + command);
        String resultOutput = null;
        final Shell shell = cli.executeCommand(command);
        try {
            resultOutput = shell.read();
        } finally {
            if (null != shell) {
                shell.disconnect();
            }
        }
        return resultOutput;

    }

    /**
     * @param changeDirCmd changeDirCmd
     * @param directory directory
     * @param listDirCmd listDirCmd
     * @param expectedOut expectedOut
     */
    public void verifyChangedirCommand(final String changeDirCmd
                                       , final String directory
                                       , final String listDirCmd
                                       , final String expectedOut) {
        writeln(changeDirCmd, directory);
        writeln(listDirCmd);
        assertThat(getStdOut().contains(expectedOut)).isTrue();
    }

    /**
     * @param changeDirCmd changeDirCmd
     * @param directory directory
     * @param tailCmd tailCmd
     * @param file file
     * @param expectedOut expectedOut
     */
    public void verifyChangedirCommandWithTail(final String changeDirCmd
                                               , final String directory
                                               , final String tailCmd
                                               , final String file
                                               , final String expectedOut) {
        writeln(changeDirCmd, directory);
        writeln(tailCmd, file);
        assertThat(getStdOut().contains(expectedOut)).isTrue();
    }

    /**
     * @param exitShellCmd exitShellCmd
     * @param timeout timeout
     * @param expectedExitCode expectedExitCode
     */
    public void verifyClose(final String exitShellCmd
                            , final int timeout
                            , final int expectedExitCode) {
        writeln(exitShellCmd);
        expectClose(timeout);
        assertThat(expectedExitCode).isEqualTo(getExitValue());
        assertThat(isClosed()).isTrue();
    }

    /**
     * @param changeDirCmd changeDirCmd
     * @param fileLocation fileLocation
     * @param file file
     * @param executeFileCmd executeFileCmd
     * @param firstPrompt firstPrompt
     * @param firstPromptResponse firstPromptResponse
     * @param secondPrompt secondPrompt
     * @param secondPromptResponse secondPromptResponse
     */
    public void executeScript(final String changeDirCmd, final String fileLocation
                              , final String file, final String executeFileCmd
                              , final String firstPrompt, final String firstPromptResponse
                              , final String secondPrompt, final String secondPromptResponse) {
        writeln(changeDirCmd, fileLocation);
        writeln("convertLineEndings", file);
        writeln(executeFileCmd, file);
        expect(firstPrompt);
        scriptInput(firstPromptResponse);
        expect(secondPrompt);
        scriptInput(secondPromptResponse);
    }

    /**
     * @return map map
     */
    public static Map<String, String> getModuleRstates() {
        return moduleRstates;
    }

    /**
     * @return map paersers rstates
     */
    public static Map<String, String> getParserRstates() {
        return parserRstates;
    }

}
                                     