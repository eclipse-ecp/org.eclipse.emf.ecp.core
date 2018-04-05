/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to allow {@link Vendor}s to specify an array of {@link Precondition}s.
 *
 * @author jonas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface Preconditions {
	/**
	 * An array of {@link Precondition}s.
	 *
	 * @return An array of {@link Precondition}s
	 */
	Precondition[] preconditions();
}
