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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
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

	private static final String SEPARATOR = "/"; //$NON-NLS-1$

	private SWTGridDescription rendererGridDescription;

	private final EMFFormsLabelProvider labelProvider;
	private EMFDataBindingContext dataBindingContext;
	private final EMFFormsRendererFactory rendererFactory;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param labelProvider the {@link EMFFormsLabelProvider label provider}
	 * @param rendererFactory the {@link EMFFormsRendererFactory renderer factory}
	 * @since 1.8
	 */
	@Inject
	public CompoundControlSWTRenderer(
		VCompoundControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsLabelProvider labelProvider,
		EMFFormsRendererFactory rendererFactory) {
		super(vElement, viewContext, reportService);
		this.labelProvider = labelProvider;
		this.rendererFactory = rendererFactory;
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

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 2, this);
			final SWTGridCell label = rendererGridDescription.getGrid().get(0);
			label.setHorizontalGrab(false);
			label.setVerticalGrab(false);
			label.setVerticalFill(false);
			final SWTGridCell controls = rendererGridDescription.getGrid().get(1);
			controls.setVerticalGrab(false);
			controls.setVerticalFill(false);
		}
		return rendererGridDescription;
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		switch (cell.getColumn()) {
		case 0:
			return createLabel(parent);
		case 1:
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
		final EList<VControl> controls = getVElement().getControls();

		final Composite labelComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2 * controls.size() -
			1).equalWidth(false).applyTo(labelComposite);

		for (int i = 0; i < controls.size(); i++) {
			if (i != 0) {
				final Label separatorLabel = new Label(labelComposite, SWT.NONE);
				GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(separatorLabel);
				separatorLabel.setText(SEPARATOR);
			}

			final Label label = new Label(labelComposite, SWT.NONE);
			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(label);
			final VControl control = controls.get(i);
			try {
				@SuppressWarnings("deprecation")
				final IObservableValue targetValue = org.eclipse.jface.databinding.swt.SWTObservables
					.observeText(label);
				final IObservableValue modelValue = getLabelProvider().getDisplayName(control.getDomainModelReference(),
					getViewModelContext().getDomainModel());
				final Binding bindValue = getDataBindingContext().bindValue(targetValue, modelValue);
				bindValue.updateModelToTarget();
			} catch (final NoLabelFoundException ex) {
				getReportService().report(new AbstractReport(ex));
			}

		}

		labelComposite.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
		return labelComposite;
	}

	/**
	 * Creates the controls composite.
	 *
	 * @param parent the parent composite
	 * @return the controls
	 * @since 1.8
	 */
	protected Control createControls(Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());

		final Map<VContainedElement, AbstractSWTRenderer<VElement>> elementRendererMap = new LinkedHashMap<VContainedElement, AbstractSWTRenderer<VElement>>();
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
			elementRendererMap.put(child, renderer);
		}

		columnComposite.setLayout(getColumnLayout(elementRendererMap.size(), false));
		for (final VContainedElement child : elementRendererMap.keySet()) {
			final AbstractSWTRenderer<VElement> renderer = elementRendererMap.get(child);
			final Composite column = new Composite(columnComposite, SWT.NONE);
			column.setBackground(parent.getBackground());

			column.setLayoutData(getSpanningLayoutData(child, 1, 1));

			final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
			column.setLayout(getColumnLayout(gridDescription.getColumns(), false));

			try {
				for (final SWTGridCell childGridCell : gridDescription.getGrid()) {
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
}
