/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.di.renderer;

import java.util.NoSuchElementException;

import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.model.common.di.renderer.POJORendererFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * @author jfaltermeier
 *
 */
@SuppressWarnings("restriction")
public class DIControlSWTRendererTester implements ECPRendererTester {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.ECPRendererTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!(vElement instanceof VControl)) {
			return NOT_APPLICABLE;
		}
		Object renderer;
		try {
			renderer = POJORendererFactory.getInstance().getRenderer(vElement, viewModelContext);
		} catch (final NoSuchElementException ex) {
			return NOT_APPLICABLE;
		}
		if (renderer == null) {
			return NOT_APPLICABLE;
		}
		return 5;
	}

}
