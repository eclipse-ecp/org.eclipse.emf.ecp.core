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
package org.eclipse.emf.ecp.view.validation.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.internal.validation.ValidationServiceImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.validation.ValidationService;
import org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent;
import org.eclipse.emf.ecp.view.validation.test.model.TestFactory;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ParallelValidationService_PTest {

	@Parameterized.Parameters
	public static List<Object[]> params() {
		// run test 500 times
		return Arrays.asList(new Object[500][0]);
	}

	private DefaultRealm defaultRealm;

	private VControl control;
	private ValidationServiceImpl validationService;
	private ExecutorService executors;

	private CrossReferenceContent content;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		defaultRealm = new DefaultRealm();
		executors = Executors.newFixedThreadPool(100);
		setupContent();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		executors.shutdownNow();
		defaultRealm.dispose();
		validationService.dispose();
	}

	private void setupContent() {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(TestPackage.eINSTANCE.getContent());

		control = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getCrossReferenceContent_Parent());
		control.setDomainModelReference(domainModelReference);

		view.getChildren().add(control);

		content = TestFactory.eINSTANCE.createCrossReferenceContent();
		final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(view, content);
		validationService = (ValidationServiceImpl) vmc.getService(ValidationService.class);
	}

	@Test
	public void validateParallel() throws InterruptedException {
		// arrange
		final AtomicBoolean doesPass = new AtomicBoolean(true);
		final CountDownLatch latch = new CountDownLatch(1000);

		// act
		for (int i = 0; i < 100; i++) {
			executors.submit(
				createValidationRunnable(doesPass, latch));
		}
		latch.await();

		// assert
		assertTrue(doesPass.get());
	}

	private Runnable createValidationRunnable(
		final AtomicBoolean doesPass,
		final CountDownLatch latch) {

		return new Runnable() {
			/**
			 * {@inheritDoc}
			 *
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				int i = 0;
				while (i < 10) {
					try {
						TimeUnit.MILLISECONDS.sleep(1);
						validationService.validate(control);
						// BEGIN SUPRESS CATCH EXCEPTION
					} catch (final Exception ex) {
						// END SUPRESS CATCH EXCEPTION
						doesPass.set(false);
					} finally {
						i += 1;
						latch.countDown();
					}
				}
			}
		};
	}
}
