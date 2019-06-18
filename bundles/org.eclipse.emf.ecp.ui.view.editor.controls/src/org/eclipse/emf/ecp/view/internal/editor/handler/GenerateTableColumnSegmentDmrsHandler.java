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

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.controls.Activator;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.MultiSegmentUtil;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Tree master detail action that generates segment based table column DMRs. This action is only used in the segment
 * mode of the tooling.
 *
 * @author Lucas Koehler
 */
public class GenerateTableColumnSegmentDmrsHandler extends MasterDetailAction {

	private final ReportService reportService;
	private final EMFFormsDatabindingEMF databinding;

	/**
	 * Creates a new instance. Gets needed services from the bundle activator.
	 */
	public GenerateTableColumnSegmentDmrsHandler() {
		this(Activator.getDefault().getEMFFormsDatabinding(), Activator.getDefault().getReportService());
	}

	/**
	 * Creates a new instance.
	 *
	 * @param databinding The {@link EMFFormsDatabindingEMF}
	 * @param reportService The {@link ReportService}
	 */
	GenerateTableColumnSegmentDmrsHandler(EMFFormsDatabindingEMF databinding, ReportService reportService) {
		this.databinding = databinding;
		this.reportService = reportService;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null || !(selection instanceof EObject)) {
			return null;
		}
		execute((EObject) selection);
		return null;
	}

	@Override
	public boolean shouldShow(EObject eObject) {
		return VTableControl.class.isInstance(eObject) && ToolingModeUtil.isSegmentToolingEnabled();
	}

	// BEGIN COMPLEX CODE
	@Override
	public void execute(EObject object) {
		final VTableControl tableControl = (VTableControl) object;
		final EClass viewRootEClass = Helper.getRootEClass(tableControl);
		if (viewRootEClass == null) {
			reportService.report(new AbstractReport(
				"Could not generate column dmrs because the view's root EClass could not be determined.")); //$NON-NLS-1$
			return;
		}

		final VDomainModelReference multiDmr = tableControl.getDomainModelReference();
		if (multiDmr == null) {
			reportService.report(new AbstractReport(
				"Could not generate column dmrs because the table control's dmr is null.")); //$NON-NLS-1$
			return;
		}

		final Optional<VMultiDomainModelReferenceSegment> multiSegment = MultiSegmentUtil.getMultiSegment(multiDmr);
		if (!multiSegment.isPresent()) {
			reportService.report(new AbstractReport(
				"Could not generate column dmrs because the table control's dmr doesn't end in a multi segment")); //$NON-NLS-1$
			return;
		}

		IEMFValueProperty tableProperty;
		try {
			tableProperty = databinding.getValueProperty(multiDmr, viewRootEClass);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new AbstractReport(ex, "Could not generate column dmrs.")); //$NON-NLS-1$
			return;
		}
		final EStructuralFeature tableFeature = tableProperty.getStructuralFeature();
		if (!(tableFeature instanceof EReference)) {
			reportService.report(new AbstractReport(
				"Could not generate column dmrs because the table control's dmr doesn't end in an EReference.")); //$NON-NLS-1$
			return;
		}
		final EReference tableReference = (EReference) tableFeature;
		final Optional<EClass> columnRoot = getColumnDmrRootEClass(tableReference.getEReferenceType());
		if (!columnRoot.isPresent()) {
			return;
		}

		// Collect features which a column dmr already exists for
		final Set<EStructuralFeature> existingChildDmrFeatures = new LinkedHashSet<EStructuralFeature>();
		for (final VDomainModelReference ref : multiSegment.get().getChildDomainModelReferences()) {
			IEMFValueProperty property;
			try {
				property = databinding.getValueProperty(ref, columnRoot.get());
			} catch (final DatabindingFailedException ex) {
				reportService.report(new AbstractReport(ex));
				continue;
			}
			existingChildDmrFeatures.add(property.getStructuralFeature());
		}

		// Generate DMRs for remaining EAttributes
		final Set<VDomainModelReference> generatedChildDmrs = new LinkedHashSet<VDomainModelReference>();
		columnRoot.get().getEAllAttributes().stream()
			.filter(attribute -> !existingChildDmrFeatures.contains(attribute))
			.map(EAttribute::getName)
			.map(GenerateTableColumnSegmentDmrsHandler::createFeatureSegment)
			.map(GenerateTableColumnSegmentDmrsHandler::createDmr)
			.forEach(generatedChildDmrs::add);

		// Add generated references via command to the multi segment
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment.get());
		final Command command = AddCommand.create(editingDomain, multiSegment.get(),
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES,
			generatedChildDmrs);
		editingDomain.getCommandStack().execute(command);
	}
	// END COMPLEX CODE

	/**
	 * Returns the root EClass of the generated column dmrs. By default this is the same as the given baseEClass. This
	 * can be overridden to select a subclass of the base EClass.
	 *
	 * @param baseEClass The reference type which the multi dmr points to
	 * @return The root EClass for the column dmrs or nothing if none was determined
	 */
	protected Optional<EClass> getColumnDmrRootEClass(EClass baseEClass) {
		return Optional.of(baseEClass);
	}

	private static VFeatureDomainModelReferenceSegment createFeatureSegment(String featureName) {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(featureName);
		return segment;
	}

	private static VDomainModelReference createDmr(VDomainModelReferenceSegment segment) {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segment);
		return dmr;
	}
}
