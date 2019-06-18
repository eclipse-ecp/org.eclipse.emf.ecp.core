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
package org.eclipse.emfforms.ide.internal.builder;

/**
 * Nature for extensible validation of the model files in the project.
 */
public class ValidationNature extends ProjectNature {

	/** Project nature identifier. */
	public static final String NATURE_ID = "org.eclipse.emfforms.ide.builder.validationNature"; //$NON-NLS-1$

	/** A prototype for nature instances. */
	public static final ValidationNature PROTOTYPE = new ValidationNature();

	/**
	 * Initializes me.
	 */
	public ValidationNature() {
		super(NATURE_ID, ValidationBuilder.BUILDER_ID);
	}

}
