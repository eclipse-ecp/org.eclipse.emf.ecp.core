/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.columnservice;

import java.util.Iterator;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.internal.table.generator.TableColumnGenerator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;

/**
 * This service will iterate over all contents of the {@link org.eclipse.emf.ecp.view.spi.model.VView VView} and will
 * add {@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumn VTableColumn}s for every {@link VTableControl}
 * without columns.
 * 
 * @author jfaltermeier
 * 
 */
public class AddColumnService implements ViewModelService {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	public void instantiate(ViewModelContext context) {
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
		if (tableControl.getColumns() == null || tableControl.getColumns().size() < 1) {
			final Iterator<Setting> settings = tableControl.getDomainModelReference().getIterator();
			if (!settings.hasNext()) {
				return;
			}
			final Setting firstSetting = settings.next();
			final EStructuralFeature structuralFeature = firstSetting.getEStructuralFeature();
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
	public void dispose() {
		// nothing to dispose
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	public int getPriority() {
		return -1;
	}

}
