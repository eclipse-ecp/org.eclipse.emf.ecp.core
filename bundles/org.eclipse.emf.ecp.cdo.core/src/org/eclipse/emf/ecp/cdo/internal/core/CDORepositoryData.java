/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	public CDONet4jSessionConfiguration createSessionConfiguration() {
		ECPProperties properties = repository.getProperties();
		String connectorType = properties.getValue(CDOProvider.PROP_CONNECTOR_TYPE);
		String connectorDescription = properties.getValue(CDOProvider.PROP_CONNECTOR_DESCRIPTION);
		String repositoryName = properties.getValue(CDOProvider.PROP_REPOSITORY_NAME);

		IConnector connector = Net4jUtil.getConnector(IPluginContainer.INSTANCE, connectorType, connectorDescription);

		CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
		configuration.setConnector(connector);
		configuration.setRepositoryName(repositoryName);
		return configuration;
	}

	@Override
	public String toString() {
		ECPProperties properties = repository.getProperties();
		String connectorType = properties.getValue(CDOProvider.PROP_CONNECTOR_TYPE);
		String connectorDescription = properties.getValue(CDOProvider.PROP_CONNECTOR_DESCRIPTION);
		String repositoryName = properties.getValue(CDOProvider.PROP_REPOSITORY_NAME);
		return connectorType + "://" + connectorDescription + "/" + repositoryName; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
