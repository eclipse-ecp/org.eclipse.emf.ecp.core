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
package org.eclipse.emfforms.internal.core.services.editsupport;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.internal.core.services.label.BundleResolverImpl;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.emfspecificservice.EMFSpecificService;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.osgi.framework.Bundle;

/**
 * EMF implementation of {@link EMFFormsEditSupport}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class EMFFormsEditSupportImpl implements EMFFormsEditSupport {
	private EMFFormsDatabinding emfFormsDatabinding;
	private EMFSpecificService emfSpecificService;
	private ReportService reportService;
	private final BundleResolver bundleResolver = new BundleResolverImpl();
	private EMFFormsLocalizationService emfFormsLocalizationService;

	/**
	 * Sets the {@link EMFFormsLocalizationService} service.
	 *
	 * @param emfFormsLocalizationService The localization service.
	 */
	protected void setEMFFormsLocalizationService(EMFFormsLocalizationService emfFormsLocalizationService) {
		this.emfFormsLocalizationService = emfFormsLocalizationService;
	}

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
	 * @see org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport#isMultiLine(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean isMultiLine(VDomainModelReference domainModelReference, EObject rootObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return false;
		}
		final IObserving observing = (IObserving) observableValue;
		final EObject value = (EObject) observing.getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);
		if (itemPropertyDescriptor == null) {
			reportMissingPropertyDescriptor(value, structuralFeature);
			return false;
		}

		return itemPropertyDescriptor.isMultiLine(value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport#canSetProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean canSetProperty(VDomainModelReference domainModelReference, EObject rootObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return false;
		}
		final IObserving observing = (IObserving) observableValue;
		final EObject value = (EObject) observing.getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);
		if (itemPropertyDescriptor == null) {
			reportMissingPropertyDescriptor(value, structuralFeature);
			return false;
		}
		return itemPropertyDescriptor.canSetProperty(value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport#getText(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	@Override
	public String getText(VDomainModelReference domainModelReference, EObject rootObject, Object element) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();
		Assert.create(element).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return ex.getMessage();
		}
		final IObserving observing = (IObserving) observableValue;
		final EObject value = (EObject) observing.getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		if (EAttribute.class.isInstance(structuralFeature)
			&& EcorePackage.eINSTANCE.getEEnum().isInstance(EAttribute.class.cast(structuralFeature).getEType())) {
			final String result = getEnumLiteral(structuralFeature, element);
			if (result != null) {
				return result;
			}
		}

		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);
		if (itemPropertyDescriptor == null) {
			reportMissingPropertyDescriptor(value, structuralFeature);
			return null;
		}
		final IItemLabelProvider labelProvider = itemPropertyDescriptor.getLabelProvider(rootObject);

		return labelProvider.getText(element);
	}

	private static final String LITERAL_NAME = "_UI_%1$s_%2$s_literal"; //$NON-NLS-1$

	private String getEnumLiteral(EStructuralFeature feature, Object element) {
		final EClassifier featureType = feature.getEType();
		Bundle bundle;
		try {
			bundle = bundleResolver.getEditBundle(featureType);
			final String key = String.format(LITERAL_NAME, featureType.getName(),
				EEnum.class.cast(featureType).getEEnumLiteralByLiteral(
					Enum.class.cast(element).toString()).getName());
			return emfFormsLocalizationService.getString(bundle, key);
		} catch (final NoBundleFoundException ex) {
			// do nothing - see bug 467498
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport#getImage(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	@Override
	public Object getImage(VDomainModelReference domainModelReference, EObject rootObject, Object element) {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();
		// Assert.create(element).notNull();

		IObservableValue observableValue;
		try {
			observableValue = emfFormsDatabinding.getObservableValue(domainModelReference,
				rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return ex.getMessage();
		}
		final IObserving observing = (IObserving) observableValue;
		final EObject value = (EObject) observing.getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		final IItemPropertyDescriptor itemPropertyDescriptor = emfSpecificService.getIItemPropertyDescriptor(value,
			structuralFeature);
		if (itemPropertyDescriptor == null) {
			reportMissingPropertyDescriptor(value, structuralFeature);
			return null;
		}
		final IItemLabelProvider labelProvider = itemPropertyDescriptor.getLabelProvider(rootObject);

		return labelProvider.getImage(element);
	}

	private void reportMissingPropertyDescriptor(final EObject value, final EStructuralFeature structuralFeature) {
		reportService
			.report(new AbstractReport(
				String
					.format(
						"No IItemPropertyDescriptor for feature %2$s in EClass %1$s found.", value.eClass().getName(), //$NON-NLS-1$
						structuralFeature.getName())));
	}
}
