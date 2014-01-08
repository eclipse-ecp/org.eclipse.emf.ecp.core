/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IViewerNotification;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider.ViewerRefresh;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * @author Eike Stepper
 * @param <INPUT> The type of input (root of the tree)
 */
public abstract class ECPContentProvider<INPUT> extends TreeContentProvider<INPUT> implements ECPModelContextProvider,
	INotifyChangedListener {
	private ViewerRefresh viewerRefresh;

	/**
	 * Default constructor.
	 */
	public ECPContentProvider() {
		InternalProvider.EMF_ADAPTER_FACTORY.addListener(this);
	}

	@Override
	public void dispose() {
		InternalProvider.EMF_ADAPTER_FACTORY.removeListener(this);
		super.dispose();
	}

	@Override
	protected void fillChildren(Object parent, InternalChildrenList childrenList) {
		final ECPContainer context = getModelContext(parent);
		if (context != null) {
			final InternalProvider provider = (InternalProvider) context.getProvider();
			provider.fillChildren(context, parent, childrenList);
		}
	}

	/** {@inheritDoc} */
	public ECPContainer getModelContext(Object element) {
		while (element != null) {
			if (element instanceof ECPContainer) {
				break;
			}

			element = getParent(element);
		}

		return (ECPContainer) element;
	}

	/** {@inheritDoc} */
	public void notifyChanged(Notification notification) {
		final TreeViewer viewer = getViewer();
		if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
			if (viewerRefresh == null) {
				viewerRefresh = new ViewerRefresh(viewer);
			}

			if (viewerRefresh.addNotification((IViewerNotification) notification)) {
				viewer.getControl().getDisplay().asyncExec(viewerRefresh);
			}
		}
	}
}
