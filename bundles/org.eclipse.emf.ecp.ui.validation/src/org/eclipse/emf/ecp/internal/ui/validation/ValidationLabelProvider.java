/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.validation;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
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
				return ((EStructuralFeature) data.get(1)).getName();
			}
			return null;
		default:
			return null;
		}
	}
}
