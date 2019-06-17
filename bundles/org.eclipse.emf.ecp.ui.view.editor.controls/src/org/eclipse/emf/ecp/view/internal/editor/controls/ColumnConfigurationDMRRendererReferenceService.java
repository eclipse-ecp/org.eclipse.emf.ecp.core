/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
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
 * Lucas Koehler - adapt to work with segments, too
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VSingleColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.view.spi.multisegment.model.MultiSegmentUtil;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * Special {@link ReferenceService} allowing stream lined DMR selection for the width configuration.
 *
 * @author Johannes Faltermeier
 *
 */
public class ColumnConfigurationDMRRendererReferenceService implements ReferenceService {

	private final Class<? extends VSingleColumnConfiguration> columnConfigClass;

	/**
	 * Constructor.
	 *
	 * @param columnConfigClass the {@link VSingleColumnConfiguration} based class to be filtered
	 */
	public ColumnConfigurationDMRRendererReferenceService(
		Class<? extends VSingleColumnConfiguration> columnConfigClass) {
		this.columnConfigClass = columnConfigClass;
	}

	@Override
	public void instantiate(ViewModelContext context) {
		/* no-op */
	}

	@Override
	public void dispose() {
		/* no-op */
	}

	@Override
	public int getPriority() {
		/* no-op */
		return 0;
	}

	@Deprecated
	@Override
	public void addNewModelElements(EObject eObject, EReference eReference) {
		/* no-op */
	}

	@Override
	public org.eclipse.emfforms.common.Optional<EObject> addNewModelElements(EObject eObject, EReference eReference,
		boolean openInNewContext) {
		/* no-op */
		return org.eclipse.emfforms.common.Optional.empty();
	}

	@SuppressWarnings("restriction")
	@Override
	public void addExistingModelElements(EObject eObject, EReference eReference) {
		if (!VTableControl.class.isInstance(eObject.eContainer())) {
			return;
		}
		final VTableControl tableControl = VTableControl.class.cast(eObject.eContainer());
		final Optional<Set<EObject>> unconfiguredColumns = getUnconfiguredColumns(
			tableControl.getDomainModelReference());
		if (!unconfiguredColumns.isPresent()) {
			return;
		}
		for (final VTableColumnConfiguration columnConfiguration : tableControl.getColumnConfigurations()) {
			if (!columnConfigClass.isInstance(columnConfiguration)) {
				continue;
			}
			unconfiguredColumns.get()
				.remove(columnConfigClass.cast(columnConfiguration).getColumnDomainReference());
		}

		final Set<EObject> selectedColumns = SelectModelElementWizardFactory
			.openModelElementSelectionDialog(
				unconfiguredColumns.get(),
				eReference.isMany());

		org.eclipse.emf.ecp.internal.edit.ECPControlHelper.addModelElementsInReference(
			eObject,
			selectedColumns,
			eReference,
			AdapterFactoryEditingDomain.getEditingDomainFor(eObject));
	}

	private Optional<Set<EObject>> getUnconfiguredColumns(VDomainModelReference dmr) {
		Set<EObject> result = null;
		if (!dmr.getSegments().isEmpty()) {
			final Optional<VMultiDomainModelReferenceSegment> multiSegment = MultiSegmentUtil.getMultiSegment(dmr);
			if (multiSegment.isPresent()) {
				result = new LinkedHashSet<>(multiSegment.get().getChildDomainModelReferences());
			}
		} else if (dmr instanceof VTableDomainModelReference) {
			final VTableDomainModelReference tableDmr = (VTableDomainModelReference) dmr;
			result = new LinkedHashSet<>(tableDmr.getColumnDomainModelReferences());
		}
		return Optional.ofNullable(result);
	}

	@Override
	public void openInNewContext(EObject eObject) {
		/* no-op */
	}

}