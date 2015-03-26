/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfforms.internal.core.services.labelprovider;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.emfforms.spi.core.services.emfspecificservice.EMFSpecificService;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;

/**
 * Implementation of {@link EMFFormsLabelProvider}. It provides a label service that delivers the display name and
 * description for a domain model reference and optionally an EObject.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsLabelProviderImpl implements EMFFormsLabelProvider {
	private EMFFormsDatabinding emfFormsDatabinding;
	private EMFSpecificService emfSpecificService;

	/**
	 * Sets the {@link EMFFormsDatabinding} service.
	 *
	 * @param emfFormsDatabinding The databinding service.
	 */
	protected void setEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * Unsets the {@link EMFFormsDatabinding} service.
	 *
	 * @param emfFormsDatabinding The databinding service.
	 */
	protected void unsetEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
		this.emfFormsDatabinding = null;
	}

	/**
	 * Sets the {@link EMFSpecificService}.
	 *
	 * @param emfSpecificService The {@link EMFSpecificService}
	 */
	protected void setEMFSpecificService(EMFSpecificService emfSpecificService) {
		this.emfSpecificService = emfSpecificService;
	}

	/**
	 * Unsets the {@link EMFSpecificService}.
	 *
	 * @param emfSpecificService The {@link EMFSpecificService}
	 */
	protected void unsetEMFSpecificService(EMFSpecificService emfSpecificService) {
		this.emfSpecificService = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public String getDisplayName(VDomainModelReference domainModelReference, EClass rootEClass) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootEClass).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootEClass);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return ex.getMessage();
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		if (eContainingClass.isAbstract() || eContainingClass.isInterface()) {
			return getFallbackLabel(structuralFeature);
		}
		final EObject tempInstance = EcoreUtil.create(eContainingClass);
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(
			tempInstance, structuralFeature);

		return itemPropertyDescriptor.getDisplayName(tempInstance);
	}

	/**
	 * Creates a fall back label from a {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature}
	 * @return The fall back label
	 */
	private String getFallbackLabel(final EStructuralFeature structuralFeature) {
		final String[] split = structuralFeature.getName().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"); //$NON-NLS-1$
		final char[] charArray = split[0].toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		split[0] = new String(charArray);
		final StringBuilder sb = new StringBuilder();
		for (final String str : split) {
			sb.append(str);
			sb.append(" "); //$NON-NLS-1$
		}
		return sb.toString().trim();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public String getDisplayName(VDomainModelReference domainModelReference, EObject rootObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return ex.getMessage();
		}
		final IObserving observing = (IObserving) observableValue;
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		final EObject value = (EObject) observing.getObserved();
		observableValue.dispose();
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);

		return itemPropertyDescriptor.getDisplayName(value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public String getDescription(VDomainModelReference domainModelReference, EClass rootEClass) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootEClass).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootEClass);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return ex.getMessage();
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		if (eContainingClass.isAbstract() || eContainingClass.isInterface()) {
			return getFallbackLabel(structuralFeature);
		}
		final EObject tempInstance = EcoreUtil.create(eContainingClass);
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(
			tempInstance, structuralFeature);

		return itemPropertyDescriptor.getDescription(tempInstance);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public String getDescription(VDomainModelReference domainModelReference, EObject rootObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return ex.getMessage();
		}
		final IObserving observing = (IObserving) observableValue;
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		final EObject value = (EObject) observing.getObserved();
		observableValue.dispose();

		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);

		return itemPropertyDescriptor.getDescription(value);
	}

}
