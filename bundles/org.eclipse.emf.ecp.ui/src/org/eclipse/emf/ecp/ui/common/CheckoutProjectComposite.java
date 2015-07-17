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
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.common;

import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.common.ui.CompositeProvider;
import org.eclipse.emf.ecp.spi.ui.UIProvider;

/**
 * @author Eugen Neufeld
 *
 */
public interface CheckoutProjectComposite extends CompositeProvider {

	/**
	 * Listener interface that will be notified if the projectName changes.
	 */
	public interface CheckoutProjectChangeListener {
		/**
		 * Callback method providing the new project name.
		 *
		 * @param projectName the new project name
		 */
		void projectNameChanged(String projectName);
	}

	/**
	 * @return the project name
	 */
	String getProjectName();

	/**
	 * @return the project properties
	 */
	ECPProperties getProjectProperties();

	/**
	 * @return the checkoutSource
	 */
	ECPCheckoutSource getCheckoutSource();

	/**
	 * @return the uiProvider
	 */
	UIProvider getUiProvider();

	/**
	 * @param listener
	 *            the listener to set
	 */
	void setListener(CheckoutProjectChangeListener listener);
}
