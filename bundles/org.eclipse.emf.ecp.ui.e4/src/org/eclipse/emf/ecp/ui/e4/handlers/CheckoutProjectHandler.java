/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.e4.handlers;

import java.util.Collections;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to check out a {@link ECPCheckoutSource}.
 * 
 * @author Jonas
 * 
 */
public class CheckoutProjectHandler {
	/**
	 * checks out a {@link ECPCheckoutSource}.
	 * 
	 * @param shell shell to display a dialog
	 * @param checkoutSource the {@link ECPCheckoutSource} to be chcked out
	 */
	@Execute
	public void execute(Shell shell,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPCheckoutSource checkoutSource) {
		ECPHandlerHelper.checkout(Collections.singletonList(checkoutSource), shell);
	}

	/**
	 * checks if the current selection is an {@link ECPCheckoutSource}.
	 * 
	 * @param checkoutSource the current selection, if it is an {@link ECPCheckoutSource} or null otherwise
	 * @return if the current selection is an {@link ECPCheckoutSource}
	 */
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPCheckoutSource checkoutSource) {
		return checkoutSource != null;
	}
}
