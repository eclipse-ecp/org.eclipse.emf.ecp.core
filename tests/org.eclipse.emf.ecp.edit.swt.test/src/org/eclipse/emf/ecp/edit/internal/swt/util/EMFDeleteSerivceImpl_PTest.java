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
package org.eclipse.emf.ecp.edit.internal.swt.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl;
import org.eclipse.emf.ecp.test.university.Address;
import org.eclipse.emf.ecp.test.university.Professor;
import org.eclipse.emf.ecp.test.university.UniversityFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emf.emfstore.bowling.Tournament;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jfaltermeier
 *
 */
public class EMFDeleteSerivceImpl_PTest {

	private DeleteService deleteService;
	private AdapterFactoryEditingDomain domain;
	private Resource resource;
	private League league;
	private Game game;
	private Player player1;
	private Player player2;
	private Player player3;
	private Tournament tournament;

	@Before
	public void setUp() {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$
		domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), resourceSet);
		resourceSet.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		resource = resourceSet.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$

		league = BowlingFactory.eINSTANCE.createLeague();
		game = BowlingFactory.eINSTANCE.createGame();
		tournament = BowlingFactory.eINSTANCE.createTournament();

		resource.getContents().add(league);
		resource.getContents().add(game);
		resource.getContents().add(tournament);

		player1 = BowlingFactory.eINSTANCE.createPlayer();
		player2 = BowlingFactory.eINSTANCE.createPlayer();
		player3 = BowlingFactory.eINSTANCE.createPlayer();
		league.getPlayers().add(player1);
		league.getPlayers().add(player2);
		league.getPlayers().add(player3);
		tournament.getPlayers().add(player1);
		tournament.getPlayers().add(player2);
		tournament.getPlayers().add(player3);

		game.setPlayer(player1);

		deleteService = new EMFDeleteServiceImpl();
		final ViewModelContext context = mock(ViewModelContext.class);
		when(context.getDomainModel()).thenReturn(league);
		deleteService.instantiate(context);
	}

	@Test
	public void testDeleteElements() {
		/* act */
		deleteService.deleteElements(Arrays.asList(Object.class.cast(player1), player2));

		/* assert */
		assertEquals(1, league.getPlayers().size());
		assertTrue(league.getPlayers().contains(player3));
		assertEquals(1, tournament.getPlayers().size());
		assertTrue(tournament.getPlayers().contains(player3));
		assertNull(game.getPlayer());

	}

	@Test
	public void testDeleteElement() {
		/* act */
		deleteService.deleteElement(player1);

		/* assert */
		assertEquals(2, league.getPlayers().size());
		assertTrue(league.getPlayers().contains(player2));
		assertTrue(league.getPlayers().contains(player3));
		assertEquals(2, tournament.getPlayers().size());
		assertTrue(tournament.getPlayers().contains(player2));
		assertTrue(tournament.getPlayers().contains(player3));
		assertNull(game.getPlayer());
	}

	@Test
	public void testDeleteElementNoChildOfParentReference() {
		// setup
		final Professor professor = UniversityFactory.eINSTANCE.createProfessor();
		final Address address1 = UniversityFactory.eINSTANCE.createAddress();
		final Address address2 = UniversityFactory.eINSTANCE.createAddress();
		professor.getAddresses().add(address1);
		professor.getAddresses().add(address2);
		resource.getContents().add(professor);

		// act
		deleteService.deleteElement(address2);

		// assert
		assertEquals(1, professor.getAddresses().size());
		assertSame(address1, professor.getAddresses().get(0));
	}

}
