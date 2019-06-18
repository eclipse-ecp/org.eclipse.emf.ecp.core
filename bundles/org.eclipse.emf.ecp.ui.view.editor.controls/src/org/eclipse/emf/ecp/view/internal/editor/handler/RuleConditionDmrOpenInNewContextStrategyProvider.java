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
import java.util.Optional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.rule.RuleConditionDmrUtil;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides a strategy to edit a <strong>segment-based</strong> {@link VDomainModelReference} for a rule
 * {@link Condition}. This provider is only active if the IDE tooling runs in segment mode.
 * <p>
 * Overwrite {@link #getSelectionValidator()} to customize valid feature selections for specific condition types.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "RuleConditionDmrOpenInNewContextStrategyProvider", property = "service.ranking:Integer=30")
public class RuleConditionDmrOpenInNewContextStrategyProvider
	extends ReferenceServiceCustomizationVendor<OpenInNewContextStrategy> implements Provider {

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
	 * Creates the {@link OpenInNewContextStrategy}.
	 *
	 * @return The created {@link OpenInNewContextStrategy}
	 */
	@Create
	public OpenInNewContextStrategy createOpenInNewContextStrategy() {
		return new Strategy();
	}

	/**
	 * This strategy allows to edit a segment-based child dmr in a {@link CreateSegmentDmrWizard}.
	 *
	 * @author Lucas Koehler
	 */
	class Strategy implements OpenInNewContextStrategy {

		@Override
		public boolean openInNewContext(EObject owner, EReference reference, EObject object) {
			final java.util.Optional<EClass> rootEClass = RuleConditionDmrUtil.getDmrRootEClass(databinding,
				reportService, owner);
			if (!rootEClass.isPresent()) {
				final String message = MessageFormat.format(
					"Could not edit the domain model reference for condition ''{0}'' because no root EClass could be determined.", //$NON-NLS-1$
					owner);
				reportService.report(new AbstractReport(message, IStatus.WARNING));
				return false;
			}

			final CreateSegmentDmrWizard dmrWizard = new CreateSegmentDmrWizard(rootEClass.get(),
				"Edit Domain Model Reference", (VDomainModelReference) object, getSelectionValidator()); //$NON-NLS-1$

			final WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), dmrWizard);
			wizardDialog.setBlockOnOpen(true);
			wizardDialog.open();

			// Replace old dmr with edited version
			final Optional<VDomainModelReference> dmr = dmrWizard.getDomainModelReference();
			if (dmr.isPresent()) {
				final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);
				// The DMR is always set in a single reference
				final Command command = SetCommand.create(domain, owner, reference, dmr.get());
				domain.getCommandStack().execute(command);
			}

			return true;
		}
	}
}
