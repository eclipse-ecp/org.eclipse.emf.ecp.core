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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.model.common.ECPStaticRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.reporting.ECPRendererDescriptionInitFailedReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RendererInitFailedReport;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;
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
public class LegacyRendererService implements EMFFormsRendererService<VElement> {

	private static final String TEST_DYNAMIC = "dynamicTest";//$NON-NLS-1$
	private static final String TEST_STATIC = "staticTest";//$NON-NLS-1$
	private static final String TESTER_PRIORITY = "priority";//$NON-NLS-1$
	private static final String TESTER_VELEMENT = "element"; //$NON-NLS-1$
	private static final String RENDERER_TESTER = "testClass"; //$NON-NLS-1$

	private static final String RENDER_EXTENSION = "org.eclipse.emf.ecp.ui.view.swt.renderers"; //$NON-NLS-1$

	private ReportService reportService;

	private final Map<Set<ECPRendererTester>, Class<AbstractSWTRenderer<VElement>>> legacyRenderer = new LinkedHashMap<Set<ECPRendererTester>, Class<AbstractSWTRenderer<VElement>>>();

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
		readRenderer(bundleContext);
	}

	private void readRenderer(BundleContext bundleContext) {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
			.getExtensionPoint(RENDER_EXTENSION);
		for (final IExtension extension : extensionPoint.getExtensions()) {

			for (final IConfigurationElement configurationElement : extension
				.getConfigurationElements()) {
				try {
					final Class<AbstractSWTRenderer<VElement>> renderer = loadClass(configurationElement
						.getContributor().getName(), configurationElement
						.getAttribute("renderer")); //$NON-NLS-1$

					final Set<ECPRendererTester> tester = new LinkedHashSet<ECPRendererTester>();
					for (final IConfigurationElement testerExtension : configurationElement.getChildren()) {
						if (TEST_DYNAMIC.equals(testerExtension.getName())) {
							tester.add((ECPRendererTester) testerExtension.createExecutableExtension(RENDERER_TESTER));
						}
						else if (TEST_STATIC.equals(testerExtension.getName())) {

							final int priority = Integer.parseInt(testerExtension.getAttribute(TESTER_PRIORITY));

							final String vElement = testerExtension.getAttribute(TESTER_VELEMENT);
							final Class<? extends VElement> supportedEObject = loadClass(testerExtension
								.getContributor()
								.getName(), vElement);

							tester.add(new ECPStaticRendererTester(priority,
								supportedEObject));
						}
					}
					legacyRenderer.put(tester, renderer);
				} catch (final CoreException ex) {
					reportService.report(new ECPRendererDescriptionInitFailedReport(ex));
				} catch (final ClassNotFoundException ex) {
					reportService.report(new ECPRendererDescriptionInitFailedReport(ex));
				} catch (final InvalidRegistryObjectException ex) {
					reportService.report(new ECPRendererDescriptionInitFailedReport(ex));
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
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(VElement,ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		int currentPriority = -1;
		for (final Set<ECPRendererTester> testerSet : legacyRenderer.keySet()) {
			for (final ECPRendererTester tester : testerSet) {
				final int testerPriority = tester.isApplicable(vElement, viewModelContext);
				if (testerPriority > currentPriority) {
					currentPriority = testerPriority;
				}
			}
		}
		return currentPriority;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#getRendererInstance(VElement, ViewModelContext)
	 */
	@Override
	public AbstractSWTRenderer<VElement> getRendererInstance(VElement vElement, ViewModelContext viewModelContext) {
		return createRenderer(vElement, viewModelContext, getFittingRenderer(vElement, viewModelContext));
	}

	private Class<AbstractSWTRenderer<VElement>> getFittingRenderer(VElement vElement,
		ViewModelContext viewModelContext) {
		int currentPriority = -1;
		Class<AbstractSWTRenderer<VElement>> best = null;
		for (final Entry<Set<ECPRendererTester>, Class<AbstractSWTRenderer<VElement>>> testerSet : legacyRenderer
			.entrySet()) {
			for (final ECPRendererTester tester : testerSet.getKey()) {
				final int testerPriority = tester.isApplicable(vElement, viewModelContext);
				if (testerPriority > currentPriority) {
					currentPriority = testerPriority;
					best = testerSet.getValue();
				}
			}
		}
		return best;
	}

	private AbstractSWTRenderer<VElement> createRenderer(VElement vElement, ViewModelContext viewContext,
		final Class<? extends AbstractSWTRenderer<VElement>> rendererClass) {
		if (rendererClass == null) {
			reportService.report(new AbstractReport(String
				.format("RendererClass for %1$s is null!", vElement.getName()))); //$NON-NLS-1$
			throw new IllegalStateException(String.format("RendererClass for %1$s is null!", vElement.getName())); //$NON-NLS-1$
		}
		try {
			return rendererClass
				.getConstructor(vElement.getClass().getInterfaces()[0], ViewModelContext.class, ReportService.class)
				.newInstance(vElement, viewContext, reportService);
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
