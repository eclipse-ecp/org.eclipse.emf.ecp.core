/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model.util;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;

/**
 * Helper class for working with {@link VViewModelProperties}.
 *
 * @author Johannes Faltermeier
 * @since 1.7
 *
 */
public final class ViewModelPropertiesHelper {

	private ViewModelPropertiesHelper() {
	}

	/**
	 * This method tries to create inherited {@link VViewModelProperties} for the given {@link VElement}. If the element
	 * is a {@link VView} with non-null {@link VView#getLoadingProperties() loading properties} this will return an
	 * inherited version of those properties. Will return an empty default object in other cases.
	 *
	 * @param element the element to inherit the properties from
	 * @return the inherited properties
	 */
	public static VViewModelProperties getInhertitedPropertiesOrEmpty(VElement element) {
		if (!VView.class.isInstance(element)) {
			return VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		}
		final VView view = VView.class.cast(element);
		if (view.getLoadingProperties() == null) {
			return VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		}
		return view.getLoadingProperties().inherit();
	}
}
