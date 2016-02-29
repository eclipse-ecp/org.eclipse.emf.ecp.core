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
package org.eclipse.emf.ecp.view.internal.compoundcontrol.swt;

import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.swt.CompoundControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;

/**
 *
 * {@link EMFFormsDIRendererService Renderer service} for {@link CompoundControlSWTRenderer}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "CompoundControlDIRendererService")
public class CompoundControlDIRendererService implements EMFFormsDIRendererService<VCompoundControl> {

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VCompoundControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		return 3d;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VCompoundControl>> getRendererClass() {
		return CompoundControlSWTRenderer.class;
	}

}
