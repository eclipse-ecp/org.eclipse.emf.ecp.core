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
package org.eclipse.emfforms.internal.core.services.label;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.emfspecificservice.EMFSpecificService;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;

/**
 * Implementation of {@link EMFFormsLabelProvider}. It provides a label service that delivers the display name and
 * description for a domain model reference and optionally an EObject.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsLabelProviderDefaultImpl implements EMFFormsLabelProvider {
	private EMFFormsDatabinding emfFormsDatabinding;
	private EMFSpecificService emfSpecificService;
	private ReportService reportService;

	/**
	 * Sets the {@link EMFFormsDatabinding} service.
	 *
	 * @param emfFormsDatabinding The databinding service.
	 */
	protected void setEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
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
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public IObservableValue getDisplayName(VDomainModelReference domainModelReference) {
		Assert.create(domainModelReference).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, null);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		if (eContainingClass.isAbstract() || eContainingClass.isInterface()) {
			return getConstantObservableValue(getFallbackLabel(structuralFeature));
		}
		final EObject tempInstance = EcoreUtil.create(eContainingClass);
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(
			tempInstance, structuralFeature);

		return getConstantObservableValue(itemPropertyDescriptor.getDisplayName(tempInstance));
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
	 * @see org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider#getDisplayName(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IObservableValue getDisplayName(VDomainModelReference domainModelReference, EObject rootObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final IObserving observing = (IObserving) observableValue;
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		final EObject value = (EObject) observing.getObserved();
		observableValue.dispose();
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);

		return getConstantObservableValue(itemPropertyDescriptor.getDisplayName(value));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public IObservableValue getDescription(VDomainModelReference domainModelReference) {
		Assert.create(domainModelReference).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, null);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		if (eContainingClass.isAbstract() || eContainingClass.isInterface()) {
			return getConstantObservableValue(getFallbackLabel(structuralFeature));
		}
		final EObject tempInstance = EcoreUtil.create(eContainingClass);
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(
			tempInstance, structuralFeature);

		return getConstantObservableValue(itemPropertyDescriptor.getDescription(tempInstance));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider#getDescription(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IObservableValue getDescription(VDomainModelReference domainModelReference, EObject rootObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final IObserving observing = (IObserving) observableValue;
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		final EObject value = (EObject) observing.getObserved();
		observableValue.dispose();

		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);

		return getConstantObservableValue(itemPropertyDescriptor.getDescription(value));
	}

	private IObservableValue getConstantObservableValue(String value) {
		return Observables.constantObservableValue(value, String.class);
	}

}
