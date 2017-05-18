/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF;

/**
 * Default implementation of {@link SegmentConverterListResultEMF}.
 *
 * @author Lucas Koehler
 *
 */
public class SegmentConverterListResultImpl implements SegmentConverterListResultEMF {

	private final IEMFListProperty listProperty;
	private final EClass nextEClass;

	/**
	 * Creates a new {@link SegmentConverterListResultImpl}.
	 *
	 * @param listProperty The {@link IEMFListProperty}
	 * @param nextEClass The next {@link EClass}, may be <code>null</code>
	 */
	public SegmentConverterListResultImpl(IEMFListProperty listProperty, EClass nextEClass) {
		this.listProperty = listProperty;
		this.nextEClass = nextEClass;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF#getListProperty()
	 */
	@Override
	public IEMFListProperty getListProperty() {
		return listProperty;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterListResultEMF#getNextEClass()
	 */
	@Override
	public EClass getNextEClass() {
		return nextEClass;
	}

}
