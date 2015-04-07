/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Renderer for {@link org.eclipse.emf.ecp.view.spi.section.model.VSection VSection} without child items.
 *
 * @author jfaltermeier
 *
 */
public class SectionLeafSWTRenderer extends AbstractSectionSWTRenderer {

	private SWTGridDescription rendererGridDescription;

	@Override
	public SWTGridDescription getGridDescription(
		SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE
				.createSimpleGrid(1, 4, this);
		}
		return rendererGridDescription;
	}

	@Override
	protected Control createFirstColumn(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1)
			.extendedMargins(computeLeftMargin(), 0, 0, 0)
			.applyTo(composite);

		final Label label = new Label(composite, SWT.NONE);
		final String text = getVElement().getName() == null ? "" //$NON-NLS-1$
			: getVElement().getName();
		label.setText(text);

		return composite;
	}

	private int computeLeftMargin() {
		int numberOfParents = 0;
		EObject current = getVElement().eContainer();
		while (!VSectionedArea.class.isInstance(current)) {
			numberOfParents++;
			current = current.eContainer();
		}
		return (numberOfParents + 1) * 8;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.section.swt.AbstractSectionSWTRenderer#initCollapseState()
	 */
	@Override
	protected void initCollapseState() {
		// no children -> empty
	}

}
