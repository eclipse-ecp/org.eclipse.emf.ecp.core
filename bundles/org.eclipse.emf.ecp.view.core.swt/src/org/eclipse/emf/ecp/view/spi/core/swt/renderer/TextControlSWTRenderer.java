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
package org.eclipse.emf.ecp.view.spi.core.swt.renderer;

import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Renders texts.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public class TextControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public TextControlSWTRenderer(VControl vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {
		final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();
		final TargetToModelUpdateStrategy targetToModelUpdateStrategy = new TargetToModelUpdateStrategy(
			structuralFeature.isUnsettable());
		final ModelToTargetUpdateStrategy modelToTargetUpdateStrategy = new ModelToTargetUpdateStrategy(false);
		final Binding binding = bindValue(control, getModelValue(), getDataBindingContext(),
			targetToModelUpdateStrategy,
			modelToTargetUpdateStrategy);
		final Binding tooltipBinding = createTooltipBinding(control, getModelValue(), getDataBindingContext(),
			targetToModelUpdateStrategy,
			new ModelToTargetUpdateStrategy(true));
		return new Binding[] { binding, tooltipBinding };
	}

	@Override
	protected Control createSWTControl(Composite parent) {
		final Text text = new Text(parent, getTextWidgetStyle());
		text.setData(CUSTOM_VARIANT, getTextVariantID());
		text.setMessage(getTextMessage());
		return text;
	}

	/**
	 * Returns the text which should be set as the message text on the Text field.
	 *
	 * @return the string to show as the message
	 */
	protected String getTextMessage() {
		return getEMFFormsLabelProvider()
			.getDisplayName(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
	}

	EMFFormsLabelProvider getEMFFormsLabelProvider() {
		return Activator.getDefault().getEMFFormsLabelProvider();
	}

	/**
	 * Creates a focus out binding for this control.
	 *
	 * @param text the {@link Text} to bind
	 * @param modelValue the {@link IObservableValue} to bind
	 * @param dataBindingContext the {@link DataBindingContext} to use
	 * @param targetToModel the {@link UpdateValueStrategy} from target to Model
	 * @param modelToTarget the {@link UpdateValueStrategy} from model to target
	 * @return the created {@link Binding}
	 */
	protected Binding bindValue(Control text, IObservableValue modelValue, DataBindingContext dataBindingContext,
		UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
		final IObservableValue value = SWTObservables.observeText(text, SWT.FocusOut);
		final Binding binding = dataBindingContext.bindValue(value, modelValue, targetToModel, modelToTarget);
		return binding;
	}

	/**
	 * Creates a tooltip binding for this control.
	 *
	 * @param text the {@link Text} to bind
	 * @param modelValue the {@link IObservableValue} to bind
	 * @param dataBindingContext the {@link DataBindingContext} to use
	 * @param targetToModel the {@link UpdateValueStrategy} from target to Model
	 * @param modelToTarget the {@link UpdateValueStrategy} from model to target
	 * @return the created {@link Binding}
	 */
	protected Binding createTooltipBinding(Control text, IObservableValue modelValue,
		DataBindingContext dataBindingContext, UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
		final IObservableValue toolTip = SWTObservables.observeTooltipText(text);
		return dataBindingContext.bindValue(toolTip, modelValue, targetToModel, modelToTarget);
	}

	/**
	 * The style to apply to the text widget. This can be changed by the concrete classes.
	 *
	 * @return the style to apply
	 */
	protected int getTextWidgetStyle() {
		int textStyle = SWT.SINGLE | SWT.BORDER;
		final EMFFormsEditSupport editSupport = getEMFFormsEditSupport();
		if (editSupport.isMultiLine(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel())) {
			textStyle = SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		}
		textStyle |= getAlignment();
		return textStyle;
	}

	/**
	 * Package visible method, to allow an easy replacement.
	 *
	 * @return The EMFFormsEditSupport
	 */
	EMFFormsEditSupport getEMFFormsEditSupport() {
		return Activator.getDefault().getEMFFormsEditSupport();
	}

	private int getAlignment() {
		final VTViewTemplateProvider vtViewTemplateProvider = Activator.getDefault().getVTViewTemplateProvider();
		if (vtViewTemplateProvider == null) {
			return getDefaultAlignment();
		}
		final Set<VTStyleProperty> styleProperties = vtViewTemplateProvider
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTAlignmentStyleProperty.class.isInstance(styleProperty)) {
				if (VTAlignmentStyleProperty.class.cast(styleProperty).getType() == AlignmentType.LEFT) {
					return SWT.LEFT;
				}
				else if (VTAlignmentStyleProperty.class.cast(styleProperty).getType() == AlignmentType.RIGHT) {
					return SWT.RIGHT;
				}
			}
		}
		return getDefaultAlignment();
	}

	/**
	 * Return the default alignment value for this renderer.
	 *
	 * @return the alignment to use if no style was defined
	 */
	protected int getDefaultAlignment() {
		return SWT.LEFT;
	}

	/**
	 * The VariantId to use e.g. for RAP
	 *
	 * @return the String identifying this control
	 */
	protected String getTextVariantID() {
		return "org_eclipse_emf_ecp_control_string"; //$NON-NLS-1$
	}

	@Override
	protected void setControlEnabled(SWTGridCell gridCell, Control control, boolean enabled) {
		if (isDisableRenderedAsEditable()
			&& (getVElement().getLabelAlignment() == LabelAlignment.NONE && gridCell.getColumn() == 1
			|| getVElement().getLabelAlignment() == LabelAlignment.LEFT && gridCell.getColumn() == 2)) {
			Control controlToUnset = control;
			if (isControlUnsettable()) {
				// if (!setting.isSet()) {
				// return;
				// }
				controlToUnset = Composite.class.cast(Composite.class.cast(control).getChildren()[0]).getChildren()[0];
			}
			Text.class.cast(controlToUnset).setEditable(enabled);
		} else {
			super.setControlEnabled(gridCell, control, enabled);
		}
	}

	private boolean isControlUnsettable() {
		IValueProperty valueProperty;
		try {
			valueProperty = getEMFFormsDatabinding()
				.getValueProperty(getVElement().getDomainModelReference());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new RenderingFailedReport(ex));
			return false;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty;
		final boolean unsettable = feature.isUnsettable();
		return unsettable;
	}

	private boolean isDisableRenderedAsEditable() {
		final VTViewTemplateProvider vtViewTemplateProvider = Activator.getDefault().getVTViewTemplateProvider();
		if (vtViewTemplateProvider == null) {
			return false;
		}
		final Set<VTStyleProperty> styleProperties = vtViewTemplateProvider
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTTextControlEnablementStyleProperty.class.isInstance(styleProperty)) {
				return VTTextControlEnablementStyleProperty.class.cast(styleProperty).isRenderDisableAsEditable();
			}
		}
		return false;
	}

	/**
	 * An {@link EMFUpdateConvertValueStrategy} that encapsulates the converting
	 * of the actual value. Use this class to provide a specific context
	 * for the conversion of the value, but likewise enable it clients to modify
	 * the conversion behavior.
	 *
	 * @author emueller
	 *
	 */
	class EMFUpdateConvertValueStrategy extends EMFUpdateValueStrategy {

		/**
		 * Constructor.
		 */
		public EMFUpdateConvertValueStrategy() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
		 */
		@Override
		public Object convert(Object value) {
			return convertValue(value);
		}

		/**
		 * Convert a value.
		 *
		 * @param value the value to convert
		 * @return the converted value
		 */
		protected Object convertValue(Object value) {
			return super.convert(value);
		}
	}

	/**
	 * The strategy to convert from model to target.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	protected class ModelToTargetUpdateStrategy extends EMFUpdateConvertValueStrategy {

		private final boolean tooltip;

		/**
		 * Constructor.
		 *
		 * @param tooltip <code>true</code> if the to be converted value is a tooltip and should be modified by a
		 *            {@link org.eclipse.emf.ecp.view.spi.provider.ECPStringModifier ECPStringModifier},
		 *            <code>false</code> otherwise.
		 */
		public ModelToTargetUpdateStrategy(boolean tooltip) {
			this.tooltip = tooltip;

		}

		@Override
		public Object convert(Object value) {
			final Object converted = convertValue(value);
			if (tooltip && String.class.isInstance(converted)) {
				IObservableValue observableValue;
				try {
					observableValue = Activator
						.getDefault()
						.getEMFFormsDatabinding()
						.getObservableValue(getVElement().getDomainModelReference(),
							getViewModelContext().getDomainModel());
				} catch (final DatabindingFailedException ex) {
					Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
					return converted;
				}
				final InternalEObject internalEObject = (InternalEObject) ((IObserving) observableValue).getObserved();
				final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
				return ECPTooltipModifierHelper.modifyString(String.class.cast(converted),
					internalEObject.eSetting(structuralFeature));
			}
			return converted;
		}

	}

	/**
	 * The strategy to convert from target to model.
	 *
	 * @author Eugen
	 *
	 */
	protected class TargetToModelUpdateStrategy extends EMFUpdateConvertValueStrategy {

		private final boolean unsetable;

		/**
		 * Constructor for indicating whether a value is unsettable.
		 *
		 * @param unsettable true if value is unsettable, false otherwise
		 */
		public TargetToModelUpdateStrategy(boolean unsettable) {
			unsetable = unsettable;

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object convert(Object value) {
			try {
				if ("".equals(value)) { //$NON-NLS-1$
					value = null;
				}
				if (value == null && unsetable) {
					return SetCommand.UNSET_VALUE;
				}

				return convertValue(value);

			} catch (final IllegalArgumentException e) {
				throw e;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return LocalizationServiceHelper
			.getString(getClass(), MessageKeys.StringControl_NoTextSetClickToSetText);
	}

}
