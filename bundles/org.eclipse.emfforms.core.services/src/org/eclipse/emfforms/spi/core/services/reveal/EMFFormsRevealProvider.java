/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.reveal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * <p>
 * A provider of reveal strategies to the {@link EMFFormsRevealService}.
 * This is intended to be implemented as OSGi services. The Eclipse DI
 * context for the provider invocation includes at least the following
 * variables, in addition to anything else injected by the editor site that
 * hosts the view context (if the editor implementation puts the Eclipse context
 * into the view context):
 * </p>
 * <ul>
 * <li>the domain model object being revealed: {@link EObject EObject.class}</li>
 * <li>the view model element describing the UI in which it is revealed: {@link VElement VElement.class}</li>
 * <li>a helper for complex drill-down and master/detail scenarios: {@link RevealHelper RevealHelper.class}</li>
 * <li>the reveal service: {@link EMFFormsRevealService EMFFormsRevealService.class}</li>
 * <li>the view context: {@link EMFFormsViewContext EMFFormsViewContext.class}</li>
 * </ul>
 * <p>
 * Additionally, in an SWT rendering at least, a method parameter of any
 * renderer type annotated with
 * {@code @org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer}
 * will be computed from the contextual {@link VElement} and {@link EMFFormsViewContext}.
 * And a context function is available to cast the injectable view context as an
 * {@code org.eclipse.emf.ecp.view.spi.context.ViewModelContext} when applicable.
 * </p>
 *
 * @see EMFFormsRevealService#addRevealProvider(EMFFormsRevealProvider)
 *
 * @since 1.22
 */
public interface EMFFormsRevealProvider extends Vendor<RevealStep> {
	// API inferred by Bazaar annotations
}
