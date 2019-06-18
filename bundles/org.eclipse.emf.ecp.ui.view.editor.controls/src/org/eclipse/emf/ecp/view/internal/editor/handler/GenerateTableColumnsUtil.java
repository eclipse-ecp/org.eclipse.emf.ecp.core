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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * Utility class for common functionality of table column generators.
 *
 * @author Lucas Koehler
 *
 */
final class GenerateTableColumnsUtil {

	private GenerateTableColumnsUtil() {
		// utility class
	}

	/**
	 * Opens a dialog to select a sub class of the given EClass in order to generate table columns for the sub class.
	 *
	 * @param baseEClass The base EClass to select a sub class of
	 * @return The selected sub class or nothing if the dialog is cancelled or closed
	 */
	static Optional<EClass> selectSubClass(EClass baseEClass) {
		EClass eclass = baseEClass;
		final Collection<EClass> subClasses = EMFUtils.getSubClasses(eclass);

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(
			adapterFactory);

		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(Display.getDefault().getActiveShell(),
			labelProvider);
		dialog.setTitle("Select EClass"); //$NON-NLS-1$
		dialog.setMessage("Select a subclass to generate columns for all attributes from this EClass."); //$NON-NLS-1$
		dialog.setMultipleSelection(false);
		dialog.setElements(subClasses.toArray(new EClass[subClasses.size()]));

		if (dialog.open() == Window.OK) {
			final Object firstResult = dialog.getFirstResult();
			if (firstResult != null) {
				eclass = (EClass) firstResult;
			}
		} else {
			eclass = null;
		}

		labelProvider.dispose();
		adapterFactory.dispose();

		return Optional.ofNullable(eclass);
	}
}
