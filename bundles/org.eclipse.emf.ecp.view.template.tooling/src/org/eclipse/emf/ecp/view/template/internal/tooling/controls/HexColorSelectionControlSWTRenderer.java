/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * @author Eugen
 *
 */
public class HexColorSelectionControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createBindings(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Binding[] createBindings(Control control, Setting setting) {
		final Composite composite = Composite.class.cast(control);
		final Control childControl = composite.getChildren()[0];
		final IObservableValue value = SWTObservables.observeBackground(childControl);
		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(setting),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					if (value == null) {
						return null;
					}
					return getString(Color.class.cast(value).getRGB());
				}
			}, new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					final String hexString = (String) value;
					if (hexString == null) {
						return null;
					}
					final int red = Integer.parseInt(hexString.substring(0, 2), 16);
					final int green = Integer.parseInt(hexString.substring(2, 4), 16);
					final int blue = Integer.parseInt(hexString.substring(4, 6), 16);
					final Color color = new Color(Display.getCurrent(), red, green, blue);
					return color;
				}

			});
		final IObservableValue textValue = SWTObservables.observeTooltipText(childControl);
		final Binding textBinding = getDataBindingContext().bindValue(textValue, getModelValue(setting));
		return new Binding[] { binding, textBinding };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Control createSWTControl(final Composite parent, final Setting setting) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		final Label colorExample = new Label(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(false, false).hint(40, SWT.DEFAULT)
			.applyTo(colorExample);
		final Button selectColorBtn = new Button(composite, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(false, false).applyTo(selectColorBtn);
		selectColorBtn.setText(Messages.HexColorSelectionControlSWTRenderer_SelectColorBtn);
		selectColorBtn.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				final ColorDialog cd = new ColorDialog(parent.getShell());
				final RGB rgb = cd.open();

				setValue(getString(rgb), setting);
			}

		});
		return composite;
	}

	private String getString(RGB rgb) {
		final String red = Integer.toHexString(0x100 | rgb.red).substring(1);
		final String green = Integer.toHexString(0x100 | rgb.green).substring(1);
		final String blue = Integer.toHexString(0x100 | rgb.blue).substring(1);
		return red + green + blue;
	}

	private void setValue(String hexColor, Setting setting) {
		final EditingDomain editingDomain = getEditingDomain(setting);
		final Command command = SetCommand.create(editingDomain, setting.getEObject(), setting.getEStructuralFeature(),
			hexColor);
		editingDomain.getCommandStack().execute(command);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		// TODO Auto-generated method stub
		return null;
	}

}
