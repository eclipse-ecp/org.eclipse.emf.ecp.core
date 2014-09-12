/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.test.mockup;

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;

public final class MockViewSWTRenderer {

	private MockViewSWTRenderer() {
	}

	public static ViewSWTRenderer withInvalidGridDescription() {
		return new ViewSWTRendererWithInvalidGridDescription();
	}

	public static ViewSWTRenderer withoutPropertyDescriptor() {
		return new ViewSWTRendererWithNoPropertyDescriptorFoundException();
	}

	public static ViewSWTRenderer withoutRenderer() {
		return new ViewSWTRendererWithNoRendererFoundException();
	}

}
