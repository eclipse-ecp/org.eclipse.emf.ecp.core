/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.validationvieweditorbridge;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Double click listener for the validation view which opens the model editor for the {@link EObject} associated with
 * the double clicked {@link Diagnostic}.
 *
 * @author jfaltermeier
 *
 */
public class ValidationViewToEditorBridge implements IDoubleClickListener {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	@Override
	public void doubleClick(DoubleClickEvent event) {
		final IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
		final Object selection = thisSelection.getFirstElement();
		if (!(selection instanceof Diagnostic)) {
			return;
		}
		final Diagnostic diagnostic = (Diagnostic) selection;
		if (diagnostic.getData().isEmpty() || !(diagnostic.getData().get(0) instanceof EObject)) {
			return;
		}
		final EObject eObject = (EObject) diagnostic.getData().get(0);
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(eObject);
		ECPHandlerHelper.openModelElement(eObject, project);
	}
}
