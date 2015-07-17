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
package org.eclipse.emf.ecp.view.spi.util.swt;

import java.net.URL;

import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * The {@link ImageRegistryService} allows to retrieve Images based on a Bundle and a path within or an URL. The images
 * are loaded only once.
 *
 * @author Eugen Neufeld
 *
 */
public interface ImageRegistryService {
	/**
	 * Retrieve an {@link Image} based on a Bundle and a path within.
	 *
	 * @param bundle the Bundle to get the image from
	 * @param path the path to the image within the bundle
	 * @return the loaded {@link Image}
	 */
	Image getImage(Bundle bundle, String path);

	/**
	 * Retrieve an {@link Image} based on an {@link URL}.
	 *
	 * @param url the {@link URL} to load the image from
	 * @return the loaded {@link Image}
	 */
	Image getImage(URL url);
}
