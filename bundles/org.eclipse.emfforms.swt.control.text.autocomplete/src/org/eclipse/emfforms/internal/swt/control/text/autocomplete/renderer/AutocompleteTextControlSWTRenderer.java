/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.control.text.autocomplete.renderer;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlJFaceViewerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.internal.swt.control.text.autocomplete.MessageKeys;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.control.text.autocomplete.viewservice.AutocompleteViewModelService;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * The text control renderer allows to enter text while given autocompletion proposals. Moreover it is possible to
 * select a proposed value from a combo box.
 *
 * @author jfaltermeier
 *
 */
public class AutocompleteTextControlSWTRenderer extends SimpleControlJFaceViewerSWTRenderer {

	/**
	 * Constructs a new {@link AutocompleteViewModelService}.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	@Inject
	public AutocompleteTextControlSWTRenderer(
		VControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlJFaceViewerSWTRenderer#createBindings(org.eclipse.jface.viewers.Viewer)
	 */
	@Override
	protected Binding[] createBindings(Viewer viewer) throws DatabindingFailedException {
		final ISWTObservableValue targetObservable = SWTObservables
			.observeText(ComboViewer.class.cast(viewer).getCombo());
		final IObservableValue modelObservable = getModelValue();
		final Binding binding = getDataBindingContext().bindValue(targetObservable, modelObservable);
		return new Binding[] { binding };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlJFaceViewerSWTRenderer#createJFaceViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Viewer createJFaceViewer(Composite parent) throws DatabindingFailedException {
		final ComboViewer combo = new ComboViewer(parent, SWT.DROP_DOWN);
		combo.setContentProvider(ArrayContentProvider.getInstance());
		combo.setInput(getProposals());
		new AutoCompleteField(
			combo.getCombo(),
			new ComboContentAdapter(),
			combo.getCombo().getItems());
		return combo;
	}

	/**
	 * Returns the list of proposals.
	 *
	 * @return the list
	 * @throws DatabindingFailedException in case the model value could not be created
	 */
	List<String> getProposals() throws DatabindingFailedException {
		final AutocompleteViewModelService service = getViewModelContext()
			.getService(AutocompleteViewModelService.class);
		if (service == null) {
			/* reported by #getService(Class) */
			return Collections.emptyList();
		}
		final IObservableValue modelValue = getModelValue();
		final EObject object = EObject.class.cast(IObserving.class.cast(modelValue).getObserved());
		final EAttribute attribute = EAttribute.class.cast(modelValue.getValueType());
		/* do not dispose the model value here, since future calls to #getModelValue will return a disposed observale */
		return service.getProposals(object, attribute);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return LocalizationServiceHelper.getString(getClass(), MessageKeys.AutocompleteTextControlSWTRenderer_Set);
	}

}
