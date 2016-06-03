/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Neil Mackenzie - initial implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core.rap;

import org.eclipse.rap.rwt.service.UISessionListener;

/**
 * This class provides the current session ID.
 *
 * @author neilmack
 *
 */
public interface SessionProvider {
	/**
	 * get the current sessions ID.
	 *
	 * @return the current sessions ID
	 */
	String getSessionId();

	/**
	 * registers a listener to the current RAP UI session.
	 *
	 * @param listener The UISessionListener to register
	 */
	void registerListenerWithSession(UISessionListener listener);

}
