/**
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.internal.ide.view.mappingsegment;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.internal.editor.controls.EditableEReferenceLabelControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.editor.controls.SelectedFeatureViewService;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * Control for the <code>mappedClass</code> feature of {@link VMappingDomainModelReferenceSegment}.
 *
 * @author Lucas Koehler
 *
 */
public class MappedEClassControlSWTRenderer extends EditableEReferenceLabelControlSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	@Inject
	public MappedEClassControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * Content provider which calculates an EClass's children based on the given list of EClasses. The children of an
	 * EClass are all EClasses which have the EClass as a direct super type.
	 */
	private static final class EClassTreeContentProvider implements ITreeContentProvider {
		private final Collection<EClass> subClasses;

		EClassTreeContentProvider(Collection<EClass> subClasses) {
			this.subClasses = subClasses;
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// Do nothing
		}

		@Override
		public void dispose() {
			// Do nothing
		}

		@Override
		public boolean hasChildren(Object element) {
			if (EClass.class.isInstance(element)) {
				final Object[] children = getChildren(element);
				return children != null && children.length > 0;
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
			if (EClass.class.isInstance(parentElement)) {
				final EClass eClass = (EClass) parentElement;
				final List<EClass> directSubClasses = subClasses.stream()
					.filter(c -> c.getESuperTypes().contains(eClass))
					.collect(Collectors.toList());
				return directSubClasses.toArray();
			}
			if (Collection.class.isInstance(parentElement)) {
				return Collection.class.cast(parentElement).toArray();
			}
			return null;
		}
	}

	@Override
	protected void linkValue(Shell shell) {
		IObservableValue<?> observableValue;
		try {
			observableValue = getEMFFormsDatabinding()
				.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			showLinkValueFailedMessageDialog(shell, ex);
			return;
		}
		final VMappingDomainModelReferenceSegment mappingSegment = (VMappingDomainModelReferenceSegment) ((IObserving) observableValue)
			.getObserved();
		observableValue.dispose();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(
			adapterFactory);

		final Optional<EClass> valueEClass = getValueEClass(mappingSegment);
		final Set<EClass> input = valueEClass.map(Collections::singleton).orElseGet(Collections::emptySet);
		final Collection<EClass> subClasses = valueEClass.map(EMFUtils::getSubClasses).orElseGet(Collections::emptySet);
		final EClassTreeContentProvider contentProvider = new EClassTreeContentProvider(subClasses);
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, labelProvider, contentProvider);
		dialog.setAllowMultiple(false);
		dialog.setValidator(selection -> {
			if (selection.length != 0 && EClass.class.isInstance(selection[0])) {

				return Status.OK_STATUS;
			}
			return new Status(IStatus.ERROR, "org.eclipse.emfforms.ide.view.mappingsegment", //$NON-NLS-1$
				"This is not an EClass."); //$NON-NLS-1$
		});

		dialog.setInput(input);
		dialog.setMessage("Select an EClass."); //$NON-NLS-1$
		dialog.setTitle("Select an EClass"); //$NON-NLS-1$
		dialog.setComparator(new ViewerComparator());
		final int result = dialog.open();
		if (Window.OK == result) {
			final Object selection = dialog.getFirstResult();
			if (EClass.class.isInstance(selection)) {
				final EClass selectedFeature = (EClass) selection;
				mappingSegment.setMappedClass(selectedFeature);
			}
		}
		labelProvider.dispose();
	}

	/**
	 * @return a collection of the selectable {@link org.eclipse.emf.ecore.EClass EClasses}.
	 *         The selectable EClasses are all subclasses of the map's values' class.
	 */
	private Optional<EClass> getValueEClass(VMappingDomainModelReferenceSegment mappingSegment) {
		// get map type from a view model service set by the advanced dmr creation wizard
		final SelectedFeatureViewService service = getViewModelContext().getService(SelectedFeatureViewService.class);
		if (service == null) {
			return Optional.empty();
		}
		final EStructuralFeature mapFeature = service.getFeature();
		if (!EReference.class.isInstance(mapFeature)) {
			return Optional.empty();
		}
		final EClass referenceMap = EReference.class.cast(mapFeature).getEReferenceType();
		final EStructuralFeature valueFeature = referenceMap.getEStructuralFeature("value"); //$NON-NLS-1$
		if (!EReference.class.isInstance(valueFeature)) {
			return Optional.empty();
		}
		final EReference valueReference = EReference.class.cast(valueFeature);

		return Optional.ofNullable(valueReference.getEReferenceType());
	}

}
