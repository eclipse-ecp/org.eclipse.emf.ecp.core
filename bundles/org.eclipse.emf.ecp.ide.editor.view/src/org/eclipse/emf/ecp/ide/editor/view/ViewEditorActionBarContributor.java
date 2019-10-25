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
package org.eclipse.emf.ecp.ide.editor.view;

import java.util.Collection;

import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.edit.spi.ConditionalDeleteService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Action-bar contributor for the View Model Editor.
 *
 * @since 1.20
 */
public class ViewEditorActionBarContributor extends EditingDomainActionBarContributor {

	/**
	 * Initializes me.
	 */
	public ViewEditorActionBarContributor() {
		super();
	}

	@Override
	protected DeleteAction createDeleteAction() {
		return new DeleteServiceAction();
	}

	/**
	 * Obtain a service of the given type from the {@linkplain #getViewModelContext() view model context}, if any.
	 *
	 * @param serviceType the type of service to obtain
	 * @return the service, or {@code null} if none can be obtained
	 *
	 * @param <T> the type of service to obtain
	 */
	protected <T> T getService(Class<T> serviceType) {
		final ViewModelContext context = getViewModelContext();
		return context == null ? null : context.getService(serviceType);
	}

	/**
	 * Obtain the editor's view model context.
	 *
	 * @return the view model context, or {@code null} if the editor has none
	 */
	protected ViewModelContext getViewModelContext() {
		return activeEditor == null ? null : activeEditor.getAdapter(ViewModelContext.class);
	}

	//
	// Nested types
	//

	/**
	 * Custom delete action that delegates to the {@link DeleteService}, if it's available.
	 */
	private class DeleteServiceAction extends DeleteAction {

		DeleteServiceAction() {
			super();
		}

		@Override
		public void run() {
			final DeleteService deleteService = getService(DeleteService.class);
			if (deleteService == null) {
				super.run();
			} else {
				@SuppressWarnings("unchecked")
				final Collection<Object> selection = getStructuredSelection().toList();
				// The delete service executes commands on the stack
				deleteService.deleteElements(selection);
			}
		}

		@Override
		public boolean updateSelection(IStructuredSelection selection) {
			final ConditionalDeleteService deleteService = ConditionalDeleteService.getDeleteService(getViewModelContext());

			return !selection.isEmpty() && deleteService.canDelete(selection.toList());
		}

	}
}
