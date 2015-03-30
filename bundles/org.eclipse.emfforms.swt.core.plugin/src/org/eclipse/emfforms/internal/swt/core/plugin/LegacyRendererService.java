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
import java.util.Set;

import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RendererInitFailedReport;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;

/**
 * Renderer service which uses the extension point derivates.
 *
 * @author Eugen Neufeld
 *
 */
public class LegacyRendererService implements EMFFormsRendererService<VElement> {

	private final Class<AbstractSWTRenderer<VElement>> renderer;
	private final Set<ECPRendererTester> testerSet;
	private final ReportService reportService;

	/**
	 * Default constructor.
	 *
	 * @param renderer The AbstractSWTRenderer class
	 * @param testerSet The Set of ECPRendererTester
	 * @param reportService The ReportService
	 */
	public LegacyRendererService(Class<AbstractSWTRenderer<VElement>> renderer, Set<ECPRendererTester> testerSet,
		ReportService reportService) {
		this.renderer = renderer;
		this.testerSet = testerSet;
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		int currentPriority = -1;
		for (final ECPRendererTester tester : testerSet) {
			final int testerPriority = tester.isApplicable(vElement, viewModelContext);
			if (testerPriority > currentPriority) {
				currentPriority = testerPriority;
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
		return createRenderer(vElement, viewModelContext, renderer);
	}

	private AbstractSWTRenderer<VElement> createRenderer(VElement vElement, ViewModelContext viewContext,
		final Class<? extends AbstractSWTRenderer<VElement>> rendererClass) {
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
