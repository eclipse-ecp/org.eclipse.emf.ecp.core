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
package org.eclipse.emfforms.internal.core.services.segments.multi;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureDmrToRootEClassConverter;
import org.eclipse.emfforms.spi.core.services.segments.DmrToRootEClassConverter;
import org.eclipse.emfforms.spi.core.services.segments.LegacyDmrToRootEClass;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Converts a {@link VTableDomainModelReference} to its root EClass.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "TableDmrToRootEClassConverter", service = DmrToRootEClassConverter.class)
public class TableDmrToRootEClassConverter extends FeatureDmrToRootEClassConverter {

	private LegacyDmrToRootEClass dmrToRootEClass;
	private ServiceReference<LegacyDmrToRootEClass> dmrToRootEClassServiceRef;

	@Override
	public double isApplicable(VDomainModelReference dmr) {
		if (dmr instanceof VTableDomainModelReference) {
			return 3d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public EClass getRootEClass(VDomainModelReference dmr) throws IllegalArgumentException {
		final VTableDomainModelReference tableDmr = (VTableDomainModelReference) dmr;
		if (tableDmr.getDomainModelReference() == null) {
			return super.getRootEClass(dmr);
		}

		return getLegacyDmrToRootEClass().getRootEClass(tableDmr.getDomainModelReference())
			.orElseThrow(() -> new IllegalArgumentException(
				"Could not determine root EClass of the table dmr's dmr: " + tableDmr.getDomainModelReference())); //$NON-NLS-1$
	}

	/** Ungets manually retrieved LegacyDmrToRootEClass. */
	@Deactivate
	public void dispose() {
		dmrToRootEClass = null;
		if (dmrToRootEClassServiceRef != null) {
			FrameworkUtil.getBundle(TableDmrToRootEClassConverter.class).getBundleContext()
				.ungetService(dmrToRootEClassServiceRef);
			dmrToRootEClassServiceRef = null;
		}
	}

	// Lazily get the LegacyDmrToRootEClass to avoid circular dependency during service creation
	private LegacyDmrToRootEClass getLegacyDmrToRootEClass() {
		if (dmrToRootEClass == null) {
			final BundleContext bundleContext = FrameworkUtil.getBundle(TableDmrToRootEClassConverter.class)
				.getBundleContext();
			dmrToRootEClassServiceRef = bundleContext.getServiceReference(LegacyDmrToRootEClass.class);
			dmrToRootEClass = bundleContext.getService(dmrToRootEClassServiceRef);
		}
		return dmrToRootEClass;
	}

	/**
	 * Sets the {@link LegacyDmrToRootEClass}. Package visibility for testing.
	 *
	 * @param dmrToRootEClass The {@link LegacyDmrToRootEClass} to use
	 */
	void setLegacyDmrToRootEClass(LegacyDmrToRootEClass dmrToRootEClass) {
		this.dmrToRootEClass = dmrToRootEClass;
	}
}
