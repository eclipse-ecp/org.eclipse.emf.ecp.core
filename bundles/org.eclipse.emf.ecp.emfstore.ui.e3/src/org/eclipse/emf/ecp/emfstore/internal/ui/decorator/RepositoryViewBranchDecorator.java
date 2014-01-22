/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.decorator;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProjectWrapper;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESBranchInfo;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

/**
 * Optional decorator. Not used in extension point
 * 
 * @author jfaltermeier
 * 
 */
public class RepositoryViewBranchDecorator implements ILightweightLabelDecorator {

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof EMFStoreProjectWrapper) {
			EMFStoreProjectWrapper wrapper = (EMFStoreProjectWrapper) element;
			ESRemoteProject remoteProject = wrapper.getCheckoutData();

			if (remoteProject == null) {
				return;
			}

			try {
				List<ESBranchInfo> branches = remoteProject.getBranches(new NullProgressMonitor());
				for (ESBranchInfo bi : branches) {
					ESPrimaryVersionSpec versSpec = bi.getHead();
					decoration.addSuffix(" [" + versSpec.getBranch() + ", v" + versSpec.getIdentifier() + "]");
				}
			} catch (ESException ex) {
			}
		} else {
			return;
		}
	}
}
