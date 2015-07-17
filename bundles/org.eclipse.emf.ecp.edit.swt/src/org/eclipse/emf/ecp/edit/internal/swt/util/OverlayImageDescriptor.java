/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Shterev - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.edit.internal.swt.util;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * Allows one image descriptor to be overlayed on another image descriptor to generate a new image. Commonly used to
 * decorate an image with a second image decoration.
 *
 * @author Shterev
 */
public class OverlayImageDescriptor extends CompositeImageDescriptor {

	/** display the overlay image in the upper left corner. */
	public static final int UPPER_LEFT = 0;

	/** display the overlay image in the upper right corner. */
	public static final int UPPER_RIGHT = 1;

	/** display the overlay image in the lower right corner. */
	public static final int LOWER_RIGHT = 2;

	/** display the overlay image in the lower left corner. */
	public static final int LOWER_LEFT = 3;

	/** default image width. */
	private static final int DEFAULT_IMAGE_WIDTH = 19;

	/** default image height. */
	private static final int DEFAULT_IMAGE_HEIGHT = 19;

	/** overlay image. */
	private final ImageDescriptor overlayDesc;

	/** the position of the overlay image. */
	private final int overlayPos = LOWER_RIGHT;

	private final int offset = 3;

	private final ImageData backgroundData;

	/**
	 * OverlayImageDescriptor constructor.
	 *
	 * @param srcImage the base image
	 * @param overlayDesc the overlay image
	 * @param overlayPos the overlay position
	 */
	public OverlayImageDescriptor(Image srcImage, ImageDescriptor overlayDesc, int overlayPos) {
		assert null != srcImage;
		assert null != overlayDesc;
		backgroundData = srcImage.getImageData();
		this.overlayDesc = overlayDesc;
	}

	/**
	 * OverlayImageDescriptor constructor.
	 *
	 * @param backgroundData the base ImageData
	 * @param overlayDesc the overlay image
	 * @param overlayPos the overlay position
	 */
	public OverlayImageDescriptor(ImageData backgroundData, ImageDescriptor overlayDesc, int overlayPos) {
		assert null != backgroundData;
		assert null != overlayDesc;
		this.backgroundData = backgroundData;
		this.overlayDesc = overlayDesc;
	}

	/**
	 * Draws the given source image data into this composite image at the given position.
	 *
	 * @param width the width of the image.
	 * @param height the height of the image.
	 * @see org.eclipse.jface.resource.CompositeImageDescriptor#drawCompositeImage(int, int)
	 */
	@Override
	protected void drawCompositeImage(int width, int height) {
		// draw the base image

		if (backgroundData != null) {
			drawImage(backgroundData, 0, 0);
		}

		// draw the overlay image
		final ImageData overlayData = overlayDesc.getImageData();
		if (overlayData != null) {
			Point pos = null;
			switch (overlayPos) {
			case UPPER_LEFT:
				pos = new Point(-overlayData.width / 2, -overlayData.height / 2);
				break;
			case UPPER_RIGHT:
				pos = new Point(backgroundData.width - overlayData.width / 2, 0);
				break;
			case LOWER_RIGHT:
				pos = new Point(backgroundData.width - overlayData.width / 2, backgroundData.height
					- overlayData.height / 2);
				break;
			// default = LOWER_LEFT
			default:
				pos = new Point(0, backgroundData.height - overlayData.height / 2);
				break;
			}
			drawImage(overlayData, pos.x - offset, pos.y - offset);
		}
	}

	/**
	 * Retrieve the size of this composite image.
	 *
	 * @return the x and y size of the image expressed as a point object
	 * @see org.eclipse.jface.resource.CompositeImageDescriptor#getSize()
	 */
	@Override
	protected Point getSize() {
		return new Point(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
	}

}
