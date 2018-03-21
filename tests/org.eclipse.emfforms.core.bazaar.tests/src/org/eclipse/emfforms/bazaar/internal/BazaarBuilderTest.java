/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.Bazaar.PriorityOverlapCallBack;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Vendor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test cases for the {@link org.eclipse.emfforms.bazaar.Bazaar.Builder} class.
 *
 * @author Christian W. Damus
 */
@RunWith(Parameterized.class)
public class BazaarBuilderTest {

	private final BazaarVariant variant;

	/**
	 * Initializes me.
	 */
	public BazaarBuilderTest(BazaarVariant variant) {
		super();

		this.variant = variant;
	}

	@Test
	public void addVendor() {
		final MyProduct myProductMock = mock(MyProduct.class);
		final Bazaar.Builder<MyProduct> fixture = basicFixture();
		final Bazaar<MyProduct> bazaar = fixture
			.add(new FullVendorParameter2(myProductMock))
			.build();

		assertThat(bazaar.createProduct(context()), is(myProductMock));
	}

	@Test
	public void addTwoVendors() {
		final MyProduct myProductMock1 = mock(MyProduct.class);
		final MyProduct myProductMock2 = mock(MyProduct.class);
		final Bazaar.Builder<MyProduct> fixture = basicFixture();
		@SuppressWarnings("unchecked")
		final Bazaar<MyProduct> bazaar = fixture
			.add(new FullVendorParameter2(myProductMock1), new FullVendorParameter2(myProductMock2))
			.build();

		assertThat(bazaar.createProducts(context()), hasItems(myProductMock1, myProductMock2));
	}

	@Test
	public void addThreeVendors() {
		final MyProduct myProductMock1 = mock(MyProduct.class);
		final MyProduct myProductMock2 = mock(MyProduct.class);
		final MyProduct myProductMock3 = mock(MyProduct.class);
		final Bazaar.Builder<MyProduct> fixture = basicFixture();
		@SuppressWarnings("unchecked")
		final Bazaar<MyProduct> bazaar = fixture
			.add(new FullVendorParameter2(myProductMock1),
				new FullVendorParameter2(myProductMock2),
				new FullVendorParameter2(myProductMock3))
			.build();

		assertThat(bazaar.createProducts(context()), hasItems(myProductMock1, myProductMock2, myProductMock3));
	}

	@Test
	public void addAllVendors() {
		final MyProduct myProductMock1 = mock(MyProduct.class);
		final MyProduct myProductMock2 = mock(MyProduct.class);
		final MyProduct myProductMock3 = mock(MyProduct.class);
		final Bazaar.Builder<MyProduct> fixture = basicFixture();
		final Bazaar<MyProduct> bazaar = fixture
			.addAll(Arrays.asList(new FullVendorParameter2(myProductMock1),
				new FullVendorParameter2(myProductMock2),
				new FullVendorParameter2(myProductMock3)))
			.build();

		assertThat(bazaar.createProducts(context()), hasItems(myProductMock1, myProductMock2, myProductMock3));
	}

	@Test
	public void onPriorityOverlap() {
		final Vendor<MyProduct> vendor = new VendorPriority1Parameter0();
		final Vendor<MyProduct> vendor2 = new VendorPriority1Parameter0();
		@SuppressWarnings("unchecked")
		final Bazaar.Builder<MyProduct> fixture = vendorFixture(vendor, vendor2);

		@SuppressWarnings("unchecked")
		final PriorityOverlapCallBack<MyProduct> priorityOverlapCallBackMock = mock(PriorityOverlapCallBack.class);
		final Bazaar<MyProduct> bazaar = fixture
			.onPriorityOverlap(priorityOverlapCallBackMock)
			.build();

		((BazaarImpl<?>) bazaar).getBestVendor(EclipseContextFactory.create());

		verify(priorityOverlapCallBackMock, times(1)).priorityOverlap(vendor, vendor2);
	}

	@Test(expected = IllegalStateException.class)
	public void onPriorityOverlapAlreadySet() {
		final Bazaar.Builder<MyProduct> fixture = basicFixture();

		@SuppressWarnings("unchecked")
		final PriorityOverlapCallBack<MyProduct> priorityOverlapCallBackMock1 = mock(PriorityOverlapCallBack.class);
		@SuppressWarnings("unchecked")
		final PriorityOverlapCallBack<MyProduct> priorityOverlapCallBackMock2 = mock(PriorityOverlapCallBack.class);

		fixture.onPriorityOverlap(priorityOverlapCallBackMock1);
		fixture.onPriorityOverlap(priorityOverlapCallBackMock2);
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Object[] parameters() {
		return BazaarVariant.values();
	}

	Bazaar.Builder<MyProduct> basicFixture() {
		return variant.<MyProduct> builder()
			.addContextFunction(String.class, new BazaarContextFunctionParameter1("")); //$NON-NLS-1$
	}

	Bazaar.Builder<MyProduct> vendorFixture(Vendor<? extends MyProduct>... vendors) {
		return variant.builder(asList(vendors))
			.addContextFunction(String.class, new BazaarContextFunctionParameter1("")); //$NON-NLS-1$
	}

	static BazaarContext context() {
		return BazaarContext.Builder.empty().add(0).build();
	}
}
