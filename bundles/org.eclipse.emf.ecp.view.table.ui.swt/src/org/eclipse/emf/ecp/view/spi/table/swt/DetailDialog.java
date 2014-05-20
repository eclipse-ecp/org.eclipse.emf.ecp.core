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
package org.eclipse.emf.ecp.view.spi.table.swt;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * A dialog allowing to edit an {@link EObject}.
 * 
 * @author Eugen Neufeld
 * 
 */
public class DetailDialog extends Dialog {

	private final EObject selection;
	private Adapter objectChangeAdapter;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private final VTableControl tableControl;

	/**
	 * Creates a dialog allowing to edit an {@link EObject}.
	 * 
	 * @param parentShell the {@link Shell} to use in the dialog
	 * @param selection the {@link EObject} to edit
	 * @param tableControl the {@link VTableControl}
	 */
	public DetailDialog(Shell parentShell, EObject selection, VTableControl tableControl) {
		super(parentShell);
		this.selection = selection;
		this.tableControl = tableControl;
		init();
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void init() {
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		final Point p = super.getInitialSize();
		int height = p.y;
		int width = p.x;
		if (height > 800) {
			height = Math.round(height / 1.5f);
		}
		if (width < 600) {
			width = Math.round(width * 1.5f);
		}
		return new Point(width, height);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		updateTitle();
		final Composite composite = (Composite) super.createDialogArea(parent);
		composite.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		final ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBackground(composite.getBackground());
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);

		final Composite content = new Composite(scrolledComposite, SWT.NONE);
		content.setBackground(composite.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(content);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(content);

		try {
			final VView view = getView();
			ECPSWTViewRenderer.INSTANCE.render(content,
				selection, view);
		} catch (final ECPRendererException ex) {
			ex.printStackTrace();
		}

		scrolledComposite.setContent(content);
		final Point point = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		content.setSize(point);
		scrolledComposite.setMinSize(point);

		objectChangeAdapter = new AdapterImpl() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				updateTitle();
			}

		};
		selection.eAdapters().add(objectChangeAdapter);

		return composite;
	}

	private VView getView() {
		// return ViewProviderHelper.getView(selection);
		final VView vView = VViewFactory.eINSTANCE.createView();
		for (final VDomainModelReference column : VTableDomainModelReference.class.cast(
			tableControl.getDomainModelReference()).getColumnDomainModelReferences()) {
			final VControl vControl = VViewFactory.eINSTANCE.createControl();
			vControl.setDomainModelReference(EcoreUtil.copy(column));
			boolean controlReadOnly = tableControl.isReadonly() || !tableControl.isEnabled();
			controlReadOnly = TableConfigurationHelper.isReadOnly(tableControl, column);

			vControl.setReadonly(controlReadOnly);

			vView.getChildren().add(vControl);
		}
		return vView;
	}

	private void updateTitle() {
		getShell().setText(
			getDefaultText(selection));
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "OK", //$NON-NLS-1$
			true);
	}

	private String getDefaultText(EObject eObject) {
		return adapterFactoryItemDelegator.getText(eObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		if (objectChangeAdapter != null) {
			selection.eAdapters().remove(objectChangeAdapter);
		}
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
		return super.close();
	}

}
