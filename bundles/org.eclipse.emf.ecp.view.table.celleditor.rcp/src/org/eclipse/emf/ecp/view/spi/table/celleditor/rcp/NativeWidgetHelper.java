/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 * Christian W. Damus - bug 545686
 ******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2006, 2014 Tom Schindl and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 * Florian Potschka <signalrauschen@gmail.com> - Bug 260061
 * Alexander Ljungberg <siker@norwinter.com> - Bug 260061
 * Jeanderson Candido <http://jeandersonbc.github.io> - Bug 414565
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.celleditor.rcp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Util class for faking native widgets.
 *
 * @author jfaltermeier
 *
 */
public final class NativeWidgetHelper {

	private static final String WS_CARBON = "carbon";//$NON-NLS-1$
	private static final String WS_COCOA = "cocoa";//$NON-NLS-1$

	private static final String CHECKED_DEFAULT = "icons/checked.png"; //$NON-NLS-1$
	private static final String UNCHECKED_DEFAULT = "icons/unchecked.png"; //$NON-NLS-1$

	private static final Lock LOCK = new ReentrantLock(true);
	private static boolean initalized;

	private static final Bundle BUNDLE;
	private static final ServiceTracker<ImageRegistryService, ImageRegistryService> IMAGE_REGISTRY_SERVICE_TRACKER;

	private static ImageData checked;
	private static ImageData unchecked;

	private static Image defaultCheckedImage;
	private static Image defaultUncheckedImage;

	static {
		BUNDLE = FrameworkUtil.getBundle(NativeWidgetHelper.class);
		IMAGE_REGISTRY_SERVICE_TRACKER = new ServiceTracker<>(BUNDLE.getBundleContext(), ImageRegistryService.class,
			new ImageRegistryServiceCustomizer());
		IMAGE_REGISTRY_SERVICE_TRACKER.open();
	}

	private NativeWidgetHelper() {
		// util
	}

	/**
	 * Creates screenshots of the platform's native checkbox in checked and unchecked state. The images can be accessed
	 * via {@link NativeWidgetHelper#getCheckBoxImage(Control, CheckBoxState)}.
	 *
	 * @param control a control which provides the {@link org.eclipse.swt.widgets.Display Display} and {@link Shell} to
	 *            create the screen shots
	 */
	public static void initCheckBoxImages(Control control) {
		try {
			LOCK.lock();
			if (!initalized) {
				createCheckBoxImage(control, true);
				createCheckBoxImage(control, false);
				initalized = true;
			}
		} finally {
			LOCK.unlock();
		}
	}

	/**
	 * Returns the image of a checkbox. If {@link NativeWidgetHelper#initCheckBoxImages(Control)} was called beforehand
	 * this will return images resembling the platform's native widgets. Otherwise a default image (Windows 7) will be
	 * returned.
	 *
	 * @param control a control which provides the {@link org.eclipse.swt.widgets.Display Display} to create the image
	 * @param state the state of the checkbox
	 * @return the image
	 */
	public static Image getCheckBoxImage(Control control, CheckBoxState state) {
		try {
			LOCK.lock();
			switch (state) {

			case checked:
				if (checked != null) {
					return new Image(control.getDisplay(), checked);
				}
				return defaultCheckedImage;
			case unchecked:
				if (unchecked != null) {
					return new Image(control.getDisplay(), unchecked);
				}
				return defaultUncheckedImage;
			default:
				break;

			}
		} finally {
			LOCK.unlock();
		}

		return null;

	}

	/**
	 * <p>
	 * Fills the image cache for checkboxes.
	 * </p>
	 * <p>
	 * This is based on org.eclipse.jface.snippets.viewers.Snippet061FakedNativeCellEditor
	 * </p>
	 *
	 * @param control the control
	 * @param type <code>true</code> for checked state, <code>false</code> otherwise
	 */
	private static void createCheckBoxImage(Control control, boolean type) {
		// Hopefully no platform uses exactly this color because we'll make
		// it transparent in the image.

		final Display display = Display.getDefault();
		final Color greenScreen = new Color(display, 3, 223, 7);

		final Shell shell = new Shell(control.getShell());
		ImageData imageData = null;

		// otherwise we have a default gray color
		shell.setBackground(greenScreen);

		if (isMac()) {
			final Button button2 = new Button(shell, SWT.CHECK);
			final Point bsize = button2.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			// otherwise an image is stretched by width
			bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
			bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
			button2.setSize(bsize);
			button2.setLocation(100, 100);
		}

		final Button button = new Button(shell, SWT.CHECK);
		button.setBackground(greenScreen);
		button.setSelection(type);

		// otherwise an image is located in a corner
		button.setLocation(1, 1);
		final Point bsize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		// otherwise an image is stretched by width
		bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
		bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
		button.setSize(bsize);

		shell.setSize(bsize);
		shell.setVisible(false);

		shell.open();

		final GC gc = new GC(shell);
		final Image image = new Image(display, bsize.x, bsize.y);
		gc.copyArea(image, 0, 0);
		gc.dispose();
		shell.close();

		imageData = image.getImageData();
		imageData.transparentPixel = imageData.palette.getPixel(greenScreen
			.getRGB());

		if (type) {
			checked = imageData;
		} else {
			unchecked = imageData;
		}

		image.dispose();
	}

	private static boolean isMac() {
		final String ws = SWT.getPlatform();
		return WS_CARBON.equals(ws) || WS_COCOA.equals(ws);
	}

	/**
	 * Enum describing the state of a checkbox.
	 *
	 * @author jfaltermeier
	 *
	 */
	public enum CheckBoxState {
		/**
		 * The enabled, visible, checked state.
		 */
		checked,

		/**
		 * The enabled, visible, uncheched state.
		 */
		unchecked
	}

	/**
	 * Service-tracker customizer that uses the image-registry service, when it is
	 * discovered or updated, to get and cache the default checked and unchecked images.
	 */
	private static final class ImageRegistryServiceCustomizer
		implements ServiceTrackerCustomizer<ImageRegistryService, ImageRegistryService> {

		@Override
		public ImageRegistryService addingService(ServiceReference<ImageRegistryService> reference) {
			final ImageRegistryService result = BUNDLE.getBundleContext().getService(reference);

			onUI(() -> updateImages(result));

			return result;
		}

		@Override
		public void modifiedService(ServiceReference<ImageRegistryService> reference, ImageRegistryService service) {
			onUI(() -> updateImages(service));
		}

		@Override
		public void removedService(ServiceReference<ImageRegistryService> reference, ImageRegistryService service) {
			onUI(() -> updateImages(null));
		}

		private void onUI(Runnable action) {
			final Display display = Display.getCurrent();
			if (display != null) {
				action.run();
			} else {
				Display.getDefault().syncExec(action);
			}
		}

		private void updateImages(ImageRegistryService imageRegistryService) {
			LOCK.lock();

			try {
				if (imageRegistryService != null) {
					defaultCheckedImage = imageRegistryService.getImage(BUNDLE, CHECKED_DEFAULT);
					defaultUncheckedImage = imageRegistryService.getImage(BUNDLE, UNCHECKED_DEFAULT);
				} else {
					defaultCheckedImage = null;
					defaultUncheckedImage = null;
				}
			} finally {
				LOCK.unlock();
			}
		}
	}
}
