/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.categorization.swt;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.swt.graphics.Image;

/**
 * Helper class for creating an image for a {@link VTTabStyleProperty} validation URL.
 *
 * @author Johannes Faltermeier
 *
 */
public final class ValidationTabImageHelper {

	private ValidationTabImageHelper() {
		/* util */
	}

	/**
	 * @param tabStyle the tab style to use
	 * @param severity the severity of the diagnostic
	 * @return the validation icon or <code>null</code>
	 */
	public static Image getValidationIcon(Optional<VTTabStyleProperty> tabStyle, int severity) {
		if (!tabStyle.isPresent()) {
			return null;
		}
		String imageUrl = null;
		switch (severity) {
		case Diagnostic.OK:
			imageUrl = tabStyle.get().getOkImageURL();
			break;
		case Diagnostic.INFO:
			imageUrl = tabStyle.get().getInfoImageURL();
			break;
		case Diagnostic.WARNING:
			imageUrl = tabStyle.get().getWarningImageURL();
			break;
		case Diagnostic.ERROR:
			imageUrl = tabStyle.get().getErrorImageURL();
			break;
		case Diagnostic.CANCEL:
			imageUrl = tabStyle.get().getCancelImageURL();
			break;
		default:
			throw new IllegalArgumentException(
				"The specified severity value " + severity + " is invalid. See Diagnostic class."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (imageUrl == null) {
			return null;
		}
		try {
			return Activator.getImage(new URL(imageUrl));
		} catch (final MalformedURLException ex) {
			return null;
		}
	}
}
