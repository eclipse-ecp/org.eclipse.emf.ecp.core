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
package org.eclipse.emfforms.internal.swt.core.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.ui.SWTValidationHelper;
import org.eclipse.emfforms.spi.swt.core.ui.SWTValidationUiService;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.osgi.service.component.annotations.Component;

/**
 * Default implementation of the {@link SWTValidationUiService} which delegates to the {@link SWTValidationHelper} to
 * get the validation icons and colors.
 *
 * @author Lucas Koehler
 *
 */
@Component
public class SWTValidationUiServiceImpl implements SWTValidationUiService {

	private final Map<Integer, Color> severityBackgroundColorMap = new HashMap<Integer, Color>();
	private final Map<Integer, Color> severityForegroundColorMap = new HashMap<Integer, Color>();
	private final Map<Integer, Image> severityIconMap = new HashMap<Integer, Image>();
	private SWTValidationHelper validationHelper = SWTValidationHelper.INSTANCE;

	/** Default constructor. */
	public SWTValidationUiServiceImpl() {
		// Nothing to do here
	}

	/**
	 * Test constructor that allows specifying a custom {@link SWTValidationHelper}.
	 *
	 * @param validationHelper The custom {@link SWTValidationHelper}
	 */
	SWTValidationUiServiceImpl(SWTValidationHelper validationHelper) {
		this.validationHelper = validationHelper;
	}

	@Override
	public Image getValidationIcon(Diagnostic diagnostic, VElement vElement, ViewModelContext viewModelContext) {
		final int severity = severity(diagnostic);
		if (!severityIconMap.containsKey(severity)) {
			final Image validationIcon = validationHelper.getValidationIcon(severity, vElement, viewModelContext);
			severityIconMap.put(severity, validationIcon);
		}
		return severityIconMap.get(severity);
	}

	@Override
	public Image getValidationIcon(VElement vElement, ViewModelContext viewModelContext) {
		return getValidationIcon(highestSeverityDiagnostic(vElement), vElement, viewModelContext);
	}

	@Override
	public Color getValidationForegroundColor(Diagnostic diagnostic, VElement vElement,
		ViewModelContext viewModelContext) {
		final int severity = severity(diagnostic);
		if (!severityForegroundColorMap.containsKey(severity)) {
			final Color validationForegroundColor = validationHelper.getValidationForegroundColor(severity, vElement,
				viewModelContext);
			severityForegroundColorMap.put(severity, validationForegroundColor);
		}
		return severityForegroundColorMap.get(severity);
	}

	@Override
	public Color getValidationForegroundColor(VElement vElement, ViewModelContext viewModelContext) {
		return getValidationForegroundColor(highestSeverityDiagnostic(vElement), vElement, viewModelContext);
	}

	@Override
	public Color getValidationBackgroundColor(Diagnostic diagnostic, VElement vElement,
		ViewModelContext viewModelContext) {
		final int severity = severity(diagnostic);
		if (!severityBackgroundColorMap.containsKey(severity)) {
			final Color validationBackgroundColor = validationHelper.getValidationBackgroundColor(severity, vElement,
				viewModelContext);
			severityBackgroundColorMap.put(severity, validationBackgroundColor);
		}
		return severityBackgroundColorMap.get(severity);
	}

	@Override
	public Color getValidationBackgroundColor(VElement vElement, ViewModelContext viewModelContext) {
		return getValidationBackgroundColor(highestSeverityDiagnostic(vElement), vElement, viewModelContext);
	}

	private static Diagnostic highestSeverityDiagnostic(VElement element) {
		Diagnostic mostSevere = Diagnostic.OK_INSTANCE;
		final VDiagnostic vDiagnostic = element.getDiagnostic();
		if (vDiagnostic != null && vDiagnostic.getDiagnostics().size() > 0) {
			for (final Object o : vDiagnostic.getDiagnostics()) {
				final Diagnostic diagnostic = (Diagnostic) o;
				mostSevere = mostSevere.getSeverity() >= diagnostic.getSeverity() ? mostSevere : diagnostic;
			}
		}
		return mostSevere;
	}

	/** Wrap getting a Diagnostic's severity to make the call null-safe. */
	private static int severity(Diagnostic diagnostic) {
		// If there is no diagnostic, we assume everything is ok.
		return diagnostic != null ? diagnostic.getSeverity() : Diagnostic.OK;
	}
}
