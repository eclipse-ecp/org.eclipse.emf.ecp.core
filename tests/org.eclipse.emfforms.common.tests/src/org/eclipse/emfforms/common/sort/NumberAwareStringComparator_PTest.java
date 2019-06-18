/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.sort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import org.eclipse.emfforms.spi.common.sort.NumberAwareStringComparator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for {@link NumberAwareStringComparator}. Run as plugin test because otherwise the hamcrest matchers do not
 * work due to a security exception.
 *
 * @author Lucas Koehler
 *
 */
public class NumberAwareStringComparator_PTest {

	private static NumberAwareStringComparator comparator;

	@BeforeClass
	public static void setUp() {
		comparator = NumberAwareStringComparator.getInstance();
	}

	@Test
	public void houseNumbers() {
		// With normal string compare a letter is bigger than a digit => 100A would be bigger than 1000A
		// With number aware compare 100A should be smaller
		assertThat(comparator.compare("100A", "1000A"), lessThanOrEqualTo(-1)); //$NON-NLS-1$//$NON-NLS-2$

		assertThat(comparator.compare("2A", "10A"), lessThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("2B", "10A"), lessThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("2B", "2A"), greaterThanOrEqualTo(1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("25C", "25C"), equalTo(0)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void mixedSameSegments() {
		assertThat(comparator.compare("n1a1", "n1aa"), lessThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1aa", "n1a1"), greaterThanOrEqualTo(1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1aa", "n1aa"), equalTo(0)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1a1", "n1a1"), equalTo(0)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1a1", "n1a"), greaterThanOrEqualTo(1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1a1", "n1aa1"), lessThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1b1", "n1a2"), greaterThanOrEqualTo(1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1a1", "n1a1a"), lessThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$

		assertThat(comparator.compare("n1a2", "n1a1a"), greaterThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/** The sorting should ignore case because otherwise z > a > Z which would be really unintuitive for users. */
	@Test
	public void ignoreCase() {
		assertThat(comparator.compare("a", "z"), lessThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$
		assertThat(comparator.compare("a", "Z"), lessThanOrEqualTo(-1)); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
