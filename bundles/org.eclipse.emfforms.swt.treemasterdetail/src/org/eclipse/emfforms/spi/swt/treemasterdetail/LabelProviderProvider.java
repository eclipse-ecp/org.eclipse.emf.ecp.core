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
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.jface.viewers.IBaseLabelProvider;

/**
 * Interface to influence the {@link IBaseLabelProvider label provider} which is added to the tree master detail.
 *
 * @author Johannes Faltermeier
 *
 */
public interface LabelProviderProvider extends Disposeable {

	/**
	 * Creates the label provider added to the treeviewer.
	 *
	 * @return the label provider
	 */
	IBaseLabelProvider getLabelProvider();

}