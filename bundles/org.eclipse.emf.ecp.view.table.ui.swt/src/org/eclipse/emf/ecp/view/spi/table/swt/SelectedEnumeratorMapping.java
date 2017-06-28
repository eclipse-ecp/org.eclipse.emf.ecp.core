/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emfforms.common.Optional;

/**
 * Utility class that determines whether
 * a given {@link Enumerator} and has been selected.
 *
 * @since 1.13
 */
public class SelectedEnumeratorMapping {

	private boolean selected;
	private final Enumerator enumerator;

	/**
	 * Search the array of {@link SelectedEnumeratorMapping}s for the given literal.
	 *
	 * @param mapping the array of mappings to be searched
	 * @param literal the literal to be searched for as a string
	 * @return an {@link Optional} containing the matched literal
	 */
	public static Optional<SelectedEnumeratorMapping> findLiteral(SelectedEnumeratorMapping[] mapping,
		String literal) {

		for (final SelectedEnumeratorMapping m : mapping) {
			if (m.getEnumerator().getLiteral().equals(literal)) {
				return Optional.of(m);
			}
		}

		return Optional.empty();
	}

	/**
	 * Find the first selected enumerator.
	 *
	 * @param mapping an array of {@link SelectedEnumeratorMapping}s
	 * @return an {@link Optional} containing the selected {@link Enumerator}
	 */
	public static Optional<Enumerator> findSelected(SelectedEnumeratorMapping[] mapping) {

		for (final SelectedEnumeratorMapping current : mapping) {
			if (current.isSelected()) {
				return Optional.of(current.getEnumerator());
			}
		}

		return Optional.empty();
	}

	/**
	 * Given a list of {@link EEnumLiteral}s creates an array of {@link SelectedEnumeratorMapping}s.
	 *
	 * @param literals a list of {@link EEnumLiteral}s
	 * @return an array of {@link SelectedEnumeratorMapping}
	 */
	public static SelectedEnumeratorMapping[] createFromList(List<EEnumLiteral> literals) {
		final SelectedEnumeratorMapping[] mapping = new SelectedEnumeratorMapping[literals.size()];
		int i = 0;
		for (final EEnumLiteral literal : literals) {
			final SelectedEnumeratorMapping current = new SelectedEnumeratorMapping(literal.getInstance(),
				false);
			mapping[i++] = current;
		}
		return mapping;
	}

	/**
	 * Select the given {@link Enumerator} in the list of given {@link SelectedEnumeratorMapping}s.
	 *
	 * @param mappings an array of {@link SelectedEnumeratorMapping}s,
	 * @param enumerator the {@link Enumerator} to be selected
	 */
	public static void select(SelectedEnumeratorMapping[] mappings, Enumerator enumerator) {
		for (final SelectedEnumeratorMapping selectedEnumeratorMapping : mappings) {
			if (enumerator.equals(selectedEnumeratorMapping.getEnumerator())) {
				selectedEnumeratorMapping.setSelected(true);
			}
		}
	}

	/**
	 * De-select all enumerators in the given array of {@link SelectedEnumeratorMapping}s.
	 * 
	 * @param mappings an array of {@link SelectedEnumeratorMapping}s
	 */
	public static void deselectAll(final SelectedEnumeratorMapping[] mappings) {
		for (final SelectedEnumeratorMapping selectedMapping : mappings) {
			selectedMapping.setSelected(false);
		}
	}

	/**
	 * Constructor.
	 *
	 * @param enumerator the {@link Enumerator} entry
	 * @param selected whether the given enumerator is selected
	 */
	public SelectedEnumeratorMapping(Enumerator enumerator, boolean selected) {
		this.enumerator = enumerator;
		setSelected(selected);
	}

	/**
	 * Returns whether whether the enumerator is selected.
	 *
	 * @return {@code true}, if the enumerator is selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Returns whether whether the enumerator is selected.
	 *
	 * @param selected the selected state
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Returns the enumerator.
	 *
	 * @return the enumerator
	 */
	public Enumerator getEnumerator() {
		return enumerator;
	}
}