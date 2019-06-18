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

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateSegmentDmrWizard;
import org.eclipse.emf.ecp.view.internal.editor.handler.FeatureSegmentGenerator;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
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
 * @author Lucas Koehler
 *
 */
@Component(name = "MultiSegmentChildDmrNewModelElementStrategyProvider", property = "service.ranking:Integer=50")
public class MultiSegmentChildDmrNewModelElementStrategyProvider
	extends ReferenceServiceCustomizationVendor<CreateNewModelElementStrategy> implements Provider {

	private EMFFormsDatabindingEMF databindingService;
	private ReportService reportService;

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
	 * Set the {@link EMFFormsDatabindingEMF}.
	 *
	 * @param databindingService The {@link EMFFormsDatabindingEMF}
	 */
	@Reference
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
			final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) owner;
			final VDomainModelReference tableDmr = (VDomainModelReference) multiSegment.eContainer();
			final EClass viewRoot = Helper.getRootEClass(tableDmr);

			// Get root EClass of child dmr
			final IValueProperty<?, ?> valueProperty;
			try {
				valueProperty = databindingService.getValueProperty(tableDmr, viewRoot);
			} catch (final DatabindingFailedException ex) {
				reportService
					.report(new AbstractReport(ex, "No Child DMR could be created due to a databinding exception.")); //$NON-NLS-1$
				return Optional.empty();

			}
			final EReference multiRef = (EReference) valueProperty.getValueType();
			final EClass childDmrRoot = multiRef.getEReferenceType();

			final CreateSegmentDmrWizard dmrWizard = new CreateSegmentDmrWizard(
				childDmrRoot, "New Child Domain Model Reference", null, //$NON-NLS-1$
				structuralFeature -> null, new FeatureSegmentGenerator(), null, false);

			final WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), dmrWizard);
			wizardDialog.setBlockOnOpen(true);
			wizardDialog.open();

			return Optional.fromJavaOptional(dmrWizard.getDomainModelReference().map(EObject.class::cast));
		}
	}
}
