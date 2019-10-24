/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.edit.spi.ConditionalDeleteService;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the {@code DeleteServiceAdapter} class via the {@link ConditionalDeleteService} APIs that provide instances of it.
 */
@SuppressWarnings("nls")
@RunWith(MockitoJUnitRunner.class)
public class DeleteServiceAdapter_PTest {

	private League league;
	private Player player;

	@Mock
	private DeleteService delegate;

	@Mock
	private ConditionalDeleteService cdelegate;

	/**
	 * Test the null adapter (the adapter for an absent delete service).
	 */
	@Test
	public void test_canDelete_null() {
		final ConditionalDeleteService service = ConditionalDeleteService.adapt(null);

		final Collection<?> objects = Arrays.asList(player, league);
		assertThat("can delete without delegate", service.canDelete(player), is(false));
		assertThat("can delete without delegate", service.canDelete(objects), is(false));
	}

	@Test
	public void test_canDelete_single() {
		final ConditionalDeleteService service = ConditionalDeleteService.adapt(delegate);

		assertThat(service.canDelete(player), is(true));
		assertThat(service.canDelete(league), is(false));
	}

	@Test
	public void test_canDelete_multiple() {
		final ConditionalDeleteService service = ConditionalDeleteService.adapt(delegate);

		assertThat(service.canDelete(Arrays.asList(player)), is(true));
		assertThat(service.canDelete(Arrays.asList(player, league)), is(false));
	}

	@Test
	public void test_canDelete_single2() {
		final ConditionalDeleteService service = ConditionalDeleteService.adapt(cdelegate);

		service.canDelete(player);

		verify(cdelegate).canDelete(player);
	}

	@Test
	public void test_canDelete_multiple2() {
		final ConditionalDeleteService service = ConditionalDeleteService.adapt(cdelegate);

		final Collection<?> objects = Arrays.asList(player, league);
		service.canDelete(objects);

		verify(cdelegate).canDelete(objects);
	}

	@Test
	public void test_deleteElement() {
		final ConditionalDeleteService service = ConditionalDeleteService.adapt(delegate);

		service.deleteElement(player);

		verify(delegate).deleteElement(player);
	}

	@Test
	public void test_deleteElementse() {
		final ConditionalDeleteService service = ConditionalDeleteService.adapt(delegate);

		final Collection<Object> objects = Arrays.asList(player, league);
		service.deleteElements(objects);

		verify(delegate).deleteElements(objects);
	}

	//
	// Test framework
	//

	@Before
	public void createModel() {
		league = BowlingFactory.eINSTANCE.createLeague();
		player = BowlingFactory.eINSTANCE.createPlayer();

		league.getPlayers().add(player);
	}

}
