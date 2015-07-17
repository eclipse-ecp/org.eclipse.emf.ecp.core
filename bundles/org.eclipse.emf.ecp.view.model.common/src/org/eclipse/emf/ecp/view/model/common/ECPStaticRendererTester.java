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
package org.eclipse.emf.ecp.view.model.common;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * The static tester for renderer.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPStaticRendererTester implements ECPRendererTester {

	private final int priority;
	private final Class<? extends VElement> supportedVElement;

	/**
	 * The constructor of the static tester.
	 *
	 * @param priority the static priority
	 * @param supportedVElement the {@link VElement} this tester allows
	 */
	public ECPStaticRendererTester(int priority,
		Class<? extends VElement> supportedVElement) {
		this.priority = priority;
		this.supportedVElement = supportedVElement;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.ECPRendererTester#isApplicable(VElement, ViewModelContext)
	 */
	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!supportedVElement.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		return priority;
	}

}
