/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * This interface defines a {@link #isApplicable(EObject, EStructuralFeature, ViewModelContext)} method which is used to
 * determine
 * the priority of a celleditor. The celleditor with the highest priority for a combination of an {@link EObject} and a
 * feature will be used.
 *
 * @author Eugen Neufeld
 * @since 1.5
 */
public interface ECPCellEditorTester {
	/**
	 * Return this whenever the renderer should not be used for the tested {@link ViewModelContext}.
	 */
	int NOT_APPLICABLE = -1;

	/**
	 * Returns the priority of the corresponding celleditor for the combination of the {@link EObject} and the
	 * {@link EStructuralFeature}.
	 *
	 * @param eStructuralFeature the {@link EStructuralFeature} to test
	 * @param eObject the {@link EObject} to test
	 * @param viewModelContext the {@link ViewModelContext} to check
	 * @return {@link #NOT_APPLICABLE} if the corresponding celleditor should not be used, a positive integer value
	 *         otherwise. The celleditor with the highest priority will be taken.
	 * @since 1.2
	 */
	int isApplicable(EObject eObject, EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext);
}
