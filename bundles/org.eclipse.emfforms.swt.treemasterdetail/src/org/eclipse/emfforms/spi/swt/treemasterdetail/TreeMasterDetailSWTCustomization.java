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

/**
 * The TreeMasterDetailSWTCustomization is used to influence the way a TreeMasterDetail is rendered and how it behaves.
 *
 * @author Johannes Faltermeier
 *
 */
public interface TreeMasterDetailSWTCustomization
	extends DetailCompositeBuilder, TreeWidthProvider, ViewModelServiceProvider, TreeViewerCustomization {
}
