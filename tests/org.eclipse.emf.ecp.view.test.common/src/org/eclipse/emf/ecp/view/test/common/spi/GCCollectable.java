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
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.test.common.spi;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Convenience class for expecting objects to be garbage collected. Because GC
 * is non-deterministic there's no time constrained guarantee, when
 * {@code isCollectable} returns true.
 * 
 * @author emueller
 */
public class GCCollectable {

	private final ReferenceQueue<Object> queue;
	private final PhantomReference<Object> phantomRef;

	private boolean result;
	private GCPhantomThread t;

	/**
	 * Constructor.
	 * 
	 * @param obj
	 *            the object that is supposed to be finalized
	 */
	public GCCollectable(Object obj) {
		queue = new ReferenceQueue<Object>();
		phantomRef = new PhantomReference<Object>(obj, getQueue());
		setResult(false);
		t = new GCPhantomThread(queue, phantomRef);
		t.start();
	}

	/**
	 * Checks whether the the object contained by the {@link GCCollectable} may
	 * be finalized.
	 * 
	 * @return {@code true}, if the contained object may be finalized,
	 *         {@code false} otherwise
	 */
	public boolean isCollectable() {
		try {
			t.join();
		} catch (InterruptedException e) {
			return false;
		}
		return t.didCollectReference();
	}

	public synchronized ReferenceQueue<Object> getQueue() {
		return queue;
	}

	public synchronized boolean isResult() {
		return result;
	}

	public synchronized void setResult(boolean result) {
		this.result = result;
	}

	public synchronized PhantomReference<Object> getPhantomRef() {
		return phantomRef;
	}

}
