/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Util class for common SWT-related layout tasks.
 *
 * @author jfaltermeier
 * @since 1.8
 *
 */
public final class EMFFormsSWTLayoutUtil {

	private static EMFFormsSWTLayoutOptimizer layoutOptimizer;

	private EMFFormsSWTLayoutUtil() {

	}

	private static synchronized EMFFormsSWTLayoutOptimizer getLayoutOptimizer() {
		if (layoutOptimizer == null) {
			final Bundle bundle = FrameworkUtil.getBundle(EMFFormsSWTLayoutUtil.class);
			final BundleContext bundleContext = bundle.getBundleContext();
			final ServiceReference<EMFFormsSWTLayoutOptimizer> serviceReference = bundleContext
				.getServiceReference(EMFFormsSWTLayoutOptimizer.class);
			if (serviceReference != null) {
				layoutOptimizer = bundleContext.getService(serviceReference);
			} else {
				layoutOptimizer = new EMFFormsSWTLayoutDelayed();
			}
		}
		return layoutOptimizer;
	}

	/**
	 * This methods helps to update the size of a parent composite when the size of a child has changed. This is needed
	 * for {@link ScrolledComposite} and {@link ExpandBar}.
	 *
	 * @param control the control with a changed size.
	 */
	public static void adjustParentSize(Control control) {
		if (control.isDisposed()) {
			return;
		}
		Composite parent = control.getParent();
		while (parent != null) {

			if (ScrolledComposite.class.isInstance(parent)) {
				final ScrolledComposite scrolledComposite = ScrolledComposite.class.cast(parent);
				final Control content = scrolledComposite.getContent();
				if (content == null) {
					return;
				}
				final Point point = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrolledComposite.setMinSize(point);
			} else if (ExpandBar.class.isInstance(parent)) {
				final ExpandBar bar = ExpandBar.class.cast(parent);
				int oldBarHeight = 0;
				int barHeight = 0;
				for (final ExpandItem item : bar.getItems()) {
					final Control itemControl = item.getControl();
					if (itemControl != null) {
						oldBarHeight += item.getHeight();
						final int height = itemControl.computeSize(bar.getSize().x, SWT.DEFAULT, true).y;
						barHeight += height;
						item.setHeight(height);
					}
				}
				if (bar.getItemCount() > 0) {
					/* only update layout data when there is at least one item */
					updateLayoutData(bar.getLayoutData(), oldBarHeight, barHeight);
				}
			}

			if (parent.getParent() == null) {
				getLayoutOptimizer().layout(parent);
			}

			else if (Shell.class.isInstance(parent)) {
				getLayoutOptimizer().layout(parent);
			}

			parent = parent.getParent();
		}

	}

	private static void updateLayoutData(final Object layoutData, int oldHeight, int newHeight) {
		if (layoutData instanceof GridData) {
			final GridData gridData = (GridData) layoutData;
			if (gridData.heightHint == -1) {
				return;
			}
			final int heightHint = gridData.heightHint - oldHeight + newHeight;
			gridData.heightHint = heightHint;

		}
	}

}