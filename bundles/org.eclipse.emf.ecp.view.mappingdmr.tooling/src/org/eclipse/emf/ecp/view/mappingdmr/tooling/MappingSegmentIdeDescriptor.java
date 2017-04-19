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
package org.eclipse.emf.ecp.view.mappingdmr.tooling;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ReferenceTypeResolver;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.osgi.service.component.annotations.Component;

/**
 * {@link SegmentIdeDescriptor} for {@link VMappingDomainModelReferenceSegment VMappingDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MappingSegmentIdeDescriptor")
public class MappingSegmentIdeDescriptor implements SegmentIdeDescriptor {

	private static final String KEY = "key"; //$NON-NLS-1$
	private static final String VALUE = "value"; //$NON-NLS-1$
	private static final String MAP_ENTRY_TYPE_REGEX = "EClassTo.+Map"; //$NON-NLS-1$
	private static final String JAVA_UTIL_MAP_ENTRY = "java.util.Map$Entry"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor#getSegmentType()
	 */
	@Override
	public EClass getSegmentType() {
		return VMappingdmrPackage.eINSTANCE.getMappingDomainModelReferenceSegment();
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
		return new EStructuralFeatureSelectionValidator() {

			@Override
			public String isValid(EStructuralFeature structuralFeature) {
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
				// TODO improve message
				return "A mapping segment requires a map which has EClass as its key type and a EReference for its values."; //$NON-NLS-1$
			}
		};
	}

	/**
	 *
	 * @param mapType
	 * @return The type of the map's key, <code>null</code> if the type is not resolvable
	 */
	private EClass getMapValueType(EClass mapType) {
		return getMapType(mapType, VALUE);
	}

	/**
	 *
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
				return getMapValueType(reference.getEReferenceType());
			}
		};
	}

}
