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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
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
	private final Adapter adapter;
	private final Notifier input;
	private final TreeViewer viewer;

	/**
	 * Default constructor.
	 *
	 * @param viewer the {@link TreeViewer}
	 * @param input the input notifier
	 */
	public ECPValidationServiceLabelDecorator(TreeViewer viewer, Notifier input) {
		this.viewer = viewer;
		this.input = input;
		cache = new DiagnosticCache();
		adapter = new ChangeListener();
		input.eAdapters().add(adapter);
		TreeIterator<Object> allContents;
		if (ResourceSet.class.isInstance(input)) {
			allContents = EcoreUtil.getAllContents(ResourceSet.class.cast(input), false);
		} else if (Resource.class.isInstance(input)) {
			allContents = EcoreUtil.getAllContents(Resource.class.cast(input), false);
		} else if (EObject.class.isInstance(input)) {
			allContents = EcoreUtil.getAllContents(EObject.class.cast(input), false);
		} else {
			return;
		}
		while (allContents.hasNext()) {
			final Object next = allContents.next();
			if (!EObject.class.isInstance(next)) {
				continue;
			}
			updateCacheWithoutRefresh(EObject.class.cast(next), cache);
		}
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

	private static void handleRemove(EObject oldValue, DiagnosticCache cache) {
		final Set<EObject> toRemove = new LinkedHashSet<EObject>();
		toRemove.add(oldValue);
		final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(oldValue, false);
		while (iterator.hasNext()) {
			toRemove.add(iterator.next());
		}
		for (final EObject object : toRemove) {
			cache.remove(object);
		}
	}

	/**
	 * Called in order to update the cache. This also triggers a viewer refresh.
	 * 
	 * @param element The element which changed
	 * @param cache The cache to update
	 */
	protected void updateCache(EObject element, DiagnosticCache cache) {
		final Diagnostic diagnostic = getDiagnostic(element);
		final Set<EObject> update = cache.update(element, diagnostic);
		for (final EObject eObject : update) {
			viewer.refresh(eObject, true);
		}
	}

	/**
	 * Called in order to update the cache. This also triggers a viewer update.
	 * 
	 * @param element The element which changed
	 * @param cache The cache to update
	 */
	protected void updateCacheWithoutRefresh(EObject element, DiagnosticCache cache) {
		final Diagnostic diagnostic = getDiagnostic(element);
		final Set<EObject> update = cache.update(element, diagnostic);
		viewer.update(update.toArray(), null);
		viewer.update(element, null);
	}

	private static Diagnostic getDiagnostic(Object object) {
		if (!EObject.class.isInstance(object)) {
			return Diagnostic.OK_INSTANCE;
		}
		final EObject eObject = EObject.class.cast(object);
		EValidator validator = EValidator.Registry.INSTANCE.getEValidator(eObject.eClass().getEPackage());
		final BasicDiagnostic diagnostics = Diagnostician.INSTANCE.createDefaultDiagnostic(eObject);

		if (validator == null) {
			validator = new EObjectValidator();
		}
		final Map<Object, Object> context = new HashMap<Object, Object>();
		context.put(EValidator.SubstitutionLabelProvider.class, Diagnostician.INSTANCE);
		context.put(EValidator.class, validator);

		validator.validate(eObject, diagnostics, context);
		return diagnostics;
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
		input.eAdapters().remove(adapter);
	}

	/**
	 *
	 * An adapter which will update the cache.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class ChangeListener extends EContentAdapter {
		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (notification.isTouch()) {
				return;
			}
			handleStructuralChangeNotification(notification);
			if (!EObject.class.isInstance(notification.getNotifier())) {
				return;
			}
			updateCacheWithoutRefresh(EObject.class.cast(notification.getNotifier()), cache);
		}

		private void handleStructuralChangeNotification(Notification notification) {
			switch (notification.getEventType()) {
			case Notification.REMOVE: {
				handleSingleRemove(notification);
				break;
			}
			case Notification.REMOVE_MANY: {
				@SuppressWarnings("unchecked")
				final List<Object> deleted = (List<Object>) notification.getOldValue();
				if (deleted.isEmpty() || !EObject.class.isInstance(deleted.get(0))) {
					break;
				}
				for (final Object oldValue : deleted) {
					handleRemove(EObject.class.cast(oldValue), cache);
				}
				break;
			}
			case Notification.ADD: {
				handleAdd(notification);
				break;
			}
			case Notification.ADD_MANY: {
				@SuppressWarnings("unchecked")
				final List<Object> added = (List<Object>) notification.getNewValue();
				if (added.isEmpty() || !EObject.class.isInstance(added.get(0))) {
					break;
				}
				for (final Object newValue : added) {
					final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(EObject.class.cast(newValue),
						false);
					while (iterator.hasNext()) {
						updateCacheWithoutRefresh(iterator.next(), cache);
					}
					updateCache(EObject.class.cast(newValue), cache);
				}
				break;

			}
			case Notification.SET: {
				if (!EReference.class.isInstance(notification.getFeature())
					|| !EReference.class.cast(notification.getFeature()).isContainment()) {
					break;
				}
				handleAdd(notification);

				break;
			}
			default:
				break;
			}
		}

		private void handleSingleRemove(Notification notification) {
			final Object oldValue = notification.getOldValue();
			if (!EObject.class.isInstance(oldValue)) {
				return;
			}
			handleRemove(EObject.class.cast(oldValue), cache);
		}

		private void handleAdd(Notification notification) {
			final Object newValue = notification.getNewValue();
			if (!EObject.class.isInstance(newValue)) {
				return;
			}
			final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(EObject.class.cast(newValue), false);
			while (iterator.hasNext()) {
				updateCacheWithoutRefresh(iterator.next(), cache);
			}
			updateCache(EObject.class.cast(newValue), cache);
		}
	}

}
