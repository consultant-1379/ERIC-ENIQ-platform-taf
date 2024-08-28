package com.ericsson.eniq.taf.platform.utils;

import static com.ericsson.eniq.taf.platform.utils.Constants.DEFAULT_JAVA_DATE_FORMAT_STRING;
import static com.ericsson.eniq.taf.platform.utils.Constants.MT_DATE_FORMAT_STRING;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Util class to parse start/end times.
 */
public final class PlatformUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformUtil.class);

    private PlatformUtil() {
    }

    /**
     * Measures the amount of seconds between 2 LOGGER events.
     *
     * @param startLogLine
     *         the LOGGER line to start on
     * @param endLogLine
     *         the LOGGER line to end on
     *
     * @return amount of seconds between startLogLine and endLogLine
     */
    public static long measureTimeBetweenTwoEventsFromLog(final String startLogLine, final String endLogLine) {
        final Date start = getTimeForEvent(startLogLine, "START:", " ", 0, 1);
        final Date end = getTimeForEvent(endLogLine, "END:", " ", 0, 1);
        return getAmountOfSecondsToMeasure(start, end);
    }

    /**
     * Converts LOGGER line String to a Date object.
     *
     * @param eventString
     *         String to be appended to debug LOGGER to indicate what we are getting
     *         date of
     * @param lineOfTextInLog
     *         String to be parsed to Date
     * @param delimiter
     *         What to seperate the line by
     * @param timeIndex
     *         Index of split LOGGER line regarding where the time part is
     * @param dateIndex
     *         Index of split LOGGER line regarding where the date part is
     *
     * @return Date object for the event
     */
    public static Date getTimeForEvent(final String eventString, final String lineOfTextInLog, final String delimiter, final int timeIndex,
            final int dateIndex) {
        LOGGER.debug(lineOfTextInLog + ":Line to parse is " + eventString);
        final String time = eventString.split(delimiter)[timeIndex];
        final String date = eventString.split(delimiter)[dateIndex];
        final String timeForEvent = time + " " + date;
        LOGGER.debug(timeForEvent + ":timeForEvent ");
        return getDateFromString(timeForEvent);
    }

    /**
     * Convert a String to a Date object.
     *
     * @param dateToConvert date to convert
     *
     * @return Date Object
     */
    public static Date getDateFromString(final String dateToConvert) {
        String dateFormatOutput = MT_DATE_FORMAT_STRING;
        if (dateToConvert.contains("ST")) {
            dateFormatOutput = DEFAULT_JAVA_DATE_FORMAT_STRING;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(dateFormatOutput).parse(dateToConvert);
            LOGGER.debug("Date Object is " + date.toString() + "\n");
        } catch (final Exception e) {
            LOGGER.error("Failed to convert String to Date Object {}", e);
        }
        return date;
    }

    /**
     * Find amount of seconds between the 2 Date objects.
     *
     * @param startTime
     *         start time to measure
     * @param endTime
     *         end time to measure
     *
     * @return Amount of seconds between the 2 Dates
     * @throws RuntimeException
     *         if end Time is before start time
     */
    public static long getAmountOfSecondsToMeasure(final Date startTime, final Date endTime) throws RuntimeException {
        final long startEpoch = startTime.getTime();
        final long endEpoch = endTime.getTime();
        final long amountOfSecondsToMeasure = (endEpoch - startEpoch) / 1000;
        final boolean endTimeLaterFlag = startEpoch < endEpoch;
        LOGGER.debug("Start to finish took " + amountOfSecondsToMeasure + " seconds");
        if (!endTimeLaterFlag) {
            throw new RuntimeException("ERROR: Start time must be before end time");
        }
        return amountOfSecondsToMeasure;
    }

    /**
     * Find amount of seconds between the 2 String dates.
     *
     * @param startTime
     *         start time to measure
     * @param endTime
     *         end time to measure
     *
     * @return Amount of seconds between the 2 Dates
     * @throws RuntimeException
     *         if end Time is before start time
     * @throws RuntimeException
     *         if fails to parse start/end times
     */
    public static long getAmountOfSecondsToMeasure(final String startTime, final String endTime) throws RuntimeException {
        Date startTimeDate = null;
        Date endTimeDate = null;
        try {
            startTimeDate = new SimpleDateFormat(Constants.MT_DATE_FORMAT_STRING).parse(startTime);
            endTimeDate = new SimpleDateFormat(Constants.MT_DATE_FORMAT_STRING).parse(endTime);
        } catch (final ParseException e) {
            LOGGER.error("Failed to parse String Object {}", e);
        }
        return getAmountOfSecondsToMeasure(startTimeDate, endTimeDate);
    }

    /**
     * Formats a Date object to whatever format specified.
     *
     * @param date
     *         Date to be formatted
     * @param dateFormat
     *         The specified format for the date
     *
     * @return Formatted date
     */
    public static String getFormattedDateAsString(final Date date, final String dateFormat) {
        String dateString = date.toString();
        Date parsedDate = null;
        try {
            parsedDate = new SimpleDateFormat(dateFormat).parse(dateString);
        } catch (final ParseException e) {
            LOGGER.error("Parsing dateString failed " + e.getMessage());
        }

        final Format formatter = new SimpleDateFormat(dateFormat);

        if (parsedDate == null) {
            parsedDate = date;
        }
        dateString = formatter.format(parsedDate);
        return dateString;
    }

    /**
     * Converts Date string in MT format to another format.
     *
     * @param dateString
     *         date string to convert.
     * @param dateFormat
     *         format to convert to.
     *
     * @return string object.
     */
    public static String convertDateStringInMtFormatToAnotherFormat(final String dateString, final String dateFormat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(MT_DATE_FORMAT_STRING).parse(dateString);
        } catch (final Exception e) {
            LOGGER.error("Parsing dateString failed " + e.getMessage());
        }
        final Format formatter = new SimpleDateFormat(dateFormat);
        final String dateAsString = formatter.format(date);
        return dateAsString;
    }

    /**
     * gets start time in format {@link com.ericsson.eniq.taf.db.utils.oss.testware.ddcoperator.utils.Constants#MT_DATE_FORMAT_STRING}.
     *
     * @return startTime
     */
    public static String getstarttimeyyyyMmDdFormat() {
        final String endTime = getFormattedDateAsString(new Date(), MT_DATE_FORMAT_STRING);
        final Date endDate = getDateFromString(endTime);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.HOUR, -1);
        final Date oneHourBack = cal.getTime();
        final String startTime = getFormattedDateAsString(oneHourBack, MT_DATE_FORMAT_STRING);
        LOGGER.debug("Start time is now " + startTime);
        return startTime;
    }

    /**
     * if start time is null calculates the time from param paased in.
     * {@link com.ericsson.oss.testware.ddcoperator.utils.Constants#AMOUNT_OF_HOURS_TO_SEARCH_BETWEEN}
     *
     * @param lastDataAvailableInDdpTime
     *         The last date in ddp.
     *
     * @return start time.
     */
   
    /**
     * if endtime is null gets the last date from ddp.
     *
     * @param lastDataAvailableInDdpTime
     *         The last date in ddp.
     *
     * @return endtime.
     */
    public static String getEndTimeHandleNull(final String lastDataAvailableInDdpTime) {
        String endTime = Constants.END_TIME;
        LOGGER.debug("End time is " + endTime);
        if (endTime == null) {
            LOGGER.warn("No end time passed. Assuming you wish to run this check up to last DDP upload time and acting accordingly.");
            endTime = PlatformUtil.convertDateStringFromOneFormatToAnother(lastDataAvailableInDdpTime, MT_DATE_FORMAT_STRING);
            LOGGER.debug("End time is now " + endTime);
        }
        return endTime;
    }

    /**
     * Calculates the last even 15 minutes from date string passed in.
     *
     * @param dateString
     *         date string to start at.
     *
     * @return String object
     */
    public static String getLastEven15MinuteDate(final String dateString) {
        Date fromDate = getDateFromString(dateString);
        final Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(fromDate);
        final int minuteFrom = fromCal.get(Calendar.MINUTE);
        final int secondFrom = fromCal.get(Calendar.SECOND);
        LOGGER.debug("Date is " + dateString + ", minute is " + minuteFrom);
        final int amountOfMinutesToDropBack = minuteFrom % 15;
        final int amountOfSecondsToDropBack = secondFrom % 60;

        if (amountOfMinutesToDropBack != 0) {
            LOGGER.warn("Adjusting date minutes");
            fromCal.add(Calendar.MINUTE, -amountOfMinutesToDropBack);
            fromDate = fromCal.getTime();
        }

        if (amountOfSecondsToDropBack != 0) {
            LOGGER.warn("Adjusting date seconds");
            fromCal.add(Calendar.SECOND, -amountOfSecondsToDropBack);
            fromDate = fromCal.getTime();
        }
        LOGGER.debug("Date is now " + fromDate.toString());
        return fromDate.toString();
    }

    /**
     * Subtracts 15 minutes from the date as string passed in.
     *
     * @param dateString
     *         date string to get 15 minutes before hand.
     *
     * @return String object
     */
    public static String getTime15MinutesBefore(final String dateString) {
        Date date = getDateFromString(dateString);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -15);
        date = cal.getTime();
        return date.toString();
    }

    /**
     * Adds the specified number of units of time to the date string (whether its a positive or negative integer).
     *
     * @param dateString
     *         the date
     * @param timeUnitToAdjust
     *         the date to adjust
     * @param amountOfUnitsToAdjust
     *         the number of time units to adjust (either negative or positive number of units)
     *
     * @return the adjusted date string time
     */
    public static String adjustDate(final String dateString, final int amountOfUnitsToAdjust, final int timeUnitToAdjust) {
        return adjustDate(dateString, amountOfUnitsToAdjust, timeUnitToAdjust, MT_DATE_FORMAT_STRING);
    }

    /**
     * Adds the specified number of units of time to the date string (whether its a positive or negative integer).
     *
     * @param timeUnitToAdjust
     *         the date to adjust
     * @param dateString
     *         the date to adjust
     * @param amountOfUnitsToAdjust
     *         the number of time units to adjust (either negative or positive number of units)
     * @param dateFormat
     *         Format of the converted date
     *
     * @return the adjusted date string
     */
    public static String adjustDate(final String dateString, final int amountOfUnitsToAdjust, final int timeUnitToAdjust, final String dateFormat) {
        final Date date = getDateFromString(dateString);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(timeUnitToAdjust, amountOfUnitsToAdjust);
        return new SimpleDateFormat(dateFormat).format(cal.getTime());
    }

    /**
     * Converts a Date String from one format to the defaault.
     * {@link com.ericsson.eniq.taf.db.utils.oss.testware.ddcoperator.utils.Constants#DEFAULT_JAVA_DATE_FORMAT_STRING}
     *
     * @param dateString
     *         string to convert
     * @param otherFormat
     *         format to convert to.
     *
     * @return String Object.
     */
    public static String convertDateStringFromOneFormatToAnother(final String dateString, final String otherFormat) {
        return convertDateStringFromOneFormatToAnother(dateString, DEFAULT_JAVA_DATE_FORMAT_STRING, otherFormat);
    }

    /**
     * Converts a Date String from one format to another.
     *
     * @param dateString
     *         string to convert
     * @param formatPassingIn
     *         format the string currently in.
     * @param otherFormat
     *         format to convert to.
     *
     * @return String Object.
     */
    public static String convertDateStringFromOneFormatToAnother(final String dateString, final String formatPassingIn, final String otherFormat) {
        LOGGER.debug("Converting format of " + dateString + " from format " + formatPassingIn + " to format " + otherFormat);
        final SimpleDateFormat sdf = new SimpleDateFormat(formatPassingIn);
        final SimpleDateFormat output = new SimpleDateFormat(otherFormat);
        String formattedTime = "";
        try {
            final Date d = sdf.parse(dateString);
            formattedTime = output.format(d);
        } catch (final Exception e) {
            LOGGER.error("Failed to convert String fomrat {}", e);
        }
        LOGGER.debug("Date in new format is " + formattedTime);
        return formattedTime;
    }
}
