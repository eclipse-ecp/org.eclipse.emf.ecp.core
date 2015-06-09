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

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.ECPAdditionalRendererTester;
import org.eclipse.emfforms.spi.swt.core.AbstractAdditionalSWTRenderer;

/**
 * A class describing a rendererClass.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPAdditionalRendererDescription {

	private final Class<AbstractAdditionalSWTRenderer<VElement>> rendererClass;
	private final ECPAdditionalRendererTester tester;

	/**
	 * The constructor of the ControlDescription.
	 *
	 * @param rendererClass the rendererClass
	 * @param tester the class testing whether the rendererClass is applicable for the current view model context
	 */
	public ECPAdditionalRendererDescription(Class<AbstractAdditionalSWTRenderer<VElement>> rendererClass,
		ECPAdditionalRendererTester tester) {
		super();
		this.rendererClass = rendererClass;
		this.tester = tester;
	}

	/**
	 * The rendererClass. It extends the {@link AbstractAdditionalSWTRenderer}.
	 *
	 * @return the class implementing this rendererClass
	 */
	public Class<AbstractAdditionalSWTRenderer<VElement>> getRenderer() {
		return rendererClass;
	}

	/**
	 * The tester for this rendererClass. The tester is used to check whether this rendererClass is usable on a specific
	 * view
	 * model context.
	 *
	 * @return the {@link ECPAdditionalRendererTester} implementation
	 */
	public ECPAdditionalRendererTester getTester() {
		return tester;
	}
}
