/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * http://wiki.eclipse.org/JFace_Data_Binding/Realm
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.test.common;

import org.eclipse.core.databinding.observable.Realm;

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
}
