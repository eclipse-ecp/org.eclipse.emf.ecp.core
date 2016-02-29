/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.core.swt.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class CorrectDipose_PTest {

	private VView view;
	private Shell shell;
	private Player domain;

	@Before
	public void before() {
		shell = SWTViewTestHelper.createShell();
		view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(BowlingPackage.eINSTANCE.getPlayer());
		domain = BowlingFactory.eINSTANCE.createPlayer();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void rendererRemovesListenerTest() {
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VFeaturePathDomainModelReference vdmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		vdmr.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		vControl.setDomainModelReference(vdmr);
		view.getChildren().add(vControl);

		try {
			ECPSWTViewRenderer.INSTANCE.render(shell, domain, view);
		} catch (final ECPRendererException e) {
			fail(e.getMessage());
		}
		// 1 for the control and 1 for the ViewModelContext
		assertEquals(1, vdmr.getChangeListener().size());
		shell.dispose();

		assertEquals(0, vdmr.getChangeListener().size());
	}
}
