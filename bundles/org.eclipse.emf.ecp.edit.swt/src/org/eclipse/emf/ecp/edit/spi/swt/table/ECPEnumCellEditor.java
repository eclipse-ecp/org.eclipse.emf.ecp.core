/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * Common base class for combo-based enum cell editors.
 *
 * @since 1.13
 */
public abstract class ECPEnumCellEditor extends CellEditor implements ECPCellEditor {

	/**
	 * Constructor.
	 *
	 * @param parent the parent {@link Composite}
	 */
	public ECPEnumCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Constructor.
	 *
	 * @param parent the parent {@link Composite}
	 * @param style SWT style bits
	 */
	public ECPEnumCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Returns the {@link EEnum} is cell editor responsible for.
	 *
	 * @return the enum
	 */
	public abstract EEnum getEEnum();

	/**
	 * Returns the list of literals of the enum.
	 *
	 * @return a list of literals
	 *
	 * @see #getEEnum
	 */
	public List<EEnumLiteral> getELiterals() {
		final List<EEnumLiteral> filtered = new ArrayList<EEnumLiteral>();
		final EList<EEnumLiteral> eLiterals = getEEnum().getELiterals();
		for (final EEnumLiteral literal : eLiterals) {

			final String isInputtable = EcoreUtil.getAnnotation(literal,
				VViewPackage.eNS_URI,
				"isInputtable"); //$NON-NLS-1$

			if (isInputtable == null || Boolean.getBoolean(isInputtable)) {
				filtered.add(literal);
			}
		}
		return filtered;
	}
}
