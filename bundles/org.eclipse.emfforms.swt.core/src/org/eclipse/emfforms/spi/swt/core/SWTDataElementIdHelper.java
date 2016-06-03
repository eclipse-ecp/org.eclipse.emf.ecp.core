/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core;

import java.text.MessageFormat;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.data.EMFFormsSWTDataService;
import org.eclipse.swt.widgets.Widget;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Use this helper to ease setting the {@link VElement#getUuid() element} as the
 * {@link org.eclipse.swt.widgets.Widget#getData(String) SWT data}.
 *
 * @author Johannes Faltermeier
 * @since 1.9
 *
 */
public final class SWTDataElementIdHelper {

	/**
	 * Key constant for the element id.
	 */
	public static final String ELEMENT_ID_KEY = "org.eclipse.emfforms.elementId"; //$NON-NLS-1$

	private static final String ID_PATTERN = "{0}#{1}"; //$NON-NLS-1$
	private static final String CONTROL = "control"; //$NON-NLS-1$

	private SWTDataElementIdHelper() {
		// helper
	}

	/**
	 * Sets the element id with the control sub id on the given widget.
	 *
	 * @param widget the widget to set the data on
	 * @param element the element including the id
	 * @param context the {@link ViewModelContext}
	 */
	public static void setElementIdDataForVControl(final Widget widget, VControl element, ViewModelContext context) {
		setElementIdDataWithSubId(widget, element, CONTROL, context);
	}

	/**
	 * Sets the element id with the given sub id on the given widget.
	 *
	 * @param widget the widget to set the data on
	 * @param element the element including the id
	 * @param subId the sub id
	 * @param context the {@link ViewModelContext}
	 */
	public static void setElementIdDataWithSubId(final Widget widget, VElement element, String subId,
		ViewModelContext context) {
		widget.setData(ELEMENT_ID_KEY, MessageFormat.format(ID_PATTERN, getId(element, context), subId));
	}

	private static String getId(VElement element, ViewModelContext context) {
		final Bundle bundle = FrameworkUtil.getBundle(SWTDataElementIdHelper.class);
		if (bundle == null) {
			return element.getUuid();
		}
		final BundleContext bundleContext = bundle.getBundleContext();
		if (bundleContext == null) {
			return element.getUuid();
		}
		final ServiceReference<EMFFormsSWTDataService> serviceReference = bundleContext
			.getServiceReference(EMFFormsSWTDataService.class);
		if (serviceReference == null) {
			return element.getUuid();
		}
		final EMFFormsSWTDataService service = bundleContext.getService(serviceReference);
		if (service == null) {
			return element.getUuid();
		}
		final String id = service.getId(element, context);
		bundleContext.ungetService(serviceReference);
		return id;
	}

}
