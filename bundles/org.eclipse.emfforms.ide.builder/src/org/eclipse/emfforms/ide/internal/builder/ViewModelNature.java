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
 * EclipseSource - initial API and implementation
 * Christian W. Damus - bug 544499
 ******************************************************************************/
package org.eclipse.emfforms.ide.internal.builder;

/**
 * Nature for the View model projects.
 */
public class ViewModelNature extends ProjectNature {

	/** Project nature identifier. */
	public static final String NATURE_ID = "org.eclipse.emfforms.ide.builder.viewModelNature"; //$NON-NLS-1$

	/** A prototype for nature instances. */
	public static final ViewModelNature PROTOTYPE = new ViewModelNature();

	/**
	 * Initializes me.
	 */
	public ViewModelNature() {
		super(NATURE_ID, ViewModelBuilder.BUILDER_ID);
	}

}
