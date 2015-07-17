/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.custom.ui.swt.di.renderer;

import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 *
 * @author jfaltermeier
 *
 */
public class DICustomControlSWTRendererTester implements ECPRendererTester {

	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (VCustomControl.class.isInstance(vElement)) {
			return 11;
		}
		return NOT_APPLICABLE;
	}

}
