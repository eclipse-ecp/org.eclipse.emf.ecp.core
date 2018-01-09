/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.context;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelServiceProvider;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * A {@link ViewModelServiceProvider} for compatibility with the injection of
 * static arrays of services, that provides them exactly once and thereafter
 * provides no services at all.
 *
 * @author Christian W. Damus
 *
 * @since 1.16
 */
public class ArrayOnceViewModelServiceProvider implements ViewModelServiceProvider {

	private List<ViewModelService> services;

	/**
	 * Initializes me with the services to provide once and once only.
	 *
	 * @param services the one-shot services to provide
	 */
	public ArrayOnceViewModelServiceProvider(ViewModelService... services) {
		super();

		this.services = Arrays.asList(services);
	}

	@Override
	public Collection<? extends ViewModelService> getViewModelServices(VElement view, EObject eObject) {
		final Collection<? extends ViewModelService> result = services;
		if (!result.isEmpty()) {
			// Only this once
			services = Collections.emptyList();
		}
		return result;
	}

}
