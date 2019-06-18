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

/**
 * The TreeMasterDetailSWTCustomization is used to influence the way a TreeMasterDetail is rendered and how it behaves.
 *
 * @author Johannes Faltermeier
 *
 */
public interface TreeMasterDetailSWTCustomization
	extends DetailCompositeBuilder, TreeWidthProvider, ViewModelServiceProvider, TreeViewerCustomization {
}
