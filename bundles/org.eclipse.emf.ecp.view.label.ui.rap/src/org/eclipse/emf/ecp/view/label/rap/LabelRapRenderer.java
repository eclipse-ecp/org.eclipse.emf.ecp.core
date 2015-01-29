/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 */
package org.eclipse.emf.ecp.view.label.rap;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.label.swt.LabelSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.swt.widgets.Label;

/**
 * @author Alexandra Buzila
 *
 */
public class LabelRapRenderer extends LabelSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public LabelRapRenderer(VLabel vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.view.spi.label.swt.LabelSWTRenderer#applyStyle(org.eclipse.swt.widgets.Label)
	 */
	@Override
	protected void applyStyle(Label label) {
		label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_ui_" + getVElement().getStyle()); //$NON-NLS-1$
	}

}
