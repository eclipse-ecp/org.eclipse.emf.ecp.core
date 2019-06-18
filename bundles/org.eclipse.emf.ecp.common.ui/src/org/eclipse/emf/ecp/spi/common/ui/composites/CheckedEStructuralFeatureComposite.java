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
package org.eclipse.emf.ecp.spi.common.ui.composites;

import org.eclipse.emf.ecp.spi.common.ui.CompositeProvider;
import org.eclipse.jface.viewers.TableViewer;

/**
 * @author jfaltermeier
 *
 */
public interface CheckedEStructuralFeatureComposite extends CompositeProvider {

	/**
	 * Returns the {@link TableViewer}.
	 *
	 * @return the viewer
	 */
	TableViewer getViewer();

	/**
	 * Returns the selected objects.
	 *
	 * @return the selection
	 */
	Object[] getSelection();

}
