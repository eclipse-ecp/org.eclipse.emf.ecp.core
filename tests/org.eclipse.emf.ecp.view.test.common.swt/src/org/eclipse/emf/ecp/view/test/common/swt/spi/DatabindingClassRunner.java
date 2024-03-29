/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.test.common.swt.spi;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.swt.widgets.Display;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Custom junit class runner to setup Realm.
 *
 * @author jfaltermeier
 *
 */
public class DatabindingClassRunner extends BlockJUnit4ClassRunner {

	/**
	 * Creates a DatabindingClassRunner providing a {@link Realm} to run {@code klass}.
	 *
	 * @param klass the test class to be executed
	 * @throws InitializationError if the test class is malformed.
	 */
	public DatabindingClassRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	public void run(final RunNotifier notifier) {
		final Display display = Display.getDefault();
		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				DatabindingClassRunner.super.run(notifier);
			}
		});
	}
}
