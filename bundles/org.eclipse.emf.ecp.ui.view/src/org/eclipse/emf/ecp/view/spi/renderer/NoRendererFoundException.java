/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.renderer;

import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Exception is thrown when no fitting Renderer could be found for a {@link VElement}.
 *
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 * @since 1.2
 */
public class NoRendererFoundException extends ECPRendererException {

	private static final String NO_RENDERER_FOUND = "No renderer for %1s found."; //$NON-NLS-1$
	private static final long serialVersionUID = -8540544811118107575L;
	private final VElement vElement;

	/**
	 * Constructor for an {@link Exception} indicating that a {@link VElement} is missing a renderer.
	 *
	 * @param vElement the {@link VElement} missing a renderer
	 */
	public NoRendererFoundException(VElement vElement) {
		super(String.format(NO_RENDERER_FOUND, vElement.eClass().getName()));
		this.vElement = vElement;
	}

	/**
	 * The {@link VElement} no renderer could be found for.
	 *
	 * @return the vElement without a renderer
	 */
	public final VElement getvElement() {
		return vElement;
	}

}
