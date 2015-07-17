/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author Eugen Neufeld
 *
 */
public class ImageDescriptorToImage {
	private final ImageDescriptor imageDescriptor;

	/**
	 * Constructor.
	 *
	 * @param imageDescriptor the {@link ImageDescriptor}
	 */
	public ImageDescriptorToImage(ImageDescriptor imageDescriptor) {
		this.imageDescriptor = imageDescriptor;
	}

	private Image image;

	/**
	 * Returns the image of this {@link ImageDescriptor}. The image is only created once.
	 *
	 * @return the {@link Image}
	 */
	public Image getImage() {
		if (image == null) {
			image = imageDescriptor.createImage();
		}
		return image;
	}

	/**
	 * The {@link ImageDescriptor}.
	 *
	 * @return the {@link ImageDescriptor}
	 */
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}
}
