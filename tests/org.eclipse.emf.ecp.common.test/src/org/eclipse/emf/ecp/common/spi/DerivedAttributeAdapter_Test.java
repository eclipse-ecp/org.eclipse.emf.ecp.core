/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecp.common.spi.DerivedAttributeAdapter.NavigationAdapter;
import org.eclipse.emf.ecp.common.test.model.Base;
import org.eclipse.emf.ecp.common.test.model.Test1;
import org.eclipse.emf.ecp.common.test.model.Test2;
import org.eclipse.emf.ecp.common.test.model.Test3;
import org.eclipse.emf.ecp.common.test.model.Test4;
import org.eclipse.emf.ecp.common.test.model.TestFactory;
import org.eclipse.emf.ecp.common.test.model.TestPackage;
import org.junit.Test;

public class DerivedAttributeAdapter_Test {

	private static final String VALUE1 = "value1";
	private static final String VALUE2 = "value2";

	@Test
	public void testLocalDependencySingle() {
		final Test1 test1 = TestFactory.eINSTANCE.createTest1();
		final TestContentAdapter testContentAdapter = new TestContentAdapter(test1,
			TestPackage.eINSTANCE.getTest1_Derived());

		test1.setSingleAttribute(VALUE1);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		test1.setDerived(VALUE2);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, VALUE1, VALUE2, testContentAdapter.getRecordedNotifications().get(0));
	}

	@Test
	public void testLocalDependencySingleUnsettable() {
		final Test2 test2 = TestFactory.eINSTANCE.createTest2();
		final TestContentAdapter testContentAdapter = new TestContentAdapter(test2,
			TestPackage.eINSTANCE.getTest2_Derived());

		test2.setSingleAttributeUnsettable(VALUE1);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		test2.unsetSingleAttributeUnsettable();
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, VALUE1, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		test2.setDerived(VALUE1);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		test2.unsetDerived();
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, VALUE1, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();
	}

	// BEGIN COMPLEX CODE
	@Test
	public void testNavigationAdapterAddingAndDisposal() {
		final Test3 test3 = TestFactory.eINSTANCE.createTest3();
		NavigationAdapter navigationAdapter = getNavigationAdapter(test3);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, test3.eAdapters().size()); // DAA + DAA.NA

		/* add middle child */
		final Base child11 = TestFactory.eINSTANCE.createBase();
		test3.setChild(child11);

		navigationAdapter = getNavigationAdapter(test3);
		assertNotNull(navigationAdapter);
		assertEquals(1, navigationAdapter.children().size());
		assertEquals(2, test3.eAdapters().size()); // DAA + DAA.NA

		navigationAdapter = getNavigationAdapter(child11);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(1, child11.eAdapters().size()); // DAA.NA

		/* add last child */
		final Base child21 = TestFactory.eINSTANCE.createBase();
		child11.setChild(child21);

		navigationAdapter = getNavigationAdapter(test3);
		assertNotNull(navigationAdapter);
		assertEquals(1, navigationAdapter.children().size());
		assertEquals(2, test3.eAdapters().size()); // DAA + DAA.NA

		navigationAdapter = getNavigationAdapter(child11);
		assertNotNull(navigationAdapter);
		assertEquals(1, navigationAdapter.children().size());
		assertEquals(1, child11.eAdapters().size()); // DAA.NA

		navigationAdapter = getNavigationAdapter(child21);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(1, child21.eAdapters().size()); // DAA.NA

		/* remove last child */
		child11.setChild(null);

		navigationAdapter = getNavigationAdapter(test3);
		assertNotNull(navigationAdapter);
		assertEquals(1, navigationAdapter.children().size());
		assertEquals(2, test3.eAdapters().size()); // DAA + DAA.NA

		navigationAdapter = getNavigationAdapter(child11);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(1, child11.eAdapters().size()); // DAA.NA

		navigationAdapter = getNavigationAdapter(child21);
		assertNull(navigationAdapter);
		assertEquals(0, child21.eAdapters().size());

		/* remove middle child */
		test3.setChild(null);

		navigationAdapter = getNavigationAdapter(test3);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, test3.eAdapters().size()); // DAA + DAA.NA

		navigationAdapter = getNavigationAdapter(child11);
		assertNull(navigationAdapter);
		assertEquals(0, child11.eAdapters().size());

		/* add middle AND last child */
		child11.setChild(child21);
		test3.setChild(child11);

		navigationAdapter = getNavigationAdapter(test3);
		assertNotNull(navigationAdapter);
		assertEquals(1, navigationAdapter.children().size());
		assertEquals(2, test3.eAdapters().size()); // DAA + DAA.NA

		navigationAdapter = getNavigationAdapter(child11);
		assertNotNull(navigationAdapter);
		assertEquals(1, navigationAdapter.children().size());
		assertEquals(1, child11.eAdapters().size()); // DAA.NA

		navigationAdapter = getNavigationAdapter(child21);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(1, child21.eAdapters().size()); // DAA.NA

		/* remove middle AND last child */
		test3.setChild(null);

		navigationAdapter = getNavigationAdapter(test3);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, test3.eAdapters().size()); // DAA + DAA.NA

		navigationAdapter = getNavigationAdapter(child11);
		assertNull(navigationAdapter);
		assertEquals(0, child11.eAdapters().size());

		navigationAdapter = getNavigationAdapter(child21);
		assertNull(navigationAdapter);
		assertEquals(0, child21.eAdapters().size());
	}

	// END COMPLEX CODE
	@Test
	public void testNavigatedSingleUnsettable() {
		final Test3 test3 = TestFactory.eINSTANCE.createTest3();
		final Base child11 = TestFactory.eINSTANCE.createBase();
		final Base child21 = TestFactory.eINSTANCE.createBase();
		child11.setChild(child21);
		test3.setChild(child11);
		final TestContentAdapter testContentAdapter = new TestContentAdapter(test3,
			TestPackage.eINSTANCE.getTest3_Derived());

		/* test set behaviour when full child hierarchy */
		child21.setSingleAttributeUnsettable(VALUE1);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		child21.unsetSingleAttributeUnsettable();
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, VALUE1, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		test3.setDerived(VALUE1);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		test3.unsetDerived();
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, VALUE1, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		test3.setDerived(VALUE1);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		/* change navigation path last child */
		child11.setChild(null);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, VALUE1, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();
		assertEquals(0, child21.eAdapters().size());

		final Base child22 = TestFactory.eINSTANCE.createBase();
		child22.setSingleAttributeUnsettable(null);
		child11.setChild(child22);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();
		assertEquals(2, child22.eAdapters().size()); // 2 = content adapter for test + derived adapter

		/* change navigation path middle child */
		final Base child12 = TestFactory.eINSTANCE.createBase();
		final Base child23 = TestFactory.eINSTANCE.createBase();
		child12.setChild(child23);
		test3.setChild(child12);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, null, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();
		assertEquals(0, child11.eAdapters().size());
		assertEquals(0, child22.eAdapters().size());
		assertEquals(2, child12.eAdapters().size()); // 2 = content adapter for test + derived adapter
		assertEquals(2, child23.eAdapters().size()); // 2 = content adapter for test + derived adapter

	}

	// BEGIN COMPLEX CODE
	@Test
	public void testNavigationAdapterMulti() {
		final Test4 test4 = TestFactory.eINSTANCE.createTest4();
		NavigationAdapter navigationAdapter = getNavigationAdapter(test4);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(3, test4.eAdapters().size()); // DAA + 2xDAA.NA

		/* add to list */
		final Base base1 = TestFactory.eINSTANCE.createBase();
		test4.getChildren().add(base1);

		navigationAdapter = getNavigationAdapter(test4);
		assertNotNull(navigationAdapter);
		assertEquals(1, navigationAdapter.children().size());
		assertEquals(3, test4.eAdapters().size()); // DAA + 2xDAA.NA

		navigationAdapter = getNavigationAdapter(base1);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, base1.eAdapters().size()); // 2xDAA.NA

		/* Add many to list */
		final Base base2 = TestFactory.eINSTANCE.createBase();
		final Base base3 = TestFactory.eINSTANCE.createBase();
		test4.getChildren().addAll(Arrays.asList(base2, base3));

		navigationAdapter = getNavigationAdapter(test4);
		assertNotNull(navigationAdapter);
		assertEquals(3, navigationAdapter.children().size());
		assertEquals(3, test4.eAdapters().size()); // DAA + 2xDAA.NA

		navigationAdapter = getNavigationAdapter(base1);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, base1.eAdapters().size()); // 2xDAA.NA

		navigationAdapter = getNavigationAdapter(base2);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, base2.eAdapters().size()); // 2xDAA.NA

		navigationAdapter = getNavigationAdapter(base3);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, base3.eAdapters().size()); // 2xDAA.NA

		/* remove from list */
		test4.getChildren().remove(base1);

		navigationAdapter = getNavigationAdapter(test4);
		assertNotNull(navigationAdapter);
		assertEquals(2, navigationAdapter.children().size());
		assertEquals(3, test4.eAdapters().size()); // DAA + 2xDAA.NA

		navigationAdapter = getNavigationAdapter(base1);
		assertNull(navigationAdapter);
		assertEquals(0, base1.eAdapters().size());

		navigationAdapter = getNavigationAdapter(base2);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, base2.eAdapters().size()); // 2xDAA.NA

		navigationAdapter = getNavigationAdapter(base3);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(2, base3.eAdapters().size()); // 2xDAA.NA

		/* remove many from list */
		test4.getChildren().removeAll(Arrays.asList(base2, base3));

		navigationAdapter = getNavigationAdapter(test4);
		assertNotNull(navigationAdapter);
		assertEquals(0, navigationAdapter.children().size());
		assertEquals(3, test4.eAdapters().size()); // DAA + 2xDAA.NA

		navigationAdapter = getNavigationAdapter(base1);
		assertNull(navigationAdapter);
		assertEquals(0, base1.eAdapters().size());

		navigationAdapter = getNavigationAdapter(base2);
		assertNull(navigationAdapter);
		assertEquals(0, base2.eAdapters().size());

		navigationAdapter = getNavigationAdapter(base3);
		assertNull(navigationAdapter);
		assertEquals(0, base3.eAdapters().size());
	}
	// END COMPLEX CODE

	@Test
	public void testMultiNavigationKeyValueCase() {
		final Test4 test4 = TestFactory.eINSTANCE.createTest4();
		final TestContentAdapter testContentAdapter = new TestContentAdapter(test4,
			TestPackage.eINSTANCE.getTest4_Derived());

		/* add unrelated key */
		final Base keyVal01 = TestFactory.eINSTANCE.createBase();
		test4.getChildren().add(keyVal01);
		assertEquals(2, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, null, null, testContentAdapter.getRecordedNotifications().get(0));
		assertNotification(Notification.UNSET, null, null, testContentAdapter.getRecordedNotifications().get(1));
		testContentAdapter.clearRecordedNotifications();

		/* change unrelated value */
		keyVal01.setSingleAttribute(VALUE1);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, null, null, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		/* change unrelated key to right */
		keyVal01.setSingleAttributeUnsettable(Test4.KEY);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		/* change value */
		keyVal01.setSingleAttribute(VALUE2);
		assertEquals(1, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, VALUE1, VALUE2, testContentAdapter.getRecordedNotifications().get(0));
		testContentAdapter.clearRecordedNotifications();

		/* remove key value */
		test4.getChildren().remove(keyVal01);
		assertEquals(2, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.UNSET, VALUE2, null, testContentAdapter.getRecordedNotifications().get(0));
		assertNotification(Notification.UNSET, null, null, testContentAdapter.getRecordedNotifications().get(1));
		testContentAdapter.clearRecordedNotifications();

		/* set derived */
		test4.setDerived(VALUE1);
		assertEquals(2, testContentAdapter.getRecordedNotifications().size());
		assertNotification(Notification.SET, null, VALUE1, testContentAdapter.getRecordedNotifications().get(0));
		assertNotification(Notification.SET, VALUE1, VALUE1, testContentAdapter.getRecordedNotifications().get(1));
		testContentAdapter.clearRecordedNotifications();

	}

	private static void assertNotification(int eventType, Object oldValue, Object newValue, Notification notification) {
		assertEquals(eventType, notification.getEventType());
		assertEquals(oldValue, notification.getOldValue());
		assertEquals(newValue, notification.getNewValue());
	}

	private static DerivedAttributeAdapter.NavigationAdapter getNavigationAdapter(EObject eObject) {
		for (final Adapter adapter : eObject.eAdapters()) {
			if (DerivedAttributeAdapter.NavigationAdapter.class.isInstance(adapter)) {
				return (NavigationAdapter) adapter;
			}
		}
		return null;
	}

	private static final class TestContentAdapter extends EContentAdapter {

		private final List<Notification> recordedNotifications = new ArrayList<Notification>();

		private final EObject filterNotifier;
		private final EStructuralFeature filterFeature;

		TestContentAdapter(EObject filterNotifier, EStructuralFeature filterFeature) {
			this.filterNotifier = filterNotifier;
			this.filterFeature = filterFeature;
			filterNotifier.eAdapters().add(this);
		}

		List<Notification> getRecordedNotifications() {
			return recordedNotifications;
		}

		void clearRecordedNotifications() {
			recordedNotifications.clear();
		}

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (filterNotifier != notification.getNotifier()) {
				return;
			}
			if (filterFeature != notification.getFeature()) {
				return;
			}
			recordedNotifications.add(notification);
		}
	}

}
