/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Alexandra Buzila- initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.internal.preview.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.model.internal.preview.Activator;
import org.eclipse.emf.ecp.view.model.internal.preview.e3.views.PreviewView;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.MasterDetailAction;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/** Opens the {@link PreviewView}. */
public class OpenPreviewHandler extends MasterDetailAction {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.MasterDetailAction#execute(VView)
	 */
	@Override
	public void execute(EObject object) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {
			// page.op
			page.showView("org.eclipse.emf.ecp.view.model.preview.e3.views.PreviewView", null,
				IWorkbenchPage.VIEW_VISIBLE);
			//			page.showView("org.eclipse.emf.ecp.view.model.preview.e3.views.PreviewView"); //$NON-NLS-1$
		} catch (final PartInitException e) {
			Activator
				.getDefault()
				.getLog()
				.log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e.getMessage(), e));
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.MasterDetailAction#shouldShow(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean shouldShow(EObject eObject) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

}
