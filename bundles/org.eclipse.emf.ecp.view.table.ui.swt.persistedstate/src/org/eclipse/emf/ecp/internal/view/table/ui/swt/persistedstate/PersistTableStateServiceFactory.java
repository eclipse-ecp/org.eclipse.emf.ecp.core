/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.view.table.ui.swt.persistedstate;

import java.text.MessageFormat;

import org.eclipse.emf.ecp.spi.view.table.ui.swt.persistedstate.PersistTableStateService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;

/**
 * Factory for creating {@link PersistTableStateServiceImpl}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "PersistTableStateServiceFactory", service = EMFFormsViewServiceFactory.class)
public class PersistTableStateServiceFactory implements EMFFormsViewServiceFactory<PersistTableStateService> {

	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.IMMEDIATE;
	}

	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.LOCAL;
	}

	@Override
	public double getPriority() {
		return 0;
	}

	@Override
	public Class<PersistTableStateService> getType() {
		return PersistTableStateService.class;
	}

	@Override
	public PersistTableStateService createService(EMFFormsViewContext emfFormsViewContext) {
		if (ViewModelContext.class.isInstance(emfFormsViewContext)) {
			final PersistTableStateServiceImpl service = new PersistTableStateServiceImpl(
				ViewModelContext.class.cast(emfFormsViewContext));
			return service;
		}
		throw new IllegalArgumentException(MessageFormat.format("Context of type {0} is not supported yet.", //$NON-NLS-1$
			emfFormsViewContext.getClass().getSimpleName()));
	}

}
