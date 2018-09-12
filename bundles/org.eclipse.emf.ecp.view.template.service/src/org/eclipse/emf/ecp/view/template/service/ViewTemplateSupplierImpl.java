/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.spi.view.template.service.ViewTemplateSupplier;
import org.eclipse.emf.ecp.spi.view.template.service.ViewTemplateSupplierUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * EMF Forms's default {@link ViewTemplateSupplier} collecting {@link VTViewTemplate VTViewTemplates} registered to the
 * extension point "<code>org.eclipse.emf.ecp.view.template</code>".
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "ViewTemplateSupplierImpl")
public class ViewTemplateSupplierImpl implements ViewTemplateSupplier {

	private static final String EXTENSION = "org.eclipse.emf.ecp.view.template"; //$NON-NLS-1$

	private Set<VTViewTemplate> registeredTemplates = new LinkedHashSet<VTViewTemplate>();

	private ReportService reportService;

	/**
	 * Sets the report service.
	 *
	 * @param reportService the {@link ReportService}
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, unbind = "-")
	void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Startup method for osgi service.
	 *
	 * @param bundleContext the {@link BundleContext}
	 */
	@Activate
	protected void startup(BundleContext bundleContext) {
		// load from extension
		final IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(EXTENSION);
		if (configurationElements.length == 0) {
			return;
		}

		// Read in all registered view templates
		for (final IConfigurationElement configuration : configurationElements) {
			final String xmiResource = configuration.getAttribute("xmi"); //$NON-NLS-1$

			final URI resourceURI = URI
				.createURI("platform:/plugin/" + configuration.getContributor().getName() + "/" + xmiResource); //$NON-NLS-1$//$NON-NLS-2$
			final VTViewTemplate viewTemplate = ViewTemplateSupplierUtil.loadViewTemplate(resourceURI);

			if (viewTemplate != null) {
				registeredTemplates.add(viewTemplate);
			} else {
				reportService.report(new AbstractReport(MessageFormat.format(
					"The registered resource at location \"{0}\" did not contain a valid VTViewTemplate.", //$NON-NLS-1$
					resourceURI.toPlatformString(true)), IStatus.WARNING));
			}
		}
	}

	@Override
	public Set<VTViewTemplate> getViewTemplates() {
		final LinkedHashSet<VTViewTemplate> result = new LinkedHashSet<VTViewTemplate>();
		for (final VTViewTemplate viewTemplate : registeredTemplates) {
			result.add(EcoreUtil.copy(viewTemplate));
		}
		return result;
	}

	/**
	 * Currently only a testing method. Must be revisited if made public.
	 *
	 * @param viewTemplates the Set of {@link VTViewTemplate VTViewTemplates} to set
	 */
	void setViewTemplates(Set<VTViewTemplate> viewTemplates) {
		registeredTemplates = viewTemplates;
	}

	@Override
	public Map<VTStyleProperty, Double> getStyleProperties(VElement vElement, ViewModelContext viewModelContext) {
		if (registeredTemplates.isEmpty()) {
			return Collections.emptyMap();
		}
		final Map<VTStyleProperty, Double> properties = new LinkedHashMap<VTStyleProperty, Double>();

		for (final VTViewTemplate viewTemplate : registeredTemplates) {
			for (final VTStyle style : viewTemplate.getStyles()) {
				final double specificity = style.getSelector().isApplicable(vElement, viewModelContext);
				if (VTStyleSelector.NOT_APPLICABLE == specificity) {
					continue;
				}
				for (final VTStyleProperty styleProperty : style.getProperties()) {
					if (properties.containsKey(styleProperty) && properties.get(styleProperty) > specificity) {
						continue; // Do not override a higher specificity
					}
					properties.put(styleProperty, specificity);
				}
			}
		}

		return properties;
	}
}
