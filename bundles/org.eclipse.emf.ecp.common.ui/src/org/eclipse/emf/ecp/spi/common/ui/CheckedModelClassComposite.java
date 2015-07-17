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

import org.eclipse.jface.viewers.CheckboxTreeViewer;

/**
 * This class provides a CheckedTree that allows the user to select {@link org.eclipse.emf.ecore.EPackage EPackages} and
 * {@link org.eclipse.emf.ecore.EClass
 * EClasses}.
 *
 * @author Eugen Neufeld
 *
 */
public interface CheckedModelClassComposite extends CompositeProvider {

	/**
	 * Returns the {@link CheckboxTreeViewer}.
	 *
	 * @return the viewer
	 */
	CheckboxTreeViewer getViewer();

	/**
	 * Returns the selected objects.
	 *
	 * @return the selection
	 */
	Object[] getSelection();

	/**
	 * Returns the checked Elements.
	 *
	 * @return an array containing the checked elements
	 */
	Object[] getChecked();

	/**
	 * Initialize the selection by setting the checked elements.
	 *
	 * @param selection the objects to check
	 */
	void setInitialSelection(Object[] selection);
}
