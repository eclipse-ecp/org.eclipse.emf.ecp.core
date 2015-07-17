/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.diagnostician;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.emf.ecp.diagnostician.ECPValidator;

/**
 * {@link org.eclipse.emf.ecore.EValidator.Registry Registry} for {@link org.eclipse.emf.ecp.diagnostician.ECPValidator
 * ECPValidators}. The validators are retrieved
 * from the extension point.
 *
 * @author jfaltermeier
 *
 */
public final class ECPValidatorRegistry extends EValidatorRegistryImpl {

	/**
	 * The instance.
	 */
	public static final ECPValidatorRegistry INSTANCE = new ECPValidatorRegistry();

	private static final long serialVersionUID = -1274718490799689910L;

	private final Map<EClassifier, Set<ECPValidator>> eclassifierToAllValidatorsMap;

	private ECPValidatorRegistry() {
		eclassifierToAllValidatorsMap = new LinkedHashMap<EClassifier, Set<ECPValidator>>();
		final Map<EPackage, Map<EClassifier, ECPValidator>> registeredValidatorsPerPackage = readElementsFromExtensionPoint();
		registerValidators(registeredValidatorsPerPackage);

	}

	private Map<EPackage, Map<EClassifier, ECPValidator>> readElementsFromExtensionPoint() {
		final Map<EPackage, Map<EClassifier, ECPValidator>> registeredValidatorsPerPackage = new LinkedHashMap<EPackage, Map<EClassifier, ECPValidator>>();

		IConfigurationElement[] batchValidators = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.emf.ecp.validation.diagnostician.ecpValidators"); //$NON-NLS-1$
		for (final IConfigurationElement element : batchValidators) {
			batchValidators = null;
			try {
				final ECPValidator validator = (ECPValidator) element.createExecutableExtension("class"); //$NON-NLS-1$
				final Set<EClassifier> validatedEClassifier = validator.getValidatedEClassifier();
				for (final EClassifier eClassifier : validatedEClassifier) {
					if (!eclassifierToAllValidatorsMap.containsKey(eClassifier)) {
						eclassifierToAllValidatorsMap.put(eClassifier, new LinkedHashSet<ECPValidator>());
					}
					eclassifierToAllValidatorsMap.get(eClassifier).add(validator);
				}

				for (final EClassifier classifier : eclassifierToAllValidatorsMap.keySet()) {
					ECPValidator validatorToRegister;
					if (eclassifierToAllValidatorsMap.get(classifier).size() == 1) {
						validatorToRegister = eclassifierToAllValidatorsMap.get(classifier).iterator().next();
					} else {
						validatorToRegister = new ClassifierValidatorWrapper(classifier,
							eclassifierToAllValidatorsMap.get(classifier));
					}
					final EPackage ePackage = classifier.getEPackage();
					if (!registeredValidatorsPerPackage.containsKey(ePackage)) {
						registeredValidatorsPerPackage.put(ePackage, new LinkedHashMap<EClassifier, ECPValidator>());
					}
					registeredValidatorsPerPackage.get(ePackage).put(classifier, validatorToRegister);
				}

			} catch (final CoreException e) {
				Activator.log(e);
			}
		}
		return registeredValidatorsPerPackage;
	}

	private void registerValidators(Map<EPackage, Map<EClassifier, ECPValidator>> registeredValidatorsPerPackage) {
		for (final EPackage ePackage : registeredValidatorsPerPackage.keySet()) {
			final Map<EClassifier, ECPValidator> map = registeredValidatorsPerPackage.get(ePackage);
			final EValidator validator = new PackageValidatorWrapper(map);
			put(ePackage, validator);
		}
	}

	/**
	 * Check if the registry contains a validator for the given classifier.
	 *
	 * @param classifier the classifier to check
	 * @return <code>true</code> if validator is registerd, <code>false</code> otherwise
	 */
	public boolean hasValidator(EClassifier classifier) {
		return eclassifierToAllValidatorsMap.containsKey(classifier);
	}
}
