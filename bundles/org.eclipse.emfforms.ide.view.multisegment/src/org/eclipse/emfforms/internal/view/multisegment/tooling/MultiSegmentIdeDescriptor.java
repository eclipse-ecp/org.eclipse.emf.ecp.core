/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
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

	@Override
	public boolean isAvailableInIde() {
		return false;
	}

	@Override
	public EStructuralFeatureSelectionValidator getEStructuralFeatureSelectionValidator() {
		return structuralFeature -> {
			if (structuralFeature != null && EReference.class.isInstance(structuralFeature)
				&& structuralFeature.isMany()) {
				return null;
			}
			return "A multi segment requires a multi reference."; //$NON-NLS-1$
		};
	}

	@Override
	public EClass getSegmentType() {
		return VMultisegmentPackage.eINSTANCE.getMultiDomainModelReferenceSegment();
	}

	@Override
	public boolean isLastElementInPath() {
		return true;
	}

	@Override
	public ReferenceTypeResolver getReferenceTypeResolver() {
		return (eReference, segment) -> eReference.getEReferenceType();
	}

	@Override
	public boolean isAllowedAsLastElementInPath() {
		return true;
	}
}
