/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.ide.view.internal.service.IDEViewModelRegistryImpl;
import org.eclipse.emf.ecp.ide.view.service.IDEViewModelRegistry;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.osgi.framework.ServiceReference;

/**
 * @author Eugen Neufeld
 *
 */
public class ControlRootEClassControlChangeableSWTRenderer extends ControlRootEClassControl2SWTRenderer {

	/**
	 * @author jonas
	 *
	 */
	private final class EClassContentProvider implements ITreeContentProvider {
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
			return null;
		}
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	@Inject
	public ControlRootEClassControlChangeableSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider, VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ControlRootEClassControl2SWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createSWTControl(final Composite parent2) {
		final Composite composite = (Composite) super.createSWTControl(parent2);

		GridLayoutFactory.fillDefaults().numColumns(2).spacing(0, 0).equalWidth(false).applyTo(composite);

		final Button selectClass = new Button(composite, SWT.PUSH);
		selectClass.setText("Link Root EClass"); //$NON-NLS-1$
		selectClass.setToolTipText("Link Root EClass"); //$NON-NLS-1$
		selectClass.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				selectAndSetEClass(parent2.getShell());
				composite.layout(true, true);
			}

		});

		return composite;
	}

	private void selectAndSetEClass(Shell shell) {
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
		dialog.setInput(getInput());
		dialog.setMessage("Select an EClass."); //$NON-NLS-1$
		dialog.setTitle("Select an EClass."); //$NON-NLS-1$
		dialog.setComparator(new ViewerComparator());
		final int result = dialog.open();
		if (Window.OK == result) {
			final Object selection = dialog.getFirstResult();
			if (EClass.class.isInstance(selection)) {
				final EClass selectedFeature = (EClass) selection;
				final VView view = (VView) getViewModelContext().getDomainModel();

				if (view.getRootEClass() != null) {
					getViewModelRegistry().unregister(
						view.getRootEClass().eResource().getURI().toString(), view);
				}

				view.setRootEClass(selectedFeature);
				getViewModelRegistry().register(view.getRootEClass().eResource().getURI().toString(), view);

				final ResourceSet rs = selectedFeature.eResource().getResourceSet();
				// we have a ResourceSet if the eclass was loaded from a workspace model
				loadWorkspaceEcore(view, rs);
			}
		}
		labelProvider.dispose();
	}

	private void loadWorkspaceEcore(final VView view, final ResourceSet rs) {
		if (rs == null) {
			return;
		}
		URI uri = null;
		for (final Resource r : rs.getResources()) {
			if (r.getURI().isPlatform()) {
				uri = r.getURI();
			}
		}

		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> map = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
		map.put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$
		final Resource ecore = resourceSet.getResource(uri, true);

		if (ecore == null) {
			return;
		}
		// put the file in the registry
		final EList<EObject> contents = ecore.getContents();
		if (contents.size() != 1) {
			return;
		}

		final EObject object = contents.get(0);
		if (!(object instanceof EPackage)) {
			return;
		}

		// Update the VView-EClass mapping
		final IDEViewModelRegistryImpl registry = (IDEViewModelRegistryImpl) getViewModelRegistry();
		if (registry == null) {
			return;
		}

		final String uriString = ecore.getURI().toPlatformString(true);
		if (!view.getEcorePaths().contains(uriString)) {
			view.getEcorePaths().add(uriString);
		}

	}

	private IDEViewModelRegistry getViewModelRegistry() {
		final ServiceReference<IDEViewModelRegistry> serviceReference = Activator.getDefault().getBundle()
			.getBundleContext().getServiceReference(IDEViewModelRegistry.class);
		if (serviceReference == null) {
			return null;
		}
		return Activator.getDefault().getBundle().getBundleContext().getService(serviceReference);
	}

	private ITreeContentProvider getContentProvider() {
		return new EClassContentProvider();
	}

	/**
	 * @return an instance of the {@link org.eclipse.emf.ecore.EPackage.Registry}
	 */
	private Object getInput() {
		return Registry.INSTANCE;
	}

}
