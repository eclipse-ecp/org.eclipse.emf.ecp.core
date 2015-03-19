/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.preview.common;

import org.eclipse.emf.ecp.view.spi.model.LocalizationAdapter;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.internal.view.model.localization.LocalizationViewModelService;

/**
 * Specific Preview Service, that changes the label text for the preview.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class PreviewLocalizationViewModelService extends LocalizationViewModelService {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.internal.view.model.localization.LocalizationViewModelService#localize(org.eclipse.emf.ecp.view.spi.model.LocalizationAdapter,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement)
	 */
	@Override
	protected void localize(LocalizationAdapter localizationAdapter, VElement vElement) {
		if (vElement.getName() == null) {
			vElement.setLabel(""); //$NON-NLS-1$
		} else if (vElement.getName().startsWith("%")) { //$NON-NLS-1$
			vElement.setLabel(vElement.getName().substring(1));
		} else {
			vElement.setLabel(vElement.getName());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.internal.view.model.localization.LocalizationViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return super.getPriority() + 1;
	}

}
