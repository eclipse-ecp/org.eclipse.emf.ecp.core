/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.ecore.swt.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * An action to select an {@link EClassifier} which is an {@link EDataType}. Will be used for editing the type of an
 * {@link org.eclipse.emf.ecore.EAttribute EAttribute}.
 *
 * @author jfaltermeier
 *
 */
public class AddEClassifierOnlyEDataTypeReferenceAction extends AddEClassifierReferenceAction {

	/**
	 * Constructor.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param setting the {@link Setting} to use
	 * @param itemPropertyDescriptor the {@link IItemPropertyDescriptor} to use
	 * @param referenceService the {@link ReferenceService} to use
	 * @param packages the {@link EPackage}s to use
	 */
	public AddEClassifierOnlyEDataTypeReferenceAction(EditingDomain editingDomain, Setting setting,
		IItemPropertyDescriptor itemPropertyDescriptor, ReferenceService referenceService, Set<EPackage> packages) {
		super(editingDomain, setting, itemPropertyDescriptor, referenceService, packages);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.ecore.swt.internal.AddEClassifierReferenceAction#getEClassifiersFromRegistry()
	 */
	@Override
	protected Set<EClassifier> getEClassifiersFromRegistry(Set<EPackage> ePackages) {
		final Set<EClassifier> elements = new HashSet<EClassifier>();
		for (final EPackage ePackage : ePackages) {
			for (final EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EDataType) {
					elements.add(eClassifier);
				}
			}
		}
		return elements;
	}

}
