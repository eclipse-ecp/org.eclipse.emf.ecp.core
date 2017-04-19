/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasChildren(Object element) {

		if (EPackage.class.isInstance(element)) {
			return true;
		}
		if (EClass.class.isInstance(element)) {
			final EClass eClass = (EClass) element;
			final boolean hasReferences = !eClass.getEAllReferences().isEmpty();
			final boolean hasAttributes = !eClass.getEAllAttributes().isEmpty();
			return hasReferences || hasAttributes;

		}
		if (EReference.class.isInstance(element)) {
			final EReference eReference = (EReference) element;

			return eReference.isMany() && !allowMultiReferences ? false : hasChildren(eReference
				.getEReferenceType());
		}
		return false;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (EClass.class.isInstance(parentElement)) {
			final EClass eClass = (EClass) parentElement;
			final Set<Object> result = getElementsForEClass(eClass);
			return result.toArray();
		}
		if (EReference.class.isInstance(parentElement)) {
			final EReference eReference = (EReference) parentElement;
			final Set<Object> result = getElementsForEClass(eReference.getEReferenceType());
			return result.toArray();
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

	private Set<Object> getElementsForEClass(EClass eClass) {
		final Set<Object> result = new LinkedHashSet<Object>();
		result.addAll(eClass.getEAllReferences());
		result.addAll(eClass.getEAllAttributes());
		return result;
	}
}
