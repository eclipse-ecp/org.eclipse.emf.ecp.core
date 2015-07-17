/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.core.util;

import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * The {@link ECPModelContextAdapter} can be used to be able to get the {@link ECPContainer} from a Notifier.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ECPModelContextAdapter extends AdapterImpl {
	private final ECPContainer context;

	/**
	 * Constructor of this Adapter which expects an {@link ECPContainer}.
	 *
	 * @param context the {@link ECPContainer} of this adapter
	 */
	public ECPModelContextAdapter(ECPContainer context) {
		this.context = context;
	}

	/**
	 * The {@link ECPContainer} of this Adapter.
	 *
	 * @return the {@link ECPContainer}
	 */
	public final ECPContainer getContext() {
		return context;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		if (type instanceof Class<?>) {
			return ((Class<?>) type).isInstance(this);
		}
		return context.equals(type);
	}

}
