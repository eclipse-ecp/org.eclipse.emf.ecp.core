/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.workspace.internal.ui;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.ui.CompositeStateObserver;
import org.eclipse.emf.ecp.spi.ui.DefaultUIProvider;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Eike Stepper
 */
public class WorkspaceUIProvider extends DefaultUIProvider {

	private static final Image PROJECT_OPEN = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
		"icons/page_code.gif").createImage(); //$NON-NLS-1$

	/**
	 * Default constructor of an UIProvider.
	 */
	public WorkspaceUIProvider() {
		super(WorkspaceProvider.NAME);
	}

	@Override
	public String getText(Object element) {
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ECPProject) {
			final ECPProject project = (ECPProject) element;
			if (project.isOpen()) {
				return PROJECT_OPEN;
			}
		}

		return super.getImage(element);
	}

	@Override
	public void fillContextMenu(IMenuManager manager, ECPContainer context, Object[] elements) {
		super.fillContextMenu(manager, context, elements);
	}

	@Override
	public Control createCheckoutUI(Composite parent, ECPCheckoutSource checkoutSource, ECPProperties projectProperties) {
		// Suppress default properties composite
		return null;
	}

	@Override
	public Control createNewProjectUI(Composite parent, CompositeStateObserver observer, ECPProperties projectProperties) {
		return new NewWorkspaceProjectComposite(parent, observer, projectProperties);
	}
}
