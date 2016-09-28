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

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentStyleProperty;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.swt.core.EMFFormsSWTConstants;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Color;
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

	private final EMFFormsEditSupport emfFormsEditSupport;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 * @since 1.6
	 */
	@Inject
	public TextControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.emfFormsEditSupport = emfFormsEditSupport;
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
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).applyTo(composite);
		final Text text = new Text(composite, getTextWidgetStyle());
		text.setData(CUSTOM_VARIANT, getTextVariantID());
		text.setMessage(getTextMessage());
		text.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				text.selectAll();
			}
		});
		final GridDataFactory gdf = GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
			.grab(true, true).span(1, 1);
		final EMFFormsEditSupport editSupport = getEMFFormsEditSupport();
		if (editSupport.isMultiLine(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel())) {
			gdf.hint(50, 200);// set x hint to enable wrapping
		}
		gdf.applyTo(text);
		return composite;
	}

	/**
	 * Returns the text which should be set as the message text on the Text field.
	 *
	 * @return the string to show as the message
	 * @since 1.6
	 */
	protected String getTextMessage() {
		try {
			return (String) getEMFFormsLabelProvider()
				.getDisplayName(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel())
				.getValue();
		} catch (final NoLabelFoundException ex) {
			// FIXME Expectations?
			getReportService().report(new RenderingFailedReport(ex));
		}
		return ""; //$NON-NLS-1$
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
		final Control controlToObserve = Composite.class.cast(text).getChildren()[0];
		final boolean useOnModifyDatabinding = useOnModifyDatabinding();
		final IObservableValue value;
		if (useOnModifyDatabinding) {
			value = WidgetProperties.text(SWT.Modify).observeDelayed(250, controlToObserve);
		} else {
			value = WidgetProperties.text(SWT.FocusOut).observe(controlToObserve);
		}
		final Binding binding = dataBindingContext.bindValue(value, modelValue, targetToModel, modelToTarget);
		return binding;
	}

	/**
	 * Whether {@link SWT#Modify} or {@link SWT#FocusOut} shall be used as the target databinding trigger.
	 *
	 * @return <code>true</code> if Modify should be used, <code>false</code> otherwise
	 * @since 1.9
	 */
	protected final boolean useOnModifyDatabinding() {
		final VElement viewCandidate = getViewModelContext().getViewModel();
		if (!VView.class.isInstance(viewCandidate)) {
			return false;
		}
		final VViewModelProperties properties = VView.class.cast(viewCandidate).getLoadingProperties();
		if (properties == null) {
			return false;
		}
		return EMFFormsSWTConstants.USE_ON_MODIFY_DATABINDING_VALUE
			.equalsIgnoreCase((String) properties.get(EMFFormsSWTConstants.USE_ON_MODIFY_DATABINDING_KEY));
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
		final IObservableValue toolTip = WidgetProperties.tooltipText().observe(text);
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
	 *
	 * @return the {@link EMFFormsEditSupport}
	 * @since 1.10
	 */
	protected EMFFormsEditSupport getEMFFormsEditSupport() {
		return emfFormsEditSupport;
	}

	private int getAlignment() {
		if (getVTViewTemplateProvider() == null) {
			return getDefaultAlignment();
		}
		final Set<VTStyleProperty> styleProperties = getVTViewTemplateProvider()
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTAlignmentStyleProperty.class.isInstance(styleProperty)) {
				if (VTAlignmentStyleProperty.class.cast(styleProperty).getType() == AlignmentType.LEFT) {
					return SWT.LEFT;
				} else if (VTAlignmentStyleProperty.class.cast(styleProperty).getType() == AlignmentType.RIGHT) {
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
				|| hasLeftLabelAlignment() && gridCell.getColumn() == 2)) {
			Control controlToUnset = control;
			if (isControlUnsettable()) {
				// if (!setting.isSet()) {
				// return;
				// }
				controlToUnset = Composite.class
					.cast(Composite.class.cast(Composite.class.cast(control).getChildren()[0]).getChildren()[0])
					.getChildren()[0];
			}
			Text.class.cast(controlToUnset).setEditable(enabled);
		} else {
			if (getVElement().getLabelAlignment() == LabelAlignment.NONE && gridCell.getColumn() == 1
				|| hasLeftLabelAlignment() && gridCell.getColumn() == 2) {
				super.setControlEnabled(gridCell, Composite.class.cast(control).getChildren()[0], enabled);
			} else {
				super.setControlEnabled(gridCell, control, enabled);
			}
		}
	}

	private boolean isControlUnsettable() {
		IValueProperty valueProperty;
		try {
			valueProperty = getEMFFormsDatabinding()
				.getValueProperty(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new RenderingFailedReport(ex));
			return false;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		final boolean unsettable = feature.isUnsettable();
		return unsettable;
	}

	private boolean isDisableRenderedAsEditable() {
		final VTViewTemplateProvider vtViewTemplateProvider = getVTViewTemplateProvider();
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
					observableValue = getModelValue();
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
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
			.getString(TextControlSWTRenderer.class, MessageKeys.StringControl_NoTextSetClickToSetText);
	}

	@Override
	protected void setValidationColor(Control control, Color validationColor) {
		super.setValidationColor(Composite.class.cast(control).getChildren()[0], validationColor);
	}

	@Override
	protected void setValidationForegroundColor(Control control, Color validationColor) {
		super.setValidationForegroundColor(Composite.class.cast(control).getChildren()[0], validationColor);
	}
}
