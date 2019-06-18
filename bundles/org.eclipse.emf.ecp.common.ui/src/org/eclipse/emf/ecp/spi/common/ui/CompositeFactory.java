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
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.spi.common.ui.composites.CheckedEStructuralFeatureComposite;
import org.eclipse.emf.ecp.spi.common.ui.composites.CheckedEStructuralFeatureCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.composites.CheckedSelectModelClassCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectModelClassCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectModelElementCompositeImpl;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * @author Eugen Neufeld
 *
 */
public final class CompositeFactory {

	private CompositeFactory() {
	}

	/**
	 * Creates a {@link CheckedModelClassComposite}.
	 *
	 * @param ePackages the packages from which classes are shown in the {@link CheckedModelClassComposite}
	 * @return {@link CheckedModelClassComposite}
	 */
	public static CheckedModelClassComposite getCheckedModelClassComposite(Collection<EPackage> ePackages) {
		return new CheckedSelectModelClassCompositeImpl(new HashSet<EPackage>(), ePackages, new HashSet<EClass>());
	}

	/**
	 * Creates a {@link SelectionComposite} to select an {@link EClass}.
	 *
	 * @param unsupportedEPackages {@link EPackage EPackages} that are not supported and will not be shown
	 * @param filteredEPackages {@link EPackage EPackages} to be shown
	 * @param filteredEClasses {@link EClass EClasses} to be shown
	 * @return {@link SelectionComposite}
	 */
	public static SelectionComposite<TreeViewer> getSelectModelClassComposite(
		Collection<EPackage> unsupportedEPackages, Collection<EPackage> filteredEPackages,
		Collection<EClass> filteredEClasses) {
		return new SelectModelClassCompositeImpl(unsupportedEPackages, filteredEPackages, filteredEClasses);
	}

	/**
	 * Creates a {@link SelectionComposite} with a {@link TableViewer} to select an {@link EObject}.
	 *
	 * @param rootObject The children of this object are shown in the table.
	 * @param multiSelection Whether the user can select multiple {@link EObject}s
	 * @return a {@link SelectionComposite}
	 * @since 1.5
	 */
	public static SelectionComposite<TableViewer> getTableSelectionComposite(Object rootObject,
		boolean multiSelection) {
		return new SelectModelElementCompositeImpl(rootObject, multiSelection);
	}

	/**
	 * Creates a {@link CompositeProvider} for a composite displaying a table with checkboxes.
	 *
	 * @param rootObject the viewer input
	 * @return the composite provider
	 */
	public static CheckedEStructuralFeatureComposite getCheckedTableSelectionComposite(Object rootObject) {
		return new CheckedEStructuralFeatureCompositeImpl(rootObject);
	}

}
