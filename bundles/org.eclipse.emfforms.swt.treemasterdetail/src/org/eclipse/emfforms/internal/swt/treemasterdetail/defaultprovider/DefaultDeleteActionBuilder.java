/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.treemasterdetail.DeleteActionBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.osgi.framework.FrameworkUtil;

/**
 * The default delete action creates a remove command and wraps it's execution in the {@link Action#run() run} method.
 *
 * @author Johannes Faltermeier
 *
 */
public class DefaultDeleteActionBuilder implements DeleteActionBuilder {

	@Override
	public Action createDeleteAction(IStructuredSelection selection, final EditingDomain editingDomain) {
		final Command deleteCommand = DeleteCommand.create(editingDomain, selection.toList());

		final Action deleteAction = new Action() {
			@Override
			public void run() {
				super.run();
				editingDomain.getCommandStack().execute(deleteCommand);
			}
		};

		if (!deleteCommand.canExecute()) {
			deleteAction.setEnabled(false);
		}

		final String deleteImagePath = "icons/delete.png";//$NON-NLS-1$
		deleteAction.setImageDescriptor(ImageDescriptor
			.createFromURL(FrameworkUtil.getBundle(TreeMasterDetailComposite.class).getResource(deleteImagePath)));
		deleteAction.setText("Delete"); //$NON-NLS-1$

		return deleteAction;
	}

}
