/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.reporting.test;

import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.internal.swt.ECPSWTViewRendererImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.internal.swt.core.EMFFormsRendererFactoryImpl;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * SWT Test renderer factory that allows to register and unregister renderers.
 *
 * @author emueller
 *
 */
public class TestSWTRendererFactory {

	private final EMFFormsRendererFactoryImpl factory;

	public TestSWTRendererFactory() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsRendererFactory> serviceReference = bundleContext
			.getServiceReference(EMFFormsRendererFactory.class);
		factory = (EMFFormsRendererFactoryImpl) bundleContext.getService(serviceReference);
	}

	public void clearRenderers() {
		factory.clearRenderers();
	}

	public ECPSWTView render(Composite parent, ViewModelContext viewModelContext) throws ECPRendererException {
		final ECPSWTViewRendererImpl renderer = new ECPSWTViewRendererImpl() {
		};
		return renderer.render(parent, viewModelContext);
	}

	public TestEMFFormsRendererService registerRenderer(int priority, AbstractSWTRenderer renderer,
		Class<?> supportedEClass) {
		final TestEMFFormsRendererService rendererService = new TestEMFFormsRendererService(priority, renderer,
			supportedEClass);
		factory.addEMFFormsRendererService(rendererService);
		return rendererService;
	}

	public void deleteRegisteredRenderer(TestEMFFormsRendererService rendererService) {
		factory.removeEMFFormsRendererService(rendererService);
	}

	public static class TestEMFFormsRendererService implements EMFFormsRendererService {

		private final double priority;
		private final AbstractSWTRenderer renderer;
		private final Class<?> supportedEClass;

		public TestEMFFormsRendererService(double priority, AbstractSWTRenderer renderer, Class<?> supportedEClass) {
			this.priority = priority;
			this.renderer = renderer;
			this.supportedEClass = supportedEClass;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#isApplicable(VElement,ViewModelContext)
		 */
		@Override
		public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
			if (supportedEClass.isInstance(vElement)) {
				return priority;
			}
			return NOT_APPLICABLE;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emfforms.spi.swt.core.EMFFormsRendererService#getRendererInstance(org.eclipse.emf.ecp.view.spi.model.VElement,
		 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
		 */
		@Override
		public AbstractSWTRenderer getRendererInstance(VElement vElement, ViewModelContext viewModelContext) {

			return renderer;
		}

	}
}
