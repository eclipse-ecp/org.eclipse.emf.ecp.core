/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.provider.ViewProviderImpl;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * Util class for retrieving a {@link VView} based on an {@link EObject}.
 * 
 * @author Eugen Neufeld
 * @since 1.2
 * 
 */
public final class ViewProviderHelper {

	private static ViewProviderImpl viewProvider = new ViewProviderImpl();

	private ViewProviderHelper() {

	}

	/**
	 * This allows to retrieve a {@link VView} based on an {@link EObject}. This method reads all {@link IViewProvider
	 * IViewProviders} and searches for the best fitting. If none can be found, then null is returned.
	 * 
	 * @param eObject the {@link EObject} to find a {@link VView} for
	 * @param context a key-value-map from String to Object
	 * @return a view model for the given {@link EObject} or null if no suited provider could be found
	 */
	public static VView getView(EObject eObject, Map<String, Object> context) {
		return viewProvider.getView(eObject, context);
	}
}
