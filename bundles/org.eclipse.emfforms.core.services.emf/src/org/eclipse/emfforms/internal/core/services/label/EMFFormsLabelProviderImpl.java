/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.label;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of {@link EMFFormsLabelProvider}. It provides a label service that delivers the display name and
 * description for a domain model reference and optionally an EObject.
 *
 * @author Eugen Neufeld
 *
 */
public class EMFFormsLabelProviderImpl implements EMFFormsLabelProvider, EMFFormsLocaleChangeListener {

	/**
	 * Key for the map of displayname observables.
	 *
	 * @author Eugen Neufeld
	 */
	private static class DisplayNameKey {
		private final String key;
		private final Bundle bundle;

		public DisplayNameKey(String key, Bundle bundle) {
			super();
			this.key = key;
			this.bundle = bundle;
		}

		String getKey() {
			return key;
		}

		Bundle getBundle() {
			return bundle;
		}
	}

	/**
	 * Key for the map of description observables.
	 *
	 * @author Eugen Neufeld
	 */
	private static class DescriptionKey {
		private final String eClassName;
		private final String featureName;
		private final Bundle bundle;

		public DescriptionKey(String eClassName, String featureName, Bundle bundle) {
			super();
			this.eClassName = eClassName;
			this.featureName = featureName;
			this.bundle = bundle;
		}

		Bundle getBundle() {
			return bundle;
		}

		String geteClassName() {
			return eClassName;
		}

		String getFeatureName() {
			return featureName;
		}
	}

	private static final String DISPLAY_NAME = "_UI_%1$s_%2$s_feature"; //$NON-NLS-1$
	private static final String DESCRIPTION = "_UI_%1$s_%2$s_description"; //$NON-NLS-1$
	private static final String DESCRIPTION_COMPOSITE = "_UI_PropertyDescriptor_description"; //$NON-NLS-1$
	private static final String TYPE = "_UI_%1$s_type"; //$NON-NLS-1$

	private EMFFormsDatabinding emfFormsDatabinding;
	private EMFFormsLocalizationService localizationService;
	private ReportService reportService;
	private BundleResolver bundleResolver = new BundleResolverImpl();

	private final Map<DisplayNameKey, WritableValue> displayKeyObservableMap = new LinkedHashMap<DisplayNameKey, WritableValue>();
	private final Map<DescriptionKey, WritableValue> descriptionKeyObservableMap = new LinkedHashMap<DescriptionKey, WritableValue>();
	private EMFFormsLocaleProvider localeProvider;
	private ServiceReference<EMFFormsLabelProvider> defaultLabelProviderReference;
	private EMFFormsLabelProvider labelProviderDefault;

	/**
	 * Sets the {@link ReportService} service.
	 *
	 * @param reportService The ReportService service.
	 */
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
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
	 * Sets the {@link EMFFormsLocalizationService}.
	 *
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 */
	protected void setEMFFormsLocalizationService(EMFFormsLocalizationService localizationService) {
		this.localizationService = localizationService;
	}

	/**
	 * Sets the {@link EMFFormsLocaleProvider}.
	 *
	 * @param localeProvider The {@link EMFFormsLocaleProvider}
	 */
	protected void setEMFFormsLocaleProvider(EMFFormsLocaleProvider localeProvider) {
		this.localeProvider = localeProvider;
		this.localeProvider.addEMFFormsLocaleChangeListener(this);
	}

	/**
	 * Sets the {@link BundleResolver}.
	 *
	 * @param bundleResolver The {@link BundleResolver}
	 */
	protected void setBundleResolver(BundleResolver bundleResolver) {
		this.bundleResolver = bundleResolver;
	}

	/**
	 * Called by the Framework during startup to retrieve a fallback label provider.
	 *
	 * @param bundleContext The {@link BundleContext} to use
	 * @throws InvalidSyntaxException thrown if the filter string is incorrect
	 */
	protected void activate(BundleContext bundleContext) throws InvalidSyntaxException {
		defaultLabelProviderReference = bundleContext
			.getServiceReferences(EMFFormsLabelProvider.class, "(service.ranking=1)").iterator().next(); //$NON-NLS-1$
		labelProviderDefault = bundleContext.getService(defaultLabelProviderReference);
	}

	/**
	 * Called by the framework during tear down. Ungets the fallback label provider.
	 *
	 * @param bundleContext The {@link BundleContext} to use
	 */
	protected void deactivate(BundleContext bundleContext) {
		bundleContext.ungetService(defaultLabelProviderReference);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFFormsLabelProvider#getDisplayName(VDomainModelReference)
	 */
	@Override
	public IObservableValue getDisplayName(VDomainModelReference domainModelReference) throws NoLabelFoundException {
		Assert.create(domainModelReference).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, null);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			throw new NoLabelFoundException(ex);
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		Bundle bundle;
		try {
			bundle = bundleResolver.getEditBundle(eContainingClass);
		} catch (final NoBundleFoundException ex) {
			reportService.report(new AbstractReport(ex, 1));
			reportService.report(new AbstractReport("Using fallback", 1)); //$NON-NLS-1$
			return labelProviderDefault.getDisplayName(domainModelReference);
		}
		final String key = String.format(DISPLAY_NAME, eContainingClass.getName(),
			structuralFeature.getName());
		final WritableValue value = getObservableValue(getDisplayName(bundle, key));
		displayKeyObservableMap.put(new DisplayNameKey(key, bundle), value);
		return value;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFFormsLabelProvider#getDisplayName(VDomainModelReference,EObject)
	 */
	@Override
	public IObservableValue getDisplayName(VDomainModelReference domainModelReference, EObject rootObject)
		throws NoLabelFoundException {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			throw new NoLabelFoundException(ex);
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();

		Bundle bundle;
		try {
			bundle = bundleResolver.getEditBundle(structuralFeature.getEContainingClass());
		} catch (final NoBundleFoundException ex) {
			reportService.report(new AbstractReport(ex, 1));
			reportService.report(new AbstractReport("Using fallback", 1)); //$NON-NLS-1$
			return labelProviderDefault.getDisplayName(domainModelReference, rootObject);
		}
		final String key = String.format(DISPLAY_NAME, structuralFeature.getEContainingClass().getName(),
			structuralFeature.getName());
		final WritableValue displayObserveValue = getObservableValue(getDisplayName(bundle, key));
		displayKeyObservableMap.put(new DisplayNameKey(key, bundle), displayObserveValue);
		return displayObserveValue;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFFormsLabelProvider#getDescription(VDomainModelReference)
	 */
	@Override
	public IObservableValue getDescription(VDomainModelReference domainModelReference) throws NoLabelFoundException {
		Assert.create(domainModelReference).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, null);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			throw new NoLabelFoundException(ex);
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		Bundle bundle;
		try {
			bundle = bundleResolver.getEditBundle(eContainingClass);
		} catch (final NoBundleFoundException ex) {
			reportService.report(new AbstractReport(ex, 1));
			reportService.report(new AbstractReport("Using fallback", 1)); //$NON-NLS-1$
			return labelProviderDefault.getDescription(domainModelReference);
		}
		final WritableValue writableValue = getObservableValue(getDescription(eContainingClass
			.getName(),
			structuralFeature.getName(), bundle));
		descriptionKeyObservableMap.put(new DescriptionKey(eContainingClass.getName(),
			structuralFeature.getName(), bundle), writableValue);
		return writableValue;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFFormsLabelProvider#getDescription(VDomainModelReference,EObject)
	 */
	@Override
	public IObservableValue getDescription(VDomainModelReference domainModelReference, EObject rootObject)
		throws NoLabelFoundException {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootObject).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			throw new NoLabelFoundException(ex);
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		Bundle bundle;
		try {
			bundle = bundleResolver.getEditBundle(structuralFeature.getEContainingClass());
		} catch (final NoBundleFoundException ex) {
			reportService.report(new AbstractReport(ex, 1));
			reportService.report(new AbstractReport("Using fallback", 1)); //$NON-NLS-1$
			return labelProviderDefault.getDescription(domainModelReference, rootObject);
		}
		final WritableValue writableValue = getObservableValue(getDescription(structuralFeature.getEContainingClass()
			.getName(),
			structuralFeature.getName(), bundle));
		descriptionKeyObservableMap.put(new DescriptionKey(structuralFeature.getEContainingClass().getName(),
			structuralFeature.getName(), bundle), writableValue);
		return writableValue;
	}

	private WritableValue getObservableValue(String value) {
		return new WritableValue(value, String.class);
	}

	private String getDisplayName(Bundle bundle, String key) {
		return localizationService.getString(bundle, key);
	}

	private String getDescription(String eClassName, String featureName, Bundle bundle) {
		final String keyDefault = String.format(DESCRIPTION, eClassName, featureName);
		if (localizationService.hasKey(bundle, keyDefault)) {
			return localizationService.getString(bundle,
				keyDefault);
		}
		final String descriptionWithSubstitution = localizationService.getString(bundle,
			DESCRIPTION_COMPOSITE);
		final String key = String.format(DISPLAY_NAME, eClassName, featureName);
		final String featureSubstitution = getDisplayName(bundle, key);
		final String eObjectSubstitution = localizationService.getString(bundle,
			String.format(TYPE, eClassName));
		return MessageFormat.format(descriptionWithSubstitution, featureSubstitution, eObjectSubstitution);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener#notifyLocaleChange()
	 */
	@Override
	public void notifyLocaleChange() {
		for (final DisplayNameKey displayNameKey : displayKeyObservableMap.keySet()) {
			final WritableValue writableValue = displayKeyObservableMap.get(displayNameKey);
			writableValue.setValue(getDisplayName(displayNameKey.getBundle(), displayNameKey.getKey()));
		}
		for (final DescriptionKey descriptionKey : descriptionKeyObservableMap.keySet()) {
			final WritableValue writableValue = descriptionKeyObservableMap.get(descriptionKey);
			writableValue.setValue(getDescription(descriptionKey.geteClassName(), descriptionKey.getFeatureName(),
				descriptionKey.getBundle()));
		}
	}
}
