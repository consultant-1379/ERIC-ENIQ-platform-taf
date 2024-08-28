package com.ericsson.eniq.taf.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.datasource.DataRecord;
import com.ericsson.cifwk.taf.datasource.TestDataSource;
import com.ericsson.cifwk.taf.datasource.TestDataSourceFactory;


/**
 * Provides parsing logic for converting the resultSet to TestDataSource DataRecord.
 */
public final class SqlParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlParser.class);

    private SqlParser(){
    }

    /**
     * parse the Result set to return a TestDatasource.
     * @param resultSet
     *      to parse.
     * @return TestDataSource DataRecord
     */
    public static TestDataSource<DataRecord> parse(final ResultSet resultSet) {
        final ArrayList<Map<String, Object>> dataRecords = new ArrayList<Map<String, Object>>();

        try {
            final ResultSetMetaData rsmd = resultSet.getMetaData();
            final int numColumns = rsmd.getColumnCount();
            LOGGER.trace("No of Columns:{}", numColumns);
            while (resultSet.next()) {
                final Map<String, Object> datarecord = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= numColumns; i++) {
                    final String columnName = rsmd.getColumnLabel(i);
                    final String value = resultSet.getString(columnName);
                    LOGGER.trace("Column Name:{}", columnName);
                    LOGGER.trace("value:{}", value);
                    datarecord.put(columnName, value);
                }
                dataRecords.add(datarecord);
            }
        } catch (final SQLException e) {
            LOGGER.error("Failed to parse result set {}", e);
        }
        return TestDataSourceFactory.createDataSource(dataRecords);
    }
}
