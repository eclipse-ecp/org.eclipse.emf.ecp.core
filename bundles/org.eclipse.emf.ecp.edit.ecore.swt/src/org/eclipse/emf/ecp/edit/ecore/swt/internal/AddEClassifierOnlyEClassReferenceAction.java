/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.ecore.swt.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * An action to select an {@link EClassifier} which is an {@link EClass}. Will be used for editing the type of an
 * {@link org.eclipse.emf.ecore.EReference EReference}.
 *
 * @author jfaltermeier
 *
 */
public class AddEClassifierOnlyEClassReferenceAction extends AddEClassifierReferenceAction {

	/**
	 * Constructor.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param setting the {@link Setting} to use
	 * @param itemPropertyDescriptor the {@link IItemPropertyDescriptor} to use
	 * @param referenceService the {@link ReferenceService} to use
	 * @param packages the {@link EPackage}s to use
	 */
	public AddEClassifierOnlyEClassReferenceAction(EditingDomain editingDomain, Setting setting,
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
				if (eClassifier instanceof EClass) {
					elements.add(eClassifier);
				}
			}
		}
		return elements;
	}

}
