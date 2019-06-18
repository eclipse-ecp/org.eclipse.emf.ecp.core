/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;

/**
 * @author Lucas Koehler
 *
 */
@Component(name = "TableColumnsDmrViewServiceFactory")
public class TableColumnsDmrViewServiceFactory implements EMFFormsViewServiceFactory<TableColumnsDmrViewService> {

	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.IMMEDIATE;
	}

	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.LOCAL;
	}

	@Override
	public double getPriority() {
		return -1999;
	}

	@Override
	public Class<TableColumnsDmrViewService> getType() {
		return TableColumnsDmrViewService.class;
	}

	@Override
	public TableColumnsDmrViewService createService(EMFFormsViewContext emfFormsViewContext) {
		if (!ToolingModeUtil.isSegmentToolingEnabled()) {
			return new TableColumnsDmrViewService(emfFormsViewContext);
		}
		return null;
	}

}
