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
import org.eclipse.emfforms.bazaar.Preconditions;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * @author jonas
 *
 */
@Preconditions(preconditions = {
	@Precondition(key = VendorWithTwoPreconditions.KEY1, value = VendorWithTwoPreconditions.VALUE1),
	@Precondition(key = VendorWithTwoPreconditions.KEY2, value = VendorWithTwoPreconditions.VALUE2) })
public class VendorWithTwoPreconditions implements Vendor<MyProduct> {

	private final int priority;

	/**
	 * Constructor specifying a priority
	 *
	 * @param priority the priority returned on {@link Bid}
	 */
	public VendorWithTwoPreconditions(int priority) {
		this.priority = priority;
	}

	/**
	 *
	 */
	public VendorWithTwoPreconditions() {
		priority = 0;
	}

	static final String VALUE1 = "value"; //$NON-NLS-1$
	static final String KEY1 = "key"; //$NON-NLS-1$
	static final String VALUE2 = "value1"; //$NON-NLS-1$
	static final String KEY2 = "key1"; //$NON-NLS-1$

	@Bid
	public double bid() {
		return priority;
	}
}
