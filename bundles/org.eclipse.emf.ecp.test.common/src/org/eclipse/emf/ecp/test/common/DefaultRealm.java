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
 * http://wiki.eclipse.org/JFace_Data_Binding/Realm
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emf.ecp.test.common;

import org.eclipse.core.databinding.observable.Realm;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Simple realm implementation that will set itself as default when constructed. Invoke {@link #dispose()} to remove the
 * realm from being the default. Does not support asyncExec(...).
 *
 * @see <a href="http://wiki.eclipse.org/JFace_Data_Binding/Realm">http://wiki.eclipse.org/JFace_Data_Binding/Realm</a>
 * @author Lucas Koehler
 */
public class DefaultRealm extends Realm {
	private final Realm previousRealm;

	/**
	 * Create a new instance of {@link DefaultRealm}.
	 */
	public DefaultRealm() {
		previousRealm = super.setDefault(this);
	}

	/**
	 * @return always returns true
	 */
	@Override
	public boolean isCurrent() {
		return true;
	}

	@Override
	protected void syncExec(Runnable runnable) {
		runnable.run();
	}

	/**
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void asyncExec(Runnable runnable) {
		throw new UnsupportedOperationException("asyncExec is unsupported"); //$NON-NLS-1$
	}

	/**
	 * Removes the realm from being the current and sets the previous realm to the default.
	 */
	public void dispose() {
		if (getDefault() == this) {
			setDefault(previousRealm);
		}
	}

	/**
	 * Obtain a JUnit rule that ensures a {@link DefaultRealm} during its execution.
	 *
	 * @return a default realm rule
	 *
	 * @since 1.22
	 */
	public static TestRule rule() {
		return new TestWatcher() {
			private DefaultRealm realm;

			@Override
			protected void starting(Description description) {
				realm = new DefaultRealm();
			}

			@Override
			protected void finished(Description description) {
				realm.dispose();
				realm = null;
			}
		};
	}

}
