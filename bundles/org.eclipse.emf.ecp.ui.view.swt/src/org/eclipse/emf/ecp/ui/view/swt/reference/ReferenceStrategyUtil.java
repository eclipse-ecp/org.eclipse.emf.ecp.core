/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.ui.view.swt.reference;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.spi.bazaar.BazaarUtil;
import org.osgi.service.component.ComponentContext;

/**
 * Common functionality for the creation strategies for the DefaultReferenceService.
 *
 * @author Lucas Koehler
 * @since 1.17
 *
 */
public final class ReferenceStrategyUtil {
	/**
	 * Prevent instantiation of this utility class.
	 */
	private ReferenceStrategyUtil() {
	}

	/**
	 * Creates an {@link EClassSelectionStrategy} which uses all {@link EClassSelectionStrategy
	 * EClassSelectionStrategies} registered to the given bazaar. When
	 * {@link EClassSelectionStrategy#collectEClasses(EObject, EReference, Collection)} of the created strategy is
	 * called, all applicable strategies are applied one after another.
	 *
	 * @param bazaar The {@link Bazaar} providing the {@link EClassSelectionStrategy EClassSelectionStrategies}
	 * @param context The receiver's ComponentContext
	 * @return The dynamic composite {@link EClassSelectionStrategy}
	 */
	public static EClassSelectionStrategy createDynamicEClassSelectionStrategy(
		final Bazaar<EClassSelectionStrategy> bazaar, final ComponentContext context) {
		return new EClassSelectionStrategy() {

			@Override
			public Collection<EClass> collectEClasses(EObject owner, EReference reference,
				Collection<EClass> eclasses) {

				Collection<EClass> result = eclasses;

				final List<EClassSelectionStrategy> delegates = bazaar.createProducts(
					createBazaarContext(context, owner, reference));
				// sort from low to high
				Collections.reverse(delegates);

				for (final EClassSelectionStrategy next : delegates) {
					result = next.collectEClasses(owner, reference, result);
				}

				return result;
			}
		};
	}

	/**
	 * Creates a basic {@link BazaarContext} that contains the properties of the {@link ComponentContext}. Adds the
	 * <code>owner</code> as a context value for class {@link EObject} and the <code>reference</code> for class
	 * {@link EReference}.
	 *
	 * @param context The {@link ComponentContext}
	 * @param owner The {@link EObject} containing a reference
	 * @param reference The {@link EReference}
	 * @return The configured {@link BazaarContext}
	 */
	public static BazaarContext createBazaarContext(ComponentContext context, EObject owner, EReference reference) {
		return BazaarUtil.createBaseContext(context.getProperties())
			.put(EObject.class, owner)
			.put(EReference.class, reference)
			.build();
	}
}
