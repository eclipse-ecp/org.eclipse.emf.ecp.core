/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.table.model.VSingleColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

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

	@Override
	public void addNewModelElements(EObject eObject, EReference eReference) {
		/* no-op */
	}

	@SuppressWarnings("restriction")
	@Override
	public void addExistingModelElements(EObject eObject, EReference eReference) {
		if (!VTableControl.class.isInstance(eObject.eContainer())) {
			return;
		}
		final VTableControl tableControl = VTableControl.class.cast(eObject.eContainer());
		if (!VTableDomainModelReference.class.isInstance(tableControl.getDomainModelReference())) {
			return;
		}
		final VTableDomainModelReference tableDMR = VTableDomainModelReference.class
			.cast(tableControl.getDomainModelReference());
		final Set<EObject> unconfiguredColumns = new LinkedHashSet<EObject>(
			tableDMR.getColumnDomainModelReferences());
		for (final VTableColumnConfiguration columnConfiguration : tableControl.getColumnConfigurations()) {
			if (!columnConfigClass.isInstance(columnConfiguration)) {
				continue;
			}
			unconfiguredColumns
				.remove(columnConfigClass.cast(columnConfiguration).getColumnDomainReference());
		}

		final Set<EObject> selectedColumns = SelectModelElementWizardFactory
			.openModelElementSelectionDialog(
				unconfiguredColumns,
				eReference.isMany());

		org.eclipse.emf.ecp.internal.edit.ECPControlHelper.addModelElementsInReference(
			eObject,
			selectedColumns,
			eReference,
			AdapterFactoryEditingDomain.getEditingDomainFor(eObject));
	}

	@Override
	public void openInNewContext(EObject eObject) {
		/* no-op */
	}

}