/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 */
package org.eclipse.emf.ecp.view.dynamictree.ui.swt;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentTree;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;

/**
 * SWT renderer for {@link DynamicContainmentTree}s.
 *
 * @author emueller
 */
public class SWTDynamicContainmentTreeRenderer extends ContainerSWTRenderer<DynamicContainmentTree> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public SWTDynamicContainmentTreeRenderer(DynamicContainmentTree vElement, ViewModelContext viewContext,
		SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getCustomVariant()
	 */
	@Override
	protected String getCustomVariant() {
		// TODO Auto-generated method stub
		return "testTree";
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getChildren()
	 */
	@Override
	protected Collection<VContainedElement> getChildren() {
		// TODO Auto-generated method stub
		return Collections.singleton(getVElement().getComposite());
	}

}
