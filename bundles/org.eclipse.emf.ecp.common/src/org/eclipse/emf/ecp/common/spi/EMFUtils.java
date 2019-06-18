/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

/**
 * Util class for basic EMF.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public final class EMFUtils {

	private EMFUtils() {
	}

	/**
	 * This method looks through all known {@link EPackage}s to find all concrete subclasses for the provided super
	 * class (abstract classes and interfaces will be ignored). If the EClass is EObject, all non abstract and non
	 * interface classes will be returned.
	 *
	 * @param superClass
	 *            - the class for which to get the subclasses
	 * @return a {@link Collection} of {@link EClass EClasses}
	 */
	public static Collection<EClass> getSubClasses(EClass superClass) {
		final Collection<EClass> classes = new HashSet<EClass>();
		for (final EPackage ePackage : getAllRegisteredEPackages()) {
			for (final EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					final EClass eClass = (EClass) eClassifier;
					if (!eClass.isAbstract() && !eClass.isInterface()
						&& (EcorePackage.eINSTANCE.getEObject() == superClass || superClass.isSuperTypeOf(eClass))) {
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
		final Set<EPackage> ePackages = new HashSet<EPackage>();
		final Set<String> namespaceURIs = new LinkedHashSet<String>(Registry.INSTANCE.keySet());
		for (final String nsURI : namespaceURIs) {
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
				/*
				 * this case is actually possible! we should only collect non null
				 * epackages
				 */
				continue;
			}
			ePackages.add(ePackage);
		}
		return ePackages;
	}

	/**
	 * Check two EObjects for equality by comparing their EClass and all their features' values.
	 * Additionally, an arbitrary number of features can be supplied whose values will <strong>not</strong> be compared
	 * to determine equality. This can be used to implement a custom equality check in the client.
	 *
	 * @param property The EObject triggering the comparison, might be <code>null</code>.
	 * @param other The other style EObject, might be <code>null</code>
	 * @param filteredFeatures Features that are ignored in the equality check.
	 * @return <code>true</code> if the EClass and the values of all features are equal or if both EObjects are
	 *         <code>null</code>, <code>false</code> otherwise
	 * @since 1.18
	 */
	public static boolean filteredEquals(EObject property, EObject other,
		EStructuralFeature... filteredFeatures) {
		if (property == null) {
			return other == null;
		}
		if (other == null) {
			return false;
		}
		if (property.eClass() != other.eClass()) {
			return false;
		}

		final List<EStructuralFeature> filter = Arrays.asList(filteredFeatures);
		for (final EStructuralFeature esf : property.eClass().getEAllStructuralFeatures()) {
			if (filter.contains(esf)) {
				continue;
			}
			if (!equalFeature(property, other, esf)) {
				return false;
			}
		}
		return true;
	}

	// extract feature comparison to reduce N-Path complexity of filteredEquals
	private static boolean equalFeature(EObject eObject1, EObject eObject2,
		final EStructuralFeature structuralFeature) {
		if (structuralFeature.isUnsettable()
			&& eObject1.eIsSet(structuralFeature) != eObject2.eIsSet(structuralFeature)) {
			return false;
		}
		if (eObject1.eGet(structuralFeature) == null) {
			return eObject2.eGet(structuralFeature) == null;
		}
		if (!eObject1.eGet(structuralFeature).equals(eObject2.eGet(structuralFeature))) {
			return false;
		}
		return true;
	}

	/**
	 * Tries to adapt the given EObject to the given class. This is done with help of the EObject's
	 * AdapterFactoryEditingDomain.
	 *
	 * @param <A> The adapter type
	 * @param object The {@link EObject} to adapt
	 * @param adapter The adapter class
	 * @return The adapted EObject or nothing if the EObjects AdapterFactoryEditingDomain could not be determined or the
	 *         EObject could not be adapted to the target type.
	 * @since 1.21
	 */
	public static <A> Optional<A> adapt(EObject object, Class<A> adapter) {
		return Optional.ofNullable(object)
			.map(AdapterFactoryEditingDomain::getEditingDomainFor)
			.filter(AdapterFactoryEditingDomain.class::isInstance)
			.map(AdapterFactoryEditingDomain.class::cast)
			.map(AdapterFactoryEditingDomain::getAdapterFactory)
			.map(factory -> factory.adapt(object, adapter))
			.map(adapter::cast);
	}
}
