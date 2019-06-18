/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Muller, Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

/**
 * Factory interface for obtaining an instance of an {@link SubstitutionLabelProvider}.
 *
 */
public interface ViewSubstitutionLabelProviderFactory {

	/**
	 * Create a {@link SubstitutionLabelProvider}.
	 *
	 * @param factory the {@link ComposedAdapterFactory} that the {@link SubstitutionLabelProvider}
	 *            can delegate to if necessary
	 * @return the created label provider
	 */
	SubstitutionLabelProvider createSubstitutionLabelProvider(ComposedAdapterFactory factory);

}
