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
package org.eclipse.emf.ecp.diffmerge.internal.renderer.swt;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.diffmerge.swt.DiffDialogHelper;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.AbstractAdditionalSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A diff merge addition renderer.
 *
 * @author Eugen Neufeld
 *
 */
public class SWTDiffMergeAddition extends AbstractAdditionalSWTRenderer<VControl> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public SWTDiffMergeAddition(VControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		final SWTGridDescription addGridDescription = gridDescription.copy();
		addGridDescription.getGrid().add(new SWTGridCell(0, addGridDescription.getColumns(), this));
		addGridDescription.setColumns(addGridDescription.getColumns() + 1);
		return addGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		// if (gridDescription.getColumns() == cell.getColumn() + 1) {
		if (cell.getRenderer() == this) {
			return createDiffButton(parent);

		}
		return null;
	}

	/**
	 * @param parent
	 */
	private Button createDiffButton(Composite parent) {
		final Button diffButton = new Button(parent, SWT.PUSH);
		diffButton.setText(Messages.getString("SWTDiffMergeControlRenderer.DiffButton")); //$NON-NLS-1$
		diffButton.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_compare_button"); //$NON-NLS-1$
		// diffButton.setLayoutData(SWTRenderingHelper.INSTANCE.getLayoutHelper().getLeftColumnLayoutData());
		diffButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				openDiffDialog((DiffMergeModelContext) getViewModelContext(), getVElement());
			}

		});
		for (final VAttachment attachment : getVElement().getAttachments()) {
			if (VDiffAttachment.class.isInstance(attachment)) {
				final Adapter adapter = new AdapterImpl() {

					/**
					 * {@inheritDoc}
					 *
					 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
					 */
					@Override
					public void notifyChanged(Notification msg) {
						super.notifyChanged(msg);
						if (msg.getFeature() == VDiffmergePackage.eINSTANCE.getDiffAttachment_MergedDiffs()) {
							updateButton(diffButton, (VDiffAttachment) attachment);
						}
					}

				};
				attachment.eAdapters().add(adapter);
				diffButton.addDisposeListener(new DisposeListener() {

					@Override
					public void widgetDisposed(DisposeEvent event) {
						attachment.eAdapters().remove(adapter);
					}
				});
				updateButton(diffButton, (VDiffAttachment) attachment);
				break;
			}
		}
		return diffButton;
	}

	private void updateButton(Button diffButton, VDiffAttachment attachment) {
		if (attachment.getMergedDiffs() == 0) {
			diffButton.setImage(Activator.getImage("icons/lightning.png")); //$NON-NLS-1$
		} else {
			diffButton.setImage(Activator.getImage("icons/accept.png")); //$NON-NLS-1$
		}
	}

	private void openDiffDialog(DiffMergeModelContext diffModelContext, VControl vControl) {

		final Setting setting = vControl.getDomainModelReference().getIterator().next();
		if (setting == null) {
			return;
		}
		final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(setting);
		final String label = itemPropertyDescriptor.getDisplayName(setting.getEObject());

		DiffDialogHelper.showDialog(diffModelContext, vControl, label);

	}

	private IItemPropertyDescriptor getItemPropertyDescriptor(Setting setting) {
		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);

		final IItemPropertyDescriptor descriptor = adapterFactoryItemDelegator.getPropertyDescriptor(
			setting.getEObject(),
			setting.getEStructuralFeature());
		composedAdapterFactory.dispose();
		return descriptor;

	}

}
