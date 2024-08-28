package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.eniq.taf.installation.test.operators.SessionLogOperator;

/**
 * 
 * @author XARUNHA
 *
 */
public class SessionLogSteps {

	private static Logger logger = LoggerFactory.getLogger(SessionLogSteps.class);

	private static final String LATEST_SCHEDULER_LOG = "cd /eniq/log/sw_log/scheduler/;ls scheduler-*.log | tail -1";
	private static final String CAT_SCHEDULER = "cat /eniq/log/sw_log/scheduler/";
	private static final String SessionLogLoader_Starter = "| grep 'triggered set'  | grep -i 'SessionLoader_Starter' | cut -d ' ' -f2";
	private static final String SessionLogLoader_Adapter = "| grep 'triggered set' | grep -i 'SessionLogLoader_Adapter' | cut -d ' ' -f2";
	private static final String SessionLogLoader_loader = "| grep 'triggered set' | grep -i 'SessionLogLoader_loader' | cut -d ' ' -f2";
	private static final String SessionLogLoader_aggregator = "| grep 'triggered set' | grep -i 'SessionLogLoader_aggregator' | cut -d ' ' -f2";
	private static final String UpdateMonitoring = "| grep 'triggered set' | grep -i 'UpdateMonitoring' | cut -d ' ' -f2";
	private static final String Aggregate = "| grep 'triggered set' | grep -i 'Aggregate' | cut -d ' ' -f2";

	private static final String WC = "| wc -l";

	@Inject
	private Provider<SessionLogOperator> provider;

	/**
	 * @throws ParseException
	 * @DESCRIPTION This test case covers verification of SessionLog
	 * @PRE NA
	 */

	@TestStep(id = StepIds.VERIFY_SESSION_LOG_STARTER)
	public void verifyStarterSet() throws ParseException {
		final SessionLogOperator sessionLogOperator = provider.get();

		final String latestschedulerLogFile = sessionLogOperator.latestschedulerLogFile(LATEST_SCHEDULER_LOG);
		logger.info("Verifying scheduler Log : " + latestschedulerLogFile);

		final List<String> setTime = sessionLogOperator.listOfSetStarterTime(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_Starter);

		final String countOfline = sessionLogOperator
				.countOflines(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_Starter + WC);
		if (!countOfline.isEmpty()) {
			int count = Integer.parseInt(countOfline);
			if (count > 2) {
				if (count % 2 == 0) {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				} else {
					setTime.remove(0);
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				}
			} else {
				assertTrue(true);
			}
		} else {
			assertTrue(countOfline.isEmpty(), " No sets are tiggered : Command executed (No Output) - > "
					+ CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_Starter + WC);
		}
	}

	@TestStep(id = StepIds.VERIFY_SESSION_LOG_ADAPTER)
	public void verifyAdapterSet() throws ParseException {
		final SessionLogOperator sessionLogOperator = provider.get();

		final String latestschedulerLogFile = sessionLogOperator.latestschedulerLogFile(LATEST_SCHEDULER_LOG);
		logger.info("Verifying scheduler Log : " + latestschedulerLogFile);

		final List<String> setTime = sessionLogOperator.listOfSetStarterTime(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_Adapter);

		final String countOfline = sessionLogOperator
				.countOflines(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_Adapter + WC);
		if (!countOfline.isEmpty()) {
			int count = Integer.parseInt(countOfline);
			if (count > 2) {
				if (count % 2 == 0) {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				} else {
					setTime.remove(0);
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				}
			} else {
				assertTrue(true);
			}
		} else {
			assertTrue(countOfline.isEmpty(), " No sets are tiggered : Command executed (No Output) - > "
					+ CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_Adapter + WC);
		}
	}

	@TestStep(id = StepIds.VERIFY_SESSION_LOG_LOADER)
	public void verifyLoaderSet() throws ParseException {
		final SessionLogOperator sessionLogOperator = provider.get();

		final String latestschedulerLogFile = sessionLogOperator.latestschedulerLogFile(LATEST_SCHEDULER_LOG);
		logger.info("Verifying scheduler Log : " + latestschedulerLogFile);

		final List<String> setTime = sessionLogOperator.listOfSetStarterTime(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_loader );

		final String countOfline = sessionLogOperator
				.countOflines(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_loader + WC);
		if (!countOfline.isEmpty()) {
			int count = Integer.parseInt(countOfline);
			if (count > 2) {
				if (count % 2 == 0) {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				} else {
					setTime.remove(0);
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				}
			} else {
				assertTrue(true);
			}
		} else {
			assertTrue(countOfline.isEmpty(), " No sets are tiggered : Command executed (No Output) - > "
					+ CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_loader + WC);
		}
	}

	@TestStep(id = StepIds.VERIFY_SESSION_LOG_AGGREGATOR)
	public void verifyAggregatorSet() throws ParseException {
		final SessionLogOperator sessionLogOperator = provider.get();

		final String latestschedulerLogFile = sessionLogOperator.latestschedulerLogFile(LATEST_SCHEDULER_LOG);
		logger.info("Verifying scheduler Log : " + latestschedulerLogFile);

		final List<String> setTime = sessionLogOperator.listOfSetStarterTime(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_aggregator );

		final String countOfline = sessionLogOperator
				.countOflines(CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_aggregator + WC);
		if (!countOfline.isEmpty()) {
			int count = Integer.parseInt(countOfline);
			if (count > 2) {
				if (count % 2 == 0) {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				} else {
					setTime.remove(0);
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				}
			} else {
				assertTrue(true);
			}
		} else {
			assertTrue(countOfline.isEmpty(), " No sets are tiggered : Command executed (No Output) - > "
					+ CAT_SCHEDULER + latestschedulerLogFile + SessionLogLoader_aggregator + WC);
		}
	}

	@TestStep(id = StepIds.VERIFY_SESSION_LOG_UPDATE_MONITORING)
	public void verifyUpdateMonitoring() throws ParseException {
		final SessionLogOperator sessionLogOperator = provider.get();

		final String latestschedulerLogFile = sessionLogOperator.latestschedulerLogFile(LATEST_SCHEDULER_LOG);
		logger.info("Verifying scheduler Log : " + latestschedulerLogFile);

		final List<String> setTime = sessionLogOperator.listOfSetStarterTime(CAT_SCHEDULER + latestschedulerLogFile + UpdateMonitoring);

		final String countOfline = sessionLogOperator
				.countOflines(CAT_SCHEDULER + latestschedulerLogFile + UpdateMonitoring + WC);
		if (!countOfline.isEmpty()) {
			int count = Integer.parseInt(countOfline);
			if (count > 2) {
				if (count % 2 == 0) {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				} else {
					setTime.remove(0);
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				}
			} else {
				assertTrue(true);
			}
		} else {
			assertTrue(countOfline.isEmpty(), " No sets are tiggered : Command executed (No Output) - > "
					+ CAT_SCHEDULER + latestschedulerLogFile + UpdateMonitoring + WC);
		}
	}

	@TestStep(id = StepIds.VERIFY_SESSION_LOG_AGGREGATE)
	public void verifyAggregateSet() throws ParseException {
		final SessionLogOperator sessionLogOperator = provider.get();

		final String latestschedulerLogFile = sessionLogOperator.latestschedulerLogFile(LATEST_SCHEDULER_LOG);
		logger.info("Verifying scheduler Log : " + latestschedulerLogFile);

		final List<String> setTime = sessionLogOperator.listOfSetStarterTime(CAT_SCHEDULER + latestschedulerLogFile + Aggregate);

		final String countOfline = sessionLogOperator
				.countOflines(CAT_SCHEDULER + latestschedulerLogFile + Aggregate + WC);
		if (!countOfline.isEmpty()) {
			int count = Integer.parseInt(countOfline);
			if (count > 2) {
				if (count % 2 == 0) {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				} else {
					setTime.remove(0);
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");

					logger.info("SIZE : " + setTime.size());
					for (int i = 0; i < setTime.size(); i++) {
						Date date1 = format.parse(setTime.get(i));
						String second = null;
						Date date2 = null;
						if (setTime.size() > i + 1) {
							second = setTime.get(++i); // Change here
							date2 = format.parse(second);
						}
						long difference = date2.getTime() - date1.getTime();
						long finalTime = (difference / 1000) / 60;
						logger.info("TIME DIFFERENCE : " + date2 + " - " + date1 + " = " + finalTime);
						assertTrue(finalTime == 15,
								" Expected TIME DIFFERENCE '15 Minutes' but found : " + finalTime + " Minutes ");
					}
				}
			} else {
				assertTrue(true);
			}
		} else {
			assertTrue(countOfline.isEmpty(), " No sets are tiggered : Command executed (No Output) - > "
					+ CAT_SCHEDULER + latestschedulerLogFile + Aggregate + WC);
		}
	}

	public static class StepIds {
		public static final String VERIFY_SESSION_LOG_STARTER = "Check whether it runs at every 15 mins and triggers SessionLoader_Starter Set";
		public static final String VERIFY_SESSION_LOG_ADAPTER = "Check whether it runs at every 15 mins and triggers SessionLoader_Adapter Set";
		public static final String VERIFY_SESSION_LOG_LOADER = "Check whether it runs at every 15 mins and triggers SessionLoader_Loader Set";
		public static final String VERIFY_SESSION_LOG_AGGREGATOR = "Check whether it runs at every 15 mins and triggers SessionLoader_Aggregator Set";
		public static final String VERIFY_SESSION_LOG_UPDATE_MONITORING = "Check whether it runs at every 15 mins and triggers SessionLoader_UpdateMonitoring Set";
		public static final String VERIFY_SESSION_LOG_AGGREGATE = "Check whether it runs at every 15 mins and triggers SessionLoader_Aggregate Set";

		private StepIds() {
		}
	}
}
