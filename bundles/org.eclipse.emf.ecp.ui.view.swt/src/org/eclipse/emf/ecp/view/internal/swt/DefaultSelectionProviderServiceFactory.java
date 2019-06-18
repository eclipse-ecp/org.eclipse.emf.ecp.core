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
package org.eclipse.emf.ecp.view.internal.swt;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.swt.services.DefaultSelectionProviderService;
import org.eclipse.emf.ecp.view.spi.swt.services.ECPSelectionProviderService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;

/**
 * Factory for the default {@link ECPSelectionProviderService} implementation.
 */
@Component
public class DefaultSelectionProviderServiceFactory implements EMFFormsViewServiceFactory<ECPSelectionProviderService> {

	/**
	 * Initializes me.
	 */
	public DefaultSelectionProviderServiceFactory() {
		super();
	}

	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.LAZY;
	}

	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.GLOBAL;
	}

	@Override
	public double getPriority() {
		return 0;
	}

	@Override
	public Class<ECPSelectionProviderService> getType() {
		return ECPSelectionProviderService.class;
	}

	@Override
	public ECPSelectionProviderService createService(EMFFormsViewContext emfFormsViewContext) {
		if (!(emfFormsViewContext instanceof ViewModelContext)) {
			throw new IllegalArgumentException("emfFormsViewContext not a view model context"); //$NON-NLS-1$
		}

		final ViewModelContext viewModelContext = (ViewModelContext) emfFormsViewContext;
		final ECPSelectionProviderService result = new DefaultSelectionProviderService();
		result.instantiate(viewModelContext);
		return result;
	}

}
