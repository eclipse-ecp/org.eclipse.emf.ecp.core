/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VDomain Model Reference</b></em>'.
 *
 * @implements ModelChangeListener
 *             <!-- end-user-doc -->
 *
 *             <p>
 *             The following features are supported:
 *             </p>
 *             <ul>
 *             <li>{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getChangeListener
 *             <em>Change Listener</em>}</li>
 *             </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDomainModelReference()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface VDomainModelReference extends EObject, ModelChangeListener {
	/**
	 * Returns the value of the '<em><b>Change Listener</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Listener</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @return the value of the '<em>Change Listener</em>' attribute list.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDomainModelReference_ChangeListener()
	 * @model dataType="org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener" transient="true"
	 * @generated
	 */
	@Deprecated
	EList<DomainModelReferenceChangeListener> getChangeListener();

	/**
	 * Resolve attempts to resolve the domain model reference. If it fails existing results will be kept. Thus even
	 * after
	 * a failed resolution the iterator might not be empty.
	 *
	 * @param eObject the root domain model to use to resolve the path
	 * @return true if resolution succeeded and false otherwise
	 * @since 1.3
	 */
	@Deprecated
	boolean init(EObject object);

	/**
	 * Returns an iterator allowing to iterate over found settings for this domain model reference.
	 * Will return an empty Iterator if resolve was not successfully completed.
	 *
	 * @return the {@link Iterator} for this domain model reference
	 */
	@Deprecated
	Iterator<Setting> getIterator();

	/**
	 * Returns an iterator allowing to iterate overall {@link EStructuralFeature EStructuralFeatures} of this domain
	 * model reference.
	 *
	 * @return the {@link Iterator} over all {@link EStructuralFeature EStructuralFeatures} for this domain model
	 *         reference
	 * @since 1.3
	 */
	@Deprecated
	Iterator<EStructuralFeature> getEStructuralFeatureIterator();

	/**
	 * Returns an iterator allowing to iterate over all setting paths for this domain model reference.
	 * Will return an empty Iterator if resolve was not successfully completed.
	 *
	 * @return the {@link Iterator} over setting paths for this domain model reference
	 * @since 1.3
	 */
	@Deprecated
	Iterator<SettingPath> getFullPathIterator();

} // VDomainModelReference
