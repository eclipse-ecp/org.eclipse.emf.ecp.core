/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.rule;

import org.eclipse.emf.ecore.impl.EAttributeImpl;

/**
 * Custom attribute used by the {@link RuleRegistry} to capture the fact
 * that a {@link org.eclipse.emf.ecp.view.spi.rule.model.Rule} may not have condition.
 *
 * @author emueller
 *
 */
public class AllEAttributes extends EAttributeImpl {

	private static AllEAttributes allEAttributes = new AllEAttributes();

	/**
	 * Returns the singleton instance.
	 *
	 * @return the instance
	 */
	public static AllEAttributes get() {
		return allEAttributes;
	}
}
