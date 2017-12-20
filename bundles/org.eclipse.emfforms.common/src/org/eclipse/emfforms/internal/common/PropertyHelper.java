/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.common;

import org.eclipse.emfforms.common.Property;

/**
 * Property Helper class.
 *
 * @author Mat Hansen
 * @since 1.15
 *
 */
public final class PropertyHelper {

	/**
	 * The constructor.
	 */
	private PropertyHelper() {
	}

	/**
	 * Invert the state of a Boolean property.
	 * Null values will be toggled to true.
	 *
	 * @param property the property to toggle
	 * @return the toggled property
	 */
	public static Property<Boolean> toggle(Property<Boolean> property) {
		property.setValue(!Boolean.valueOf(property.getValue()));
		return property;
	}

}
