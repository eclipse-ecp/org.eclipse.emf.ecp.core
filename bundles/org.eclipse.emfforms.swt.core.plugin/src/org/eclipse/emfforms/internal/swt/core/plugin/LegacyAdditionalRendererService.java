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
package org.eclipse.emfforms.internal.swt.core.plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.ECPAdditionalRendererTester;
import org.eclipse.emf.ecp.view.spi.swt.reporting.ECPRendererDescriptionInitFailedReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RendererInitFailedReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractAdditionalSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsAdditionalRendererService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Renderer service which uses the extension point derivates.
 *
 * @author Eugen Neufeld
 *
 */
@Component
public class LegacyAdditionalRendererService implements EMFFormsAdditionalRendererService<VElement> {

	private static final String ADDITIONAL_RENDER_EXTENSION = "org.eclipse.emf.ecp.ui.view.swt.additionalRenderers"; //$NON-NLS-1$
	private ReportService reportService;
	private final Map<ECPAdditionalRendererTester, Class<AbstractAdditionalSWTRenderer<VElement>>> legacyRenderer = new LinkedHashMap<ECPAdditionalRendererTester, Class<AbstractAdditionalSWTRenderer<VElement>>>();

	/**
	 * Called by the initializer to set the {@link ReportService}.
	 *
	 * @param reportService The ReportService to set
	 */
	@Reference
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Activate method of OSGI Component.
	 *
	 * @param bundleContext The {@link BundleContext} to use
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		readAdditionalRenderer(bundleContext);
	}

	private void readAdditionalRenderer(BundleContext bundleContext) {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
			.getExtensionPoint(ADDITIONAL_RENDER_EXTENSION);

		for (final IExtension extension : extensionPoint.getExtensions()) {

			for (final IConfigurationElement configurationElement : extension
				.getConfigurationElements()) {
				try {
					final Class<AbstractAdditionalSWTRenderer<VElement>> renderer = loadClass(configurationElement
						.getContributor().getName(), configurationElement
						.getAttribute("renderer")); //$NON-NLS-1$
					final ECPAdditionalRendererTester tester = (ECPAdditionalRendererTester) configurationElement
						.createExecutableExtension("tester"); //$NON-NLS-1$
					legacyRenderer.put(tester, renderer);
				} catch (final CoreException ex) {
					reportService.report(new ECPRendererDescriptionInitFailedReport(ex));
				} catch (final ClassNotFoundException e) {
					reportService.report(new ECPRendererDescriptionInitFailedReport(e));
				} catch (final InvalidRegistryObjectException e) {
					reportService.report(new ECPRendererDescriptionInitFailedReport(e));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz)
		throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(clazz + bundleName);
		}
		return (Class<T>) bundle.loadClass(clazz);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsAdditionalRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public boolean isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		boolean result = false;
		for (final ECPAdditionalRendererTester tester : legacyRenderer.keySet()) {
			result |= tester.isApplicable(vElement, null);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsAdditionalRendererService#getRendererInstances(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public Collection<AbstractAdditionalSWTRenderer<VElement>> getRendererInstances(VElement vElement,
		ViewModelContext viewModelContext) {
		final Collection<AbstractAdditionalSWTRenderer<VElement>> result = new ArrayList<AbstractAdditionalSWTRenderer<VElement>>();
		for (final Class<AbstractAdditionalSWTRenderer<VElement>> renderer : getFittingRenderer(vElement,
			viewModelContext)) {
			final AbstractAdditionalSWTRenderer<VElement> additionalRenderer = createRenderer(vElement,
				viewModelContext,
				reportService, renderer);
			additionalRenderer.init();
			result.add(additionalRenderer);
		}
		return result;
	}

	private Collection<Class<AbstractAdditionalSWTRenderer<VElement>>> getFittingRenderer(VElement vElement,
		ViewModelContext viewModelContext) {
		final Collection<Class<AbstractAdditionalSWTRenderer<VElement>>> fitting = new ArrayList<Class<AbstractAdditionalSWTRenderer<VElement>>>();

		for (final Entry<ECPAdditionalRendererTester, Class<AbstractAdditionalSWTRenderer<VElement>>> entry : legacyRenderer
			.entrySet()) {
			if (entry.getKey().isApplicable(vElement, viewModelContext)) {
				fitting.add(entry.getValue());
			}
		}
		return fitting;
	}

	private AbstractAdditionalSWTRenderer<VElement> createRenderer(VElement vElement, ViewModelContext viewContext,
		final ReportService reportService,
		final Class<? extends AbstractAdditionalSWTRenderer<VElement>> rendererClass) {
		try {
			return rendererClass
				.getConstructor(vElement.getClass().getInterfaces()[0], ViewModelContext.class)
				.newInstance(vElement, viewContext);
		} catch (final InstantiationException ex) {
			reportService.report(new RendererInitFailedReport(ex));
			throw new IllegalStateException(ex);
		} catch (final IllegalAccessException ex) {
			reportService.report(new RendererInitFailedReport(ex));
			throw new IllegalStateException(ex);
		} catch (final IllegalArgumentException ex) {
			reportService.report(new RendererInitFailedReport(ex));
			throw new IllegalStateException(ex);
		} catch (final InvocationTargetException ex) {
			reportService.report(new RendererInitFailedReport(ex));
			throw new IllegalStateException(ex);
		} catch (final NoSuchMethodException ex) {
			reportService.report(new RendererInitFailedReport(ex));
			throw new IllegalStateException(ex);
		} catch (final SecurityException ex) {
			reportService.report(new RendererInitFailedReport(ex));
			throw new IllegalStateException(ex);
		}

	}
}
