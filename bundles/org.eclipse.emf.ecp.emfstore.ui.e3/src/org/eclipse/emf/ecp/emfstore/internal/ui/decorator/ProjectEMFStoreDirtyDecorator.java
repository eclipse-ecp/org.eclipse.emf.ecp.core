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
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.decorator;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.emfstore.internal.ui.e3.Activator;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

/**
 * Dirty Decorator for Projects.
 * 
 * @author Tobias Verhoeven
 */
public class ProjectEMFStoreDirtyDecorator implements ILightweightLabelDecorator {

	private String dirtyPath = "icons/dirty.png";

	/**
	 * Instantiates a new EMFStoreDirtyDecorator.
	 */
	public ProjectEMFStoreDirtyDecorator() {
		super();
	}

	/** {@inheritDoc} */
	public void decorate(Object element, IDecoration decoration) {

		if (!(element instanceof ECPProject)) {
			return;
		}
		ECPProvider ecpProvider = ECPUtil.getECPProviderRegistry().getProvider(EMFStoreProvider.NAME);
		EMFStoreProvider provider = (EMFStoreProvider) ECPUtil.getResolvedElement(ecpProvider);
		InternalProject project = (InternalProject) element;
		if (!project.getProvider().equals(provider)) {
			return;
		}
		ESLocalProject projectSpace = provider.getProjectSpace(project);

		if (projectSpace == null) {
			return;
		}

		if (project.isOpen() && projectSpace.isShared() && projectSpace.hasUncommitedChanges()) {
			decoration.addOverlay(Activator.getImageDescriptor(dirtyPath), IDecoration.BOTTOM_LEFT);
		}

	}

	/** {@inheritDoc} */
	public void addListener(ILabelProviderListener listener) {
	}

	/** {@inheritDoc} */
	public void dispose() {

	}

	/** {@inheritDoc} */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/** {@inheritDoc} */
	public void removeListener(ILabelProviderListener listener) {
	}
}