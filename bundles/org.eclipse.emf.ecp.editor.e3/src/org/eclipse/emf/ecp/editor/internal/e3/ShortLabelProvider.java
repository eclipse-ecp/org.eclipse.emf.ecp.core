/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.editor.internal.e3;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

// TODO: Review this class
/**
 * Label provider to shorten the getText Method.
 * 
 * @author helming
 */
public class ShortLabelProvider extends AdapterFactoryLabelProvider {

	/**
	 * Default constructor.
	 * 
	 * @param adapterFactory the {@link AdapterFactory} to be used.
	 */
	public ShortLabelProvider(AdapterFactory adapterFactory) {

		super(adapterFactory);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.provider.IItemLabelProvider#getText(java.lang.Object)
	 * @override
	 */
	@Override
	public String getText(Object object) {
		final int limit = 30;
		String name = super.getText(object);
		if (name == null) {
			name = ""; //$NON-NLS-1$
		}
		if (name.length() > limit + 5) {
			name = name.substring(0, limit).concat("[...]"); //$NON-NLS-1$
		}
		return name;
	}
}
