/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * stefan - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.util;

import java.util.Set;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;

/**
 * Util class for SWT Control Renderer.
 *
 * @author Stefan Dirix
 * @since 1.14
 */
public final class RendererUtil {

	/**
	 * Util Class Constructor.
	 */
	private RendererUtil() {

	}

	/**
	 * Returns a {@link VTStyleProperty} of the given class or {@code null} if none was found.
	 *
	 * @param templateProvider the {@link VTViewTemplateProvider}
	 * @param vElement the {@link VElement}
	 * @param context the {@link ViewModelContext}.
	 * @param stylePropertyClass the style property class
	 * @param <SP> stylePropertyClass
	 *
	 * @return the property or {@code null}
	 */
	public static <SP extends VTStyleProperty> SP getStyleProperty(VTViewTemplateProvider templateProvider,
		VElement vElement, ViewModelContext context, Class<SP> stylePropertyClass) {
		final Set<VTStyleProperty> styleProperties = templateProvider
			.getStyleProperties(vElement, context);
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (stylePropertyClass.isInstance(styleProperty)) {
				return stylePropertyClass.cast(styleProperty);
			}
		}
		return null;
	}
}
