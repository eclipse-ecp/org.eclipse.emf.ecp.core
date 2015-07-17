/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.ide.view.internal.service.IDEViewModelRegistryImpl;
import org.eclipse.emf.ecp.ide.view.service.IDEViewModelRegistry;
import org.eclipse.emf.ecp.view.spi.editor.controls.AbstractFilteredReferenceAction;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.osgi.framework.ServiceReference;

/**
 * This control is used to select the root {@link EClass} of a {@link VView}.
 *
 * @author Eugen Neufeld
 *
 */
public class ControlRootEClassControl extends LinkControl {

	@Override
	protected int getNumButtons() {
		return 2;
	}

	@Override
	protected Button[] createButtons(Composite composite) {
		final Button[] buttons = new Button[2];
		final Setting setting = getFirstSetting();
		buttons[0] = createButtonForAction(new DeleteReferenceAction(getEditingDomain(setting), setting,
			getService(ReferenceService.class)), composite);
		buttons[1] = createButtonForAction(new FilteredReferenceAction(getEditingDomain(setting), setting,
			getItemPropertyDescriptor(setting), composite.getShell()), composite);
		return buttons;
	}

	private class FilteredReferenceAction extends AbstractFilteredReferenceAction {

		public FilteredReferenceAction(EditingDomain editingDomain, Setting setting,
			IItemPropertyDescriptor descriptor, Shell shell) {
			super(editingDomain, setting, descriptor, shell);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			getEditingDomain().getCommandStack()
				.execute(new FilteredReferenceCommand(getSetting().getEObject(), getShell()));
		}
	}

	private class FilteredReferenceCommand extends ChangeCommand {

		private final Shell shell;

		public FilteredReferenceCommand(Notifier notifier, Shell shell) {
			super(notifier);
			this.shell = shell;
		}

		@Override
		protected void doExecute() {

			final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(
				getComposedAdapterFactory());
			final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, labelProvider,
				getContentProvider());
			dialog.setAllowMultiple(false);
			dialog.setValidator(new ISelectionStatusValidator() {

				@Override
				public IStatus validate(Object[] selection) {
					if (selection.length != 0 && EClass.class.isInstance(selection[0])) {

						return Status.OK_STATUS;
					}
					return new Status(IStatus.ERROR,
						org.eclipse.emf.ecp.view.internal.editor.controls.Activator.PLUGIN_ID, "This is not an EClass."); //$NON-NLS-1$
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
					final VView view = (VView) getFirstSetting().getEObject();

					if (view.getRootEClass() != null) {
						getViewModelRegistry().unregister(
							view.getRootEClass().eResource().getURI().toString(), view);
					}

					view.setRootEClass(selectedFeature);
					getViewModelRegistry().register(view.getRootEClass().eResource().getURI().toString(), view);

					final ResourceSet resourceSet = new ResourceSetImpl();
					final Map<String, Object> map =
						resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
					map.put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$

					final ResourceSet rs = selectedFeature.eContainer().eResource().getResourceSet();
					URI uri = null;
					for (final Resource r : rs.getResources()) {
						if (r.getURI().isPlatform()) {
							uri = r.getURI();
						}
					}

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

					view.setEcorePath(ecore.getURI().toPlatformString(true));

				}
			}
			labelProvider.dispose();
		}

		/**
		 * Return the {@link IDEViewModelRegistry}.
		 *
		 * @return the {@link IDEViewModelRegistry}
		 */
		public IDEViewModelRegistry getViewModelRegistry() {
			final ServiceReference<IDEViewModelRegistry> serviceReference = Activator.getDefault().getBundle()
				.getBundleContext().getServiceReference(IDEViewModelRegistry.class);
			if (serviceReference == null) {
				return null;
			}
			return Activator.getDefault().getBundle().getBundleContext().getService(serviceReference);
		}

		private ITreeContentProvider getContentProvider() {
			return new ITreeContentProvider() {

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
			};
		}

	}

	/**
	 * @return an instance of the {@link org.eclipse.emf.ecore.EPackage.Registry}
	 */
	protected Object getInput() {
		return Registry.INSTANCE;
	}

}
