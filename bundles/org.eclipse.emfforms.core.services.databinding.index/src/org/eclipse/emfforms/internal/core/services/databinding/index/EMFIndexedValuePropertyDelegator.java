/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - moved, adjusted comments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.index;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.internal.EMFValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * This class provides a ValueProperty that supports addressing specific elements of a list by an index.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class EMFIndexedValuePropertyDelegator extends EMFValueProperty {

	private final int index;
	private final EditingDomain editingDomain;
	private final IValueProperty valueProperty;

	/**
	 * Constructor for an Index ValueProperty.
	 *
	 * @param editingDomain The {@link EditingDomain}
	 * @param index The index
	 * @param valueProperty the {@link IValueProperty} to the index
	 * @param eStructuralFeature
	 *            the {@link EStructuralFeature} of the indexed feature
	 */
	public EMFIndexedValuePropertyDelegator(EditingDomain editingDomain, int index, IValueProperty valueProperty,
		EStructuralFeature eStructuralFeature) {
		super(eStructuralFeature);
		this.editingDomain = editingDomain;
		this.valueProperty = valueProperty;
		if (index < 0) {
			throw new IllegalArgumentException(
				"\t \t \t \t \t Who thinks it's a good idea to use a negative list index?!"); //$NON-NLS-1$
		}
		this.index = index;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object doGetValue(Object source) {
		final Object result = valueProperty.getValue(source);
		final EList<Object> list = (EList<Object>) result;
		if (list == null || index >= list.size()) {
			return null;
		}
		return list.get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doSetValue(Object source, Object value) {
		final EObject eObject = (EObject) source;
		final Object result = valueProperty.getValue(source);
		final EList<Object> list = (EList<Object>) result;
		Command command;
		// FIXME allow add?
		if (index == list.size()) {
			command = AddCommand.create(editingDomain, eObject, getFeature(), value, index);
		} else {
			command = SetCommand.create(editingDomain, eObject, getFeature(), value, index);
		}
		editingDomain.getCommandStack().execute(command);
	}

	@Override
	public String toString() {
		String s = super.toString();
		s += " index " + index; //$NON-NLS-1$
		return s;
	}
}
