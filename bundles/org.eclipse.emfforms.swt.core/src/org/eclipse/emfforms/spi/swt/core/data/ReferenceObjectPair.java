/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.data;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * Data class to pair {@link VDomainModelReference}s with {@link EObject}s.
 *
 * @author Stefan Dirix
 * @since 1.8
 *
 */
public class ReferenceObjectPair {
	private final VDomainModelReference vReference;
	private final EObject eObject;

	/**
	 * Constructor.
	 *
	 * @param vReference
	 *            The {@link VDomainModelReference}.
	 * @param eObject
	 *            The {@link EObject}.
	 */
	public ReferenceObjectPair(VDomainModelReference vReference, EObject eObject) {
		this.vReference = vReference;
		this.eObject = eObject;
	}

	/**
	 * @return The {@link VDomainModelReference} for the given {@link EObject}.
	 */
	public VDomainModelReference getVDomainModelReference() {
		return vReference;
	}

	/**
	 * @return The {@link EObject} for the given {@link VDomainModelReference}.
	 */
	public EObject getEObject() {
		return eObject;
	}
}
