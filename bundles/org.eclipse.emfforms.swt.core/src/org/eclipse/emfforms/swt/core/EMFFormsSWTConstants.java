/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.core;

/**
 * Constants which may be used by SWT related renderers.
 *
 * @author Johannes Faltermeier
 * @since 1.9
 *
 */
public final class EMFFormsSWTConstants {

	private EMFFormsSWTConstants() {
		// empty
	}

	/**
	 * Key used to pass a value indicating if a renderer should setup databinding using on modify events or on focus
	 * out event.
	 */
	public static final String USE_ON_MODIFY_DATABINDING_KEY = "useOnModifyDatabinding"; //$NON-NLS-1$

	/**
	 * Value for the {@link #USE_ON_MODIFY_DATABINDING_KEY} indicating the modify events should be used.
	 */
	public static final String USE_ON_MODIFY_DATABINDING_VALUE = "true"; //$NON-NLS-1$

}
