/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import javax.inject.Inject;

import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.rule.RuleConditionDmrUtil;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Renders a control to select the expected value of a LeafCondition which uses a segment based domain model
 * reference.
 * <p>
 * This implementation does not support selecting a value dmr anymore.
 * If iterating over a multi reference is wanted, this should be done with an
 * {@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition IterateCondition} instead.
 *
 * @author Lucas Koehler
 *
 */
public class LeafConditionSegmentControlRenderer extends ExpectedValueControlRenderer {

	/**
	 * @param vElement The {@link VControl}
	 * @param viewContext The {@link ViewModelContext}
	 * @param reportService The {@link ReportService}
	 * @param databindingService The {@link EMFFormsDatabindingEMF}
	 * @param labelProvider The {@link EMFFormsLabelProvider}
	 * @param viewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	@Inject
	public LeafConditionSegmentControlRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabindingEMF databindingService, EMFFormsLabelProvider labelProvider,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService, databindingService, labelProvider, viewTemplateProvider);
	}

	@Override
	protected void onSelectButton(Label control) {
		LeafCondition condition;
		try {
			condition = (LeafCondition) getObservedEObject();
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new DatabindingFailedReport(ex));
			return;
		}

		if (condition.getDomainModelReference() == null) {
			showError(control.getShell(), "No Domain Model Reference found", //$NON-NLS-1$
				"A Domain Model Reference needs to be added to the condition first."); //$NON-NLS-1$
			return;
		}

		EStructuralFeature structuralFeature;
		try {
			final EClass conditionRoot = RuleConditionDmrUtil.getDmrRootEClass(getEMFFormsDatabinding(),
				getReportService(), condition).orElse(null);
			final IEMFValueProperty valueProperty = getEMFFormsDatabinding()
				.getValueProperty(condition.getDomainModelReference(), conditionRoot);
			structuralFeature = valueProperty.getStructuralFeature();
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new DatabindingFailedReport(ex));
			return;
		}

		if (!EAttribute.class.isInstance(structuralFeature)) {
			showError(control.getShell(), "No EAttribute selected", //$NON-NLS-1$
				"The condition's domain model reference must point to an EAttribute."); //$NON-NLS-1$
			return;
		}

		if (condition.getValueDomainModelReference() != null) {
			showInfo(control.getShell(), "Legacy Value DMR will be removed", //$NON-NLS-1$
				"The LeafCondition's Value DMR is deprecated with the usage of segments. This LeafCondition's value DMR is removed now. If you want to iterate over a multi reference, please use the Iterate Condition."); //$NON-NLS-1$
			// This should not be undoable
			condition.setValueDomainModelReference(null);
		}

		final Object object = getSelectedObject((EAttribute) structuralFeature);
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(condition);
		editingDomain.getCommandStack().execute(
			SetCommand.create(editingDomain, condition, RulePackage.Literals.LEAF_CONDITION__EXPECTED_VALUE, object));

		if (object != null) {
			control.setText(object.toString());
		} else {
			control.setText("null"); //$NON-NLS-1$
		}
	}

	@Override
	protected EMFFormsDatabindingEMF getEMFFormsDatabinding() {
		return (EMFFormsDatabindingEMF) super.getEMFFormsDatabinding();
	}

	/**
	 * Opens a dialog showing information to the user.
	 *
	 * @param parent The parent {@link Shell}
	 * @param title The title of the warning dialog
	 * @param message The message describing the warning
	 */
	protected void showInfo(Shell parent, String title, String message) {
		MessageDialog.openInformation(parent, title, message);
	}
}
