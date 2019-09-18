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
package org.eclipse.emfforms.spi.core.services.reveal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Helper utility for common revealing patterns.
 *
 * @since 1.22
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface RevealHelper {

	/**
	 * Create an intermediate step that drills down into the best fitting of the children
	 * of the view-model element in the current scope.
	 *
	 * @param drillDownStep a computation of the drill-down step. Must have a
	 *            method annotated with {@link Reveal @Reveal} and result type conforming to
	 *            {@link RevealStep}
	 *
	 * @return the intermediate drill-down step
	 * @see #drillDown(Object, Object)
	 */
	default RevealStep drillDown(Object drillDownStep) {
		return drillDown(drillDownStep, null);
	}

	/**
	 * Create an intermediate step that drills down into the best fitting of the children
	 * of the view-model element in the current scope.
	 * In addition to the usual injections, the drill-down step computation is also provided
	 * the {@link RevealStep} that is the next step in the sequence, revealing the object in
	 * whatever child of the parent element the computed step reveals.
	 *
	 * @param drillDownStep a computation of the drill-down step. Must have a
	 *            method annotated with {@link Reveal @Reveal} and result type conforming to
	 *            {@link RevealStep}
	 * @param childrenFunction an optional function returning the subset of the {@code element}'s
	 *            children to consider for drilling down into. If omitted, all of the
	 *            {@link EObject#eContents()} of the {@code element} that are
	 *            {@link VElement}s are implied. Must have a method annotated with
	 *            {@link DrillDown @DrillDown} and result type conforming to
	 *            {@link Iterable Iterable&lt;? extends VElement&gt;}
	 *
	 * @return the intermediate drill-down step
	 */
	RevealStep drillDown(Object drillDownStep, Object childrenFunction);

	/**
	 * Create a deferred reveal step that will attempt to reveal the domain model object
	 * in the current context.
	 *
	 * @param deferredStep a computation of the deferred reveal step. Must have a
	 *            method annotated with {@link Reveal @Reveal} and result type conforming to
	 *            {@link RevealStep}
	 *
	 * @return the deferred terminal reveal step
	 */
	RevealStep defer(Object deferredStep);

	/**
	 * Create a master/detail reveal step that will reveal the master of the object to be
	 * revealed in the current context and then reveal that detail object, if it is a detail
	 * of some master.
	 *
	 * @param masterStep in the case that a master object is found to reveal, a computation of the
	 *            reveal step for the master selection. Must have a method annotated with
	 *            {@link Reveal @Reveal} and result type conforming to {@link RevealStep}
	 * @param masterFunction a function to compute the master object of which the {@code object} is a
	 *            detail in the presentation of the given {@code element}. If the function returns either
	 *            the input {@link EObject} domain model object or {@code null}, then that input is
	 *            considered to be a master itself, and not a detail. Must have a method annotated with
	 *            {@link DrillUp @DrillUp} and result type conforming to {@link EObject}
	 *
	 * @return the master/detail reveal step
	 */
	RevealStep masterDetail(Object masterStep, Object masterFunction);

}
