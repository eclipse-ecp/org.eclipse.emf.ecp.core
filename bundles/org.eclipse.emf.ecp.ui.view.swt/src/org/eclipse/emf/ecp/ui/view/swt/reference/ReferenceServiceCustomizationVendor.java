/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * A partial implementation of vendor services for
 * {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * <em>customization strategies</em> in the bazaar. Clients may subclass to suit.
 *
 * @param <T> the type of reference service customization that I provide
 *
 * @author Christian W. Damus
 *
 * @since 1.16
 */
public class ReferenceServiceCustomizationVendor<T> implements Vendor<T> {

	private double defaultBid;

	/**
	 * Initializes me .
	 */
	public ReferenceServiceCustomizationVendor() {
		super();
	}

	/**
	 * Bid on provision for the given {@owner} and {@code reference}. This implementation
	 * returns the default bid, which is the OSGi configured ranking of this service,
	 * if the {@link #handles(EObject, EReference)} method return {@code true}.
	 *
	 * @param owner the owner of a reference being edited by the <em>Reference Service</em>
	 * @param reference the reference being edited
	 *
	 * @return the bid, or {@code null} to opt out
	 *
	 * @see #handles(EObject, EReference)
	 */
	@Bid
	public Double bid(EObject owner, EReference reference) {
		if (handles(owner, reference)) {
			return defaultBid;
		}
		return null;
	}

	/**
	 * Queries whether I should bid on the given {@code owner} and {@code reference}.
	 * The default implementation just returns {@code true}; subclasses should override.
	 *
	 * @param owner the owner of a reference being edited by the <em>Reference Service</em>
	 * @param reference the reference being edited
	 *
	 * @return whether I should bid
	 */
	protected boolean handles(EObject owner, EReference reference) {
		return true;
	}
}
