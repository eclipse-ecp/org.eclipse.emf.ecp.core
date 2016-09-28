/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emfforms.spi.rulerepository.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Rule Repository</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository#getRuleEntries <em>Rule Entries</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage#getRuleRepository()
 * @model
 * @generated
 */
public interface VRuleRepository extends EObject {
	/**
	 * Returns the value of the '<em><b>Rule Entries</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Entries</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Rule Entries</em>' containment reference
	 *         list.
	 * @see org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage#getRuleRepository_RuleEntries()
	 * @model containment="true"
	 * @generated
	 */
	EList<VRuleEntry> getRuleEntries();

} // VRuleRepository
