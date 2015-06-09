/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc and changes
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.core;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPProjectAware;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectContentChangedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectContentTouchedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectOpenClosedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectsChangedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPRepositoriesChangedObserver;
import org.eclipse.emf.ecp.internal.core.util.InternalUtil;
import org.eclipse.emf.ecp.internal.core.util.PropertiesStore;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider.LifecycleEvent;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.net4j.util.AdapterUtil;

/**
 * This class manages the available {@link ECPProject ECPProjects}.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public final class ECPProjectManagerImpl extends PropertiesStore<InternalProject, ECPObserver> implements
ECPProjectManager, ECPRepositoriesChangedObserver {

	private static final String PROJECT_FOLDERNAME = "projects"; //$NON-NLS-1$

	/**
	 * This variable defines whether the projects where already initialized. Default value is false.
	 */
	private boolean initializedProjects;

	/**
	 * Should not be called directly, use service instead.
	 */
	public ECPProjectManagerImpl() {
		initializeFolder(null);
	}

	/**
	 * @param sessionId Will be appended to the folder name so that there are different folders for each session
	 */

	public ECPProjectManagerImpl(String sessionId) {
		initializeFolder(sessionId);
	}

	private void initializeFolder(String sessionId) {

		final File stateLocation = Activator.getInstance().getStateLocation().toFile();
		setFolder(new File(stateLocation, PROJECT_FOLDERNAME));
		String finalFolderName = PROJECT_FOLDERNAME;
		if (sessionId != null) {
			finalFolderName += "-" + sessionId;
		}
		setFolder(new File(stateLocation, finalFolderName));
	}

	/** {@inheritDoc} */
	@Override
	public ECPProject createProject(ECPProvider provider, String name) throws ECPProjectWithNameExistsException {
		return this.createProject(provider, name, ECPUtil.createProperties());
	}

	/** {@inheritDoc} */
	@Override
	public ECPProject createProject(ECPProvider provider, String name, ECPProperties properties)
		throws ECPProjectWithNameExistsException {
		if (projectExists(name)) {
			throw new ECPProjectWithNameExistsException("A project with name " + name + " already exists"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (!provider.hasCreateProjectWithoutRepositorySupport()) {
			throw new UnsupportedOperationException("The provider " + provider.getLabel() //$NON-NLS-1$
				+ " doesn't support the creation of projects without an ECPRepository (aka offline project)."); //$NON-NLS-1$
		}
		final InternalProject project = new ECPProjectImpl((InternalProvider) provider, name, properties);
		return createProject(project);
	}

	/** {@inheritDoc} */
	@Override
	public ECPProject createProject(ECPRepository repository, String name, ECPProperties properties)
		throws ECPProjectWithNameExistsException {
		if (projectExists(name)) {
			throw new ECPProjectWithNameExistsException("A project with name " + name + " already exists"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		final InternalProject project = new ECPProjectImpl(repository, name, properties);
		return createProject(project);
	}

	/** {@inheritDoc} */
	@Override
	public ECPProject createProject(ECPProject project, String name) {
		final InternalProject internalProject = (InternalProject) project;
		final InternalProject newProject = internalProject.clone(name);
		return createProject(newProject);
	}

	private boolean projectExists(String name) {
		return getProject(name) != null;
	}

	private ECPProject createProject(InternalProject project) {
		project.getProvider().handleLifecycle(project, LifecycleEvent.CREATE);
		changeElements(null, Collections.singleton(project));

		return project;
	}

	/** {@inheritDoc} */
	@Override
	public InternalProject getProject(Object adaptable) {
		if (adaptable instanceof ECPProjectAware) {
			final ECPProjectAware projectAware = (ECPProjectAware) adaptable;
			return (InternalProject) projectAware.getProject();
		}
		final InternalProject result = getInternalProject(adaptable);
		if (result != null) {
			return result;
		}
		return AdapterUtil.adapt(adaptable, InternalProject.class);
	}

	private InternalProject getInternalProject(Object object) {
		for (final ECPProvider provider : ECPUtil.getECPProviderRegistry().getProviders()) {
			final InternalProvider internalProvider = (InternalProvider) ECPUtil.getResolvedElement(provider);
			final ECPContainer modelContext = internalProvider.getModelContext(object);
			if (modelContext != null && InternalProject.class.isInstance(modelContext)) {
				return (InternalProject) modelContext;
			}
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public InternalProject getProject(String name) {
		return getElement(name);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<ECPProject> getProjects() {
		initializeProjects();
		return (Collection) getElements();
	}

	/**
	 * @param projects
	 */
	private void initializeProjects() {
		if (!initializedProjects) {

			for (final InternalProject project : getElements()) {

				if (!project.getProvider().modelExists(project)) {
					project.close();
					Activator.log(IStatus.ERROR, "Project Data was deleted since last start. Project is now closed."); //$NON-NLS-1$

					continue;
				}
				project.notifyProvider(LifecycleEvent.INIT);
			}
			initializedProjects = true;
		}
	}

	/**
	 * This is called by projects to notify observers if a project gets openes or closed.
	 *
	 * @param project the project that called this method
	 * @param opened whether the project is open
	 * @param store whether to store the change
	 */
	public void changeProject(ECPProject project, boolean opened, boolean store) {
		if (store) {
			storeElement((InternalProject) project);
		}

		try {
			ECPUtil.getECPObserverBus().notify(ECPProjectOpenClosedObserver.class).projectChanged(project, opened);
		} catch (final RuntimeException ex) {
			Activator.log(ex);
		}
	}

	/**
	 * This is called by projects to notify observers about object changes.
	 * First the {@link ECPProjectContentChangedObserver IECPProjectObjectsChangedObservers} are notified then the
	 * {@link ECPProjectsChangedObserver IECPProjectsChangedUIObservers}.
	 *
	 * @param project the project that called this method
	 * @param objects the objects that changed
	 * @param structural whether the changes where structural
	 */
	public void notifyObjectsChanged(ECPProject project, Collection<Object> objects, boolean structural) {

		final Collection<Object> affected = ECPUtil.getECPObserverBus().notify(ECPProjectContentChangedObserver.class)
			.objectsChanged(project, objects);
		final Set<Object> toUpdate = new HashSet<Object>(objects);
		if (affected != null) {
			toUpdate.addAll(affected);
		}
		ECPUtil.getECPObserverBus().notify(ECPProjectContentTouchedObserver.class)
			.contentTouched(project, toUpdate, structural);

	}

	/** {@inheritDoc} */
	@Override
	public void repositoriesChanged(Collection<ECPRepository> oldRepositories, Collection<ECPRepository> newRepositories) {
		final Set<ECPRepository> addedRepositories = InternalUtil.getAddedElements(oldRepositories, newRepositories);
		final Collection<InternalProject> projects = getElements();

		for (final ECPRepository repository : addedRepositories) {
			for (final InternalProject project : projects) {
				if (!project.isOpen() && project.getRepository() != null
					&& repository.getName().equals(project.getRepository().getName())) {
					project.undispose((InternalRepository) repository);
				}
			}
		}
	}

	@Override
	protected void elementsChanged(Collection<InternalProject> oldElements, Collection<InternalProject> newElements) {
		super.elementsChanged(oldElements, newElements);
	}

	@Override
	protected void doActivate() throws Exception {
		super.doActivate();
		ECPUtil.getECPObserverBus().register(this);
	}

	@Override
	protected void doDeactivate() throws Exception {
		ECPUtil.getECPObserverBus().unregister(this);
		super.doDeactivate();
	}

	@Override
	protected InternalProject loadElement(ObjectInput in) throws IOException {
		return new ECPProjectImpl(in);
	}

	// @Override
	// protected InternalProject[] createElementArray(int size) {
	// return new InternalProject[size];
	// }

	@Override
	protected void notifyObservers(Collection<InternalProject> oldElements, Collection<InternalProject> newElements)
		throws Exception {
		ECPUtil.getECPObserverBus().notify(ECPProjectsChangedObserver.class)
			.projectsChanged((Collection) oldElements, (Collection) newElements);
	}

	@Override
	protected boolean isRemoveDisposedElements() {
		return false;
	}

}
