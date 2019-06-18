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
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bug 546899
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.validation;

import java.util.List;
import java.util.MissingResourceException;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * {@link ITableLabelProvider} for displaying {@link org.eclipse.emf.common.util.Diagnostic Diagnostics} in a Tree with
 * columns.
 *
 * @author jfaltermeier
 *
 */
public class ValidationLabelProvider implements ITableLabelProvider {

	private final ComposedAdapterFactory composedAdapterFactory;
	private final AdapterFactoryLabelProvider labelProvider;
	private final Image warning;
	private final Image error;

	/**
	 * Default constructor.
	 */
	public ValidationLabelProvider() {
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
		error = Activator.getImageDescriptor("icons/error_decorate.png").createImage(); //$NON-NLS-1$
		warning = Activator.getImageDescriptor("icons/warning_decorate.png").createImage(); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		composedAdapterFactory.dispose();
		labelProvider.dispose();
		error.dispose();
		warning.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		final Diagnostic diagnostic = (Diagnostic) element;
		switch (columnIndex) {
		case 0:
			return getImageForSeverity(diagnostic.getSeverity());
		default:
			return null;
		}
	}

	private Image getImageForSeverity(int severity) {
		switch (severity) {
		case Diagnostic.OK:
			return null;
		case Diagnostic.INFO:
			return null;
		case Diagnostic.WARNING:
			return warning;
		case Diagnostic.ERROR:
			return error;
		case Diagnostic.CANCEL:
			return null;
		default:
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		final Diagnostic diagnostic = (Diagnostic) element;
		final List<?> data = diagnostic.getData();
		switch (columnIndex) {
		case 0:
			return diagnostic.getMessage();
		case 1:
			if (data.size() > 0) {
				return labelProvider.getText(data.get(0));
			}
			return null;
		case 2:
			if (data.size() > 1) {
				return getFeatureName((EObject) data.get(0), (EStructuralFeature) data.get(1));
			}
			return null;
		default:
			return null;
		}
	}

	/**
	 * Get the localized name of a {@code feature} of an object.
	 *
	 * @param owner the object that owns the {@code feature}
	 * @param feature the feature for which to get the localized name
	 * @return the localized feature name, or just its simple name if not found
	 */
	protected String getFeatureName(EObject owner, EStructuralFeature feature) {
		String result = feature.getName();

		final IItemLabelProvider provider = (IItemLabelProvider) composedAdapterFactory.adapt(owner,
			IItemLabelProvider.class);
		if (provider instanceof ResourceLocator) {
			final EClass eClass = feature.getEContainingClass();
			final String key = String.format("_UI_%s_%s_feature", eClass.getName(), result); //$NON-NLS-1$

			try {
				final String l10nResult = ((ResourceLocator) provider).getString(key);
				if (l10nResult != null) {
					result = l10nResult;
				}
			} catch (final MissingResourceException e) {
				// That's okay. We'll just go with the feature name
			}
		}

		return result;
	}

}
