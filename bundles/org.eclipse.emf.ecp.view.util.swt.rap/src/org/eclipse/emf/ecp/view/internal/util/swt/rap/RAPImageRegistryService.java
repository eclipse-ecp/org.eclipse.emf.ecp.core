/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.util.swt.rap;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecp.view.internal.util.swt.RCPImageRegistryService;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

/**
 * The RAP instance of the ImageRegistryService.
 *
 * @author Eugen Neufeld
 *
 */
public class RAPImageRegistryService implements ServiceFactory<ImageRegistryService>, UISessionListener {

	private final Map<String, ImageRegistryService> sessionRegistry = new HashMap<String, ImageRegistryService>();

	public RAPImageRegistryService() {
	}

	@Override
	public ImageRegistryService getService(Bundle bundle,
		ServiceRegistration<ImageRegistryService> registration) {

		final UISession uiSession = RWT.getUISession();
		uiSession.addUISessionListener(this);
		final String sessionId = uiSession.getId();
		if (!sessionRegistry.containsKey(sessionId)) {
			sessionRegistry.put(sessionId, new RCPImageRegistryService());
		}
		return sessionRegistry.get(sessionId);
	}

	@Override
	public void ungetService(Bundle bundle,
		ServiceRegistration<ImageRegistryService> registration,
		ImageRegistryService service) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.rap.rwt.service.UISessionListener#beforeDestroy(org.eclipse.rap.rwt.service.UISessionEvent)
	 */
	@Override
	public void beforeDestroy(UISessionEvent event) {
		sessionRegistry.remove(event.getUISession().getId());
	}

}
