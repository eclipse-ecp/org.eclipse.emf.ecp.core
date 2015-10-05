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
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.ECPRepositoryManager;
import org.eclipse.emf.ecp.core.util.observer.ECPObserverBus;
import org.eclipse.emf.ecp.internal.core.Activator;
import org.eclipse.emf.ecp.internal.core.util.ElementDescriptor;
import org.eclipse.emf.ecp.internal.core.util.Properties;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable;

/**
 * This class provides common functionality.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public final class ECPUtil {

	private ECPUtil() {
	}

	/**
	 * Return the common {@link ECPContainer} for the provided elements.
	 *
	 * @param contextProvider the {@link ECPModelContextProvider} to use
	 * @param elements the elements to check
	 * @return the common {@link ECPContainer} for the elements or null
	 */
	public static ECPContainer getModelContext(ECPModelContextProvider contextProvider, Object... elements) {
		ECPContainer commonContext = null;
		for (final Object element : elements) {
			final ECPContainer elementContext = contextProvider.getModelContext(element);
			if (elementContext == null) {
				return null;
			}

			if (elementContext != commonContext) {
				if (commonContext == null) {
					commonContext = elementContext;
				} else {
					return null;
				}
			}
		}

		return commonContext;
	}

	/**
	 * This creates an empty {@link ECPProperties}.
	 *
	 * @return an empty {@link ECPProperties}
	 */
	public static ECPProperties createProperties() {
		return new Properties();
	}

	/**
	 * Checks whether an object is an {@link ECPDisposable} and disposed.
	 *
	 * @param object the object to check
	 * @return true if the object is an instance of {@link ECPDisposable} and {@link ECPDisposable#isDisposed()} returns
	 *         true, false otherwise
	 */
	public static boolean isDisposed(Object object) {
		if (object instanceof ECPDisposable) {
			final ECPDisposable disposable = (ECPDisposable) object;
			return disposable.isDisposed();
		}

		return false;
	}

	/**
	 * Checks whether an object is an {@link ECPProject} and closed.
	 *
	 * @param object the object to check
	 * @return true if the object is an instance of {@link ECPProject} and not open, false otherwise
	 */
	public static boolean isClosed(Object object) {
		if (object instanceof ECPProject) {
			final ECPProject closeable = (ECPProject) object;
			return !closeable.isOpen();
		}

		return false;
	}

	/**
	 * Checks whether the {@link ECPElement} is an {@link ElementDescriptor} and resolves it when necessary.
	 *
	 * @param elementOrDescriptor the {@link ECPElement} to check
	 * @return the resolved Object or the original object if it is not an descriptor
	 */
	public static ECPElement getResolvedElement(ECPElement elementOrDescriptor) {
		if (elementOrDescriptor instanceof ElementDescriptor) {
			final ElementDescriptor<?> descriptor = (ElementDescriptor<?>) elementOrDescriptor;
			return descriptor.getResolvedElement();
		}

		return elementOrDescriptor;
	}

	/**
	 * This method looks through all known {@link EPackage}s to find all subclasses for the provided super class.
	 *
	 * @param superClass
	 *            - the class for which to get the subclasses
	 * @return a {@link Collection} of {@link EClass EClasses}
	 */
	public static Collection<EClass> getSubClasses(EClass superClass) {
		final Collection<EClass> classes = new HashSet<EClass>();

		// avoid ConcurrentModificationException while iterating over the registry's key set
		final List<String> keySet = new ArrayList<String>(Registry.INSTANCE.keySet());
		for (final String nsURI : keySet) {
			EPackage ePackage;
			try {
				ePackage = Registry.INSTANCE.getEPackage(nsURI);
			}
			// BEGIN SUPRESS CATCH EXCEPTION
			catch (final Exception ex) {// END SUPRESS CATCH EXCEPTION
				/* If there is a wrongly configured EPackage the call to getEPackage might throw a runtime exception */
				/* Catch here, so we can still loop through the whole registry */
				continue;
			}
			if (ePackage == null) {
				/* possible! */
				continue;
			}
			for (final EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					final EClass eClass = (EClass) eClassifier;
					if (superClass.isSuperTypeOf(eClass) && !eClass.isAbstract() && !eClass.isInterface()) {
						classes.add(eClass);
					}
				}
			}
		}
		return classes;
	}

	/**
	 * Returns the set of all known {@link EPackage EPackages}.
	 *
	 * @return the Set of all known {@link EPackage Epackages}
	 */
	public static Set<EPackage> getAllRegisteredEPackages() {
		return EMFUtils.getAllRegisteredEPackages();
	}

	/**
	 * Helper method to get the instance of the {@link ECPProjectManager}.
	 *
	 * @return the {@link ECPProjectManager}
	 */
	public static ECPProjectManager getECPProjectManager() {

		final ECPProjectManager ecpProjectManagerInstance = Activator.getECPProjectManager();

		return ecpProjectManagerInstance;
	}

	/**
	 * Helper method to get the instance of the {@link ECPRepositoryManager}.
	 *
	 * @return the {@link ECPRepositoryManager}
	 */
	public static ECPRepositoryManager getECPRepositoryManager() {

		final ECPRepositoryManager epm = Activator.getECPRepositoryManager();

		return epm;
	}

	/**
	 * Helper method to get the instance of the {@link ECPProviderRegistry}.
	 *
	 * @return the {@link ECPProviderRegistry}
	 */
	public static ECPProviderRegistry getECPProviderRegistry() {

		final ECPProviderRegistry ecpProviderRegistryInstance = Activator.getECPProviderRegistry();

		return ecpProviderRegistryInstance;
	}

	/**
	 * Helper method to get the instance of the {@link ECPObserverBus}.
	 *
	 * @return the {@link ECPObserverBus}
	 */
	public static ECPObserverBus getECPObserverBus() {
		return Activator.getECPObserverBus();
	}

}
