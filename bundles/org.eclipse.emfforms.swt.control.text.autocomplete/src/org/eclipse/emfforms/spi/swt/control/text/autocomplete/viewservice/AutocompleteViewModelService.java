/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.control.text.autocomplete.viewservice;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * A {@link ViewModelService} which returns a list of proposals for a setting of an {@link EObject} and an
 * {@link EAttribute}.
 *
 * @author jfaltermeier
 *
 */
public interface AutocompleteViewModelService extends ViewModelService {

	/**
	 * Returns a list of possible values for the given setting. This method is not expected to return <code>null</code>
	 * values.
	 *
	 * @param object the {@link EObject}
	 * @param attribute the {@link EAttribute}
	 * @return a list of proposals for the setting
	 */
	List<String> getProposals(EObject object, EAttribute attribute);

}
