/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

/**
 * A TreeMasterDetail selection manipulator.
 * 
 * @author Eugen Neufeld
 * 
 */
public interface TreeMasterDetailSelectionManipulator {

	/**
	 * Manipulate the selection.
	 * 
	 * @param selection the selected Object
	 * @return the newly selected Object
	 */
	Object manipulateSelection(Object selection);
}
