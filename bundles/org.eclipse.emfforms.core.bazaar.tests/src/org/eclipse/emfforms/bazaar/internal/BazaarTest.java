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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emfforms.bazaar.Bazaar.PriorityOverlapCallBack;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.BazaarContextFunction;
import org.eclipse.emfforms.bazaar.Vendor;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jonas
 *
 */
public class BazaarTest {

	public static final String TESTSTRING = ""; //$NON-NLS-1$
	private BazaarImpl<MyWare> bazaar;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bazaar = new BazaarImpl<MyWare>();
	}

	@Test
	public void testEmptyBazaar() {
		final Vendor<MyWare> bestMountebank = bazaar.getBestMountebank(EclipseContextFactory.create());

		assertSame(null, bestMountebank);
	}

	@Test
	public void testSingleMountebankNoParameter() {
		final Vendor<MyWare> mountebank = new MountebankPriority1Parameter0();
		bazaar.addVendor(mountebank);

		final Vendor<MyWare> bestMountebank = bazaar.getBestMountebank(EclipseContextFactory.create());

		assertSame(mountebank, bestMountebank);
	}

	@Test
	public void testTwoMountebankNoParameter() {
		final Vendor<MyWare> mountebank = new MountebankPriority1Parameter0();
		final Vendor<MyWare> mountebank2 = new MountebankPriority2Parameter0();
		bazaar.addVendor(mountebank);
		bazaar.addVendor(mountebank2);

		final Vendor<MyWare> bestMountebank = bazaar.getBestMountebank(EclipseContextFactory.create());

		assertSame(mountebank2, bestMountebank);
	}

	@Test
	public void testTwoMountebankNoMatchingParameter() {
		final Vendor<MyWare> mountebank = new MountebankPriority1Parameter0();
		final Vendor<MyWare> mountebank2 = new MountebankPriority2Parameter1();
		bazaar.addVendor(mountebank);
		bazaar.addVendor(mountebank2);

		final IEclipseContext context = EclipseContextFactory.create();

		final Vendor<MyWare> bestMountebank = bazaar.getBestMountebank(context);

		assertSame(mountebank, bestMountebank);
	}

	@Test
	public void testTwoMountebankMatchingParameter() {
		final Vendor<MyWare> mountebank = new MountebankPriority1Parameter0();
		final Vendor<MyWare> mountebank2 = new MountebankPriority2Parameter1();
		bazaar.addVendor(mountebank);
		bazaar.addVendor(mountebank2);

		final IEclipseContext context = EclipseContextFactory.create();
		context.set(String.class, TESTSTRING);

		final Vendor<MyWare> bestMountebank = bazaar.getBestMountebank(context);

		assertSame(mountebank2, bestMountebank);
	}

	@Test
	public void testTwoMountebankSamePriority() {
		final Vendor<MyWare> mountebank = new MountebankPriority1Parameter0();
		final Vendor<MyWare> mountebank2 = new MountebankPriority1Parameter0();
		bazaar.addVendor(mountebank);
		bazaar.addVendor(mountebank2);

		@SuppressWarnings("unchecked")
		final PriorityOverlapCallBack<MyWare> priorityOverlapCallBackMock = mock(PriorityOverlapCallBack.class);

		bazaar.setPriorityOverlapCallBack(priorityOverlapCallBackMock);

		final IEclipseContext context = EclipseContextFactory.create();
		bazaar.getBestMountebank(context);

		verify(priorityOverlapCallBackMock, times(1)).priorityOverlap(mountebank, mountebank2);

	}

	@Test
	public void testTwoMountebankSamePriorityNoCallBack() {
		final Vendor<MyWare> mountebank = new MountebankPriority1Parameter0();
		final Vendor<MyWare> mountebank2 = new MountebankPriority1Parameter0();
		bazaar.addVendor(mountebank);
		bazaar.addVendor(mountebank2);

		final IEclipseContext context = EclipseContextFactory.create();
		bazaar.getBestMountebank(context);
	}

	@Test
	public void testCreateWareNoParameter0() {
		final MyWare myWareMock = mock(MyWare.class);
		final Vendor<MyWare> mountebank = new MountebankCreatingWareParameter0(myWareMock);

		final MyWare myWare = bazaar.createWare(mountebank, EclipseContextFactory.create());
		assertSame(myWareMock, myWare);
	}

	@Test
	public void testCreateWareParameter1() {
		final MyWare myWareMock = mock(MyWare.class);
		final Vendor<MyWare> mountebank = new MountebankCreatingWareParameter1(myWareMock);

		final IEclipseContext context = EclipseContextFactory.create();
		context.set(String.class, TESTSTRING);

		final MyWare myWare = bazaar.createWare(mountebank, context);
		assertSame(myWareMock, myWare);
	}

	@Test
	public void testCreateEmptyEclipseContext() {
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);

		final IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);

		assertNotNull(eclipseContext);
	}

	@Test
	public void testCreateEclipseContextOneObject() {
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		final HashMap<String, Object> mockMap = new HashMap<String, Object>();
		final Object mock = mock(Object.class);
		mockMap.put(TESTSTRING, mock);
		when(bazaarContextMock.getContextMap()).thenReturn(mockMap);

		final IEclipseContext eclipseContext = bazaar.createEclipseContext(bazaarContextMock);

		final Object actual = eclipseContext.get(TESTSTRING);
		assertSame(mock, actual);
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
	public void testCreateWare() {
		final BazaarContextFunction contextFunction = new BazaarContextFunctionParameter1(TESTSTRING);
		bazaar.addContextFunction(String.class.getName(), contextFunction);
		final BazaarContext bazaarContextMock = mock(BazaarContext.class);
		final HashMap<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put(Integer.class.getName(), new Integer(0));
		when(bazaarContextMock.getContextMap()).thenReturn(contextMap);

		final MyWare myWareMock = mock(MyWare.class);
		final Vendor<MyWare> mountebank = new FullMountebankParameter2(myWareMock);
		bazaar.addVendor(mountebank);

		final MyWare createdWare = bazaar.createWare(bazaarContextMock);

		assertSame(myWareMock, createdWare);
	}

}
