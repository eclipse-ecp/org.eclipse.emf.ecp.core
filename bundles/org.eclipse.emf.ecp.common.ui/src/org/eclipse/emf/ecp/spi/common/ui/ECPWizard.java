/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
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

package org.eclipse.emf.ecp.spi.common.ui;

import org.eclipse.jface.wizard.Wizard;

/**
 * An abstract class for all Wizard with only one page that use an {@link CompositeProvider} to create this page.
 *
 * @param <T> the {@link CompositeProvider} to use during build
 *
 * @author Eugen Neufeld
 */
public abstract class ECPWizard<T extends CompositeProvider> extends Wizard {
	private T uiProvider;

	/**
	 * Sets the {@link CompositeProvider} for this wizard.
	 *
	 * @param compositeProvider the {@link CompositeProvider} to set
	 */
	@SuppressWarnings("unchecked")
	public void setCompositeProvider(CompositeProvider compositeProvider) {
		this.uiProvider = (T) compositeProvider;
	}

	/**
	 * Returns the set {@link CompositeProvider}.
	 *
	 * @return the {@link CompositeProvider} that was set
	 */

	protected T getCompositeProvider() {
		return uiProvider;
	}
}
