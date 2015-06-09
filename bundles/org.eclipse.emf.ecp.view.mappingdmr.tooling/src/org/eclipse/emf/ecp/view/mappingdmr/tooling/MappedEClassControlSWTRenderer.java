/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.mappingdmr.tooling;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * Control for a {@link VMappingDomainModelReference}.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class MappedEClassControlSWTRenderer extends
	EditableEReferenceLabelControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	public MappedEClassControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * @author Eugen
	 *
	 */
	private static final class EClassTreeContentProvider implements ITreeContentProvider {
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean hasChildren(Object element) {
			if (EPackage.class.isInstance(element)) {
				return true;
			}
			if (Descriptor.class.isInstance(element)) {
				final Descriptor descriptor = (Descriptor) element;
				descriptor.getEPackage();
				return true;
			}
			return false;
		}

		@Override
		public Object getParent(Object element) {
			if (EClass.class.isInstance(element)) {
				return ((EClass) element).eContainer();
			}
			return null;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (EPackage.class.isInstance(parentElement)) {
				final EPackage ePackage = (EPackage) parentElement;
				final Set<EClass> result = new LinkedHashSet<EClass>();
				for (final EClassifier classifier : ePackage.getEClassifiers()) {
					if (EClass.class.isInstance(classifier)) {
						result.add((EClass) classifier);
					}
				}
				return result.toArray();
			}
			if (Descriptor.class.isInstance(parentElement)) {
				return getChildren(((Descriptor) parentElement).getEPackage());
			}
			if (Registry.class.isInstance(parentElement)) {
				return ((Registry) parentElement).values().toArray();
			}
			if (Collection.class.isInstance(parentElement)) {
				return Collection.class.cast(parentElement).toArray();
			}
			return null;
		}
	}

	@Override
	protected void linkValue(Shell shell) {
		IObservableValue observableValue;
		try {
			observableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			showLinkValueFailedMessageDialog(shell, ex);
			return;
		}
		final VMappingDomainModelReference dmr = (VMappingDomainModelReference) ((IObserving) observableValue)
			.getObserved();
		observableValue.dispose();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(
			adapterFactory);
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, labelProvider,
			getContentProvider());
		dialog.setAllowMultiple(false);
		dialog.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length != 0 && EClass.class.isInstance(selection[0])) {

					return Status.OK_STATUS;
				}
				return new Status(IStatus.ERROR, org.eclipse.emf.ecp.view.internal.editor.controls.Activator.PLUGIN_ID,
					"This is not an EClass."); //$NON-NLS-1$
			}
		});
		dialog.setInput(getInput(dmr));
		dialog.setMessage("Select an EClass."); //$NON-NLS-1$
		dialog.setTitle("Select an EClass."); //$NON-NLS-1$
		dialog.setComparator(new ViewerComparator());
		final int result = dialog.open();
		if (Window.OK == result) {
			final Object selection = dialog.getFirstResult();
			if (EClass.class.isInstance(selection)) {
				final EClass selectedFeature = (EClass) selection;

				dmr.setMappedClass(selectedFeature);

			}
		}
		labelProvider.dispose();
	}

	private ITreeContentProvider getContentProvider() {
		return new EClassTreeContentProvider();
	}

	/**
	 * @return an instance of the {@link org.eclipse.emf.ecore.EPackage.Registry}
	 */
	private Object getInput(VMappingDomainModelReference dmr) {
		final EClass referenceMap = EReference.class.cast(dmr.getDomainModelEFeature()).getEReferenceType();

		final EReference valueReference = EReference.class.cast(referenceMap.getEStructuralFeature("value")); //$NON-NLS-1$
		return EMFUtils.getSubClasses(valueReference.getEReferenceType());
		// return Registry.INSTANCE;
	}

}
