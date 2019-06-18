/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.junit.Before;
import org.junit.Test;

/**
 * @author eugen
 *
 */
public class EMFFormsViewServiceManagerImpl_Test {

	private EMFFormsViewServiceManagerImpl serviceManager;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		serviceManager = new EMFFormsViewServiceManagerImpl();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateLocalImmediateService_null() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.IMMEDIATE);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.LOCAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(null);
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createLocalImmediateService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateLocalImmediateService_nonNull() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.IMMEDIATE);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.LOCAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(EcoreFactory.eINSTANCE.createEClass());
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createLocalImmediateService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertTrue(service.isPresent());
	}

	@Test
	public void testCreateLocalImmediateService_notExisting() {
		final Optional<?> service = serviceManager.createLocalImmediateService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateLocalLazyService_null() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.LAZY);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.LOCAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(null);
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createLocalLazyService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateLocalLazyService_nonNull() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.LAZY);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.LOCAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(EcoreFactory.eINSTANCE.createEClass());
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createLocalLazyService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertTrue(service.isPresent());
	}

	@Test
	public void testCreateLocalLazyService_notExisting() {
		final Optional<?> service = serviceManager.createLocalLazyService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateGlobalImmediateService_null() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.IMMEDIATE);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.GLOBAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(null);
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createGlobalImmediateService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateGlobalImmediateService_nonNull() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.IMMEDIATE);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.GLOBAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(EcoreFactory.eINSTANCE.createEClass());
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createGlobalImmediateService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertTrue(service.isPresent());
	}

	@Test
	public void testCreateGlobalImmediateService_notExisting() {
		final Optional<?> service = serviceManager.createGlobalImmediateService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateGlobalLazyService_null() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.LAZY);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.GLOBAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(null);
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createGlobalLazyService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateGlobalLazyService_nonNull() {
		final EMFFormsViewServiceFactory<EClass> factory = mock(EMFFormsViewServiceFactory.class);
		when(factory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.LAZY);
		when(factory.getScope()).thenReturn(EMFFormsViewServiceScope.GLOBAL);
		when(factory.getType()).thenReturn(EClass.class);
		when(factory.createService(any(EMFFormsViewContext.class))).thenReturn(EcoreFactory.eINSTANCE.createEClass());
		serviceManager.addEMFFormsScopedServiceProvider(factory);
		final Optional<?> service = serviceManager.createGlobalLazyService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertTrue(service.isPresent());
	}

	@Test
	public void testCreateGlobalLazyService_notExisting() {
		final Optional<?> service = serviceManager.createGlobalLazyService(EClass.class,
			mock(EMFFormsViewContext.class));
		assertFalse(service.isPresent());
	}

}
