/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;

/**
 * @author Eike Stepper
 */
public class RepositoriesLabelProvider extends ECPLabelProvider {

	public RepositoriesLabelProvider(ECPModelContextProvider modelContextProvider) {
		super(modelContextProvider);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ECPRepository) {
			final ECPRepository repository = (ECPRepository) element;
			return repository.getLabel();
		}

		return super.getText(element);
	}
}
