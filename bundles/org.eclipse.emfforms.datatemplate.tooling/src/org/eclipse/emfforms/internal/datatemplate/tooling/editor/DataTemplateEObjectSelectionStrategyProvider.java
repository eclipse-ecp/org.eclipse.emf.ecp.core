/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 550799
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.editor;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.datatemplate.TemplateCollection;
import org.osgi.service.component.annotations.Component;

/**
 * Provider of an data template specific object selection strategy.
 * The objects which are allowed to be selected must be from the same template as the object we want to add them to.
 *
 * @author Eugen Neufeld
 * @since 1.23
 */
@Component(property = "service.ranking:Integer=5")
public class DataTemplateEObjectSelectionStrategyProvider
	extends ReferenceServiceCustomizationVendor<EObjectSelectionStrategy>
	implements EObjectSelectionStrategy.Provider {

	/**
	 * Initializes me.
	 */
	public DataTemplateEObjectSelectionStrategyProvider() {
		super();
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return EcoreUtil.getRootContainer(owner) instanceof TemplateCollection;
	}

	/**
	 * Create the selection strategy.
	 *
	 * @return the selection strategy
	 */
	@Create
	public EObjectSelectionStrategy createEObjectSelectionStrategy() {
		return new Strategy();
	}

	//
	// Nested types
	//

	/**
	 * The selection strategy.
	 */
	private static class Strategy implements EObjectSelectionStrategy {
		/**
		 * Initializes me.
		 */
		Strategy() {
			super();
		}

		@Override
		public Collection<EObject> collectExistingObjects(EObject owner, EReference reference,
			Collection<EObject> existingObjects) {
			final EObject template = getEnclosingTemplate(owner);
			return existingObjects.stream().filter(o -> EcoreUtil.isAncestor(template,
				o)).collect(Collectors.toList());
		}

		private EObject getEnclosingTemplate(EObject eObject) {
			EObject result = eObject;
			while (result != null && !(result instanceof Template)) {
				result = result.eContainer();
			}
			return result;
		}

	}

}
