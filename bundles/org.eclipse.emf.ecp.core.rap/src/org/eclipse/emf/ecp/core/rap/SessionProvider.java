/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
