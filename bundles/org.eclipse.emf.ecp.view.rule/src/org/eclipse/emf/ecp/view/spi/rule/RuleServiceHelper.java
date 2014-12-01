/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule;

import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * @author Eugen Neufeld
 * @since 1.2
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 *
 */
public interface RuleServiceHelper {

	/**
	 * Gets the involved {@link org.eclipse.emf.ecore.EObject EObject}s that would be hidden
	 * or disabled if {@code newValue} would be set.
	 *
	 * @param <T>
	 *            the type of the returned {@link org.eclipse.emf.ecore.EObject EObject}s
	 *
	 * @param setting
	 *            the current setting
	 * @param newValue
	 *            the new value which may be set
	 * @param renderableClass
	 *            the class type that has to be matched. Used for filtering the result set
	 * @return the involved {@link VElement}s that match the given type {@code T}
	 * @since 1.5
	 */
	<T extends VElement> Set<T> getInvolvedEObjects(Setting setting, Object newValue, Class<T> renderableClass);
}
