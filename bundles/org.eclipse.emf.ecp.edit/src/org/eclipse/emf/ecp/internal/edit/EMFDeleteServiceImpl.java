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
package org.eclipse.emf.ecp.internal.edit;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Default EMF implementation of the {@link DeleteService}. Uses {@link DeleteCommand}
 * respectively.
 *
 * @author jfaltermeier
 *
 */
public class EMFDeleteServiceImpl implements DeleteService {

	private EditingDomain editingDomain;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		editingDomain = AdapterFactoryEditingDomain
			.getEditingDomainFor(context.getDomainModel());
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
	 * @see org.eclipse.emf.ecp.internal.edit.DeleteService#deleteElements(java.util.Collection)
	 */
	@Override
	public void deleteElements(Collection<Object> toDelete) {
		if (toDelete == null || toDelete.isEmpty()) {
			return;
		}

		if (editingDomain == null) {
			deleteWithoutEditingDomain(toDelete);
			return;
		}

		final Command deleteCommand = DeleteCommand.create(editingDomain, toDelete);
		if (deleteCommand.canExecute()) {
			if (editingDomain.getCommandStack() == null) {
				deleteCommand.execute();
			} else {
				editingDomain.getCommandStack().execute(deleteCommand);
			}
		}
	}

	private void deleteWithoutEditingDomain(Collection<Object> toDelete) {
		for (final Object object : toDelete) {
			final Object unwrap = AdapterFactoryEditingDomain.unwrap(object);
			if (!EObject.class.isInstance(unwrap)) {
				continue;
			}
			EcoreUtil.delete(EObject.class.cast(unwrap), true);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.internal.edit.DeleteService#deleteElement(java.lang.Object)
	 */
	@Override
	public void deleteElement(Object toDelete) {
		if (toDelete == null) {
			return;
		}
		/* delete command for collections/single object works the same */
		deleteElements(Collections.singleton(toDelete));
	}

}
