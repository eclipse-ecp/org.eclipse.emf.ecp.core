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
package org.eclipse.emfforms.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Ranking helper class to generically handle prioritization use cases.
 * This class assumes that priorities are in ascending order (higher rank means higher priority).
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.14
 *
 * @param <T> the type for the rank comparison
 */
public class RankingHelper<T> {

	/**
	 * Value used to initialize the rank comparison in case no initialRank value is passed.
	 */
	public static final double DEFAULT_INITIAL_RANK = -1d;

	private final Class<?> elementClass;
	private final double initialRank;
	private final Double ignoreRankValue;

	/**
	 * Constructor which disables ignore rank feature.
	 *
	 * @param elementClass the class the prioritization is based on, required for logging purposes
	 * @param initialRank the initialization value for the rank comparison (see INITIAL_RANK)
	 */
	public RankingHelper(Class<?> elementClass, double initialRank) {
		this(elementClass, initialRank, null);
	}

	/**
	 * The main constructor.
	 *
	 * @param elementClass the class the prioritization is based on, required for logging purposes
	 * @param initialRank the initialization value for the rank comparison (see INITIAL_RANK)
	 * @param ignoreRankValue a specific value which will result in the given element being omitted.
	 *            Must be non-null otherwise this feature is not enabled.
	 */
	public RankingHelper(Class<?> elementClass, double initialRank, Double ignoreRankValue) {
		this.elementClass = elementClass;
		this.initialRank = initialRank;
		this.ignoreRankValue = ignoreRankValue;
	}

	/**
	 * Helper callback interface for rank testing.
	 *
	 * @param <T> the type for the rank comparison.
	 * @see RankingHelper
	 */
	public interface RankTester<T> {

		/**
		 * Callback method which should delegate to the actual ranking element.
		 *
		 * @param element the element to get the rank/priority for
		 * @return numeric priority value. Higher value means higher priority.
		 */
		double getRank(T element);

	}

	/**
	 * Determine the highest ranking element by iterating over all elements and calling rankTester.getRank().
	 *
	 * @param elements the elements to consider
	 * @param rankTester the tester instance to fetch a rank/priority for a given element
	 * @return the highest ranking element found (may be null)
	 */
	public T getHighestRankingElement(final Collection<T> elements,
		final RankTester<T> rankTester) {

		final List<T> highestRankingElements = new ArrayList<T>();
		double highestRank = initialRank;

		for (final T element : elements) {

			final double rank = rankTester.getRank(element);

			if (ignoreRankValue != null && ignoreRankValue.equals(rank)) {
				continue;
			} else if (rank >= highestRank) {
				if (rank > highestRank) {
					highestRank = rank;
					highestRankingElements.clear();
				}
				highestRankingElements.add(element);
			}

		}

		if (highestRankingElements.size() > 1) {
			reportClashingPriorities(createWarning(highestRankingElements, highestRank));
		}

		if (highestRankingElements.isEmpty()) {
			return null;
		}
		return highestRankingElements.get(0);
	}

	/**
	 * Create warning about clashing priorities.
	 *
	 * @param highestRankingElements list of clashing elements
	 * @param rank the rank of the elements
	 * @return the warning message
	 */
	protected String createWarning(List<T> highestRankingElements, double rank) {
		final String warning = "Priority clash while trying to find highest ranking instance of {1}.\n" + //$NON-NLS-1$
			"Instance {2} clashes with {3}{4,choice, 0#|0< and {4,number,integer} more}, reporting rank {0,number}."; //$NON-NLS-1$

		final Object[] arguments = new Object[] {
			rank,
			elementClass,
			highestRankingElements.get(0).getClass(),
			highestRankingElements.get(1).getClass(),
			highestRankingElements.size() - 2
		};

		return MessageFormat.format(warning, arguments);
	}

	/**
	 * Report clashing priorities using ReportService.
	 *
	 * @param warningMessage the warning message
	 */
	protected void reportClashingPriorities(String warningMessage) {

		final Bundle bundle = FrameworkUtil.getBundle(getClass());
		if (bundle != null) {

			// we fetch the report service for every error individually
			// because this shouldn't happen very often
			final BundleContext bundleContext = bundle.getBundleContext();
			final ServiceReference<ReportService> serviceReference = bundleContext
				.getServiceReference(ReportService.class);
			final ReportService reportService = bundleContext.getService(serviceReference);

			reportService
				.report(new AbstractReport(warningMessage, 2));

			bundleContext.ungetService(serviceReference);

		} else {
			// we still want to complain in case we have no OSGi context
			System.err.println(warningMessage);
		}

	}

}
