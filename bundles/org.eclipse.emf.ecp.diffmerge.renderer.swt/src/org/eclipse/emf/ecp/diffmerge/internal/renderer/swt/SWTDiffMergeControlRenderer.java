/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.internal.renderer.swt;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.diffmerge.spi.context.ControlPair;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.diffmerge.swt.DiffDialog;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SWTControlRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * A specific DiffMerge Control Renderer.
 * 
 * @author Eugen Neufeld
 * 
 */
public class SWTDiffMergeControlRenderer extends SWTControlRenderer {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SWTControlRenderer#createLabelControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VControl, org.eclipse.emf.ecp.edit.spi.ECPAbstractControl,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected Control createLabelControl(Composite parent, final VControl vControl, final ECPAbstractControl control,
		final ViewModelContext viewContext)
		throws NoPropertyDescriptorFoundExeption {
		final Composite labelDiffComposite = new Composite(parent, SWT.NONE);
		labelDiffComposite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(labelDiffComposite);
		final Control label = super.createLabelControl(labelDiffComposite, vControl, control, viewContext);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(label);
		final Button diffButton = new Button(labelDiffComposite, SWT.PUSH);
		diffButton.setText(Messages.getString("SWTDiffMergeControlRenderer.DiffButton")); //$NON-NLS-1$
		diffButton.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_compare_button"); //$NON-NLS-1$
		diffButton.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			public void widgetSelected(SelectionEvent e) {
				openDiffDialog((DiffMergeModelContext) viewContext, vControl, control);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		return labelDiffComposite;
	}

	private void openDiffDialog(DiffMergeModelContext diffModelContext, VControl vControl,
		ECPAbstractControl ecpControl) {
		final ControlPair pairWithDiff = diffModelContext.getPairWithDiff(vControl);
		if (pairWithDiff == null) {
			return;
		}
		final Setting setting = ecpControl.getFirstSetting();
		if (setting == null) {
			return;
		}
		final IItemPropertyDescriptor itemPropertyDescriptor = ecpControl.getItemPropertyDescriptor(setting);
		final String label = itemPropertyDescriptor.getDisplayName(setting.getEObject());

		final DiffDialog dialog = new DiffDialog(diffModelContext, label, pairWithDiff.getLeftControl()
			.getDomainModelReference(),
			pairWithDiff.getRightControl().getDomainModelReference(), vControl.getDomainModelReference());

		final Shell shell = new Shell(SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
		shell.setText(String.format(Messages.getString("SWTDiffMergeControlRenderer.ShellTitle1Parameter"), label)); //$NON-NLS-1$
		shell.setLayout(new FillLayout());
		dialog.create(shell);
		shell.pack();
		final Rectangle clientArea = shell.getDisplay().getBounds();
		final Point size = shell.getSize();
		shell.setLocation((clientArea.width - size.x) / 2, (clientArea.height - size.y) / 2);
		shell.open();
	}
}
