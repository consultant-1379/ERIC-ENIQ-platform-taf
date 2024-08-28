package com.ericsson.eniq.taf.installation.test.steps;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.cifwk.taf.annotations.TestStep;
import com.ericsson.cifwk.taf.utils.FileFinder;
import com.ericsson.eniq.taf.installation.test.operators.DclipOperators;

/**
 * To verify all the jar files are extracted under dclib path
 * 
 * @author xsounpk
 *
 */
public class DclipSteps {
	private static Logger logger = LoggerFactory.getLogger(DclipSteps.class);
	private static final String LATEST_LIBS_RSTATE = "cd /eniq/sw/platform/;ls | grep libs-R* | tail -1";
	private static final String JAR_PATH_FILE = "data/JarFile.csv";
	List<String> jarNotFound = new ArrayList<String>();

	@Inject
	private Provider<DclipOperators> provider;

	@TestStep(id = StepIds.VERIFY_JAR)
	public void verifyJars() throws FileNotFoundException {
		final DclipOperators dclipOperators = provider.get();

		final String latestLibsRstate = dclipOperators.executeCommand(LATEST_LIBS_RSTATE);
		final String JAR_PATH = "cd /eniq/sw/platform/" + latestLibsRstate
				+ "/dclib/;ls | grep jar > /eniq/sw/platform/" + latestLibsRstate + "/jar.txt";
		final String JAR_EXISTS = dclipOperators.executeCommand(JAR_PATH);
		final String JAR_PATH_SERVER = "/eniq/sw/platform/" + latestLibsRstate + "/jar.txt";
		File jarFile = new File(FileFinder.findFile(JAR_PATH_FILE).get(0));
		if (jarFile.exists()) {
			String line = "";
			try (BufferedReader br = new BufferedReader(new FileReader(jarFile))) {
				while ((line = br.readLine()) != null) {
					logger.info("JAR : " + line);
					final String JAR_FIND = "cd /eniq/sw/platform/" + latestLibsRstate + "/dclib/;ls | grep '" + line
							+ "' /eniq/sw/platform/" + latestLibsRstate + "/jar.txt";
					final String JAR_CHECK = dclipOperators.executeCommand(JAR_FIND);
					if (!JAR_CHECK.equals(line)) {
						jarNotFound.add(line);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			final String JAR_RM_FILE = "cd /eniq/sw/platform/" + latestLibsRstate + "/; rm -rf jar.txt";
			final String JAR_RM = dclipOperators.executeCommand(JAR_RM_FILE);
			final String JAR_FILE_COUNT = "cd /eniq/sw/platform/" + latestLibsRstate
					+ "/dclib/; ls -lrt | grep .jar | wc -l";
			final String JAR_count = dclipOperators.executeCommand(JAR_FILE_COUNT);

			assertFalse(JAR_EXISTS.length() > 0, ".jar not created");
			assertTrue(JAR_count.equals("32"), "Expected : 32 jar files extracted under dclib folder /eniq/sw/platform/"
					+ latestLibsRstate + "/dclib/ but found : " + JAR_count);
			if (!jarNotFound.isEmpty()) {
				assertFalse(!jarNotFound.isEmpty(), jarNotFound.toString() + " not found");
			}
		} else {
			throw new FileNotFoundException("Unable to find the file : " + jarFile);
		}
	}

	public static class StepIds {
		public static final String VERIFY_JAR = "To verify all the jar files are extracted under dclib path.";

		private StepIds() {
		}
	}
}
