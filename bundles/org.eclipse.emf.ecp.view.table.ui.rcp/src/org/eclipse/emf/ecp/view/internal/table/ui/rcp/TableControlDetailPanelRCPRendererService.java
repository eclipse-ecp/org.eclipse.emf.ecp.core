/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.ui.rcp;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.ui.rcp.TableControlDetailPanelRCPRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;

/**
 * {@link EMFFormsDIRendererService} for the {@link TableControlDetailPanelRCPRenderer}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "TableControlDetailPanelRCPRendererService", service = EMFFormsDIRendererService.class)
public class TableControlDetailPanelRCPRendererService implements EMFFormsDIRendererService<VTableControl> {

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VTableControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		if (DetailEditing.WITH_PANEL == VTableControl.class.cast(vElement).getDetailEditing()) {
			return 11;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VTableControl>> getRendererClass() {
		return TableControlDetailPanelRCPRenderer.class;
	}

}
