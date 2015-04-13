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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * The DeleteService is used by renderers in order to delete/remove objects from the containment tree.
 *
 * @author jfaltermeier
 * @since 1.6
 *
 */
public interface DeleteService extends ViewModelService {

	/**
	 * Deletes the given objects from the containment tree. This will cut all references to the deleted objects.
	 *
	 * @param editingDomain the editing domain with command stack
	 * @param toDelete the objects to delete
	 */
	void deleteElements(EditingDomain editingDomain, Collection<Object> toDelete);

	/**
	 * Deletes the given object from the containment tree. This will cut all references to the deleted object.
	 *
	 * @param editingDomain the editing domain with command stack
	 * @param toDelete the object to delete
	 */
	void deleteElement(EditingDomain editingDomain, Object toDelete);

	/**
	 * <p>
	 * Removes the given objects from the owning object. The elements might still be reachable from other references.
	 * </p>
	 * <p>
	 * <b>Notice: </b>If the feature is a containment feature, consider using
	 * {@link #deleteElements(EditingDomain, Collection)}
	 * instead.
	 * </p>
	 *
	 * @param editingDomain the editing domain with command stack
	 * @param source the object which references the objects to be removed
	 * @param feature the feature which references the objects to be removed
	 * @param toRemove the objects to be removed
	 */
	void removeElements(EditingDomain editingDomain, Object source, EStructuralFeature feature,
		Collection<Object> toRemove);

	/**
	 * <p>
	 * Removes the given object from the owning object. The element might still be reachable from other references.
	 * </p>
	 * <p>
	 * <b>Notice: </b>If the feature is a containment feature, consider using
	 * {@link #deleteElement(EditingDomain, Object)}
	 * instead.
	 * </p>
	 *
	 * @param editingDomain the editing domain with command stack
	 * @param source the object which references the object to be removed
	 * @param feature the feature which references the object to be removed
	 * @param toRemove the object to be removed
	 */
	void removeElement(EditingDomain editingDomain, Object source, EStructuralFeature feature, Object toRemove);

}
