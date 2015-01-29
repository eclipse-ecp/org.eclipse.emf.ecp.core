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

import java.io.File;
import java.net.MalformedURLException;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Control for selecting a file and saving the path as url.
 *
 * @author Eugen Neufeld
 *
 */
public class URLSelectionControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public URLSelectionControlSWTRenderer(VControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
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
		final Composite composite = Composite.class.cast(control);
		final Control childControl = composite.getChildren()[0];
		final IObservableValue value = SWTObservables.observeText(childControl);
		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(setting));

		final IObservableValue toolTip = SWTObservables.observeTooltipText(childControl);
		final Binding tooltipBinding = getDataBindingContext().bindValue(toolTip, getModelValue(setting));
		return new Binding[] { binding, tooltipBinding };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Control createSWTControl(Composite parent, final Setting setting) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);
		final Label label = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(label);
		final Button selectExternFileButton = new Button(composite, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false)
			.applyTo(selectExternFileButton);
		selectExternFileButton.setText(Messages.URLSelectionControlSWTRenderer_SelectExternalFileBtn);
		selectExternFileButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// show dialog
				final FileDialog fd = new FileDialog(composite.getShell(), SWT.OPEN);
				final String open = fd.open();
				if (open == null) {
					return;
				}
				String selectedURL = null;
				try {
					selectedURL = new File(open).toURI().toURL().toExternalForm();
				} catch (final MalformedURLException ex) {
					Activator.log(ex);
				}
				setValue(selectedURL, setting);
			}

		});

		final Button selectWorkspaceFileButton = new Button(composite, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false)
			.applyTo(selectWorkspaceFileButton);
		selectWorkspaceFileButton.setText(Messages.URLSelectionControlSWTRenderer_SelectWorkspaceFileBtn);
		selectWorkspaceFileButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				final ITreeContentProvider contentProvider = new WorkbenchContentProvider();
				final ILabelProvider labelProvider = new WorkbenchLabelProvider();
				final ElementTreeSelectionDialog etsd = new ElementTreeSelectionDialog(composite.getShell(),
					labelProvider, contentProvider);
				etsd.setInput(ResourcesPlugin.getWorkspace().getRoot());
				final int open = etsd.open();
				if (Window.CANCEL == open) {
					return;
				}
				final IResource resource = (IResource) etsd.getResult()[0];

				final String selectedURL = "platform:/plugin" + resource.getFullPath().toString(); //$NON-NLS-1$

				setValue(selectedURL, setting);
			}

		});
		return composite;
	}

	private void setValue(String selectedURL, Setting setting) {
		final EditingDomain editingDomain = getEditingDomain(setting);
		final Command command = SetCommand.create(editingDomain, setting.getEObject(), setting.getEStructuralFeature(),
			selectedURL);
		editingDomain.getCommandStack().execute(command);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return Messages.URLSelectionControlSWTRenderer_UnsetText;
	}

}
