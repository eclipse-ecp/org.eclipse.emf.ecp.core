/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.test.common.spi;

/**
 * Convenience class for returning multiple values at once.
 * 
 * @author emueller
 **/
public class Tuple<T, U> {

	public static <A, B> Tuple<A, B> create(A first, B second) {
		return new Tuple<A, B>(first, second);
	}

	private final T t;
	private final U u;

	public Tuple(T t, U u) {
		this.t = t;
		this.u = u;
	}

	public T first() {
		return t;
	}

	public U second() {
		return u;
	}
}