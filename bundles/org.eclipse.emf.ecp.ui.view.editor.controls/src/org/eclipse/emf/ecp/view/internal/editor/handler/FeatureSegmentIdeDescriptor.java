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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.emf.ecore.EClass;
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

	@Override
	public EClass getSegmentType() {
		return VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT;
	}

	@Override
	public boolean isAvailableInIde() {
		return true;
	}

	@Override
	public EStructuralFeatureSelectionValidator getEStructuralFeatureSelectionValidator() {
		// Every selection is valid for feature segments.
		// Returning null means the selection is valid.
		return structuralFeature -> null;
	}

	@Override
	public boolean isLastElementInPath() {
		return false;
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
