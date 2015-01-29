/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.makeithappen.view.group.swt.pgroup;

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Renders a VGroup as Eclipse Nebula PGroup widget.
 *
 * @author EclipseSource Munich
 *
 */
public class PGroupRenderer extends ContainerSWTRenderer<VGroup> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public PGroupRenderer(VGroup vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	@Override
	protected Collection<VContainedElement> getChildren() {
		return getVElement().getChildren();
	}

	@Override
	protected String getCustomVariant() {
		return "PGroup"; //$NON-NLS-1$
	}

	@Override
	protected Composite getComposite(Composite parent) {
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		final PGroup group = new PGroup(parent, SWT.SMOOTH);
		if (getVElement().getName() != null) {
			group.setText(getVElement().getName());
		}
		return group;
	}

}
