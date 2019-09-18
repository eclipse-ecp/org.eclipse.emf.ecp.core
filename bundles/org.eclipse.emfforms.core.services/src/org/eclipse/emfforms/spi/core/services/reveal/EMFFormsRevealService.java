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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * A service that mediates requests to reveal (navigate to) objects in an
 * EMF Forms Editor with providers that know how to fulfil those requests.
 *
 * @since 1.22
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface EMFFormsRevealService {

	/**
	 * Attempt to reveal an {@code object} in the most appropriate (by best effort)
	 * control in the current editor context. Effectively equivalent to
	 * {@link #reveal(EObject, VElement)} in the topmost scope (the root {@link VView})
	 * of the context.
	 *
	 * @param object an object to reveal
	 * @return {@code true} if the {@code object} was revealed; {@code false}, otherwise
	 */
	boolean reveal(EObject object);

	/**
	 * Attempt to reveal a {@code feature} of an {@code object} in the most appropriate
	 * (by best effort) control in the current editor context. Effectively equivalent to
	 * {@link #reveal(EObject, EStructuralFeature, VElement)} in the topmost scope
	 * (the root {@link VView}) of the context.
	 *
	 * @param object an object to reveal
	 * @param feature a specific feature (implying a detail control) to reveal
	 * @return {@code true} if the {@code object} was revealed; {@code false}, otherwise
	 */
	boolean reveal(EObject object, EStructuralFeature feature);

	/**
	 * Attempt to reveal an {@code object} in the most appropriate (by best effort)
	 * control within the given {@code scope}. Most applications will not need to interact
	 * with reveal steps directly but only through the {@link #reveal(EObject)} API;
	 * {@linkplain EMFFormsRevealProvider reveal providers} may need this API to delegate
	 * a {@linkplain RevealStep#drillDown(VElement, EObject, RevealStep, Runnable) drill down}.
	 *
	 * @param object an object to reveal
	 * @param scope a control within which to attempt to reveal the {@code object}
	 *
	 * @return a step-wise chain of operations to progressively reveal the {@code object}
	 *         in the given {@code scope}, or {@linkplain RevealStep#fail() a failed step} if none
	 */
	RevealStep reveal(EObject object, VElement scope);

	/**
	 * Attempt to reveal a {@code feature} of an {@code object} in the most appropriate
	 * (by best effort) control within the given {@code scope}. Most applications will not need
	 * to interact with reveal steps directly but only through the
	 * {@link #reveal(EObject, EStructuralFeature)} API;
	 * {@linkplain EMFFormsRevealProvider reveal providers} may need this API to delegate
	 * a {@linkplain RevealStep#drillDown(VElement, EObject, RevealStep, Runnable) drill down}.
	 *
	 * @param object an object to reveal
	 * @param feature a specific feature (implying a detail control) to reveal
	 * @param scope a control within which to attempt to reveal the {@code object}
	 *
	 * @return a step-wise chain of operations to progressively reveal the {@code object}
	 *         in the given {@code scope}, or {@linkplain RevealStep#fail() a failed step} if none
	 */
	RevealStep reveal(EObject object, EStructuralFeature feature, VElement scope);

	/**
	 * Register a reveal provider.
	 *
	 * @param provider the reveal provider to register
	 */
	void addRevealProvider(EMFFormsRevealProvider provider);

	/**
	 * Unregister a reveal provider.
	 *
	 * @param provider the reveal provider to unregister
	 */
	void removeRevealProvider(EMFFormsRevealProvider provider);

}
