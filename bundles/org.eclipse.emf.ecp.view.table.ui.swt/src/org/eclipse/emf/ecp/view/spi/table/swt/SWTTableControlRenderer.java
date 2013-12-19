/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.table.TableColumnConfiguration;
import org.eclipse.emf.ecp.edit.internal.swt.table.TableControlConfiguration;
import org.eclipse.emf.ecp.view.internal.ui.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumn;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * SWT Renderer for Table Control.
 * 
 * @author Eugen Neufeld
 * 
 */
public class SWTTableControlRenderer extends AbstractSWTRenderer<VTableControl> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#render(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public List<RenderingResultRow<Control>> render(Composite parent, final VTableControl vTableControl,
		final ViewModelContext viewContext)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		return renderModel(parent, vTableControl, viewContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VTableControl vTableControl,
		ViewModelContext viewContext)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final Iterator<Setting> settings = vTableControl.getDomainModelReference().getIterator();
		if (!settings.hasNext()) {
			return null;
		}

		final TableControlConfiguration tcc = new TableControlConfiguration();
		tcc.setAddRemoveDisabled(vTableControl.isAddRemoveDisabled());

		for (final VTableColumn column : vTableControl.getColumns()) {
			tcc.getColumns().add(
				new TableColumnConfiguration(column.isReadOnly(), column.getAttribute()));
		}

		final org.eclipse.emf.ecp.edit.internal.swt.controls.TableControl control = new org.eclipse.emf.ecp.edit.internal.swt.controls.TableControl();
		control.setTableControlConfiguration(tcc);
		control.init(viewContext, vTableControl);

		Label label = null;
		if (control.showLabel()) {
			label = new Label(parent, SWT.NONE);
			label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
			label.setBackground(parent.getBackground());
			String extra = ""; //$NON-NLS-1$
			final Setting setting = control.getFirstSetting();
			final IItemPropertyDescriptor itemPropertyDescriptor = control.getItemPropertyDescriptor(setting);

			if (itemPropertyDescriptor == null) {
				throw new NoPropertyDescriptorFoundExeption(setting.getEObject(), setting.getEStructuralFeature());
			}
			if (((EStructuralFeature) itemPropertyDescriptor.getFeature(null)).getLowerBound() > 0) {
				extra = "*"; //$NON-NLS-1$
			}

			label.setText(itemPropertyDescriptor.getDisplayName(null)
				+ extra);
			label.setToolTipText(itemPropertyDescriptor.getDescription(null));
		}

		final List<RenderingResultRow<Control>> createControls = control.createControls(parent);
		// controlComposite.setBackground(parent.getBackground());

		control.setEditable(!vTableControl.isReadonly());

		Activator.getDefault().ungetECPControlFactory();
		if (label == null) {
			return createControls;
		}
		return createResult(label, createControls.iterator().next().getControls().iterator().next());
	}
}
