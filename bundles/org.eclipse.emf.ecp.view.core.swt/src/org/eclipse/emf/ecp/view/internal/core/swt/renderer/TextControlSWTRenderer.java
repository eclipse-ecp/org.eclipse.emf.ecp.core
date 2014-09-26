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
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Renders texts.
 * 
 * @author Eugen Neufeld
 * 
 */
public class TextControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {
	/**
	 * Default constructor.
	 */
	public TextControlSWTRenderer() {
		super();
	}

	/**
	 * Test constructor.
	 * 
	 * @param factory the {@link SWTRendererFactory} to use.
	 */
	TextControlSWTRenderer(SWTRendererFactory factory) {
		super(factory);
	}

	@Override
	protected Binding[] createBindings(Control control, Setting setting) {
		final TargetToModelUpdateStrategy targetToModelUpdateStrategy = new TargetToModelUpdateStrategy(
			setting.getEStructuralFeature().isUnsettable());
		final ModelToTargetUpdateStrategy modelToTargetUpdateStrategy = new ModelToTargetUpdateStrategy(false);
		final Binding binding = bindValue(control, getModelValue(setting), getDataBindingContext(),
			targetToModelUpdateStrategy,
			modelToTargetUpdateStrategy);
		final Binding tooltipBinding = createTooltipBinding(control, getModelValue(setting), getDataBindingContext(),
			targetToModelUpdateStrategy,
			new ModelToTargetUpdateStrategy(true));
		return new Binding[] { binding, tooltipBinding };
	}

	@Override
	protected Control createSWTControl(Composite parent, Setting setting) {
		final Text text = new Text(parent, getTextWidgetStyle());
		text.setData(CUSTOM_VARIANT, getTextVariantID());
		text.setMessage(getTextMessage(setting));
		return text;
	}

	/**
	 * Returns the text which should be set as the message text on the Text field.
	 * 
	 * @param setting the setting being shown in the text field
	 * @return the string to show as the message
	 */
	protected String getTextMessage(Setting setting) {
		return getItemPropertyDescriptor(setting).getDisplayName(null);
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
		if (getItemPropertyDescriptor(getVElement().getDomainModelReference().getIterator().next()).isMultiLine(null)) {
			textStyle = SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		}
		textStyle |= getAlignment();
		return textStyle;
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
			final EStructuralFeature feature = getVElement().getDomainModelReference().getEStructuralFeatureIterator()
				.next();
			Control controlToUnset = control;
			if (feature.isUnsettable()) {
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

		public ModelToTargetUpdateStrategy(boolean tooltip) {
			this.tooltip = tooltip;

		}

		@Override
		public Object convert(Object value) {
			final Object converted = convertValue(value);
			if (tooltip && String.class.isInstance(converted)) {
				return ECPTooltipModifierHelper.modifyString(String.class.cast(converted), getVElement()
					.getDomainModelReference().getIterator().next());
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
		return RendererMessages.StringControl_NoTextSetClickToSetText;
	}

}
