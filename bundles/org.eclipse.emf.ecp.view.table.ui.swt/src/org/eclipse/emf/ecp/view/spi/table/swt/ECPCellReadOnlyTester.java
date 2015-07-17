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
package org.eclipse.emf.ecp.view.spi.table.swt;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;

/**
 * Interface used in the cellReadOnly extensionpoint.
 * By providing such a tester, the user can set individual cells to read only.
 *
 * @author Eugen Neufeld
 *
 */
public interface ECPCellReadOnlyTester {

	/**
	 * The test method, which defines whether a cell should be readonly or not.
	 *
	 * @param vTableControl the {@link VTableControl} the view model element of the table
	 * @param setting the {@link Setting} of the cell
	 * @return true if the cell should be read only, false otherwise
	 */
	boolean isCellReadOnly(VTableControl vTableControl, Setting setting);
}
