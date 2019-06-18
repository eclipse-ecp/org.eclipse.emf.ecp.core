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
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

/**
 * Utility methods for testing the reference service strategies.
 *
 * @author Lucas Koehler
 *
 */
final class StrategyTestUtil {

	private StrategyTestUtil() {
		// utility class
	}

	/**
	 * Waits until the {@link Shell} with the given title is active. The active shell is checked every 100 milli seconds
	 * and the wait times out after 10 seconds.
	 *
	 * @param title The title of the {@link Shell}.
	 */
	static void waitForShell(String title) {
		new SWTBot().waitUntil(Conditions.shellIsActive(title), 10000, 100);
	}

	/**
	 * Waits until the element at index 0 of the given array is not null. The check is repeated every 100 milli seconds
	 * and the wait times out after 20 seconds.
	 *
	 * @param array The array which will contain the wanted element at index 0
	 * @return the result if the wait succeeds
	 */
	static <T> T waitUntilNotNull(T[] array) {
		new SWTBot().waitUntil(new DefaultCondition() {

			@Override
			public boolean test() throws Exception {
				return array[0] != null;
			}

			@Override
			public String getFailureMessage() {
				return "Strategy did not return!"; //$NON-NLS-1$
			}
		}, 20000, 100);
		return array[0];
	}
}
