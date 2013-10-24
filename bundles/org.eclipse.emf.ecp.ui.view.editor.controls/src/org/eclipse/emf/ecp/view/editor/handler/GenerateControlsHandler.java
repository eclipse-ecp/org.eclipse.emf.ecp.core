/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.view.editor.handler;

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.view.editor.controls.Helper;
import org.eclipse.emf.ecp.view.model.VContainer;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for generating controls on a {@link VContainer}.
 * 
 * @author Eugen Neufeld
 * 
 */
public class GenerateControlsHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null) {
			return null;
		}
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(selection);
		final EClass rootClass = Helper.getRootEClass((EObject) selection);
		final SelectAttributesDialog sad = new SelectAttributesDialog(project, rootClass,
			HandlerUtil.getActiveShell(event));
		final int result = sad.open();
		if (result == Window.OK) {
			final EClass datasegment = sad.getDataSegment();
			final Set<EStructuralFeature> features = sad.getSelectedFeatures();
			final VContainer compositeCollection = (VContainer) selection;
			AdapterFactoryEditingDomain.getEditingDomainFor(compositeCollection).getCommandStack()
				.execute(new ChangeCommand(compositeCollection) {

					@Override
					protected void doExecute() {
						ControlGenerator.addControls(rootClass, compositeCollection, datasegment, features);
					}
				});

		}

		return null;
	}

}
