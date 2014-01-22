/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.handler;

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProjectWrapper;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UICheckoutController;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore Checkout Branch Helper delegating to the EMFStore {@link UICheckoutController}.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class CheckoutBranchHelper {
	private CheckoutBranchHelper() {
	}

	/**
	 * Delegates the call to {@link UICheckoutController}.
	 * 
	 * @param projectWrapper the {@link EMFStoreProjectWrapper}
	 * @param shell the {@link Shell}
	 */
	public static void checkoutBranch(EMFStoreProjectWrapper projectWrapper, Shell shell) {
		new UICheckoutController(shell, projectWrapper.getCheckoutData(), true).execute();
	}
}
