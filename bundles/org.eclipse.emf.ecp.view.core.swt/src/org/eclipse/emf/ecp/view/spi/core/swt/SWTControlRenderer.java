/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPControlSWT;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.internal.ui.view.Activator;
import org.eclipse.emf.ecp.internal.ui.view.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.internal.ui.view.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.internal.ui.view.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Renderer for {@link org.eclipse.swt.widgets.Control Controls}.
 * 
 * @author Eugen Neufeld
 * 
 */
public class SWTControlRenderer extends AbstractSWTRenderer<VControl> {
	/**
	 * Instance field to access this renderer as a singleton.
	 */
	public static final SWTControlRenderer INSTANCE = new SWTControlRenderer();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#render(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public List<RenderingResultRow<Control>> render(Composite parent, final VControl vControl,
		final ViewModelContext viewContext)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		return renderModel(parent, vControl, viewContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VControl vControl,
		ViewModelContext viewContext) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final ECPControlFactory controlFactory = Activator.getDefault().getECPControlFactory();

		if (controlFactory == null) {
			Activator.getDefault().ungetECPControlFactory();
			return null;
		}

		final ECPAbstractControl control = controlFactory.createControl(ECPAbstractControl.class,
			vControl.getDomainModelReference());

		if (control != null) {
			control.init(viewContext, vControl);
			Label label = null;
			labelRender: if (vControl.getLabelAlignment() == LabelAlignment.LEFT) {
				final Setting setting = control.getFirstSetting();
				if (setting == null) {
					break labelRender;
				}
				final IItemPropertyDescriptor itemPropertyDescriptor = control.getItemPropertyDescriptor(setting);

				if (itemPropertyDescriptor == null) {
					throw new NoPropertyDescriptorFoundExeption(setting.getEObject(), setting.getEStructuralFeature());
				}

				label = new Label(parent, SWT.NONE);
				label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
				label.setBackground(parent.getBackground());
				String extra = ""; //$NON-NLS-1$
				if (setting.getEStructuralFeature().getLowerBound() > 0) {
					extra = "*"; //$NON-NLS-1$
				}
				final String labelText = itemPropertyDescriptor.getDisplayName(setting.getEObject());
				if (labelText != null && labelText.trim().length() != 0) {
					label.setText(labelText + extra);
					label.setToolTipText(itemPropertyDescriptor.getDescription(setting.getEObject()));
				}

			}

			final List<RenderingResultRow<org.eclipse.swt.widgets.Control>> createControls = ((ECPControlSWT) control)
				.createControls(parent);
			if (createControls == null) {
				return null;
			}
			control.setEditable(!vControl.isReadonly());
			if (!vControl.isReadonly()) {
				control.setEditable(vControl.isEnabled());
			}

			List<RenderingResultRow<org.eclipse.swt.widgets.Control>> result = new ArrayList<RenderingResultRow<org.eclipse.swt.widgets.Control>>();
			final Control next = createControls.iterator().next().getControls().iterator().next();
			if (label != null) {
				result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
					.createRenderingResultRow(label, next));
			}
			else {
				result = createControls;
			}
			return result;

		}

		Activator.getDefault().ungetECPControlFactory();

		return null;
	}

}
