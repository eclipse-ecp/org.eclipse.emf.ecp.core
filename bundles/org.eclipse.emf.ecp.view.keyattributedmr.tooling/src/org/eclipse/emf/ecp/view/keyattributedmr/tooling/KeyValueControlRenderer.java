/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.keyattributedmr.tooling;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.controls.ExpectedValueControlRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Label;

/**
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class KeyValueControlRenderer extends ExpectedValueControlRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	public KeyValueControlRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@Override
	protected void onSelectButton(Label control) {
		VKeyAttributeDomainModelReference condition;
		try {
			condition = (VKeyAttributeDomainModelReference) getObservedEObject();
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return;
		}

		if (!VFeaturePathDomainModelReference.class.isInstance(condition.getKeyDMR())) {
			MessageDialog.openError(control.getShell(), "No Feature Path Domain Model Reference found", //$NON-NLS-1$
				"A Feature Path Domain Model Reference needs to be added to the VKeyAttributeDomainModelReference first. " //$NON-NLS-1$
			);
			return;
		}
		final EStructuralFeature structuralFeature = ((VFeaturePathDomainModelReference) condition
			.getKeyDMR()).getDomainModelEFeature();
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
			final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(condition);
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, condition,
					VKeyattributedmrPackage.eINSTANCE.getKeyAttributeDomainModelReference_KeyValue(), object));

			control.setText(object.toString());
		}
	}
}
