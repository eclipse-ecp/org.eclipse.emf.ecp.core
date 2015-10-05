/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Johannes Faltermeier - Bug 459998
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;

/**
 * The TooltipModifier allows to manipulate a provided String.
 *
 * @author Eugen Neufeld
 * @author Johannes Faltermeier
 * @since 1.3
 */
public interface ECPStringModifier {

	/**
	 * Modifies a string.
	 *
	 * @param text the text to modify
	 * @param setting the Setting used to generate this text
	 * @return the modified text
	 */
	String modifyString(String text, Setting setting);

	/**
	 * Returns the priority of this modifier. A lower priority means that the {@link #modifyString(String, Setting)}
	 * method of this modifier is executed before modifiers with higher priority.
	 *
	 * @return the priority
	 *
	 * @since 1.7
	 */
	double getPriority();
}
