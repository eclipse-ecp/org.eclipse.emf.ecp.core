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
package org.eclipse.emf.ecp.core.rap.sessionprovider.concurrent.test;

import org.eclipse.emf.ecp.core.rap.SessionProvider;
import org.eclipse.rap.rwt.service.UISessionListener;

/**
 * This s a mock session provider for testing, it does not
 * receive a web session.
 *
 * @author neil
 *
 */
public class MockConcurrentSessionProvider implements SessionProvider {

	/**
	 * The singleton instance.
	 */
	private static MockConcurrentSessionProvider instance;

	/**
	 * default constructor.
	 */
	public MockConcurrentSessionProvider() {
		instance = this;
	}

	/**
	 * returns the sessionprovider instance.
	 *
	 * @return the sessionprovider instance
	 */
	public static MockConcurrentSessionProvider getInstance() {
		if (instance == null) {
			instance = new MockConcurrentSessionProvider();
		}
		return instance;
	}

	@Override
	public final String getSessionId() {

		return Thread.currentThread().getName();

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.core.rap.SessionProvider#registerListenerWithSession(UISessionListener)
	 */
	@Override
	public void registerListenerWithSession(UISessionListener listener) {
		// do nothing since this is the Mock implementation.
	}

}
