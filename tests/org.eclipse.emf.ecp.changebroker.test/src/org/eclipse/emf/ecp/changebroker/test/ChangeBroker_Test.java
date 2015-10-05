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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl;
import org.eclipse.emf.ecp.changebroker.spi.ChangeObserver;
import org.eclipse.emf.ecp.changebroker.spi.NotificationProvider;
import org.eclipse.emf.ecp.changebroker.spi.ReadOnlyChangeObserver;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Matchup;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.eclipse.emf.emfstore.bowling.TournamentType;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jfaltermeier
 *
 */
public class ChangeBroker_Test {

	private ChangeBrokerImpl broker;
	private Tournament tournament;
	private ContentAdapterNotificationProvider provider;

	@Before
	public void before() {
		broker = new ChangeBrokerImpl();
		tournament = BowlingFactory.eINSTANCE.createTournament();
		provider = new ContentAdapterNotificationProvider(tournament);
		broker.addNotificationProvider(provider);
	}

	@Test
	public void testMultipleEMFObserver() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		broker.subscribe(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch1.countDown();
			}
		});
		broker.subscribe(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch2.countDown();
			}
		});

		// act
		tournament.getMatchups().add(matchup);

		// assert
		assertTrue("Notify was not called", latch1.await(1, TimeUnit.MILLISECONDS));
		assertTrue("Notify was not called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRemoveObserver() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		broker.subscribe(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch.countDown();
			}
		});

		// act
		broker.removeNotificationProvider(provider);
		tournament.getMatchups().add(matchup);

		// assert
		assertFalse("Notify was called although it shouln't be called", latch.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testAddSameEMFObserverTwiceAndRemove() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		broker.subscribe(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch.countDown();
			}
		});
		broker.addNotificationProvider(provider);
		broker.removeNotificationProvider(provider);

		// act
		tournament.getMatchups().add(matchup);

		// assert
		assertFalse("Notify was called although it shouln't be called", latch.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRemoveUnregisteredObserver() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		broker.subscribe(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch.countDown();
			}
		});

		// act

		broker.removeNotificationProvider(new ContentAdapterNotificationProvider(tournament));
		tournament.getMatchups().add(matchup);

		// assert
		assertTrue("Notify was not called", latch.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testEMFObserverNoStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		broker.subscribe(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				assertSame(Notification.ADD, notification.getEventType());
				assertSame(BowlingPackage.eINSTANCE.getTournament_Matchups(), notification.getFeature());
				assertSame(matchup, notification.getNewValue());
				latch.countDown();
			}
		});

		// act
		tournament.getMatchups().add(matchup);

		// assert
		assertTrue("Notify was not called", latch.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testEMFObserverEClassStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		tournament.getMatchups().add(matchup);
		broker.subscribeToEClass(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				if (notification.getNotifier() == tournament) {
					latch1.countDown();
				} else if (notification.getNotifier() == matchup) {
					latch2.countDown();
				}
			}
		}, BowlingPackage.eINSTANCE.getTournament());

		// act&assert
		tournament.setType(TournamentType.PRO);
		assertTrue(latch1.await(1, TimeUnit.MILLISECONDS));

		// act&assert
		matchup.setNrSpectators(new BigInteger("1"));
		assertFalse("Notify was called although it shouln't be called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testEMFObserverContainingEClassStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		tournament.getMatchups().add(matchup);
		broker.subscribeToTree(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				if (notification.getNotifier() == tournament) {
					latch1.countDown();
				} else if (notification.getNotifier() == matchup) {
					latch2.countDown();
				}
			}
		}, BowlingPackage.eINSTANCE.getTournament());

		// act&assert
		tournament.setType(TournamentType.PRO);
		assertTrue("Notify was not called", latch1.await(1, TimeUnit.MILLISECONDS));

		// act&assert
		matchup.setNrSpectators(new BigInteger("1"));
		assertTrue("Notify was not called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testIgnoreNotifactionsByChangesFromEMFObserver() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		tournament.getMatchups().add(matchup);
		broker.subscribeToEClass(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				matchup.setNrSpectators(new BigInteger("1"));
				latch1.countDown();

			}
		}, BowlingPackage.eINSTANCE.getTournament());
		broker.subscribeToEClass(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch2.countDown();

			}
		}, BowlingPackage.eINSTANCE.getMatchup());

		// act
		tournament.setType(TournamentType.PRO);

		// assert
		assertTrue("Notify was not called", latch1.await(1, TimeUnit.MILLISECONDS));
		assertFalse("Notify was called although it shouln't be called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testIgnoreNotifactionsByCallingStopNotification() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		broker.subscribeToEClass(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch1.countDown();
			}
		}, BowlingPackage.eINSTANCE.getTournament());
		broker.stopNotification();

		// act
		tournament.setType(TournamentType.PRO);

		// assert
		assertFalse("Notify was called although it shouln't be called", latch1.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testEMFObserverContinueNotification() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		broker.subscribeToEClass(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch1.countDown();
			}
		}, BowlingPackage.eINSTANCE.getTournament());

		broker.stopNotification();

		tournament.setType(TournamentType.PRO);
		assertFalse("Notify was called although it shouln't be called", latch1.await(1, TimeUnit.MILLISECONDS));

		// act
		broker.continueNotification();
		tournament.setType(TournamentType.AMATEUR);

		// assert
		assertTrue("Notify was not called", latch1.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testEMFObserverFeatureStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();

		broker.subscribeToFeature(new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				if (notification.getFeature() == BowlingPackage.eINSTANCE.getTournament_Type()) {
					latch1.countDown();
				} else if (notification.getFeature() == BowlingPackage.eINSTANCE.getTournament_Matchups()) {
					latch2.countDown();
				}
			}
		}, BowlingPackage.eINSTANCE.getTournament_Type());

		// act&assert
		tournament.setType(TournamentType.PRO);
		assertTrue(latch1.await(1, TimeUnit.MILLISECONDS));

		// act&assert
		tournament.getMatchups().add(matchup);
		assertFalse("Notify was called although it shouln't be called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRemoveObserverNoStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		final ChangeObserver receiver = new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch1.countDown();
			}
		};
		broker.subscribe(receiver);

		// act
		broker.unsubsribe(receiver);
		tournament.getMatchups().add(matchup);

		// assert
		assertFalse("Notify was called although it shouln't be called", latch1.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRemoveObserverEClassStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		final ChangeObserver receiver = new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch1.countDown();
			}
		};
		broker.subscribeToEClass(receiver, BowlingPackage.eINSTANCE.getTournament());

		// act
		broker.unsubsribe(receiver);
		tournament.getMatchups().add(matchup);

		// assert
		assertFalse("Notify was called although it shouln't be called", latch1.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRemoveObserverContainingEClassStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		final ChangeObserver receiver = new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch1.countDown();
			}
		};
		broker.subscribeToTree(receiver, BowlingPackage.eINSTANCE.getTournament());

		// act
		broker.unsubsribe(receiver);
		tournament.getMatchups().add(matchup);

		// assert
		assertFalse("Notify was called although it shouln't be called", latch1.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRemoveObserverFeatureStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		final ChangeObserver receiver = new ChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				latch1.countDown();
			}
		};
		broker.subscribeToFeature(receiver, BowlingPackage.eINSTANCE.getTournament_Matchups());

		// act
		broker.unsubsribe(receiver);
		tournament.getMatchups().add(matchup);

		// assert
		assertFalse("Notify was called although it shouln't be called", latch1.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testReadOnlyEMFObserverNoStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		broker.subscribe(new ReadOnlyChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				assertSame(Notification.ADD, notification.getEventType());
				assertSame(BowlingPackage.eINSTANCE.getTournament_Matchups(), notification.getFeature());
				assertSame(matchup, notification.getNewValue());
				latch.countDown();
			}
		});

		// act
		tournament.getMatchups().add(matchup);

		// assert
		assertTrue("Notify was not called", latch.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testReadOnlyEMFObserverEClassStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		tournament.getMatchups().add(matchup);
		broker.subscribeToEClass(new ReadOnlyChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				if (notification.getNotifier() == tournament) {
					latch1.countDown();
				} else if (notification.getNotifier() == matchup) {
					latch2.countDown();
				}
			}
		}, BowlingPackage.eINSTANCE.getTournament());

		// act&assert
		tournament.setType(TournamentType.PRO);
		assertTrue(latch1.await(1, TimeUnit.MILLISECONDS));

		// act&assert
		matchup.setNrSpectators(new BigInteger("1"));
		assertFalse("Notify was called although it shouln't be called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testReadOnlyEMFObserverContainingEClassStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();
		tournament.getMatchups().add(matchup);
		broker.subscribeToTree(new ReadOnlyChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				if (notification.getNotifier() == tournament) {
					latch1.countDown();
				} else if (notification.getNotifier() == matchup) {
					latch2.countDown();
				}
			}
		}, BowlingPackage.eINSTANCE.getTournament());

		// act&assert
		tournament.setType(TournamentType.PRO);
		assertTrue("Notify was not called", latch1.await(1, TimeUnit.MILLISECONDS));

		// act&assert
		matchup.setNrSpectators(new BigInteger("1"));
		assertTrue("Notify was not called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testReadOnlyEMFObserverFeatureStrategy() throws InterruptedException {
		// setup
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		final Matchup matchup = BowlingFactory.eINSTANCE.createMatchup();

		broker.subscribeToFeature(new ReadOnlyChangeObserver() {

			@Override
			public void handleNotification(Notification notification) {
				if (notification.getFeature() == BowlingPackage.eINSTANCE.getTournament_Type()) {
					latch1.countDown();
				} else if (notification.getFeature() == BowlingPackage.eINSTANCE.getTournament_Matchups()) {
					latch2.countDown();
				}
			}
		}, BowlingPackage.eINSTANCE.getTournament_Type());

		// act&assert
		tournament.setType(TournamentType.PRO);
		assertTrue(latch1.await(1, TimeUnit.MILLISECONDS));

		// act&assert
		tournament.getMatchups().add(matchup);
		assertFalse("Notify was called although it shouln't be called", latch2.await(1, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testReadOnlyObserversStillCalledDuringChangesFromEMFObserver() throws InterruptedException {
		final CountDownLatch latch1 = new CountDownLatch(2);
		final CountDownLatch latch2 = new CountDownLatch(2);

		broker.subscribe(new ChangeObserver() {
			@Override
			public void handleNotification(Notification notification) {
				tournament.getReceivesTrophy().add(Boolean.TRUE);
				latch1.countDown();
			}
		});
		broker.subscribe(new ReadOnlyChangeObserver() {
			@Override
			public void handleNotification(Notification notification) {
				latch2.countDown();
			}
		});

		tournament.setType(TournamentType.PRO);
		assertTrue(latch2.await(1, TimeUnit.MICROSECONDS));
		assertEquals(1, latch1.getCount());
	}

	@Test
	public void testReadOnlyObserversStillCalledWhenStopNotificationUsed() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);

		broker.subscribe(new ReadOnlyChangeObserver() {
			@Override
			public void handleNotification(Notification notification) {
				latch.countDown();
			}
		});
		broker.stopNotification();
		tournament.setType(TournamentType.PRO);
		assertTrue(latch.await(1, TimeUnit.MICROSECONDS));
	}

	@Test
	public void testReadOnlyObserversStillCalledDuringChangesFromEMFObserverComplex() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(5);
		addEMFObserverAddingTrophies();
		addEMFObserverAddingTrophies();
		broker.subscribe(new ReadOnlyChangeObserver() {
			@Override
			public void handleNotification(Notification notification) {
				latch.countDown();
			}
		});
		addEMFObserverAddingTrophies();
		addEMFObserverAddingTrophies();
		tournament.setType(TournamentType.PRO);
		assertTrue(latch.await(1, TimeUnit.MICROSECONDS));
	}

	private void addEMFObserverAddingTrophies() {
		broker.subscribe(new ChangeObserver() {
			@Override
			public void handleNotification(Notification notification) {
				tournament.getReceivesTrophy().add(Boolean.TRUE);
			}
		});
	}

	@Test
	public void testGetNotificationProviders() {
		final Set<NotificationProvider> notificationProviders = broker.getNotificationProviders();
		assertEquals(1, notificationProviders.size());
		assertTrue(notificationProviders.contains(provider));
	}

	@Test
	public void testGetRegisteredObservers() {
		final ReadOnlyChangeObserver obs1 = new ReadOnlyChangeObserver() {
			@Override
			public void handleNotification(Notification notification) {
				// no op
			}
		};
		final ChangeObserver obs2 = new ChangeObserver() {
			@Override
			public void handleNotification(Notification notification) {
				// no op
			}
		};
		broker.subscribe(obs1);
		broker.subscribeToEClass(obs2, BowlingPackage.eINSTANCE.getPlayer());
		final Set<ChangeObserver> registeredObservers = broker.getRegisteredObservers();
		assertEquals(2, registeredObservers.size());
		assertTrue(registeredObservers.contains(obs1));
		assertTrue(registeredObservers.contains(obs2));
	}

	@Test
	public void testBlockNotification() {
		final ReadOnlyChangeObserver readOnlyEMFObserver = mock(ReadOnlyChangeObserver.class);
		final ChangeObserver emfObserver = mock(ChangeObserver.class);
		broker.subscribe(readOnlyEMFObserver);
		broker.subscribe(emfObserver);

		broker.stopNotification(this);
		tournament.setType(TournamentType.PRO);

		verify(readOnlyEMFObserver, times(1)).handleNotification(any(Notification.class));
		verify(emfObserver, times(0)).handleNotification(any(Notification.class));
	}

	@Test
	public void testBlockNotificationAndCallContinue() {
		final ReadOnlyChangeObserver readOnlyEMFObserver = mock(ReadOnlyChangeObserver.class);
		final ChangeObserver emfObserver = mock(ChangeObserver.class);
		broker.subscribe(readOnlyEMFObserver);
		broker.subscribe(emfObserver);

		broker.stopNotification(this);
		broker.continueNotification();
		tournament.setType(TournamentType.PRO);

		verify(readOnlyEMFObserver, times(1)).handleNotification(any(Notification.class));
		verify(emfObserver, times(0)).handleNotification(any(Notification.class));
	}

	@Test
	public void testUnBlockNotification() {
		final ReadOnlyChangeObserver readOnlyEMFObserver = mock(ReadOnlyChangeObserver.class);
		final ChangeObserver emfObserver = mock(ChangeObserver.class);
		broker.subscribe(readOnlyEMFObserver);
		broker.subscribe(emfObserver);

		broker.stopNotification(this);
		broker.continueNotification(this);
		tournament.setType(TournamentType.PRO);

		verify(readOnlyEMFObserver, times(1)).handleNotification(any(Notification.class));
		verify(emfObserver, times(1)).handleNotification(any(Notification.class));
	}

	@Test
	public void testMultipleBlockers() {
		final ReadOnlyChangeObserver readOnlyEMFObserver = mock(ReadOnlyChangeObserver.class);
		final ChangeObserver emfObserver = mock(ChangeObserver.class);
		broker.subscribe(readOnlyEMFObserver);
		broker.subscribe(emfObserver);

		broker.stopNotification(this);
		broker.stopNotification(this.getClass());
		broker.continueNotification(this);
		tournament.setType(TournamentType.PRO);

		verify(readOnlyEMFObserver, times(1)).handleNotification(any(Notification.class));
		verify(emfObserver, times(0)).handleNotification(any(Notification.class));
	}

	@Test
	public void testRemoveMultipleBlockers() {
		final ReadOnlyChangeObserver readOnlyEMFObserver = mock(ReadOnlyChangeObserver.class);
		final ChangeObserver emfObserver = mock(ChangeObserver.class);
		broker.subscribe(readOnlyEMFObserver);
		broker.subscribe(emfObserver);

		broker.stopNotification(this);
		broker.stopNotification(this.getClass());
		broker.continueNotification(this);
		broker.continueNotification(this.getClass());
		tournament.setType(TournamentType.PRO);

		verify(readOnlyEMFObserver, times(1)).handleNotification(any(Notification.class));
		verify(emfObserver, times(1)).handleNotification(any(Notification.class));
	}

	@Test
	public void testRemovingBlockerContinuesUpdate() {

		final ReadOnlyChangeObserver readOnlyEMFObserver = mock(ReadOnlyChangeObserver.class);
		final ChangeObserver emfObserver = mock(ChangeObserver.class);
		broker.subscribe(readOnlyEMFObserver);
		broker.subscribe(emfObserver);

		broker.stopNotification(this);
		broker.stopNotification();
		broker.continueNotification(this);
		tournament.setType(TournamentType.PRO);

		verify(readOnlyEMFObserver, times(1)).handleNotification(any(Notification.class));
		verify(emfObserver, times(1)).handleNotification(any(Notification.class));
	}
}
