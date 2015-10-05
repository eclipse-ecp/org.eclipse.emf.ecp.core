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
package org.eclipse.emfforms.internal.swt.controlgrid.renderer;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid;
import org.eclipse.emfforms.spi.swt.controlgrid.renderer.ControlGridSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;

/**
 * {@link EMFFormsDIRendererService} for {@link ControlGridSWTRenderer}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "ControlGridSWTRendererService")
public class ControlGridSWTRendererService implements EMFFormsDIRendererService<VControlGrid> {

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControlGrid.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		return 1;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VControlGrid>> getRendererClass() {
		return ControlGridSWTRenderer.class;
	}

}
