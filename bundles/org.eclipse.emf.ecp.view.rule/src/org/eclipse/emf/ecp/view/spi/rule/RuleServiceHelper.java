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

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * @author Eugen Neufeld
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
	 *            the setting
	 * @param newValue
	 *            the new value
	 * @param attribute the {@link EAttribute} to find involved {@link VElement VElements} for
	 * @param renderableClass
	 *            the class type that has to be matched. Used for filtering the result set
	 * @return the involved {@link VElement}s that match the given type {@code T}
	 */
	<T extends VElement> Set<T> getInvolvedEObjects(Setting setting, Object newValue, EAttribute attribute,
		Class<T> renderableClass);

	/**
	 * Gets the involved {@link org.eclipse.emf.ecore.EObject EObject}s that would be hidden
	 * or disabled if {@code possibleNewValues} would be set.
	 * 
	 * @param <T>
	 *            the type of the returned {@link org.eclipse.emf.ecore.EObject EObjects}
	 * 
	 * @param possibleNewValues
	 *            a mapping of settings to their would-be new value
	 * @param renderableClass
	 *            the class type that has to be matched. Used for filtering the result set
	 * @param changedAttribute the attribute that was changed
	 * @return the involved {@link VElement}s that match the given type {@code T}
	 */
	<T extends VElement> Set<T> getInvolvedEObjects(Map<Setting, Object> possibleNewValues,
		EAttribute changedAttribute,
		Class<T> renderableClass);
}
