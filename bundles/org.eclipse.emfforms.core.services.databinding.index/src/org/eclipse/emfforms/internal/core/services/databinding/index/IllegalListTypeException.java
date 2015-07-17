/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.index;

import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * An {@link IllegalListTypeException} is thrown by an {@link IndexDomainModelReferenceConverter} when the domain model
 * e feature of a {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference
 * VIndexDomainModelReference} referencing a list is not of the type {@link org.eclipse.emf.ecore.EReference EReference}
 * .
 *
 * @author Lucas Koehler
 *
 */
public class IllegalListTypeException extends DatabindingFailedException {

	/**
	 * Creates a new {@link IllegalListTypeException} with the given message.
	 *
	 * @param message The message text of the exception
	 */
	public IllegalListTypeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -7882225641484854585L;

}
