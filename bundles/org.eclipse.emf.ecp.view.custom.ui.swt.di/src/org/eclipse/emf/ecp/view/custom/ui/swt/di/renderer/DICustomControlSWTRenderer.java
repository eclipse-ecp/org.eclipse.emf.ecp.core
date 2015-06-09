/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.custom.ui.swt.di.renderer;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.custom.swt.CustomControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.framework.Bundle;

/**
 * Custom control SWTRenderer for Dependency Injection.
 *
 * @author jfaltermeier
 *
 */
public class DICustomControlSWTRenderer extends CustomControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link ReportService}
	 */
	public DICustomControlSWTRenderer(VCustomControl vElement, ViewModelContext viewContext, ReportService factory) {
		super(vElement, viewContext, factory);
	}

	private IEclipseContext eclipseContext;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.CustomControlSWTRenderer#loadCustomControl(org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl)
	 */
	@SuppressWarnings("restriction")
	@Override
	protected ECPAbstractCustomControlSWT loadCustomControl(VCustomControl customControl) {
		String bundleName = customControl.getBundleName();
		String className = customControl.getClassName();
		if (customControl.getBundleName() != null) {
		}
		if (bundleName == null) {
			bundleName = ""; //$NON-NLS-1$
		}
		if (className == null) {
			className = ""; //$NON-NLS-1$
		}
		final Object pojo = loadPOJO(bundleName, className);
		eclipseContext = org.eclipse.emf.ecp.view.model.common.di.renderer.DIRendererUtil.getContextForElement(
			getVElement(), getViewModelContext());
		final DICustomControlSWT customControlSWT = new DICustomControlSWT(pojo, eclipseContext);
		return customControlSWT;
	}

	private static Object loadPOJO(String bundleName, String clazz) {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			return null;
		}
		try {
			final Class<?> loadClass = bundle.loadClass(clazz);
			return loadClass.newInstance();
		} catch (final ClassNotFoundException ex) {
			return null;
		} catch (final InstantiationException ex) {
			return null;
		} catch (final IllegalAccessException ex) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.CustomControlSWTRenderer#renderControl(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@SuppressWarnings("restriction")
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		org.eclipse.emf.ecp.ui.view.swt.di.util.SWTContextUtil.setAbstractSWTRendererObjects(eclipseContext,
			getVElement(), getViewModelContext(), parent);
		eclipseContext.set(SWTGridCell.class, cell);
		return super.renderControl(cell, parent);
	}

}
