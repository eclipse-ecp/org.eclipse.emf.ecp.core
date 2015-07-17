/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Neil Mackenzie - initial implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core.rap.test;

import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.ECPRepositoryManager;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.rap.SessionProvider;
import org.eclipse.emf.ecp.core.rap.sessionprovider.test.MockSessionProvider;
import org.eclipse.emf.ecp.core.rap.sessionprovider.test.MockSessionProvider.SessionProviderType;
import org.eclipse.emf.ecp.core.util.observer.ECPObserverBus;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author neilmack
 *         Tests the service factory classes from the
 *         org.eclipse.emf.ecp.core.rap package.
 */
@SuppressWarnings("restriction")
public class RAPFactories_PTest extends TestCase {
	/**
	 * Test retrieval of the mock SessionProvider service.
	 */
	@Test
	public void testRetrieveMock() {
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<SessionProvider> ref =
			bundleContext.getServiceReference(SessionProvider.class);
		final SessionProvider provider = bundleContext.getService(ref);
		assertNotNull(provider);
		bundleContext.ungetService(ref);
	}

	/**
	 * test that the ECPProjectManagerFacotry returns a
	 * working ECPProjectManager.
	 *
	 * @throws ECPProjectWithNameExistsException in case of exception
	 */
	@Test
	public final void testServices() throws ECPProjectWithNameExistsException {
		MockSessionProvider.getInstance();
		MockSessionProvider.
		setSessionProvider(SessionProviderType.DIFFERENT_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPProjectManager> serviceReference =
			bundleContext.getServiceReference(ECPProjectManager.class);

		final ECPProjectManager service1 =
			bundleContext.getService(serviceReference);
		final ECPProject project1 =
			service1.createProject(new EMFStoreProvider(), "Test10"); //$NON-NLS-1$
		final ECPProject project2 =
			service1.createProject(EMFStoreProvider.INSTANCE, "Test2"); //$NON-NLS-1$

		bundleContext.ungetService(serviceReference);
		final ECPProjectManager service2 =
			bundleContext.getService(serviceReference);
		assertNotSame(service1, service2);

		final ECPProject project3 =
			service2.createProject(EMFStoreProvider.INSTANCE, "Test3"); //$NON-NLS-1$

		final Collection<ECPProject> projects1 = service1.getProjects();
		assertEquals(2, projects1.size());
		assertTrue(projects1.contains(project1));
		assertTrue(projects1.contains(project2));

		final Collection<ECPProject> projects2 = service2.getProjects();
		assertEquals(1, projects2.size());
		assertTrue(projects2.contains(project3));
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPProjectManagerFactory is asked for
	 * a ECPProjectManager from 2 different sessions, that 2 different
	 * ECPProjectManagers are returned.
	 */
	@Test
	public void testDifferentSessionIdsECPProjectManager() {
		MockSessionProvider.getInstance();
		MockSessionProvider.
		setSessionProvider(
			SessionProviderType.DIFFERENT_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPProjectManager> serviceReference =
			bundleContext.
				getServiceReference(ECPProjectManager.class);

		final ECPProjectManager service1 =
			bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPProjectManager service2 =
			bundleContext.getService(serviceReference);
		assertNotSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPProjectManageractory is asked for
	 * a ECPProjectManager 2 times from the same session,
	 * that the same ECPProjectManager is returned.
	 */
	@Test
	public void testSameSessionIdsECPProjectManager() {
		MockSessionProvider.getInstance();
		MockSessionProvider.
		setSessionProvider(SessionProviderType.SAME_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPProjectManager> serviceReference =
			bundleContext.
			getServiceReference(ECPProjectManager.class);

		final ECPProjectManager service1 =
			bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPProjectManager service2 =
			bundleContext.getService(serviceReference);
		assertSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPObserverBusFactory is asked for
	 * a ECPObserverBus from 2 different sessions, that 2 different
	 * ECPObserverBus's are returned.
	 */
	@Test
	public final void testDifferentSessionIdsECPObserverBus() {
		MockSessionProvider.
		getInstance();
		MockSessionProvider.
			setSessionProvider(SessionProviderType.DIFFERENT_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPObserverBus> serviceReference =
			bundleContext.getServiceReference(ECPObserverBus.class);

		final ECPObserverBus service1 =
			bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPObserverBus service2 =
			bundleContext.getService(serviceReference);
		assertNotSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPObserverBusFactory is asked for
	 * a ECPObserverBus 2 times from the same session,
	 * that the same ECPObserverBus is returned.
	 */
	@Test
	public final void testSameSessionIdsECPObserverBus() {
		MockSessionProvider.
		getInstance();
		MockSessionProvider.
			setSessionProvider(SessionProviderType.SAME_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPObserverBus> serviceReference =
			bundleContext.getServiceReference(ECPObserverBus.class);

		final ECPObserverBus service1 =
			bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPObserverBus service2 =
			bundleContext.getService(serviceReference);
		assertSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPProviderRegistryFactory is asked for
	 * a ECPProviderRegistry from 2 different sessions, that 2 different
	 * ECPProviderRegistry's are returned.
	 */
	@Test
	public final void testDifferentSessionIdsECPProviderRegistry() {
		MockSessionProvider.
		getInstance();
		MockSessionProvider.
			setSessionProvider(SessionProviderType.DIFFERENT_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPProviderRegistry> serviceReference =
			bundleContext.
			getServiceReference(ECPProviderRegistry.class);

		final ECPProviderRegistry service1 = bundleContext.
			getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPProviderRegistry service2 =
			bundleContext.getService(serviceReference);
		assertNotSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPProviderRegistryFactory is asked for
	 * a ECPProviderRegistry 2 times from the same session,
	 * that the same ECPProviderRegistry is returned.
	 */
	@Test
	public final void testSameSessionIdsECPProviderRegistry() {
		MockSessionProvider.
		getInstance();
		MockSessionProvider.
			setSessionProvider(SessionProviderType.SAME_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPProviderRegistry> serviceReference =
			bundleContext.getServiceReference(ECPProviderRegistry.class);

		final ECPProviderRegistry service1 =
			bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPProviderRegistry service2 =
			bundleContext.getService(serviceReference);
		assertSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPRepositoryManagerFactory is asked for
	 * a ECPRepositoryManager from 2 different sessions, that 2 different
	 * ECPRepositoryManagers are returned.
	 */
	@Test
	public final void testDifferentSessionIdsECPRepositoryManager() {
		MockSessionProvider.
		getInstance();
		MockSessionProvider.
			setSessionProvider(SessionProviderType.DIFFERENT_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPRepositoryManager> serviceReference =
			bundleContext.getServiceReference(ECPRepositoryManager.class);

		final ECPRepositoryManager service1 =
			bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPRepositoryManager service2 =
			bundleContext.getService(serviceReference);
		assertNotSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

	/**
	 * Test that when the ECPRepositoryManagerFactory is asked for
	 * a ECPRepositoryManager 2 times from the same session,
	 * that the same ECPRepositoryManager is returned.
	 */
	@Test
	public final void testSameSessionIdsECPRepositoryManager() {
		MockSessionProvider.
		getInstance();
		MockSessionProvider.
			setSessionProvider(SessionProviderType.SAME_SESSION_ID);
		final BundleContext bundleContext =
			FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<ECPRepositoryManager> serviceReference =
			bundleContext.
			getServiceReference(ECPRepositoryManager.class);

		final ECPRepositoryManager service1 =
			bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);

		final ECPRepositoryManager service2 =
			bundleContext.getService(serviceReference);
		assertSame(service1, service2);
		bundleContext.ungetService(serviceReference);
	}

}
