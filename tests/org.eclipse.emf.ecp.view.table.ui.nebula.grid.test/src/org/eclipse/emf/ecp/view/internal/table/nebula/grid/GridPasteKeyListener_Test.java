/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.swt.graphics.Point;
import org.junit.Test;

public class GridPasteKeyListener_Test {

	private static Point[] createCellSelection(int... pointsToCreate) {
		if (pointsToCreate.length % 2 != 0) {
			throw new IllegalArgumentException("X Y Pairs have to be supplied");
		}
		int resultIndex = 0;
		final Point[] result = new Point[pointsToCreate.length / 2];
		for (int i = 0; i < pointsToCreate.length;) {
			result[resultIndex++] = new Point(pointsToCreate[i++], pointsToCreate[i++]);
		}
		return result;
	}

	@Test
	public void testFillSelectionWithMultipleCopiesSingleRow() {
		/* setup */
		final Point[] cellSelection = createCellSelection(
			1, 1,
			2, 1);
		final String contents = "foo\tbar";

		/* act */
		final boolean fillSelectionWithMultipleCopies = GridPasteKeyListener
			.fillSelectionWithMultipleCopies(cellSelection, contents);

		/* assert */
		assertFalse(fillSelectionWithMultipleCopies);
	}

	@Test
	public void testFillSelectionWithMultipleCopiesSelectionNotUniform() {
		/* setup */
		final Point[] cellSelection = createCellSelection(
			1, 1,
			2, 1,
			1, 2);
		final String contents = "foo\tbar";

		/* act */
		final boolean fillSelectionWithMultipleCopies = GridPasteKeyListener
			.fillSelectionWithMultipleCopies(cellSelection, contents);

		/* assert */
		assertFalse(fillSelectionWithMultipleCopies);
	}

	@Test
	public void testFillSelectionWithMultipleCopiesSelectionSelectionAndContentLengthDifferent() {
		/* setup */
		final Point[] cellSelection = createCellSelection(
			1, 1,
			2, 1,
			3, 1,
			1, 2,
			2, 2,
			3, 2);
		final String contents = "foo\tbar";

		/* act */
		final boolean fillSelectionWithMultipleCopies = GridPasteKeyListener
			.fillSelectionWithMultipleCopies(cellSelection, contents);

		/* assert */
		assertFalse(fillSelectionWithMultipleCopies);
	}

	@Test
	public void testFillSelectionWithMultipleCopiesSelectionSelectionColumnsWithGap() {
		/* setup */
		final Point[] cellSelection = createCellSelection(
			1, 1,
			2, 1,
			4, 1,
			1, 2,
			2, 2,
			4, 2);
		final String contents = "foo\tbar\telf";

		/* act */
		final boolean fillSelectionWithMultipleCopies = GridPasteKeyListener
			.fillSelectionWithMultipleCopies(cellSelection, contents);

		/* assert */
		assertFalse(fillSelectionWithMultipleCopies);
	}

	@Test
	public void testFillSelectionWithMultipleCopiesSelectionAllFineMultipleSelection() {
		/* setup */
		final Point[] cellSelection = createCellSelection(
			1, 1,
			2, 1,
			1, 2,
			2, 2);
		final String contents = "foo\tbar";

		/* act */
		final boolean fillSelectionWithMultipleCopies = GridPasteKeyListener
			.fillSelectionWithMultipleCopies(cellSelection, contents);

		/* assert */
		assertTrue(fillSelectionWithMultipleCopies);
	}

	@Test
	public void testFillSelectionWithMultipleCopiesSelectionAllFineSingleSelection() {
		/* setup */
		final Point[] cellSelection = createCellSelection(
			1, 1,
			1, 2);
		final String contents = "foo\tbar";

		/* act */
		final boolean fillSelectionWithMultipleCopies = GridPasteKeyListener
			.fillSelectionWithMultipleCopies(cellSelection, contents);

		/* assert */
		assertTrue(fillSelectionWithMultipleCopies);
	}

	@Test
	public void getFillStartPoints() {
		/* setup */
		final Point[] cellSelection = createCellSelection(
			2, 2,
			1, 1,
			1, 2,
			2, 1,
			3, 1,
			3, 2);

		/* act */
		final Point[] fillStartPoints = GridPasteKeyListener.getFillStartPoints(cellSelection);

		/* assert */
		assertEquals(2, fillStartPoints.length);
		final Set<Point> expectedStartPoints = new LinkedHashSet<Point>(Arrays.asList(createCellSelection(
			1, 1,
			1, 2)));
		expectedStartPoints.removeAll(Arrays.asList(fillStartPoints));
		assertTrue(expectedStartPoints.isEmpty());
	}

}
