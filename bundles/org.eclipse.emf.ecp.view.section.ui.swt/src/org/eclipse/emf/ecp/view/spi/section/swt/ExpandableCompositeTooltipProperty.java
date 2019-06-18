/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

/**
 * Databinding Property for {@link ExpandableComposite}.
 *
 * @author Edgar Mueller
 * @generated
 * @since 1.13
 */
public class ExpandableCompositeTooltipProperty extends WidgetValueProperty {

	public ExpandableCompositeTooltipProperty() {
		super();
	}

	ExpandableCompositeTooltipProperty(int event) {
		super(event);
	}

	ExpandableCompositeTooltipProperty(int[] events) {
		super(events);
	}

	ExpandableCompositeTooltipProperty(int[] events, int[] staleEvents) {
		super(events, staleEvents);
	}

	@Override
	public Object getValueType() {
		return String.class;
	}

	@Override
	protected Object doGetValue(Object source) {
		return doGetStringValue(source);
	}

	@Override
	protected void doSetValue(Object source, Object value) {
		doSetStringValue(source, (String) value);
	}

	// end of copied code

	String doGetStringValue(Object source) {
		return ((ExpandableComposite) source).getToolTipText();
	}

	void doSetStringValue(Object source, String value) {
		((ExpandableComposite) source).setToolTipText(value == null ? "" : value); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return "Section.text <String>"; //$NON-NLS-1$
	}
}
