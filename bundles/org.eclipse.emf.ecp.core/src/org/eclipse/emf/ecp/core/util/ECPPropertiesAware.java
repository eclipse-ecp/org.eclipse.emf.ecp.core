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

/**
 * Interface used on classes which have properties attached.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ECPPropertiesAware {
	/**
	 * This returns the {@link ECPProperties} attached to this object.
	 *
	 * @return the {@link ECPProperties} this object knows of
	 */
	ECPProperties getProperties();
}
