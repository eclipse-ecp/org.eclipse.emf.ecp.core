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
 * Christian W. Damus - bug 529138
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emfforms.core.services.datatemplate.TemplateProvider;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.datatemplate.TemplateCollection;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Template provider that reads in templates from an extension point.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "XmiTemplateProvider")
public class XmiTemplateProvider implements TemplateProvider {

	private static final String FILE_ATTRIBUTE = "file"; //$NON-NLS-1$
	private static final String EXTENSION_POINT = "org.eclipse.emfforms.core.services.datatemplate.xmi"; //$NON-NLS-1$

	private final Map<EClass, LinkedHashSet<Template>> templates = new LinkedHashMap<EClass, LinkedHashSet<Template>>();
	private ReportService reportService;

	/**
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService} to set
	 */
	@Reference
	void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Reads the extension point on service activation.
	 */
	@Activate
	void activate() {
		readExtensionPoint();
	}

	/**
	 * Reads in the registered templates from the extension point.
	 */
	void readExtensionPoint() {
		final IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(EXTENSION_POINT);

		for (final IConfigurationElement configurationElement : configurationElements) {
			try {
				final URL resourceURL = Platform.getBundle(configurationElement.getContributor().getName())
					.getResource(configurationElement.getAttribute(FILE_ATTRIBUTE));
				final ResourceSet resourceSet = new ResourceSetImpl();
				// resourceSet.getLoadOptions().putAll(LOAD_OPTIONS);
				final Resource resource = resourceSet.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
				final InputStream inputStream = resourceURL.openStream();
				try {
					resource.load(inputStream, null);
					final TemplateCollection templateCollection = (TemplateCollection) resource.getContents().get(0);
					registerTemplateCollection(templateCollection);
				} finally {
					inputStream.close();
				}
			} catch (final IOException ex) {
				reportService.report(new AbstractReport(ex, "An Exception occured while reading in a data template.")); //$NON-NLS-1$
			}
		}
	}

	@Override
	public boolean canProvideTemplates(EObject owner, EReference reference) {
		final EClass referenceType = reference.getEReferenceType();

		// if we have a key for the given template we surely have at least one template
		if (templates.containsKey(referenceType)) {
			return true;
		}

		// otherwise we have to analyze all keys and see if our type is a super type (of at least one)
		for (final EClass type : templates.keySet()) {
			if (referenceType.isSuperTypeOf(type)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Template> provideTemplates(EObject owner, EReference reference) {
		final Set<Template> matchingTemplates = new LinkedHashSet<Template>();
		final EClass referenceType = reference.getEReferenceType();

		for (final EClass type : templates.keySet()) {
			if (!referenceType.isSuperTypeOf(type)) {
				continue;
			}
			matchingTemplates.addAll(templates.get(type));
		}
		return matchingTemplates;
	}

	private void addToTemplateMap(EClass type, Template template) {
		if (!templates.containsKey(type)) {
			templates.put(type, new LinkedHashSet<Template>());
		}
		templates.get(type).add(template);
	}

	private void registerTemplateCollection(TemplateCollection collection) {
		for (final Template template : collection.getTemplates()) {
			registerTemplate(template);
		}
	}

	/**
	 * Register a template for its instance's type.
	 *
	 * @param template The template
	 */
	void registerTemplate(Template template) {
		if (template.getInstance() == null) {
			reportService.report(new AbstractReport(
				"Ignoring template '{0}', as it does not contain a valid EObject instance", template.getName())); //$NON-NLS-1$
			return;
		}

		final EClass type = template.getInstance().eClass();
		addToTemplateMap(type, template);
	}
}
