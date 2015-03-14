/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.databinding.index;

import java.util.List;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;

/**
 * An implementation of {@link DomainModelReferenceConverter} that converts {@link VIndexDomainModelReference
 * VIndexDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
public class IndexDomainModelReferenceConverter implements DomainModelReferenceConverter {

	private EMFFormsDatabinding emfFormsDatabinding;

	/**
	 * Sets the {@link EMFFormsDatabinding}.
	 *
	 * @param emfFormsDatabinding the emfFormsDatabinding to set
	 */
	protected void setEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * Unsets the {@link EMFFormsDatabinding}.
	 *
	 * @param emfFormsDatabinding the emfFormsDatabinding to unset
	 */
	protected void unsetEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
		this.emfFormsDatabinding = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#isApplicable(VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (domainModelReference instanceof VIndexDomainModelReference) {
			return 10d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(VDomainModelReference)
	 */
	@Override
	public IValueProperty convertToValueProperty(VDomainModelReference domainModelReference)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VIndexDomainModelReference."); //$NON-NLS-1$
		}

		final VIndexDomainModelReference indexReference = VIndexDomainModelReference.class.cast(domainModelReference);
		if (indexReference.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VIndexDomainModelReference must not be null."); //$NON-NLS-1$
		}

		final List<EReference> referencePath = indexReference.getDomainModelEReferencePath();
		IValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = new EMFIndexedValueProperty(indexReference.getIndex(),
				indexReference.getDomainModelEFeature());
		} else {
			IEMFValueProperty emfValueProperty = EMFProperties.value(referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}
			final EMFIndexedValueProperty indexedValueProperty = new EMFIndexedValueProperty(indexReference.getIndex(),
				indexReference.getDomainModelEFeature());
			valueProperty = emfValueProperty.value(indexedValueProperty);
		}

		return valueProperty.value(emfFormsDatabinding.getValueProperty(indexReference.getTargetDMR()));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToListProperty(VDomainModelReference)
	 */
	@Override
	public IListProperty convertToListProperty(VDomainModelReference domainModelReference)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VIndexDomainModelReference."); //$NON-NLS-1$
		}

		final VIndexDomainModelReference indexReference = VIndexDomainModelReference.class.cast(domainModelReference);
		if (indexReference.getDomainModelEFeature() == null) {
			throw new DatabindingFailedException(
				"The field domainModelEFeature of the given VIndexDomainModelReference must not be null."); //$NON-NLS-1$
		}

		final List<EReference> referencePath = indexReference.getDomainModelEReferencePath();
		IValueProperty valueProperty;
		if (referencePath.isEmpty()) {
			valueProperty = new EMFIndexedValueProperty(indexReference.getIndex(),
				indexReference.getDomainModelEFeature());
		} else {
			IEMFValueProperty emfValueProperty = EMFProperties.value(referencePath.get(0));
			for (int i = 1; i < referencePath.size(); i++) {
				emfValueProperty = emfValueProperty.value(referencePath.get(i));
			}
			final EMFIndexedValueProperty indexedValueProperty = new EMFIndexedValueProperty(indexReference.getIndex(),
				indexReference.getDomainModelEFeature());
			valueProperty = emfValueProperty.value(indexedValueProperty);
		}

		return valueProperty.list(emfFormsDatabinding.getListProperty(indexReference.getTargetDMR()));
	}

}