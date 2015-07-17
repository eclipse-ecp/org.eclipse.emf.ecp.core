/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecp.internal.core.Activator;
import org.eclipse.emf.ecp.spi.core.util.DisposeException;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable;

/**
 * @author Eike Stepper
 */
public class Disposable implements ECPDisposable {
	private final List<DisposeListener> listeners = new ArrayList<DisposeListener>();

	private final ECPDisposable delegate;

	private boolean disposed;

	/**
	 * Constructor of an disposable object.
	 *
	 * @param delegate the delegate to call
	 */
	public Disposable(ECPDisposable delegate) {
		this.delegate = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public final synchronized boolean isDisposed() {
		return disposed;
	}

	/** {@inheritDoc} */
	@Override
	public final void dispose() {
		DisposeListener[] array = null;

		synchronized (this) {
			if (!disposed) {
				doDispose();
				disposed = true;
				array = listeners.toArray(new DisposeListener[listeners.size()]);
			}
		}

		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				final DisposeListener listener = array[i];

				try {
					listener.disposed(delegate);
				} catch (final DisposeException ex) {
					Activator.log(ex);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public final synchronized void addDisposeListener(DisposeListener listener) {
		listeners.add(listener);
	}

	/** {@inheritDoc} */
	@Override
	public final synchronized void removeDisposeListener(DisposeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * this method is called so that each implementation can execute specific dispose methods.
	 */
	protected void doDispose() {
	}
}
