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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.workspace.CDOWorkspaceBase;
import org.eclipse.emf.cdo.workspace.CDOWorkspaceConfiguration;
import org.eclipse.emf.cdo.workspace.CDOWorkspaceUtil;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.core.DefaultProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.h2.H2Adapter;
import org.eclipse.net4j.util.UUIDGenerator;
import org.eclipse.net4j.util.io.IOUtil;
import org.h2.jdbcx.JdbcDataSource;

/**
 * @author Eike Stepper
 */
public class CDOProvider extends DefaultProvider {
	/**
	 * The unique provider name.
	 */
	public static final String NAME = "org.eclipse.emf.ecp.cdo.provider"; //$NON-NLS-1$

	/**
	 * The key for the connector type ECP project property.
	 */
	public static final String PROP_CONNECTOR_TYPE = "connectorType"; //$NON-NLS-1$

	/**
	 * The key for the connector description ECP project property.
	 */
	public static final String PROP_CONNECTOR_DESCRIPTION = "connectorDescription"; //$NON-NLS-1$

	/**
	 * The key for the repository name ECP project property.
	 */
	public static final String PROP_REPOSITORY_NAME = "repositoryName"; //$NON-NLS-1$

	/**
	 * The key for the branch path ECP project property.
	 */
	public static final String PROP_BRANCH_PATH = "branchPath"; //$NON-NLS-1$

	/**
	 * The key for the time stamp ECP project property.
	 */
	public static final String PROP_TIME_STAMP = "timeStamp"; //$NON-NLS-1$

	/**
	 * The key for the workspace ID ECP project property.
	 */
	public static final String PROP_WORKSPACE_ID = "workspaceID"; //$NON-NLS-1$

	/**
	 * Contains the singleton instance of this provider.
	 */
	private static CDOProvider instance;

	/**
	 * Default constructor.
	 */
	@SuppressWarnings("deprecation")
	public CDOProvider() {
		super(NAME);
		instance = this;
		CDOUtil.setLegacyModeDefault(true);
	}

	/**
	 * Get the CDO Provider singleton.
	 *
	 * @return the singleton instance or null
	 * @deprecated use ECPUtil.getECPProviderRegistry().getProvider(CDOProvider.NAME) instead
	 */
	@Deprecated
	public static CDOProvider getInstance() {
		// TODO: what if instance is still null because constructor was never called?
		return instance;
	}

	@Override
	protected void doDispose() {
		instance = null;
		super.doDispose();
	}

	@Override
	public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
		final T adapter = ECPProjectAdapterFactory.adapt(adaptable, adapterType);
		if (adapter != null) {
			return adapter;
		}

		return super.getAdapter(adaptable, adapterType);
	}

	@Override
	public boolean isSlow(Object parent) {
		if (parent instanceof CDOBranchWrapper) {
			return true;
		}

		if (parent instanceof EObject) {
			return true;
		}

		return super.isSlow(parent);
	}

	@Override
	public void fillChildren(ECPContainer context, Object parent, InternalChildrenList childrenList) {
		if (parent instanceof InternalProject) {
			final InternalProject project = (InternalProject) parent;
			final CDOProjectData projectData = getProjectData(project);
			childrenList.addChildren(projectData.getRootResource().getContents());
		} else if (parent instanceof InternalRepository) {
			final InternalRepository repository = (InternalRepository) parent;
			final CDOBranchWrapper wrapper = new CDOBranchWrapper(repository, CDOBranch.MAIN_BRANCH_NAME);
			childrenList.addChild(wrapper);
		} else if (parent instanceof CDOBranchWrapper) {
			final CDOBranchWrapper parentWrapper = (CDOBranchWrapper) parent;
			final InternalRepository repository = parentWrapper.getRepository();
			final String branchPath = parentWrapper.getBranchPath();

			CDONet4jSession session = null;

			try {
				final CDORepositoryData repositoryData = getRepositoryData(repository);
				final CDONet4jSessionConfiguration sessionConfiguration = repositoryData.createSessionConfiguration();
				session = sessionConfiguration.openNet4jSession();

				final CDOBranch branch = session.getBranchManager().getBranch(branchPath);
				for (final CDOBranch child : branch.getBranches()) {
					final CDOBranchWrapper wrapper = new CDOBranchWrapper(repository, child.getPathName());
					childrenList.addChild(wrapper);
				}
			} finally {
				IOUtil.close(session);
			}
		} else {
			super.fillChildren(context, parent, childrenList);
		}
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public EList<Object> getElements(InternalProject project) {
		final CDOProjectData data = (CDOProjectData) project.getProviderSpecificData();
		return (EList<Object>) (EList<?>) data.getTransaction().getResourceSet().getResources();
		// TODO: implement CDOProvider.addRootElement(project, rootElement)
		// throw new UnsupportedOperationException();
		// return new BasicEList<Object>();
	}

	@Override
	public void handleLifecycle(ECPContainer context, LifecycleEvent event) {
		super.handleLifecycle(context, event);

		if (context instanceof InternalProject) {
			final InternalProject project = (InternalProject) context;
			switch (event) {
			case CREATE:
				createProject(project);
				break;

			case INIT:
				break;

			case DISPOSE:
				disposeProject(project);
				break;

			case REMOVE:
				removeProject(project);
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Creates a provider specific project for the given internal project.
	 *
	 * @param project the internal project
	 */
	protected void createProject(InternalProject project) {
		final String workspaceID = UUIDGenerator.DEFAULT.generate();
		project.getProperties().addProperty(PROP_WORKSPACE_ID, workspaceID);

		final CDOProjectData projectData = getProjectData(project);
		projectData.checkoutWorkspace();
		final File folder = getProjectFolder(project);
		PrintStream stream = null;

		try {
			stream = new PrintStream(new File(folder, "ecp.properties")); //$NON-NLS-1$
			stream.println("project.name = " + project.getName()); //$NON-NLS-1$

		} catch (final FileNotFoundException ex) {
			Activator.log(ex);
			throw new IllegalStateException("Retrieving project folder failed!", ex); //$NON-NLS-1$
		} finally {
			IOUtil.close(stream);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public ECPContainer getModelContext(Object element) {
		if (element instanceof CDOResource) {
			final Set<InternalProject> openProjects = getOpenProjects();
			for (final InternalProject project : openProjects) {
				final CDOProjectData projectData = (CDOProjectData) project.getProviderSpecificData();
				if (projectData.getRootResource().getContents().contains(element)) {
					return project;
				}
			}
		}
		return super.getModelContext(element);
	}

	/**
	 * Create and store a {@link CDOWorkspaceConfiguration} for the given internal project.
	 *
	 * @param project the internal project.
	 * @return the {@link CDOWorkspaceConfiguration}
	 */
	protected CDOWorkspaceConfiguration createWorkspaceConfiguration(InternalProject project) {
		final File folder = getProjectFolder(project);
		folder.mkdirs();

		final IDBStore localStore = createLocalStore(project, folder);
		final CDOWorkspaceBase base = createWorkspaceBase(project, folder);
		final CDOSessionConfigurationFactory remote = getRepositoryData(project.getRepository());

		final ECPProperties properties = project.getProperties();
		final String branchPath = properties.getValue(PROP_BRANCH_PATH);
		final String timeStamp = properties.getValue(PROP_TIME_STAMP);

		final CDOWorkspaceConfiguration config = CDOWorkspaceUtil.createWorkspaceConfiguration();
		config.setLocalRepositoryName(folder.getName()); // workspaceID
		config.setStore(localStore);
		config.setBase(base);
		config.setRemote(remote);
		config.setBranchPath(branchPath == null ? CDOBranch.MAIN_BRANCH_NAME : branchPath);
		config.setTimeStamp(timeStamp == null ? CDOBranchPoint.UNSPECIFIED_DATE : Long.parseLong(timeStamp));

		return config;
	}

	/**
	 * Create and store a {@link CDOWorkspaceBase}.
	 *
	 * @param project the internal project.
	 * @param folder the folder to put the {@link CDOWorkspaceBase} into
	 * @return the {@link CDOWorkspaceBase}
	 */
	protected CDOWorkspaceBase createWorkspaceBase(InternalProject project, File folder) {
		final File base = new File(folder, "base"); //$NON-NLS-1$
		base.mkdirs();

		return CDOWorkspaceUtil.createFolderWorkspaceBase(base);
	}

	/**
	 * Create a local {@link IDBStore}.
	 *
	 * @param project the internal project
	 * @param folder the folder to store the data in
	 * @return the {@link IDBStore}
	 */
	protected IDBStore createLocalStore(InternalProject project, File folder) {
		final File local = new File(folder, "local"); //$NON-NLS-1$
		local.mkdirs();

		final JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:" + new File(local, "local").getAbsolutePath()); //$NON-NLS-1$ //$NON-NLS-2$

		final IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(false, false);
		final IDBAdapter dbAdapter = new H2Adapter();
		final IDBConnectionProvider dbConnectionProvider = DBUtil.createConnectionProvider(dataSource);

		return CDODBUtil.createStore(mappingStrategy, dbAdapter, dbConnectionProvider);
	}

	/**
	 * Dispose the given project and its data.
	 *
	 * @param project the internal project
	 */
	protected void disposeProject(InternalProject project) {
		final CDOProjectData data = (CDOProjectData) project.getProviderSpecificData();
		data.dispose();
	}

	/**
	 * Remove the internal project and its configuration data.
	 *
	 * @param project the internal project
	 */
	protected void removeProject(InternalProject project) {
		final File folder = getProjectFolder(project);
		if (folder.exists()) {
			if (!folder.delete()) {
				folder.deleteOnExit();
			}
		}
	}

	/**
	 * Retrieve {@link CDORepositoryData} for a given {@link InternalRepository}.
	 *
	 * @param repository the internal repositorz
	 * @return the {@link CDORepositoryData}
	 */
	public static CDORepositoryData getRepositoryData(InternalRepository repository) {
		synchronized (repository) {
			CDORepositoryData data = (CDORepositoryData) repository.getProviderSpecificData();
			if (data == null) {
				data = new CDORepositoryData(repository);
				repository.setProviderSpecificData(data);
			}

			return data;
		}
	}

	/**
	 * Get {@link CDOProjectData} for the given internal project.
	 *
	 * @param project the internal project
	 * @return the {@link CDOProjectData}
	 */
	public static CDOProjectData getProjectData(InternalProject project) {
		synchronized (project) {
			CDOProjectData data = (CDOProjectData) project.getProviderSpecificData();
			if (data == null) {
				data = new CDOProjectData(project);
				project.setProviderSpecificData(data);
			}

			return data;
		}
	}

	/**
	 * Get the folder for the configuration data of the internal project.
	 *
	 * @param project the internal project.
	 * @return the {@link File}
	 */
	public static File getProjectFolder(InternalProject project) {
		final String workspaceID = project.getProperties().getValue(PROP_WORKSPACE_ID);
		return new File(Activator.getInstance().getStateLocation().toFile(), workspaceID);
	}

	/** {@inheritDoc} */
	@Override
	public void cloneProject(final InternalProject projectToClone, InternalProject targetProject) {
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public Notifier getRoot(InternalProject project) {
		final CDOProjectData data = (CDOProjectData) project.getProviderSpecificData();
		if (data != null) {
			return data.getRootResource();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#isThreadSafe()
	 */
	@Override
	public boolean isThreadSafe() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.spi.core.DefaultProvider#doDelete(org.eclipse.emf.ecp.spi.core.InternalProject,
	 *      java.util.Collection)
	 */
	@Override
	public void doDelete(InternalProject project, Collection<Object> objects) {
		final CDOResource cdoResource = getProjectData(project).getRootResource();
		cdoResource.getContents().removeAll(objects);

	}
}
