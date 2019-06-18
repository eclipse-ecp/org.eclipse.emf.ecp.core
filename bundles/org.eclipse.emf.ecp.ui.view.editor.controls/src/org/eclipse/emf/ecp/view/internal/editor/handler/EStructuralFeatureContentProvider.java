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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * An {@link ITreeContentProvider} that allows to select {@link EStructuralFeature EStructuralFeatures}. These are
 * read from an {@link EClass}, an {@link EPackage}, or an already expanded {@link EReference}.
 *
 * @author Lucas Koehler
 *
 */
public class EStructuralFeatureContentProvider implements ITreeContentProvider {
	private final boolean allowMultiReferences;

	/**
	 *
	 * @param expandableMultiReferences Whether the content provider considers a multi reference to have children.
	 */
	public EStructuralFeatureContentProvider(boolean expandableMultiReferences) {
		allowMultiReferences = expandableMultiReferences;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do here
	}

	@Override
	public void dispose() {
		// nothing to do here
	}

	@Override
	public boolean hasChildren(Object element) {
		if (EPackage.class.isInstance(element)) {
			final EPackage p = (EPackage) element;
			return !p.getEClassifiers().isEmpty() || !p.getESubpackages().isEmpty();
		}
		if (EPackage.Registry.class.isInstance(element)) {
			return !EPackage.Registry.class.cast(element).values().isEmpty();
		}
		if (EClass.class.isInstance(element)) {
			return !EClass.class.cast(element).getEAllStructuralFeatures().isEmpty();
		}
		if (EReference.class.isInstance(element)) {
			final EReference eReference = (EReference) element;

			return eReference.isMany() && !allowMultiReferences ? false
				: hasChildren(eReference.getEReferenceType());
		}
		return false;
	}

	@Override
	public Object getParent(Object element) {
		// nothing to do here
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (EClass.class.isInstance(parentElement)) {
			return EClass.class.cast(parentElement).getEAllStructuralFeatures().toArray();
		}
		if (EReference.class.isInstance(parentElement)) {
			final EClass referenceType = EReference.class.cast(parentElement).getEReferenceType();
			return referenceType.getEAllStructuralFeatures().toArray();
		}
		if (EPackage.Registry.class.isInstance(parentElement)) {
			return EPackage.Registry.class.cast(parentElement).values().toArray();
		}
		if (EPackage.class.isInstance(parentElement)) {
			final Set<Object> children = new LinkedHashSet<Object>();
			children.addAll(EPackage.class.cast(parentElement).getESubpackages());
			children.addAll(EPackage.class.cast(parentElement).getEClassifiers());
			return children.toArray();
		}
		return null;
	}
}
