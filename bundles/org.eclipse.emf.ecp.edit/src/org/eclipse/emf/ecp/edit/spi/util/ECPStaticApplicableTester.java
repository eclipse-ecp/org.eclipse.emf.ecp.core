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
package org.eclipse.emf.ecp.edit.spi.util;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * The implementation of the {@link ECPApplicableTester} for a static test, like defined in the staticTest element.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public final class ECPStaticApplicableTester implements ECPApplicableTester {

	private final boolean singleValue;
	private final int priority;
	private final Class<?> supportedClassType;
	private final Class<? extends EObject> supportedEObject;
	private final String supportedFeature;

	/**
	 * The constructor of the static tester.
	 *
	 * @param singleValue whether the corresponding control supports only single valued features
	 * @param priority the static priority
	 * @param supportedClassType the class of the supported type
	 * @param supportedEObject the eobject this tester allows
	 * @param supportedFeature the feature this tester allows
	 */
	public ECPStaticApplicableTester(boolean singleValue, int priority, Class<?> supportedClassType,
		Class<? extends EObject> supportedEObject, String supportedFeature) {
		this.singleValue = singleValue;
		this.priority = priority;
		this.supportedClassType = supportedClassType;
		this.supportedEObject = supportedEObject;
		this.supportedFeature = supportedFeature;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Deprecated
	public int isApplicable(IItemPropertyDescriptor itemPropertyDescriptor, EObject eObject) {
		return isApplicable(eObject, (EStructuralFeature) itemPropertyDescriptor.getFeature(eObject));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 * @since 1.2
	 * @deprecated
	 */
	@Deprecated
	@Override
	public int isApplicable(VDomainModelReference domainModelReference) {
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester#isApplicable(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 * @since 1.2
	 * @deprecated
	 */
	@Deprecated
	@Override
	public int isApplicable(EObject eObject, EStructuralFeature feature) {
		// if the feature is a multiValue and the description is a singlevalue continue
		if (isSingleValue() == feature.isMany()) {
			return NOT_APPLICABLE;
		}
		// if we have an attribute
		if (EAttribute.class.isInstance(feature)) {
			if (checkAttributeInvalid((EAttribute) feature)) {
				return NOT_APPLICABLE;
			}
		}
		// if we have an reference the the classes
		else if (EReference.class.isInstance(feature)) {
			Class<?> instanceClass = feature.getEType().getInstanceClass();
			if (instanceClass == null) {
				instanceClass = EObject.class;
			}
			if (!getSupportedClassType().isAssignableFrom(instanceClass)) {
				return NOT_APPLICABLE;
			}
		}
		// if the supported eobject is assignable from the current eobject and the supported feature is eitehr null or
		// equals the current one
		if (getSupportedEObject().isInstance(eObject)
			&& (getSupportedFeature() == null || feature.equals(eObject.eClass().getEStructuralFeature(
				getSupportedFeature())))) {
			return getPriority();
		}
		return NOT_APPLICABLE;
	}

	/**
	 * @return
	 *
	 */
	private boolean checkAttributeInvalid(EAttribute attribute) {
		final Class<?> instanceClass = attribute.getEAttributeType().getInstanceClass();
		if (instanceClass == null) {
			return true;
		}
		if (Object.class.equals(getSupportedClassType())) {
			return false;
		}
		// if the attribute class is an primitive test the primitive types
		if (instanceClass.isPrimitive()) {
			try {
				final Class<?> primitive = (Class<?>) getSupportedClassType().getField("TYPE").get(null); //$NON-NLS-1$
				if (!primitive.equals(instanceClass)) {
					return true;
				}

			} catch (final IllegalArgumentException e) {
				return true;
			} catch (final SecurityException e) {
				return true;
			} catch (final IllegalAccessException e) {
				return true;
			} catch (final NoSuchFieldException e) {
				return true;
			}
		}
		// otherwise test the classes itself
		else if (!getSupportedClassType().isAssignableFrom(instanceClass)) {
			return true;
		}

		return false;
	}

	/**
	 * Whether the corresponding control is allowed only for single values.
	 *
	 * @return true if only a single value is allows
	 */
	public boolean isSingleValue() {
		return singleValue;
	}

	/**
	 * The static priority of the corresponding control.
	 *
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * The eobejct which is supported by the corresponding control.
	 *
	 * @return the class of the supported eobejct
	 */
	public Class<? extends EObject> getSupportedEObject() {
		return supportedEObject;
	}

	/**
	 * The name of the feature the corresponding control supports.
	 *
	 * @return the name of the supported feature
	 */
	public String getSupportedFeature() {
		return supportedFeature;
	}

	/**
	 * The class of the type the corresponding control supports.
	 *
	 * @return the class of the supported type
	 */
	public Class<?> getSupportedClassType() {
		return supportedClassType;
	}

}
