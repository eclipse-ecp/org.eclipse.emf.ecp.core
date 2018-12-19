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
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
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
	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private EMFSpecificService emfSpecificService;
	private ReportService reportService;

	/**
	 * Sets the {@link EMFFormsDatabinding} service.
	 *
	 * @param emfFormsDatabinding The databinding service.
	 */
	protected void setEMFFormsDatabinding(EMFFormsDatabindingEMF emfFormsDatabinding) {
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
	 * Returns the display name of the {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature}
	 * @return The localized feature name
	 */
	public IObservableValue getDisplayName(EStructuralFeature structuralFeature) {
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		if (eContainingClass.isAbstract() || eContainingClass.isInterface()
			|| eContainingClass.getInstanceClass() == null) {
			return getConstantObservableValue(getFallbackLabel(structuralFeature));
		}
		final EObject tempInstance = EcoreUtil.create(eContainingClass);
		return getDisplayNameObservableValue(tempInstance, structuralFeature);
	}

	@Deprecated
	@Override
	public IObservableValue getDisplayName(VDomainModelReference domainModelReference) {
		Assert.create(domainModelReference).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, (EObject) null);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();

		return getDisplayName(structuralFeature);
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

	@Override
	public IObservableValue getDisplayName(VDomainModelReference domainModelReference, EClass rootEClass) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootEClass).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootEClass);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();

		return getDisplayName(structuralFeature);
	}

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
		return getDisplayNameObservableValue(value, structuralFeature);
	}

	private IObservableValue getDisplayNameObservableValue(EObject containingEObject,
		EStructuralFeature structuralFeature) {
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(
			containingEObject, structuralFeature);
		if (itemPropertyDescriptor == null) {
			reportMissingPropertyDescriptor(containingEObject, structuralFeature);
			return getConstantObservableValue(getFallbackLabel(structuralFeature));
		}

		return getConstantObservableValue(itemPropertyDescriptor.getDisplayName(containingEObject));
	}

	@Deprecated
	@Override
	public IObservableValue getDescription(VDomainModelReference domainModelReference) {
		Assert.create(domainModelReference).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, (EObject) null);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		if (eContainingClass.isAbstract() || eContainingClass.isInterface()
			|| eContainingClass.getInstanceClass() == null) {
			return getConstantObservableValue(getFallbackLabel(structuralFeature));
		}
		final EObject tempInstance = EcoreUtil.create(eContainingClass);
		return getDescriptionObservable(tempInstance, structuralFeature);
	}

	@Override
	public IObservableValue getDescription(VDomainModelReference domainModelReference, EClass rootEClass) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootEClass).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootEClass);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return getConstantObservableValue(ex.getMessage());
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		if (eContainingClass.isAbstract() || eContainingClass.isInterface()
			|| eContainingClass.getInstanceClass() == null) {
			return getConstantObservableValue(getFallbackLabel(structuralFeature));
		}
		final EObject tempInstance = EcoreUtil.create(eContainingClass);
		return getDescriptionObservable(tempInstance, structuralFeature);
	}

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
		return getDescriptionObservable(value, structuralFeature);
	}

	private IObservableValue getDescriptionObservable(EObject containingEObject, EStructuralFeature structuralFeature) {
		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(
			containingEObject, structuralFeature);
		if (itemPropertyDescriptor == null) {
			reportMissingPropertyDescriptor(containingEObject, structuralFeature);
			return getConstantObservableValue(getFallbackLabel(structuralFeature));
		}

		return getConstantObservableValue(itemPropertyDescriptor.getDescription(containingEObject));
	}

	private IObservableValue getConstantObservableValue(String value) {
		return Observables.constantObservableValue(value, String.class);
	}

	private void reportMissingPropertyDescriptor(final EObject value, final EStructuralFeature structuralFeature) {
		reportService.report(
			new AbstractReport(String.format("No IItemPropertyDescriptor for feature %2$s in EClass %1$s found.", //$NON-NLS-1$
				value.eClass().getName(), structuralFeature.getName())));
	}

}
