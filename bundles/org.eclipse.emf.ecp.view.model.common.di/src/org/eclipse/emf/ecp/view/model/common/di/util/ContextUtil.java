/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.di.util;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Util class for injection all needed objects in an {@link IEclipseContext}.
 *
 * @author jfaltermeier
 *
 */
public final class ContextUtil {

	/**
	 * Named key for the domain model.
	 */
	public static final String EMF_FORMS_DOMAIN_MODEL = "EMFForms_DomainModel"; //$NON-NLS-1$

	private ContextUtil() {

	}

	/**
	 * Adds the objects of an AbstractRenderer to the {@link IEclipseContext}.
	 *
	 * @param eclipseContext the eclipse context
	 * @param element the {@link VElement}
	 * @param viewModelContext the {@link ViewModelContext}
	 */
	public static void setAbstractRendererObjects(IEclipseContext eclipseContext, VElement element,
		ViewModelContext viewModelContext) {
		eclipseContext.set(VElement.class, element);
		eclipseContext.set(ViewModelContext.class, viewModelContext);
		eclipseContext.set(EMF_FORMS_DOMAIN_MODEL, viewModelContext.getDomainModel());

		final Class<?> elementClass = element.eClass().getInstanceClass();
		eclipseContext.set(elementClass.getName(), elementClass.cast(element));

		final Class<?> domainClass = viewModelContext.getDomainModel().eClass().getInstanceClass();
		eclipseContext.set(domainClass.getName(), domainClass.cast(viewModelContext.getDomainModel()));
	}
}
