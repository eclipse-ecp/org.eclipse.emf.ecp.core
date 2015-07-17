/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.core.util;

import org.eclipse.emf.ecp.core.util.ECPElement;

/**
 * @author Eike Stepper
 */
public abstract class Element implements ECPElement {
	private final String name;

	/**
	 * The constructor of an {@link Element}.
	 *
	 * @param name the name of the created element
	 */
	public Element(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		this.name = name;
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(ECPElement o) {
		return name.compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		result = prime * result + getType().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (obj instanceof Element) {
			final Element that = (Element) obj;
			return getType().equals(that.getType()) && name.equals(that.getName());
		}

		return false;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * This return the type of the object.
	 *
	 * @return the type
	 */
	public abstract String getType();
}
