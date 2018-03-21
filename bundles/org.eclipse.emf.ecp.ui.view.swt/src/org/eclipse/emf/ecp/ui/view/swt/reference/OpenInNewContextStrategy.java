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
package org.eclipse.emf.ecp.ui.view.swt.reference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;

/**
 * A {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * <em>customization strategy</em> for opening a newly created object for editing its details.
 *
 * @since 1.16
 *
 * @see org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService
 */
public interface OpenInNewContextStrategy {
	/**
	 * The default strategy. It opens the new object in a dialog for focused editing.
	 */
	OpenInNewContextStrategy DEFAULT = new OpenInNewContextStrategy() {
		@Override
		public boolean openInNewContext(EObject owner, EReference reference, EObject object) {
			final Dialog dialog = new EditNewObjectDialog(Display.getDefault().getActiveShell(), object);

			new ECPDialogExecutor(dialog) {
				@Override
				public void handleResult(int codeResult) {
					// Nothing to do
				}
			}.execute();

			return true;
		}
	};

	/**
	 * Open a new {@code object} for editing its details in its own context.
	 *
	 * @param owner the container of the {@code object}, or {@code null} if it is contained in a resource
	 * @param reference the reference of the {@code owner} in which the {@code object} is contained,
	 *            or {@code null} if it is contained in a resource
	 * @param object the new object to be opened for editing
	 *
	 * @return {@code true} if the {@code object} was opened for editing by this
	 *         strategy; {@code false}, otherwise
	 */
	boolean openInNewContext(EObject owner, EReference reference, EObject object);

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for open strategies.
	 *
	 * @since 1.16
	 */
	public interface Provider extends Vendor<OpenInNewContextStrategy> {
		// Nothing to add to the superinterface
	}

}
