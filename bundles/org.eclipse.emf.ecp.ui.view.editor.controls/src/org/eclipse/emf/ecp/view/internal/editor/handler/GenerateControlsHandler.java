/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for generating controls on a {@link org.eclipse.emf.ecp.view.spi.model.VContainedContainer VContainer} or
 * {@link org.eclipse.emf.ecp.view.spi.model.VView VView}.
 *
 * @author Eugen Neufeld
 *
 */
public class GenerateControlsHandler extends MasterDetailAction {

	@Override
	public void execute(final EObject object) {
		final CallbackFeatureSupplier callBack = new CallbackFeatureSupplier() {

			@Override
			public Set<EStructuralFeature> get(VView view) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final SelectAttributesWizard selectAttributesWizard = new SelectAttributesWizard(view);
				final WizardDialog sad = new WizardDialog(shell, selectAttributesWizard);
				final int result = sad.open();
				if (result == Window.OK) {
					return selectAttributesWizard.getSelectedFeatures();
				}
				return Collections.emptySet();
			}
		};
		execute(object, callBack);

	}

	/**
	 * Creates controls based on the features supplied by the {@link CallbackFeatureSupplier}.
	 *
	 * @param vElement The element the action was executed on
	 * @param featureSupplier The Supplier which provides the features to create controls for
	 */
	void execute(final EObject vElement, CallbackFeatureSupplier featureSupplier) {
		if (!(VView.class.isInstance(vElement) || VContainer.class.isInstance(vElement))) {
			return;
		}

		final VView view = getView(vElement);
		if (view == null) {
			return;
		}

		final EClass rootEClass = view.getRootEClass();

		final Set<EStructuralFeature> featuresToAdd = featureSupplier.get(view);
		if (featuresToAdd.isEmpty()) {
			return;
		}
		AdapterFactoryEditingDomain.getEditingDomainFor(view).getCommandStack()
			.execute(new ChangeCommand(view) {
				@Override
				protected void doExecute() {
					ControlGenerator.addControls(rootEClass, (VElement) vElement, featuresToAdd);
				}
			});
	}

	private VView getView(EObject object) {

		while (!(object instanceof VView)) {
			object = object.eContainer();
			if (object == null) {
				return null;
			}
		}
		return (VView) object;

	}

	@Override
	public boolean shouldShow(EObject object) {
		if (object == null) {
			return false;
		}
		return object instanceof VView || object instanceof VContainer;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null || !(selection instanceof EObject)) {
			return null;
		}
		execute((EObject) selection);
		return null;
	}

	/**
	 * Internal Interface defined for testability. It is used to get the list of features which should be used to create
	 * controls.
	 */
	interface CallbackFeatureSupplier {
		/**
		 * Gets the result based on the provided view.
		 *
		 * @param view The {@link VView} which is the root
		 * @return The Set of {@link EStructuralFeature EStructuralFeatures} supplied by this supplier
		 */
		Set<EStructuralFeature> get(VView view);
	}
}
