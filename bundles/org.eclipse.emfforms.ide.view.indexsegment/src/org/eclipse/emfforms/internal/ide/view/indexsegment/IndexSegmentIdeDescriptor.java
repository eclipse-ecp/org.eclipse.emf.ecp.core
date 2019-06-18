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
package org.eclipse.emfforms.internal.ide.view.indexsegment;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ReferenceTypeResolver;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentPackage;
import org.osgi.service.component.annotations.Component;

/**
 * {@link SegmentIdeDescriptor} for
 * {@link org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment
 * VIndexDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "IndexSegmentIdeDescriptor")
public class IndexSegmentIdeDescriptor implements SegmentIdeDescriptor {

	@Override
	public boolean isAvailableInIde() {
		return true;
	}

	@Override
	public boolean isLastElementInPath() {
		return false;
	}

	@Override
	public boolean isAllowedAsLastElementInPath() {
		return false;
	}

	@Override
	public EStructuralFeatureSelectionValidator getEStructuralFeatureSelectionValidator() {
		return structuralFeature -> {
			if (EReference.class.isInstance(structuralFeature) && structuralFeature.isMany()) {
				return null;
			}
			return "An index segment requires a multi reference."; //$NON-NLS-1$
		};
	}

	@Override
	public ReferenceTypeResolver getReferenceTypeResolver() {
		return (reference, segment) -> reference.getEReferenceType();
	}

	@Override
	public EClass getSegmentType() {
		return VIndexsegmentPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT;
	}

}
