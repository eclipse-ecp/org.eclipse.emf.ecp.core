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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlJFaceViewerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Renderer Enums.
 *
 * @author Eugen Neufeld
 *
 */
public class EnumComboViewerSWTRenderer extends SimpleControlJFaceViewerSWTRenderer {

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
	 */
	public EnumComboViewerSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.emfFormsEditSupport = emfFormsEditSupport;
	}

	@Override
	protected Binding[] createBindings(Viewer viewer) throws DatabindingFailedException {
		final Binding binding = getDataBindingContext().bindValue(ViewersObservables.observeSingleSelection(viewer),
			getModelValue());
		final Binding tooltipBinding = getDataBindingContext().bindValue(
			SWTObservables.observeTooltipText(viewer.getControl()),
			getModelValue());
		return new Binding[] { binding, tooltipBinding };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlJFaceViewerSWTRenderer#createJFaceViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Viewer createJFaceViewer(Composite parent) throws DatabindingFailedException {
		final IValueProperty valueProperty = getEMFFormsDatabinding()
			.getValueProperty(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel().eClass());
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final ComboViewer combo = new ComboViewer(parent);
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				return getEMFFormsEditSupport()
					.getText(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel(), element);
			}

		});
		final List<Object> inputValues = new ArrayList<Object>();
		for (final EEnumLiteral literal : EEnum.class.cast(structuralFeature.getEType()).getELiterals()) {
			inputValues.add(literal.getInstance());
		}
		combo.setInput(inputValues);
		combo.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_enum"); //$NON-NLS-1$
		return combo;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return LocalizationServiceHelper
			.getString(getClass(), MessageKeys.EEnumControl_NoValueSetClickToSetValue);
	}

	private EMFFormsEditSupport getEMFFormsEditSupport() {
		return emfFormsEditSupport;
	}

}
