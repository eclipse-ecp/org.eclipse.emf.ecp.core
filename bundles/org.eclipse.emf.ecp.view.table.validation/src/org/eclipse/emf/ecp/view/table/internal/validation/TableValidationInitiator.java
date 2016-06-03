/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.internal.validation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * The TableValidationInitiator searches for Tables with an Editing Panel, and creates the necessary ViewModelContexts.
 *
 * @author Eugen Neufeld
 *
 */
public class TableValidationInitiator implements GlobalViewModelService, EMFFormsContextListener {

	/**
	 * A Mapping Class for tables.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private static class TableContextMapping {
		/**
		 * Default Constructor.
		 *
		 * @param control The {@link VTableControl}
		 * @param context The {@link ViewModelContext}
		 */
		TableContextMapping(VTableControl control, ViewModelContext context) {
			this.control = control;
			this.context = context;
		}

		private final VTableControl control;
		private final ViewModelContext context;
	}

	private final Map<UniqueSetting, TableContextMapping> mapping = new LinkedHashMap<UniqueSetting, TableContextMapping>();
	private ViewModelContext context;

	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		context.registerDomainChangeListener(new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getRawNotification().isTouch() || mapping.isEmpty()) {
					return;
				}
				final TableContextMapping tableContextMapping = mapping.get(UniqueSetting.createSetting(
					notification.getNotifier(), notification.getStructuralFeature()));
				if (tableContextMapping == null) {
					return;
				}
				for (final EObject newValue : notification.getNewEObjects()) {
					try {
						tableContextMapping.context.getChildContext(newValue,
							tableContextMapping.control, getView(tableContextMapping.control));
					} catch (final DatabindingFailedException ex) {
						Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
					}
				}
			}
		});
		checkForTables(context);
		context.registerEMFFormsContextListener(this);
	}

	@SuppressWarnings("unchecked")
	private void checkForTables(ViewModelContext context) {
		final EObject viewRoot = context.getViewModel();
		final TreeIterator<EObject> eAllContents = viewRoot.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			if (VTableControl.class.isInstance(eObject)) {
				final VTableControl tableControl = VTableControl.class.cast(eObject);
				if (!isCorrectContainer(tableControl, viewRoot)) {
					continue;
				}
				if (tableControl.getDetailEditing() == DetailEditing.WITH_PANEL) {
					final VTableDomainModelReference tableDomainModelReference = (VTableDomainModelReference) tableControl
						.getDomainModelReference();
					final IObservableValue observableValue;
					try {
						if (tableDomainModelReference.getDomainModelReference() != null) {
							observableValue = Activator.getDefault().getEMFFormsDatabinding()
								.getObservableValue(tableDomainModelReference.getDomainModelReference(),
									context.getDomainModel());
						} else {
							observableValue = Activator.getDefault().getEMFFormsDatabinding()
								.getObservableValue(tableDomainModelReference, context.getDomainModel());
						}
					} catch (final DatabindingFailedException ex) {
						Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
						continue;
					}
					final IObserving observing = (IObserving) observableValue;
					final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
					final EObject observed = (EObject) observing.getObserved();
					observableValue.dispose();

					mapping.put(UniqueSetting.createSetting(observed, structuralFeature), new TableContextMapping(
						tableControl, context));
					final EList<EObject> tableContents = (EList<EObject>) observed.eGet(structuralFeature, true);
					for (final EObject tableEObject : tableContents) {
						try {
							final VView detailView = getView(tableControl);
							final ViewModelContext childContext = context.getChildContext(tableEObject, tableControl,
								detailView);
							childContext.addContextUser(this);
						} catch (final DatabindingFailedException ex) {
							Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
						}
					}

				}
			}
		}
	}

	private boolean isCorrectContainer(VTableControl tableControl, EObject root) {
		EObject current = tableControl.eContainer();
		while (!VView.class.isInstance(current) && current != root) {
			current = current.eContainer();
		}
		return current == root;
	}

	private VView getView(VTableControl tableControl) throws DatabindingFailedException {
		VView detailView = tableControl.getDetailView();
		if (detailView == null) {
			final VTableDomainModelReference tableDomainModelReference = (VTableDomainModelReference) tableControl
				.getDomainModelReference();
			final IValueProperty valueProperty;
			if (tableDomainModelReference.getDomainModelReference() != null) {
				valueProperty = Activator.getDefault().getEMFFormsDatabinding()
					.getValueProperty(tableDomainModelReference.getDomainModelReference(), context.getDomainModel());
			} else {
				valueProperty = Activator.getDefault().getEMFFormsDatabinding()
					.getValueProperty(tableDomainModelReference, context.getDomainModel());
			}
			final EReference reference = (EReference) valueProperty.getValueType();
			final VElement viewModel = context.getViewModel();
			final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(viewModel);
			detailView = ViewProviderHelper.getView(
				EcoreUtil.create(reference.getEReferenceType()),
				properties);
		}

		return detailView;
	}

	@Override
	public void dispose() {
		context.unregisterEMFFormsContextListener(this);
	}

	@Override
	public int getPriority() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService#childViewModelContextAdded(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void childViewModelContextAdded(ViewModelContext childContext) {
		checkForTables(childContext);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextAdded(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
		if (ViewModelContext.class.isInstance(childContext)) {
			checkForTables(ViewModelContext.class.cast(childContext));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextDisposed(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextDisposed(EMFFormsViewContext childContext) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextInitialised()
	 */
	@Override
	public void contextInitialised() {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextDispose()
	 */
	@Override
	public void contextDispose() {
		// intentionally left empty
	}

}
