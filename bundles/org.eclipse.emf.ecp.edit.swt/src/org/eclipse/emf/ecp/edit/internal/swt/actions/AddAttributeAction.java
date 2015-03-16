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
package org.eclipse.emf.ecp.edit.internal.swt.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;

/**
 * The action to allow adding of attribute values to multi attribute controls.
 *
 * @author Eugen Neufeld
 *
 */
public class AddAttributeAction extends ECPSWTAction {

	/**
	 * The constructor for the add attribute action.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param setting the {@link Setting} to use
	 */
	public AddAttributeAction(EditingDomain editingDomain, Setting setting) {
		super(editingDomain, setting);
		// TODO remove PlatformUI
		setImageDescriptor(Activator.getImageDescriptor("icons/add.png")); //$NON-NLS-1$
		setToolTipText(LocalizationServiceHelper.getString(getClass(), ActionMessageKeys.AddAttributeAction_AddEntry));
	}

	@Override
	public void run() {
		super.run();
		if (getEditingDomain() == null) {
			return;
		}
		// TODO show message if something goes wrong
		Object defaultValue = getSetting().getEStructuralFeature().getEType().getDefaultValue();
		if (defaultValue == null) {
			try {
				defaultValue = getSetting().getEStructuralFeature().getEType().getInstanceClass().getConstructor()
					.newInstance();
			} catch (final InstantiationException e) {
				Activator.logException(e);
			} catch (final IllegalAccessException e) {
				Activator.logException(e);
			} catch (final IllegalArgumentException e) {
				Activator.logException(e);
			} catch (final InvocationTargetException e) {
				Activator.logException(e);
			} catch (final NoSuchMethodException e) {
				Activator.logException(e);
			} catch (final SecurityException e) {
				Activator.logException(e);
			}
		}
		getEditingDomain()
			.getCommandStack()
			.execute(
				AddCommand.create(getEditingDomain(), getSetting().getEObject(), getSetting().getEStructuralFeature(),
					defaultValue));

	}
}
