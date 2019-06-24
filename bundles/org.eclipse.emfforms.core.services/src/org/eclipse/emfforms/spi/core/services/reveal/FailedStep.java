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

/**
 * The failed reveal step.
 *
 * @since 1.22
 */
final class FailedStep implements RevealStep {

	/**
	 * Initializes me.
	 */
	FailedStep() {
		super();
	}

	@Override
	public RevealStepKind getType() {
		return RevealStepKind.FAILED;
	}

	@Override
	public VElement getViewModel() {
		return null;
	}

	@Override
	public EObject getDomainModel() {
		return null;
	}

	@Override
	public EStructuralFeature getFeature() {
		return null;
	}

	@Override
	public RevealStep drillDown() {
		throw new IllegalStateException("reveal failed"); //$NON-NLS-1$
	}

	@Override
	public void reveal() {
		throw new IllegalStateException("reveal failed"); //$NON-NLS-1$
	}

}
