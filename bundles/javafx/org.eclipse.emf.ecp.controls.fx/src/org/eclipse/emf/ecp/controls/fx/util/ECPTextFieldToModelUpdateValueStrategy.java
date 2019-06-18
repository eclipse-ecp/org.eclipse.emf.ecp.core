/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.fx.util;

import org.eclipse.emf.databinding.EMFUpdateValueStrategy;

/**
 * ECPTextFieldToModelUpdateValueStrategy for text fields.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPTextFieldToModelUpdateValueStrategy extends
	EMFUpdateValueStrategy {
	/**
	 * Default constructor.
	 */
	public ECPTextFieldToModelUpdateValueStrategy() {
		super(POLICY_ON_REQUEST);
	}

	@Override
	public Object convert(Object value) {
		if (value == null || "".equals(value)) { //$NON-NLS-1$
			return null;
		}
		return super.convert(value);
	}
}
