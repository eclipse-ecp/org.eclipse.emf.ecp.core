/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core;

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.data.ReferenceObjectPair;
import org.eclipse.swt.widgets.Control;

/**
 * <p>
 * A service which should be called while controls are initially rendered.
 * While this service is generically supported within the {@link AbstractSWTRenderer}, extenders of
 * {@link AbstractSWTRenderer} or custom controls should call this service for each generated SWT control which relates
 * to a {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference}.
 * </p>
 * <p>
 * Note: Since all generated controls are potentially exposed, the possibilities of this service are practically
 * limitless. But with power comes great responsibility: Too liberal modifications of the given {@link Control}s can
 * lead to undefined and unwanted behavior, including unresponsive controls, misaligned layouts or even cancellation of
 * the whole rendering process.
 * </p>
 *
 * @author Stefan Dirix
 * @since 1.8
 *
 */
public interface EMFFormsControlProcessorService extends ViewModelService {

	/**
	 * Process the given {@code control} rendered by the given {@code vControl}.
	 *
	 * @param control
	 *            The {@link Control} which will be processed by this service.
	 * @param vControl
	 *            The {@link VControl} responsible for the given {@code control}.
	 * @param controlViewModelContext
	 *            The {@link ViewModelContext} of the control.
	 */
	void process(Control control, VControl vControl, ViewModelContext controlViewModelContext);

	/**
	 * Process the given {@code control} which relates to the given {@code references}.
	 *
	 * @param control
	 *            The {@link Control} which will be processed by this service.
	 * @param references
	 *            The {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference}s with their
	 *            corresponding context {@link org.eclipse.emf.ecore.EObject EObject}s which relate to the given
	 *            {@code control}.
	 */
	void process(Control control, Collection<ReferenceObjectPair> references);
}
