/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.impl.LeagueImpl;
import org.eclipse.emf.emfstore.bowling.impl.PlayerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The Class ViewModelContextTest.
 * 
 * @author Eugen Neufeld
 */
public class ViewModelContextTest {

	private Player player;
	private VView view;
	private ViewModelContext viewModelContext;

	/**
	 * @throws java.lang.Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		player = BowlingFactory.eINSTANCE.createPlayer();
		view = VViewFactory.eINSTANCE.createView();
		numPlayerAdapters = player.eAdapters().size();
		numViewAdapters = view.eAdapters().size();
		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);
	}

	/**
	 * @throws java.lang.Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ViewModelContext#getViewModel()}.
	 */
	@Test
	public void testGetViewModel() {
		// TODO return copy?
		assertEquals(view, viewModelContext.getViewModel());
	}

	/**
	 * Test method for {@link ViewModelContext#getDomainModel()}.
	 */
	@Test
	public void testGetDomainModel() {
		// TODO return copy?
		assertEquals(player, viewModelContext.getDomainModel());
	}

	/**
	 * Test method for {@link ViewModelContext#dispose()}.
	 */
	@Test()
	public void testDisposeAdapters() {
		viewModelContext.dispose();
		// assert adapters not there anymore
		assertEquals(numPlayerAdapters, player.eAdapters().size());
		assertEquals(numViewAdapters, view.eAdapters().size());
	}

	/**
	 * Test dispose get domain model.
	 */
	@Test(expected = IllegalStateException.class)
	public void testDisposeGetDomainModel() {
		viewModelContext.dispose();
		viewModelContext.getDomainModel();
		fail("No exception throw!");
	}

	/**
	 * Test dispose get view model.
	 */
	@Test(expected = IllegalStateException.class)
	public void testDisposeGetViewModel() {
		viewModelContext.dispose();
		viewModelContext.getViewModel();
		fail("No exception throw!");
	}

	/**
	 * Test dispose register domain listner.
	 */
	@Test(expected = IllegalStateException.class)
	public void testDisposeRegisterDomainListner() {
		viewModelContext.dispose();
		viewModelContext.registerDomainChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		fail("No exception throw!");
	}

	/**
	 * Test dispose register view listner.
	 */
	@Test(expected = IllegalStateException.class)
	public void testDisposeRegisterViewListner() {
		viewModelContext.dispose();
		viewModelContext.registerViewChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		fail("No exception throw!");
	}

	/**
	 * Test dispose un register domain listner.
	 */
	@Test(expected = IllegalStateException.class)
	@Ignore
	public void testDisposeUnRegisterDomainListner() {
		viewModelContext.dispose();
		viewModelContext.unregisterDomainChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		fail("No exception throw!");
	}

	/**
	 * Test dispose un register view listner.
	 */
	@Test(expected = IllegalStateException.class)
	@Ignore
	public void testDisposeUnRegisterViewListner() {
		viewModelContext.dispose();
		viewModelContext.unregisterViewChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		fail("No exception throw!");
	}

	/**
	 * Test method for
	 * {@link ViewModelContext#registerViewChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeAddRemoveListener)}
	 * .
	 */
	private boolean correctNotificationArrived;
	private int numPlayerAdapters;
	private int numViewAdapters;

	/**
	 * Test register view change listener.
	 */
	@Test
	public void testRegisterViewChangeListener() {
		viewModelContext.registerViewChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = notification.getNotifier() == view
					&& notification.getStructuralFeature() == VViewPackage.eINSTANCE.getElement_Name();
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		view.setName("Test");
		assertTrue(correctNotificationArrived);
	}

	/**
	 * Test register view change listener null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterViewChangeListenerNull() {
		viewModelContext.registerViewChangeListener(null);
		fail("No exception thrown.");
	}

	/**
	 * Test register view change listener change on domain.
	 */
	@Test
	public void testRegisterViewChangeListenerChangeOnDomain() {
		viewModelContext.registerViewChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = true;
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		player.setName("Test");
		assertFalse(correctNotificationArrived);
	}

	/**
	 * Test method for
	 * {@link ViewModelContext#unregisterViewChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeAddRemoveListener)}
	 * .
	 */
	@Test
	public void testUnregisterViewChangeListener() {
		final ModelChangeAddRemoveListener modelChangeListener = new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = true;
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		};
		viewModelContext.registerViewChangeListener(modelChangeListener);
		viewModelContext.unregisterViewChangeListener(modelChangeListener);
		view.setName("Test");
		assertFalse(correctNotificationArrived);
	}

	/**
	 * Test unregister view change listener change on domain.
	 */
	@Test
	public void testUnregisterViewChangeListenerChangeOnDomain() {
		final ModelChangeAddRemoveListener modelChangeListener = new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = true;
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		};
		viewModelContext.registerViewChangeListener(modelChangeListener);
		viewModelContext.unregisterViewChangeListener(modelChangeListener);
		player.setName("Test");
		assertFalse(correctNotificationArrived);
	}

	/**
	 * Test method for
	 * {@link ViewModelContext#registerDomainChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeAddRemoveListener)}
	 * .
	 */
	@Test
	public void testRegisterDomainChangeListener() {
		viewModelContext.registerDomainChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = notification.getNotifier() == player
					&& notification.getStructuralFeature() == BowlingPackage.eINSTANCE.getPlayer_Name();
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		player.setName("Test");
		assertTrue(correctNotificationArrived);
	}

	/**
	 * Test register domain change listener null.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRegisterDomainChangeListenerNull() {
		viewModelContext.registerDomainChangeListener(null);
		fail("No exception thrown.");
	}

	/**
	 * Test register domain change listener change on view.
	 */
	@Test
	public void testRegisterDomainChangeListenerChangeOnView() {
		viewModelContext.registerDomainChangeListener(new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = true;
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		});
		view.setName("Test");
		assertFalse(correctNotificationArrived);
	}

	/**
	 * Test method for
	 * {@link ViewModelContext#unregisterDomainChangeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeAddRemoveListener)}
	 * .
	 */
	@Test
	public void testUnregisterDomainChangeListener() {
		final ModelChangeAddRemoveListener modelChangeListener = new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = true;
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		};
		viewModelContext.registerDomainChangeListener(modelChangeListener);
		viewModelContext.unregisterDomainChangeListener(modelChangeListener);
		player.setName("Test");
		assertFalse(correctNotificationArrived);
	}

	/**
	 * Test unregister domain change listener change on view.
	 */
	@Test
	public void testUnregisterDomainChangeListenerChangeOnView() {
		final ModelChangeAddRemoveListener modelChangeListener = new ModelChangeAddRemoveListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				correctNotificationArrived = true;
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyRemove(Notifier notifier) {
				// TODO Auto-generated method stub

			}
		};
		viewModelContext.registerDomainChangeListener(modelChangeListener);
		viewModelContext.unregisterDomainChangeListener(modelChangeListener);
		view.setName("Test");
		assertFalse(correctNotificationArrived);
	}

	/**
	 * Tests setting to control mapping static.
	 */
	@Test
	public void testSettingToControlMapSingleControlStatic() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(control);

		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);

		final Set<VControl> controls = viewModelContext.getControlsFor(((PlayerImpl) player)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Name()));
		assertEquals(1, controls.size());
		assertEquals(control, controls.iterator().next());
	}

	/**
	 * Tests setting to control mapping static.
	 */
	@Test
	public void testSettingToControlMapMultipleControlsStatic() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(control);

		final VControl control2 = VViewFactory.eINSTANCE.createControl();
		control2.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(control2);

		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);

		final Set<VControl> controls = viewModelContext.getControlsFor(((PlayerImpl) player)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Name()));
		assertEquals(2, controls.size());

		assertTrue(controls.contains(control));
		assertTrue(controls.contains(control2));
	}

	/**
	 * Tests setting to control mapping static.
	 */
	@Test
	public void testSettingToTableMapSingleControlStatic() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VTableControl control = VTableFactory.eINSTANCE.createTableControl();
		final VTableDomainModelReference domRef = VTableFactory.eINSTANCE.createTableDomainModelReference();
		domRef.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Players());
		control.setDomainModelReference(domRef);
		final VFeaturePathDomainModelReference col = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		col.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		VTableDomainModelReference.class.cast(control.getDomainModelReference()).getColumnDomainModelReferences()
			.add(col);
		view.getChildren().add(control);

		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.getPlayers().add(player);

		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, league);

		final Set<VControl> controls = viewModelContext.getControlsFor(((LeagueImpl) league)
			.eSetting(BowlingPackage.eINSTANCE.getLeague_Players()));
		assertEquals(1, controls.size());
		assertEquals(control, controls.iterator().next());

		final Set<VControl> controls2 = viewModelContext.getControlsFor(((PlayerImpl) player)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Name()));
		assertEquals(1, controls2.size());
		assertEquals(control, controls2.iterator().next());
	}

	/**
	 * Tests dynamic domain add.
	 */
	@Test
	public void testSettingToTableMapSingleControlDynamicDomainAdd() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VTableControl control = VTableFactory.eINSTANCE.createTableControl();
		final VTableDomainModelReference domRef = VTableFactory.eINSTANCE.createTableDomainModelReference();
		domRef.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Players());
		control.setDomainModelReference(domRef);
		final VFeaturePathDomainModelReference col = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		col.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		VTableDomainModelReference.class.cast(control.getDomainModelReference()).getColumnDomainModelReferences()
			.add(col);
		view.getChildren().add(control);

		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.getPlayers().add(player);

		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, league);

		final Player player2 = BowlingFactory.eINSTANCE.createPlayer();
		league.getPlayers().add(player2);

		final Set<VControl> controls2 = viewModelContext.getControlsFor(((PlayerImpl) player2)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Name()));
		assertEquals(1, controls2.size());
		assertEquals(control, controls2.iterator().next());
	}

	/**
	 * Tests dynamic domain remove.
	 */
	@Test
	public void testSettingToTableMapSingleControlDynamicDomainRemove() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VTableControl control = VTableFactory.eINSTANCE.createTableControl();
		final VTableDomainModelReference domRef = VTableFactory.eINSTANCE.createTableDomainModelReference();
		domRef.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Players());
		control.setDomainModelReference(domRef);
		final VFeaturePathDomainModelReference col = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		col.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		VTableDomainModelReference.class.cast(control.getDomainModelReference()).getColumnDomainModelReferences()
			.add(col);
		view.getChildren().add(control);

		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.getPlayers().add(player);
		final Player player2 = BowlingFactory.eINSTANCE.createPlayer();
		league.getPlayers().add(player2);

		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, league);

		league.getPlayers().remove(player);

		final Set<VControl> controls2 = viewModelContext.getControlsFor(((PlayerImpl) player)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Name()));
		assertEquals(0, controls2.size());

		final Set<VControl> controls3 = viewModelContext.getControlsFor(((PlayerImpl) player2)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Name()));
		assertEquals(1, controls3.size());
		assertEquals(control, controls3.iterator().next());
	}

	/**
	 * Tests dynamic view model add.
	 */
	@Test
	public void testSettingToControlMapDynamicViewAdd() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(control);

		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);

		final VControl control2 = VViewFactory.eINSTANCE.createControl();
		control2.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Gender());
		view.getChildren().add(control2);

		final Set<VControl> controls = viewModelContext.getControlsFor(((PlayerImpl) player)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Gender()));
		assertEquals(1, controls.size());
		assertEquals(control2, controls.iterator().next());
	}

	/**
	 * Tests dynamic view model remove.
	 */
	@Test
	public void testSettingToControlMapDynamicViewRemove() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Name());
		view.getChildren().add(control);
		final VControl control2 = VViewFactory.eINSTANCE.createControl();
		control2.setDomainModelReference(BowlingPackage.eINSTANCE.getPlayer_Gender());
		view.getChildren().add(control2);

		viewModelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view, player);

		view.getChildren().remove(control);

		final Set<VControl> controls = viewModelContext.getControlsFor(((PlayerImpl) player)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Name()));
		assertNull(controls);

		final Set<VControl> controls2 = viewModelContext.getControlsFor(((PlayerImpl) player)
			.eSetting(BowlingPackage.eINSTANCE.getPlayer_Gender()));
		assertEquals(1, controls2.size());
		assertEquals(control2, controls2.iterator().next());
	}

}
