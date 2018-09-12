/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Label;

/**
 * @author Alexandra Buzila
 *
 */
public class StackItemControlRenderer extends ExpectedValueControlRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param databindingService The {@link EMFFormsDatabinding}
	 * @param labelProvider The {@link EMFFormsLabelProvider}
	 * @param viewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	@Inject
	public StackItemControlRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding databindingService, EMFFormsLabelProvider labelProvider,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService, databindingService, labelProvider, viewTemplateProvider);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ExpectedValueControlRenderer#onSelectButton(org.eclipse.swt.widgets.Text)
	 */
	@Override
	protected void onSelectButton(Label control) {
		VStackItem stackItem;
		try {
			stackItem = (VStackItem) getObservedEObject();
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new DatabindingFailedReport(ex));
			return;
		}
		final VStackLayout eContainer = (VStackLayout) stackItem.eContainer();

		if (eContainer.getDomainModelReference() == null) {
			MessageDialog.openError(control.getShell(), "No Feature Path Domain Model Reference found", //$NON-NLS-1$
				"A Feature Path Domain Model Reference needs to be added to the Stack Layout first. " //$NON-NLS-1$
			);
			return;
		}
		final EStructuralFeature structuralFeature = ((VFeaturePathDomainModelReference) eContainer
			.getDomainModelReference()).getDomainModelEFeature();
		if (structuralFeature == null) {
			MessageDialog.openError(control.getShell(), "No value selected", //$NON-NLS-1$
				"Please set a value to the Domain Model Reference first. " //$NON-NLS-1$
			);
			return;
		}
		if (EReference.class.isInstance(structuralFeature)) {
			// TODO show all references
			return;
		}

		final Object object = getSelectedObject((EAttribute) structuralFeature);

		if (object != null) {
			final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(stackItem);
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, stackItem,
					VStackPackage.eINSTANCE.getStackItem_Value(), object));

			control.setText(object.toString());
		}
	}
}
