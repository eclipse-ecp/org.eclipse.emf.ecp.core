/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.swt;

import java.util.Set;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.ECPRendererTester;

/**
 * A class describing a renderer.
 * 
 * @author Eugen Neufeld
 * 
 */
public class ECPRendererDescription {

	private final AbstractSWTRenderer<VElement> renderer;
	private final Set<ECPRendererTester> tester;

	/**
	 * The constructor of the ControlDescription.
	 * 
	 * @param renderer the renderer
	 * @param tester the class testing whether the renderer is applicable for the current view model context
	 */
	public ECPRendererDescription(AbstractSWTRenderer<VElement> renderer,
		Set<ECPRendererTester> tester) {
		super();
		this.renderer = renderer;
		this.tester = tester;
	}

	/**
	 * The renderer. It extends the {@link AbstractSWTRenderer}.
	 * 
	 * @return the class implementing this renderer
	 */
	public AbstractSWTRenderer<VElement> getRenderer() {
		return renderer;
	}

	/**
	 * The tester for this renderer. The tester is used to check whether this renderer is usable on a specific view
	 * model context.
	 * 
	 * @return the {@link ECPRendererTester} implementation
	 */
	public Set<ECPRendererTester> getTester() {
		return tester;
	}
}
