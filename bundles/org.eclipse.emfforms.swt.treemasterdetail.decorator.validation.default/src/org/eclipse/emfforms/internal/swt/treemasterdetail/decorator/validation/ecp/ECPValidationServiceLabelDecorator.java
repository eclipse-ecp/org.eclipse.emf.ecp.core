/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache;
import org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache.ValidationListener;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Decorator showing diagnostics.
 *
 * @author Johannes Faltermeier
 *
 */
public class ECPValidationServiceLabelDecorator implements ILabelDecorator {

	private final DiagnosticCache cache;
	private final TreeViewer viewer;

	/**
	 * Default constructor.
	 *
	 * @param viewer the {@link TreeViewer}
	 * @param input the input notifier
	 * @param cache the {@link DiagnosticCache}
	 */
	public ECPValidationServiceLabelDecorator(TreeViewer viewer, Notifier input, DiagnosticCache cache) {
		this.viewer = viewer;
		this.cache = cache;
		cache.registerValidationListener(new ValidationListener() {

			@Override
			public void revalidationOccurred(Collection<EObject> object, boolean potentialStructuralChange) {
				if (potentialStructuralChange) {
					for (final EObject o : object) {
						refreshViewer(o);
					}
				} else {
					for (final EObject o : object) {
						updateViewer(o);
					}
				}
			}
		});
		viewer.refresh();
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		if (image == null) {
			return image;
		}
		if (!EObject.class.isInstance(element) && !Resource.class.isInstance(element)) {
			return image;
		}

		final org.eclipse.emf.common.util.Diagnostic diagnostic = cache.getCachedValue(element);
		final int severity = diagnostic.getSeverity();
		final ImageDescriptor validationOverlayDescriptor = SWTValidationHelper.INSTANCE
			.getValidationOverlayDescriptor(severity);
		if (validationOverlayDescriptor == null) {
			return image;
		}
		final Rectangle bounds = image.getBounds();
		final Point size = new Point(bounds.width, bounds.height);
		final DecorationOverlayIcon icon = new DecorationOverlayIcon(image,
			new ImageDescriptor[] { validationOverlayDescriptor }, size);
		return icon.createImage();
	}

	/**
	 * Called in order to update the cache. This also triggers a viewer refresh.
	 *
	 * @param element The element which changed
	 */
	protected void refreshViewer(EObject element) {
		viewer.refresh(element, true);
	}

	/**
	 * Called in order to update the cache. This also triggers a viewer update.
	 *
	 * @param element The element which changed
	 */
	protected void updateViewer(EObject element) {
		viewer.update(element, null);
	}

	@Override
	public String decorateText(String text, Object element) {
		/* no op */
		return text;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		/* no op */
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		/* no op */
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		/* no op */
	}

	@Override
	public void dispose() {
		cache.dispose();
	}

}
