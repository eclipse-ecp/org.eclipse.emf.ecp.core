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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.rule.RuleConditionDmrUtil;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides a strategy to create and configure a <strong>segment-based</strong> {@link VDomainModelReference} for a rule
 * {@link Condition}. This provider is only active if the IDE tooling runs in segment mode.
 * <p>
 * Overwrite {@link #getSelectionValidator()} to customize valid feature selections for specific condition types.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "RuleConditionDmrNewModelElementStrategyProvider", property = "service.ranking:Integer=30")
public class RuleConditionDmrNewModelElementStrategyProvider
	extends ReferenceServiceCustomizationVendor<CreateNewModelElementStrategy> implements Provider {

	private EMFFormsDatabindingEMF databinding;
	private ReportService reportService;

	/**
	 * Set the {@link EMFFormsDatabindingEMF}.
	 *
	 * @param databinding The {@link EMFFormsDatabindingEMF}
	 */
	@Reference(unbind = "-")
	void setEMFFormsDatabindingEMF(EMFFormsDatabindingEMF databinding) {
		this.databinding = databinding;
	}

	/**
	 * Set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return isSegmentToolingEnabled()
			&& owner instanceof Condition
			&& reference.getEReferenceType() == VViewPackage.Literals.DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * @return true if segment based tooling is enabled
	 */
	protected boolean isSegmentToolingEnabled() {
		return ToolingModeUtil.isSegmentToolingEnabled();
	}

	/**
	 * Returns the {@link EStructuralFeatureSelectionValidator} used by the DMR creation wizard to validate the
	 * structural feature selection.
	 * <p>
	 * Overwrite this to customize valid selections
	 *
	 * @return The {@link EStructuralFeatureSelectionValidator}
	 */
	protected EStructuralFeatureSelectionValidator getSelectionValidator() {
		return feature -> null;
	}

	/**
	 * Creates the {@link CreateNewModelElementStrategy}.
	 *
	 * @return The created {@link CreateNewModelElementStrategy}
	 */
	@Create
	public CreateNewModelElementStrategy createCreateNewModelElementStrategy() {
		return new Strategy();
	}

	/**
	 * This strategy allows to create a new segment-based child dmr in a {@link CreateSegmentDmrWizard}.
	 *
	 * @author Lucas Koehler
	 */
	class Strategy implements CreateNewModelElementStrategy {

		@Override
		public Optional<EObject> createNewModelElement(EObject owner, EReference reference) {
			final java.util.Optional<EClass> rootEClass = RuleConditionDmrUtil.getDmrRootEClass(databinding,
				reportService, owner);
			if (!rootEClass.isPresent()) {
				final String message = MessageFormat.format(
					"Could not create a new domain model reference for condition ''{0}'' because no root EClass could be determined.", //$NON-NLS-1$
					owner);
				reportService.report(new AbstractReport(message, IStatus.WARNING));
				return Optional.empty();
			}

			final CreateSegmentDmrWizard dmrWizard = new CreateSegmentDmrWizard(rootEClass.get(),
				"New Domain Model Reference", null, getSelectionValidator()); //$NON-NLS-1$

			final WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), dmrWizard);
			wizardDialog.setBlockOnOpen(true);
			wizardDialog.open();

			return Optional.fromJavaOptional(dmrWizard.getDomainModelReference().map(EObject.class::cast));
		}
	}
}
