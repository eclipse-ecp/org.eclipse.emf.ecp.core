/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * Implementation of the VTViewTemplateProvider.
 *
 * @author Eugen Neufeld
 *
 */
@Component(name = "viewTemplate", service = VTViewTemplateProvider.class)
public class ViewTemplateProviderImpl implements VTViewTemplateProvider {

	private VTViewTemplate registeredTemplate;

	private ReportService reportService;

	/**
	 * Sets the report service.
	 *
	 * @param reportService the {@link ReportService}
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, unbind = "-")
	public void setReportService(ReportService reportService) {
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
		final IConfigurationElement[] controls = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.emf.ecp.view.template"); //$NON-NLS-1$
		if (controls.length == 0) {
			return;
		}
		if (controls.length > 1) {
			if (ViewModelUtil.isDebugMode()) {
				throw new IllegalArgumentException("Multiple template models found. Only one is supported."); //$NON-NLS-1$
			}
			reportService.report(new AbstractReport(
				"Multiple template models have been found. Only one of them will be used. Fix your project setup.")); //$NON-NLS-1$
		}
		final IConfigurationElement e = controls[0];
		final String xmiResource = e.getAttribute("xmi"); //$NON-NLS-1$
		final Resource resource = loadResource(URI.createURI("platform:/plugin/" + e.getContributor().getName() + "/" //$NON-NLS-1$ //$NON-NLS-2$
			+ xmiResource));
		if (resource == null) {
			return;
		}
		if (resource.getContents().size() != 1) {
			return;
		}
		registeredTemplate = (VTViewTemplate) resource.getContents().get(0);

	}

	private static Resource loadResource(URI uri) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> extensionToFactoryMap = resourceSet
			.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(VTTemplatePackage.eNS_URI,
			VTTemplatePackage.eINSTANCE);
		final Resource resource = resourceSet.createResource(uri);

		try {
			resource.load(null);
		} catch (final IOException exception) {

		}

		int rsSize = resourceSet.getResources().size();
		EcoreUtil.resolveAll(resourceSet);
		while (rsSize != resourceSet.getResources().size()) {
			EcoreUtil.resolveAll(resourceSet);
			rsSize = resourceSet.getResources().size();
		}

		return resource;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider#getViewTemplate()
	 */
	@Override
	public VTViewTemplate getViewTemplate() {
		if (registeredTemplate != null) {
			return registeredTemplate;
		}
		return null;
	}

	/**
	 * Currently only a testing method. Must be revisited if made public.
	 *
	 * @param viewTemplate the {@link VTViewTemplate} to set
	 */
	protected void setViewTemplate(VTViewTemplate viewTemplate) {
		registeredTemplate = viewTemplate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider#getStyleProperties(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public Set<VTStyleProperty> getStyleProperties(VElement vElement, ViewModelContext viewModelContext) {
		if (registeredTemplate == null) {
			return Collections.emptySet();
		}
		final Map<VTStyleProperty, Double> properties = new LinkedHashMap<VTStyleProperty, Double>();

		for (final VTStyle style : registeredTemplate.getStyles()) {
			final double specificity = style.getSelector().isApplicable(vElement, viewModelContext);
			if (VTStyleSelector.NOT_APPLICABLE == specificity) {
				continue;
			}
			for (final VTStyleProperty styleProperty : style.getProperties()) {
				// a new property -> add it
				final VTStyleProperty savedStyleProperty = getSavedStyleProperty(styleProperty, properties.keySet());
				if (savedStyleProperty == null) {
					properties.put(styleProperty, specificity);
					continue;
				}
				// the old property is less specific as the new -> remove it
				if (properties.get(savedStyleProperty) < specificity) {
					properties.remove(savedStyleProperty);
				}
				// add the new property
				properties.put(styleProperty, specificity);
			}
		}

		/* returned properties should by ordered by specificity */
		final Set<VTStyleProperty> treeSet = new TreeSet<VTStyleProperty>(new Comparator<VTStyleProperty>() {
			@Override
			public int compare(VTStyleProperty o1, VTStyleProperty o2) {
				final Double specificity1 = properties.get(o1);
				final Double specificity2 = properties.get(o2);

				/* higher value comes before lower value */
				final int result = specificity2.compareTo(specificity1);
				if (result != 0) {
					return result;
				}
				return o1.toString().compareTo(o2.toString());
			}
		});
		treeSet.addAll(properties.keySet());
		return treeSet;
	}

	private VTStyleProperty getSavedStyleProperty(VTStyleProperty style, Set<VTStyleProperty> properties) {
		if (style == null) {
			return null;
		}
		if (properties == null) {
			return null;
		}
		if (properties.isEmpty()) {
			return null;
		}
		for (final VTStyleProperty styleProperty : properties) {
			if (!styleProperty.getClass().equals(style.getClass())) {
				continue;
			}
			if (styleProperty.equalStyles(style)) {
				return styleProperty;
			}
		}
		return null;
	}

}
