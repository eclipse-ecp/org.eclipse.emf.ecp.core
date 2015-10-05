/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl;
import org.eclipse.emf.ecp.changebroker.spi.PostDeleteObserver;
import org.eclipse.emf.ecp.changebroker.spi.PreDeleteObserver;
import org.eclipse.emf.ecp.changebroker.spi.VetoableDeleteObserver;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jonas
 *
 */
public class ChangeBrokerDeleteObserver_Test {

	private ChangeBrokerImpl broker;
	private EObject receivedPreDeleteObject;

	@Before
	public void before() {
		broker = new ChangeBrokerImpl();
		receivedPreDeleteObject = null;
	}

	@Test
	public void testOnePreDeleteObserver() {
		final EObject toBeDeleted = BowlingFactory.eINSTANCE.createTournament();
		subscribePreDeleteObserver();
		broker.notifyPreDelete(toBeDeleted);
		assertSame(toBeDeleted, receivedPreDeleteObject);

	}

	@Test
	public void testUnsubscribeOnePreDeleteObserver() {
		final EObject toBeDeleted = BowlingFactory.eINSTANCE.createTournament();
		final PreDeleteObserver predeleteObserver = subscribePreDeleteObserver();
		broker.unsubsribe(predeleteObserver);
		broker.notifyPreDelete(toBeDeleted);
		assertSame(null, receivedPreDeleteObject);

	}

	private PreDeleteObserver subscribePreDeleteObserver() {
		final PreDeleteObserver preDeleteObserver = new PreDeleteObserver() {

			@Override
			public void preDelete(EObject objectToBeDeleted) {
				receivedPreDeleteObject = objectToBeDeleted;

			}
		};
		broker.subscribe(preDeleteObserver);
		return preDeleteObserver;
	}

	@Test
	public void testOnePostDeleteObserver() {
		final EObject toBeDeleted = BowlingFactory.eINSTANCE.createTournament();
		subscribePostDeleteObserver();
		broker.notifyPostDelete(toBeDeleted);
		assertSame(toBeDeleted, receivedPreDeleteObject);

	}

	@Test
	public void testUnsubscribeOnePostDeleteObserver() {
		final EObject toBeDeleted = BowlingFactory.eINSTANCE.createTournament();
		final PostDeleteObserver postdeleteObserver = subscribePostDeleteObserver();
		broker.unsubsribe(postdeleteObserver);
		broker.notifyPostDelete(toBeDeleted);
		assertSame(null, receivedPreDeleteObject);

	}

	private PostDeleteObserver subscribePostDeleteObserver() {
		final PostDeleteObserver postDeleteObserver = new PostDeleteObserver() {

			@Override
			public void postDelete(EObject objectToBeDeleted) {
				receivedPreDeleteObject = objectToBeDeleted;

			}
		};
		broker.subscribe(postDeleteObserver);
		return postDeleteObserver;
	}

	@Test
	public void testOneVetoableDeleteObserver() {
		final EObject toBeDeleted = BowlingFactory.eINSTANCE.createTournament();
		subscribeVetoableDeleteListener();
		final boolean canDelete = broker.canDelete(toBeDeleted);
		assertFalse(canDelete);
		assertSame(toBeDeleted, receivedPreDeleteObject);

	}

	@Test
	public void testUnsubscribeOneVetoableDeleteObserver() {
		final EObject toBeDeleted = BowlingFactory.eINSTANCE.createTournament();
		final VetoableDeleteObserver vetoableDeleteListener = subscribeVetoableDeleteListener();
		broker.unsubsribe(vetoableDeleteListener);
		final boolean canDelete = broker.canDelete(toBeDeleted);
		assertTrue(canDelete);
		assertSame(null, receivedPreDeleteObject);

	}

	private VetoableDeleteObserver subscribeVetoableDeleteListener() {
		final VetoableDeleteObserver vetoableDeleteObserver = new VetoableDeleteObserver() {

			@Override
			public boolean canDelete(EObject objectToBeDeleted) {
				receivedPreDeleteObject = objectToBeDeleted;
				return false;
			}
		};
		broker.subscribe(vetoableDeleteObserver);
		return vetoableDeleteObserver;
	}

}
