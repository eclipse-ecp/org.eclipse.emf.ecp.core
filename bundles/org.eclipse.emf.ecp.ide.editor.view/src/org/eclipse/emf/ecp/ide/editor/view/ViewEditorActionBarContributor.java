/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.editor.view;

import java.util.Collection;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;

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

	@Override
	protected CopyAction createCopyAction() {
		return new CopyViewTreeAction();
	}

	@Override
	protected PasteAction createPasteAction() {
		return new PasteViewTreeAction();
	}

	@Override
	protected CutAction createCutAction() {
		return new CutViewTreeAction();
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

	/**
	 * Returns whether the tree showing the view model's VElement hierarchy is currently active.
	 * 
	 * @return <code>true</code> if the tree is active; <code>false</code> otherwise
	 */
	private boolean isViewTreeActive() {
		return Display.getCurrent().getFocusControl() instanceof Tree;
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
			final DeleteService deleteService = getService(DeleteService.class);
			if (deleteService == null) {
				return super.updateSelection(selection);
			}

			return !selection.isEmpty() && Stream.of(selection.toArray()).noneMatch(this::isRoot);
		}

		private boolean isRoot(Object object) {
			return object instanceof EObject && ((EObject) object).eContainer() == null;
		}

	}

	/** EMF copy action that is only enabled if the tree of the view model's VElement hierarchy is active. */
	private class CopyViewTreeAction extends CopyAction {

		@Override
		public boolean isEnabled() {
			return isViewTreeActive() && super.isEnabled();
		}

	}

	/** EMF paste action that is only enabled if the tree of the view model's VElement hierarchy is active. */
	private class PasteViewTreeAction extends PasteAction {

		@Override
		public boolean isEnabled() {
			return isViewTreeActive() && super.isEnabled();
		}

	}

	/** EMF cut action that is only enabled if the tree of the view model's VElement hierarchy is active. */
	private class CutViewTreeAction extends CutAction {

		@Override
		public boolean isEnabled() {
			return isViewTreeActive() && super.isEnabled();
		}

	}
}