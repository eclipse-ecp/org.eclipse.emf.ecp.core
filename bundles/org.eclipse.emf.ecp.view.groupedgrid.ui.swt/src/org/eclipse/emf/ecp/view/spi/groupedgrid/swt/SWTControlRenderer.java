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
package org.eclipse.emf.ecp.view.spi.groupedgrid.swt;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.internal.ui.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * The AbstractSWTRenderer of an SWTControlRenderer.
 * 
 * @author Eugen Neufeld
 * 
 */
public class SWTControlRenderer extends AbstractSWTRenderer<VControl> {
	/**
	 * The instance of the SWTControlRenderer.
	 */
	public static final SWTControlRenderer INSTANCE = new SWTControlRenderer();
	private static final int IDENT = 10;

	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VControl modelControl,
		ViewModelContext viewContext) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final Setting setting = modelControl.getDomainModelReference().getIterator().next();
		final EClass dataClass = setting.getEStructuralFeature().getEContainingClass();

		if (dataClass == null) {
			return null;
		}

		final ECPControlFactory controlFactory = Activator.getDefault().getECPControlFactory();

		if (controlFactory == null) {
			Activator.getDefault().ungetECPControlFactory();
			return null;
		}

		final SWTControl control = controlFactory.createControl(SWTControl.class,
			modelControl.getDomainModelReference());
		if (control == null) {
			return null;
		}
		control.init(viewContext, modelControl);

		if (control != null) {
			int numControl = 2;
			Label label = null;
			if (modelControl.getLabelAlignment() == LabelAlignment.LEFT) {
				final IItemPropertyDescriptor itemPropertyDescriptor = control.getItemPropertyDescriptor(setting);

				if (itemPropertyDescriptor == null) {
					throw new NoPropertyDescriptorFoundExeption(setting.getEObject(), setting.getEStructuralFeature());
				}

				numControl = 1;
				label = new Label(parent, SWT.NONE);
				label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label");
				label.setBackground(parent.getBackground());
				String extra = "";
				if (((EStructuralFeature) itemPropertyDescriptor.getFeature(null)).getLowerBound() > 0) {
					extra = "*";
				}
				label.setText(itemPropertyDescriptor.getDisplayName(null)
					+ extra);
				label.setToolTipText(itemPropertyDescriptor.getDescription(null));
				GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(label);

				GridDataFactory.createFrom((GridData) label.getLayoutData()).indent(IDENT, 0).applyTo(label);
			}

			final Composite controlComposite = control.createControl(parent);
			controlComposite.setEnabled(!modelControl.isReadonly());
			controlComposite.setBackground(parent.getBackground());

			GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.FILL, SWT.CENTER).span(numControl, 1)
				.applyTo(controlComposite);

			if (label == null) {
				return createResult(controlComposite);
			}
			return createResult(label, controlComposite);
		}

		Activator.getDefault().ungetECPControlFactory();

		return null;
	}
}
