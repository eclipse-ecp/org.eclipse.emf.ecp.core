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
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to allow {@link Vendor}s to specify a {@link Precondition}, which must be present in the
 * {@link BazaarContext}. A {@link Precondition} specifies a key and a value, This key value pair must be present in the
 * {@link BazaarContext}, otherwise the {@link Vendor} is not asked for a {@link Bid}.
 *
 * @author jonas
 *
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Precondition {
	/**
	 * default for values. If no value is used, just the existence of the key is checked.
	 */
	String UNASSIGNED = "[unassigned]"; //$NON-NLS-1$

	/**
	 *
	 * @return the key to be present in the {@link BazaarContext}
	 */
	String key();

	/**
	 *
	 * @return the value to be present in the {@link BazaarContext} under the specified key. Default is
	 *         {@link Precondition}.UNASSIGNED
	 */
	String value() default UNASSIGNED;
}
