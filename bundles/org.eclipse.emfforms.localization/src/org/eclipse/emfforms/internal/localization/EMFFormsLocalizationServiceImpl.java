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
package org.eclipse.emfforms.internal.localization;

import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.osgi.service.localization.BundleLocalization;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Service Implementation for retrieving translated Strings.
 *
 * @author Eugen Neufeld
 *
 */
@Component(name = "localizationService")
public class EMFFormsLocalizationServiceImpl implements EMFFormsLocalizationService {

	private EMFFormsLocaleProvider localeProvider;
	private BundleLocalization bundleLocalization;
	private ReportService reportService;

	/**
	 * Called by the framework to set the EMFFormsLocaleProvider.
	 *
	 * @param localeProvider The {@link EMFFormsLocaleProvider}
	 */
	@Reference(cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC)
	protected void setEMFFormsLocaleProvider(EMFFormsLocaleProvider localeProvider) {
		this.localeProvider = localeProvider;
	}

	/**
	 * Called by the framework to unset the EMFFormsLocaleProvider.
	 *
	 * @param localeProvider The {@link EMFFormsLocaleProvider}
	 */
	protected void unsetEMFFormsLocaleProvider(EMFFormsLocaleProvider localeProvider) {
		this.localeProvider = null;
	}

	/**
	 * Called by the framework to set the BundleLocalization.
	 *
	 * @param bundleLocalization The {@link BundleLocalization}
	 */
	@Reference
	protected void setBundleLocalization(BundleLocalization bundleLocalization) {
		this.bundleLocalization = bundleLocalization;
	}

	/**
	 * Called by the framework to set the ReportService.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService#getString(java.lang.Class,
	 *      java.lang.String)
	 */
	@Override
	public String getString(Class<?> clazz, String key) {
		return getString(FrameworkUtil.getBundle(clazz), key);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService#getString(org.osgi.framework.Bundle,
	 *      java.lang.String)
	 */
	@Override
	public String getString(Bundle bundle, String key) {
		return getString(bundle, getLocale(), key);
	}

	private String getLocale() {
		if (localeProvider == null) {
			return null;
		}
		final Locale result = localeProvider.getLocale();
		return result.getLanguage();
	}

	private String getString(Bundle bundle, String localeLanguage, String key) {
		final ResourceBundle resourceBundle = bundleLocalization.getLocalization(bundle, localeLanguage);
		if (resourceBundle == null) {
			reportService
				.report(new AbstractReport(
					String
						.format(
							"No ResourceBundle found for Language '%1$s' in Bundle %2$s with Version %3$s.", localeLanguage, bundle.getSymbolicName(), bundle.getVersion().toString()))); //$NON-NLS-1$
			return key;
		}
		if (!resourceBundle.containsKey(key)) {
			reportService
				.report(new AbstractReport(
					String
						.format(
							"The ResourceBundle for Language '%1$s' in Bundle %2$s with Version %3$s doesn't contain the key '%4$s'.", localeLanguage, bundle.getSymbolicName(), bundle.getVersion().toString(), key))); //$NON-NLS-1$
			return key;
		}
		return resourceBundle.getString(key);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService#hasKey(org.osgi.framework.Bundle,
	 *      java.lang.String)
	 */
	@Override
	public boolean hasKey(Bundle bundle, String key) {
		return hasKey(bundle, getLocale(), key);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService#hasKey(java.lang.Class, java.lang.String)
	 */
	@Override
	public boolean hasKey(Class<?> clazz, String key) {
		return hasKey(FrameworkUtil.getBundle(clazz), key);
	}

	private boolean hasKey(Bundle bundle, String localeLanguage, String key) {
		final ResourceBundle resourceBundle = bundleLocalization.getLocalization(bundle, localeLanguage);
		if (resourceBundle == null) {
			reportService
				.report(new AbstractReport(
					String
						.format(
							"No ResourceBundle found for Language '%1$s' in Bundle %2$s with Version %3$s.", localeLanguage, bundle.getSymbolicName(), bundle.getVersion().toString()))); //$NON-NLS-1$
			return false;
		}
		return resourceBundle.containsKey(key);
	}
}
