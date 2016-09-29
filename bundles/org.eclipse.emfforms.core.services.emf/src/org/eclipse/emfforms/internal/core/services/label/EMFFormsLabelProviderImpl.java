/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

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
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.osgi.framework.Bundle;

/**
 * Implementation of {@link EMFFormsLabelProvider}. It provides a label service that delivers the display name and
 * description for a domain model reference and optionally an EObject.
 *
 * @author Eugen Neufeld
 *
 */
public class EMFFormsLabelProviderImpl implements EMFFormsLabelProvider, EMFFormsLocaleChangeListener {

	/**
	 * Key for the map of description observables.
	 *
	 * @author Eugen Neufeld
	 */
	private static class DescriptionKey {
		private final String eClassName;
		private final String featureName;
		private final Bundle bundle;

		DescriptionKey(String eClassName, String featureName, Bundle bundle) {
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (bundle == null ? 0 : bundle.hashCode());
			result = prime * result + (eClassName == null ? 0 : eClassName.hashCode());
			result = prime * result + (featureName == null ? 0 : featureName.hashCode());
			return result;
		}

		// BEGIN COMPLEX CODE
		// path complexity check does not take returns into account
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DescriptionKey)) {
				return false;
			}
			final DescriptionKey other = (DescriptionKey) obj;
			if (bundle == null) {
				if (other.bundle != null) {
					return false;
				}
			} else if (!bundle.equals(other.bundle)) {
				return false;
			}
			if (eClassName == null) {
				if (other.eClassName != null) {
					return false;
				}
			} else if (!eClassName.equals(other.eClassName)) {
				return false;
			}
			if (featureName == null) {
				if (other.featureName != null) {
					return false;
				}
			} else if (!featureName.equals(other.featureName)) {
				return false;
			}
			return true;
		}
		// END COMPLEX CODE
	}

	private static final String DISPLAY_NAME = "_UI_%1$s_%2$s_feature"; //$NON-NLS-1$
	private static final String DESCRIPTION = "_UI_%1$s_%2$s_description"; //$NON-NLS-1$
	private static final String DESCRIPTION_COMPOSITE = "_UI_PropertyDescriptor_description"; //$NON-NLS-1$
	private static final String TYPE = "_UI_%1$s_type"; //$NON-NLS-1$

	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private EMFFormsLocalizationService localizationService;
	private ReportService reportService;
	private BundleResolver bundleResolver = new BundleResolverImpl();

	private final Map<WritableValue, BundleKeyWrapper> displayKeyObservableMap = new WeakHashMap<WritableValue, BundleKeyWrapper>();
	private final Map<WritableValue, DescriptionKey> descriptionKeyObservableMap = new WeakHashMap<WritableValue, DescriptionKey>();
	private EMFFormsLocaleProvider localeProvider;
	private EMFFormsLabelProviderDefaultImpl labelProviderDefault;

	/**
	 * Sets the {@link ReportService} service.
	 *
	 * @param reportService The ReportService service.
	 */
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Sets the {@link EMFFormsDatabindingEMF} service.
	 *
	 * @param emfFormsDatabinding The databinding service.
	 */
	protected void setEMFFormsDatabinding(EMFFormsDatabindingEMF emfFormsDatabinding) {
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
	 * Sets the default {@link EMFFormsLabelProviderDefaultImpl}.
	 *
	 * @param labelProviderDefault the labelProviderDefault to set
	 */
	protected void setLabelProviderDefault(EMFFormsLabelProviderDefaultImpl labelProviderDefault) {
		this.labelProviderDefault = labelProviderDefault;
	}

	private BundleKeyResultWrapper getDisplayBundleKeyResultWrapper(EStructuralFeature structuralFeature)
		throws NoBundleFoundException {
		final EClass eContainingClass = structuralFeature.getEContainingClass();
		final Bundle bundle = bundleResolver.getEditBundle(eContainingClass);
		final String key = String.format(DISPLAY_NAME, eContainingClass.getName(),
			structuralFeature.getName());
		final String displayName = getDisplayName(bundle, key);
		return new BundleKeyResultWrapper(new BundleKeyWrapper(key, bundle), displayName);
	}

	/**
	 * Returns the display name of the {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature}
	 * @return The localized feature name
	 */
	public String getDisplayName(EStructuralFeature structuralFeature) {
		try {
			return getDisplayBundleKeyResultWrapper(structuralFeature).getResult();
		} catch (final NoBundleFoundException ex) {
			return labelProviderDefault.getDisplayName(structuralFeature);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFFormsLabelProvider#getDisplayName(VDomainModelReference)
	 */
	@Override
	public IObservableValue getDisplayName(VDomainModelReference domainModelReference, EClass rootEClass)
		throws NoLabelFoundException {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootEClass).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootEClass);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			throw new NoLabelFoundException(ex);
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		BundleKeyResultWrapper bundleKeyResultWrapper;
		try {
			bundleKeyResultWrapper = getDisplayBundleKeyResultWrapper(structuralFeature);
		} catch (final NoBundleFoundException ex) {
			return labelProviderDefault.getDisplayName(domainModelReference, rootEClass);
		}

		final WritableValue value = getObservableValue(bundleKeyResultWrapper.getResult());
		displayKeyObservableMap.put(value, bundleKeyResultWrapper.getBundleKeyWrapper());
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

		BundleKeyResultWrapper displayBundleKeyResultWrapper;
		try {
			displayBundleKeyResultWrapper = getDisplayBundleKeyResultWrapper(structuralFeature);
		} catch (final NoBundleFoundException ex) {
			return labelProviderDefault.getDisplayName(domainModelReference, rootObject);
		}

		final WritableValue displayObserveValue = getObservableValue(displayBundleKeyResultWrapper.getResult());
		displayKeyObservableMap.put(displayObserveValue, displayBundleKeyResultWrapper.getBundleKeyWrapper());
		return displayObserveValue;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFFormsLabelProvider#getDescription(VDomainModelReference)
	 */
	@Override
	public IObservableValue getDescription(VDomainModelReference domainModelReference, EClass rootEClass)
		throws NoLabelFoundException {
		Assert.create(domainModelReference).notNull();
		Assert.create(rootEClass).notNull();

		IValueProperty valueProperty;
		try {
			valueProperty = emfFormsDatabinding.getValueProperty(domainModelReference, rootEClass);
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
			return labelProviderDefault.getDescription(domainModelReference, rootEClass);
		}
		final WritableValue writableValue = getObservableValue(getDescription(eContainingClass
			.getName(),
			structuralFeature.getName(), bundle));
		descriptionKeyObservableMap.put(writableValue, new DescriptionKey(eContainingClass.getName(),
			structuralFeature.getName(), bundle));
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
			return labelProviderDefault.getDescription(domainModelReference, rootObject);
		}
		final WritableValue writableValue = getObservableValue(getDescription(structuralFeature.getEContainingClass()
			.getName(),
			structuralFeature.getName(), bundle));
		descriptionKeyObservableMap.put(writableValue,
			new DescriptionKey(structuralFeature.getEContainingClass().getName(),
				structuralFeature.getName(), bundle));
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
		for (final Entry<WritableValue, BundleKeyWrapper> entry : displayKeyObservableMap.entrySet()) {
			final BundleKeyWrapper displayNameKey = entry.getValue();
			final WritableValue writableValue = entry.getKey();
			writableValue.setValue(getDisplayName(displayNameKey.getBundle(), displayNameKey.getKey()));
		}
		for (final Entry<WritableValue, DescriptionKey> entry : descriptionKeyObservableMap.entrySet()) {
			final DescriptionKey descriptionKey = entry.getValue();
			final WritableValue writableValue = entry.getKey();
			writableValue.setValue(getDescription(descriptionKey.geteClassName(), descriptionKey.getFeatureName(),
				descriptionKey.getBundle()));
		}
	}
}
