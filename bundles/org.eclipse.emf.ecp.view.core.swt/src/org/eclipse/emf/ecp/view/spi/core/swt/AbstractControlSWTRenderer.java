/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsControlProcessorService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Super class for all kinds of control renderer.
 *
 * @param <VCONTROL> the {@link VControl} of this renderer.
 * @author Eugen Neufeld
 *
 */
public abstract class AbstractControlSWTRenderer<VCONTROL extends VControl> extends AbstractSWTRenderer<VCONTROL> {

	private final EMFFormsDatabinding emfFormsDatabinding;
	private final EMFFormsLabelProvider emfFormsLabelProvider;
	private final VTViewTemplateProvider vtViewTemplateProvider;
	private boolean isDisposed;
	private IObservableValue modelValue;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @since 1.6
	 */
	public AbstractControlSWTRenderer(VCONTROL vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService);
		this.emfFormsDatabinding = emfFormsDatabinding;
		this.emfFormsLabelProvider = emfFormsLabelProvider;
		this.vtViewTemplateProvider = vtViewTemplateProvider;
		viewModelDBC = new EMFDataBindingContext();
		isDisposed = false;
	}

	/**
	 * The {@link EMFFormsDatabinding} to use.
	 *
	 * @return The EMFFormsDatabinding
	 * @since 1.6
	 */
	protected EMFFormsDatabinding getEMFFormsDatabinding() {
		return emfFormsDatabinding;
	}

	/**
	 * The {@link EMFFormsLabelProvider} to use.
	 *
	 * @return The EMFFormsLabelProvider
	 * @since 1.6
	 */
	protected EMFFormsLabelProvider getEMFFormsLabelProvider() {
		return emfFormsLabelProvider;
	}

	/**
	 * The {@link VTViewTemplateProvider} to use.
	 *
	 * @return The VTViewTemplateProvider
	 * @since 1.6
	 */
	protected VTViewTemplateProvider getVTViewTemplateProvider() {
		return vtViewTemplateProvider;
	}

	private DataBindingContext dataBindingContext;
	private DomainModelReferenceChangeListener domainModelReferenceChangeListener;
	private final EMFDataBindingContext viewModelDBC;

	// TODO is this needed?
	@Override
	protected void postInit() {
		super.postInit();
		domainModelReferenceChangeListener = new DomainModelReferenceChangeListener() {

			@Override
			public void notifyChange() {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						if (!isDisposed) {
							applyEnable();
						}
					}
				});
			}
		};
		if (getVElement().getDomainModelReference() != null) {
			getVElement().getDomainModelReference().getChangeListener().add(domainModelReferenceChangeListener);
		}
		applyEnable();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#render(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Control render(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control control = super.render(cell, parent);

		if (control == null) {
			return null;
		}

		if (!canHandleControlProcessor()) {
			defaultHandleControlProcessorForCell(control, cell);
		}

		return control;
	}

	/**
	 * <p>
	 * Indicates if the given Control SWT renderer takes the responsibility to call a possibly existing
	 * {@link EMFFormsControlProcessorService} itself.
	 * </p>
	 * <p>
	 * The default implementation returns {@code false}.
	 * </p>
	 *
	 * @return
	 *         {@code true} if the Control SWT renderer can handle the {@link EMFFormsControlProcessorService} itself,
	 *         {@code false} otherwise.
	 * @since 1.8
	 */
	protected boolean canHandleControlProcessor() {
		return false;
	}

	/**
	 * This method is called by {link {@link #render(SWTGridCell, Composite)} for each
	 * {@code control} and {@code cell} if {@link #canHandleControlProcessor()} returns {@code false}. The default
	 * implementation forwards to {@link #defaultHandleControlProcessor(Control)} if the cell's column is {@code 2}.
	 *
	 * @param control
	 *            The {@link Control} which is to be processed by the {@link EMFFormsControlProcessorService}.
	 * @param cell
	 *            The {@link SWTGridCell} for the given {@code control}.
	 * @since 1.8
	 */
	protected void defaultHandleControlProcessorForCell(Control control, SWTGridCell cell) {
		if (cell.getColumn() == 2) {
			defaultHandleControlProcessor(control);
		}
	}

	/**
	 * Calls a possibly existing {@link EMFFormsControlProcessorService} for the given {@code control}.
	 *
	 * @param control
	 *            The {@link Control} which is to be processed by the {@link EMFFormsControlProcessorService}.
	 * @since 1.8
	 */
	protected void defaultHandleControlProcessor(Control control) {
		if (getViewModelContext().hasService(EMFFormsControlProcessorService.class)) {
			final EMFFormsControlProcessorService service = getViewModelContext()
				.getService(EMFFormsControlProcessorService.class);
			service.process(control, getVElement(), getViewModelContext());
		}
	}

	@Override
	protected void dispose() {
		isDisposed = true;
		if (getVElement().getDomainModelReference() != null) {
			getVElement().getDomainModelReference().getChangeListener().remove(domainModelReferenceChangeListener);
		}

		domainModelReferenceChangeListener = null;

		if (dataBindingContext != null) {
			dataBindingContext.dispose();
			dataBindingContext = null;
		}
		viewModelDBC.dispose();
		if (modelValue != null) {
			modelValue.dispose();
		}
		super.dispose();
	}

	/**
	 * Returns the validation icon matching the given severity.
	 *
	 * @param severity the severity of the {@link org.eclipse.emf.common.util.Diagnostic}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	protected final Image getValidationIcon(int severity) {
		return SWTValidationHelper.INSTANCE.getValidationIcon(severity, getVElement(), getViewModelContext());
	}

	/**
	 * Returns the background color for a control with the given validation severity.
	 *
	 * @param severity severity the severity of the {@link org.eclipse.emf.common.util.Diagnostic}
	 * @return the color to be used as a background color
	 */
	protected final Color getValidationBackgroundColor(int severity) {
		if (isDisposed) {
			return null;
		}
		return SWTValidationHelper.INSTANCE
			.getValidationBackgroundColor(severity, getVElement(), getViewModelContext());
	}

	/**
	 * Creates a new {@link DataBindingContext}.
	 *
	 * @return a new {@link DataBindingContext} each time this method is called
	 */
	protected final DataBindingContext getDataBindingContext() {
		if (dataBindingContext == null) {
			dataBindingContext = new EMFDataBindingContext();
		}
		return dataBindingContext;
	}

	/**
	 * Returns an {@link IObservableValue} based on the control's domain model reference and domain model.
	 *
	 * @return the {@link IObservableValue}
	 * @throws DatabindingFailedException if the databinding of the domain model object fails.
	 * @since 1.6
	 */
	protected final IObservableValue getModelValue() throws DatabindingFailedException {
		if (modelValue == null) {
			final VDomainModelReference ref = getVElement().getDomainModelReference();
			if (ref == null) {
				throw new DatabindingFailedException(String
					.format(
						"No DomainModelReference could be found for the VElement %1$s.", getVElement().getName())); //$NON-NLS-1$
			}
			final EObject eObject = getViewModelContext().getDomainModel();

			final EMFFormsDatabinding databindingService = getEMFFormsDatabinding();
			modelValue = databindingService.getObservableValue(ref, eObject);
		}
		return modelValue;
	}

	/**
	 * Returns the {@link EditingDomain} for the provided {@link EObject domain model}.
	 *
	 * @param domainModel The provided {@link EObject domain model}
	 * @return The {@link EditingDomain} of this {@link EObject domain model}
	 * @since 1.6
	 */
	protected final EditingDomain getEditingDomain(EObject domainModel) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(domainModel);
	}

	/**
	 * Create the {@link Control} displaying the label of the current {@link VControl}.
	 *
	 * @param parent the {@link Composite} to render onto
	 * @return the created {@link Control} or null
	 */
	protected final Control createLabel(final Composite parent) {
		Label label = null;
		labelRender: if (hasLeftLabelAlignment()) {
			final VDomainModelReference domainModelReference = getVElement().getDomainModelReference();
			final IValueProperty valueProperty;
			try {
				valueProperty = getEMFFormsDatabinding().getValueProperty(domainModelReference,
					getViewModelContext().getDomainModel());
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				break labelRender;
			} catch (final IllegalArgumentException ex) {
				getReportService().report(new AbstractReport(ex));
				break labelRender;
			}

			final EMFFormsLabelProvider labelProvider = getEMFFormsLabelProvider();
			label = new Label(parent, SWT.NONE);
			label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
			label.setBackground(parent.getBackground());

			final EObject rootObject = getViewModelContext().getDomainModel();
			try {
				final IObservableValue textObservable = WidgetProperties.text().observe(label);
				final IObservableValue displayNameObservable = labelProvider.getDisplayName(domainModelReference,
					rootObject);
				viewModelDBC.bindValue(textObservable, displayNameObservable, null, new UpdateValueStrategy() {

					/**
					 * {@inheritDoc}
					 *
					 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
					 */
					@Override
					public Object convert(Object value) {
						String extra = ""; //$NON-NLS-1$
						final VTMandatoryStyleProperty mandatoryStyle = getMandatoryStyle();
						final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
						if (mandatoryStyle.isHighliteMandatoryFields() && structuralFeature.getLowerBound() > 0) {
							extra = mandatoryStyle.getMandatoryMarker();
						}
						final String result = (String) super.convert(value);
						return result + extra;
					}

				});
				final IObservableValue tooltipObservable = WidgetProperties.tooltipText().observe(label);
				final IObservableValue descriptionObservable = labelProvider.getDescription(domainModelReference,
					rootObject);
				viewModelDBC.bindValue(tooltipObservable, descriptionObservable);
			} catch (final NoLabelFoundException e) {
				// FIXME Expectations?
				getReportService().report(new RenderingFailedReport(e));
			}

		}
		return label;
	}

	/**
	 * Whether the label for this control should be rendered on the left of the control. This is the case if the
	 * {@link VControl#getLabelAlignment()} is set to {@link LabelAlignment#LEFT} or {@link LabelAlignment#DEFAULT}.
	 *
	 * @return <code>true</code> if label should be on the left, <code>false</code> otherwise
	 * @since 1.7
	 */
	protected boolean hasLeftLabelAlignment() {
		return getVElement().getLabelAlignment() == LabelAlignment.LEFT
			|| getVElement().getLabelAlignment() == LabelAlignment.DEFAULT;
	}

	private VTMandatoryStyleProperty getMandatoryStyle() {
		if (vtViewTemplateProvider == null) {
			return getDefaultStyle();
		}
		final Set<VTStyleProperty> styleProperties = vtViewTemplateProvider
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTMandatoryStyleProperty.class.isInstance(styleProperty)) {
				return (VTMandatoryStyleProperty) styleProperty;
			}
		}
		return getDefaultStyle();
	}

	private VTMandatoryStyleProperty getDefaultStyle() {
		return VTMandatoryFactory.eINSTANCE.createMandatoryStyleProperty();
	}

	/**
	 * Creates a validation icon.
	 *
	 * @param composite the {@link Composite} to create onto
	 * @return the created Label
	 */
	protected final Label createValidationIcon(Composite composite) {
		final Label validationLabel = new Label(composite, SWT.NONE);
		validationLabel.setBackground(composite.getBackground());
		return validationLabel;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#applyEnable()
	 * @since 1.6
	 */
	@Override
	protected void applyEnable() {
		for (final SWTGridCell gridCell : getControls().keySet()) {
			try {
				final boolean observedNotNull = ((IObserving) getModelValue()).getObserved() != null;
				final boolean enabled = observedNotNull && getVElement().isEnabled();
				setControlEnabled(gridCell, getControls().get(gridCell), enabled);
				if (Boolean.FALSE.equals(enabled)) {
					getVElement().setDiagnostic(null);
				}
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
				setControlEnabled(gridCell, getControls().get(gridCell), false);
				getVElement().setDiagnostic(null);
			}
		}
	}
}
