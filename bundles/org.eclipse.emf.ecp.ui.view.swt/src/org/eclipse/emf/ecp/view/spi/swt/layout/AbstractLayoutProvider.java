/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.layout;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.swt.Activator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.swt.core.layout.LayoutProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Layout;

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

	/**
	 * Delegates to {@link #getColumnLayout(int, boolean)}.
	 *
	 * @param numColumns the number of columns to create
	 * @param equalWidth whether the columns should be equal width
	 * @param margins the margins of the layout
	 * @return the layout to use
	 * @since 1.7
	 */
	@Override
	public Layout getColumnLayout(int numColumns, boolean equalWidth, Point margins) {
		return getColumnLayout(numColumns, equalWidth);
	}

}
