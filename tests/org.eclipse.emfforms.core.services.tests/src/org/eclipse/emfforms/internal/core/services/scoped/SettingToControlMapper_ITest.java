/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.internal.core.services.controlmapper.SettingToControlMapperImpl;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.junit.Before;
import org.junit.Test;

public class SettingToControlMapper_ITest {

	private static final int NR_UPDATES = 100;
	private static final int NR_RUNNABLES = 100;

	private SettingToControlMapperImpl mapper;
	private EObject domainObject;
	private VControl control;
	private FakeViewContext context;

	@Before
	public void setup() {
		final VView view = VViewFactory.eINSTANCE.createView();
		control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		view.getChildren().add(control);
		domainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		context = new FakeViewContext(domainObject, view);
		mapper = new SettingToControlMapperImpl(
			new EMFFormsMappingProviderManager() {
				@Override
				public Set<UniqueSetting> getAllSettingsFor(VDomainModelReference domainModelReference,
					EObject domainObject) {
					final Set<UniqueSetting> settings = new LinkedHashSet<UniqueSetting>();
					settings.add(
						UniqueSetting.createSetting(domainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
					return settings;
				}
			}, context);
	}

	@Test
	public void updateMappingsConcurrently() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(NR_RUNNABLES);

		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
		final ArrayList<Runnable> runnables = new ArrayList<Runnable>();
		for (int i = 0; i < NR_RUNNABLES; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					try {
						int j = 0;
						while (j < NR_UPDATES) {
							mapper.updateControlMapping(control);
							Thread.sleep(1);
							j += 1;
						}
						// BEGIN SUPRESS CATCH EXCEPTION
					} catch (final Exception exception) {
						exceptions.add(exception);
						// END SUPRESS CATCH EXCEPTION
					} finally {
						latch.countDown();
					}

				}
			});
		}
		final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(runnables.size());

		// act
		for (final Runnable runnable : runnables) {
			newFixedThreadPool.submit(runnable);
		}

		latch.await();

		// assert
		assertTrue(exceptions.isEmpty());
	}

	@Test
	public void getControlsForDirectControlAdd() {
		mapper.vControlAdded(control);
		final Set<VElement> controlsFor = mapper
			.getControlsFor(UniqueSetting.createSetting(domainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		assertEquals(1, controlsFor.size());
		assertTrue(controlsFor.contains(control));
	}

	@Test
	public void getControlsForChildContextAdd() {
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);
		final Set<VElement> controlsFor = mapper
			.getControlsFor(
				UniqueSetting.createSetting(childDomainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		assertEquals(2, controlsFor.size());
		assertTrue(controlsFor.contains(control));
		assertTrue(controlsFor.contains(childControl));
	}

	@Test
	public void getControlsForGrandChildContextAdd() {
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);

		final VView grandChildView = VViewFactory.eINSTANCE.createView();
		final VControl grandChildControl = VViewFactory.eINSTANCE.createControl();
		grandChildControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		grandChildView.getChildren().add(grandChildControl);
		final EObject grandChildDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext grandChildContext = new FakeViewContext(grandChildDomainObject, grandChildView);
		childContext.addChildContext(childControl, grandChildContext);

		final Set<VElement> controlsFor = mapper
			.getControlsFor(
				UniqueSetting.createSetting(grandChildDomainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		assertEquals(3, controlsFor.size());
		assertTrue(controlsFor.contains(control));
		assertTrue(controlsFor.contains(childControl));
		assertTrue(controlsFor.contains(grandChildControl));
	}

	@Test
	public void getControlsForChildContextPerformance() {

		for (int i = 0; i < 4999; i++) {
			final VView childView = VViewFactory.eINSTANCE.createView();
			final VControl childControl = VViewFactory.eINSTANCE.createControl();
			childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
			childView.getChildren().add(childControl);
			final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
			final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
			context.addChildContext(control, childContext);
		}
		final VView childView = VViewFactory.eINSTANCE.createView();
		final VControl childControl = VViewFactory.eINSTANCE.createControl();
		childControl.setDomainModelReference(EcorePackage.eINSTANCE.getEClass_EAttributes());
		childView.getChildren().add(childControl);
		final EObject childDomainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final FakeViewContext childContext = new FakeViewContext(childDomainObject, childView);
		context.addChildContext(control, childContext);
		final long startTime = System.nanoTime();
		final Set<VElement> controlsFor = mapper
			.getControlsFor(
				UniqueSetting.createSetting(childDomainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
		final long endTime = System.nanoTime();
		assertEquals(2, controlsFor.size());
		assertTrue(controlsFor.contains(control));
		assertTrue(controlsFor.contains(childControl));
		final long durationYs = (endTime - startTime) / 1000;
		// half a millisecond should be more then enough time
		assertTrue(MessageFormat.format("Duration was {0}.", durationYs), durationYs < 500); //$NON-NLS-1$
	}
}
