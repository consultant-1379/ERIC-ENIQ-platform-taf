package com.ericsson.eniq.taf.db;

import static com.ericsson.eniq.taf.db.Constants.BACKSLASH_N;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TafDataSources;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.cifwk.taf.datasource.TestDataSourceFormatter;
import com.ericsson.eniq.taf.db.utils.DBHandlerException;
import com.ericsson.eniq.taf.db.utils.DateTimeUtil;

/**
 * ddc Operator.
 */
@Singleton
public class EniqDBOperator {
	private static final Logger LOGGER = LoggerFactory.getLogger(EniqDBOperator.class);

	private DBHandler dbHandler;

	private Map<String, String> sqlQueryParameters = new HashMap<String, String>();

	/**
	 * Creates a MySQL DB connection and loads the default MySQL query parameters
	 * from TAF Configuration into the sqlQueryParameters hash map. The execute
	 * query methods in this class will use these parameters by default when running
	 * the queries unless otherwise specified.
	 */
	public void setupDWHREP() {
		LOGGER.info("Setting up DDC DB resources");
		try {
			dbHandler = new DBHandler(Constants.DWHRepHostINfoHelper.DB_HOST,
					Constants.DWHRepHostINfoHelper.DWHREP_PORT_NUMBER, Constants.DWHRepHostINfoHelper.DWHREP_DB_NAME,
					Constants.DWHRepHostINfoHelper.DWHREP_USERNAME, Constants.DWHRepHostINfoHelper.DWHREP_PASSWORD);
		} catch (final DBHandlerException e) {
			LOGGER.error("Failure in connecting to DDC DB", e);
		}
	}

	public void setupDWHDB() {
		LOGGER.info("Setting up DDC DB resources");
		try {
			dbHandler = new DBHandler(Constants.DWHdbHostINfoHelper.DWHDB_HOST,
					Constants.DWHdbHostINfoHelper.DWHDB_PORT_NUMBER, Constants.DWHdbHostINfoHelper.DWHDB_DB_NAME,
					Constants.DWHdbHostINfoHelper.DWHDB_USERNAME, Constants.DWHdbHostINfoHelper.DWHDB_PASSWORD);
		} catch (final DBHandlerException e) {
			LOGGER.error("Failure in connecting to DDC DB", e);
		}
	}

	public void setupETLREP() {
		LOGGER.info("Setting up DDC DB resources");
		try {
			dbHandler = new DBHandler(Constants.ETLRepHostINfoHelper.ETL_HOST,
					Constants.ETLRepHostINfoHelper.ETLREP_PORT_NUMBER, Constants.ETLRepHostINfoHelper.ETLREP_DB_NAME,
					Constants.ETLRepHostINfoHelper.ETLREP_USERNAME, Constants.ETLRepHostINfoHelper.ETLREP_PASSWORD);
		} catch (final DBHandlerException e) {
			LOGGER.error("Failure in connecting to DDC DB", e);
		}
	}

	/**
	 * teardown
	 */
	public void teardown() {
		try {
			dbHandler.close();
		} catch (final NullPointerException e) {
			LOGGER.warn("Nullpointer exception for mysqlHandler. Disregard if running Health Checks at present");
		}
	}

	/**
	 * Executes a single MySQL query from the specified SQL file and then merges the
	 * values from the CSV test date file into the result set and returns this.
	 *
	 * @param sourceSqlFile    the location of the source filename containing the
	 *                         MySQL query to run
	 * @param testDataFilename the location of the CSV filename containing test data
	 *                         such as expected values or thresholds
	 *
	 * @return result set from the MySQL query
	 */
	public TestDataSource<DataRecord> executeQueryWithTestDataFile(final String sourceSqlFile,
			final String testDataFilename) {
		return executeQueryWithTestDataFile(sourceSqlFile, testDataFilename, sqlQueryParameters);
	}

	/**
	 * Executes a single MySQL query from the specified SQL file with specific
	 * parameter substitution and then merges the values from the CSV test date file
	 * into the result set and returns this.
	 *
	 * @param sourceSqlFile      the location of the source filename containing the
	 *                           MySQL query to run
	 * @param testDataFilename   the location of the CSV filename containing test
	 *                           data such as expected values or thresholds
	 *
	 * @param sqlQueryParameters map of parameters
	 * @return result set from the MySQL query
	 */
	public TestDataSource<DataRecord> executeQueryWithTestDataFile(final String sourceSqlFile,
			final String testDataFilename, final Map<String, String> sqlQueryParameters) {
		final TestDataSource<DataRecord> actual = executeQuery(sourceSqlFile, sqlQueryParameters);

		// Parse our expected value
		final TestDataSource<DataRecord> expected = TafDataSources.fromCsv(testDataFilename);

		// Merge the expected value with the result set and return this
		final TestDataSource<DataRecord> merged = TafDataSources.merge(actual, expected);
		if (merged.iterator().hasNext()) {
			LOGGER.info("\n" + TestDataSourceFormatter.format(merged));
		}
		return merged;
	}

	/**
	 * Executes a single MySQL query from the specified SQL file with default
	 * parameter substitution (retrieved in setup of this class).
	 *
	 * @param sourceSqlFile the location of the source file which contains the MySQL
	 *                      query
	 *
	 * @return result set from the MySQL query
	 */
	public TestDataSource<DataRecord> executeQuery(final String sourceSqlFile) {
		return executeQuery(sourceSqlFile, sqlQueryParameters);
	}

	/**
	 * Executes a single MySQL query from the specified SQL file with specific
	 * parameter substitution.
	 *
	 * @param sourceSqlFile      the location of the source file which contains the
	 *                           MySQL query
	 * @param sqlQueryParameters the parameters to substitute into the MySQL query
	 *
	 * @return result set from the MySQL query
	 */
	public TestDataSource<DataRecord> executeQuery(final String sourceSqlFile,
			final Map<String, String> sqlQueryParameters) {
		final TestDataSource<DataRecord> resultSet = dbHandler.executeQuery(sourceSqlFile, sqlQueryParameters);
		if (resultSet.iterator().hasNext()) {
			LOGGER.info(BACKSLASH_N + TestDataSourceFormatter.format(resultSet));
		}
		return resultSet;
	}

	/**
	 * Last upload time.
	 * 
	 * @return Date date
	 * @throws ParseException     parseException
	 * @throws DBHandlerException dbHandlerException
	 */
	public Date getLastDdpUploadTimeFromDatabase() throws ParseException, DBHandlerException {
		final TestDataSource<DataRecord> resultSet = executeQuery(
				Constants.DDC_GENERAL + Constants.DDP_DB_GET_LAST_UPLOAD_TIME_QUERY);
		final Iterator<DataRecord> itr = resultSet.iterator();
		if (!itr.hasNext()) {
			throw new DBHandlerException(Constants.RESULT_SET_CONTAINS_NO_MORE_ROWS_MESSAGE);
		}
		final DataRecord dataRecord = itr.next();
		return new SimpleDateFormat(Constants.MT_DATE_FORMAT_STRING, Locale.ENGLISH)
				.parse((String) dataRecord.getFieldValue("lastupload"));
	}

	/**
	 * Checks whether Assertions or System health checks are being run and uses
	 * current time, if it is system health checks, uses upgrade end time, if it is
	 * assertions.
	 *
	 * @param isHealthCheck boolean parameter to check system health
	 *                      checks/Assertions
	 *
	 * @return failure message
	 *
	 * @throws ParseException     if fails to parse date
	 * @throws DBHandlerException
	 */
	public String setDatesWithErrorMessageIfFailing(final boolean isHealthCheck)
			throws ParseException, DBHandlerException {
		final Date formattedTimeToCheck;
		final Date latestAvailableTimeInDb = getLastDdpUploadTimeFromDatabase();
		final String latestDdpTimeString = latestAvailableTimeInDb.toString();
		final int amountOfUnitsToAdjust;
		final String timeToAdjust;
		if (isHealthCheck) {
			// For Health checks, as end time is not necessary, get the current time and
			// subtract 120 minutes
			LOGGER.info("Running Health Checks on SUT ");
			timeToAdjust = DateTimeUtil.convertDateStringFromOneFormatToAnother(new Date().toString(),
					Constants.MT_DATE_FORMAT_STRING);
			amountOfUnitsToAdjust = -120;
		} else {
			// For Assertions, end time is provided in the job. subtract an hour and 30
			// minutes
			LOGGER.info("Running Assertions on SUT");
			timeToAdjust = DateTimeUtil.getEndTimeHandleNull(latestDdpTimeString);
			amountOfUnitsToAdjust = -90;
		}

		final String adjustedTimeInMinutes = DateTimeUtil.adjustDate(timeToAdjust, amountOfUnitsToAdjust,
				Calendar.MINUTE);
		formattedTimeToCheck = new SimpleDateFormat(Constants.MT_DATE_FORMAT_STRING, Locale.ENGLISH)
				.parse(adjustedTimeInMinutes);
		LOGGER.info(
				"Checking that the last upload time in DDP database is greater than or equal than the following end time '"
						+ formattedTimeToCheck + "'");

		// Calculate whether the last upload time in DDP database is greater or equal
		// than our end time(for Assertions)/current time (for Health
		// checks)
		if (latestAvailableTimeInDb.getTime() >= formattedTimeToCheck.getTime()) {
			LOGGER.debug("Confirmed that Data is available in DDP for the time range. Last DDP data available at "
					+ latestAvailableTimeInDb);
			return "";
		}
		return "DDP is NOT up to date. The last 'data available in DDP' time is less than or equal to 90 minutes before the end time."
				+ "(" + latestAvailableTimeInDb + " <= " + formattedTimeToCheck
				+ "). If you upgraded in last hour, this is expected."
				+ "Try HealthChecks again in an hour. Also check that DDP upload crontab HC is passing. Skipping all DDP queries";
	}
}
