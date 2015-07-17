/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.validation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * {@link ITreeContentProvider} for displaying {@link org.eclipse.emf.common.util.Diagnostic Diagnostics}.
 *
 * @author jfaltermeier
 *
 */
public class ValidationContentProvider implements ITreeContentProvider {

	private final Map<Object, Object> diagnosticToParentMap;

	/**
	 * Default constructor.
	 */
	public ValidationContentProvider() {
		diagnosticToParentMap = new LinkedHashMap<Object, Object>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		diagnosticToParentMap.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		diagnosticToParentMap.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Diagnostic) {
			return getChildren(inputElement);
		}
		if (inputElement instanceof Diagnostic[]) {
			return (Diagnostic[]) inputElement;
		}
		if (inputElement instanceof List<?>) {
			return ((List<?>) inputElement).toArray(new Diagnostic[((List<?>) inputElement).size()]);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		final Diagnostic diagnostic = (Diagnostic) parentElement;
		final Diagnostic[] childDiagnostics = diagnostic.getChildren().toArray(
			new Diagnostic[diagnostic.getChildren().size()]);
		for (final Diagnostic child : childDiagnostics) {
			diagnosticToParentMap.put(child, diagnostic);
		}
		return childDiagnostics;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		return diagnosticToParentMap.get(element);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		final Diagnostic diagnostic = (Diagnostic) element;
		return diagnostic.getChildren().size() > 0 ? true : false;
	}

}
