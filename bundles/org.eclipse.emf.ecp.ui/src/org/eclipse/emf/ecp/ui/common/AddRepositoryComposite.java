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
public interface AddRepositoryComposite extends CompositeProvider {

	/**
	 * A Listener interface to listen on changes during the creation of an repository.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	public interface AddRepositoryChangeListener {
		/**
		 * Notifies when the name is changed.
		 *
		 * @param repositoryName the new repositoryName
		 */
		void repositoryNameChanged(String repositoryName);

		/**
		 * Notifies when the label changes.
		 *
		 * @param repositoryLabel the new repositoryLabel
		 */
		void repositoryLabelChanged(String repositoryLabel);

		/**
		 * Notifies when the description changes.
		 *
		 * @param repositoryDescription the new repositoryDescription
		 */
		void repositoryDescriptionChanged(String repositoryDescription);

		/**
		 * Notifies when the provider changes.
		 *
		 * @param provider the new {@link ECPProvider}
		 */
		void repositoryProviderChanged(ECPProvider provider);
	}

	/**
	 * Gets the selected or set {@link ECPProvider} for this repository.
	 *
	 * @return the {@link ECPProvider} for the creation of the repository
	 */
	ECPProvider getProvider();

	/**
	 * The name for the Repository to create.
	 *
	 * @return the name of the repository
	 */
	String getRepositoryName();

	/**
	 * The description for the Repository to create.
	 *
	 * @return the description of the repository
	 */
	String getRepositoryDescription();

	/**
	 * The {@link ECPProperties} for the Repository to create.
	 *
	 * @return the properties of the repository
	 */
	ECPProperties getProperties();

	/**
	 * The label for the Repository to create.
	 *
	 * @return the label of the repository
	 */
	String getRepositoryLabel();

	/**
	 * Register a {@link AddRepositoryChangeListener}.
	 *
	 * @param listener
	 *            the listener to set
	 */
	void setListener(AddRepositoryChangeListener listener);

}
