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
package org.eclipse.emfforms.internal.core.services.databinding.mapping;

import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * A checked {@link Exception} that is thrown when a
 * {@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference VMappingDomainModelReference}
 * should be converted to a property and the referenced map's values are no {@link org.eclipse.emf.ecore.EObject
 * EObjects}.
 *
 * @author Lucas Koehler
 *
 */
public class IllegalMapTypeException extends DatabindingFailedException {

	private static final long serialVersionUID = 4729660645033802593L;

	/**
	 * Creates a new {@link IllegalMapTypeException}.
	 *
	 * @param message The message of the Exception
	 */
	public IllegalMapTypeException(String message) {
		super(message);
	}

}
