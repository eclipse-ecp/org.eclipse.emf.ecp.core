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
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPControlSWT;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public List<RenderingResultRow<Control>> render(Composite parent, final VControl vControl,
		final ViewModelContext viewContext)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		return renderModel(parent, vControl, viewContext);
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(final Composite parent, final VControl vControl,
		final ViewModelContext viewContext) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final ECPControlFactory controlFactory = Activator.getDefault().getECPControlFactory();

		if (controlFactory == null) {
			Activator.getDefault().ungetECPControlFactory();
			return null;
		}

		final ECPAbstractControl control = controlFactory.createControl(ECPAbstractControl.class,
			vControl.getDomainModelReference());

		if (control != null) {
			control.init(viewContext, vControl);
			final Control label = createLabelControl(parent, vControl, control, viewContext);

			final List<RenderingResultRow<org.eclipse.swt.widgets.Control>> createControls = ((ECPControlSWT) control)
				.createControls(parent);
			if (createControls == null) {
				return null;
			}
			// TODO discuss whether to call from here or checked by control
			control.setEditable(!vControl.isReadonly());
			if (!vControl.isReadonly()) {
				control.setEditable(vControl.isEnabled());
			}

			final List<RenderingResultRow<org.eclipse.swt.widgets.Control>> result = new ArrayList<RenderingResultRow<org.eclipse.swt.widgets.Control>>();

			if (label != null) {
				final Set<Control> mainControls = createControls.get(0).getControls();
				final Control[] controls = new Control[mainControls.size() + 1];
				controls[0] = label;
				int i = 1;
				for (final Control mainControl : mainControls) {
					controls[i++] = mainControl;
				}
				result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
					.createRenderingResultRow(controls));
			}
			else {
				result.addAll(createControls);
			}

			final ModelChangeListener listener = new ModelChangeListener() {

				public void notifyRemove(Notifier notifier) {
					// TODO Auto-generated method stub

				}

				public void notifyChange(ModelChangeNotification notification) {
					if (notification.getNotifier() != vControl) {
						return;
					}
					if (notification.getStructuralFeature() == VViewPackage.eINSTANCE.getElement_Visible()) {
						applyVisible(vControl, result);
					}
				}

				public void notifyAdd(Notifier notifier) {
					// TODO Auto-generated method stub

				}
			};
			viewContext.registerViewChangeListener(listener);
			parent.addDisposeListener(new DisposeListener() {

				private static final long serialVersionUID = 1L;

				public void widgetDisposed(DisposeEvent e) {
					viewContext.unregisterViewChangeListener(listener);
				}
			});
			applyVisible(vControl, result);

			return result;

		}

		Activator.getDefault().ungetECPControlFactory();

		return null;
	}

	/**
	 * Create the {@link Control} displaying the label of the current {@link VControl}.
	 * 
	 * @param parent the {@link Composite} to render onto
	 * @param vControl the {@link VControl} to create the label for
	 * @param control the {@link ECPAbstractControl} created for the vControl
	 * @param viewContext the {@link ViewModelContext} used to create the current control
	 * @return the created {@link Control} or null
	 * @throws NoPropertyDescriptorFoundExeption thrown if the {@link org.eclipse.emf.ecore.EStructuralFeature
	 *             EStructuralFeature} of the {@link VControl} doesn't have a registered {@link IItemPropertyDescriptor}
	 */
	protected Control createLabelControl(final Composite parent, final VControl vControl,
		final ECPAbstractControl control, ViewModelContext viewContext)
		throws NoPropertyDescriptorFoundExeption {
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
		return label;
	}
}
