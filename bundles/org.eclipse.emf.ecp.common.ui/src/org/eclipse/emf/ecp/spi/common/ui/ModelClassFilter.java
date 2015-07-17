/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui;

// TODO: Revise
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * Filter for selective input in the NewModelElementWizard.
 *
 * @author Shterev
 */
public class ModelClassFilter extends ECPViewerFilter {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (getSearchTerm() == null || getSearchTerm().length() == 0) {
			return true;
		}
		if (element instanceof EClass) {
			return ((EClass) element).getName().toLowerCase().contains(getSearchTerm().toLowerCase())
				|| EPackage.class.isInstance(parentElement)
				&& ((EPackage) parentElement).getName().toLowerCase().contains(getSearchTerm().toLowerCase());
		} else if (element instanceof EPackage) {
			final EPackage ePackage = (EPackage) element;
			final Object[] children = ((ITreeContentProvider) ((TreeViewer) viewer).getContentProvider())
				.getChildren(element);
			boolean show = ePackage.getName().toLowerCase().contains(getSearchTerm().toLowerCase());
			for (final Object child : children) {
				show = show || select(viewer, element, child);
			}
			return show;
		}
		return true;
	}
}
