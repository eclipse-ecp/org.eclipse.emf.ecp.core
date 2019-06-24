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
package org.eclipse.emfforms.internal.swt.core.di;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsContextProvider;
import org.osgi.service.component.annotations.Component;

/**
 * EMF Forms global view service factory that provides the Eclipse Context in the
 * View Context.
 *
 * @since 1.22
 */
@Component(name = "e4ContextViewServiceFactory")
public class EclipseContextViewServiceFactory implements EMFFormsViewServiceFactory<IEclipseContext> {

	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.LOCAL;
	}

	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.LAZY;
	}

	@Override
	public double getPriority() {
		return 0;
	}

	@Override
	public Class<IEclipseContext> getType() {
		return IEclipseContext.class;
	}

	@Override
	public IEclipseContext createService(EMFFormsViewContext emfFormsViewContext) {
		final EMFFormsContextProvider provider = emfFormsViewContext.getService(EMFFormsContextProvider.class);

		// Would be strange, indeed, if the service provided by this bundle were not there
		return provider != null ? provider.getContext() : null;
	}

}
