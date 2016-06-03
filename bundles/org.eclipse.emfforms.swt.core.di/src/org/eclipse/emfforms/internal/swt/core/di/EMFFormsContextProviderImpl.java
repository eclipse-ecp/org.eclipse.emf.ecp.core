/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core.di;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsContextProvider;
import org.osgi.framework.FrameworkUtil;

/**
 * Basic implementation of {@link EMFFormsContextProvider}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsContextProviderImpl implements EMFFormsContextProvider {

	private IEclipseContext eclipseContext;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		if (eclipseContext != null) {
			eclipseContext.dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsContextProvider#getContext()
	 */
	@Override
	public IEclipseContext getContext() {
		if (eclipseContext == null) {
			final IEclipseContext originalServiceContext = EclipseContextFactory
				.getServiceContext(FrameworkUtil.getBundle(EMFFormsContextProviderImpl.class).getBundleContext());
			eclipseContext = originalServiceContext.createChild();
		}
		return eclipseContext;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.di.EMFFormsContextProvider#setContext(org.eclipse.e4.core.contexts.IEclipseContext)
	 */
	@Override
	public void setContext(IEclipseContext eclipseContext) {
		this.eclipseContext = eclipseContext.createChild();
	}

}
