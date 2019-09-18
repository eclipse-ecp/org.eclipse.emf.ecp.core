/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.test.common.spi;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData.EClassifierExtendedMetaData;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData.EStructuralFeatureExtendedMetaData;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.listeners.InvocationListener;
import org.mockito.stubbing.Answer;

/**
 * Implementation of the EMF mock settings protocol.
 *
 * @since 1.22
 */
final class EMockSettingsImpl implements EMockSettings {

	private final MockSettings settings = Mockito.withSettings();

	private EClass eClass;
	private EList<? extends EObject> eContents;
	private EObject eContainer;

	/**
	 * Initializes me.
	 */
	EMockSettingsImpl() {
		super();

		settings.extraInterfaces(InternalEObject.class);
	}

	@Override
	public EMockSettings name(String name) {
		settings.name(name);
		return this;
	}

	@Override
	public EMockSettings defaultAnswer(Answer<?> defaultAnswer) {
		settings.defaultAnswer(defaultAnswer);
		return this;
	}

	@Override
	public EMockSettings verboseLogging() {
		settings.verboseLogging();
		return null;
	}

	@Override
	public EMockSettings invocationListeners(InvocationListener... listeners) {
		settings.invocationListeners(listeners);
		return this;
	}

	@Override
	public EMockSettings eClass(EClass eClass) {
		this.eClass = eClass;
		return this;
	}

	@Override
	public EMockSettings eContents(EList<? extends EObject> eContents) {
		this.eContents = eContents;
		return this;
	}

	@Override
	public EMockSettings eContainer(EObject eContainer) {
		this.eContainer = eContainer;
		return this;
	}

	<E extends EObject> E make(Class<E> type) {
		updateAdditionalInterfaces(type);
		final E result = mock(type, settings);

		EClass eClass = this.eClass;
		if (eClass == null) {
			eClass = guessEClass(type);
		}
		if (eClass != null) {
			when(result.eClass()).thenReturn(eClass);
		}

		if (eContents != null) {
			when(result.eContents()).thenReturn(wrap(eContents));
		}
		if (eContainer != null) {
			EMFMocking.eSetContainer(result, eContainer);
		}

		// Many corners of the EMF framework and EMF Forms expect an adapter list
		final EList<Adapter> adapterList = new BasicNotifierImpl.EAdapterList<>(result);
		Mockito.when(result.eAdapters()).thenReturn(adapterList);

		return result;
	}

	private <E> EList<E> wrap(List<? extends E> delegate) {
		return delegate == ECollections.EMPTY_ELIST
			? ECollections.emptyEList()
			: ECollections.unmodifiableEList(delegate);
	}

	/**
	 * Some EMF interfaces require additional internal-ish interfaces in their implementations.
	 * For example, access to "extended metadata".
	 *
	 * @param type the type being mocked
	 */
	private void updateAdditionalInterfaces(Class<?> type) {
		if (EClassifier.class.isAssignableFrom(type)) {
			settings.extraInterfaces(InternalEObject.class, EClassifierExtendedMetaData.Holder.class);
		} else if (EStructuralFeature.class.isAssignableFrom(type)) {
			settings.extraInterfaces(InternalEObject.class, EStructuralFeatureExtendedMetaData.Holder.class);
		}
	}

	private static EClass guessEClass(Class<? extends EObject> interfaceType) {
		final String typeName = interfaceType.getName();
		final String packageName = getPackageName(interfaceType);

		for (final Object next : EPackage.Registry.INSTANCE.values()) {
			final EPackage ePackage = next instanceof EPackage
				? (EPackage) next
				: ((EPackage.Descriptor) next).getEPackage();

			if (ePackage == null) {
				continue;
			}

			final Class<?> ePackageImplClass = ePackage.getClass();
			final Class<?>[] interfaces = ePackageImplClass.getInterfaces();
			for (final Class<?> iface : interfaces) {
				if (EPackage.class.isAssignableFrom(iface) && iface != EPackage.class) {
					// This is the package API interface
					final String ifacePackageName = getPackageName(iface);
					if (ifacePackageName.equals(packageName)) {
						// Find the EClass whose interface name is 'typeName'
						return ePackage.getEClassifiers().stream()
							.filter(EClass.class::isInstance).map(EClass.class::cast)
							.filter(ec -> typeName.equals(ec.getInstanceClassName()))
							.findAny().orElse(null);
					}
				}
			}
		}

		return null;
	}

	private static String getPackageName(Class<?> classs) {
		final String className = classs.getName();
		return className.substring(0, className.lastIndexOf('.'));
	}

}
