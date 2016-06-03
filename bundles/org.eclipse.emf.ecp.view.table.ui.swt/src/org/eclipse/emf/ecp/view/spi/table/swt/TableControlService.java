/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.common.Optional;

/**
 * Service used by the {@link TableControlSWTRenderer} to customize button behaviour.
 *
 * @author Johannes Faltermeier
 * @since 1.9
 *
 */
public interface TableControlService {

	/**
	 * This method is called by the {@link TableControlSWTRenderer} when the add button was pressed. Implementors are
	 * supposed to create a new element <b>but not yet add</b> it to the multireference.
	 *
	 * @param clazz the {@link EClass} defining the EObject to create
	 * @param eObject The reference {@link EObject}
	 * @param structuralFeature The reference {@link EStructuralFeature}
	 * @return the object which will be added
	 */
	Optional<EObject> createNewElement(EClass clazz, EObject eObject, EStructuralFeature structuralFeature);

	/**
	 * Called when an object in the table is double clicked.
	 *
	 * @param table the table control
	 * @param eObject the object which was double clicked
	 */
	void doubleClick(VTableControl table, EObject eObject);
}
