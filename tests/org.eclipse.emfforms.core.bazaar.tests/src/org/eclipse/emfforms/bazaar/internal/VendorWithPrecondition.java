/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Precondition;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * @author jonas
 *
 */
@Precondition(key = VendorWithPrecondition.KEY, value = VendorWithPrecondition.VALUE)
public class VendorWithPrecondition implements Vendor<MyProduct> {

	private final int priority;

	/**
	 *
	 */
	public VendorWithPrecondition() {
		priority = 0;
	}

	/**
	 *
	 */
	public VendorWithPrecondition(int priority) {
		this.priority = priority;

	}

	static final String VALUE = "value"; //$NON-NLS-1$
	static final String KEY = "key"; //$NON-NLS-1$

	@Bid
	public double bid() {
		return priority;
	}

}
