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
 * Christian W. Damus - bug 529542
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emfforms.bazaar.Bazaar.PriorityOverlapCallBack;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.BazaarContextFunction;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.Vendor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author jonas
 *
 */
@RunWith(Parameterized.class)
public class Bazaar_ITest {

	public static final String TESTSTRING = ""; //$NON-NLS-1$
	private final BazaarImpl<MyProduct> bazaar;

	/**
	 * Initializes me with the bazaar variant to create.
	 *
	 * @param variant the bazaar variant
	 */
	public Bazaar_ITest(BazaarVariant variant) {
		super();

		bazaar = variant.createBazaarImpl();
	}

	@Parameters(name = "{0}")
	public static Object[] parameters() {
		return BazaarVariant.values();
	}

	@Test
	public void testEmptyBazaar() {
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(EclipseContextFactory.create());

		assertSame(null, bestVendor);
	}

	@Test
	public void testSingleVendorNoParameter() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		bazaar.addVendor(vendor);

		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(EclipseContextFactory.create());

		assertSame(vendor, bestVendor);
	}

	@Test
	public void testSingleVendorNoParameterWrongBid() {
		final Vendor<MyProduct> vendor = new VendorWrongBidParameter0();
		bazaar.addVendor(vendor);

		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(EclipseContextFactory.create());

		assertNull(bestVendor);
	}

	@Test
	public void testTwoVendorNoParameter() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority2Parameter0();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);

		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(EclipseContextFactory.create());

		assertSame(vendor2, bestVendor);
	}

	@Test
	public void testTwoVendorNoParameterTwisted() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority2Parameter0();
		bazaar.addVendor(vendor2);
		bazaar.addVendor(vendor);

		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(EclipseContextFactory.create());

		assertSame(vendor2, bestVendor);
	}

	@Test
	public void testTwoVendorNoMatchingParameter() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority2Parameter1();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);

		final IEclipseContext context = EclipseContextFactory.create();

		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);

		assertSame(vendor, bestVendor);
	}

	@Test
	public void testTwoVendorMatchingParameter() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority2Parameter1();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);

		final IEclipseContext context = EclipseContextFactory.create();
		context.set(String.class, TESTSTRING);

		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);

		assertSame(vendor2, bestVendor);
	}

	@Test
	public void testTwoVendorSamePriority() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority1Parameter0();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);

		@SuppressWarnings("unchecked")
		final PriorityOverlapCallBack<MyProduct> priorityOverlapCallBackMock = mock(PriorityOverlapCallBack.class);

		bazaar.setPriorityOverlapCallBack(priorityOverlapCallBackMock);

		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> best = bazaar.getBestVendor(context);

		verify(priorityOverlapCallBackMock, times(1)).priorityOverlap(vendor, vendor2);
		assertThat(best, is((Object) vendor));
	}

	@Test
	public void testTwoVendorSamePriorityNoCallBack() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority1Parameter0();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);

		final IEclipseContext context = EclipseContextFactory.create();
		bazaar.getBestVendor(context);
	}

	@Test
	public void testVendorNoMatchingPreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertNull(bestVendor);
	}

	@Test
	public void testVendorMatchingPreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition(0);
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithPrecondition.KEY, VendorWithPrecondition.VALUE);
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertSame(vendor, bestVendor);
	}

	@Test
	public void testVendorMatchingNoValuePreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithNoValuePrecondition();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithPrecondition.KEY, VendorWithPrecondition.VALUE);
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertNotNull(bestVendor);
	}

	@Test
	public void testVendorWithNullInContextNoValuePreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithNoValuePrecondition();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithPrecondition.KEY, null);
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertNotNull(bestVendor);
	}

	@Test
	public void testVendorNotMatchingNoValuePreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithNoValuePrecondition();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertNull(bestVendor);

	}

	@Test
	public void testTwoVendorOneMatchingPreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition(0);
		final Vendor<MyProduct> vendor2 = new VendorWithTwoPreconditions(1);
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithPrecondition.KEY, VendorWithPrecondition.VALUE);
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertSame(vendor, bestVendor);
	}

	@Test
	public void testTwoVendorNoMatchingPreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition();
		final Vendor<MyProduct> vendor2 = new VendorWithTwoPreconditions();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);
		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertNull(bestVendor);
	}

	@Test
	public void testTwoVendorBothMatchingPreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition(1);
		final Vendor<MyProduct> vendor2 = new VendorWithTwoPreconditions(0);
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithPrecondition.KEY, VendorWithPrecondition.VALUE);
		context.set(VendorWithTwoPreconditions.KEY1, VendorWithTwoPreconditions.VALUE1);
		context.set(VendorWithTwoPreconditions.KEY2, VendorWithTwoPreconditions.VALUE2);
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertSame(vendor, bestVendor);
	}

	@Test
	public void testNoMatchingPreConditionKey() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		final boolean checkPreConditions = bazaar.checkPreConditions(vendor, context);
		assertFalse(checkPreConditions);
	}

	@Test
	public void testNoMatchingPreConditionValue() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithPrecondition.KEY, mock(Object.class));
		final boolean checkPreConditions = bazaar.checkPreConditions(vendor, context);
		assertFalse(checkPreConditions);
	}

	@Test
	public void testTwoPreConditionsNoMatch() {
		final Vendor<MyProduct> vendor = new VendorWithTwoPreconditions();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithTwoPreconditions.KEY1, mock(Object.class));
		final boolean checkPreConditions = bazaar.checkPreConditions(vendor, context);
		assertFalse(checkPreConditions);
	}

	@Test
	public void testTwoPreConditionsOneNoMatch() {
		final Vendor<MyProduct> vendor = new VendorWithTwoPreconditions();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithTwoPreconditions.KEY1, VendorWithTwoPreconditions.VALUE1);
		final boolean checkPreConditions = bazaar.checkPreConditions(vendor, context);
		assertFalse(checkPreConditions);
	}

	@Test
	public void testTwoPreConditionsBothMatch() {
		final Vendor<MyProduct> vendor = new VendorWithTwoPreconditions();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithTwoPreconditions.KEY1, VendorWithTwoPreconditions.VALUE1);
		context.set(VendorWithTwoPreconditions.KEY2, VendorWithTwoPreconditions.VALUE2);
		final boolean checkPreConditions = bazaar.checkPreConditions(vendor, context);
		assertTrue(checkPreConditions);
	}

	@Test
	public void testMatchingPreCondition() {
		final Vendor<MyProduct> vendor = new VendorWithPrecondition();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		context.set(VendorWithPrecondition.KEY, VendorWithPrecondition.VALUE);
		final boolean checkPreConditions = bazaar.checkPreConditions(vendor, context);
		assertTrue(checkPreConditions);
	}

	@Test
	public void testOneStaticBid() {
		final Vendor<MyProduct> vendor = new VendorWithStaticBid0();
		bazaar.addVendor(vendor);
		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertSame(vendor, bestVendor);
	}

	@Test
	public void testTwoStaticBids() {
		final Vendor<MyProduct> vendor = new VendorWithStaticBid0();
		final Vendor<MyProduct> vendor2 = new VendorWithStaticBid2();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);
		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertSame(vendor2, bestVendor);
	}

	@Test
	public void testStaticAndDynamicBidsDynamicWins() {
		final Vendor<MyProduct> vendor = new VendorWithStaticBid0();
		final Vendor<MyProduct> vendor2 = new VendorPriority1Parameter0();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);
		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertSame(vendor2, bestVendor);
	}

	@Test
	public void testStaticAndDynamicBidsStaticWins() {
		final Vendor<MyProduct> vendor = new VendorWithStaticBid2();
		final Vendor<MyProduct> vendor2 = new VendorPriority1Parameter0();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);
		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> bestVendor = bazaar.getBestVendor(context);
		assertSame(vendor, bestVendor);
	}

	@Test
	public void testOverlappingBidsStaticDynamic() {
		final Vendor<MyProduct> vendor = new VendorWithStaticBid2();
		final Vendor<MyProduct> vendor2 = new VendorPriority2Parameter0();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);

		@SuppressWarnings("unchecked")
		final PriorityOverlapCallBack<MyProduct> priorityOverlapCallBackMock = mock(PriorityOverlapCallBack.class);

		bazaar.setPriorityOverlapCallBack(priorityOverlapCallBackMock);

		final IEclipseContext context = EclipseContextFactory.create();
		final Vendor<? extends MyProduct> best = bazaar.getBestVendor(context);

		verify(priorityOverlapCallBackMock, times(1)).priorityOverlap(vendor, vendor2);
		assertThat(best, is((Object) vendor));
	}

	@Test
	public void testCreateProductNoParameter0() {
		final MyProduct myProductMock = mock(MyProduct.class);
		final Vendor<MyProduct> vendor = new VendorCreatingProductParameter0(myProductMock);

		final MyProduct myProduct = bazaar.createProduct(vendor, EclipseContextFactory.create());
		assertSame(myProductMock, myProduct);
	}

	@Test
	public void testCreateProductParameter1() {
		final MyProduct myProductMock = mock(MyProduct.class);
		final Vendor<MyProduct> vendor = new VendorCreatingProductParameter1(myProductMock);

		final IEclipseContext context = EclipseContextFactory.create();
		context.set(String.class, TESTSTRING);

		final MyProduct myProduct = bazaar.createProduct(vendor, context);
		assertSame(myProductMock, myProduct);
	}

	@Test
	public void testCreateEmptyEclipseContext() {
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);

		final IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);

		assertNotNull(eclipseContext);
	}

	@Test
	public void testCreateEclipseContextOneObject() {
		final Object value = new Object();
		final BazaarContext bazaarContext = BazaarContext.Builder.with(
			Collections.singletonMap(TESTSTRING, value)).build();

		final IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContext);

		final Object actual = eclipseContext.get(TESTSTRING);
		assertSame(value, actual);
	}

	@Test
	public void testContextWithContextFunction() {
		final Object transformed = mock(Object.class);
		final BazaarContextFunction contextFunction = new BazaarContextFunctionNoParameter(transformed);
		bazaar.addContextFunction(TESTSTRING, contextFunction);
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);
		eclipseContext = bazaar.addContextFunctions(eclipseContext);
		final Object actual = eclipseContext.get(TESTSTRING);
		assertSame(transformed, actual);
	}

	@Test
	public void testContextWithContextFunctionNoMatchingParameter() {
		final Object transformed = mock(Object.class);
		final BazaarContextFunction contextFunction = new BazaarContextFunctionParameter1(transformed);
		bazaar.addContextFunction(TESTSTRING, contextFunction);
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);
		eclipseContext = bazaar.addContextFunctions(eclipseContext);

		final Object actual = eclipseContext.get(TESTSTRING);
		assertSame(null, actual);
	}

	@Test
	public void testContextWithContextFunctionMatchingParameter() {
		final Object transformed = mock(Object.class);
		final BazaarContextFunction contextFunction = new BazaarContextFunctionParameter1(transformed);
		bazaar.addContextFunction(TESTSTRING, contextFunction);
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);
		eclipseContext = bazaar.addContextFunctions(eclipseContext);
		eclipseContext.set(Integer.class, new Integer(1));

		final Object actual = eclipseContext.get(TESTSTRING);

		assertSame(transformed, actual);
	}

	@Test
	public void testContextWithContextFunctionReturningNull() {
		final BazaarContextFunction contextFunction = new BazaarContextFunctionReturningNull();
		bazaar.addContextFunction(TESTSTRING, contextFunction);
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);
		eclipseContext = bazaar.addContextFunctions(eclipseContext);

		final Object actual = eclipseContext.get(TESTSTRING);

		assertSame(null, actual);
	}

	@Test
	public void testContextWithContextFunctionCacheValue() {
		final Object transformed = mock(Object.class);
		final BazaarContextFunctionWithCounter contextFunction = new BazaarContextFunctionWithCounter(transformed);
		bazaar.addContextFunction(TESTSTRING, contextFunction);
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);
		eclipseContext = bazaar.addContextFunctions(eclipseContext);

		Object actual = eclipseContext.get(TESTSTRING);
		actual = eclipseContext.get(TESTSTRING);

		assertSame(transformed, actual);
		assertSame(1, contextFunction.getCount());
	}

	@Test
	public void testCreateProduct() {
		final BazaarContextFunction contextFunction = new BazaarContextFunctionParameter1(TESTSTRING);
		bazaar.addContextFunction(String.class.getName(), contextFunction);
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		final HashMap<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put(Integer.class.getName(), new Integer(0));
		when(bazaarContextMock.getContextMap()).thenReturn(contextMap);

		final MyProduct myProductMock = mock(MyProduct.class);
		final Vendor<MyProduct> vendor = new FullVendorParameter2(myProductMock);
		bazaar.addVendor(vendor);

		final MyProduct createdProduct = bazaar.createProduct(bazaarContextMock);

		assertSame(myProductMock, createdProduct);
	}

	@Test
	public void testCreateProductNoBiddingVendor() {
		final BazaarContextFunction contextFunction = new BazaarContextFunctionParameter1(TESTSTRING);
		bazaar.addContextFunction(String.class.getName(), contextFunction);

		final BazaarContext bazaarContext = BazaarContext.Builder.empty()
			.put(Integer.class, 0).build();

		final MyProduct myProductMock = mock(MyProduct.class);
		bazaar.addVendor(new VendorCreatingProductParameter0(myProductMock) {
			@Bid
			public Double bid(String string) {
				return null;
			}
		});

		final MyProduct createdProduct = bazaar.createProduct(bazaarContext);
		assertThat(createdProduct, nullValue());
	}

	@Test
	public void testCreateProductNoVendor() {

		final BazaarContext bazaarContext = BazaarContext.Builder.empty().build();

		final MyProduct createdProduct = bazaar.createProduct(bazaarContext);
		assertThat(createdProduct, nullValue());
	}

	@Test
	public void testCreateProductsWithPriority() {
		final BazaarContextFunction contextFunction = new BazaarContextFunctionParameter1(TESTSTRING);
		bazaar.addContextFunction(String.class.getName(), contextFunction);

		final BazaarContext bazaarContext = BazaarContext.Builder.empty()
			.add(0).build();

		final MyProduct myProductMock1 = mock(MyProduct.class);
		bazaar.addVendor(new VendorPriority01Parameter1() {
			@Create
			public MyProduct make(String string) {
				return myProductMock1;
			}
		});
		final MyProduct myProductMock2 = mock(MyProduct.class);
		bazaar.addVendor(new VendorPriority1Parameter1() {
			@Create
			public MyProduct make(String string) {
				return myProductMock2;
			}
		});
		final MyProduct myProductMock3 = mock(MyProduct.class);
		bazaar.addVendor(new VendorPriority2Parameter1() {
			@Create
			public MyProduct make(String string) {
				return myProductMock3;
			}
		});

		final List<MyProduct> createdProducts = bazaar.createProducts(bazaarContext);

		// The vendor bids order these in reverse (high bid first)
		assertThat(createdProducts, is(asList(myProductMock3, myProductMock2, myProductMock1)));
	}

	@Test
	public void testCreateProductsNoBid() {

		final BazaarContext bazaarContext = BazaarContext.Builder.empty().build();

		final MyProduct myProductMock = mock(MyProduct.class);
		bazaar.addVendor(new VendorCreatingProductParameter1(myProductMock)); // No bid

		final List<MyProduct> createdProducts = bazaar.createProducts(bazaarContext);

		assertThat(createdProducts, not(hasItem(any(MyProduct.class))));
	}

	@Test
	public void testCreateProductsBidButNoCreate() {

		final BazaarContext bazaarContext = BazaarContext.Builder.empty().build();

		final MyProduct myProductMock = mock(MyProduct.class);
		bazaar.addVendor(new VendorCreatingProductParameter1(myProductMock) {
			@Bid
			public double bid() {
				return 2.0;
			}
		});

		final List<MyProduct> createdProducts = bazaar.createProducts(bazaarContext);

		assertTrue(createdProducts.isEmpty());
	}

	@Test
	public void testCreateProductsBidCreateNull() {

		final BazaarContext bazaarContext = BazaarContext.Builder.empty().build();

		bazaar.addVendor(new Vendor<MyProduct>() {

			@Bid
			public double bid() {
				return 2.0;
			}

			@Create
			public MyProduct create() {
				return null;
			}
		});

		final List<MyProduct> createdProducts = bazaar.createProducts(bazaarContext);

		assertTrue(createdProducts.isEmpty());
	}

	@Test
	public void testRemoveVendor() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority2Parameter1();
		bazaar.addVendor(vendor);
		bazaar.addVendor(vendor2);

		final IEclipseContext context = EclipseContextFactory.create();
		context.set(String.class, TESTSTRING);

		Vendor<?> bestVendor = bazaar.getBestVendor(context);

		assumeThat(bestVendor, is((Object) vendor2));

		bazaar.removeVendor(vendor2);

		bestVendor = bazaar.getBestVendor(context);

		assertThat(bestVendor, is((Object) vendor));
	}

}
