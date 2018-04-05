/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.BazaarContextFunction;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Vendor;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jonas
 *
 */
public class Bazaar_PTest {

	public static final String TESTSTRING = ""; //$NON-NLS-1$
	private BazaarImpl<MyProduct> bazaar;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bazaar = new BazaarImpl<MyProduct>();
	}

	/**
	 * Can be moved to ITest once BR 513883 is fixed in platform
	 */
	@Test
	public void testNamedContextVariable() {

		final BazaarContextFunction contextFunction = new BazaarContextFunctionParameter1(TESTSTRING);
		bazaar.addContextFunction(String.class.getName(), contextFunction);

		final BazaarContext bazaarContext = BazaarContext.Builder.empty()
			.put(Integer.class, 0)
			.put("superclass", Number.class) //$NON-NLS-1$
			.build();

		final MyProduct myProductMock1 = mock(MyProduct.class);
		bazaar.addVendor(new VendorCreatingProductParameter0(myProductMock1) {
			@Bid
			public double bid(Class<?> clazz) {
				return 3.0;
			}
		});
		final MyProduct myProductMock2 = mock(MyProduct.class);
		bazaar.addVendor(new VendorCreatingProductParameter0(myProductMock2) {
			@Bid
			public double bid(@Named("superclass") Class<?> superclass) {
				return 2.0; // Lesser bid but the other isn't eligible
			}
		});

		final MyProduct createdProduct = bazaar.createProduct(bazaarContext);
		assertThat(createdProduct, is(myProductMock2));
	}

	@Test
	public void testPreConditionPerformanceNoMatch() {
		final IEclipseContext context = EclipseContextFactory.create();
		// Add another value, otherwise, empty context is too fast
		context.set(TESTSTRING, mock(Object.class));
		assertTrue(doComparison(true, context));
	}

	@Test
	public void testPreConditionPerformanceMatch() {
		final IEclipseContext context = EclipseContextFactory.create();
		// Add another value, otherwise, empty context is too fast
		context.set(VendorWithPrecondition.KEY, VendorWithPrecondition.VALUE);
		assertTrue(doComparison(false, context));
	}

	/**
	 *
	 * @return if expectedTheWithPreConditionsBetter, true if that is the case. Otherwise true, if with precondition not
	 *         more the 50% slower
	 */
	public boolean doComparison(boolean expectedTheWithPreConditionsBetter, IEclipseContext context) {
		final int iterations = 50000;
		final Vendor<MyProduct> vendor = new VendorWithPrecondition();
		bazaar.addVendor(vendor);
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			bazaar.getBestVendor(context);
		}
		final long withPreConditions = System.currentTimeMillis() - currentTimeMillis;
		bazaar.removeVendor(vendor);

		final Vendor<MyProduct> vendor2 = new VendorWithoutPrecondition();
		bazaar.addVendor(vendor2);
		currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			bazaar.getBestVendor(context);
		}
		final long withoutPreConditions = System.currentTimeMillis() - currentTimeMillis;

		if (expectedTheWithPreConditionsBetter) {
			return withPreConditions < withoutPreConditions;
		}
		return 1.5 * withoutPreConditions > withPreConditions;

	}
}
