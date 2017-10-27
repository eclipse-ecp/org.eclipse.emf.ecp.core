/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * Makes the currently selected feature of a wizard page available.
 * This allows to access this feature by a rendered view contained in the page.
 * This is needed for the proper creation of mapping segments in the {@link AdvancedCreateDomainModelReferenceWizard}.
 *
 * @author Lucas Koehler
 *
 */
public interface SelectedFeatureViewService extends ViewModelService {
	/**
	 * @return The selected feature
	 */
	EStructuralFeature getFeature();
}
