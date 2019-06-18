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
 * Christian W. Damus - bugs 529138, 546974
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.core.services.datatemplate.TemplateLoaderService;
import org.eclipse.emfforms.core.services.datatemplate.TemplateProvider;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.datatemplate.TemplateCollection;
import org.eclipse.emfforms.spi.bazaar.BazaarUtil;
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

	private final Bazaar<TemplateLoaderService> templateLoaderBazaar = BazaarUtil.createBazaar(
		TemplateLoaderService.DEFAULT);

	private final Map<EClass, LinkedHashSet<Template>> templates = new LinkedHashMap<EClass, LinkedHashSet<Template>>();
	private ReportService reportService;
	private IExtensionRegistry extensionRegistry;

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
	 * Sets the extension registry dependency.
	 *
	 * @param extensionRegistry the extension registry
	 */
	@Reference
	void setExtensionRegistry(IExtensionRegistry extensionRegistry) {
		this.extensionRegistry = extensionRegistry;
	}

	/**
	 * Reads the extension point on service activation.
	 */
	@Activate
	void activate() {
		readExtensionPoint();
	}

	/**
	 * Add a template loader service provider.
	 *
	 * @param provider the provider to add
	 *
	 * @since 1.21
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	public void addLoaderServiceProvider(TemplateLoaderService.Provider provider) {
		templateLoaderBazaar.addVendor(provider);
	}

	/**
	 * Remove a template loader service provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeLoaderServiceProvider(TemplateLoaderService.Provider provider) {
		templateLoaderBazaar.removeVendor(provider);
	}

	/**
	 * Reads in the registered templates from the extension point.
	 */
	void readExtensionPoint() {
		final IConfigurationElement[] configurationElements = extensionRegistry
			.getConfigurationElementsFor(EXTENSION_POINT);

		for (final IConfigurationElement configurationElement : configurationElements) {
			try {
				final String contributor = configurationElement.getContributor().getName();
				final String path = configurationElement.getAttribute(FILE_ATTRIBUTE).replaceFirst("^/*", ""); //$NON-NLS-1$//$NON-NLS-2$
				final URI resourceURI = URI.createPlatformPluginURI(
					String.format("%s/%s", contributor, path), true); //$NON-NLS-1$

				final TemplateLoaderService loader = getLoader(resourceURI, contributor);
				final Collection<? extends TemplateCollection> collections = loader.loadTemplates(resourceURI);
				collections.forEach(this::registerTemplateCollection);
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

	private TemplateLoaderService getLoader(URI uri, String contributorID) {
		final BazaarContext context = BazaarContext.Builder.empty()
			.put(URI.class, uri)
			.put(TemplateLoaderService.Provider.CONTRIBUTOR_ID, contributorID)
			.build();
		return templateLoaderBazaar.createProduct(context);
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
