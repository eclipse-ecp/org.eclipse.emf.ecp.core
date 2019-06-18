/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.cdo.internal.core;

import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.util.container.IPluginContainer;

/**
 * Provider-specific wrapper for an ECO {@link InternalRepository}.
 *
 * @author Eike Stepper
 */
public final class CDORepositoryData implements CDOSessionConfigurationFactory {
	private final InternalRepository repository;

	/**
	 * Constructor.
	 *
	 * @param repository the {@link InternalRepository}
	 */
	public CDORepositoryData(InternalRepository repository) {
		this.repository = repository;
	}

	/**
	 * Get the {@link InternalRepository}.
	 *
	 * @return the {@link InternalRepository}
	 */
	public InternalRepository getRepository() {
		return repository;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory#createSessionConfiguration()
	 */
	@Override
	public CDONet4jSessionConfiguration createSessionConfiguration() {
		final ECPProperties properties = repository.getProperties();
		final String connectorType = properties.getValue(CDOProvider.PROP_CONNECTOR_TYPE);
		final String connectorDescription = properties.getValue(CDOProvider.PROP_CONNECTOR_DESCRIPTION);
		final String repositoryName = properties.getValue(CDOProvider.PROP_REPOSITORY_NAME);

		final IConnector connector = Net4jUtil.getConnector(IPluginContainer.INSTANCE, connectorType,
			connectorDescription);

		final CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
		configuration.setConnector(connector);
		configuration.setRepositoryName(repositoryName);
		return configuration;
	}

	@Override
	public String toString() {
		final ECPProperties properties = repository.getProperties();
		final String connectorType = properties.getValue(CDOProvider.PROP_CONNECTOR_TYPE);
		final String connectorDescription = properties.getValue(CDOProvider.PROP_CONNECTOR_DESCRIPTION);
		final String repositoryName = properties.getValue(CDOProvider.PROP_REPOSITORY_NAME);
		return connectorType + "://" + connectorDescription + "/" + repositoryName; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
