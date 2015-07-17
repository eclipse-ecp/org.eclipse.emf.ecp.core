/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Style Selector</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#getStyleSelector()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface VTStyleSelector extends EObject
{

	/**
	 * Return this constant if the selector is not applicable.
	 */
	static final Double NOT_APPLICABLE = Double.MIN_VALUE;

	/**
	 * Checks how well a {@link VElement} is fitting.
	 *
	 * @param vElement the {@link VElement} to check
	 * @param viewModelContext the {@link ViewModelContext} currently used
	 * @return a double defining the specificity of the selector. The higher the number the more specific the tester is.
	 */
	double isApplicable(VElement vElement, ViewModelContext viewModelContext);
} // VTStyleSelector
