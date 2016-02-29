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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

/**
 *
 * @author Eugen Neufeld
 *
 */
public abstract class ValueSelectionHelper {

	/**
	 * Opens a dialog to select a possible value for a given {@link EStructuralFeature}.
	 * If the feature is a enum or boolean, it will show suggestions, otherwise, it will accept Strings.
	 * 
	 * @param shell The {@link Shell} to open the dialog on
	 * @param structuralFeature The {@link EStructuralFeature} to select possible values for
	 * @return The selected value as an {@link Object}
	 */
	public static Object openValueSelectionDialog(Shell shell, EStructuralFeature structuralFeature) {
		if (structuralFeature == null) {
			return null;
		}
		if (EReference.class.isInstance(structuralFeature)) {
			// TODO show all references
			return null;
		}
		final EAttribute attribute = (EAttribute) structuralFeature;
		Class<?> attribuetClazz = attribute.getEAttributeType().getInstanceClass();
		if (attribuetClazz.isPrimitive()) {
			if (int.class.isAssignableFrom(attribuetClazz)) {
				attribuetClazz = Integer.class;
			} else if (long.class.isAssignableFrom(attribuetClazz)) {
				attribuetClazz = Long.class;
			} else if (float.class.isAssignableFrom(attribuetClazz)) {
				attribuetClazz = Float.class;
			} else if (double.class.isAssignableFrom(attribuetClazz)) {
				attribuetClazz = Double.class;
			} else if (boolean.class.isAssignableFrom(attribuetClazz)) {
				attribuetClazz = Boolean.class;
			} else if (char.class.isAssignableFrom(attribuetClazz)) {
				attribuetClazz = Character.class;
			}
		}
		Object object = null;
		if (Enum.class.isAssignableFrom(attribuetClazz)) {
			final Object[] enumValues = attribuetClazz.getEnumConstants();
			final ListDialog ld = new ListDialog(shell);
			ld.setLabelProvider(new LabelProvider());
			ld.setContentProvider(ArrayContentProvider.getInstance());
			ld.setInput(enumValues);
			ld.setInitialSelections(new Object[] { enumValues[0] });
			ld.setMessage("Please select the enum value to set."); //$NON-NLS-1$
			ld.setTitle("Select a value"); //$NON-NLS-1$
			final int enumSelectionResult = ld.open();
			if (Window.OK == enumSelectionResult) {
				object = ld.getResult()[0];
			}
		} else if (String.class.isAssignableFrom(attribuetClazz)
			|| Number.class.isAssignableFrom(attribuetClazz) || Boolean.class.isAssignableFrom(attribuetClazz)) {
			try {
				final Constructor<?> constructor = attribuetClazz.getConstructor(String.class);
				final InputDialog id = new InputDialog(
					shell,
					"Insert the value", //$NON-NLS-1$
					"The value must be parseable by the " //$NON-NLS-1$
						+ attribuetClazz.getSimpleName()
						+ " class. For a double value please use the #.# format. For boolean values 'true' or 'false'.", //$NON-NLS-1$
					null, null);
				final int inputResult = id.open();
				if (Window.OK == inputResult) {
					object = constructor.newInstance(id.getValue());
				}
			} catch (final IllegalArgumentException ex) {

			} catch (final SecurityException ex) {

			} catch (final NoSuchMethodException ex) {
			} catch (final InstantiationException ex) {
			} catch (final IllegalAccessException ex) {
			} catch (final InvocationTargetException ex) {
			}
		} else {
			MessageDialog.openError(shell, "Not primitive Attribute selected", //$NON-NLS-1$
				"The selected attribute has a not primitive type. We can't provide you support for it!"); //$NON-NLS-1$
		}
		return object;
	}
}
