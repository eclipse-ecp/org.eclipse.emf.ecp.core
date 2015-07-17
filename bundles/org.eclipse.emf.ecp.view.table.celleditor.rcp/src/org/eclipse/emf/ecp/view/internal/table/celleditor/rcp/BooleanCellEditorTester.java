/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.celleditor.rcp;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * Tester for {@link BooleanCellEditor}.
 *
 * @author jfaltermeier
 *
 */
public class BooleanCellEditorTester implements ECPCellEditorTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see ECPCellEditorTester#isApplicable(EObject, EStructuralFeature, ViewModelContext)
	 */
	@Override
	public int isApplicable(EObject eObject, EStructuralFeature feature, ViewModelContext viewModelContext) {
		if (EAttribute.class.isInstance(feature)) {
			final Class<?> instanceClass = ((EAttribute) feature).getEAttributeType().getInstanceClass();
			if (instanceClass == null) {
				return NOT_APPLICABLE;
			}
			if (Boolean.class.isAssignableFrom(instanceClass)) {
				return 3;
			}
			if (instanceClass.isPrimitive()) {
				try {
					final Class<?> primitive = (Class<?>) Boolean.class.getField("TYPE").get(null); //$NON-NLS-1$
					if (primitive.equals(instanceClass)) {
						return 3;
					}
					return NOT_APPLICABLE;
				} catch (final IllegalArgumentException e) {
					return NOT_APPLICABLE;
				} catch (final SecurityException e) {
					return NOT_APPLICABLE;
				} catch (final IllegalAccessException e) {
					return NOT_APPLICABLE;
				} catch (final NoSuchFieldException e) {
					return NOT_APPLICABLE;
				}
			}

		}
		return NOT_APPLICABLE;
	}
}
