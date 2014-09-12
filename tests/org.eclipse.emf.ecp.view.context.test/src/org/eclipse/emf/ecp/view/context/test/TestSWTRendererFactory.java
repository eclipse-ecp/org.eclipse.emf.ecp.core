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
package org.eclipse.emf.ecp.view.context.test;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.internal.swt.ECPRendererDescription;
import org.eclipse.emf.ecp.view.internal.swt.ECPSWTViewRendererImpl;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.model.common.ECPStaticRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * SWT Test renderer factory that allows to register and unregister renderers.
 * 
 * @author emueller
 * 
 */
public class TestSWTRendererFactory {

	private final SWTTestRendererFactoryImpl factory;

	public TestSWTRendererFactory() {
		factory = new SWTTestRendererFactoryImpl();
	}

	public void clearRenderers() {
		factory.clearRenderers();
	}

	public ECPSWTView render(Composite parent, ViewModelContext viewModelContext) throws ECPRendererException {
		final ECPSWTViewRendererImpl renderer = new ECPSWTViewRendererImpl() {
			@Override
			protected SWTRendererFactory createFactory() {
				return factory;
			}
		};
		return renderer.render(parent, viewModelContext);
	}

	public void registerRenderer(int priority,
		Class<AbstractSWTRenderer<VElement>> class1,
		Class<? extends VElement> supportedEObject) {

		final Set<ECPRendererTester> tester = new LinkedHashSet<ECPRendererTester>();
		tester.add(new ECPStaticRendererTester(priority, supportedEObject));
		final ECPRendererDescription descriptor = new ECPRendererDescription(class1, tester);
		factory.registerRenderer(descriptor);
	}

	public void replaceViewRenderer(int priority, Class<AbstractSWTRenderer<VElement>> class1,
		Class<? extends VElement> supportedEObject) {

		final Set<ECPRendererTester> tester = new LinkedHashSet<ECPRendererTester>();
		tester.add(new ECPStaticRendererTester(priority, supportedEObject));
		final ECPRendererDescription descriptor = new ECPRendererDescription(class1, tester);
		factory.replaceViewRenderer(descriptor);
	}

}
