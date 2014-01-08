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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.view.editor.controls.Activator;
import org.eclipse.emf.ecp.view.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for generating controls on a {@link org.eclipse.emf.ecp.view.spi.model.VContainer VContainer} or
 * {@link org.eclipse.emf.ecp.view.spi.model.VView VView}.
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
			final Set<EStructuralFeature> featuresToAdd = getFeaturesToCreate(sad);
			final VElement compositeCollection = (VElement) selection;
			AdapterFactoryEditingDomain.getEditingDomainFor(compositeCollection).getCommandStack()
				.execute(new ChangeCommand(compositeCollection) {

					@Override
					protected void doExecute() {
						ControlGenerator.addControls(rootClass, compositeCollection, sad.getDataSegment(),
							featuresToAdd);
					}
				});

		}

		return null;
	}

	private Set<EStructuralFeature> getFeaturesToCreate(final SelectAttributesDialog sad) {
		final EClass datasegment = sad.getDataSegment();
		final Set<EStructuralFeature> features = sad.getSelectedFeatures();
		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
		final Set<EStructuralFeature> featuresToAdd = new HashSet<EStructuralFeature>();
		IItemPropertyDescriptor propertyDescriptor = null;
		for (final EStructuralFeature feature : features) {
			propertyDescriptor =
				adapterFactoryItemDelegator
					.getPropertyDescriptor(EcoreUtil.create(datasegment), feature);
			if (propertyDescriptor != null) {
				featuresToAdd.add(feature);
			}
			else {
				logInvalidFeature(feature.getName(), datasegment.getName());
			}
		}
		return featuresToAdd;
	}

	private void logInvalidFeature(String featureName, String eClassName) {
		final String infoMessage = "Feature " + featureName //$NON-NLS-1$
			+ " of the class " + eClassName + "could not be rendered because it has no property descriptor."; //$NON-NLS-1$ //$NON-NLS-2$
		final ILog log = Activator
			.getDefault()
			.getLog();
		log.log(
			new Status(
				IStatus.INFO,
				Activator.PLUGIN_ID, infoMessage));
	}
}
