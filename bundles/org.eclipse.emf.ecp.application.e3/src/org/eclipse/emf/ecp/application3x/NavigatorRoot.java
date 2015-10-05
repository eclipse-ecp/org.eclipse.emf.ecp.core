/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.application3x;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Custom NavigatorRoot.
 */
public class NavigatorRoot implements IAdaptable, IPersistableElement, IElementFactory {

	/**
	 * Instantiates a new navigator root.
	 */
	public NavigatorRoot() {
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (adapter == IPersistableElement.class) {
			return this;
		}
		if (adapter == IWorkbenchAdapter.class) {
			return ResourcesPlugin.getWorkspace().getRoot().getAdapter(adapter);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getFactoryId() {
		return this.getClass().getCanonicalName();
	}

	/** {@inheritDoc} */
	@Override
	public void saveState(IMemento memento) {
		// TODO Auto-generated method stub
		return;
	}

	/** {@inheritDoc} */
	@Override
	public IAdaptable createElement(IMemento memento) {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
}