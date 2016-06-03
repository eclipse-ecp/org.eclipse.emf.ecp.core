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

import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * @author Alexandra Buzila
 *
 */
public class LeafConditionControlRenderer extends ExpectedValueControlRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	@Inject
	public LeafConditionControlRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ExpectedValueControlRenderer#onSelectButton()
	 */
	@Override
	protected void onSelectButton(Label control) {
		LeafCondition condition;
		try {
			condition = (LeafCondition) getObservedEObject();
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return;
		}

		if (condition.getDomainModelReference() == null) {
			MessageDialog.openError(control.getShell(), "No Feature Path Domain Model Reference found", //$NON-NLS-1$
				"A Feature Path Domain Model Reference needs to be added to the condition first. " //$NON-NLS-1$
			);
			return;
		}

		EStructuralFeature structuralFeature = ((VFeaturePathDomainModelReference) condition
			.getDomainModelReference()).getDomainModelEFeature();

		if (structuralFeature == null) {
			MessageDialog.openError(control.getShell(), "No value selected", //$NON-NLS-1$
				"Please set a value to the Domain Model Reference first. " //$NON-NLS-1$
			);
			return;
		}

		if (EReference.class.isInstance(structuralFeature)) {
			final EReference reference = EReference.class.cast(structuralFeature);
			final EClass referenceType = reference.getEReferenceType();
			final Collection<EClass> dmrEClasses = ECPUtil.getSubClasses(VViewPackage.eINSTANCE
				.getDomainModelReference());
			final Setting valueDMRSeting = ((LeafConditionImpl) condition).eSetting(RulePackage.eINSTANCE
				.getLeafCondition_ValueDomainModelReference());
			final CreateDomainModelReferenceWizard dmrWizard = new CreateDomainModelReferenceWizard(valueDMRSeting,
				getEditingDomain(valueDMRSeting.getEObject()), referenceType,
				"New Domain Model Reference", "New value reference", "New value reference", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"Select the attribute to be tested.", null); //$NON-NLS-1$
			final SelectionComposite<TreeViewer> compositeProvider = CompositeFactory.getSelectModelClassComposite(
				new HashSet<EPackage>(), new HashSet<EPackage>(), dmrEClasses);
			dmrWizard.setCompositeProvider(compositeProvider);
			final WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), dmrWizard);
			wizardDialog.open();
		}

		if (condition.getValueDomainModelReference() != null) {
			structuralFeature = ((VFeaturePathDomainModelReference) condition.getValueDomainModelReference())
				.getDomainModelEFeature();
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
					RulePackage.eINSTANCE.getLeafCondition_ExpectedValue(), object));

			control.setText(object.toString());
		}
	}
}
