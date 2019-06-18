/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
 * Lucas Koehler - added new add new model elements api
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emfforms.common.Optional;

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
	 * @since 1.5
	 * @deprecated use {@link #addNewModelElements(EObject, EReference, boolean)} instead
	 */
	@Deprecated
	void addNewModelElements(EObject eObject, EReference eReference);

	/**
	 * Adds new model elements to the {@link EReference}.
	 * The implementation is responsible for providing a selection mechanism, e.g. a dialog.
	 *
	 * @param eObject the {@link EObject} to add
	 * @param eReference the {@link EReference} to add the {@link EObject} to
	 * @param openInNewContext Hints the reference service whether the created model element should be opened in a new
	 *            context
	 * @since 1.17
	 * @return The created model element
	 */
	Optional<EObject> addNewModelElements(EObject eObject, EReference eReference, boolean openInNewContext);

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
