package com.ericsson.eniq.taf.platform.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import com.ericsson.cifwk.taf.data.DataHandler;

public class MyTransformer implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		/*
		 * if ("invoke".equals(testMethod.getName())) {
		 * annotation.setInvocationCount(5); }
		 */
		String testGroup = System.getProperty("test.group");
		System.out.println("testGroup::::" + testGroup +"   testName:::"+annotation.getTestName());
		if (testGroup != null && testGroup.length() > 0) {
			String groups = DataHandler.getAttribute(annotation.getTestName()).toString();
			System.out.println("groups:::"+groups);
			String[] gs = groups.split(":");
			boolean disable = true;
			for (String tg : gs) {
				System.out.println("groups running:::" + tg);
				if (tg.equalsIgnoreCase(testGroup)) {
					disable = false;
					break;
				}
			}
			if (disable) {
				System.out.println("disable Test:::" + testMethod.getName());
				annotation.setEnabled(false);
			}
		}
	}

}