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
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import java.util.Collections;
import java.util.Optional;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateSegmentDmrWizard;
import org.eclipse.emf.ecp.view.internal.editor.handler.FeatureSegmentGenerator;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides a strategy that opens a dialog to edit a child domain model reference of a multi segment. This provider is
 * only active if the IDE tooling runs in segment mode.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MultiSegmentChildDmrOpenInNewContextStrategyProvider", property = "service.ranking:Integer=50")
public class MultiSegmentChildDmrOpenInNewContextStrategyProvider
	extends ReferenceServiceCustomizationVendor<OpenInNewContextStrategy> implements Provider {

	private EMFFormsDatabindingEMF databindingService;
	private ReportService reportService;

	/**
	 * Set the {@link EMFFormsDatabindingEMF}.
	 *
	 * @param databindingService The {@link EMFFormsDatabindingEMF}
	 */
	@Reference(unbind = "-")
	void setEMFFormsDatabinding(EMFFormsDatabindingEMF databindingService) {
		this.databindingService = databindingService;
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

	/**
	 * Creates the strategy a multi segment's child dmr in a dialog.
	 *
	 * @return The strategy
	 */
	@Create
	public OpenInNewContextStrategy createOpenInNewContextStrategy() {
		return new Strategy();
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return isSegmentToolingEnabled()
			&& owner instanceof VMultiDomainModelReferenceSegment
			&& reference == VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES;
	}

	/**
	 * @return true if segment based tooling is enabled
	 */
	boolean isSegmentToolingEnabled() {
		return ToolingModeUtil.isSegmentToolingEnabled();
	}

	/**
	 * This strategy opens the child dmr in a {@link CreateSegmentDmrWizard}.
	 *
	 * @author Lucas Koehler
	 */
	class Strategy implements OpenInNewContextStrategy {

		@Override
		public boolean openInNewContext(EObject owner, EReference reference, EObject object) {
			final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) owner;
			final VDomainModelReference tableDmr = (VDomainModelReference) multiSegment.eContainer();
			final EClass viewRoot = Helper.getRootEClass(tableDmr);

			// Get root EClass of child dmr
			final IValueProperty<?, ?> valueProperty;
			try {
				valueProperty = databindingService.getValueProperty(tableDmr, viewRoot);
			} catch (final DatabindingFailedException ex) {
				reportService.report(new AbstractReport(ex, String.format(
					"Child DMR %s could not be opened in a new context due to a databinding exception.", object))); //$NON-NLS-1$
				return false;

			}
			final EReference multiRef = (EReference) valueProperty.getValueType();
			final EClass childDmrRoot = multiRef.getEReferenceType();

			final VDomainModelReference childDmr = (VDomainModelReference) object;
			// Open dmr wizard for the existing dmr.
			final CreateSegmentDmrWizard dmrWizard = new CreateSegmentDmrWizard(
				childDmrRoot, "Edit Child Domain Model Reference", childDmr, //$NON-NLS-1$
				structuralFeature -> null, new FeatureSegmentGenerator(), null, false);

			final WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), dmrWizard);
			wizardDialog.setBlockOnOpen(true);
			wizardDialog.open();

			// Replace old dmr with edited version
			final Optional<VDomainModelReference> dmr = dmrWizard.getDomainModelReference();
			final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);
			if (dmr.isPresent()) {
				final Command replace = ReplaceCommand.create(domain, owner, reference, childDmr,
					Collections.singleton(dmr.get()));
				domain.getCommandStack().execute(replace);
			}

			return true;
		}

	}
}
