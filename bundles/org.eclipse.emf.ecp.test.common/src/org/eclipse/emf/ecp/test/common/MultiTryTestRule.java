/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.test.common;

import java.text.MessageFormat;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * This JUnit rule allows to try unit tests a given number of times. If at least one of the tries succeeds, the test
 * counts as passed. Every try executes all {@link org.junit.Before Before} and {@link org.junit.After After} methods
 * of the test. However, it does not re-execute {@link org.junit.BeforeClass BeforeClass} and
 * {@link org.junit.AfterClass AfterClass} methods.
 *
 * @author Lucas Koehler
 *
 */
public class MultiTryTestRule implements TestRule {

	private final int maxTries;
	private boolean executeAll = true;

	/**
	 * Create a new {@link MultiTryTestRule} which allows the given number of tries for all test cases of a test class.
	 * The number of tries is never less than 1 even if the given number of tries is lower.
	 *
	 * @param maxTries The maximum number of tries
	 */
	public MultiTryTestRule(int maxTries) {
		this.maxTries = Integer.max(1, maxTries);
	}

	/**
	 * Create a new {@link MultiTryTestRule} which allows the given number of tries for test cases of a test class.
	 * The number of tries is never less than 1 even if the given number of tries is lower.
	 *
	 * @param maxTries The maximum number of tries
	 * @param executeAll <code>true</code> if all test cases of the test class should use multiple tries.
	 *            <code>false</code> if only test cases annotated with {@link MultiTry} should use multiple tries.
	 */
	public MultiTryTestRule(int maxTries, boolean executeAll) {
		this.maxTries = Integer.max(1, maxTries);
		this.executeAll = executeAll;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		final MultiTry multiTry = description.getAnnotation(MultiTry.class);
		if (!executeAll && multiTry == null) {
			// no annotation and not all tests are multi tried => no multi try for this test
			return base;
		}

		final int tries = Integer.max(maxTries, multiTry == null ? 1 : multiTry.maxTries());
		return new Statement() {

			// CHECKSTYLE.OFF: IllegalCatch
			@Override
			public void evaluate() throws Throwable {
				Throwable lastCaughtThrowable = null;
				for (int i = 1; i <= tries; i++) {
					try {
						base.evaluate();
						return;
					} catch (final Throwable t) {
						System.err
							.println(MessageFormat.format("{0}: Try {1} failed.", description.getDisplayName(), i)); //$NON-NLS-1$
						lastCaughtThrowable = t;
					}
				}
				System.err.println(MessageFormat.format("{0} finally failed after {1} tries.", //$NON-NLS-1$
					description.getDisplayName(), tries));
				throw lastCaughtThrowable;
			}
			// CHECKSTYLE.ON: IllegalCatch
		};
	}
}
