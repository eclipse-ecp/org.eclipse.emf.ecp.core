/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Default EMF implementation of the {@link DeleteService}. Uses {@link RemoveCommand} and {@link DeleteCommand}
 * respectively.
 *
 * @author jfaltermeier
 * @since 1.6
 *
 */
public class EMFDeleteServiceImpl implements DeleteService {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		// no op
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		// no op
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 1;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.DeleteService#deleteElements(org.eclipse.emf.edit.domain.EditingDomain,
	 *      java.util.Collection)
	 */
	@Override
	public void deleteElements(EditingDomain editingDomain, Collection<Object> toDelete) {
		final Command deleteCommand = DeleteCommand.create(editingDomain, toDelete);
		if (deleteCommand.canExecute()) {
			deleteCommand.execute();
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.DeleteService#deleteElement(org.eclipse.emf.edit.domain.EditingDomain,
	 *      java.lang.Object)
	 */
	@Override
	public void deleteElement(EditingDomain editingDomain, Object toDelete) {
		final Command deleteCommand = DeleteCommand.create(editingDomain, toDelete);
		if (deleteCommand.canExecute()) {
			deleteCommand.execute();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.DeleteService#removeElements(org.eclipse.emf.edit.domain.EditingDomain,
	 *      java.lang.Object, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	@Override
	public void removeElements(EditingDomain editingDomain, Object source, EStructuralFeature feature,
		Collection<Object> toRemove) {
		final Command removeCommand = RemoveCommand.create(editingDomain, source, feature, toRemove);
		if (removeCommand.canExecute()) {
			removeCommand.execute();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.DeleteService#removeElement(org.eclipse.emf.edit.domain.EditingDomain,
	 *      java.lang.Object, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@Override
	public void removeElement(EditingDomain editingDomain, Object source, EStructuralFeature feature, Object toRemove) {
		if (feature.isMany()) {
			removeElements(editingDomain, source, feature, Collections.singleton(toRemove));
		} else {
			final Command command = SetCommand.create(editingDomain, source, feature, SetCommand.UNSET_VALUE);
			if (command.canExecute()) {
				command.execute();
			}
		}

	}

}
