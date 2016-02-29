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
package org.eclipse.emfforms.internal.core.services.legacy;

import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;

/**
 * An abstract implementation for the {@link EMFFormsViewServiceFactory}.
 *
 * @param <T> The actual type of the service
 * @author Eugen Neufeld
 *
 */
public abstract class EMFFormsAbstractLegacyServiceFactory<T extends ViewModelService>
	implements EMFFormsViewServiceFactory<T> {

	private final Class<T> type;
	private final double priority;
	private final ReportService reportService;

	/**
	 * Default constructor used to create an {@link EMFFormsLegacyGlobalServiceFactory}.
	 *
	 * @param type The type of the service to wrap
	 * @param priority The priority of the wrapped service
	 * @param reportService The {@link ReportService} to use for logging
	 */
	public EMFFormsAbstractLegacyServiceFactory(Class<T> type, double priority, ReportService reportService) {
		this.type = type;
		this.priority = priority;
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getPriority()
	 */
	@Override
	public double getPriority() {
		return priority;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#getType()
	 */
	// needed as we want to return the interface if it is not to generic
	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		Class<T> typeToCheck = type;
		while (typeToCheck.getInterfaces().length == 0) {
			typeToCheck = (Class<T>) typeToCheck.getSuperclass();
		}
		for (final Class<?> superTypeInterface : typeToCheck.getInterfaces()) {
			// FIXME : what is a nice way to do this?
			if (!GlobalViewModelService.class.equals(superTypeInterface)
				&& !ViewModelService.class.equals(superTypeInterface)
				&& !EMFFormsContextListener.class.equals(superTypeInterface)) {
				return (Class<T>) superTypeInterface;
			}
		}
		return type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory#createService(EMFFormsViewContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T createService(EMFFormsViewContext emfFormsViewContext) {
		try {
			final ViewModelService vms = type.newInstance();
			if (ViewModelContext.class.isInstance(emfFormsViewContext)) {
				vms.instantiate(ViewModelContext.class.cast(emfFormsViewContext));
				emfFormsViewContext.registerEMFFormsContextListener(new EMFFormsContextListener() {

					@Override
					public void contextInitialised() {
						// do nothing
					}

					@Override
					public void contextDispose() {
						vms.dispose();
					}

					@Override
					public void childContextDisposed(EMFFormsViewContext childContext) {
						// do nothing
					}

					@Override
					public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
						// do nothing
					}
				});
			}
			return (T) vms;
		} catch (final InstantiationException ex) {
			reportService.report(new AbstractReport(ex));
		} catch (final IllegalAccessException ex) {
			reportService.report(new AbstractReport(ex));
		} catch (final IllegalArgumentException ex) {
			reportService.report(new AbstractReport(ex));
		} catch (final SecurityException ex) {
			reportService.report(new AbstractReport(ex));
		}
		throw new IllegalStateException("Class could not be instantiated. Please check your log!"); //$NON-NLS-1$
	}

}