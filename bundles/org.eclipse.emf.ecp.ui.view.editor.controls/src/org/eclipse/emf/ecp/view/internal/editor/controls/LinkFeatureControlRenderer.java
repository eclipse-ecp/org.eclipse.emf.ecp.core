/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.editor.controls.AbstractFilteredReferenceCommand;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Shell;

/**
 * SWT Renderer for linking a {@link EStructuralFeature domain model feature} to a
 * {@link VFeaturePathDomainModelReference}.
 */
public class LinkFeatureControlRenderer extends EditableEReferenceLabelControlSWTRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	@Inject
	public LinkFeatureControlRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@Override
	protected void linkValue(Shell shell) {
		final EObject eObject;
		try {
			eObject = getEObject();
		} catch (final DatabindingFailedException ex) {
			showLinkValueFailedMessageDialog(shell, ex);
			return;
		}
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });

		final FilteredReferenceCommand referenceCommand = new FilteredReferenceCommand(eObject, adapterFactory, shell);
		if (referenceCommand.canExecute()) {
			referenceCommand.execute();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Object getText(Object value) {
		final EObjectContainmentEList list = (EObjectContainmentEList) value;
		return super.getText(list.getEObject());
	}

	/**
	 * @return the domain model EObject
	 * @throws DatabindingFailedException if the
	 */
	private EObject getEObject() throws DatabindingFailedException {
		IObservableValue observableValue;
		observableValue = Activator.getDefault().getEMFFormsDatabinding()
			.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		return (EObject) ((IObserving) observableValue).getObserved();
	}

	/**
	 * The command for linking the reference.
	 */
	private class FilteredReferenceCommand extends AbstractFilteredReferenceCommand<EStructuralFeature> {

		FilteredReferenceCommand(final Notifier notifier, ComposedAdapterFactory composedAdapterFactory,
			Shell shell) {

			super(notifier, composedAdapterFactory, shell, getRootEClass(notifier),
				new ECPSelectionStatusValidator() {

					@Override
					public IStatus validate(Object[] selection) {

						if (selection.length != 0 && EStructuralFeature.class.isInstance(selection[0])) {
							final TreePath treePath = getTreePath();
							if (!Helper
								.hasFeaturePropertyDescriptor(EStructuralFeature.class.cast(selection[0])
									.getEContainingClass(), treePath)) {
								// FIXME Hack, for allowing the selection of EStructuralFeatures w/o property
								// descriptors. Should return error.
								return new Status(IStatus.WARNING,
									org.eclipse.emf.ecp.view.internal.editor.controls.Activator.PLUGIN_ID,
									"The selected " + EStructuralFeature.class.getSimpleName() //$NON-NLS-1$
										+ " has no PropertyDescriptor."); //$NON-NLS-1$
							}
							return Status.OK_STATUS;
						}
						return new Status(IStatus.ERROR,
							org.eclipse.emf.ecp.view.internal.editor.controls.Activator.PLUGIN_ID,
							"This is not an " + EStructuralFeature.class.getSimpleName() + "."); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}, false);
		}

		@Override
		protected void setSelectedValues(EStructuralFeature selectedFeature, List<EReference> bottomUpPath) {
			try {
				// Transform the selected path to DMR segments and add them to the dmr.
				final VDomainModelReference modelReference = (VDomainModelReference) getEObject();
				if (!modelReference.getSegments().isEmpty()) {
					modelReference.getSegments().clear();
				}
				for (final EReference eReference : bottomUpPath) {
					modelReference.getSegments().add(createDMRSegment(eReference));
				}
				modelReference.getSegments().add(createDMRSegment(selectedFeature));
			} catch (final DatabindingFailedException ex) {
				Activator.getDefault().getReportService().report(new AbstractReport(ex));
			}
		}

		/**
		 * Creates a {@link VDomainModelReferenceSegment} for the given {@link EStructuralFeature}.
		 *
		 * @param structuralFeature The {@link EStructuralFeature} that defines the path part represented by the created
		 *            segment
		 * @return The created {@link VDomainModelReference}
		 */
		private VDomainModelReferenceSegment createDMRSegment(final EStructuralFeature structuralFeature) {
			final VFeatureDomainModelReferenceSegment pathSegment = VViewFactory.eINSTANCE
				.createFeatureDomainModelReferenceSegment();
			pathSegment.setDomainModelFeature(structuralFeature.getName());
			return pathSegment;
		}

	}

	/**
	 * Allows to retrieve the root eclass necessary to select the {@link VFeaturePathDomainModelReference}.
	 *
	 * @param notifier The {@link Notifier} triggering the selection
	 * @return The {@link EClass} that should be used as root
	 */
	protected EClass getRootEClass(Notifier notifier) {
		return Helper.getRootEClass((EObject) notifier);
	}

}
