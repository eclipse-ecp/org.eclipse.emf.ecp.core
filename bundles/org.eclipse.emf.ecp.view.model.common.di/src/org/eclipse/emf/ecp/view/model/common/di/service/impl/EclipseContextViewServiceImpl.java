/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.di.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.model.common.di.service.EclipseContextViewService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Implementation of the {@link EclipseContextViewService}.
 *
 * @author jfaltermeier
 *
 */
public class EclipseContextViewServiceImpl implements EclipseContextViewService {

	private Map<VElement, IEclipseContext> map;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		map = new LinkedHashMap<VElement, IEclipseContext>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		for (final IEclipseContext context : map.values()) {
			context.dispose();
		}
		map.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 42;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.di.service.EclipseContextViewService#putContext(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.e4.core.contexts.IEclipseContext)
	 */
	@Override
	public void putContext(VElement element, IEclipseContext context) {
		map.put(element, context);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.di.service.EclipseContextViewService#getContext(org.eclipse.emf.ecp.view.spi.model.VElement)
	 */
	@Override
	public IEclipseContext getContext(VElement element) {
		return map.get(element);
	}

}
