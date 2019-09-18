/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfforms.spi.common.BundleResolver;
import org.eclipse.emfforms.spi.common.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.sort.NumberAwareStringComparator;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.osgi.framework.Bundle;

/**
 * Retrieves the localized labels for two {@link Enumerator} values and compares them to each other.
 * Enumerators are localized by retrieving their literals' labels from the corresponding edit bundle by using the
 * {@link EMFFormsLocalizationService}. Retrieved edit bundles are cached to avoid re-resolving them for every
 * comparison.
 *
 * @author Lucas Koehler
 * @since 1.22
 *
 */
public class LocalizedEnumeratorComparator implements FeatureAwareComparator<Enumerator> {

	/**
	 * Template to generate the localization key for an enum value. First parameter is the EEnum's type name, second
	 * parameter is the value's name.
	 */
	private static final String ENUM_KEY_TEMPLATE = "_UI_%s_%s_literal"; //$NON-NLS-1$

	// use Optional to avoid trying to resolve absent edit bundles over and over again
	private final Map<EClassifier, Optional<Bundle>> editBundles = new HashMap<>();
	private final EMFFormsLocalizationService localizationService;
	private final BundleResolver bundleResolver;
	private final ReportService reportService;

	/**
	 * Default constructor.
	 *
	 * @param localizationService The {@link EMFFormsLocalizationService} to get the localized literal labels
	 * @param bundleResolver The {@link BundleResolver} to resolve the edit bundles
	 * @param reportService The {@link ReportService} to report missing edit bundles
	 */
	public LocalizedEnumeratorComparator(EMFFormsLocalizationService localizationService, BundleResolver bundleResolver,
		ReportService reportService) {
		this.localizationService = localizationService;
		this.bundleResolver = bundleResolver;
		this.reportService = reportService;
	}

	@Override
	public int compare(EStructuralFeature feature, Enumerator leftValue, Enumerator rightValue) {
		if (leftValue == null) {
			if (rightValue == null) {
				return 0;
			}
			return 1;
		} else if (rightValue == null) {
			return -1;
		}

		final String typeName = feature.getEType().getName();
		final Optional<Bundle> editBundle = editBundles.computeIfAbsent(feature.getEType(), key -> {
			try {
				return Optional.of(bundleResolver.getEditBundle(feature.getEContainingClass()));
			} catch (final NoBundleFoundException ex) {
				reportService
					.report(new AbstractReport(
						MessageFormat.format(
							"No edit bundle was found for EEnum ''{0}''. Hence, its literals cannot be internationalized for feature ''{1}''.", //$NON-NLS-1$
							typeName, feature.getName()),
						IStatus.WARNING));
			}
			return Optional.empty();
		});

		final String leftLabel = editBundle
			.map(eB -> localizationService.getString(eB,
				String.format(ENUM_KEY_TEMPLATE, typeName, leftValue.getName())))
			.orElse(leftValue.getLiteral());

		final String rightLabel = editBundle
			.map(eB -> localizationService.getString(eB,
				String.format(ENUM_KEY_TEMPLATE, typeName, rightValue.getName())))
			.orElse(rightValue.getLiteral());

		return NumberAwareStringComparator.getInstance().compare(leftLabel, rightLabel);
	}
}
