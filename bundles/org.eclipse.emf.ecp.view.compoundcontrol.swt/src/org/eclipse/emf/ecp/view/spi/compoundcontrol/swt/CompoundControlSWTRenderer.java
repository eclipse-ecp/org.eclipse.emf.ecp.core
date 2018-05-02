/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.compoundcontrol.swt;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.common.util.RendererUtil;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRendererUtil;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRendererUtil;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

/**
 * {@link AbstractSWTRenderer} for the {@link VCompoundControl} view model.
 *
 * @author jfaltermeier
 *
 */
public class CompoundControlSWTRenderer extends AbstractSWTRenderer<VCompoundControl> {

	private static final String SEPARATOR = " / "; //$NON-NLS-1$

	private SWTGridDescription rendererGridDescription;

	private final EMFFormsLabelProvider labelProvider;
	private EMFDataBindingContext dataBindingContext;
	private final EMFFormsRendererFactory rendererFactory;

	private LinkedHashMap<VContainedElement, AbstractSWTRenderer<VElement>> elementRendererMap;

	private final VTViewTemplateProvider viewTemplateProvider;

	private boolean firstControlValidationIconUsed;

	private final EMFFormsDatabindingEMF databindingService;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param labelProvider the {@link EMFFormsLabelProvider label provider}
	 * @param rendererFactory the {@link EMFFormsRendererFactory renderer factory}
	 * @param viewTemplateProvider {@link VTViewTemplateProvider}
	 * @param databindingService {@link EMFFormsDatabindingEMF}
	 * @since 1.17
	 */
	@Inject
	public CompoundControlSWTRenderer(
		VCompoundControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsLabelProvider labelProvider,
		EMFFormsRendererFactory rendererFactory,
		VTViewTemplateProvider viewTemplateProvider,
		EMFFormsDatabindingEMF databindingService) {
		super(vElement, viewContext, reportService);
		this.labelProvider = labelProvider;
		this.rendererFactory = rendererFactory;
		this.viewTemplateProvider = viewTemplateProvider;
		this.databindingService = databindingService;
	}

	/**
	 * Returns the {@link EMFFormsLabelProvider}.
	 *
	 * @return the label provider
	 * @since 1.8
	 */
	protected EMFFormsLabelProvider getLabelProvider() {
		return labelProvider;
	}

	/**
	 * Returns the {@link EMFFormsRendererFactory}.
	 *
	 * @return the renderer factory
	 * @since 1.8
	 */
	protected EMFFormsRendererFactory getRendererFactory() {
		return rendererFactory;
	}

	/**
	 * @return the {@link EMFFormsDatabindingEMF}.
	 * @since 1.17
	 */
	protected EMFFormsDatabindingEMF getDatabindingService() {
		return databindingService;
	}

	/**
	 * Returns the {@link DataBindingContext}.
	 *
	 * @return the databining context
	 * @since 1.8
	 */
	protected DataBindingContext getDataBindingContext() {
		if (dataBindingContext == null) {
			dataBindingContext = new EMFDataBindingContext();
		}
		return dataBindingContext;
	}

	/**
	 * @return child control to renderer map
	 * @since 1.16
	 */
	protected LinkedHashMap<VContainedElement, AbstractSWTRenderer<VElement>> getElementRendererMap() {
		initChildRendererMap();
		return elementRendererMap;
	}

	private void setElementRendererMap(
		LinkedHashMap<VContainedElement, AbstractSWTRenderer<VElement>> elementRendererMap) {
		this.elementRendererMap = elementRendererMap;
	}

	/**
	 * @return the viewTemplateProvider
	 * @since 1.16
	 */
	protected VTViewTemplateProvider getViewTemplateProvider() {
		return viewTemplateProvider;
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			final int columns = SimpleControlSWTRendererUtil
				.showLabel(getVElement(), getReportService(), getClass().getName()) ? 3 : 2;

			rendererGridDescription = GridDescriptionFactory.INSTANCE.createEmptyGridDescription();
			rendererGridDescription.setRows(1);
			rendererGridDescription.setColumns(columns);

			final List<SWTGridCell> grid = new ArrayList<SWTGridCell>();

			if (columns == 3) {
				final SWTGridCell labelCell = SimpleControlSWTRendererUtil
					.createLabelCell(grid.size(), this, getLabelWidth());
				grid.add(labelCell);
			}

			final SWTGridCell validationCell = SimpleControlSWTRendererUtil.createValidationCell(grid.size(), this);
			grid.add(validationCell);

			final SWTGridCell controlCel = SimpleControlSWTRendererUtil.createControlCell(grid.size(), this);
			grid.add(controlCel);

			rendererGridDescription.setGrid(grid);
		}
		return rendererGridDescription;
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		int controlIndex = cell.getColumn();
		if (getVElement().getLabelAlignment() == LabelAlignment.NONE) {
			controlIndex++;
		}
		switch (controlIndex) {
		case 0:
			return createLabel(parent);
		case 1:
			return createValidationIcon(parent);
		case 2:
			return createControls(parent);
		default:
			throw new IllegalArgumentException(
				String.format("The provided SWTGridCell (%1$s) cannot be used by this (%2$s) renderer.", //$NON-NLS-1$
					cell.toString(), toString()));
		}
	}

	/**
	 * Creates the label composite.
	 *
	 * @param parent the parent composite
	 * @return the label
	 * @since 1.8
	 */
	protected Control createLabel(Composite parent) {
		final Label label = new Label(parent, getLabelStyleBits());
		label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
		SWTDataElementIdHelper
			.setElementIdDataWithSubId(label, getVElement(), "control_label", getViewModelContext()); //$NON-NLS-1$
		label.setBackground(parent.getBackground());

		final IObservableValue textObservable = WidgetProperties.text().observe(label);
		final Map<IObservableValue, Map.Entry<VControl, EStructuralFeature>> displayNameObservables = getLabelDisplayNameObservables();

		for (final IObservableValue displayNameObservable : displayNameObservables.keySet()) {
			getDataBindingContext().bindValue(
				textObservable,
				displayNameObservable,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER),
				new CompoundControlDisplayNameUpdateValueStrategy(displayNameObservables));
		}

		final IObservableValue tooltipObservable = WidgetProperties.tooltipText().observe(label);
		final List<IObservableValue> labelDescriptionObservables = getLabelDescriptionObservables();
		for (final IObservableValue descriptionObservable : labelDescriptionObservables) {
			getDataBindingContext().bindValue(
				tooltipObservable,
				descriptionObservable,
				new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER),
				new EMFUpdateValueStrategy() {
					@Override
					public Object convert(Object value) {
						final StringBuilder stringBuilder = new StringBuilder();
						for (final IObservableValue obs : labelDescriptionObservables) {
							if (stringBuilder.length() > 0) {
								stringBuilder.append("\n"); //$NON-NLS-1$
							}
							stringBuilder.append(obs.getValue());
						}
						return stringBuilder.toString();
					}
				});

		}

		return label;
	}

	private Map<IObservableValue, Map.Entry<VControl, EStructuralFeature>> getLabelDisplayNameObservables() {
		final LinkedHashMap<IObservableValue, Entry<VControl, EStructuralFeature>> displayNames = new LinkedHashMap<IObservableValue, Map.Entry<VControl, EStructuralFeature>>();

		for (final VControl control : getVElement().getControls()) {
			try {
				final IObservableValue displayName = labelProvider
					.getDisplayName(control.getDomainModelReference(), getViewModelContext().getDomainModel());

				final EStructuralFeature feature = getDatabindingService()
					.getValueProperty(control.getDomainModelReference(), getViewModelContext().getDomainModel())
					.getStructuralFeature();

				displayNames.put(displayName,
					new AbstractMap.SimpleEntry<VControl, EStructuralFeature>(control, feature));
			} catch (final NoLabelFoundException ex) {
				getReportService().report(new AbstractReport(ex, IStatus.WARNING));
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new AbstractReport(ex, IStatus.WARNING));
			}
		}

		return displayNames;
	}

	private List<IObservableValue> getLabelDescriptionObservables() {
		final List<IObservableValue> labelDescriptionObservables = new ArrayList<IObservableValue>();
		for (final VControl control : getVElement().getControls()) {
			try {
				labelDescriptionObservables.add(labelProvider.getDescription(control.getDomainModelReference(),
					getViewModelContext().getDomainModel()));
			} catch (final NoLabelFoundException ex) {
				getReportService().report(new AbstractReport(ex, IStatus.WARNING));
			}
		}
		return labelDescriptionObservables;
	}

	/**
	 * @return the style bits for the control's label
	 * @since 1.17
	 */
	protected int getLabelStyleBits() {
		return AbstractControlSWTRendererUtil
			.getLabelStyleBits(getViewTemplateProvider(), getVElement(), getViewModelContext());
	}

	/**
	 * @return an optional width for the control's label
	 * @since 1.16
	 */
	protected Optional<Integer> getLabelWidth() {
		final VTLabelWidthStyleProperty styleProperty = RendererUtil.getStyleProperty(
			getViewTemplateProvider(),
			getVElement(),
			getViewModelContext(),
			VTLabelWidthStyleProperty.class);
		if (styleProperty == null || !styleProperty.isSetWidth()) {
			return Optional.empty();
		}
		return Optional.of(styleProperty.getWidth());
	}

	/**
	 * Return the validation cell of the fist child. If no children or strange first cell, return a dummy validation
	 * icon.
	 *
	 * @param parent the parent to render on
	 * @return the validation control
	 *
	 * @throws NoRendererFoundException this is thrown when a renderer cannot be found
	 * @throws NoPropertyDescriptorFoundExeption this is thrown when no property descriptor can be found
	 * @since 1.17
	 */
	protected Control createValidationIcon(Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		if (getVElement().getControls().isEmpty()) {
			return createDummyValidationIcon(parent);
		}
		final AbstractSWTRenderer<VElement> renderer = getElementRendererMap()
			.get(getVElement().getControls().get(0));
		if (renderer == null) {
			return createDummyValidationIcon(parent);
		}
		final SWTGridDescription gridDescription = renderer
			.getGridDescription(GridDescriptionFactory.INSTANCE.createEmptyGridDescription());
		if (gridDescription.getColumns() < 2) {
			return createDummyValidationIcon(parent);
		}
		/* use child renderer cell */
		setFirstControlValidationIconUsed(true);
		final SWTGridCell validationCell = gridDescription.getGrid().get(0);
		return validationCell.getRenderer().render(validationCell, parent);
	}

	/**
	 * Whether the first cell of the first child control was used to renderer our validation icon.
	 *
	 * @param used <code>true</code> if used, <code>false</code> otherwise
	 * @since 1.17
	 */
	protected void setFirstControlValidationIconUsed(boolean used) {
		firstControlValidationIconUsed = used;
	}

	/**
	 * Creates the validation icon of the first cell of the first child may not be used as the validation icon.
	 *
	 * @param parent the parent
	 * @return the validation icon
	 * @since 1.17
	 */
	protected Control createDummyValidationIcon(Composite parent) {
		final Label validationLabel = new Label(parent, SWT.NONE);
		SWTDataElementIdHelper
			.setElementIdDataWithSubId(validationLabel, getVElement(), "control_validation", getViewModelContext()); //$NON-NLS-1$
		validationLabel.setBackground(parent.getBackground());
		return validationLabel;
	}

	/**
	 * Creates the controls composite.
	 *
	 * @param parent the parent composite
	 * @return the controls
	 * @since 1.8
	 */
	protected Control createControls(Composite parent) {
		return createControls(parent, firstControlValidationIconUsed ? 1 : 0);
	}

	/**
	 * Creates the controls composite. May skip the first cell(s).
	 *
	 * @param parent the parent composite
	 * @param cellsToSkip number of cells to skip
	 * @return the controls
	 * @since 1.16
	 */
	protected Control createControls(Composite parent, int cellsToSkip) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());

		columnComposite.setLayout(getColumnLayout(getElementRendererMap().size(), false));
		for (final VContainedElement child : getElementRendererMap().keySet()) {
			final AbstractSWTRenderer<VElement> renderer = getElementRendererMap().get(child);
			final Composite column = new Composite(columnComposite, SWT.NONE);
			column.setBackground(parent.getBackground());

			column.setLayoutData(getSpanningLayoutData(child, 1, 1));

			final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
			final int columns = gridDescription.getColumns() - cellsToSkip;
			column.setLayout(getColumnLayout(columns < 1 ? 1 : columns, false));

			try {
				for (final SWTGridCell childGridCell : gridDescription.getGrid()) {
					if (cellsToSkip > 0) {
						cellsToSkip--;
						continue;
					}
					final Control control = childGridCell.getRenderer().render(childGridCell, column);
					if (control == null) {
						continue;
					}
					control.setLayoutData(getLayoutData(childGridCell, gridDescription,
						gridDescription, gridDescription, childGridCell.getRenderer().getVElement(),
						getViewModelContext().getDomainModel(), control));
				}
				for (final SWTGridCell childGridCell : gridDescription.getGrid()) {
					childGridCell.getRenderer().finalizeRendering(column);
				}
			} catch (final NoPropertyDescriptorFoundExeption e) {
				getReportService().report(new RenderingFailedReport(e));
				continue;
			} catch (final NoRendererFoundException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				continue;
			}
		}

		return columnComposite;
	}

	/**
	 * Initialises the child control to renderer map.
	 *
	 * @since 1.16
	 */
	protected void initChildRendererMap() {
		if (elementRendererMap != null) {
			return;
		}
		setElementRendererMap(new LinkedHashMap<VContainedElement, AbstractSWTRenderer<VElement>>());
		for (final VControl child : getVElement().getControls()) {
			child.setLabelAlignment(LabelAlignment.NONE);
			AbstractSWTRenderer<VElement> renderer;
			try {
				renderer = getRendererFactory().getRendererInstance(child, getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(
					new AbstractReport(ex, String.format("No Renderer for %s found.", child.eClass().getName()))); //$NON-NLS-1$
				continue;
			}
			getElementRendererMap().put(child, renderer);
		}
	}

	/**
	 * For testing purposes.
	 *
	 * @param numColumns numColumns
	 * @param equalWidth equalWidth
	 * @return ColumnLayout
	 * @since 1.8
	 */
	protected Layout getColumnLayout(int numColumns, boolean equalWidth) {
		return LayoutProviderHelper.getColumnLayout(numColumns, equalWidth);
	}

	/**
	 * For testing purposes.
	 *
	 * @param child the element for which the spanning layout is requested
	 * @param spanX spanX
	 * @param spanY spanY
	 * @return SpanningLayoutData
	 * @since 1.8
	 */
	protected Object getSpanningLayoutData(VContainedElement child, int spanX, int spanY) {
		return LayoutProviderHelper.getSpanningLayoutData(spanX, spanY);
	}

	/**
	 * For testing purposes.
	 *
	 * @param gridCell gridCell
	 * @param controlGridDescription controlGridDescription
	 * @param currentRowGridDescription currentRowGridDescription
	 * @param fullGridDescription fullGridDescription
	 * @param vElement vElement
	 * @param domainModel domainModel
	 * @param control control
	 * @return LayoutData
	 * @since 1.8
	 */
	protected Object getLayoutData(SWTGridCell gridCell, SWTGridDescription controlGridDescription,
		SWTGridDescription currentRowGridDescription, SWTGridDescription fullGridDescription, VElement vElement,
		EObject domainModel, Control control) {
		return LayoutProviderHelper.getLayoutData(gridCell, controlGridDescription, currentRowGridDescription,
			fullGridDescription, vElement, domainModel, control);
	}

	@Override
	protected void dispose() {
		if (getDataBindingContext() != null) {
			getDataBindingContext().dispose();
		}
		super.dispose();
	}

	/**
	 * Model to target for display names which concats all display names.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class CompoundControlDisplayNameUpdateValueStrategy extends EMFUpdateValueStrategy {
		private final Map<IObservableValue, Entry<VControl, EStructuralFeature>> displayNameObservables;

		/**
		 * @param displayNameObservables
		 */
		private CompoundControlDisplayNameUpdateValueStrategy(
			Map<IObservableValue, Entry<VControl, EStructuralFeature>> displayNameObservables) {
			this.displayNameObservables = displayNameObservables;
		}

		@Override
		public Object convert(Object value) {
			final StringBuilder stringBuilder = new StringBuilder();

			for (final Entry<IObservableValue, Map.Entry<VControl, EStructuralFeature>> obs : displayNameObservables
				.entrySet()) {
				if (stringBuilder.length() > 0) {
					stringBuilder.append(SEPARATOR);
				}
				stringBuilder.append(
					getDisplayName(obs.getKey(), obs.getValue().getKey(), obs.getValue().getValue()));

			}
			return stringBuilder.toString();
		}

		private Object getDisplayName(
			final IObservableValue obs,
			VControl control,
			EStructuralFeature structuralFeature) {

			final Object value = obs.getValue();
			String extra = ""; //$NON-NLS-1$
			final VTMandatoryStyleProperty mandatoryStyle = AbstractControlSWTRendererUtil
				.getMandatoryStyle(getViewTemplateProvider(), control, getViewModelContext());
			if (mandatoryStyle.isHighliteMandatoryFields() && structuralFeature.getLowerBound() > 0) {
				extra = mandatoryStyle.getMandatoryMarker();
			}
			return value + extra;
		}
	}
}
