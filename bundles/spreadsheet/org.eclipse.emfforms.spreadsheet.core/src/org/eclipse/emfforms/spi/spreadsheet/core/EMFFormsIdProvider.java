/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core;

import org.eclipse.emf.ecore.EObject;

/**
 * This service provides the value for the ID column used for identifying values accross multiple sheets.
 * 
 * @author Eugen Neufeld
 *
 */
public interface EMFFormsIdProvider {

	/**
	 * The name of the ID column.
	 */
	String ID_COLUMN = "EOBJECT_ID"; //$NON-NLS-1$

	/**
	 * Returns the id value for an {@link EObject}.
	 * 
	 * @param eObject The {@link EObject} to get an id for
	 * @return The id
	 */
	String getId(EObject eObject);
}
