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
package org.eclipse.emfforms.view.multisegment.tooling;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ReferenceTypeResolver;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link SegmentIdeDescriptor} for
 * {@link org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment
 * VMultiDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MultiSegmentIdeDescriptor")
public class MultiSegmentIdeDescriptor implements SegmentIdeDescriptor {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#isAvailableInIde()
	 */
	@Override
	public boolean isAvailableInIde() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#getEStructuralFeatureSelectionValidator()
	 */
	@Override
	public EStructuralFeatureSelectionValidator getEStructuralFeatureSelectionValidator() {
		return new EStructuralFeatureSelectionValidator() {

			@Override
			public String isValid(EStructuralFeature structuralFeature) {
				if (structuralFeature != null && EReference.class.isInstance(structuralFeature)
					&& structuralFeature.isMany()) {
					return null;
				}
				return "A multi segment requires a multi reference."; //$NON-NLS-1$
			}
		};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#getSegmentType()
	 */
	@Override
	public EClass getSegmentType() {
		return VMultisegmentPackage.eINSTANCE.getMultiDomainModelReferenceSegment();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#isLastElementInPath()
	 */
	@Override
	public boolean isLastElementInPath() {
		return true;
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
}
