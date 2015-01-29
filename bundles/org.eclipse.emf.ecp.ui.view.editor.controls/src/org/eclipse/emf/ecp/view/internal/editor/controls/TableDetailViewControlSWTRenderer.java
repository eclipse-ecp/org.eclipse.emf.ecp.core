/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.NewReferenceAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * @author jfaltermeier
 *
 */
public class TableDetailViewControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public TableDetailViewControlSWTRenderer(VControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private Label label;
	private Label imageLabel;
	private ECPModelElementChangeListener modelElementChangeListener;
	private Composite labelComposite;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createBindings(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Binding[] createBindings(Control control, final Setting setting) {
		final Binding[] bindings = new Binding[3];
		final IObservableValue value = SWTObservables.observeText(label);

		bindings[0] = getDataBindingContext().bindValue(value, getModelValue(setting), new UpdateValueStrategy() {

			@Override
			public Object convert(Object value) {
				return getModelValue(setting).getValue();
			}
		}, new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return getText(value);
			}
		});

		final IObservableValue tooltipValue = SWTObservables.observeTooltipText(label);
		bindings[1] = getDataBindingContext().bindValue(tooltipValue, getModelValue(setting),
			new UpdateValueStrategy() {

				@Override
				public Object convert(Object value) {
					return getModelValue(setting).getValue();
				}
			}, new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getText(value);
				}
			});

		final IObservableValue imageValue = SWTObservables.observeImage(imageLabel);
		bindings[2] = getDataBindingContext().bindValue(imageValue, getModelValue(setting), new UpdateValueStrategy() {

			@Override
			public Object convert(Object value) {
				return getModelValue(setting).getValue();
			}
		}, new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return getImage(value);
			}
		});

		return bindings;
	}

	private Object getText(Object value) {
		final String textName = adapterFactoryItemDelegator.getText(value);
		return textName == null ? "" : textName; //$NON-NLS-1$
	}

	private Object getImage(Object value) {
		if (value == null) {
			return null;
		}
		return Activator.getImage((URL) adapterFactoryItemDelegator.getImage(value));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Control createSWTControl(Composite parent, final Setting setting) {
		/* parent composite */
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).spacing(0, 0).applyTo(composite);

		/* label composite */
		labelComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(labelComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(labelComposite);
		labelComposite.setBackground(composite.getBackground());

		/* image */
		imageLabel = new Label(labelComposite, SWT.NONE);
		imageLabel.setBackground(labelComposite.getBackground());
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.FILL, SWT.BEGINNING).hint(20, 20)
			.applyTo(imageLabel);

		/* text */
		label = new Label(labelComposite, SWT.NONE);
		label.setBackground(labelComposite.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(label);

		/* button bar */
		final Composite buttonComposite = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.FILL, SWT.BEGINNING).applyTo(buttonComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).spacing(2, 0).applyTo(buttonComposite);

		/* delete button */
		final Button deleteButton = createButtonForAction(new DeleteReferenceAction(getEditingDomain(setting), setting,
			getItemPropertyDescriptor(setting), null), buttonComposite);
		deleteButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Command setCommand = SetCommand.create(getEditingDomain(setting), setting.getEObject(),
					setting.getEStructuralFeature(), null);
				getEditingDomain(setting).getCommandStack().execute(setCommand);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		/* create button */
		final Button createButton = createButtonForAction(new NewReferenceAction(getEditingDomain(setting), setting,
			getItemPropertyDescriptor(setting), null), buttonComposite);
		createButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final VView detailView = VViewFactory.eINSTANCE.createView();
				final VTableControl tableControl = (VTableControl) setting.getEObject();
				if (tableControl.getDomainModelReference() == null) {
					MessageDialog.openInformation(Display.getDefault().getActiveShell(),
						"Set Domain Model Reference", "Please set a Domain Model Reference first."); //$NON-NLS-1$ //$NON-NLS-2$
					return;
				}
				final VTableDomainModelReference domainModelReference = (VTableDomainModelReference) tableControl
					.getDomainModelReference();
				EReference ref = null;
				if (domainModelReference.getDomainModelReference() == null) {
					ref = (EReference) domainModelReference.getDomainModelEFeature();
				} else {
					final Iterator<EStructuralFeature> iterator = domainModelReference.getDomainModelReference()
						.getEStructuralFeatureIterator();
					if (iterator.hasNext()) {
						final EStructuralFeature feature = iterator.next();
						if (EReference.class.isInstance(feature)) {
							ref = EReference.class.cast(feature);
						}
					}
				}

				if (ref == null) {
					MessageDialog.openInformation(Display.getDefault().getActiveShell(),
						"Set Domain Model Reference", "Please set a Domain Model Reference first."); //$NON-NLS-1$ //$NON-NLS-2$
					return;
				}
				detailView.setRootEClass(ref.getEReferenceType());
				final Command setCommand = SetCommand.create(getEditingDomain(setting), setting.getEObject(),
					setting.getEStructuralFeature(), detailView);
				getEditingDomain(setting).getCommandStack().execute(setCommand);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		/* init */
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		return composite;
	}

	private Button createButtonForAction(final Action action, final Composite composite) {
		final Button selectButton = new Button(composite, SWT.PUSH);
		selectButton.setImage(Activator.getImage(action));
		selectButton.setEnabled(true);
		selectButton.setToolTipText(action.getToolTipText());
		return selectButton;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		composedAdapterFactory.dispose();
		composedAdapterFactory = null;
		adapterFactoryItemDelegator = null;
		if (modelElementChangeListener != null) {
			modelElementChangeListener.remove();
		}
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return ""; //$NON-NLS-1$
	}

}
