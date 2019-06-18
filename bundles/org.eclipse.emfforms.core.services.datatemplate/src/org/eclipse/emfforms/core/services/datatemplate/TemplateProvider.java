/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 * Christian W. Damus - bug 529138
 ******************************************************************************/
package org.eclipse.emfforms.core.services.datatemplate;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfforms.datatemplate.Template;

/**
 * Template provider interface.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.17
 */
public interface TemplateProvider {

	/**
	 * Queries whether the provider can provide templates that may be assigned
	 * to the given {@code reference} of the given {@code owner} object.
	 *
	 * @param owner the object owning a {@code reference} to be assigned from a template
	 * @param reference a reference feature of the {@code owner} that is to be assigned from a template.
	 *            If the {@code reference} is a {@link EReference#isContainment() containment} then
	 *            the {@code owner} would be the {@link EObject#eContainer() container} of the template
	 *
	 * @return whether I have any templates to offer
	 */
	boolean canProvideTemplates(EObject owner, EReference reference);

	/**
	 * Obtains templates wrapping objects that can be assigned to the given
	 * {@code reference} of the given {@code owner} object. This will only
	 * be called for an {@code owner} and {@code reference} for which the
	 * receiver previously answered {@code true} to an invocation of the
	 * {@link #canProvideTemplates(EObject, EReference)} query.
	 *
	 * @param owner the object owning a {@code reference} to be assigned from a template
	 * @param reference a reference feature of the {@code owner} that is to be assigned from a template.
	 *            If the {@code reference} is a {@link EReference#isContainment() containment} then
	 *            the {@code owner} would be the {@link EObject#eContainer() container} of the template
	 *
	 * @return my available templates, or an empty set if none (never {@code null})
	 */
	Set<Template> provideTemplates(EObject owner, EReference reference);

}
