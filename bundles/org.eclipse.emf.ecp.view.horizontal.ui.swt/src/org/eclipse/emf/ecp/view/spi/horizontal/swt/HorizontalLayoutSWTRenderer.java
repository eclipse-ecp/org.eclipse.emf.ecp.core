/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.horizontal.swt;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * The SWT Renderer for the {@link VHorizontalLayout}. It renders all elements next to each other.
 * 
 * @author Eugen Neufeld
 * 
 */
public class HorizontalLayoutSWTRenderer extends AbstractSWTRenderer<VHorizontalLayout> {

	private static final String CONTROL_COLUMN_COMPOSITE = "org_eclipse_emf_ecp_ui_layout_horizontal"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VHorizontalLayout vHorizontalLayout,
		ViewModelContext viewContext)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());
		columnComposite.setData(CUSTOM_VARIANT, CONTROL_COLUMN_COMPOSITE);

		columnComposite.setLayout(getLayoutHelper().getColumnLayout(vHorizontalLayout.getChildren().size(), true));

		for (final VContainedElement child : vHorizontalLayout.getChildren()) {

			final Composite column = new Composite(columnComposite, SWT.NONE);
			column.setBackground(parent.getBackground());

			column.setLayoutData(getLayoutHelper().getSpanningLayoutData(1, 1));

			column.setLayout(getLayoutHelper().getColumnLayout(3, false));

			List<RenderingResultRow<Control>> resultRows;

			try {
				resultRows = SWTRendererFactory.INSTANCE.render(column, child, viewContext);
			} catch (final NoPropertyDescriptorFoundExeption e) {
				continue;
			}

			setLayoutDataForResultRows(resultRows);

			// if (!node.isVisible()) {
			// node.show(false);
			// }
		}

		return createResult(columnComposite);
	}

}
