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
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Eugen Neufeld
 *
 */
public abstract class EditableEReferenceLabelControlSWTRenderer extends EReferenceLabelControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public EditableEReferenceLabelControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ControlRootEClassControl2SWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Control createSWTControl(final Composite parent2, final Setting setting) {
		// TODO Auto-generated method stub
		final Composite composite = (Composite) super.createSWTControl(parent2, setting);

		GridLayoutFactory.fillDefaults().numColumns(3).spacing(0, 0).equalWidth(false).applyTo(composite);

		final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(setting);

		String labelText = ""; //$NON-NLS-1$
		String tooltip = ""; //$NON-NLS-1$
		if (itemPropertyDescriptor != null) {
			labelText = itemPropertyDescriptor.getDisplayName(setting.getEObject());
			tooltip = itemPropertyDescriptor.getDescription(setting.getEObject());
		}

		final Button selectClass = new Button(composite, SWT.PUSH);
		selectClass.setText("Link " + labelText); //$NON-NLS-1$
		selectClass.setToolTipText("Link " + tooltip); //$NON-NLS-1$
		selectClass.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				linkValue(composite.getShell());
				composite.layout(true, true);
			}

		});

		final Button unset = new Button(composite, SWT.PUSH);
		unset.setText("Unset"); //$NON-NLS-1$
		unset.setToolTipText("Unset"); //$NON-NLS-1$
		unset.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				setting.unset();
				composite.layout(true, true);
			}

		});

		return composite;
	}

	/**
	 * This method should be overwritten to provide a correct selection mechanism.
	 *
	 * @param shell the Shell
	 */
	protected abstract void linkValue(Shell shell);
}
