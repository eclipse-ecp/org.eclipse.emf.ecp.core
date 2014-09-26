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
package org.eclipse.emf.ecp.edit.spi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public interface ReferenceService extends ViewModelService {

	/**
	 * Adds new model elements to the {@link EReference}.
	 * The implementation is responsible for providing a selection meachsims, e.g. a dialog.
	 *
	 * @param eObject the {@link EObject} to add
	 * @param eReference the {@link EReference} to add the {@link EObject} to
	 * @return
	 * @since 1.5
	 */
	void addNewModelElements(EObject eObject, EReference eReference);

	/**
	 * Adds existing model elements to the {@link EReference}.
	 * The implementation is responsible for providing a selection meachsims, e.g. a dialog.
	 *
	 * @param eObject the {@link EObject} to add
	 * @param eReference the {@link EReference} to add the {@link EObject} to
	 * @since 1.5
	 */
	void addExistingModelElements(EObject eObject, EReference eReference);

	/**
	 * Opens an {@link EObject} in a new context.
	 *
	 * @param eObject the {@link EObject} to open in a new context
	 */
	void openInNewContext(EObject eObject);
}
