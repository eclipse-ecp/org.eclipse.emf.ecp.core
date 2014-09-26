/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EClass;
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

	public static CheckedModelClassComposite getCheckedModelClassComposite(Collection<EPackage> ePackages) {
		return new CheckedSelectModelClassCompositeImpl(new HashSet<EPackage>(), ePackages, new HashSet<EClass>());
	}

	public static SelectionComposite<TreeViewer> getSelectModelClassComposite(
		Collection<EPackage> unsupportedEPackages, Collection<EPackage> filteredEPackages,
		Collection<EClass> filteredEClasses) {
		return new SelectModelClassCompositeImpl(unsupportedEPackages, filteredEPackages, filteredEClasses);
	}

	public static SelectionComposite<TableViewer> getTableSelectionComposite(Object rootObject, boolean multiSelection) {
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
