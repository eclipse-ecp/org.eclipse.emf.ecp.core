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
