/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * mat - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import org.eclipse.emfforms.spi.swt.table.action.Action;
import org.eclipse.emfforms.spi.swt.table.action.ActionControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Action icon default implementation for table actions.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public class TableActionIconButton implements ActionControlCreator<Button> {

	private final String tooltipText;
	private final Image icon;

	/**
	 * The constructor.
	 *
	 * @param tooltipText the tooltip text to use
	 * @param icon the icon to use
	 */
	public TableActionIconButton(String tooltipText, Image icon) {
		super();
		this.tooltipText = tooltipText;
		this.icon = icon;
	}

	@Override
	public Button createControl(Composite parent, Action action) {
		final Button button = createIconButton(parent, action);
		configureIconButton(button);
		return button;
	}

	private Button createIconButton(Composite parent, final Action action) {
		final Button button = new Button(parent, SWT.None);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (action.canExecute()) {
					action.execute();
				}
			}
		});
		return button;
	}

	private void configureIconButton(Button button) {
		button.setImage(icon);
		button.setToolTipText(tooltipText);
	}

}
