/*******************************************************************************
 * Copyright (c) 2011-2020 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bugs 552385, 559267
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Default EMF implementation of the {@link DeleteService}. Uses {@link DeleteCommand}
 * respectively.
 *
 * @author jfaltermeier
 * @since 1.6
 *
 */
public class EMFDeleteServiceImpl implements ConditionalDeleteService {

	private EditingDomain editingDomain;

	@Override
	public void instantiate(ViewModelContext context) {
		editingDomain = AdapterFactoryEditingDomain
			.getEditingDomainFor(context.getDomainModel());
	}

	@Override
	public void dispose() {
		// no op
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public void deleteElements(final Collection<Object> toDelete) {
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
			return;
		}

		/*
		 * the default DeleteCommand cannot be executed for whatever reason.
		 * Wrap the default delete in a change command for undo support.
		 */
		final Command changeCommand = new ChangeCommand(editingDomain.getResourceSet()) {
			@Override
			protected void doExecute() {
				deleteWithoutEditingDomain(toDelete);
			}
		};
		if (changeCommand.canExecute()) {
			if (editingDomain.getCommandStack() == null) {
				changeCommand.execute();
			} else {
				editingDomain.getCommandStack().execute(changeCommand);
			}
			return;
		}

		throw new IllegalStateException("Delete was not successful."); //$NON-NLS-1$
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

	@Override
	public void deleteElement(Object toDelete) {
		if (toDelete == null) {
			return;
		}
		/* delete command for collections/single object works the same */
		deleteElements(Collections.singleton(toDelete));
	}

	@Override
	public boolean canDelete(Iterable<?> objects) {
		boolean result;

		if (editingDomain != null) {
			final Collection<?> collection = asCollection(objects);
			final Command deleteCommand = DeleteCommand.create(editingDomain, collection);
			result = deleteCommand.canExecute();
		} else {
			// Just see whether any object is a root
			result = true;

			for (final Object next : objects) {
				if (next instanceof EObject && ((EObject) next).eContainer() == null) {
					// Cannot delete a root object
					result = false;
					break;
				}
			}
		}

		return result;
	}

	private static <T> Collection<T> asCollection(Iterable<T> iterable) {
		Collection<T> result;

		if (iterable instanceof Collection<?>) {
			result = (Collection<T>) iterable;
		} else {
			result = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
		}

		return result;
	}

	@Override
	public boolean canRemove(Object owner, Object feature, Iterable<?> objects) {
		if (!(feature instanceof EReference)) {
			return canDelete(objects);
		}

		final EReference reference = (EReference) feature;
		if (reference.isContainment()) {
			return canDelete(objects);
		}

		boolean result;

		if (editingDomain != null) {
			final Collection<?> collection = asCollection(objects);
			final Command removeCommand = RemoveCommand.create(editingDomain, owner, reference, collection);
			result = removeCommand.canExecute();
		} else {
			// No commands? Then there's nothing to say 'no'
			result = true;
		}

		return result;
	}

}
