/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core.di;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsContextProvider;
import org.osgi.framework.FrameworkUtil;

/**
 * Basic implementation of {@link EMFFormsContextProvider}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsContextProviderImpl implements EMFFormsContextProvider {

	private static final String CONTEXT_NAME = "EMF Forms View Context"; //$NON-NLS-1$

	// Track contexts that have been set in us to dispose with the view context.
	// We need to keep them as long as clients may still be using them.
	// Map the external context to our child context
	private final Map<IEclipseContext, IEclipseContext> eclipseContexts = new HashMap<>();

	// The current Eclipse context to provide to clients
	private IEclipseContext eclipseContext;
	private ViewModelContext viewModelContext;

	@Override
	public void instantiate(ViewModelContext context) {
		viewModelContext = context;
	}

	@Override
	public void dispose() {
		eclipseContexts.values().forEach(IEclipseContext::dispose);
		eclipseContexts.clear();
		viewModelContext = null;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public IEclipseContext getContext() {
		if (eclipseContext == null) {
			// Was the view context created with an initial Eclipse context?
			IEclipseContext bootstrapContext = (IEclipseContext) viewModelContext
				.getContextValue(IEclipseContext.class.getName());
			if (bootstrapContext == null) {
				bootstrapContext = EclipseContextFactory
					.getServiceContext(FrameworkUtil.getBundle(EMFFormsContextProviderImpl.class).getBundleContext());
			}
			setContext(bootstrapContext);
		}
		return eclipseContext;
	}

	@Override
	public void setContext(IEclipseContext eclipseContext) {
		// We may have seen this context before
		this.eclipseContext = eclipseContexts.computeIfAbsent(eclipseContext,
			ctx -> {
				final IEclipseContext child = ctx.createChild(CONTEXT_NAME);

				// Put the view model context in the Eclipse context
				child.set(EMFFormsViewContext.class, viewModelContext);

				return child;
			});
	}

}
