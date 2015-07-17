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
package org.eclipse.emf.ecp.view.internal.util.swt;

import java.net.URL;

import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * An {@link ImageRegistryService} which expects exactly one UI Thread.
 *
 * @author Eugen Neufeld
 *
 */
public class RCPImageRegistryService implements ImageRegistryService {

	private final ImageRegistry registry;

	/**
	 * The default constructor.
	 */
	public RCPImageRegistryService() {
		registry = new ImageRegistry();
	}

	@Override
	public Image getImage(Bundle bundle, String path) {
		Image image = registry.get(path);
		if (image == null) {
			final URL url = bundle.getResource(path);
			if (url == null) {
				return null;
			}
			image = ImageDescriptor.createFromURL(url).createImage();
			registry.put(path, image);
		}
		return image;
	}

	@Override
	public Image getImage(URL url) {
		Image image = registry.get(url.toString());
		if (image == null) {
			image = ImageDescriptor.createFromURL(url).createImage();
			registry.put(url.toString(), image);
		}
		return image;
	}

}
