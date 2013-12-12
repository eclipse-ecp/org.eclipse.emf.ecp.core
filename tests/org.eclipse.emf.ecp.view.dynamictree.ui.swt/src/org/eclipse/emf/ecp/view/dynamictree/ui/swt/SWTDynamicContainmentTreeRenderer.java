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

import java.util.List;

import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentTree;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * SWT renderer for {@link DynamicContainmentTree}s.
 * 
 * @author emueller
 */
public class SWTDynamicContainmentTreeRenderer extends AbstractSWTRenderer<DynamicContainmentTree> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent,
		DynamicContainmentTree dynamicContainmentTree,
		ViewModelContext viewModelContext)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());

		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(columnComposite);

		List<RenderingResultRow<Control>> resultRows;
		try {
			resultRows = SWTRendererFactory.INSTANCE.render(columnComposite, dynamicContainmentTree.getComposite(),
				viewModelContext);
		} catch (final NoPropertyDescriptorFoundExeption e) {
			return null;
		}
		// TODO when does this case apply?
		if (resultRows == null) {
			return null;
		}

		setLayoutDataForResultRows(resultRows);

		return createResult(columnComposite);
	}

}
