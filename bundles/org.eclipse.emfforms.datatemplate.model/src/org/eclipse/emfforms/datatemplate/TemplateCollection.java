/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 */
package org.eclipse.emfforms.datatemplate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfforms.datatemplate.TemplateCollection#getTemplates <em>Templates</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.datatemplate.DataTemplatePackage#getTemplateCollection()
 * @model
 * @generated
 */
public interface TemplateCollection extends EObject {
	/**
	 * Returns the value of the '<em><b>Templates</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.datatemplate.Template}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Templates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Templates</em>' containment reference list.
	 * @see org.eclipse.emfforms.datatemplate.DataTemplatePackage#getTemplateCollection_Templates()
	 * @model containment="true"
	 * @generated
	 */
	EList<Template> getTemplates();

} // TemplateCollection
