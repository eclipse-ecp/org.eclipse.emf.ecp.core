/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui.composites;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Helper class for creating a dialog which allows to select an {@link EClass}.
 *
 * @author Eugen Neufeld
 *
 */
public class SelectModelClassCompositeImpl extends AbstractEClassTreeSelectionComposite implements
	SelectionComposite<TreeViewer> {

	/**
	 * Constructor for providing the filter data manually.
	 *
	 * @param unsupportedEPackages {@link EPackage}s that are not available for selection
	 * @param filteredEPackages {@link EPackage}s which are selectable
	 * @param filteredEClasses {@link EClass}es which are selectable
	 */
	public SelectModelClassCompositeImpl(Collection<EPackage> unsupportedEPackages,
		Collection<EPackage> filteredEPackages, Collection<EClass> filteredEClasses) {
		super(unsupportedEPackages, filteredEPackages, filteredEClasses);
	}

	@Override
	protected boolean isCheckedTree() {
		return false;
	}
}
