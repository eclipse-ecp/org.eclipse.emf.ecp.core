/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emfforms.common.RankingHelper;
import org.eclipse.emfforms.common.RankingHelper.RankTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public class RankingHelper_Test {

	private static class DummyRankedElement {

		private final double rank;

		DummyRankedElement(double rank) {
			super();
			this.rank = rank;
		}

		@Override
		@SuppressWarnings("nls")
		public String toString() {
			return super.toString() + "{rank " + rank + "}";
		}

	}

	private static final DummyRankedElement DUMMY_RANK_1 = new DummyRankedElement(1d);
	private static final DummyRankedElement DUMMY_RANK_2 = new DummyRankedElement(2d);
	private static final DummyRankedElement DUMMY_RANK_2_REDUNDANT = new DummyRankedElement(2d);
	private static final DummyRankedElement DUMMY_RANK_3 = new DummyRankedElement(3d);

	private RankingHelper<DummyRankedElement> rankingHelper;

	@Before
	public void setup() {
		rankingHelper = new RankingHelper<RankingHelper_Test.DummyRankedElement>(DummyRankedElement.class, -1d);
	}

	@Test
	public void testRankingNoWinner() {

		// empty element list should lead to null winner
		final List<DummyRankedElement> emptyElements = Arrays.asList(new DummyRankedElement[] {});

		final DummyRankedElement winner = rankingHelper.getHighestRankingElement(emptyElements,
			new RankTester<RankingHelper_Test.DummyRankedElement>() {

				@Override
				public double getRank(DummyRankedElement element) {
					return element.rank;
				}
			});

		assertNull(winner);

	}

	@Test
	public void testRankingWithDuplicate() {

		rankingHelper = new RankingHelper<DummyRankedElement>(DummyRankedElement.class, -1d) {
			private boolean warningCreated;
			private boolean warningReported;

			@Override
			public DummyRankedElement getHighestRankingElement(Collection<DummyRankedElement> elements,
				org.eclipse.emfforms.common.RankingHelper.RankTester<DummyRankedElement> rankTester) {
				try {
					return super.getHighestRankingElement(elements, rankTester);
				} finally {
					assertTrue(warningCreated);
					assertTrue(warningReported);
				}
			}

			@Override
			protected String createWarning(List<DummyRankedElement> highestRankingElements, double rank) {
				assertEquals(highestRankingElements.size(), 2);
				assertEquals(DUMMY_RANK_2, highestRankingElements.get(0));
				assertEquals(DUMMY_RANK_2_REDUNDANT, highestRankingElements.get(1));
				assertEquals(DUMMY_RANK_2.rank, rank, 0.0d);
				warningCreated = true;
				return super.createWarning(highestRankingElements, rank);
			}

			@Override
			protected void reportClashingPriorities(String warning) {
				warningReported = true;
			}
		};

		final List<DummyRankedElement> elements = Arrays.asList(
			new DummyRankedElement[] { DUMMY_RANK_1, DUMMY_RANK_2, DUMMY_RANK_2_REDUNDANT });

		final DummyRankedElement winner = rankingHelper.getHighestRankingElement(elements,
			new RankTester<RankingHelper_Test.DummyRankedElement>() {

				@Override
				public double getRank(DummyRankedElement element) {
					return element.rank;
				}
			});

		assertEquals(DUMMY_RANK_2, winner);

	}

	@Test
	public void testRankingHighestRank() {

		DummyRankedElement winner = null;
		final List<DummyRankedElement> elements = new ArrayList<RankingHelper_Test.DummyRankedElement>();
		elements.addAll(Arrays.asList(new DummyRankedElement[] { DUMMY_RANK_1, DUMMY_RANK_2 }));

		final RankTester<DummyRankedElement> tester = new RankTester<RankingHelper_Test.DummyRankedElement>() {

			@Override
			public double getRank(DummyRankedElement element) {
				return element.rank;
			}
		};

		winner = rankingHelper.getHighestRankingElement(elements, tester);
		assertEquals(DUMMY_RANK_2, winner);

		elements.add(DUMMY_RANK_3);
		winner = rankingHelper.getHighestRankingElement(elements, tester);
		assertEquals(DUMMY_RANK_3, winner);

	}

}
