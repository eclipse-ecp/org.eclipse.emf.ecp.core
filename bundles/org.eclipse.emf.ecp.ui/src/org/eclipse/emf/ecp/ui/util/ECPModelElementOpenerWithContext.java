/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.util;

import java.util.Map;

import org.eclipse.emf.ecp.core.ECPProject;

/**
 * This interface is used to open a specific editor for a model element.
 * ECP provides a common implementation able to handle all EObjects.
 * Additionally this interface allows to pass a context map with arbitrary objects to the opener.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.12
 *
 */
public interface ECPModelElementOpenerWithContext extends ECPModelElementOpener {

	/**
	 * The action to open the model element.
	 *
	 * @param element
	 *            the element to open
	 * @param ecpProject
	 *            the project this element belongs to
	 * @param context the additional context objects
	 */
	void openModelElement(Object element, ECPProject ecpProject, Map<Object, Object> context);

}
