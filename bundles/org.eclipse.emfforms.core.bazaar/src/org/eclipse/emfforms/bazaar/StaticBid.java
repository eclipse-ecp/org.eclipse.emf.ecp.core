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
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Allows a vendor to do a static bid. Will be called only if {@link Precondition}s are met. Not {@link Bid} will be
 * called after a {@link StaticBid}.
 *
 * @author jonas
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface StaticBid {
	/**
	 *
	 * @return the static bid
	 */
	double bid();

}
