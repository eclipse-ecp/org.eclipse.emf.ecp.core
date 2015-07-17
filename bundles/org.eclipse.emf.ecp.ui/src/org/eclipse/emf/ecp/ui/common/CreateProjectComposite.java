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

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.common.ui.CompositeProvider;

/**
 * @author Eugen Neufeld
 *
 */
public interface CreateProjectComposite extends CompositeProvider {
	/**
	 * Listener interface that will be notified if the projectName or the selected provider changes.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	public interface CreateProjectChangeListener {
		/**
		 * Callback method providing the new project name.
		 *
		 * @param projectName the new project name
		 */
		void projectNameChanged(String projectName);

		/**
		 * Callback method providing the new provide.
		 *
		 * @param provider the new selected provider.
		 */
		void providerChanged(ECPProvider provider);

		/**
		 * Callback method providing the new provide.
		 *
		 * @param status the new status.
		 */
		void completeStatusChanged(boolean status);

	}

	/**
	 * @return the provider
	 */
	ECPProvider getProvider();

	/**
	 * @return the projectName
	 */
	String getProjectName();

	/**
	 * @return the project properties
	 */
	ECPProperties getProperties();

	/**
	 * @param listener
	 *            the listener to set
	 */
	void setListener(CreateProjectChangeListener listener);
}
