/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.ui;

import org.eclipse.emfforms.common.TriFunction;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

/**
 * The {@link ObjectViewerComparator} allows to rotate between three sorting states:
 * <ol>
 * <li>no sorting
 * <li>ascending
 * <li>descending
 * </ol>
 * To sort the objects, the comparator applies the configured sorting function to the given objects.
 *
 * @author Lucas Koehler
 * @since 1.20
 *
 */
public class ObjectViewerComparator extends ViewerComparator {

	private static final int NONE = 0;
	private int direction = NONE;
	private final TriFunction<Integer, Integer, Object, Object> compareFunction;

	/**
	 * Creates a new instance.
	 *
	 * @param compareFunction The function used to compare objects of the viewer. This tri-function accepts the sorting
	 *            direction as its first argument and the objects to compare as the following arguments. The sorting
	 *            directions are: 0 = none, 1 = ascending, 2 = descending
	 */
	public ObjectViewerComparator(TriFunction<Integer, Integer, Object, Object> compareFunction) {
		this.compareFunction = compareFunction;
		direction = NONE;
	}

	/** Toggles through the sorting directions: NONE -&gt; UP (ascending) -&gt; DOWN (descending) -&gt; NONE. */
	public void toggleDirection() {
		direction = (direction + 1) % 3;
	}

	/**
	 * Get the current sorting direction as an SWT constant.
	 *
	 * @return SWT.NONE, SWT.UP (ascending), or SWT.DOWN (descending)
	 */
	public int getDirection() {
		switch (direction) {
		case 0:
			return SWT.NONE;
		case 1:
			return SWT.UP;
		case 2:
			return SWT.DOWN;
		default:
			return SWT.NONE;
		}

	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return compareFunction.apply(direction, e1, e2);
	}
}
