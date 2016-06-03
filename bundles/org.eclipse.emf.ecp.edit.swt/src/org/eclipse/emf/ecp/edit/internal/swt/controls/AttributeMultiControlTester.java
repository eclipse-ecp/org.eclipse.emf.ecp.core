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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.spi.ECPControlDescription;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester;
import org.eclipse.emf.ecp.edit.spi.util.ECPStaticApplicableTester;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * This is a dynamic tester for an attribute multi control. It tests whether there is a control with a static tester
 * which would fit.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class AttributeMultiControlTester implements ECPApplicableTester {

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated
	 **/
	@Override
	@Deprecated
	public int isApplicable(IItemPropertyDescriptor itemPropertyDescriptor, EObject eObject) {
		return isApplicable(eObject, (EStructuralFeature) itemPropertyDescriptor.getFeature(eObject));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester#isApplicable(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public int isApplicable(EObject eObject, EStructuralFeature feature) {
		int bestPriority = NOT_APPLICABLE;
		final ECPControlFactory controlFactory = Activator.getDefault().getECPControlFactory();
		if (controlFactory == null) {
			Activator.getDefault().ungetECPControlFactory();
			return bestPriority;
		}
		for (final ECPControlDescription description : controlFactory.getControlDescriptors()) {
			for (final ECPApplicableTester tester : description.getTester()) {
				if (ECPStaticApplicableTester.class.isInstance(tester)) {
					final ECPStaticApplicableTester test = (ECPStaticApplicableTester) tester;
					final int priority = getTesterPriority(test, feature, eObject);
					if (bestPriority < priority) {
						bestPriority = priority;
					}
				} else {
					continue;
				}
			}
		}
		Activator.getDefault().ungetECPControlFactory();
		return bestPriority;
	}

	/**
	 * Calculates the priority of the attribute tester.
	 *
	 * @param tester the tester to get the priority for
	 * @param feature the {@link EStructuralFeature}
	 * @param eObject the {@link EObject}
	 * @return the priority
	 */
	public static int getTesterPriority(ECPStaticApplicableTester tester,
		EStructuralFeature feature, EObject eObject) {
		if (!feature.isMany()) {
			return NOT_APPLICABLE;
		}

		if (EAttribute.class.isInstance(feature)) {
			final Class<?> instanceClass = ((EAttribute) feature).getEAttributeType().getInstanceClass();
			if (instanceClass != null) {
				if (instanceClass.isPrimitive()) {
					try {
						final Class<?> primitive = (Class<?>) tester.getSupportedClassType().getField("TYPE").get(null);//$NON-NLS-1$
						if (!primitive.equals(instanceClass)) {
							return NOT_APPLICABLE;
						}

					} catch (final IllegalArgumentException e) {
						return NOT_APPLICABLE;
					} catch (final SecurityException e) {
						return NOT_APPLICABLE;
					} catch (final IllegalAccessException e) {
						return NOT_APPLICABLE;
					} catch (final NoSuchFieldException e) {
						return NOT_APPLICABLE;
					}
				} else if (!tester.getSupportedClassType().isAssignableFrom(instanceClass)) {
					return NOT_APPLICABLE;
				}
			}
		} else if (EReference.class.isInstance(feature)) {
			return NOT_APPLICABLE;
		}
		if (!tester.isSingleValue()) {
			return NOT_APPLICABLE;
		}
		if (tester.getSupportedEObject().isInstance(eObject)
			&& (tester.getSupportedFeature() == null || feature.equals(eObject.eClass().getEStructuralFeature(
				tester.getSupportedFeature())))) {
			return tester.getPriority();
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 * @deprecated
	 *
	 */
	@Override
	@Deprecated
	public int isApplicable(VDomainModelReference domainModelReference) {
		return NOT_APPLICABLE;
	}

}
