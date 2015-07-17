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
 * Edgar Mueller - Bug 440798: EMFStoreProvider project cloning isn't transaction-friendly
 *******************************************************************************/
package org.eclipse.emf.ecp.emfstore.core.internal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.core.DefaultProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.util.ESVoidCallable;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreClientUtil;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.observers.OperationObserver;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.impl.ProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * This is the EMFStore Provider for ECP.
 *
 * @author Eugen Neufeld
 */
public final class EMFStoreProvider extends DefaultProvider {

	/**
	 * This is the name of the EMFStore Provider.
	 */
	public static final String NAME = "org.eclipse.emf.ecp.emfstore.provider"; //$NON-NLS-1$

	/**
	 * EMFStore Provider Singleton.
	 *
	 * @deprecated use ECPUtil.getECPProviderRegistry().getProvider(EMFStoreProvider.NAME) instead
	 */
	@Deprecated
	public static EMFStoreProvider INSTANCE;

	/**
	 * Property constant for Repository URL.
	 */
	public static final String PROP_REPOSITORY_URL = "repositoryUrl"; //$NON-NLS-1$
	/**
	 * Property constant for Repository Port.
	 */
	public static final String PROP_PORT = "port"; //$NON-NLS-1$
	/**
	 * Property constant for Repository Certificate.
	 */
	public static final String PROP_CERTIFICATE = "certificate"; //$NON-NLS-1$
	/**
	 * Property constant for ProjectSpaceID.
	 */
	public static final String PROP_PROJECTSPACEID = "projectSpaceID"; //$NON-NLS-1$
	/**
	 * Property constant for ServerInfoID.
	 */
	public static final String PROP_SERVERINFOID = "serverInfoID"; //$NON-NLS-1$

	private final Set<EMFStoreProviderChangeListener> changeListeners = new CopyOnWriteArraySet<EMFStoreProviderChangeListener>();

	private AdapterImpl adapter;

	/**
	 * Default constructor.
	 */
	public EMFStoreProvider() {
		super(NAME);
		configureEMFStore();
		INSTANCE = this;
	}

	/**
	 *
	 */
	private void configureEMFStore() {
		Configuration.getClientBehavior().setAutoSave(false);
	}

	/** {@inheritDoc} */
	@Override
	public EditingDomain createEditingDomain(final InternalProject project) {

		final EditingDomain domain = ECPEMFUtils.getESWorkspaceProviderInstance().getWorkspace().toInternalAPI()
			.getEditingDomain();

		return domain;
	}

	/** {@inheritDoc} */
	@Override
	public void fillChildren(final ECPContainer context, final Object parent, final InternalChildrenList childrenList) {
		if (parent instanceof InternalProject) {
			final ESLocalProject projectSpace = getProjectSpace((InternalProject) parent);
			if (projectSpace != null) {
				childrenList.addChildren(projectSpace.getModelElements());
			}
		} else if (parent instanceof InternalRepository) {
			final ESServer serverInfo = getServerInfo((InternalRepository) parent);
			if (serverInfo.getLastUsersession() != null && serverInfo.getLastUsersession().isLoggedIn()) {
				try {
					final List<ESRemoteProject> projectInfos = serverInfo.getRemoteProjects(serverInfo
						.getLastUsersession());
					for (final ESRemoteProject projectInfo : projectInfos) {
						childrenList.addChild(new EMFStoreProjectWrapper((InternalRepository) parent, projectInfo));
					}
				} catch (final ESException e) {
					Activator.log(e);
				}

			}
		}
		RunESCommand.run(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				EMFStoreProvider.super.fillChildren(context, parent, childrenList);
				return null;
			}
		});
	}

	@Override
	public boolean hasCreateProjectWithoutRepositorySupport() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public EList<? extends Object> getElements(InternalProject project) {
		return getProjectSpace(project).getModelElements();
	}

	/** {@inheritDoc} */
	@Override
	public void handleLifecycle(ECPContainer context, LifecycleEvent event) {
		switch (event) {
		case INIT:
			handleInit(context);
			break;
		case DISPOSE:
			handelDispose(context);
			break;
		case CREATE:
			handleCreate(context);
			break;
		case REMOVE:
			handleRemove(context);
			break;
		default:
			break;
		}
		final String providerClass = getClass().getSimpleName();
		final String contextClass = context.getClass().getSimpleName();
		Activator.log(IStatus.INFO, providerClass + " received " + event + " for " + contextClass + " " + context); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Called to handle the remove operation on an {@link ECPContainer}.
	 *
	 * @param context the {@link ECPContainer} to remove
	 */
	private void handleRemove(ECPContainer context) {
		if (context instanceof InternalProject) {
			final InternalProject project = (InternalProject) context;
			final ESLocalProject ps = (ESLocalProject) project.getProviderSpecificData();
			if (ps != null) {
				try {
					ps.delete(new NullProgressMonitor());
				} catch (final ESException ex) {
					Activator.log(ex);
				} catch (final IOException ex) {
					Activator.log(ex);
				}
				project.setProviderSpecificData(null);
			}

		}

	}

	/**
	 * @param context
	 */
	private void handleCreate(final ECPContainer context) {
		if (context instanceof InternalRepository) {
			final ESServer serverInfo = getServerInfo((InternalRepository) context);
			// TODO autologin?
			// if (serverInfo.getLastUsersession() != null && !serverInfo.getLastUsersession().isLoggedIn()) {
			// try {
			// serverInfo.login(serverInfo.getLastUsersession().getUsername(), serverInfo.getLastUsersession()
			// .getPassword());
			// } catch (AccessControlException ex) {
			// Activator.log(ex);
			// } catch (ESException ex) {
			// Activator.log(ex);
			// }
			// }
		} else if (context instanceof InternalProject) {
			getProjectSpace((InternalProject) context);
		}
	}

	/**
	 * @param context
	 */
	private void handelDispose(ECPContainer context) {
		if (context instanceof InternalProject) {
			final ESLocalProject projectSpace = getProjectSpace((InternalProject) context);

			if (projectSpace != null) {
				((ESLocalProjectImpl) projectSpace).toInternalAPI().getProject().eAdapters().remove(adapter);
			}

		}

	}

	private boolean isAutosave() {
		final IPreferencesService service = Platform.getPreferencesService();
		return service.getBoolean("org.eclipse.emf.ecp", "AUTOSAVE", false, null); //$NON-NLS-1$ //$NON-NLS-2$

	}

	/**
	 * @param context
	 */
	private void handleInit(final ECPContainer context) {
		if (context instanceof InternalProject) {
			final ESLocalProject localProject = getProjectSpace((InternalProject) context, true);
			if (localProject == null) {
				return;
			}
			final ProjectSpace projectSpace = ((ESLocalProjectImpl) localProject).toInternalAPI();

			if (isAutosave()) {
				// TODO EMFStore how to listen to operations?
				projectSpace.getOperationManager().addOperationObserver(new OperationObserver() {

					@Override
					public void operationUndone(AbstractOperation operation) {
						doSave((InternalProject) context);
					}

					@Override
					public void operationExecuted(AbstractOperation operation) {
						doSave((InternalProject) context);
					}
				});
			}
			// TODO EMFStore how to add IdEObjectCollection Observer?
			projectSpace.getProject().addIdEObjectCollectionChangeObserver(new IdEObjectCollectionChangeObserver() {
				// 2
				@Override
				public void notify(Notification notification, IdEObjectCollection collection, EObject modelElement) {

					for (final EMFStoreProviderChangeListener listener : changeListeners) {
						listener.onNewNotification(notification);
					}

					if (modelElement instanceof ProjectImpl) {
						final ProjectSpaceImpl projectSpace = (ProjectSpaceImpl) modelElement.eContainer();
						final ECPProject ecpProject = getProject(projectSpace.toAPI());

						((InternalProject) context).notifyObjectsChanged(
							Collections.singleton((Object) ecpProject), true);

					} else {
						((InternalProject) context).notifyObjectsChanged(
							Collections.singleton((Object) modelElement), true);
					}
				}

				// 3
				@Override
				public void modelElementRemoved(IdEObjectCollection collection, EObject modelElement) {
					if (modelElement.eContainer() == null) {
						((InternalProject) context).notifyObjectsChanged(Arrays.asList(context, modelElement), true);
					}
				}

				// 1
				@Override
				public void modelElementAdded(IdEObjectCollection collection, EObject modelElement) {
					if (Project.class.isInstance(modelElement.eContainer())) {
						((InternalProject) context).notifyObjectsChanged(Arrays.asList(modelElement, context), true);
					}
					((InternalProject) context).notifyObjectsChanged(Collections.singleton((Object) modelElement),
						true);
				}

				@Override
				public void collectionDeleted(IdEObjectCollection collection) {
					// project delete
					((InternalProject) context).notifyObjectsChanged(Collections.singleton((Object) context), true);
				}
			});
		}

	}

	/** {@inheritDoc} */
	@Override
	public Iterator<EObject> getLinkElements(InternalProject project, EObject modelElement, EReference eReference) {
		final Collection<EObject> result = new HashSet<EObject>();
		final EClass elementClass = modelElement.eClass();
		EClassifier type = EcoreUtil.getReifiedType(elementClass, eReference.getEGenericType()).getEClassifier();
		if (type == null) {
			type = eReference.getEType();
		}

		// TODO EMFStore does it work with ESLocalProject?
		final ProjectSpace projectSpace = ((ESLocalProjectImpl) getProjectSpace(project)).toInternalAPI();
		ItemPropertyDescriptor.collectReachableObjectsOfType(new HashSet<EObject>(), result, projectSpace.getProject(),
			type);
		return result.iterator();
	}

	/**
	 * @param info
	 * @param ecpProperties
	 * @return
	 */
	private boolean isSameServerInfo(ESServer info, String url, int port, String certificate) {
		return info.getURL().equalsIgnoreCase(url) && info.getPort() == port
			&& info.getCertificateAlias().equalsIgnoreCase(certificate);
	}

	/** {@inheritDoc} */
	@Override
	public void doSave(InternalProject project) {
		getProjectSpace(project).save();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDirty(InternalProject project) {
		return getProjectSpace(project).hasUnsavedChanges();
	}

	/** {@inheritDoc} */
	@Override
	public void delete(InternalProject project, final Collection<Object> objects) {
		final ProjectSpace projectSpace = ((ESLocalProjectImpl) getProjectSpace(project)).toInternalAPI();
		// TODO EMFStore how to delete eObject?
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				for (final Object object : objects) {
					if (EObject.class.isInstance(object)) {
						projectSpace.getProject().deleteModelElement((EObject) object);
					}
				}
			}
		}.run(false);

	}

	/** {@inheritDoc} */
	@Override
	public void cloneProject(final InternalProject projectToClone, InternalProject targetProject) {
		final ProjectSpace toClone = ((ESLocalProjectImpl) getProjectSpace(projectToClone)).toInternalAPI();
		final ProjectSpace target = ((ESLocalProjectImpl) getProjectSpace(targetProject)).toInternalAPI();
		RunESCommand.run(new ESVoidCallable() {
			@Override
			public void run() {
				target.setProject(ModelUtil.clone(toClone.getProject()));
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public boolean modelExists(InternalProject project) {
		return getProjectSpace(project, false) != null;
	}

	/** {@inheritDoc} */
	@Override
	public Notifier getRoot(InternalProject project) {
		// TODO EMFStore other way to get root of localproject?
		return ((ESLocalProjectImpl) getProjectSpace(project)).toInternalAPI().getProject();
	}

	@Override
	public boolean contains(InternalProject project, Object object) {
		if (!EObject.class.isInstance(object)) {
			return false;
		}
		final ESLocalProject projectSpace = getProjectSpace(project);
		return projectSpace.contains((EObject) object);
	}

	@Override
	public ECPContainer getModelContext(Object element) {
		if (element instanceof ECPContainer) {
			return (ECPContainer) element;
		}

		if (element instanceof ECPModelContextProvider) {
			return ((ECPModelContextProvider) element).getModelContext(element);
		}

		if (element instanceof EObject) {
			final EObject eObject = (EObject) element;
			ProjectSpace ps = null;
			try {

				ps = ESWorkspaceProviderImpl.getProjectSpace(eObject);
			} catch (final IllegalArgumentException iae) {
				return null;
			}
			if (ps != null) {
				final ECPContainer context = getModelContextFromAdapter(ps.getProject());
				if (context != null) {
					return context;
				}
			}
			element = eObject.eResource();
		}

		if (element instanceof Resource) {
			final Resource resource = (Resource) element;
			final ECPContainer context = getModelContextFromAdapter(resource);
			if (context != null) {
				return context;
			}

			element = resource.getResourceSet();
		}

		if (element instanceof ResourceSet) {
			final ResourceSet resourceSet = (ResourceSet) element;
			final ECPContainer context = getModelContextFromAdapter(resourceSet);
			if (context != null) {
				return context;
			}
		}

		return null;
	}

	/**
	 * This retrieves the {@link ProjectSpace} from an {@link InternalProject}.
	 * First it checks whether the {@link InternalProject} has a ProjectSpaceID attached.
	 * If an ID is attached, a ProjectSpace is searched with this ID.
	 * If no ID is attached or now ProjectSpace was found a LocalProject is created.
	 *
	 * @param internalProject the project to get the ProjectSpace for
	 * @return the corresponding ProjectSpace
	 */
	public ESLocalProject getProjectSpace(InternalProject internalProject) {
		return getProjectSpace(internalProject, false);
	}

	private ESLocalProject getProjectSpace(InternalProject internalProject, boolean createNewIfNeeded) {

		ESLocalProject projectSpace = (ESLocalProject) internalProject.getProviderSpecificData();

		if (projectSpace == null) {
			boolean found = false;
			final List<ESLocalProject> localProjects = ECPEMFUtils.getESWorkspaceProviderInstance().getWorkspace()
				.getLocalProjects();
			for (final ESLocalProject localProject : localProjects) {
				final String projectSpaceID = internalProject.getProperties().getValue(
					EMFStoreProvider.PROP_PROJECTSPACEID);
				if (localProject.getLocalProjectId().getId().equals(projectSpaceID)) {
					found = true;
					projectSpace = localProject;
					break;
				}
			}

			if (!found && createNewIfNeeded) {
				projectSpace = ECPEMFUtils.getESWorkspaceProviderInstance().getWorkspace()
					.createLocalProject(internalProject.getName());
				internalProject.getProperties().addProperty(EMFStoreProvider.PROP_PROJECTSPACEID,
					projectSpace.getLocalProjectId().getId());

			}
			internalProject.setProviderSpecificData(projectSpace);
		}
		return projectSpace;
	}

	/**
	 * This retrieves the {@link org.eclipse.emf.emfstore.internal.client.model.ServerInfo} from an
	 * {@link InternalRepository}.
	 * First it checks whether the {@link InternalRepository} has a ServerInfoID attached.
	 * If an ID is attached, a ServerInfo is searched with this ID.
	 * If no ID is attached or now ServerInfo was found a default ServerInfo is created.
	 *
	 * @param internalRepository the repository to get the ServerInfo for
	 * @return the corresponding ServerInfo
	 */
	public ESServer getServerInfo(InternalRepository internalRepository) {
		ESServer serverInfo = (ESServer) internalRepository.getProviderSpecificData();

		if (serverInfo == null) {

			final ESWorkspace workspace = ECPEMFUtils.getESWorkspaceProviderInstance().getWorkspace();
			boolean foundExisting = false;

			for (final ESServer info : workspace.getServers()) {
				if (internalRepository.getProperties().hasProperties()
					&& isSameServerInfo(info,
						internalRepository.getProperties().getValue(EMFStoreProvider.PROP_REPOSITORY_URL),
						Integer.parseInt(internalRepository.getProperties().getValue(EMFStoreProvider.PROP_PORT)),
						internalRepository.getProperties().getValue(EMFStoreProvider.PROP_CERTIFICATE))) {
					serverInfo = info;
					foundExisting = true;
					break;
				}
			}
			if (!foundExisting && internalRepository.getProperties().hasProperties()) {
				serverInfo = EMFStoreClientUtil.createServerInfo(
					internalRepository.getProperties().getValue(EMFStoreProvider.PROP_REPOSITORY_URL),
					Integer.parseInt(internalRepository.getProperties().getValue(EMFStoreProvider.PROP_PORT)),
					internalRepository.getProperties().getValue(EMFStoreProvider.PROP_CERTIFICATE)).toAPI();
				workspace.addServer(serverInfo);
			} else if (!foundExisting && !internalRepository.getProperties().hasProperties()) {
				serverInfo = EMFStoreClientUtil.giveServerInfo("localhost", 8080).toAPI(); //$NON-NLS-1$
			}
			internalRepository.setProviderSpecificData(serverInfo);
		}
		return serverInfo;
	}

	/**
	 * This gets the ECPProject based on a ProjectSpace.
	 *
	 * @param projectSpace the {@link ProjectSpace} to get the {@link ECPProject} for
	 * @return the {@link ECPProject} corresponding to this ProjectSpace or null if none found
	 */
	public ECPProject getProject(ESLocalProject projectSpace) {
		for (final InternalProject project : getOpenProjects()) {
			final ESLocalProject localProjectSpace = (ESLocalProject) project.getProviderSpecificData();
			if (localProjectSpace.equals(projectSpace)) {
				return project;
			}
		}
		return null;
	}

	/**
	 * This gets the ECPRepository based on a ServerInfo.
	 *
	 * @param serverInfo the {@link ESServer} to get the {@link ECPRepository} for
	 * @return the {@link ECPRepository} corresponding to this ServerInfo or null if none found
	 */
	public ECPRepository getRepository(ESServer serverInfo) {
		if (serverInfo != null) {
			for (final ECPRepository internalRepository : ECPUtil.getECPRepositoryManager().getRepositories()) {
				if (internalRepository.getProvider().equals(this)) {
					if (serverInfo.equals(((InternalRepository) internalRepository).getProviderSpecificData())) {
						return internalRepository;
					}
				}
			}
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
		return false;
	}

	/**
	 * Registers a new {@link EMFStoreProviderChangeListener}.
	 *
	 * @param listener the listener
	 */
	public void registerChangeListener(EMFStoreProviderChangeListener listener) {
		changeListeners.add(listener);
	}

	/**
	 * Unregisters a new {@link EMFStoreProviderChangeListener}.
	 *
	 * @param listener the listener
	 */
	public void unregisterChangeListener(EMFStoreProviderChangeListener listener) {
		changeListeners.remove(listener);
	}

}
