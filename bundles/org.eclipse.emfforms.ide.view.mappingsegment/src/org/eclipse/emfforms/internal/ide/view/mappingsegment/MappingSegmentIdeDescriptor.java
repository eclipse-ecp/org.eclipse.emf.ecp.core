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

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ReferenceTypeResolver;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage;
import org.osgi.service.component.annotations.Component;

/**
 * {@link SegmentIdeDescriptor} for
 * {@link org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment
 * VMappingDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MappingSegmentIdeDescriptor")
public class MappingSegmentIdeDescriptor implements SegmentIdeDescriptor {

	/** Feature containing the key of the map. */
	private static final String KEY = "key"; //$NON-NLS-1$
	/** Feature containing the value of the map. */
	private static final String VALUE = "value"; //$NON-NLS-1$
	/** Name patter for EMap types. */
	private static final String MAP_ENTRY_TYPE_REGEX = "EClassTo.+Map"; //$NON-NLS-1$
	/** Instance class name of EMaps. */
	private static final String JAVA_UTIL_MAP_ENTRY = "java.util.Map$Entry"; //$NON-NLS-1$

	@Override
	public EClass getSegmentType() {
		return VMappingsegmentPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT;
	}

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
			if (structuralFeature != null && EReference.class.isInstance(structuralFeature)
				&& structuralFeature.isMany()) {
				final EReference ref = (EReference) structuralFeature;
				final EClass refType = ref.getEReferenceType();
				final EClass keyEClass = getMapKeyType(refType);
				if (keyEClass != null && EcorePackage.eINSTANCE.getEClass().isSuperTypeOf(keyEClass)) {
					final EClass valueEClass = getMapValueType(refType);
					if (valueEClass != null) {
						return null;
					}
				}
			}
			return "A mapping segment requires a map which has EClass as its key type and an EReference for its values."; //$NON-NLS-1$
		};
	}

	@Override
	public ReferenceTypeResolver getReferenceTypeResolver() {
		return (reference, segment) -> {
			if (VMappingDomainModelReferenceSegment.class.isInstance(segment)) {
				final VMappingDomainModelReferenceSegment mappingSegment = (VMappingDomainModelReferenceSegment) segment;
				if (mappingSegment.getMappedClass() != null) {
					return mappingSegment.getMappedClass();
				}
				return getMapValueType(reference.getEReferenceType());
			}
			throw new IllegalArgumentException(
				MessageFormat.format("The given segment '{0}' isn't a mapping segment", segment)); //$NON-NLS-1$
		};
	}

	/**
	 * @param mapType
	 * @return The type of the map's key, <code>null</code> if the type is not resolvable
	 */
	private EClass getMapValueType(EClass mapType) {
		return getMapType(mapType, VALUE);
	}

	/**
	 * @param mapType
	 * @return The type of the map's value, <code>null</code> if the type is not resolvable
	 */
	private EClass getMapKeyType(EClass mapType) {
		return getMapType(mapType, KEY);
	}

	private EClass getMapType(EClass mapType, String featureName) {
		if (JAVA_UTIL_MAP_ENTRY.equals(mapType.getInstanceClassName())
			&& mapType.getName().matches(MAP_ENTRY_TYPE_REGEX)) {

			final EStructuralFeature feature = mapType.getEStructuralFeature(featureName);
			if (feature != null && EReference.class.isInstance(feature)) {
				return ((EReference) feature).getEReferenceType();
			}
		}
		return null;
	}
}
