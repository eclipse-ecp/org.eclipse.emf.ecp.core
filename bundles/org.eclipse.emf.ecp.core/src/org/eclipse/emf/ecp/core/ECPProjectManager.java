/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core;

import java.util.Collection;

import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPProperties;

/**
 * The ECPProjectManager provides access to ECPProjects and manages their lifecycle.
 * It is available as an OSGi service or using {@link org.eclipse.emf.ecp.core.util.ECPUtil} It publishes
 * observable events on the {@link org.eclipse.emf.ecp.core.util.observer.ECPObserverBus ECPObserverBus}.
 * Related Observer types: {@link org.eclipse.emf.ecp.core.util.observer.ECPProjectsChangedObserver
 * ECPProjectsChangedObserver}, {@link org.eclipse.emf.ecp.core.util.observer.ECPProjectContentChangedObserver
 * ECPProjectContentChangedObserver}, {@link org.eclipse.emf.ecp.core.util.observer.ECPProjectOpenClosedObserver
 * ECPProjectOpenClosedObserver}, {@link org.eclipse.emf.ecp.core.util.observer.ECPProjectPreDeleteObserver
 * ECPProjectPreDeleteObserver}. Use {@link org.eclipse.emf.ecp.core.util.ECPUtil#getECPObserverBus()
 * ECPUtil#getECPObserverBus()} to
 * retrieve the ObserverBus and
 * {@link org.eclipse.emf.ecp.core.util.observer.ECPObserverBus#register(org.eclipse.emf.ecp.core.util.observer.ECPObserver)
 * ECPObserverBus#register(ECPObserver)} to register an Observer.
 *
 * @author Eike Stepper
 * @author Jonas
 * @author Eugen Neufeld
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ECPProjectManager {

	/**
	 * Method to construct an offline Project, this method calls
	 * {@link #createProject(ECPProvider, String, ECPProperties)} with empty properties. If
	 * {@link ECPProvider#hasCreateProjectWithoutRepositorySupport()} returns
	 * false an UnsupportedOperationException is thrown.
	 *
	 * @param provider the {@link ECPProvider} of this project
	 * @param name the name of the project
	 * @return created {@link ECPProject}
	 * @throws ECPProjectWithNameExistsException when a project with the same name already exists
	 */

	ECPProject createProject(ECPProvider provider, String name) throws ECPProjectWithNameExistsException;

	/**
	 * Method to construct an offline Project and notify listeners about this add. If
	 * {@link ECPProvider#hasCreateProjectWithoutRepositorySupport()} returns
	 * false an UnsupportedOperationException is thrown.
	 *
	 * @param provider the {@link ECPProvider} of this project
	 * @param name the name of the project
	 * @param properties the project properties
	 * @return created {@link ECPProject}
	 * @throws ECPProjectWithNameExistsException when a project with the same name already exists
	 */

	ECPProject createProject(ECPProvider provider, String name, ECPProperties properties)
		throws ECPProjectWithNameExistsException;

	/**
	 * Method to construct an shared Project, e.g. during a checkout, and notify listeners about this add.
	 *
	 * @param repository the {@link ECPRepository} of this project
	 * @param name the name of the project
	 * @param properties the project properties
	 * @return created {@link ECPProject}
	 * @throws ECPProjectWithNameExistsException when a project with the same name already exists
	 */
	ECPProject createProject(ECPRepository repository, String name, ECPProperties properties)
		throws ECPProjectWithNameExistsException;

	/**
	 * Method to construct a new Project based on an existing project as template. If the template project is shared, so
	 * is the created project.
	 *
	 * @param project the template {@link ECPProject}
	 * @param name the name of the created project
	 * @return the clone of the {@link ECPProject}
	 */
	ECPProject createProject(ECPProject project, String name);

	/**
	 * Retrieves the project the adaptable belongs to if possible.
	 * This method checks whether the adaptable is {@link org.eclipse.emf.ecp.core.util.ECPProjectAware ECPProjectAware}
	 * and else uses the AdapterUtil to adapt to a
	 * project.
	 *
	 * @param adaptable the Object to adapt
	 * @return the adapted {@link ECPProject}
	 */
	ECPProject getProject(Object adaptable);

	/**
	 * Searches for a project based on the provided {@link String}.
	 *
	 * @param name of the project to search for
	 * @return the {@link ECPProject}
	 */

	ECPProject getProject(String name);

	/**
	 * Returns all known projects.
	 *
	 * @return an array of all known projects
	 */
	Collection<ECPProject> getProjects();

}
