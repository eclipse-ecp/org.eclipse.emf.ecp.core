/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.swt.widgets.Composite;

/**
 * Interface to influence the creation of the detail composite of the tree master detail.
 * 
 * @author Johannes Faltermeier
 *
 */
public interface DetailCompositeBuilder {

	/**
	 * Called to create the detail composite on the right.
	 *
	 * @param parent the parent composite
	 * @return the detail composite
	 */
	Composite createDetailComposite(Composite parent);

}