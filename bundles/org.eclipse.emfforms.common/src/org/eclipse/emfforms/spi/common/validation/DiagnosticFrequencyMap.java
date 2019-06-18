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
package org.eclipse.emfforms.spi.common.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * A simple collector of diagnostics that maps them in a histogram by severity.
 * Collected diagnostics are {@linkplain Iterable#iterator() iterated} in
 * decreasing severity order.
 *
 * @since 1.21
 */
public interface DiagnosticFrequencyMap extends Iterable<Diagnostic> {

	/**
	 * Remove all diagnostics and histogram counts.
	 */
	void clear();

	/**
	 * Queries the number of diagnostics that I contain.
	 *
	 * @return my size
	 */
	int size();

	/**
	 * Queries whether I am empty of any diagnostics.
	 *
	 * @return {@code true} I have no diagnostics; {@code false}, otherwise
	 */
	boolean isEmpty();

	/**
	 * Queries whether I am full to capacity, unable to accept any more diagnostics.
	 *
	 * @return {@code true} if I have reached my limit of diagnostics; {@code false}, otherwise
	 */
	boolean isFull();

	/**
	 * Query the greatest severity of diagnostics that had to be discarded because the
	 * map {@linkplain #isFull() filled up}.
	 *
	 * @return the highest discarded severity, or {@link Diagnostic#OK} if no diagnostics were discarded
	 */
	int getDiscardedSeverity();

	/**
	 * Add a diagnostic.
	 *
	 * @param diagnostic a diagnostic to add
	 * @return {@code true} if it was added; {@code false}, otherwise, for example if I am {@linkplain #isFull() full}
	 */
	boolean add(Diagnostic diagnostic);

	/**
	 * Add a predicate that matches diagnostics that should be collected.
	 * Diagnostics that do not match the filter are discarded, but their
	 * {@linkplain #getDiscardedSeverity() severity is retained} for separate
	 * reporting if the client should so wish. A diagnostic is only accepted
	 * that satisfies all {@code filter}s (overall, it's a conjunction).
	 *
	 * @param filter a predicate matching diagnostics to accept
	 */
	void addDiagnosticFilter(Predicate<? super Diagnostic> filter);

	/**
	 * Add a bunch of diagnostics.
	 *
	 * @param diagnostics some diagnostics to add
	 * @return {@code true} if any of them were added; {@code false} if none
	 *
	 * @see #add(Diagnostic)
	 */
	default boolean addAll(Collection<?> diagnostics) {
		boolean result = false;

		for (final Object next : diagnostics) {
			if (next instanceof Diagnostic) {
				result = add((Diagnostic) next) || result;
				if (!result) {
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Append the diagnostics that I have collected, in decreasing severity order,
	 * to a collection of {@code diagnostics}.
	 *
	 * @param diagnostics a collection of diagnostics to append to
	 */
	default void appendTo(Collection<? super Diagnostic> diagnostics) {
		for (final Diagnostic next : this) {
			diagnostics.add(next);
		}
	}

	/**
	 * Create a frequency map of unlimited size that keeps all diagnostics added to it.
	 *
	 * @return a new unlimited-size diagnostic frequency map
	 */
	static DiagnosticFrequencyMap unlimited() {
		return new Unlimited();
	}

	/**
	 * Create a frequency map that retains at most the given number of
	 * most severe problems.
	 *
	 * @param size the maximal size of diagnostic collection
	 * @return a new limited-{@code size} diagnostic frequency map
	 *
	 * @throws IllegalArgumentException if the {@code size} is negative
	 */
	static DiagnosticFrequencyMap limitedTo(int size) {
		return new Limited(size);
	}

	//
	// Nested types
	//

	/**
	 * A frequency map of unlimited size that keeps all diagnostics added to it.
	 *
	 * @since 1.21
	 */
	class Unlimited implements DiagnosticFrequencyMap {

		// CHECKSTYLE.OFF: VisibilityModifier - only visible to other inner classes
		/** The bucket of error (and cancel) diagnostics. */
		final List<Diagnostic> errors;

		/** The bucket of warning diagnostics. */
		final List<Diagnostic> warnings;

		/** The bucket of informational diagnostics. */
		final List<Diagnostic> infos;
		// CHECKSTYLE.ON: VisibilityModifier

		private Predicate<Diagnostic> filter;

		private int size;

		private int discardedSeverity = Diagnostic.OK;

		/**
		 * Initializes me.
		 */
		Unlimited() {
			this(10); // The ArrayList default capacity
		}

		/**
		 * Initializes me with the initial capacity of my buckets.
		 *
		 * @param capacity the initial capacity of my diagnostic severity buckets
		 */
		Unlimited(int capacity) {
			super();

			errors = new ArrayList<>(capacity);
			warnings = new ArrayList<>(capacity);
			infos = new ArrayList<>(capacity);
		}

		@Override
		public void clear() {
			size = 0;
			discardedSeverity = Diagnostic.OK;
			errors.clear();
			warnings.clear();
			infos.clear();
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean isEmpty() {
			return size == 0;
		}

		@Override
		public boolean isFull() {
			// I cannot be full
			return false;
		}

		@Override
		public int getDiscardedSeverity() {
			return discardedSeverity;
		}

		@Override
		public boolean add(Diagnostic diagnostic) {
			if (isFull() || !filter(diagnostic)) {
				// Already full or doesn't pass the filter
				discarded(diagnostic.getSeverity());
				return false;
			}

			boolean result = false;

			switch (diagnostic.getSeverity()) {
			case Diagnostic.CANCEL:
			case Diagnostic.ERROR:
				// No point in tracking more errors than problems we can report in total
				if (canAdd(Diagnostic.ERROR)) {
					result = errors.add(diagnostic);
					if (!discard(Diagnostic.WARNING)) {
						size = size + 1;
					}
				} else {
					discarded(diagnostic.getSeverity());
				}
				break;
			case Diagnostic.WARNING:
				// No point in tracking more warnings than problems we can report in total
				if (canAdd(Diagnostic.ERROR | Diagnostic.WARNING)) {
					result = warnings.add(diagnostic);
					if (!discard(Diagnostic.INFO)) {
						size = size + 1;
					}
				} else {
					discarded(Diagnostic.WARNING);
				}
				break;
			case Diagnostic.INFO:
				// No point in tracking more infos than problems we can report in total
				if (canAdd(Diagnostic.ERROR | Diagnostic.WARNING | Diagnostic.INFO)) {
					result = infos.add(diagnostic);
					if (!discard(Diagnostic.OK)) {
						size = size + 1;
					}
				} else {
					discarded(Diagnostic.INFO);
				}
				break;
			default:
				// We don't track OK diagnostics at all
				break;
			}

			return result;
		}

		@Override
		public void addDiagnosticFilter(Predicate<? super Diagnostic> filter) {
			if (this.filter == null) {
				@SuppressWarnings("unchecked") // This is safe because we only filter diagnostics
				final Predicate<Diagnostic> cast = (Predicate<Diagnostic>) filter;
				this.filter = cast;
			} else {
				this.filter = this.filter.and(filter);
			}
		}

		/**
		 * Query whether a {@code diagnostic} passes my filter.
		 *
		 * @param diagnostic a diagnostic
		 * @return {@code true} if I have no filter or if the {@code diagnostic} passes it
		 */
		private boolean filter(Diagnostic diagnostic) {
			return filter == null || filter.test(diagnostic);
		}

		/**
		 * Query whether any diagnostic with a severity matching the given mask
		 * can be added.
		 *
		 * @param severityMask a bitmask of severity values
		 * @return {@code true} if I have room for another diagnostic of any of the masked
		 *         severities; {@code false}, otherwise
		 */
		boolean canAdd(int severityMask) {
			return true;
		}

		/**
		 * Discard a diagnostic of the given {@code severity}, if appropriate.
		 *
		 * @param severity severity of a diagnostic to consider discarding
		 * @return {@code true} if the diagnostic was discarded; {@code false}, otherwise
		 */
		boolean discard(int severity) {
			return false;
		}

		/**
		 * Process the successful discarding of a diagnostic of the given {@code severity}.
		 *
		 * @param severity the severity of a diagnostic that was discarded
		 */
		void discarded(int severity) {
			if (discardedSeverity < severity) {
				discardedSeverity = severity;
			}
		}

		@Override
		public void appendTo(Collection<? super Diagnostic> diagnostics) {
			if (!errors.isEmpty()) {
				diagnostics.addAll(errors);
			}
			if (!warnings.isEmpty()) {
				diagnostics.addAll(warnings);
			}
			if (!infos.isEmpty()) {
				diagnostics.addAll(infos);
			}
		}

		@Override
		public Iterator<Diagnostic> iterator() {
			return new Iterator<Diagnostic>() {
				private final Iterator<Iterator<Diagnostic>> iitr = Arrays.asList(
					errors.iterator(), warnings.iterator(), infos.iterator())
					.iterator();

				private Iterator<Diagnostic> itr = iitr.next();

				private Iterator<Diagnostic> itr() {
					while (!itr.hasNext() && iitr.hasNext()) {
						itr = iitr.next();
					}
					return itr;
				}

				@Override
				public boolean hasNext() {
					return itr().hasNext();
				}

				@Override
				public Diagnostic next() {
					return itr().next();
				}
			};
		}

	}

	/**
	 * A frequency map of limited size that collects up to and no more than a certain
	 * number of diagnostics, keeping the most severe of them.
	 *
	 * @since 1.21
	 */
	class Limited extends Unlimited {
		private final int limit;

		/**
		 * Initializes me with the maximal number of diagnostics that we want to retain.
		 *
		 * @param limit the maximal number of diagnostics
		 *
		 * @throws IllegalArgumentException if the {@code limit} is negative
		 */
		Limited(int limit) {
			super(checkLimit(limit));

			this.limit = limit;
		}

		private static int checkLimit(int limit) {
			if (limit < 0) {
				throw new IllegalArgumentException("negative limit"); //$NON-NLS-1$
			}
			return limit;
		}

		@Override
		public boolean isFull() {
			return errors.size() >= limit;
		}

		@Override
		boolean canAdd(int severityMask) {
			int count = 0;

			if ((severityMask & Diagnostic.ERROR) != 0) {
				count = count + errors.size();
			}
			if ((severityMask & Diagnostic.WARNING) != 0) {
				count = count + warnings.size();
			}
			if ((severityMask & Diagnostic.INFO) != 0) {
				count = count + infos.size();
			}

			return count < limit;
		}

		@Override
		boolean discard(int severity) {
			// Do we need to discard anything?
			if (size() < limit) {
				// Nope
				return false;
			}

			Diagnostic discarded = null;

			switch (severity) {
			case Diagnostic.CANCEL:
			case Diagnostic.ERROR:
				// We cannot discard errors because we don't accumulate more than the limit in the first place
				throw new IllegalArgumentException("Cannot discard errors"); //$NON-NLS-1$
			case Diagnostic.WARNING:
				// Preferentially kick out infos
				if (!infos.isEmpty()) {
					discarded = infos.remove(infos.size() - 1);
				} else if (!warnings.isEmpty()) {
					discarded = warnings.remove(warnings.size() - 1);
				}
				break;
			case Diagnostic.INFO:
				if (!infos.isEmpty()) {
					discarded = infos.remove(infos.size() - 1);
				}
				break;
			default:
				// Cannot discard OK diagnostics because we don't track them
				break;
			}

			if (discarded != null) {
				discarded(discarded.getSeverity());
			}

			return discarded != null;
		}

	}

}
