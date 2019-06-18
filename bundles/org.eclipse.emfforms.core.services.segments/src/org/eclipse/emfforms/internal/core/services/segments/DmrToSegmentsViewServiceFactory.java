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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments;

import org.eclipse.emfforms.spi.core.services.segments.RuntimeModeUtil;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;

/**
 * View service factory that creates {@link DmrToSegmentsViewService DmrToSegmentsViewServices}.
 *
 * @author Lucas Koehler
 */
@Component(name = "DmrToSegmentsViewServiceFactory")
public class DmrToSegmentsViewServiceFactory implements EMFFormsViewServiceFactory<DmrToSegmentsViewService> {

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
		return -1000d;
	}

	@Override
	public Class<DmrToSegmentsViewService> getType() {
		return DmrToSegmentsViewService.class;
	}

	@Override
	public DmrToSegmentsViewService createService(EMFFormsViewContext emfFormsViewContext) {
		if (RuntimeModeUtil.isSegmentMode()) {
			return new DmrToSegmentsViewService(emfFormsViewContext);
		}
		return null;
	}

}
