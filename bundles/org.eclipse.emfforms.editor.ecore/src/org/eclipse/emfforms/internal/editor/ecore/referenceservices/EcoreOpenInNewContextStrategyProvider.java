/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 * Martin Fleck - bug 487101
 * Christian W. Damus - bug 529542
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore.referenceservices;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emfforms.bazaar.Create;
import org.osgi.service.component.annotations.Component;

/**
 * Provider of strategy for opening of new objects in Ecore models.
 * In general, this strategy declines to open the object because
 * the Ecore editor solicits all critical details <em>a priori</em>.
 *
 * @since 1.16
 */
// Ranking as was for EcoreReferenceService
@Component(name = "ecoreOpenInNewContextStrategyProvider", property = "service.ranking:Integer=3")
public class EcoreOpenInNewContextStrategyProvider extends ReferenceServiceCustomizationVendor<OpenInNewContextStrategy>
	implements OpenInNewContextStrategy.Provider {

	/**
	 * Initializes me.
	 */
	public EcoreOpenInNewContextStrategyProvider() {
		super();
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return owner instanceof EModelElement;
	}

	/**
	 * Create the open strategy.
	 *
	 * @return the open strategy
	 */
	@Create
	public OpenInNewContextStrategy createOpenInNewContextStrategy() {
		return new OpenInNewContextStrategy() {
			@Override
			public boolean openInNewContext(EObject owner, EReference reference, EObject object) {
				// No op. stay inside editor
				return true;
			}
		};
	}
}
