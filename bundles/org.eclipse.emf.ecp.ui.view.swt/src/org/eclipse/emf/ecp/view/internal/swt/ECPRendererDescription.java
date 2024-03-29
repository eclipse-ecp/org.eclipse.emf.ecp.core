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
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.swt;

import java.util.Set;

import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

/**
 * A class describing a rendererClass.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPRendererDescription {

	private final Class<AbstractSWTRenderer<VElement>> rendererClass;
	private final Set<ECPRendererTester> tester;

	/**
	 * The constructor of the ControlDescription.
	 *
	 * @param rendererClass the rendererClass
	 * @param tester the class testing whether the rendererClass is applicable for the current view model context
	 */
	public ECPRendererDescription(Class<AbstractSWTRenderer<VElement>> rendererClass,
		Set<ECPRendererTester> tester) {
		super();
		this.rendererClass = rendererClass;
		this.tester = tester;
	}

	/**
	 * The rendererClass. It extends the {@link AbstractSWTRenderer}.
	 *
	 * @return the class implementing this rendererClass
	 */
	public Class<AbstractSWTRenderer<VElement>> getRenderer() {
		return rendererClass;
	}

	/**
	 * The tester for this rendererClass. The tester is used to check whether this rendererClass is usable on a specific
	 * view
	 * model context.
	 *
	 * @return the {@link ECPRendererTester} implementation
	 */
	public Set<ECPRendererTester> getTester() {
		return tester;
	}
}
