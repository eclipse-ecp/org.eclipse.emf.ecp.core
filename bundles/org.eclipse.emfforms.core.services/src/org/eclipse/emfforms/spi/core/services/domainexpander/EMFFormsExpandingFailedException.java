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
package org.eclipse.emfforms.spi.core.services.domainexpander;

/**
 * An {@link Exception} that represents that the domain expansion of a domain object for a
 * {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference} failed.
 *
 * @author Lucas Koehler
 * @since 1.7
 *
 */
public class EMFFormsExpandingFailedException extends Exception {

	private static final long serialVersionUID = 2329893946849012207L;

	/**
	 * Creates a new {@link EMFFormsExpandingFailedException}.
	 *
	 * @param message The message of the exception.
	 */
	public EMFFormsExpandingFailedException(String message) {
		super(message);
	}
}
