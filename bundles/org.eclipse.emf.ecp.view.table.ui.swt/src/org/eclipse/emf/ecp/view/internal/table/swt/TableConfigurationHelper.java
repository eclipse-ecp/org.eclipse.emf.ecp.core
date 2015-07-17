/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;

/**
 * Helper class for retrieving configuration information.
 *
 * @author Eugen Neufeld
 *
 */
public final class TableConfigurationHelper {

	private TableConfigurationHelper() {

	}

	/**
	 * Retrieve the readonly state for the table control of a {@link VDomainModelReference}.
	 *
	 * @param tableControl the {@link VTableControl} to get the readonly state of a column for
	 * @param domainModelReference the {@link VDomainModelReference} to get the state for
	 * @return true if the domainModelReference is readonly, false otherwise
	 */
	public static boolean isReadOnly(VTableControl tableControl, VDomainModelReference domainModelReference) {
		final boolean readOnly = false;
		for (final VTableColumnConfiguration columnConfiguration : tableControl.getColumnConfigurations()) {
			if (VReadOnlyColumnConfiguration.class.isInstance(columnConfiguration)) {
				final VReadOnlyColumnConfiguration configuration = VReadOnlyColumnConfiguration.class
					.cast(columnConfiguration);
				return configuration.getColumnDomainReferences().contains(domainModelReference);
			}
		}
		return readOnly;
	}
}
