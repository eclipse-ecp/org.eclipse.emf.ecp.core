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
package org.eclipse.emfforms.internal.view.model.localization;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.LocalizationAdapter;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * LocalizationViewModelService which will localize the view model.
 *
 * @author Eugen Neufeld
 *
 */
public class LocalizationViewModelService implements ViewModelService, EMFFormsLocaleChangeListener {

	private EMFFormsLocaleProvider localeProvider;
	private ServiceReference<EMFFormsLocaleProvider> localeServiceReference;
	private BundleContext bundleContext;
	private VElement view;
	private ReportService reportService;
	private ServiceReference<ReportService> reportServiceReference;

	/**
	 * Default constructor.
	 */
	public LocalizationViewModelService() {
		super();
		final Bundle bundle = FrameworkUtil.getBundle(getClass());
		if (bundle == null) {
			return;
		}
		bundleContext = bundle.getBundleContext();
		if (bundleContext == null) {
			return;
		}
		localeServiceReference = bundleContext.getServiceReference(EMFFormsLocaleProvider.class);
		if (localeServiceReference == null) {
			return;
		}
		localeProvider = bundleContext.getService(localeServiceReference);

		reportServiceReference = bundleContext.getServiceReference(ReportService.class);
		reportService = bundleContext.getService(reportServiceReference);

		localeProvider.addEMFFormsLocaleChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		view = context.getViewModel();
		final LocalizationAdapter adapter = getLocalizationAdapter(view);
		localizeView(adapter, view);

		context.registerViewChangeListener(new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getRawNotification().getFeature() == VViewPackage.eINSTANCE.getElement_Name()) {
					localize(adapter, (VElement) notification.getNotifier());
				} else if (notification.getRawNotification().getEventType() == Notification.ADD
					|| notification.getRawNotification().getEventType() == Notification.ADD_MANY) {
					for (final EObject eObject : notification.getNewEObjects()) {
						if (VElement.class.isInstance(eObject)) {
							localize(adapter, VElement.class.cast(eObject));
							checkContents(adapter, VElement.class.cast(eObject));
						}
					}
				}
			}
		});
	}

	private void localizeView(LocalizationAdapter adapter, final VElement view) {
		localize(adapter, view);
		checkContents(adapter, view);
	}

	private LocalizationAdapter getLocalizationAdapter(VElement view) {
		for (final Adapter adapter : view.eAdapters()) {
			if (LocalizationAdapter.class.isInstance(adapter)) {
				return (LocalizationAdapter) adapter;
			}
		}
		return null;
	}

	private void checkContents(final LocalizationAdapter adapter, final VElement vElementRoot) {
		final TreeIterator<EObject> eAllContents = vElementRoot.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			if (VElement.class.isInstance(eObject)) {
				final VElement vElement = VElement.class.cast(eObject);
				localize(adapter, vElement);
			}
		}
	}

	/**
	 * The actual method localizing a {@link VElement}.
	 *
	 * @param localizationAdapter The LocalizationAdapter to use for localization
	 * @param vElement The {@link VElement} to localize
	 */
	protected void localize(LocalizationAdapter localizationAdapter, final VElement vElement) {
		if (vElement.getName() == null) {
			vElement.setLabel(""); //$NON-NLS-1$
		} else if (vElement.getName().startsWith("%")) { //$NON-NLS-1$
			if (localizationAdapter != null) {
				vElement.setLabel(localizationAdapter.localize(vElement.getName().substring(1)));
			} else {
				reportService.report(new AbstractReport(
					"No LocalizationAdapter found for the current view:" + view.toString())); //$NON-NLS-1$
				vElement.setLabel(vElement.getName());
			}
		} else {
			vElement.setLabel(vElement.getName());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		if (localeProvider != null) {
			localeProvider.removeEMFFormsLocaleChangeListener(this);
		}
		if (bundleContext != null && localeServiceReference != null) {
			bundleContext.ungetService(localeServiceReference);
		}
		if (bundleContext != null && reportServiceReference != null) {
			bundleContext.ungetService(reportServiceReference);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return -100;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener#notifyLocaleChange()
	 */
	@Override
	public void notifyLocaleChange() {
		final LocalizationAdapter adapter = getLocalizationAdapter(view);
		localizeView(adapter, view);
	}

}
