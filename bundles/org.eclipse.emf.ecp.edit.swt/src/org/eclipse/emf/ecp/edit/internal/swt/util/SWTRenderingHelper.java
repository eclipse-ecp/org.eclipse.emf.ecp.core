/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import org.eclipse.emf.ecp.view.spi.renderer.LayoutHelper;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingConfiguration;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRowFactory;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * @author Eugen Neufeld
 * 
 */
public final class SWTRenderingHelper {

	public static final SWTRenderingHelper INSTANCE = new SWTRenderingHelper();

	private final LayoutHelper<Layout> defaultLayoutHelper;
	private final RenderingResultRowFactory<Control> defaultRowFactory;

	/**
	 * 
	 */
	private SWTRenderingHelper() {
		defaultLayoutHelper = new DefaultLayoutHelper();
		defaultRowFactory = new DefaultRenderingResultFactory();
	}

	public LayoutHelper<Layout> getLayoutHelper() {
		final LayoutHelper<Layout> layoutHelper = RenderingConfiguration.getCurrent().getLayoutHelper(Layout.class);
		if (layoutHelper != null) {
			return layoutHelper;
		}
		return defaultLayoutHelper;
	}

	public RenderingResultRowFactory<Control> getResultRowFactory() {
		final RenderingResultRowFactory<Control> rowFactory = RenderingConfiguration.getCurrent()
			.getRenderingRowFactory(Control.class);
		if (rowFactory != null) {
			return rowFactory;
		}
		return defaultRowFactory;
	}

}
