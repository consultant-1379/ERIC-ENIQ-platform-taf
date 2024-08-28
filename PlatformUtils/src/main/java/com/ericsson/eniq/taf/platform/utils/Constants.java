package com.ericsson.eniq.taf.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.data.DataHandler;

/**
 * General constants shared among different classes in the project.
 */
public final class Constants {

    public static final String BACKSLASH_N = "\n";

    // DDC Sleep Interval
    public static final long SLEEP_INTERVAL = 30000; // 30 seconds

    // Date Format Strings
    public static final String MT_DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_JAVA_DATE_FORMAT_STRING = "EEE MMM dd HH:mm:ss z yyyy";

    // DDC SQL FOLDERS
    public static final String DDC_GENERAL = "ddc/general/";
    public static final String DDP_DB_GET_LAST_UPLOAD_TIME_QUERY = "ddp_db_get_last_upload_time.sql";
    public static final String DDP_URL = DataHandler.getAttribute("ddc.ddp.url").toString();

    public static final String RESULT_SET_CONTAINS_NO_MORE_ROWS_MESSAGE =
            "Data unavailable : Possible that DDP has not got data for this query on " + "its web page";

    public static final String START_TIME = DataHandler.getAttribute("start_time").toString();
    public static final String END_TIME = DataHandler.getAttribute("end_time").toString();
    //DDC DB resources
    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.class);

    private Constants() {
    }
    /**
     * Database keys.
     */
    public static final class DbKeys {
        public static final String SITE_NAME = "site_name";
        public static final String START_TIME_IN_YYYY_MM_DD_FORMAT = "start_time_in_YYYY_MM_DD_format";
        public static final String UPGRADE_THRESHOLD_IN_MINUTES = "upgrade_threshold_in_minutes";
        public static final String NODE_UPGRADE_THRESHOLD_IN_MINUTES = "node_upgrade_threshold_in_minutes";
        public static final String MAX_CPU_THRESHOLD_PERCENTAGE = "max_cpu_threshold_percentage";
        public static final String AVG_CPU_THRESHOLD_PERCENTAGE = "avg_cpu_threshold_percentage";
        public static final String AVG_IO_WAIT = "avg_io_wait";
        public static final String MAX_IO_WAIT = "max_io_wait";
        public static final String PM_MAX_DURATIONS_THRESHOLD = "pm_max_durations_threshold";
        public static final String PM_AVG_DURATIONS_THRESHOLD = "pm_avg_durations_threshold";
        public static final String MAX_MESSAGE_COUNT_LIMIT = "max_message_count_limit";
        public static final String MAX_TOPIC_MESSAGE_COUNT = "max_topic_message_count";
        public static final String EXPECTED_NOTIFICATIONS_PER_SECOND = "expected_notifications_per_second";
        public static final String NUMBER_NODES_IN_WORKLOAD_POOL = "number_of_nodes_in_workload_pool";
        public static final String CM_EXPORT_FORMAT = "cm_export_format";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String SECONDS_BETWEEN_START_AND_FINISH = "SECONDS_BETWEEN_START_AND_FINISH";

        private DbKeys() {
        }
    }

    /**
     * Database host info.
     */
    public static final class DWhrepHostINfo {
        public static final String DWHREP_DB_HOST = DataHandler.getAttribute("dwhrep.db.hostname").toString();
        public static final String DWHREP_PORT_NUMBER = DataHandler.getAttribute("dwhrep.db.port").toString();
        public static final String DWHREP_DB_NAME = DataHandler.getAttribute("dwhrep.db.dbname").toString();
        public static final String DWHREP_USERNAME = DataHandler.getAttribute("dwhrep.db.username").toString();
        public static final String DWHREP_PASSWORD = DataHandler.getAttribute("dwhrep.db.password").toString();

        private DWhrepHostINfo() {
        }
    }
}
