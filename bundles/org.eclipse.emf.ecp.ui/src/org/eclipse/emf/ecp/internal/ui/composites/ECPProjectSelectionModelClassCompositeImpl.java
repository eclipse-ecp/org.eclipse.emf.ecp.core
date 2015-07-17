/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.composites;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectModelClassCompositeImpl;
import org.eclipse.emf.ecp.spi.core.InternalProject;

/**
 * @author Eugen
 *
 */
public class ECPProjectSelectionModelClassCompositeImpl extends SelectModelClassCompositeImpl {

	/**
	 * Constructor that delegates to the
	 * {@link org.eclipse.emf.ecp.spi.common.ui.composites.AbstractEClassTreeSelectionComposite#AbstractEClassTreeSelectionComposite(java.util.Collection, java.util.Collection, java.util.Collection)}
	 * by reading the data from the project.
	 *
	 * @param project the {@link ECPProject} to read the data from
	 */
	public ECPProjectSelectionModelClassCompositeImpl(ECPProject project) {
		super(((InternalProject) project).getUnsupportedEPackages(), ((InternalProject) project).getVisiblePackages(),
			((InternalProject) project).getVisibleEClasses());
	}

}
