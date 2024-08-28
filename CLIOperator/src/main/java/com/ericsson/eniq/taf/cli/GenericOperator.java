package com.ericsson.eniq.taf.cli;

import java.io.FileNotFoundException;

import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.User;
import com.ericsson.cifwk.taf.tools.cli.Shell;
import com.ericsson.cifwk.taf.tools.cli.TimeoutException;

/**
 * GenericOperator
 * @author zvaddee
 *
 */
public interface GenericOperator {
    String cliCommandPropertyPrefix = "clicommand.";
    String mwsHost = "mws.host";
    String mwsUser = "mws.user";
    String mwsPassword = "mws.pwd";

    /**
     * Retrieves the command specified in cliCommands.properties file
     * @param command command
     * @return CLI command
     */
    String getCommand(String command);

    /**
     * Creates CLI Shell instance using hostname provided
     * @param host host
     * @param user user
     */
    void initializeShell(Host host, User user);

    /**
     * Reads from the standard output
     * @return string
     */
    String getStdOut();

    /**
     * Execute a command with args on the host
     * @param command command
     * @param args args
     * @return
     */
    void writeln(String command, String args);

    /**
     * Execute a command with no args on the host
     * @param command command
     * @return
     */
    void writeln(String command);

    /**
     * Converts a null error to a blank string if no standard error found
     * @param error error
     * @return string
     */
    String checkForNullError(String error);

    /**
     * Checks open/close status of shell
     * @return boolean
     * @throws TimeoutException timeoutException
     */
    boolean isClosed() throws TimeoutException;

    /**
     * Wait for the spawned process to finish.
     * @param timeout timeout
     * @throws TimeoutException timeoutException
     */
    void expectClose(int timeout) throws TimeoutException;

    /**
     * Method that waits for pattern to appear on standard out
     * @param expectedText expected
     * @return read standard out
     * @throws TimeoutException timeoutException
     */
    String expect(String expectedText) throws TimeoutException;

    /**
     * Get the exitCode of the most recent command
     * @return Integer representing exit value
     */
    int getExitValue();

    /**
     * Closes and invalidates SSH shell
     */
    void disconnect();

    /**
     * Send a file to a remote server
     * @param host host
     * @param fileName fileName
     * @param remoteFileLocation remoteFileLocation
     * @throws FileNotFoundException fileNotFoundException
     */
    void sendFileRemotely(Host host, String fileName, String remoteFileLocation) throws FileNotFoundException;

    /**
     * Delete a file from remote server
     * @param host host
     * @param fileName fileName
     * @param remoteFileLocation remoteFileLocation
     * @throws FileNotFoundException fileNotFoundException
     */
    void deleteRemoteFile(Host host, String fileName, String remoteFileLocation) throws FileNotFoundException;

    /**
     * String response to input prompt from script
     * @param message message
     */
    void scriptInput(String message);

    /**
     * Create {@link Shell} and execute the command on it<br />
     * Command will be like a single command, or a list of commands that can be executed one after the other
     *
     * @param command executed commands
     * @return new shell object, representing the shell result of the executed command
     */
    String executeCommand(String command);

    /**
     * 
     * @param command command
     * @return string
     */
    String executeWithoutSSHCommand(String command);
}


