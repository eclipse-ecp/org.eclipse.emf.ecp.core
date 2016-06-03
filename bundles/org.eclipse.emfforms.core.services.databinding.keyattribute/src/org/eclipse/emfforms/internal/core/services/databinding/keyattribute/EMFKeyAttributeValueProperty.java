/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.keyattribute;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.internal.EMFValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;

/**
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class EMFKeyAttributeValueProperty extends EMFValueProperty {

	private final EditingDomain editingDomain;
	private final EMFFormsDatabindingEMF databinding;
	private final VDomainModelReference keyDMR;
	private final Object key;

	/**
	 * @param editingDomain The {@link EditingDomain}
	 * @param databinding The {@link EMFFormsDatabindingEMF emf databinding service}
	 * @param keyDMR The reference to the key field
	 * @param key The key
	 * @param eStructuralFeature The {@link EStructuralFeature} of the key attribute feature
	 */
	public EMFKeyAttributeValueProperty(EditingDomain editingDomain, EMFFormsDatabindingEMF databinding,
		VDomainModelReference keyDMR, Object key, EStructuralFeature eStructuralFeature) {
		super(eStructuralFeature);
		this.editingDomain = editingDomain;
		this.databinding = databinding;
		this.keyDMR = keyDMR;
		this.key = key;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.databinding.internal.EMFValueProperty#doGetValue(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object doGetValue(Object source) {
		final Object object = super.doGetValue(source);
		final EList<Object> list = (EList<Object>) object;
		if (list == null) {
			return null;
		}

		// If it exists, find the EObject that contains the key.
		for (int i = 0; i < list.size(); i++) {
			final EObject eObject = (EObject) list.get(i);
			Setting setting;
			try {
				setting = databinding.getSetting(keyDMR, eObject);
			} catch (final DatabindingFailedException ex) {
				// TODO report needed?
				continue;
			}
			if (key.equals(setting.get(true))) {
				// key was present in the current EObject
				return eObject;
			}
		}

		// return null if the key was not present in any EObject
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.databinding.internal.EMFValueProperty#doSetValue(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doSetValue(Object source, Object value) {
		final EObject newValue = (EObject) value;
		Setting setting;
		try {
			setting = databinding.getSetting(keyDMR, newValue);
		} catch (final DatabindingFailedException ex) {
			throw new IllegalArgumentException("The new value's key could not be resolved.", ex); //$NON-NLS-1$
		}
		if (setting == null || !key.equals(setting.get(true))) {
			throw new IllegalArgumentException("The new value must contain the key '" + key + "'."); //$NON-NLS-1$//$NON-NLS-2$
		}

		final Object oldValue = doGetValue(source);
		Command command = null;
		if (oldValue == null) {
			// no EObject in the list contains the key => add new EObject
			command = AddCommand.create(editingDomain, source, getFeature(), newValue);
		} else {
			// An EObject in the list contains the key => Replace old EObject
			final Object object = super.doGetValue(source);
			final EList<Object> list = (EList<Object>) object;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(oldValue)) {
					command = SetCommand.create(editingDomain, source, getFeature(), newValue, i);
					break;
				}
			}
		}

		if (command != null) {
			editingDomain.getCommandStack().execute(command);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.databinding.internal.EMFValueProperty#toString()
	 */
	@Override
	public String toString() {
		final String s = super.toString();
		return s + " key '" + key + "'"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
