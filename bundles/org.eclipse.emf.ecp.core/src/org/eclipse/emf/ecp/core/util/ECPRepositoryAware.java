/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.util;

import org.eclipse.emf.ecp.core.ECPRepository;

/**
 * This interface is used on classes that are aware of the repository they belong to.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 *
 */
public interface ECPRepositoryAware extends ECPProviderAware {
	/**
	 * This returns the {@link ECPRepository} this object references in any way.
	 *
	 * @return the known {@link ECPRepository}
	 */
	ECPRepository getRepository();
}
