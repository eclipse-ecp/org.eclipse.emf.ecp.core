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
package org.eclipse.emf.ecp.spi.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPProjectAware;
import org.eclipse.emf.ecp.internal.core.util.PropertiesStore.StorableElement;
import org.eclipse.emf.ecp.spi.core.InternalProvider.LifecycleEvent;

/**
 * @author Eike Stepper
 * @author Eugen Neufeld
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 1.1
 */
public interface InternalProject extends ECPProject, ECPProjectAware, StorableElement, Cloneable {

	/**
	 * This method returns the repository this project is shared on.
	 *
	 * @return the repository of this project or null if not shared
	 */
	@Override
	InternalRepository getRepository();

	/**
	 * This method returns the provider of this project.
	 *
	 * @return the provider of this project
	 */
	@Override
	InternalProvider getProvider();

	/**
	 * This method returns the provider specific data of this project.
	 *
	 * @return the provider specific data of this project
	 */
	Object getProviderSpecificData();

	/**
	 * This method sets the provider specific data of this project.
	 *
	 * @param data the provider specific data of this project
	 */
	void setProviderSpecificData(Object data);

	/**
	 * This method is a callback for the provider to notify the project about changes.
	 *
	 * @param objects the objects that have changed
	 * @param structural if the changes where structural (e.g. delete)
	 */
	void notifyObjectsChanged(Collection<Object> objects, boolean structural);

	/**
	 * This method undisposes the project based on a repository.
	 *
	 * @param repository the repository
	 */
	void undispose(InternalRepository repository);

	/**
	 * This method is used to notify the provider about a {@link LifecycleEvent} of this project.
	 *
	 * @param event to pass to the provider
	 */
	void notifyProvider(LifecycleEvent event);

	/**
	 * This method clones a project.
	 *
	 * @param name the name of the project to create
	 * @return the cloned project
	 */
	InternalProject clone(String name);

	/**
	 * Saves the properties, such as visible packages or the name of the project into the workspace.
	 *
	 * @deprecated As of 1.1 properties are saved automatically when they're changed.
	 */
	@Deprecated
	void saveProperties();

	/**
	 * Get all possible {@link EObject}s from the provider to which a reference can be added from a certain
	 * {@link EObject} based
	 * on the type of the {@link EReference}.
	 *
	 * @param eObject
	 *            - the {@link EObject} for which the reference should be set.
	 * @param eReference
	 *            - the {@link EReference} to be set.
	 * @return {@link Iterator} over all {@link EObject} that can be added as a reference
	 */
	Iterator<EObject> getReferenceCandidates(EObject eObject, EReference eReference);

	/**
	 * This method checks whether the provided object is the model root of the project.
	 *
	 * @param object the object to check
	 * @return true if the object is the root of the model of this project, false otherwise
	 */
	boolean isModelRoot(Object object);

	/**
	 * Returns a collection of {@link EPackage}s which are not supported by the provider. EObjects from these packages
	 * cannot be created within the project.
	 *
	 * @return {@link Collection} of unsupported {@link EPackage}s
	 */
	Set<EPackage> getUnsupportedEPackages();

	/**
	 * Set the visible {@link EPackage}s. New model elements can only be created from {@link EPackage}s contained in
	 * the visiblePackages and the {@link #setVisibleEClasses(Set)}.
	 *
	 * @param visiblePackages the {@link EPackage}s to be visible
	 */
	void setVisiblePackages(Set<EPackage> visiblePackages);

	/**
	 * Get the currently visible {@link EPackage}s. If no filter is set, then all {@link EPackage}s supported by the
	 * provider are returned.
	 *
	 * @return {@link Set} of {@link EPackage}s that should be available, or all supported EPackages
	 */
	Set<EPackage> getVisiblePackages();

	/**
	 * Get the currently visible {@link EClass}es. If no visible {@link EClass}es are set, then an empty {@link Set} is
	 * returned.
	 *
	 * @return {@link Set} of {@link EClass}es that should be available, or empty.
	 */
	Set<EClass> getVisibleEClasses();

	/**
	 * Set the visible {@link EClass}es.
	 *
	 * @param visibleEClasses the classes that should be available
	 */
	void setVisibleEClasses(Set<EClass> visibleEClasses);

	/**
	 * Check whether a project contains an Object.
	 *
	 * @param object the object to check for containment
	 * @return true if the object is in the project, false otherwise
	 */
	boolean contains(Object object);
}
