/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.group.swt;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
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
 * Renders a SWT group.
 * 
 */
public class SWTGroupRenderer extends AbstractSWTRenderer<VGroup> {
	/**
	 * Singleton instance of the SWT Group renderer.
	 */
	public static final SWTGroupRenderer INSTANCE = new SWTGroupRenderer();
	private static final Object CONTROL_GROUP = "org_eclipse_emf_ecp_ui_control_group"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VGroup vGroup,
		ViewModelContext viewContext)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final org.eclipse.swt.widgets.Group group = new org.eclipse.swt.widgets.Group(parent, SWT.TITLE);
		group.setData(CUSTOM_VARIANT, CONTROL_GROUP);
		if (vGroup.getName() != null) {
			group.setText(vGroup.getName());
		}
		group.setBackground(parent.getBackground());
		group.setLayout(getLayoutHelper().getColumnLayout(2, false));

		for (final VContainedElement child : vGroup.getChildren()) {

			List<RenderingResultRow<Control>> resultRows;
			try {
				resultRows = SWTRendererFactory.INSTANCE.render(group, child, viewContext);
			} catch (final NoPropertyDescriptorFoundExeption e) {
				continue;
			}
			// TODO when does this case apply?
			if (resultRows == null) {
				continue;
			}

			setLayoutDataForResultRows(resultRows);
		}

		return createResult(group);
	}
}
