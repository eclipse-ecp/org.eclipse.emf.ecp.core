/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.columnservice;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.table.generator.TableColumnGenerator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * This service will iterate over all contents of the {@link org.eclipse.emf.ecp.view.spi.model.VView VView} and will
 * add {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReferences} for every
 * {@link VTableControl} without columns.
 *
 * @author jfaltermeier
 *
 */
public class AddColumnService implements ViewModelService {

	private ViewModelContext context;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		final VElement viewModel = context.getViewModel();
		if (viewModel instanceof VTableControl) {
			addColumnsIfNeeded((VTableControl) viewModel);
			return;
		}
		final TreeIterator<EObject> contents = viewModel.eAllContents();
		while (contents.hasNext()) {
			final EObject current = contents.next();
			if (current instanceof VTableControl) {
				final VTableControl tableControl = (VTableControl) current;
				addColumnsIfNeeded(tableControl);
			}
		}
	}

	private void addColumnsIfNeeded(VTableControl tableControl) {
		if (tableControl.getDomainModelReference() == null) {
			return;
		}
		if (!VTableDomainModelReference.class.isInstance(tableControl.getDomainModelReference())) {
			return;
		}
		final VTableDomainModelReference tableDmr = VTableDomainModelReference.class
			.cast(tableControl.getDomainModelReference());

		boolean addColumns = false;
		boolean segments = false;
		if (tableDmr.getSegments().size() > 0) {
			final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) tableDmr
				.getSegments().get(tableDmr.getSegments().size() - 1);
			addColumns = multiSegment.getChildDomainModelReferences().size() < 1;
			segments = true;
		} else {
			addColumns = tableDmr.getColumnDomainModelReferences().size() < 1;
		}

		if (addColumns) {
			final VTableDomainModelReference tableDMR = (VTableDomainModelReference) tableControl
				.getDomainModelReference();
			final IValueProperty valueProperty;
			try {
				if (segments || tableDMR.getDomainModelReference() == null) {
					valueProperty = context.getService(EMFFormsDatabinding.class)
						.getValueProperty(tableDMR, context.getDomainModel());
				} else {
					valueProperty = context.getService(EMFFormsDatabinding.class)
						.getValueProperty(tableDMR.getDomainModelReference(), context.getDomainModel());
				}
			} catch (final DatabindingFailedException ex) {
				context.getService(ReportService.class).report(new DatabindingFailedReport(ex));
				return;
			}

			final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
			final EClassifier eType = structuralFeature.getEType();
			if (eType instanceof EClass) {
				final EClass clazz = (EClass) eType;
				TableColumnGenerator.generateColumns(clazz, tableControl);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		// nothing to dispose
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return -1;
	}

}
