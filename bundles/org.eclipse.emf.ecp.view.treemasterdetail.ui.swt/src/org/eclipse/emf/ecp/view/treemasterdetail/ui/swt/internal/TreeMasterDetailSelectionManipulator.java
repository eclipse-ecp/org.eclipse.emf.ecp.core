/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

/**
 * A TreeMasterDetail selection manipulator.
 *
 * @author Eugen Neufeld
 * @since 1.5
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
