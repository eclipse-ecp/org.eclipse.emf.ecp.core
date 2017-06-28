/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.group.swt;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.group.swt.GroupTextProperty;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;

/**
 * Renders a SWT group.
 *
 * @since 1.5
 *
 */
public class GroupSWTRenderer extends ContainerSWTRenderer<VGroup> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @since 1.6
	 */
	@Inject
	public GroupSWTRenderer(VGroup vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory, EMFFormsDatabinding emfFormsDatabinding) {
		super(vElement, viewContext, reportService, factory, emfFormsDatabinding);
		dbc = new EMFDataBindingContext();
	}

	private static final String CONTROL_GROUP = "org_eclipse_emf_ecp_ui_control_group"; //$NON-NLS-1$
	private final EMFDataBindingContext dbc;
	private SWTGridDescription rendererGridDescription;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getCustomVariant()
	 */
	@Override
	protected String getCustomVariant() {
		return CONTROL_GROUP;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getChildren()
	 */

	@Override
	protected Collection<VContainedElement> getChildren() {
		return getVElement().getChildren();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite getComposite(Composite parent) {
		final Group group = new Group(parent, SWT.TITLE);
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(getVElement());
		final IObservableValue modelLabelValue = EMFEditObservables.observeValue(
			editingDomain,
			getVElement(),
			VViewPackage.eINSTANCE.getElement_Label());
		final IObservableValue targetLabelValue = new GroupTextProperty().observe(group);
		// FIXME fixed with JFace-Databinding 4.5
		// final IObservableValue targetValue = SWTObservables.observeText(group);
		dbc.bindValue(targetLabelValue, modelLabelValue);

		final IObservableValue modelTooltipValue = EMFEditObservables.observeValue(
			editingDomain,
			getVElement(),
			VViewPackage.eINSTANCE.getHasTooltip_Tooltip());
		final ISWTObservableValue targetTooltipValue = WidgetProperties.tooltipText().observe(group);
		dbc.bindValue(targetTooltipValue, modelTooltipValue);

		return group;
	}

	@Override
	protected Layout getLayout(int numControls, boolean equalWidth) {
		return LayoutProviderHelper.getColumnLayout(numControls, equalWidth, new Point(5, 5));
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
			final SWTGridCell swtGridCell = rendererGridDescription.getGrid().get(0);
			swtGridCell.setVerticalFill(false);
			swtGridCell.setVerticalGrab(false);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		dbc.dispose();
		super.dispose();
	}

}
