/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.template.service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.spi.view.template.service.ViewTemplateSupplier;
import org.eclipse.emf.ecp.spi.view.template.service.ViewTemplateSupplierUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.RuntimeModeUtil;
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
	private EMFFormsSegmentGenerator segmentGenerator;

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
	 * Sets the segment generator.
	 *
	 * @param segmentGenerator The {@link EMFFormsSegmentGenerator}
	 */
	@Reference(unbind = "-")
	void setEMFFormsSegmentGenerator(EMFFormsSegmentGenerator segmentGenerator) {
		this.segmentGenerator = segmentGenerator;
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
				// If segment generation is enabled, generate segments for all dmr selectors' legacy dmrs
				if (RuntimeModeUtil.isSegmentMode()) {
					generateSegments(viewTemplate);
				}
				registeredTemplates.add(viewTemplate);
			} else {
				reportService.report(new AbstractReport(MessageFormat.format(
					"The registered resource at location \"{0}\" did not contain a valid VTViewTemplate.", //$NON-NLS-1$
					resourceURI.toPlatformString(true)), IStatus.WARNING));
			}
		}
	}

	/**
	 * Generate segments for all DMRs of domain model reference selectors and set the corresponding EClasses to the
	 * selectors.
	 *
	 * @param template The view template containing the DMR selectors
	 */
	protected void generateSegments(VTViewTemplate template) {
		final List<VTDomainModelReferenceSelector> dmrSelectors = template.getStyles().stream()
			.map(VTStyle::getSelector)
			.filter(VTDomainModelReferenceSelector.class::isInstance)
			.map(VTDomainModelReferenceSelector.class::cast)
			.collect(Collectors.toList());

		for (final VTDomainModelReferenceSelector dmrSelector : dmrSelectors) {
			final VDomainModelReference dmr = dmrSelector.getDomainModelReference();

			// If the dmr isn't a feature dmr, we cannot determine the root EClass
			// If the dmr already contains segments, there isn't anything to do
			if (!(dmr instanceof VFeaturePathDomainModelReference) || !dmr.getSegments().isEmpty()) {
				continue;
			}
			final List<VDomainModelReferenceSegment> segments = segmentGenerator.generateSegments(dmr);
			if (segments.isEmpty()) {
				continue;
			}
			dmr.getSegments().addAll(segments);

			// determine and set root EClass
			final VFeaturePathDomainModelReference featureDmr = (VFeaturePathDomainModelReference) dmr;
			final EClass rootEClass = featureDmr.getDomainModelEReferencePath().isEmpty()
				? featureDmr.getDomainModelEFeature().getEContainingClass()
				: featureDmr.getDomainModelEReferencePath().get(0).getEContainingClass();
			dmrSelector.setRootEClass(rootEClass);
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
