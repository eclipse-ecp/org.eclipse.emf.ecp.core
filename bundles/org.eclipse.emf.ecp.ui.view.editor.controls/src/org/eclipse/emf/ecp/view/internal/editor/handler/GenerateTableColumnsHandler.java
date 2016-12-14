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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.editor.controls.Activator;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Eugen
 *
 */
public class GenerateTableColumnsHandler extends MasterDetailAction {
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null || !(selection instanceof EObject)) {
			return null;
		}
		execute((EObject) selection);
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction#shouldShow(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean shouldShow(EObject eObject) {
		if (VTableControl.class.isInstance(eObject)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction#execute(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void execute(EObject object) {
		final VTableControl tableControl = VTableControl.class.cast(object);
		final VDomainModelReference domainModelReference = tableControl.getDomainModelReference();
		if (domainModelReference == null || domainModelReference.getSegments().isEmpty()) {
			return;
		}
		final EClass rootEClass = Helper.getRootEClass(object);

		IValueProperty valueProperty;
		try {
			valueProperty = Activator.getDefault().getEMFFormsDatabinding().getValueProperty(domainModelReference,
				rootEClass);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return;
		}
		final Object eStructuralFeature = valueProperty.getValueType();
		if (!EReference.class.isInstance(eStructuralFeature)) {
			return;
		}
		final EReference eReference = (EReference) eStructuralFeature;

		// Get multi segment
		final VDomainModelReferenceSegment lastSegment = Helper.getLastSegment(domainModelReference);
		if (!VMultiDomainModelReferenceSegment.class.isInstance(lastSegment)) {
			return;
		}
		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) lastSegment;

		// Get features for which a child dmr already exists
		final Set<EStructuralFeature> generatedFeatures = new LinkedHashSet<EStructuralFeature>();
		for (final VDomainModelReference childDmr : multiSegment.getChildDomainModelReferences()) {
			try {
				final IValueProperty childValueProperty = Activator.getDefault().getEMFFormsDatabinding()
					.getValueProperty(childDmr, eReference.getEReferenceType());
				final EStructuralFeature childStructuralFeature = (EStructuralFeature) childValueProperty
					.getValueType();
				generatedFeatures.add(childStructuralFeature);
			} catch (final DatabindingFailedException e) {
				Activator.getDefault().getReportService().report(new DatabindingFailedReport(e));
				return;
			}
		}
		// Generate child references for remaining attributes
		final Set<VDomainModelReference> references = new LinkedHashSet<VDomainModelReference>();
		for (final EAttribute attribute : eReference.getEReferenceType().getEAllAttributes()) {
			if (generatedFeatures.contains(attribute)) {
				continue;
			}
			final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
			final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
				.createFeatureDomainModelReferenceSegment();
			featureSegment.setDomainModelFeature(attribute.getName());
			dmr.getSegments().add(featureSegment);
			references.add(dmr);

		}

		final EditingDomain editingDomainFor = AdapterFactoryEditingDomain.getEditingDomainFor(object);
		final Command command = AddCommand.create(editingDomainFor, multiSegment,
			VMultisegmentPackage.eINSTANCE.getMultiDomainModelReferenceSegment_ChildDomainModelReferences(),
			references);
		editingDomainFor.getCommandStack().execute(command);

	}
}
