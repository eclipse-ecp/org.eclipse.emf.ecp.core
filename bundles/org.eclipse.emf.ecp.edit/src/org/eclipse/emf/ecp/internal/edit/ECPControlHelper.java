/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Jonas - initial API and implementation
 * Christian W. Damus - bug 530829
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.edit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author Jonas
 *
 */
public abstract class ECPControlHelper {
	/**
	 * @param eObject the object to add a reference to
	 * @param newMEInstance the object which is added as a reference
	 * @param eReference the reference to be modified
	 * @param editingDomain the editing domain to execute commands on
	 */
	public static void addModelElementInReference(EObject eObject, EObject newMEInstance, EReference eReference,
		EditingDomain editingDomain) {

		// add the new object to the reference
		// Object object = modelElement.eGet(eReference);
		if (eReference.getUpperBound() == 1) {
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, eObject, eReference, newMEInstance));
		} else {
			editingDomain.getCommandStack().execute(
				AddCommand.create(editingDomain, eObject, eReference, newMEInstance));
		}
	}

	/**
	 * @param eObject the objects to add a reference to
	 * @param eObjects the objects which is added as a reference
	 * @param eReference the reference to be modified
	 * @param editingDomain the editing domain to execute commands on
	 */
	public static void addModelElementsInReference(EObject eObject, Set<? extends EObject> eObjects,
		EReference eReference, EditingDomain editingDomain) {

		if (eObjects.isEmpty()) {
			return;
		}
		// add the new object to the reference
		// Object object = modelElement.eGet(eReference);
		if (eReference.getUpperBound() == 1) {
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, eObject, eReference, eObjects.iterator().next()));
		} else {
			editingDomain.getCommandStack().execute(
				AddCommand.create(editingDomain, eObject, eReference, eObjects));
		}
	}

	/**
	 * Removes elements, which are already referenced.
	 *
	 * @param eObject The object holding the reference
	 * @param eReference the reference
	 * @param elements the elements to remove existing elements from
	 */
	public static void removeExistingReferences(EObject eObject, EReference eReference,
		Set<? extends EObject> elements) {

		final Set<EObject> existing = new HashSet<EObject>();
		final Object eGet = eObject.eGet(eReference);
		if (eGet == null) {
			return;
		}
		if (eReference.getUpperBound() == 1) {
			existing.add((EObject) eGet);
		} else {
			@SuppressWarnings("unchecked")
			final Collection<? extends EObject> collection = (Collection<? extends EObject>) eGet;
			existing.addAll(collection);
		}
		elements.removeAll(existing);

	}

}
