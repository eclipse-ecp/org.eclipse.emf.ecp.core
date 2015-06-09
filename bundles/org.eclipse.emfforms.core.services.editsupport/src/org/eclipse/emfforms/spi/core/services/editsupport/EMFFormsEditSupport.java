/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.editsupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * The {@link EMFFormsEditSupport} provides methods to get information about a property described by a
 * {@link VDomainModelReference} and the corresponding root {@link EObject}.
 *
 * @author Lucas Koehler
 *
 */
public interface EMFFormsEditSupport {

	/**
	 * Returns whether the property is multi line.
	 *
	 * @param domainModelReference The {@link VDomainModelReference}
	 * @param rootObject The root {@link EObject} of the {@link VDomainModelReference}
	 * @return true if the property is multi line, false otherwise
	 */
	boolean isMultiLine(VDomainModelReference domainModelReference, EObject rootObject);

	/**
	 * Returns whether the property can be set.
	 *
	 * @param domainModelReference The {@link VDomainModelReference}
	 * @param rootObject The root {@link EObject} of the {@link VDomainModelReference}
	 * @return true if the property can be set, false otherwise
	 */
	boolean canSetProperty(VDomainModelReference domainModelReference, EObject rootObject);

	/**
	 * Returns the label text for the given element.
	 *
	 * @param domainModelReference The {@link VDomainModelReference}
	 * @param rootObject The root {@link EObject} of the {@link VDomainModelReference}
	 * @param element The element for which the label text should be retrieved
	 * @return The label text
	 */
	String getText(VDomainModelReference domainModelReference, EObject rootObject, Object element);

	/**
	 * Returns the label image for the given element.
	 *
	 * @param domainModelReference The {@link VDomainModelReference}
	 * @param rootObject The root {@link EObject} of the {@link VDomainModelReference}
	 * @param element The element for which the label image should be retrieved
	 * @return The label image
	 */
	Object getImage(VDomainModelReference domainModelReference, EObject rootObject, Object element);
}
