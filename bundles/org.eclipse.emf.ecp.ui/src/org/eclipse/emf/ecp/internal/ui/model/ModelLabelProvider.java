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

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectOpenClosedObserver;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;

/**
 * @author Eike Stepper
 */
public class ModelLabelProvider extends ECPLabelProvider implements ECPProjectOpenClosedObserver {

	public ModelLabelProvider(ECPModelContextProvider modelContextProvider) {
		super(modelContextProvider);
		ECPUtil.getECPObserverBus().register(this);
	}

	@Override
	public void dispose() {
		ECPUtil.getECPObserverBus().unregister(this);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ECPProject) {
			final ECPProject project = (ECPProject) element;
			return project.getName();
		}

		return super.getText(element);
	}

	/** {@inheritDoc} */
	@Override
	public void projectChanged(final ECPProject project, boolean opened) {
		fireEvent(new LabelProviderChangedEvent(this, project));
	}
}
