/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.custom.ui.swt.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomFactory;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author jfaltermeier
 *
 */
@RunWith(DatabindingClassRunner.class)
public class CustomControlDMR_PTest {

	private static final String OLD_NAME = "old";
	private static final String NEW_NAME = "new";

	@Test
	public void testDMRChangeListener() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		InterruptedException, EMFFormsNoRendererException {
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		final Merchandise old = BowlingFactory.eINSTANCE.createMerchandise();
		old.setName(OLD_NAME);
		fan.setFavouriteMerchandise(old);

		final VView view = VViewFactory.eINSTANCE.createView();
		final VCustomControl custom = VCustomFactory.eINSTANCE.createCustomControl();
		custom.setBundleName("org.eclipse.emf.ecp.view.custom.ui.swt.test");
		custom.setClassName("org.eclipse.emf.ecp.view.custom.ui.swt.test.CustomControlStub2");
		final VCustomDomainModelReference customDMR = VCustomFactory.eINSTANCE.createCustomDomainModelReference();
		customDMR.setBundleName("org.eclipse.emf.ecp.view.custom.ui.swt.test");
		customDMR.setClassName("org.eclipse.emf.ecp.view.custom.ui.swt.test.CustomControlStub2");
		custom.setDomainModelReference(customDMR);
		view.getChildren().add(custom);

		final Shell shell = SWTViewTestHelper.createShell();
		final Control control = SWTViewTestHelper.render(view, fan, shell);

		final CountDownLatch latch = new CountDownLatch(1);

		CustomControlStub2.resolvedDomainModelReference.getChangeListener().add(
			new DomainModelReferenceChangeListener() {
				@Override
				public void notifyChange() {
					latch.countDown();
				}
			});

		final Merchandise nevv = BowlingFactory.eINSTANCE.createMerchandise();
		nevv.setName(NEW_NAME);
		fan.setFavouriteMerchandise(nevv);

		assertTrue(latch.await(1, TimeUnit.SECONDS));
		final Setting next = CustomControlStub2.resolvedDomainModelReference.getIterator().next();
		assertSame(nevv, next.getEObject());
	}
}
