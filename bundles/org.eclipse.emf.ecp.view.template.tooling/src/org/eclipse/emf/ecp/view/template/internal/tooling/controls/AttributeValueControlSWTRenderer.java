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
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.ecp.view.template.internal.tooling.util.ValueSelectionHelper;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Renderer for the
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#setAttributeValue(Object)
 * VTViewModelElementSelector#setAttributeValue(Object)}.
 *
 * @author Eugen Neufeld
 *
 */
public class AttributeValueControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public AttributeValueControlSWTRenderer(VControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createBindings(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Binding[] createBindings(Control control, Setting setting) {
		final Label label = (Label) Composite.class.cast(control).getChildren()[0];
		final Binding binding = getDataBindingContext().bindValue(SWTObservables.observeText(label),
			getModelValue(setting), new UpdateValueStrategy(), new UpdateValueStrategy() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
				 */
				@Override
				public Object convert(Object value) {
					return super.convert(value).toString();
				}

			});
		return new Binding[] { binding };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Control createSWTControl(final Composite parent, Setting setting) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		final Label label = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(label);

		final Button button = new Button(composite, SWT.PUSH);
		button.setText(Messages.AttributeValueControlSWTRenderer_SelectAttributeValue);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).applyTo(button);

		button.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				final Setting setting = getVElement().getDomainModelReference().getIterator().next();
				final Object result = ValueSelectionHelper.getValueSelectionDialog(parent.getShell(),
					VTViewModelElementSelector.class.cast(setting.getEObject()).getAttribute());

				final EditingDomain editingDomain = getEditingDomain(setting);
				final Command command = SetCommand.create(editingDomain, setting.getEObject(),
					setting.getEStructuralFeature(),
					result);
				editingDomain.getCommandStack().execute(command);
			}
		});

		return composite;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return Messages.AttributeValueControlSWTRenderer_UnsetAttributeMessage;
	}

}
