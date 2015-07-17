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
 * Lucas Koehler - moved/copied here from org.eclipse.emf.ecp.view.mappingdmr.databinding
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.mapping;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.databinding.internal.EMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * This class provides a ValueProperty for EClass Mappings.
 *
 * @author Eugen Neufeld
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class EMFMappingValueProperty extends EMFValueProperty {

	private final EClass mappedEClass;
	private final EditingDomain editingDomain;

	/**
	 * Constructor for a EClassMapping ValueProperty.
	 *
	 * @param editingDomain The {@link EditingDomain}
	 * @param mappedEClass the EClass being mapped
	 * @param eStructuralFeature the {@link EStructuralFeature} of the map
	 */
	public EMFMappingValueProperty(EditingDomain editingDomain, EClass mappedEClass,
		EStructuralFeature eStructuralFeature) {
		super(eStructuralFeature);
		this.editingDomain = editingDomain;
		this.mappedEClass = mappedEClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object doGetValue(Object source) {
		final Object result = super.doGetValue(source);
		final EMap<EClass, Object> map = (EMap<EClass, Object>) result;
		return map.get(mappedEClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doSetValue(Object source, final Object value) {
		final Object result = super.doGetValue(source);
		final EObject eObject = (EObject) source;
		final ChangeCommand command = new ChangeCommand(eObject) {

			@Override
			protected void doExecute() {
				final EMap<EClass, Object> map = (EMap<EClass, Object>) result;
				map.put(mappedEClass, value);
			}
		};
		editingDomain.getCommandStack().execute(command);
	}

	@Override
	public String toString() {
		String s = super.toString();
		s += " mapping " + mappedEClass.getName(); //$NON-NLS-1$
		return s;
	}
}
