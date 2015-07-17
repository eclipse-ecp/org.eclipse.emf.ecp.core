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
package org.eclipse.emf.ecp.core.rap.sessionprovider.test;

import org.eclipse.emf.ecp.core.rap.SessionProvider;

/**
 * This s a mock session provider for testing, it does not
 * recieve a web session.
 *
 * @author neilmack
 *
 */
public class MockSessionProvider implements SessionProvider {

	/**
	 *
	 */
	public enum SessionProviderType {
		/**
		 * SAME_SESSION_ID relates to test where succesive
		 * calls are from the same session, DIFFERENT_SESSION_ID refers to
		 * when succesive calls are form different sessions.
		 */
		SAME_SESSION_ID, DIFFERENT_SESSION_ID;
	}

	/**
	 * session ID.
	 */
	private int sessionId;

	/**
	 * The type od session, types defined in SessionProviderType enum.
	 */
	private static SessionProviderType type;
	/**
	 * THe singleton instance.
	 */
	private static MockSessionProvider instance;

	/**
	 * default constructor.
	 */
	public MockSessionProvider() {
		instance = this;
	}

	/**
	 * returns the provider instance.
	 *
	 * @return the provider instance
	 */
	public static MockSessionProvider getInstance() {
		if (instance == null) {
			instance = new MockSessionProvider();
		}
		return instance;
	}

	/**
	 * sets the type of the session provider. this determines whether
	 * the provider gives the same id each time it is asked for a sessionID
	 * or a different one each time.
	 *
	 * @param type the type
	 */
	public static void setSessionProvider(SessionProviderType type) {
		MockSessionProvider.type = type;
	}

	@Override
	public final String getSessionId() {
		if (type.equals(SessionProviderType.SAME_SESSION_ID)) {
			return "1212"; //$NON-NLS-1$
		}
		return (++sessionId) + ""; //$NON-NLS-1$

	}

}
