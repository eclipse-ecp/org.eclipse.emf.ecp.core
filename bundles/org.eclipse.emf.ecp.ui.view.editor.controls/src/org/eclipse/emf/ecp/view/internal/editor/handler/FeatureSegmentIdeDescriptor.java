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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ReferenceTypeResolver;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link SegmentIdeDescriptor} for {@link org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment
 * VFeatureDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "FeatureSegmentIdeDescriptor")
public class FeatureSegmentIdeDescriptor implements SegmentIdeDescriptor {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#getSegmentType()
	 */
	@Override
	public EClass getSegmentType() {
		return VViewPackage.eINSTANCE.getFeatureDomainModelReferenceSegment();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#isAvailableInIde()
	 */
	@Override
	public boolean isAvailableInIde() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#getEStructuralFeatureSelectionValidator()
	 */
	@Override
	public EStructuralFeatureSelectionValidator getEStructuralFeatureSelectionValidator() {
		//
		return new EStructuralFeatureSelectionValidator() {

			@Override
			public String isValid(EStructuralFeature structuralFeature) {
				// Every selection is valid for feature segments
				return null; // null means the selection is valid
			}
		};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#isLastElementInPath()
	 */
	@Override
	public boolean isLastElementInPath() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#getReferenceTypeResolver()
	 */
	@Override
	public ReferenceTypeResolver getReferenceTypeResolver() {
		return new ReferenceTypeResolver() {

			@Override
			public EClass resolveNextEClass(EReference reference) {
				return reference.getEReferenceType();
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#isAllowedAsLastElementInPath()
	 */
	@Override
	public boolean isAllowedAsLastElementInPath() {
		return true;
	}

}
