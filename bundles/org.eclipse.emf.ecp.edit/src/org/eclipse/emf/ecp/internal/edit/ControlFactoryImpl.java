/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.edit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlDescription;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester;
import org.eclipse.emf.ecp.edit.spi.util.ECPStaticApplicableTester;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.osgi.framework.Bundle;

/**
 * The ControlFactoryImpl is a Singleton which reads the org.eclipse.emf.ecp.editor.widgets ExtensionPoint and provides
 * a method ({@link #createControl(Class, EObject, VDomainModelReference)}) for creating a suitable
 * control for with the known widgets.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public final class ControlFactoryImpl implements ECPControlFactory {

	private static final String CONTROL_EXTENSION = "org.eclipse.emf.ecp.edit.controls"; //$NON-NLS-1$

	private static final String CLASS_ATTRIBUTE = "class";//$NON-NLS-1$
	private static final String CONTROL_ID = "id";//$NON-NLS-1$
	private static final String LABEL_ATTRIBUTE = "showLabel";//$NON-NLS-1$

	private static final String TEST_DYNAMIC = "dynamicTest";//$NON-NLS-1$
	private static final String CONTROL_TESTER = "testClass";//$NON-NLS-1$

	private static final String TEST_STATIC = "staticTest";//$NON-NLS-1$
	private static final String TESTER_PRIORITY = "priority";//$NON-NLS-1$
	private static final String TESTER_CLASSTYPE = "supportedClassType";//$NON-NLS-1$
	private static final String TESTER_EOBJECT = "supportedEObject";//$NON-NLS-1$
	private static final String TESTER_FEATURE = "supportedFeature";//$NON-NLS-1$
	private static final String TESTER_SINGLEVALUE = "singleValue";//$NON-NLS-1$

	private final Set<ECPControlDescription> controlDescriptors = new HashSet<ECPControlDescription>();

	/**
	 * The Singleton for accessing the ControlFactoryImpl.
	 */
	public static final ControlFactoryImpl INSTANCE = new ControlFactoryImpl();

	/**
	 * Constructor which reads the extension points. Thus new controls can't be added during runtime.
	 */
	public ControlFactoryImpl() {
		readExtensionPoint();
	}

	private void readExtensionPoint() {
		final IConfigurationElement[] controls = Platform.getExtensionRegistry().getConfigurationElementsFor(
			CONTROL_EXTENSION);
		for (final IConfigurationElement e : controls) {
			try {
				final String id = e.getAttribute(CONTROL_ID);
				final String clazz = e.getAttribute(CLASS_ATTRIBUTE);
				final Class<? extends ECPAbstractControl> resolvedClass = loadClass(e.getContributor().getName(), clazz);
				final boolean showLabel = Boolean.parseBoolean(e.getAttribute(LABEL_ATTRIBUTE));

				// ECPApplicableTester tester=null;
				final Set<ECPApplicableTester> tester = new HashSet<ECPApplicableTester>();
				for (final IConfigurationElement testerExtension : e.getChildren()) {
					if (TEST_DYNAMIC.equals(testerExtension.getName())) {
						tester.add((ECPApplicableTester) testerExtension.createExecutableExtension(CONTROL_TESTER));
					}
					else if (TEST_STATIC.equals(testerExtension.getName())) {
						final boolean singleValue = Boolean.parseBoolean(testerExtension
							.getAttribute(TESTER_SINGLEVALUE));
						final int priority = Integer.parseInt(testerExtension.getAttribute(TESTER_PRIORITY));

						final String type = testerExtension.getAttribute(TESTER_CLASSTYPE);
						final Class<?> supportedClassType = loadClass(testerExtension.getContributor().getName(), type);
						// Class.forName(type);

						String eObject = testerExtension.getAttribute(TESTER_EOBJECT);
						if (eObject == null) {
							eObject = "org.eclipse.emf.ecore.EObject";//$NON-NLS-1$
						}
						final Class<? extends EObject> supportedEObject = loadClass(testerExtension.getContributor()
							.getName(), eObject);

						final String supportedFeature = testerExtension.getAttribute(TESTER_FEATURE);

						tester.add(new ECPStaticApplicableTester(singleValue, priority, supportedClassType,
							supportedEObject, supportedFeature));
					}
				}
				final ECPControlDescription controlDescription = new ECPControlDescription(id, resolvedClass,
					showLabel, tester);
				controlDescriptors.add(controlDescription);
			} catch (final ClassNotFoundException e1) {
				Activator.logException(e1);
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz) throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(clazz
				+ LocalizationServiceHelper.getString(ControlFactoryImpl.class,
					MessageKeys.CONTROLFACTROY_CANNOT_BE_LOADED)
				+ bundleName
				+ LocalizationServiceHelper.getString(ControlFactoryImpl.class,
					MessageKeys.CONTROLFACTORY_CANNOT_BE_RESOLVED));
		}
		return (Class<T>) bundle.loadClass(clazz);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ECPControlFactory#createControl(java.lang.Class, org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public <T> T createControl(Class<T> controlType, EObject domainModel, VDomainModelReference domainModelReference) {

		final ECPControlDescription controlDescription = getControlCandidate(controlType, domainModel,
			domainModelReference);
		if (controlDescription == null) {
			return null;
		}
		final T control = getControlInstance(controlDescription);

		return control;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ECPControlFactory#createControl(java.lang.String)
	 */
	@Override
	public <T> T createControl(String controlId) {

		ECPControlDescription controlDescription = null;
		for (final ECPControlDescription desc : controlDescriptors) {
			if (desc.getId().equals(controlId)) {
				controlDescription = desc;
				break;
			}
		}
		if (controlDescription == null) {
			return null;
		}
		final T control = getControlInstance(controlDescription);

		return control;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<ECPControlDescription> getControlDescriptors() {
		return new HashSet<ECPControlDescription>(controlDescriptors);
	}

	@SuppressWarnings("unchecked")
	private static <T> T getControlInstance(ECPControlDescription controlDescription) {
		try {
			final Constructor<?> controlConstructor = controlDescription.getControlClass()
				.getConstructor();
			return (T) controlConstructor.newInstance();
		} catch (final IllegalArgumentException ex) {
			Activator.logException(ex);
		} catch (final InstantiationException ex) {
			Activator.logException(ex);
		} catch (final IllegalAccessException ex) {
			Activator.logException(ex);
		} catch (final InvocationTargetException ex) {
			Activator.logException(ex);
		} catch (final SecurityException ex) {
			Activator.logException(ex);
		} catch (final NoSuchMethodException ex) {
			Activator.logException(ex);
		}
		return null;
	}

	private ECPControlDescription getControlCandidate(Class<?> controlClass, EObject domainModel,
		VDomainModelReference domainModelReference) {
		int highestPriority = -1;
		ECPControlDescription bestCandidate = null;
		if (domainModelReference == null) {
			return bestCandidate;
		}
		IObservableValue observableValue;
		try {
			observableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(domainModelReference, domainModel);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return bestCandidate;
		}
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		for (final ECPControlDescription description : controlDescriptors) {

			if (!controlClass.isAssignableFrom(description.getControlClass())) {
				continue;
			}
			int currentPriority = -1;

			for (final ECPApplicableTester tester : description.getTester()) {
				final int testerPriority = tester.isApplicable(eObject, structuralFeature);
				if (testerPriority > currentPriority) {
					currentPriority = testerPriority;
				}

			}

			if (currentPriority > highestPriority) {
				highestPriority = currentPriority;
				bestCandidate = description;
			}
		}
		return bestCandidate;
	}

}
