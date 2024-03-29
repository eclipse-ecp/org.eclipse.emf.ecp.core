/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.swt.table.util;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.common.ServiceObjectTracker;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * A {@link CellLabelProvider} which points to a value specified by a {@link VDomainModelReference}.
 *
 * @author Johannes Faltermeier
 *
 */
public class DMRCellLabelProvider extends CellLabelProvider {

	private final VDomainModelReference dmr;
	private final EMFFormsDatabinding databindingService;

	private ServiceObjectTracker<EMFFormsDatabinding> databindingTracker;
	// TODO observable list needed?

	/**
	 * Constructs a new {@link DMRCellLabelProvider}.
	 *
	 * @param dmr the {@link VDomainModelReference}.
	 */
	public DMRCellLabelProvider(
		VDomainModelReference dmr) {
		this(dmr, null);
	}

	/**
	 *
	 * Constructs a new {@link DMRCellLabelProvider}.
	 *
	 * @param dmr the {@link VDomainModelReference}
	 * @param databindingService the {@link EMFFormsDatabinding databinding service}
	 */
	public DMRCellLabelProvider(
		VDomainModelReference dmr,
		EMFFormsDatabinding databindingService) {
		super();
		this.dmr = dmr;
		if (databindingService == null) {
			this.databindingService = getService();
		} else {
			this.databindingService = databindingService;
		}
	}

	@Override
	public String getToolTipText(Object element) {
		final EObject domainObject = (EObject) element;
		IObservableValue observableValue;
		try {
			observableValue = databindingService.getObservableValue(dmr, domainObject);
		} catch (final DatabindingFailedException ex) {
			return null;
		}
		final Object value = observableValue.getValue();
		observableValue.dispose();
		if (value == null) {
			return null;
		}
		return String.valueOf(value);
	}

	@Override
	public void update(ViewerCell cell) {
		final EObject element = (EObject) cell.getElement();
		IObservableValue observableValue;
		try {
			observableValue = databindingService.getObservableValue(dmr, element);
			final Object value = observableValue.getValue();
			observableValue.dispose();
			if (value == null) {
				cell.setText(""); //$NON-NLS-1$
			}
			cell.setText(String.valueOf(value));
		} catch (final DatabindingFailedException ex) {
			cell.setText(""); //$NON-NLS-1$
		}
	}

	private EMFFormsDatabinding getService() {
		final Bundle bundle = FrameworkUtil.getBundle(DMRCellLabelProvider.class);
		final BundleContext bundleContext = bundle.getBundleContext();
		databindingTracker = new ServiceObjectTracker<EMFFormsDatabinding>(bundleContext, EMFFormsDatabinding.class);

		return databindingTracker.getService();
	}

	@Override
	public void dispose() {
		databindingTracker.dispose();
		super.dispose();
	}

}