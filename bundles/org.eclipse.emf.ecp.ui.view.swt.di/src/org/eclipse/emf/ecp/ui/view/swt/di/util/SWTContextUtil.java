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
package org.eclipse.emf.ecp.ui.view.swt.di.util;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.model.common.di.util.ContextUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.swt.widgets.Composite;

/**
 * SWT util class for injection all needed objects in an {@link IEclipseContext}.
 *
 * @author jfaltermeier
 *
 */
@SuppressWarnings("restriction")
public final class SWTContextUtil {

	private SWTContextUtil() {

	}

	/**
	 * Sets the given elements in the {@link IEclipseContext}. This method will call
	 * {@link ContextUtil#setAbstractRendererObjects(IEclipseContext, VElement, ViewModelContext)}.
	 *
	 * @param eclipseContext the eclipse context to add the element to
	 * @param element the {@link VElement}
	 * @param viewModelContext the {@link ViewModelContext}
	 * @param parent the {@link Composite} to draw onto
	 */
	public static void setAbstractSWTRendererObjects(IEclipseContext eclipseContext, VElement element,
		ViewModelContext viewModelContext, Composite parent) {
		ContextUtil.setAbstractRendererObjects(eclipseContext, element, viewModelContext);
		eclipseContext.set(Composite.class, parent);
	}

}
