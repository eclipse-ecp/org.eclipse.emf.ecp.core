/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.test.fx;

import org.eclipse.core.databinding.observable.Realm;

/**
 * Simple realm implementation that will set itself as default when constructed. Invoke {@link #dispose()} to remove the
 * realm from being the default. Does not support asyncExec(...).
 *
 * @author Lucas
 */
public class DefaultRealm extends Realm {
	private final Realm previousRealm;

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
}
