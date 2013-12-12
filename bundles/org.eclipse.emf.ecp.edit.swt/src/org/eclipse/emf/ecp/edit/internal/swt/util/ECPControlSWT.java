/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Interface describing a control which can be renderer with SWT.
 * 
 * @author Eugen Neufeld
 * 
 */
public interface ECPControlSWT {
	/**
	 * Renders a control on the provided parent and returning a list of {@link RenderingResultRow RenderingResultRows}.
	 * 
	 * @param parent the {@link Composite} to render onto
	 * @return the list of {@link RenderingResultRow RenderingResultRows}
	 */
	List<RenderingResultRow<Control>> createControls(final Composite parent);
}
