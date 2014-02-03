/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.spi.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;

/**
 * The {@link DiffMergeModelContext} extends the {@link ViewModelContext} and allows to retrieve two origin objects.
 * 
 * @see ViewModelContext
 * @author Eugen Neufeld
 */
public interface DiffMergeModelContext extends ViewModelContext {

	/**
	 * Gets the left model.
	 * 
	 * @return the left model
	 */
	EObject getLeftModel();

	/**
	 * Gets the right model.
	 * 
	 * @return the right model
	 */
	EObject getRightModel();

	/**
	 * Checks whether a control has a diff.
	 * 
	 * @param control the {@link VControl} to check
	 * @return true if there is a difference
	 */
	boolean hasDiff(VControl control);

	/**
	 * Returns a pair containing the left and right control for the diff.
	 * 
	 * @param control the control to get the pair for
	 * @return a pair or null if no diff exists for the provided control
	 */
	ControlPair getPairWithDiff(VControl control);

	/**
	 * Returns the total number of differences.
	 * 
	 * @return the total number of differences
	 */
	int getTotalNumberOfDiffs();

	/**
	 * Returns the diff index of a control. If the control does not have a diff the index will be -1.
	 * 
	 * @param control the {@link VControl} to get the index for
	 * @return the index of the control
	 */
	int getIndexOf(VControl control);

	/**
	 * Returns the control based on the diff index. If the index is less then 0 and greater or equals the total number
	 * of diff, then a {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param diffIndex the diff index to get the control for
	 * @return the {@link VControl}
	 */
	VControl getControl(int diffIndex) throws IllegalArgumentException;
}
