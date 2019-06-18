/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.datatemplate;

import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.emfforms.datatemplate.Template;

/**
 * Protocol for a data template filter service.
 *
 * @since 1.21
 */
public interface TemplateFilterService {

	/**
	 * A filter service that provides no filtering.
	 */
	TemplateFilterService NULL = (owner, reference) -> null;

	/**
	 * Obtain a template filter applicable to templates provided for the given
	 * {@code reference} of an {@code owner} object in the editor.
	 *
	 * @param owner the object owning a {@code reference} to be assigned from a template
	 * @param reference a reference feature of the {@code owner} that is to be assigned from a template.
	 *            If the {@code reference} is a {@link EReference#isContainment() containment} then
	 *            the {@code owner} would be the {@link EObject#eContainer() container} of the template
	 *
	 * @return a predicate with which to filter templates (only templates satisfying the predicate
	 *         are retained), or else {@code null} to opt out of filtering
	 */
	Predicate<? super Template> getTemplateFilter(EObject owner, EReference reference);

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for {@link TemplateFilterService} providers.
	 * It is intended that implementations be registered as OSGi services, for
	 * clients of the template providers to find them and apply as appropriate
	 * to the data templates that they obtain.
	 *
	 * @since 1.21
	 */
	public interface Provider extends Vendor<TemplateFilterService> {
		// Nothing to add to the superinterface
	}

}
