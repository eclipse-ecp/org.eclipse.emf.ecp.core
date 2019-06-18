/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import java.util.Optional;

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

	@Override
	public IEMFValueProperty getValueProperty() {
		return valueProperty;
	}

	@Override
	public Optional<EClass> getNextEClass() {
		return Optional.ofNullable(nextEClass);
	}

}
