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
package org.eclipse.emfforms.internal.ide.view.mappingsegment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ReferenceTypeResolver;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link MappingSegmentIdeDescriptor}.
 *
 * @author Lucas Koehler
 *
 */
public class MappingSegmentIdeDescriptor_Test {

	private MappingSegmentIdeDescriptor descriptor;

	@Before
	public void setUp() {
		descriptor = new MappingSegmentIdeDescriptor();
	}

	@Test
	public void getEStructuralFeatureSelectionValidator_valid() {
		final EStructuralFeatureSelectionValidator validator = descriptor.getEStructuralFeatureSelectionValidator();
		final String result = validator.isValid(TestUtil.mockMapReference());

		assertNull(result);
	}

	@Test
	public void getEStructuralFeatureSelectionValidator_invalid_illegalMap() {
		final EReference keyReference = mock(EReference.class);
		// This makes it invalid, because a valid map for a mapping segment must have the EClass EClass as reference
		// type
		when(keyReference.getEReferenceType()).thenReturn(EcorePackage.Literals.ECLASSIFIER);
		when(keyReference.getName()).thenReturn("key"); //$NON-NLS-1$

		final EReference valueReference = mock(EReference.class);
		when(valueReference.getEReferenceType()).thenReturn(mock(EClass.class));
		when(valueReference.getName()).thenReturn("value"); //$NON-NLS-1$

		final EClass referenceType = mock(EClass.class);
		when(referenceType.getInstanceClassName()).thenReturn("java.util.Map$Entry"); //$NON-NLS-1$
		when(referenceType.getEStructuralFeature("key")).thenReturn(keyReference); //$NON-NLS-1$
		when(referenceType.getEStructuralFeature("value")).thenReturn(valueReference); //$NON-NLS-1$
		when(referenceType.getName()).thenReturn("EClassToMockMap"); //$NON-NLS-1$

		final EReference eRef = mock(EReference.class);
		when(eRef.isMany()).thenReturn(true);
		when(eRef.getEReferenceType()).thenReturn(referenceType);

		final EStructuralFeatureSelectionValidator validator = descriptor.getEStructuralFeatureSelectionValidator();
		final String result = validator.isValid(eRef);

		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	public void getEStructuralFeatureSelectionValidator_invalid_attribute() {
		final EAttribute eRef = mock(EAttribute.class);
		when(eRef.isMany()).thenReturn(true);
		when(eRef.getName()).thenReturn("fakeMap"); //$NON-NLS-1$

		final EStructuralFeatureSelectionValidator validator = descriptor.getEStructuralFeatureSelectionValidator();
		final String result = validator.isValid(eRef);

		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	public void getReferenceTypeResolver() {
		final VMappingDomainModelReferenceSegment mappingSegment = mock(VMappingDomainModelReferenceSegment.class);
		when(mappingSegment.eClass())
			.thenReturn(VMappingsegmentPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT);

		final EClass mappedClass = mock(EClass.class);
		when(mappingSegment.getMappedClass()).thenReturn(mappedClass);

		final ReferenceTypeResolver resolver = descriptor.getReferenceTypeResolver();
		final EClass result = resolver.resolveNextEClass(TestUtil.mockMapReference(), mappingSegment);

		assertSame("Mapped EClass was not resolved correctly.", mappedClass, result); //$NON-NLS-1$
	}

	@Test
	public void getReferenceTypeResolver_noMappedEClass() {
		final VMappingDomainModelReferenceSegment mappingSegment = mock(VMappingDomainModelReferenceSegment.class);
		when(mappingSegment.eClass())
			.thenReturn(VMappingsegmentPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT);
		when(mappingSegment.getMappedClass()).thenReturn(null);

		final ReferenceTypeResolver resolver = descriptor.getReferenceTypeResolver();
		final EClass expected = mock(EClass.class);
		final EReference mapReference = TestUtil.mockMapReference(expected);
		final EClass result = resolver.resolveNextEClass(mapReference, mappingSegment);

		assertSame("Mapped EClass was not resolved correctly.", expected, result); //$NON-NLS-1$
	}

	@Test(expected = IllegalArgumentException.class)
	public void getReferenceTypeResolver_illegalSegment() {
		final ReferenceTypeResolver resolver = descriptor.getReferenceTypeResolver();
		resolver.resolveNextEClass(TestUtil.mockMapReference(), mock(VFeatureDomainModelReferenceSegment.class));
	}

}
