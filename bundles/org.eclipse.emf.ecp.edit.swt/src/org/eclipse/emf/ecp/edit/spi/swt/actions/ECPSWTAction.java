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
package org.eclipse.emf.ecp.edit.spi.swt.actions;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;

/**
 * An abstract action used by ecp.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public abstract class ECPSWTAction extends Action {
	private final EditingDomain editingDomain;
	private final Setting setting;

	/**
	 * The constructor of all ecp actions.
	 *
	 * @param editingDomain the {@link EditingDomain} to use
	 * @param setting the {@link Setting} to use
	 */
	public ECPSWTAction(EditingDomain editingDomain, Setting setting) {
		this.editingDomain = editingDomain;
		this.setting = setting;

	}

	/**
	 * The set {@link Setting}.
	 *
	 * @return the {@link Setting}
	 */
	protected Setting getSetting() {
		return setting;
	}

	/**
	 * The set {@link EditingDomain}.
	 *
	 * @return the {@link EditingDomain}
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getClass().getName().hashCode() * setting.getEStructuralFeature().getName().hashCode();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!other.getClass().isInstance(this)) {
			return false;
		}
		final ECPSWTAction otherAction = (ECPSWTAction) other;
		return setting.getEStructuralFeature().getName().equals(otherAction.setting.getEStructuralFeature().getName());
	}
}
