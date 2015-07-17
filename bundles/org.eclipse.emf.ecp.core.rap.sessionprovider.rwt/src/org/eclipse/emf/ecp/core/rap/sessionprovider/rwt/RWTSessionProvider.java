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
package org.eclipse.emf.ecp.core.rap.sessionprovider.rwt;

import org.eclipse.emf.ecp.core.rap.SessionProvider;
import org.eclipse.rap.rwt.RWT;

/**
 * This class provides the current session ID.
 *
 * @author neilmack
 *
 */
public class RWTSessionProvider implements SessionProvider {

	/**
	 * get the current sessions ID.
	 *
	 * @return the current sessions ID
	 */
	@Override
	public final String getSessionId() {
		final String sessionId = RWT.getUISession().toString();
		return sessionId;
	}

}
