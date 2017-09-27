/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.di;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;

/**
 * The {@link EMFFormsDIRendererFactory} provides renderer instances by creating an instance with help of a suitable
 * {@link EMFFormsDIRendererService}.
 * It then injects the renderer's required services.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDIRendererFactory implements EMFFormsRendererService<VElement> {

	private ReportService reportService;
	private final Set<EMFFormsDIRendererService<VElement>> diRendererServices = //
		new LinkedHashSet<EMFFormsDIRendererService<VElement>>();

	private static final RankingHelper<EMFFormsDIRendererService<VElement>> RANKING_HELPER = //
		new RankingHelper<EMFFormsDIRendererService<VElement>>(
			EMFFormsDIRendererService.class,
			Double.MIN_VALUE,
			EMFFormsRendererService.NOT_APPLICABLE);

	/**
	 * Called by the initializer to add a {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService} to add
	 */
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Returns the {@link ReportService}.
	 *
	 * @return The {@link ReportService}
	 */
	protected ReportService getReportService() {
		return reportService;
	}

	/**
	 * Called by the initializer to add an {@link EMFFormsDIRendererService}.
	 *
	 * @param diRendererService The EMFFormsDIRendererService to add
	 */
	protected void addEMFFormsDIRendererService(EMFFormsDIRendererService<VElement> diRendererService) {
		diRendererServices.add(diRendererService);
	}

	/**
	 * Called by the initializer to remove an {@link EMFFormsDIRendererService}.
	 *
	 * @param diRendererService The EMFFormsDIRendererService to remove
	 */
	protected void removeEMFFormsDIRendererService(EMFFormsDIRendererService<VElement> diRendererService) {
		diRendererServices.remove(diRendererService);
	}

	private EMFFormsDIRendererService<VElement> getHighestRankingRender(
		final VElement vElement, final ViewModelContext viewModelContext) {

		// Use a copy of the registered renderers to avoid exceptions from OSGI
		// which can occur if renderer services are (de)registered during the iteration.
		final Set<EMFFormsDIRendererService<VElement>> rendererServicesCopy = //
			new LinkedHashSet<EMFFormsDIRendererService<VElement>>(diRendererServices);

		return RANKING_HELPER.getHighestRankingElement(
			rendererServicesCopy,
			new RankingHelper.RankTester<EMFFormsDIRendererService<VElement>>() {

				@Override
				public double getRank(final EMFFormsDIRendererService<VElement> renderer) {
					return renderer.isApplicable(vElement, viewModelContext);
				}

			});

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(final VElement vElement, final ViewModelContext viewModelContext) {

		final EMFFormsDIRendererService<VElement> bestRendererService = //
			getHighestRankingRender(vElement, viewModelContext);
		if (bestRendererService != null) {
			return bestRendererService.isApplicable(vElement, viewModelContext);
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 * @since 1.6
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AbstractSWTRenderer<VElement> getRendererInstance(VElement vElement, ViewModelContext viewModelContext) {

		final EMFFormsDIRendererService<VElement> bestRendererService = //
			getHighestRankingRender(vElement, viewModelContext);
		if (bestRendererService == null) {
			return null;
		}

		final EMFFormsContextProvider contextProvider = viewModelContext.getService(EMFFormsContextProvider.class);
		if (contextProvider == null) {
			reportService
				.report(new AbstractReport(
					"The given ViewModelContext does not have a EMFFormsContextProvider. Hence, no renderer instance can be created.")); //$NON-NLS-1$
			return null;
		}
		final IEclipseContext eclipseContext = contextProvider.getContext();
		final IEclipseContext childContext = eclipseContext.createChild();
		childContext.set(ViewModelContext.class, viewModelContext);
		childContext.set(VElement.class, vElement);

		final Class<? extends VElement> elementClass = vElement.getClass();
		final Class<?>[] elementInterfaces = elementClass.getInterfaces();
		for (@SuppressWarnings("rawtypes")
		final Class elementInterface : elementInterfaces) {
			if (VElement.class.isAssignableFrom(elementInterface)) {
				childContext.set(elementInterface, elementInterface.cast(vElement));
			}
		}

		final Class<? extends AbstractSWTRenderer<VElement>> rendererClass = bestRendererService.getRendererClass();
		final AbstractSWTRenderer<VElement> rendererInstance;
		try {
			rendererInstance = ContextInjectionFactory.make(rendererClass, childContext);
		} catch (@SuppressWarnings("restriction") final org.eclipse.e4.core.di.InjectionException ex) {
			reportService.report(new AbstractReport(ex));
			return null;
		}

		return rendererInstance;
	}

}
