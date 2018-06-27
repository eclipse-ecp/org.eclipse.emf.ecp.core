/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import java.net.URL;
import java.util.Enumeration;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.internal.core.services.label.BundleResolverImpl;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * Create a label provider for {@link EClass}es that doesn't present them as they
 * are in the Ecore model editor, but instead using their EMF.Edit localized names
 * and icons.
 */
@SuppressWarnings("restriction") // for BundleResolver API
final class EClassLabelProvider implements ILabelProvider {
	private final EMFFormsLocalizationService l10nService;
	private final BundleResolver bundleResolver;

	/**
	 * Initializes me with the EMF Forms localization service from which I get
	 * the EMF.Edit plug-in strings.
	 *
	 * @param l10nService the localization service
	 */
	EClassLabelProvider(EMFFormsLocalizationService l10nService) {
		super();

		this.l10nService = l10nService;
		bundleResolver = new BundleResolverImpl();
	}

	@Override
	public void dispose() {
		// I allocate nothing disposable
	}

	@Override
	public String getText(Object element) {
		if (!(element instanceof EClass)) {
			return String.valueOf(element);
		}

		final EClass eClass = (EClass) element;
		Bundle editBundle;

		try {
			editBundle = bundleResolver.getEditBundle(eClass);
		} catch (final NoBundleFoundException e) {
			editBundle = null;
		}

		return editBundle != null
			? l10nService.getString(editBundle, String.format("_UI_%s_type", eClass.getName())) //$NON-NLS-1$
			: eClass.getName();
	}

	@Override
	public Image getImage(Object element) {
		if (!(element instanceof EClass)) {
			return null;
		}

		final EClass eClass = (EClass) element;
		Bundle editBundle;

		Enumeration<URL> imageURLs = null;
		try {
			editBundle = bundleResolver.getEditBundle(eClass);
			imageURLs = editBundle.findEntries("icons/full/obj16", //$NON-NLS-1$
				String.format("%s.*", eClass.getName()), //$NON-NLS-1$
				false);
		} catch (final NoBundleFoundException e) {
			// Expected
		}

		// Don't keep these in a resource manager for disposal because the images
		// in the EMF image registry must never be disposed: they are shared
		return imageURLs != null && imageURLs.hasMoreElements()
			? ExtendedImageRegistry.getInstance().getImage(imageURLs.nextElement())
			: null;
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// I do not notify, so don't need to track listeners
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// I do not notify, so don't need to track listeners
	}

}