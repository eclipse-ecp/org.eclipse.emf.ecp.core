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
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * A {@link ITreeContentProvider} to show available {@link EClass}s in a tree. The hierachy will be build based on
 * {@link EPackage}s. If there is only one {@link EPackage} containing all {@link EClass}s, a flat list will be shown.
 *
 * @author Eugen Neufeld
 *
 *
 */
public class FilteredEClassContentProvider implements ITreeContentProvider {

	private final Map<EPackage, Set<EClass>> packageClassesMap = new HashMap<EPackage, Set<EClass>>();

	/**
	 * Default constructor.
	 *
	 * @param unsupportedEPackages {@link EPackage}s to be ignored completely
	 * @param packagesToBeShown {@link EPackage}s to be shown in the tree, if a package is not in this list, no EClasses
	 *            will be shown.
	 * @param eClassesToBeShown {@link EClass}s to be shown.
	 */
	public FilteredEClassContentProvider(Collection<EPackage> unsupportedEPackages,
		Collection<EPackage> packagesToBeShown, Collection<EClass> eClassesToBeShown) {
		for (final EPackage ePackage : EMFUtils.getAllRegisteredEPackages()) {
			if (unsupportedEPackages.contains(ePackage)) {
				continue;
			}
			final boolean addToPackages = packagesToBeShown.contains(ePackage);
			for (final EClassifier classifier : ePackage.getEClassifiers()) {
				if (classifier instanceof EClass && !((EClass) classifier).isAbstract()) {
					final EClass eClass = (EClass) classifier;
					if (addToPackages || eClassesToBeShown.contains(eClass)) {
						if (!packageClassesMap.containsKey(ePackage)) {
							packageClassesMap.put(ePackage, new HashSet<EClass>());
						}
						packageClassesMap.get(ePackage).add(eClass);

					}
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public Object[] getElements(Object inputElement) {
		if (packageClassesMap.size() == 1) {
			return packageClassesMap.values().iterator().next().toArray();
		}
		return packageClassesMap.keySet().toArray();
	}

	/** {@inheritDoc} */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (EPackage.class.isInstance(parentElement)) {
			return packageClassesMap.get(parentElement).toArray();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Object getParent(Object element) {
		if (EClass.class.isInstance(element)) {
			return ((EClass) element).getEPackage();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildren(Object element) {
		return packageClassesMap.containsKey(element);
	}

}
