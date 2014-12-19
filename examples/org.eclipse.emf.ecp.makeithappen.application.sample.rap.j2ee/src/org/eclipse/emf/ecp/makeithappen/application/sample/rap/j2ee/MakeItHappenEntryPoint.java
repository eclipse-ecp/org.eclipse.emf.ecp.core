/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.makeithappen.application.sample.rap.j2ee;

import java.awt.Composite;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskPackage;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;

/**
 * Entry Point for a standalone RAP Application.
 */
public class MakeItHappenEntryPoint extends AbstractEntryPoint {

	private EObject getDummyEObject() {
		// Replace this with your own model EClass to test the application with a custom model
		final EClass eClass = TaskPackage.eINSTANCE.getUser();
		return EcoreUtil.create(eClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.rap.rwt.application.AbstractEntryPoint#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createContents(Composite parent) {
		// Special call needed only on RAP J2EE
		RealmSetter.initialize();

		final EObject dummyObject = getDummyEObject();

		try {
			final Composite content = new Composite(parent, SWT.NONE);
			content.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			content.setLayout(GridLayoutFactory.fillDefaults().margins(10, 10).create());
			content.setLayoutData(GridDataFactory.fillDefaults().create());

			final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(
				ViewProviderHelper.getView(dummyObject, null), dummyObject, new DefaultReferenceService());

			ECPSWTViewRenderer.INSTANCE.render(content, vmc);

			content.layout();

		} catch (final ECPRendererException e) {
			e.printStackTrace();
		}

		parent.pack();
	}

}
