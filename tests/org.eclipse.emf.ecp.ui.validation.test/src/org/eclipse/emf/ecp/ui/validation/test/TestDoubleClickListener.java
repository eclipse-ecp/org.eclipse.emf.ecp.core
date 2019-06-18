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
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.validation.test;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;

/**
 * @author jfaltermeier
 *
 */
public class TestDoubleClickListener implements IDoubleClickListener {

	private static int hitCount;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	@Override
	public void doubleClick(DoubleClickEvent event) {
		hitCount++;
	}

	/**
	 * @return the hitCount
	 */
	public static int getHitCount() {
		return hitCount;
	}

	public static void resetHitCount() {
		TestDoubleClickListener.hitCount = 0;
	}

}
