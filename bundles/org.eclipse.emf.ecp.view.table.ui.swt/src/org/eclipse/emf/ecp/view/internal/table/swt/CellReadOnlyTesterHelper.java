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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.ECPCellReadOnlyTester;

/**
 * Helper class which collects the cell testers and allows to iterate over all to check whether a cell should be read
 * only.
 *
 * @author Eugen Neufeld
 *
 */
public final class CellReadOnlyTesterHelper {

	private static CellReadOnlyTesterHelper instance;

	/**
	 * The CellReadOnlyTesterHelper instance.
	 *
	 * @return the CellReadOnlyTesterHelper instance
	 */
	public static CellReadOnlyTesterHelper getInstance() {
		if (instance == null) {
			instance = new CellReadOnlyTesterHelper();
		}
		return instance;
	}

	private final Set<ECPCellReadOnlyTester> testers = new LinkedHashSet<ECPCellReadOnlyTester>();

	private CellReadOnlyTesterHelper() {
		readExtensionPoint();
	}

	private void readExtensionPoint() {

		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.table.ui.swt.cellReadOnly"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final ECPCellReadOnlyTester tester = (ECPCellReadOnlyTester) e.createExecutableExtension("class"); //$NON-NLS-1$
				testers.add(tester);
			} catch (final CoreException e1) {
				Activator.log(e1);
			}
		}
	}

	/**
	 * Register an {@link ECPCellReadOnlyTester} programmatically.
	 *
	 * @param tester the {@link ECPCellReadOnlyTester} to register
	 */
	public void registerCellReadOnlyTester(ECPCellReadOnlyTester tester) {
		testers.add(tester);
	}

	/**
	 * Check all {@link ECPCellReadOnlyTester} for the cell.
	 *
	 * @param vTableControl the {@link VTableControl}
	 * @param setting the {@link Setting} of the cell
	 * @return true if any {@link ECPCellReadOnlyTester} wants the cell to be readonly false otherwise.
	 */
	public boolean isReadOnly(VTableControl vTableControl, Setting setting) {
		boolean result = false;
		for (final ECPCellReadOnlyTester tester : testers) {
			result |= tester.isCellReadOnly(vTableControl, setting);
		}
		return result;
	}
}
