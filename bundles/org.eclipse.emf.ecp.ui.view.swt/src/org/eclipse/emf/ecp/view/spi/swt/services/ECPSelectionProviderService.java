/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.services;

import java.util.function.Supplier;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.selection.IMasterDetailSelectionProvider;
import org.eclipse.emf.ecp.view.spi.swt.selection.MasterDetailFocusAdapter;
import org.eclipse.emf.ecp.view.spi.swt.selection.MasterDetailSelectionProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;

/**
 * A mediator of selection providers for the {@link ViewModelContext}, to provide
 * a coherent selection provider for the rendering of a view.
 *
 * @since 1.20
 */
public interface ECPSelectionProviderService extends ViewModelService {

	/**
	 * Obtain a selection provider that aggregates the selection in the
	 * view model context. This should be suitable for use as, for example,
	 * the selection provider of an Eclipse workbench part site. The
	 * selection provider is never {@code null}, but if there are no
	 * registered providers to which it can delegate, it may have no useful effect.
	 *
	 * @return the selection provider
	 */
	ISelectionProvider getSelectionProvider();

	/**
	 * Register a selection provider for a given {@code element}. The effect
	 * is undefined if the {@code element} already has a provider registered.
	 * It is probably a good practice that only the renderer of the {@code element}
	 * be responsible for registering a selection provider for it.
	 *
	 * @param element an element in the view model
	 * @param selectionProvider a selection provider to register for it
	 *
	 * @throws NullPointerException if the either the element or the selection provider is {@code null}
	 */
	void registerSelectionProvider(VElement element, ISelectionProvider selectionProvider);

	/**
	 * Create a master-detail selection provider on the given {@code master} viewer.
	 *
	 * @param master the master viewer
	 * @param detailSupplier the supplier of the detail view currently presented
	 *            for the selection in the {@code master}
	 * @return a master-detail selection provider
	 *
	 * @since 1.21
	 */
	default IMasterDetailSelectionProvider createMasterDetailSelectionProvider(Viewer master,
		Supplier<? extends Control> detailSupplier) {

		final IMasterDetailSelectionProvider result = new MasterDetailSelectionProvider(master);
		final MasterDetailFocusAdapter adapter = new MasterDetailFocusAdapter(result, detailSupplier);
		master.getControl().addFocusListener(adapter);
		return result;
	}
}
