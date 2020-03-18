/*******************************************************************************
 * Copyright (c) 2011-2020 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 * Christian W. Damus - bugs 552385, 559267
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.edit.spi.ConditionalDeleteService;
import org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl;
import org.eclipse.emf.ecp.test.university.Address;
import org.eclipse.emf.ecp.test.university.Professor;
import org.eclipse.emf.ecp.test.university.UniversityFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.command.OverrideableCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
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
@SuppressWarnings("nls")
public class EMFDeleteSerivceImpl_PTest {

	private ConditionalDeleteService deleteService;
	private AdapterFactoryEditingDomain domain;
	private ViewModelContext context;
	private Resource resource;
	private League league;
	private Game game;
	private Player player1;
	private Player player2;
	private Player player3;
	private Tournament tournament;

	private EObject denyDeletion;

	@Before
	public void setUp() {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$
		domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), resourceSet) {

			@Override
			public Command createOverrideCommand(OverrideableCommand command) {
				if (denyDeletion != null && command instanceof RemoveCommand
					&& ((RemoveCommand) command).getCollection().contains(denyDeletion)) {
					return UnexecutableCommand.INSTANCE;
				}
				return super.createOverrideCommand(command);
			}
		};
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
		context = mock(ViewModelContext.class);
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

	@Test
	public void testCanDeleteElement() {
		assertThat("can delete returns false", deleteService.canDelete(player1), is(true));

		denyDeletion = player1;
		assertThat("can delete returns true", deleteService.canDelete(player1), is(false));
	}

	@Test
	public void testCanDeleteElements() {
		final Collection<?> objects = Arrays.asList(player1, player2);

		assertThat("can delete returns false", deleteService.canDelete(objects), is(true));

		denyDeletion = player2; // Not the first one
		assertThat("can delete returns true", deleteService.canDelete(objects), is(false));
	}

	@Test
	public void testCanDeleteNoEditingDomain() {
		// Disconnect everything from the editing domain
		resource.getContents().clear();
		resource.getResourceSet().eAdapters().clear();
		resource.getResourceSet().getResources().clear();
		resource.unload();
		resource.eAdapters().clear();

		// Re-initialize to forget the editing domain
		deleteService.dispose();
		deleteService.instantiate(context);

		assertThat("cannot delete a contained object", deleteService.canDelete(player1), is(true));

		assertThat("can delete a root object", deleteService.canDelete(league), is(false));
	}

	@Test
	public void canRemoveElement_containment() {
		assertThat("can delete returns false",
			deleteService.canRemove(league, BowlingPackage.Literals.LEAGUE__PLAYERS, player1), is(true));

		denyDeletion = player1;
		assertThat("can delete returns true",
			deleteService.canRemove(league, BowlingPackage.Literals.LEAGUE__PLAYERS, player1), is(false));
	}

	@Test
	public void canRemoveElement_crossReferenced() {
		assertThat("cannot remove a referenced object",
			deleteService.canRemove(tournament, BowlingPackage.Literals.TOURNAMENT__PLAYERS, player1), is(true));
	}

	@Test
	public void canRemoveElement_notReferenced() {
		tournament.getPlayers().remove(player3);

		assertThat("cannot remove a referenced object",
			deleteService.canRemove(tournament, BowlingPackage.Literals.TOURNAMENT__PLAYERS, player3), is(false));
	}

	@Test
	public void canRemoveElement_crossReferenced_readOnly() {
		final Resource readOnly = resource.getResourceSet().createResource(URI.createURI("READ_ONLY"));
		final League others = BowlingFactory.eINSTANCE.createLeague();
		others.setName("The Others");
		others.getPlayers().add(player3);
		domain.setResourceToReadOnlyMap(new HashMap<>());
		domain.getResourceToReadOnlyMap().put(readOnly, true);

		assertThat("cannot remove a referenced object",
			deleteService.canRemove(tournament, BowlingPackage.Literals.TOURNAMENT__PLAYERS, player3), is(true));
	}

	@Test
	public void testRemove_noEditingDomain() {
		// Disconnect everything from the editing domain
		resource.getContents().clear();
		resource.getResourceSet().eAdapters().clear();
		resource.getResourceSet().getResources().clear();
		resource.unload();
		resource.eAdapters().clear();

		// Re-initialize to forget the editing domain
		deleteService.dispose();
		deleteService.instantiate(context);

		assertThat("cannot remove a referenced object",
			deleteService.canRemove(tournament, BowlingPackage.Literals.TOURNAMENT__PLAYERS, player1), is(true));
	}

}
