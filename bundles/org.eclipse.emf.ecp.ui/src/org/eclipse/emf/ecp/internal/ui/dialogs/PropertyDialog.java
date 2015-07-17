/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.dialogs;

import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog to edit one property.
 *
 * @author Eike Stepper
 *
 */
public class PropertyDialog extends TitleAreaDialog {
	private final boolean keyEditable;

	private String key;

	private String value;

	private Text keyText;

	private Text valueText;

	/**
	 * Constructor to edit an existing property.
	 *
	 * @param parentShell the paren {@link Shell}
	 * @param keyEditable if the property is editable
	 * @param key the key of the property
	 * @param value the current value of the property
	 */
	public PropertyDialog(Shell parentShell, boolean keyEditable, String key, String value) {
		super(parentShell);
		this.keyEditable = keyEditable;
		this.key = key;
		this.value = value;
	}

	/**
	 * Constructor for a new property.
	 *
	 * @param parentShell the parent {@link Shell}
	 */
	public PropertyDialog(Shell parentShell) {
		this(parentShell, true, null, null);
	}

	/**
	 *
	 * @return the key of the currently edited property
	 */
	public final String getKey() {
		return key;
	}

	/**
	 *
	 * @return the value of the currently edited property
	 */
	public final String getValue() {
		return value;
	}

	/**
	 *
	 * @return whether the property is editable
	 */
	public final boolean isKeyEditable() {
		return keyEditable;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage(Messages.PropertyDialog_Message);
		setTitle(Messages.PropertyDialog_Title);
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label lblKey = new Label(container, SWT.NONE);
		lblKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblKey.setText(Messages.PropertyDialog_Key);

		keyText = new Text(container, SWT.BORDER);
		keyText.setText(key == null ? "" : key); //$NON-NLS-1$
		keyText.setEditable(keyEditable);
		keyText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		keyText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				key = keyText.getText();
			}
		});

		final Label lblValue = new Label(container, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblValue.setText(Messages.PropertyDialog_Value);

		valueText = new Text(container, SWT.BORDER);
		valueText.setText(value == null ? "" : value); //$NON-NLS-1$
		valueText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		valueText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				value = valueText.getText();
			}
		});

		return area;
	}

	@Override
	protected void cancelPressed() {
		key = null;
		value = null;
		super.cancelPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 228);
	}
}
