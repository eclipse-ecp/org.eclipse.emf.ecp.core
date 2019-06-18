/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.ui.util;

import org.eclipse.emf.ecp.core.ECPProject;

/**
 * This interface is used to open a specific editor for a model element. ECP provides a common implementation able to
 * handle all EObjects.
 *
 * @author helming
 */
public interface ECPModelElementOpener {

	/**
	 * The action to open the model element.
	 *
	 * @param element
	 *            the element to open
	 * @param ecpProject
	 *            the project this element belongs to
	 */
	void openModelElement(Object element, ECPProject ecpProject);
}
