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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This class defines a Control which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature
 * EStructuralFeature}s which have a enum
 * value.
 *
 * @author Eugen Neufeld
 *
 */
public class EEnumControl extends SingleControl {

	private ComboViewer combo;

	@Override
	protected void fillControlComposite(Composite composite) {
		final Setting firstSetting = getFirstSetting();
		final IItemLabelProvider labelProvider = getItemPropertyDescriptor(firstSetting).getLabelProvider(null);

		combo = new ComboViewer(composite);
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				return labelProvider.getText(element);
			}

		});
		combo.setInput(firstSetting.getEStructuralFeature().getEType().getInstanceClass().getEnumConstants());
		combo.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_enum"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEditable(boolean isEditable) {
		combo.getControl().setEnabled(isEditable);
	}

	@Override
	public Binding bindValue() {
		final IObservableValue target = ViewersObservables.observeSingleSelection(combo);
		final Binding bindValue = getDataBindingContext().bindValue(target, getModelValue());
		getDataBindingContext().bindValue(SWTObservables.observeTooltipText(combo.getControl()), getModelValue());
		return bindValue;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.EEnumControl_NoValueSetClickToSetValue);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(), DepricatedControlMessageKeys.EEnumControl_UnsetValue);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getControlForTooltip()
	 */
	@Override
	protected Control[] getControlsForTooltip() {
		// return new Control[] { combo.getControl() };
		return new Control[0];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#updateValidationColor(org.eclipse.swt.graphics.Color)
	 */
	@Override
	protected void updateValidationColor(Color color) {
		combo.getControl().setBackground(color);
	}

}
