/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.provider.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.changebroker.provider.internal.ECPNotificationProvider;
import org.eclipse.emf.ecp.changebroker.spi.ChangeBroker;
import org.eclipse.emf.ecp.changebroker.spi.ChangeObserver;
import org.eclipse.emf.ecp.changebroker.spi.NotificationProvider;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.core.DefaultProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.ProviderChangeListener;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Tests the integration with the ChangeBroker service
 *
 * @author jfaltermeier
 *
 */
@SuppressWarnings("restriction")
public class ChangeBroker_ITest {

	/**
	 * @author Jonas
	 *
	 */
	private final class ProviderChangeListenerMock implements ProviderChangeListener {
		private final LinkedHashSet<EObject> objectWhichCanBeDeleted;

		/**
		 * @param objectWhichCanBeDeleted
		 */
		private ProviderChangeListenerMock(LinkedHashSet<EObject> objectWhichCanBeDeleted) {
			this.objectWhichCanBeDeleted = objectWhichCanBeDeleted;

		}

		@Override
		public void notify(Notification notification) {
			fail("no notify expected on delete");
		}

		@Override
		public void postDelete(EObject objectToBeDeleted) {
			calledMethods = calledMethods + postDelete;
		}

		@Override
		public void preDelete(EObject objectToBeDeleted) {
			calledMethods = calledMethods + preDelete;
		}

		@Override
		public boolean canDelete(EObject objectToBeDeleted) {
			calledMethods = calledMethods + canDelete;
			return objectWhichCanBeDeleted.contains(objectToBeDeleted);
		}
	}

	/**
	 * @author Jonas
	 *
	 */
	private final class DefaultProviderMock extends DefaultProvider {

		/**
		 * @param name
		 */
		private DefaultProviderMock(String name) {
			super(name);
		}

		@Override
		public boolean isThreadSafe() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Notifier getRoot(InternalProject project) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EList<? extends Object> getElements(InternalProject project) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void cloneProject(InternalProject projectToClone, InternalProject targetProject) {
			// TODO Auto-generated method stub

		}

		private final List<Object> deletedObjects = new ArrayList<Object>();

		@Override
		public void doDelete(InternalProject project, Collection<Object> objects) {
			getDeletedObjects().addAll(objects);

		}

		/**
		 * @return the deletedObjects
		 */
		public List<Object> getDeletedObjects() {
			return deletedObjects;
		}

	}

	@Test
	public void testServiceAvailable() {
		assertTrue(org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl.class.isInstance(broker));
		final org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl brokerImpl = org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl.class
			.cast(broker);
		final Set<NotificationProvider> notificationProviders = brokerImpl.getNotificationProviders();
		assertEquals(1, notificationProviders.size());
		assertTrue(ECPNotificationProvider.class.isInstance(notificationProviders.iterator().next()));
	}

	@Before
	public void setUp() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		final ServiceReference<ChangeBroker> serviceReference = bundleContext.getServiceReference(ChangeBroker.class);
		assertNotNull("Null service reference", serviceReference); //$NON-NLS-1$
		broker = bundleContext.getService(serviceReference);
		assertNotNull("ChangeBroker is null", broker); //$NON-NLS-1$
	}

	private Notification receivedNotification;
	private ChangeBroker broker;

	@Test
	public void testWorkspaceProviderIntegration() throws ECPProjectWithNameExistsException {
		final ECPProvider provider = ECPUtil.getECPProviderRegistry().getProvider(WorkspaceProvider.NAME);
		final ECPProperties properties = ECPUtil.createProperties();
		properties.addProperty(WorkspaceProvider.PROP_ROOT_URI, WorkspaceProvider.VIRTUAL_ROOT_URI);
		final ECPProject project = ECPUtil.getECPProjectManager().createProject(provider, "TestProject", properties);
		final EClass testEObject = EcoreFactory.eINSTANCE.createEClass();
		project.getContents().add(testEObject);
		broker.subscribe(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				receivedNotification = notification;

			}
		});
		final String testName = "testName";
		testEObject.setName(testName);
		assertNotNull(receivedNotification);
		assertEquals(testEObject, receivedNotification.getNotifier());
		assertEquals(EcorePackage.eINSTANCE.getENamedElement_Name(), receivedNotification.getFeature());
		assertEquals(testName, receivedNotification.getNewValue());
	}

	private String calledMethods = "";
	private final String postDelete = "postDelete";
	private final String preDelete = "preDelete";
	private final String canDelete = "canDelete";

	@Test
	public void testDeleteNotificationCanDelete() {
		final DefaultProviderMock providerMock = new DefaultProviderMock("testProvider");
		final EClass toBeDeletedEObject = EcoreFactory.eINSTANCE.createEClass();
		final LinkedHashSet<EObject> objectWhichCanBeDeleted = new LinkedHashSet<EObject>();
		objectWhichCanBeDeleted.add(toBeDeletedEObject);
		providerMock
			.registerChangeListener(new ProviderChangeListenerMock(objectWhichCanBeDeleted));
		final Set<Object> toBeDeleted = new LinkedHashSet<Object>();
		toBeDeleted.add(toBeDeletedEObject);
		final InternalProject project = null;
		providerMock.delete(project, toBeDeleted);
		assertEquals(canDelete + preDelete + postDelete, calledMethods);
		assertEquals(1, providerMock.getDeletedObjects().size());
		assertTrue(providerMock.getDeletedObjects().contains(toBeDeletedEObject));
	}

	@Test
	public void testDeleteNotificationCannotDelete() {
		final DefaultProviderMock providerMock = new DefaultProviderMock("testProvider");
		final EClass toBeDeletedEObject = EcoreFactory.eINSTANCE.createEClass();
		final LinkedHashSet<EObject> objectWhichCanBeDeleted = new LinkedHashSet<EObject>();
		providerMock
			.registerChangeListener(new ProviderChangeListenerMock(objectWhichCanBeDeleted));
		final Set<Object> toBeDeleted = new LinkedHashSet<Object>();
		toBeDeleted.add(toBeDeletedEObject);
		final InternalProject project = null;
		providerMock.delete(project, toBeDeleted);
		assertEquals(canDelete, calledMethods);
		assertEquals(0, providerMock.getDeletedObjects().size());
		assertFalse(providerMock.getDeletedObjects().contains(toBeDeletedEObject));
	}

	@Test
	public void testDeleteNotificationCanAndCannotDelete() {
		final DefaultProviderMock providerMock = new DefaultProviderMock("testProvider");
		final EClass toBeDeletedEObject1 = EcoreFactory.eINSTANCE.createEClass();
		final EClass toBeDeletedEObject2 = EcoreFactory.eINSTANCE.createEClass();
		final LinkedHashSet<EObject> objectWhichCanBeDeleted = new LinkedHashSet<EObject>();
		objectWhichCanBeDeleted.add(toBeDeletedEObject1);
		providerMock
			.registerChangeListener(new ProviderChangeListenerMock(objectWhichCanBeDeleted));
		final Set<Object> toBeDeleted = new LinkedHashSet<Object>();
		toBeDeleted.add(toBeDeletedEObject1);
		toBeDeleted.add(toBeDeletedEObject2);
		final InternalProject project = null;
		providerMock.delete(project, toBeDeleted);
		assertEquals(canDelete + preDelete + canDelete + postDelete, calledMethods);
		assertEquals(1, providerMock.getDeletedObjects().size());
		assertTrue(providerMock.getDeletedObjects().contains(toBeDeletedEObject1));
	}

	@Test
	public void testDeleteNotificationEObjectAndObject() {
		final DefaultProviderMock providerMock = new DefaultProviderMock("testProvider");
		final EClass toBeDeletedEObject1 = EcoreFactory.eINSTANCE.createEClass();
		final LinkedHashSet<EObject> objectWhichCanBeDeleted = new LinkedHashSet<EObject>();
		providerMock
			.registerChangeListener(new ProviderChangeListenerMock(objectWhichCanBeDeleted));
		final Set<Object> toBeDeleted = new LinkedHashSet<Object>();
		toBeDeleted.add(toBeDeletedEObject1);
		final Object otherObjectToBeDeleted = "";
		toBeDeleted.add(otherObjectToBeDeleted);
		final InternalProject project = null;
		providerMock.delete(project, toBeDeleted);
		assertEquals(canDelete, calledMethods);
		assertEquals(1, providerMock.getDeletedObjects().size());
		assertTrue(providerMock.getDeletedObjects().contains(otherObjectToBeDeleted));
	}

}
