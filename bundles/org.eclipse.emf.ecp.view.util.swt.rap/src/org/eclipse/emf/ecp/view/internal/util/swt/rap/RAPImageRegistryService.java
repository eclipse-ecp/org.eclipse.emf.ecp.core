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

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.UISession;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;

/**
 * The RAP instance of the ImageRegistryService.
 *
 * @author Eugen Neufeld
 *
 */
@Component(service = { ImageRegistryService.class }, property = { "service.ranking:Integer=5" })
public class RAPImageRegistryService implements ImageRegistryService, UISessionListener {

	private static final long serialVersionUID = 1223772305074418261L;
	private final Map<String, Map<String, WeakReference<Image>>> sessionRegistry = new LinkedHashMap<String, Map<String, WeakReference<Image>>>();

	/** Constructor. */
	public RAPImageRegistryService() {
	}

	@Override
	public void beforeDestroy(UISessionEvent event) {
		sessionRegistry.remove(event.getUISession().getId());
	}

	@Override
	public Image getImage(Bundle bundle, String path) {
		final URL url = bundle.getResource(path);
		if (url == null) {
			return null;
		}
		return getImage(url);
	}

	@Override
	public Image getImage(URL url) {
		final UISession uiSession = RWT.getUISession();
		uiSession.addUISessionListener(this);
		final String sessionId = uiSession.getId();
		if (!sessionRegistry.containsKey(sessionId)) {
			sessionRegistry.put(sessionId, new LinkedHashMap<String, WeakReference<Image>>());
		}
		final Map<String, WeakReference<Image>> registry = sessionRegistry.get(sessionId);
		final WeakReference<Image> weakRef = registry.get(url.toString());
		boolean loadImage = weakRef == null;
		Image image = null;
		if (weakRef != null) {
			image = weakRef.get();
			loadImage = image == null;
		}

		if (loadImage) {
			image = ImageDescriptor.createFromURL(url).createImage();
			registry.put(url.toString(), new WeakReference<Image>(image));
		}
		return image;
	}

}
