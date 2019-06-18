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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.test.common;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Convenience class for timing performance tests.
 *
 * @author Lucas Koehler
 *
 */
public final class PerformanceClock {

	private PerformanceClock() {
		// Utility class
	}

	/**
	 * Run an {@code experiment} several times to compute the average elapsed time with standard deviation.
	 *
	 * @param iterations the number of iterations to run the experiment
	 * @param before runnable executed before every experiment
	 * @param experiment the experiment to execute and measure
	 * @param after runnable executed after every experiment
	 * @return The measured average execution time and standard deviation
	 */
	public static Measure time(int iterations, Runnable before, Runnable experiment, Runnable after) {
		final int count = Math.max(7, iterations); // We toss high and low so need at least five
		final int n = count - 2;

		final double[] samples = new double[count];

		before.run();
		try {
			for (int i = 0; i < count; i++) {
				final long start = System.nanoTime();
				experiment.run();
				final long end = System.nanoTime();
				samples[i] = (end - start) / 1_000_000d;
			}
		} finally {
			after.run();
		}

		Arrays.sort(samples);

		final int last = count - 1;
		double sum = 0.0;
		for (int i = 1; i < last; i++) {
			sum = sum + samples[i];
		}

		final double average = sum / n;
		double sumdev = 0.0;
		for (int i = 1; i < last; i++) {
			final double dev = samples[i] - average;
			sumdev = sumdev + dev * dev;
		}
		final double stddev = Math.sqrt(sumdev / (n - 1));

		return new Measure(average, stddev);
	}

	/**
	 * Run an {@code experiment} on both the small- and the large inputs,
	 * measuring the performance of each, and compare the performance to verify that
	 * it's not worse than the expected worst case multiplier.
	 *
	 * @param iterations the number of iterations to perform of the {@code experiment} on each input
	 * @param worstCaseMultiplier a multiplier on the measured timing of the small-scale scenario that is it maximal
	 *            acceptable timing of the large-scale scenario. Performance worse than this multiplier fails the test
	 * @param smallInput supplier of the small input. It can supply the same input or a different input on every
	 *            invocation
	 * @param largeInput supplier of the large-scale input. It can supply the same input or a different input on every
	 *            invocation
	 * @param experiment
	 *            the experiment to run at each scale of input
	 *
	 * @param <T> the type of input for the {@code experiment}
	 */
	public static <T> void test(int iterations, double worstCaseMultiplier, //
		Supplier<? extends T> smallInput, Supplier<? extends T> largeInput, //
		Consumer<? super T> experiment) {

		test(iterations, worstCaseMultiplier, smallInput, largeInput, null, experiment, null);
	}

	/**
	 * Run an {@code experiment} on both the small- and the large inputs,
	 * measuring the performance of each, and compare the performance to verify that
	 * it's not worse than the expected worst case multiplier.
	 *
	 * @param iterations the number of iterations to perform of the {@code experiment} on each input
	 * @param worstCaseMultiplier a multiplier on the measured timing of the small-scale scenario that is it maximal
	 *            acceptable timing of the large-scale scenario. Performance worse than this multiplier fails the test
	 * @param smallInput supplier of the small input. It can supply the same input or a different input on every
	 *            invocation
	 * @param largeInput supplier of the large-scale input. It can supply the same input or a different input on every
	 *            invocation
	 * @param before consumer to run before every experiment. May be <code>null</code>
	 * @param experiment
	 *            the experiment to run at each scale of input
	 * @param after consumer to run after every experiment. May be <code>null</code>
	 *
	 * @param <T> the type of input for the {@code experiment}
	 */
	public static <T> void test(int iterations, double worstCaseMultiplier, //
		Supplier<? extends T> smallInput, Supplier<? extends T> largeInput, //
		Consumer<? super T> before, Consumer<? super T> experiment, Consumer<? super T> after) {

		final Measure smallScale = time(iterations, bind(before, smallInput),
			bind(experiment, smallInput), bind(after, smallInput));
		System.out.println("Small scale: " + smallScale); //$NON-NLS-1$
		final Measure largeScale = time(iterations, bind(before, largeInput),
			bind(experiment, largeInput), bind(after, largeInput));
		System.out.println("Large scale: " + largeScale); //$NON-NLS-1$

		if (largeScale.average > worstCaseMultiplier * smallScale.average) {
			fail(String.format("Performance does not scale:%n\t%s ≫ %s", largeScale, smallScale)); //$NON-NLS-1$
		}
	}

	private static <T> Runnable bind(Consumer<? super T> hook, Supplier<T> input) {
		return hook == null
			? PerformanceClock::noop
			: () -> hook.accept(input.get());
	}

	private static void noop() {
		// do nothing
	}

	/** Average execution time and standard deviation of a performance experiment. */
	public static final class Measure {
		private final double average;
		private final double stddev;

		/**
		 * Creates a new time measure.
		 *
		 * @param average The recorded average execution time
		 * @param stddev The standard deviation
		 */
		public Measure(double average, double stddev) {
			super();

			this.average = average;
			this.stddev = stddev;
		}

		/**
		 * @return The average execution time
		 */
		public double average() {
			return average;
		}

		/**
		 * @return the standard deviation
		 */
		public double stddev() {
			return stddev;
		}

		@Override
		public String toString() {
			return String.format("%.1f ms (σ = %.2f ms)", average, stddev); //$NON-NLS-1$
		}
	}
}
