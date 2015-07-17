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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.internal.editor.controls.Activator;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
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

	private Set<EStructuralFeature> getFeaturesToCreate(final SelectAttributesDialog sad) {
		final Set<EStructuralFeature> features = sad.getSelectedFeatures();
		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
		final Set<EStructuralFeature> featuresToAdd = new LinkedHashSet<EStructuralFeature>();
		IItemPropertyDescriptor propertyDescriptor = null;
		for (final EStructuralFeature feature : features) {
			propertyDescriptor =
				adapterFactoryItemDelegator
					.getPropertyDescriptor(EcoreUtil.create(sad.getRootClass()), feature);
			if (propertyDescriptor != null) {
				featuresToAdd.add(feature);
			}
			else {
				logInvalidFeature(feature.getName(), sad.getRootClass().getName());
			}
		}
		composedAdapterFactory.dispose();
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction#execute(EObject)
	 */
	@Override
	public void execute(final EObject object) {
		if (!(VView.class.isInstance(object) || VContainedContainer.class.isInstance(object))) {
			return;
		}

		final Object obj = getView(object);
		if (obj == null) {
			return;
		}

		final VView view = (VView) obj;
		final EClass rootEClass = view.getRootEClass();
		final VElement container = getContainer(object);

		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final SelectAttributesDialog sad = new SelectAttributesDialog(new SelectAttributesWizard(), view, rootEClass,
			shell);
		final int result = sad.open();
		if (result == Window.OK) {
			final Set<EStructuralFeature> featuresToAdd = getFeaturesToCreate(sad);
			AdapterFactoryEditingDomain.getEditingDomainFor(view).getCommandStack()
				.execute(new ChangeCommand(view) {
					@Override
					protected void doExecute() {
						ControlGenerator.addControls(rootEClass, container, featuresToAdd);
					}
				});
		}
	}

	/**
	 * @param object
	 */
	private VView getView(EObject object) {

		if (object == null) {
			return null;
		}
		// TODO Create test cases for this method
		while (!(object instanceof VView)) {
			object = object.eContainer();
			if (object == null) {
				return null;
			}
		}
		return (VView) object;

	}

	private VElement getContainer(EObject object) {

		if (object == null) {
			return null;
		}
		// TODO Create test cases for this method
		while (!(object instanceof VView || object instanceof VContainedContainer)) {
			object = object.eContainer();
			if (object == null) {
				return null;
			}
		}
		return (VElement) object;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction#shouldShow(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean shouldShow(EObject object) {
		if (object == null) {
			return false;
		}
		return object instanceof VView || object instanceof VContainedContainer;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null || !(selection instanceof EObject)) {
			return null;
		}
		execute((EObject) selection);
		return null;
	}
}
