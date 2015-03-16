/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.group.swt;

import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.swt.widgets.Group;

/**
 * Databinding Property for SWT Groups.
 *
 * @author Eugen Neufeld
 * @generated
 */
public class GroupTextProperty extends WidgetValueProperty {
	// better use WidgetStringValueProperty
	public GroupTextProperty() {
		super();
	}

	GroupTextProperty(int event) {
		super(event);
	}

	GroupTextProperty(int[] events) {
		super(events);
	}

	GroupTextProperty(int[] events, int[] staleEvents) {
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
		return ((Group) source).getText();
	}

	void doSetStringValue(Object source, String value) {
		((Group) source).setText(value == null ? "" : value); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return "Group.text <String>"; //$NON-NLS-1$
	}
}