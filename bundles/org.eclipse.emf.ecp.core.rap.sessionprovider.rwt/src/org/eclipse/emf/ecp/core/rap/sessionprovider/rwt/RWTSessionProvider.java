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
package org.eclipse.emf.ecp.core.rap.sessionprovider.rwt;

import org.eclipse.emf.ecp.core.rap.SessionProvider;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.rap.rwt.service.UISessionListener;

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.core.rap.SessionProvider#registerListenerWithSession(org.eclipse.rap.rwt.service.UISessionListener)
	 */
	@Override
	public void registerListenerWithSession(UISessionListener listener) {
		final UISession uiSession = RWT.getUISession();
		uiSession.addUISessionListener(listener);

	}

}
