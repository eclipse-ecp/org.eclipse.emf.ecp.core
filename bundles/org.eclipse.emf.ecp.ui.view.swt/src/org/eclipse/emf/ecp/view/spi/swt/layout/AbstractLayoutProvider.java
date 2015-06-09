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
package org.eclipse.emf.ecp.view.spi.swt.layout;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.swt.core.layout.LayoutProvider;

/**
 * Abstract implementation of a {@link LayoutProvider} which contributes helper methods.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public abstract class AbstractLayoutProvider implements LayoutProvider {

	/**
	 * Checks whether a setting is set to multiline.
	 *
	 * @param domainModelReference the {@link VDomainModelReference} pointing to the feature to check
	 * @param domainModel the root {@link EObject} of the domain model reference
	 * @return true if multiline, false otherwise
	 * @since 1.6
	 */
	protected static boolean isMultiLine(VDomainModelReference domainModelReference, EObject domainModel) {
		return Activator.getDefault().getEMFFormsEditSupport().isMultiLine(domainModelReference, domainModel);
	}

}
