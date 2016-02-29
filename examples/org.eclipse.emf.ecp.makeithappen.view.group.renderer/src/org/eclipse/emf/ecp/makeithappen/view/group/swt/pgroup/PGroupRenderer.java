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

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.EMFFormsSWTLayoutUtil;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Renders a VGroup as Eclipse Nebula PGroup widget.
 *
 * @author EclipseSource Munich
 *
 */
public class PGroupRenderer extends ContainerSWTRenderer<VGroup> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 */
	@Inject
	public PGroupRenderer(VGroup vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory, EMFFormsDatabinding emfFormsDatabinding) {
		super(vElement, viewContext, reportService, factory, emfFormsDatabinding);
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
		if (getVElement().getLabel() != null) {
			group.setText(getVElement().getLabel());
		}
		group.addExpandListener(new ExpandListener() {

			@Override
			public void itemCollapsed(ExpandEvent e) {
				EMFFormsSWTLayoutUtil.adjustParentSize(group);
			}

			@Override
			public void itemExpanded(ExpandEvent e) {
				EMFFormsSWTLayoutUtil.adjustParentSize(group);
			}

		});
		return group;
	}

}
