/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * Renderer for the Attribute element of the View Model Element Selector class.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class AttributeSelectControlSWTRenderer extends EditableEReferenceLabelControlSWTRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	public AttributeSelectControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer#linkValue(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void linkValue(Shell shell) {
		IObservableValue observableValue;
		try {
			observableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			showLinkValueFailedMessageDialog(shell, ex);
			return;
		}
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(
			adapterFactory);
		final ListDialog ld = new ListDialog(shell);
		ld.setLabelProvider(labelProvider);
		ld.setContentProvider(ArrayContentProvider.getInstance());
		ld.setInput(VTViewModelElementSelector.class.cast(eObject).getClassType().getEAllAttributes());
		ld.setBlockOnOpen(true);
		final int open = ld.open();
		adapterFactory.dispose();
		labelProvider.dispose();
		if (Window.CANCEL == open) {
			return;
		}

		final Object result = ld.getResult()[0];

		final EditingDomain editingDomain = getEditingDomain(eObject);
		final Command command = SetCommand.create(editingDomain, eObject, structuralFeature, result);
		editingDomain.getCommandStack().execute(command);
	}

}
