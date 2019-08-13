/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRendererUtil;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * Renderer for {@link org.eclipse.emf.ecp.view.spi.section.model.VSection VSection} without child items.
 *
 * @author jfaltermeier
 *
 */
public class SectionLeafSWTRenderer extends AbstractSectionSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param viewTemplateProvider the {@link VTViewTemplateProvider}
	 * @since 1.18
	 */
	@Inject
	public SectionLeafSWTRenderer(VSection vElement, ViewModelContext viewContext, ReportService reportService,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService, viewTemplateProvider);
	}

	private SWTGridDescription rendererGridDescription;

	@Override
	public SWTGridDescription getGridDescription(
		SWTGridDescription gridDescription) {
		/* +1 because of label */
		final int columns = getVElement().getChildren().size() + 1;
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE
				.createSimpleGrid(1, columns, this);
			final Optional<Integer> labelWidth = getLabelWidth();
			if (labelWidth.isPresent()) {
				rendererGridDescription.getGrid().get(0).setPreferredSize(labelWidth.get(), SWT.DEFAULT);
			}
		}
		return rendererGridDescription;
	}

	@Override
	protected Control createFirstColumn(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1)
			.extendedMargins(computeLeftMargin(), 0, 0, 0)
			.applyTo(composite);

		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(getVElement());
		final Label label = new Label(composite, getLabelStyleBits());

		final IObservableValue<String> modelLabelValue = EMFEditObservables.observeValue(
			editingDomain,
			getVElement(),
			VViewPackage.eINSTANCE.getElement_Label());

		final IObservableValue<String> textObservable = WidgetProperties.text().observe(label);

		getDataBindingContext().bindValue(textObservable, modelLabelValue);

		GridDataFactory.fillDefaults().grab(true, false).applyTo(label);

		final IObservableValue<String> modelTooltipValue = EMFEditObservables.observeValue(
			editingDomain,
			getVElement(),
			VViewPackage.eINSTANCE.getHasTooltip_Tooltip());
		final IObservableValue<String> targetTooltipValue = WidgetProperties.tooltipText().observe(label);
		getDataBindingContext().bindValue(targetTooltipValue, modelTooltipValue);

		return composite;
	}

	/**
	 * Returns the style bits that are set on the label in the first column.
	 *
	 * @return the style bits
	 *
	 * @since 1.18
	 */
	protected int getLabelStyleBits() {
		return AbstractControlSWTRendererUtil.getLabelStyleBits(getViewTemplateProvider(), getVElement(),
			getViewModelContext());
	}

	@Override
	protected void initCollapseState() {
		// no children -> empty
	}

}
