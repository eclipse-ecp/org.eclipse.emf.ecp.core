/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.spi.context;

import org.eclipse.emf.ecp.view.spi.model.VControl;

/**
 * Represents a compare pair of two controls.
 *
 * @author Eugen Neufeld
 *
 */
public class ControlPair {
	private final VControl leftControl;
	private final VControl rightControl;

	/**
	 * The left compare of the compare.
	 *
	 * @return the leftControl
	 */
	public VControl getLeftControl() {
		return leftControl;
	}

	/**
	 * The right control of the compare.
	 *
	 * @return the rightControl
	 */
	public VControl getRightControl() {
		return rightControl;
	}

	/**
	 * Creates the pair.
	 *
	 * @param left the left {@link VControl}
	 * @param right the right {@link VControl}
	 */
	public ControlPair(VControl left, VControl right) {
		leftControl = left;
		rightControl = right;
	}
}