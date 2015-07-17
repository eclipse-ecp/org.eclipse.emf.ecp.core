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
package org.eclipse.emf.ecp.changebroker.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl;
import org.eclipse.emf.ecp.changebroker.spi.AbstractNotificationProvider;
import org.eclipse.emf.ecp.changebroker.spi.EMFObserver;
import org.eclipse.emf.ecp.changebroker.spi.ReadOnlyEMFObserver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all strategies.
 *
 * @author jfaltermeier
 *
 */
public class Strategy_Test {

	private EMFObserver emfObserver;
	private ReadOnlyEMFObserver readOnlyEMFObserver;
	private ChangeBrokerImpl broker;
	private Set<EMFObserver> observers;
	private TestNotificationProvider notificationProvider;

	@Before
	public void before() {
		broker = new ChangeBrokerImpl();
		observers = new LinkedHashSet<EMFObserver>();
		emfObserver = new EMFObserver() {
			@Override
			public void handleNotification(Notification notification) {
				observers.add(this);
			}
		};
		readOnlyEMFObserver = new ReadOnlyEMFObserver() {
			@Override
			public void handleNotification(Notification notification) {
				observers.add(this);
			}
		};
		notificationProvider = new TestNotificationProvider();
		broker.addNotificationProvider(notificationProvider);
	}

	@Test
	public void testNoStrategyGetObservers() {
		// setup
		broker.subscribe(emfObserver);
		broker.subscribe(readOnlyEMFObserver);

		// act
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(EcoreFactory.eINSTANCE.createEAttribute());
		notifyBroker(notification);

		// assert
		assertEquals(2, observers.size());
		assertTrue(observers.contains(emfObserver));
		assertTrue(observers.contains(readOnlyEMFObserver));
	}

	@Test
	public void testEClassStrategyGetObserversRightEClass() {
		// setup

		broker.subscribeToEClass(emfObserver, EcorePackage.eINSTANCE.getEAttribute());
		broker.subscribeToEClass(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEAttribute());

		// act
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(EcoreFactory.eINSTANCE.createEAttribute());
		notifyBroker(notification);

		// assert
		assertEquals(2, observers.size());
		assertTrue(observers.contains(emfObserver));
		assertTrue(observers.contains(readOnlyEMFObserver));
	}

	@Test
	public void testEClassStrategyGetObserversWrongEClass() {
		// setup

		broker.subscribeToEClass(emfObserver, EcorePackage.eINSTANCE.getEAttribute());
		broker.subscribeToEClass(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEAttribute());

		// act
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(EcoreFactory.eINSTANCE.createEReference());
		notifyBroker(notification);

		// assert
		assertEquals(0, observers.size());
	}

	@Test
	public void testEClassStrategyGetObserversSuperEClass() {
		// setup

		broker.subscribeToEClass(emfObserver, EcorePackage.eINSTANCE.getEStructuralFeature());
		broker.subscribeToEClass(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEStructuralFeature());

		// act
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(EcoreFactory.eINSTANCE.createEReference());
		notifyBroker(notification);

		// assert
		assertEquals(2, observers.size());
		assertTrue(observers.contains(emfObserver));
		assertTrue(observers.contains(readOnlyEMFObserver));
	}

	@Test
	public void testEClassStrategyGetObserversOnlyReadOnlyWrongEClass() {
		// setup

		broker.subscribeToEClass(emfObserver, EcorePackage.eINSTANCE.getEAttribute());
		broker.subscribeToEClass(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEAttribute());

		// act
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(EcoreFactory.eINSTANCE.createEReference());
		notifyBroker(notification);

		// assert
		assertEquals(0, observers.size());
	}

	@Test
	public void testContainingEClassStrategyGetObserversRightEClass() {
		// setup

		broker.subscribeToTree(emfObserver, EcorePackage.eINSTANCE.getEAttribute());
		broker.subscribeToTree(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEAttribute());

		// act
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		eAttribute.getEAnnotations().add(eAnnotation);
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(eAnnotation);
		notifyBroker(notification);

		// assert
		assertEquals(2, observers.size());
		assertTrue(observers.contains(emfObserver));
		assertTrue(observers.contains(readOnlyEMFObserver));
	}

	@Test
	public void testContainingEClassStrategyGetObserversWrongEClass() {
		// setup

		broker.subscribeToTree(emfObserver, EcorePackage.eINSTANCE.getEReference());
		broker.subscribeToTree(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEReference());

		// act
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		eAttribute.getEAnnotations().add(eAnnotation);
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(eAnnotation);
		notifyBroker(notification);

		// assert
		assertEquals(0, observers.size());
	}

	@Test
	public void testContainingEClassStrategyGetObserversSuperEClass() {
		// setup

		broker.subscribeToTree(emfObserver, EcorePackage.eINSTANCE.getEStructuralFeature());
		broker.subscribeToTree(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEStructuralFeature());

		// act
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		eAttribute.getEAnnotations().add(eAnnotation);
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(eAnnotation);
		notifyBroker(notification);

		// assert
		assertEquals(2, observers.size());
		assertTrue(observers.contains(emfObserver));
		assertTrue(observers.contains(readOnlyEMFObserver));
	}

	@Test
	public void testContainingEClassStrategyGetObserversOnlyReadOnlyWrongEClass() {
		// setup

		broker.subscribeToTree(emfObserver, EcorePackage.eINSTANCE.getEReference());
		broker.subscribeToTree(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEReference());

		// act
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		final EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		eAttribute.getEAnnotations().add(eAnnotation);
		final Notification notification = mock(Notification.class);
		when(notification.getNotifier()).thenReturn(eAnnotation);
		notifyBroker(notification);

		// assert
		assertEquals(0, observers.size());
	}

	@Test
	public void testFeatureStrategyGetObserversRightFeature() {
		// setup

		broker.subscribeToFeature(emfObserver, EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
		broker.subscribeToFeature(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEModelElement_EAnnotations());

		// act
		final Notification notification = mock(Notification.class);
		when(notification.getFeature()).thenReturn(EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
		when(notification.getNotifier()).thenReturn(EcoreFactory.eINSTANCE.createEReference());
		notifyBroker(notification);

		// assert
		assertEquals(2, observers.size());
		assertTrue(observers.contains(emfObserver));
		assertTrue(observers.contains(readOnlyEMFObserver));
	}

	@Test
	public void testFeatureStrategyGetObserversWrongFeature() {
		// setup

		broker.subscribeToFeature(emfObserver, EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
		broker.subscribeToFeature(readOnlyEMFObserver, EcorePackage.eINSTANCE.getEModelElement_EAnnotations());

		// act
		final Notification notification = mock(Notification.class);
		when(notification.getFeature()).thenReturn(EcorePackage.eINSTANCE.getEStructuralFeature_Changeable());
		when(notification.getNotifier()).thenReturn(EcoreFactory.eINSTANCE.createEReference());
		notifyBroker(notification);

		// assert
		assertEquals(0, observers.size());
	}

	private void notifyBroker(Notification notification) {
		notificationProvider.notifyAll(notification);
	}

	private class TestNotificationProvider extends AbstractNotificationProvider {
		public void notifyAll(Notification notification) {
			notifyAllReceivers(notification);
		}
	}

}
