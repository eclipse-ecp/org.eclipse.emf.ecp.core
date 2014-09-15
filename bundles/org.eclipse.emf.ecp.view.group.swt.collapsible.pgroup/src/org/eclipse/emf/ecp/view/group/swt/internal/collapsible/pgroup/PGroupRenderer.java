/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial PGroup Renderer
 * Johannes Faltermeier - adaptions to collapsible group model
 *
 */
package org.eclipse.emf.ecp.view.group.swt.internal.collapsible.pgroup;

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Renderer for a collapsible {@link VGroup} using {@link PGroup} from Nebula.
 *
 * @author jfaltermeier
 *
 */
public class PGroupRenderer extends ContainerSWTRenderer<VGroup> {

	@Override
	protected Collection<VContainedElement> getChildren() {
		return getVElement().getChildren();
	}

	@Override
	protected String getCustomVariant() {
		return "PGroup"; //$NON-NLS-1$
	}

	@Override
	protected Composite getComposite(final Composite parent) {
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		final PGroup group = new PGroup(parent, SWT.SMOOTH);
		if (getVElement().getName() != null) {
			group.setText(getVElement().getName());
		}
		group.addExpandListener(new ExpandListener() {

			@Override
			public void itemCollapsed(ExpandEvent e) {
				parent.layout(true, true);
				getVElement().setCollapsed(true);
			}

			@Override
			public void itemExpanded(ExpandEvent e) {
				parent.layout(true, true);
				getVElement().setCollapsed(false);
			}

		});
		group.setExpanded(!getVElement().isCollapsed());
		return group;
	}

}
