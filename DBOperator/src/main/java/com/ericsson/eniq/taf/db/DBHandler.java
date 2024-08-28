
package com.ericsson.eniq.taf.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.eniq.taf.db.utils.DBHandlerException;
/**
 * Sybase Handler.
 * Provides Convenience methods for accessing a Sybase Database <br>
 */
public class DBHandler {

    private static final String DRIVER = "com.sybase.jdbc3.jdbc.SybDriver";
    private static final String JDBC_PROTOCOL = "jdbc:sybase:Tds:";
    private static Logger logger = LoggerFactory.getLogger(DBHandler.class);
    private final String dbHost;
    private final String dbName;
    private final String portno;
    private final String username;
    private final String password;
    private Connection connection;

    /**
     * Creates a Sybase connection for the required database.
     *
     * @param dbHost
     *         the database host or IP
     * @param portNumber
     *         the database port
     * @param dbName
     *         the database name
     * @param username
     *         the user to connect to the database
     * @param password
     *         the user's password
     *
     * @throws DBHandlerException
     *         exception.
     */
    public DBHandler(final String dbHost, final String portNumber, final String dbName, final String username,
            final String password) throws DBHandlerException {
        this.dbHost = dbHost;
        this.portno = portNumber;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        createDbConnection();
    }

    /**
     * Executes a single Sybase query from the specified SQL file with parameter
     * substitution.
     *
     * @param sqlFile
     *         the source file which contains the Sybase query
     * @param queryParameters
     *         the parameters to substitute into the Sybase query
     *
     * @return result set from the Sybase query
     */
    public TestDataSource<DataRecord> executeQuery(final String sqlFile, final Map<String, String> queryParameters) {
        final String sqlQuery = readSqlFile(sqlFile);
        final String finalQuery = processQuery(sqlQuery, queryParameters);

        return executeQuery(finalQuery);
    }

    /**
     * Executes a single Sybase query.
     *
     * @param sqlQuery
     *         the source file which contains the Sybase query
     *
     * @return result set from the Sybase query
     */
 
    public TestDataSource<DataRecord> executeQuery(final String sqlQuery) {
        Statement stmt = null;
        ResultSet queryResultSet = null;
        TestDataSource<DataRecord> results = null;

        if (sqlQuery != null) {
            try {
                stmt = connection.createStatement();
                logger.info("SQL Query that will be executed\n{}", sqlQuery);
                queryResultSet = stmt.executeQuery(sqlQuery);
                results = SqlParser.parse(queryResultSet);
            } catch (final SQLException ex) {
                logger.error("Unable to get the result set object from Sybase", ex);
            } finally {
                try {
                    if (queryResultSet != null) {
                        queryResultSet.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (final SQLException e) {
                    logger.error("Unable to close the resultset and statement", e);
                }
            }
        } else {
            logger.info("Input parameters not received");
        }
        return results;
    }

    /**
     * Processes/Substitutes the query parameters for the Sybase query.
     *
     * @param sqlQuery
     *         the Sybase query
     * @param valuesMap
     *         the query parameters to substitute
     *
     * @return the complete Sybase query as a string
     */
    public String processQuery(final String sqlQuery, final Map<String, String> valuesMap) {
        final StrSubstitutor sub = new StrSubstitutor(valuesMap);
        final String resolvedString = sub.replace(sqlQuery);
        return resolvedString;
    }

    /**
     * Releases this Connection object's database and JDBC resources immediately.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (final SQLException sqle) {
            logger.error("Unable to close the connection", sqle);
        }
    }

    private void createDbConnection() throws DBHandlerException {
        try {
            final String url = JDBC_PROTOCOL + this.dbHost + ":" + portno + "/" + this.dbName;
            System.setProperty(DRIVER, "");
            // Open the connection.
            logger.info("dburl:::"+url);
            DriverManager.registerDriver((Driver) Class.forName(DRIVER).newInstance());

            logger.info("DataBase Url:{} and Username:{}", url, username);
            connection = DriverManager.getConnection(url, username, password);
        } catch (final Exception exc) {
            logger.error("Unable to register the database driver", exc);
            throw new DBHandlerException(exc);
        }
    }

    private String readSqlFile(final String fileName) {
        String result = "";
        logger.info("SQL File Name {}", fileName);
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            logger.info("SQL Query File that will be used\n{}", classLoader.getResource(fileName).toExternalForm());
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (final IOException e) {
            logger.error("Cannot read the SQL file", e);
        }
        return result;
    }
}
