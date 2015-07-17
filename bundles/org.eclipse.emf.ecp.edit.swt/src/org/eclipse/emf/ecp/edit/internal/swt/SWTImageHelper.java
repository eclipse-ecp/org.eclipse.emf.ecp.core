/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.swt.graphics.Image;

/**
 * Helper methods for handling of SWT images.
 */
public final class SWTImageHelper {

	private SWTImageHelper() {
	}

	/**
	 * @param image an {@link URI}. {@link URL} or {@link ComposedImage} describing the {@link Image} to be retrieved
	 *
	 * @return the {@link Image}
	 */
	public static Image getImage(Object image) {
		if (ComposedImage.class.isInstance(image)) {
			image = ((ComposedImage) image).getImages().get(0);
		}
		if (URI.class.isInstance(image)) {
			try {
				image = new URL(((URI) image).toString());
			} catch (final MalformedURLException ex) {
				Activator.logException(ex);
			}
		}
		if (URL.class.isInstance(image)) {
			return Activator.getImage((URL) image);
		}
		return null;
	}

}
