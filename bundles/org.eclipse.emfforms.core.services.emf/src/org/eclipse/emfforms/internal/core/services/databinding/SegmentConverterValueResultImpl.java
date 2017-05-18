/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF;

/**
 * Default implementation of {@link SegmentConverterValueResultEMF}.
 * 
 * @author Lucas Koehler
 *
 */
public class SegmentConverterValueResultImpl implements SegmentConverterValueResultEMF {
	private final IEMFValueProperty valueProperty;
	private final EClass nextEClass;

	/**
	 * Creates a new {@link SegmentConverterValueResultImpl}.
	 *
	 * @param valueProperty The {@link IEMFValueProperty}
	 * @param nextEClass The next {@link EClass}, may be <code>null</code>
	 */
	public SegmentConverterValueResultImpl(IEMFValueProperty valueProperty, EClass nextEClass) {
		this.valueProperty = valueProperty;
		this.nextEClass = nextEClass;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF#getValueProperty()
	 */
	@Override
	public IEMFValueProperty getValueProperty() {
		return valueProperty;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.emf.SegmentConverterValueResultEMF#getNextEClass()
	 */
	@Override
	public EClass getNextEClass() {
		return nextEClass;
	}

}
