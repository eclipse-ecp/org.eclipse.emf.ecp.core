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
package org.eclipse.emf.ecp.view.editor.controls;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
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
			getItemPropertyDescriptor(setting), getService(ReferenceService.class)), composite);
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

			final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(getComposedAdapterFactory());
			final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, labelProvider,
				getContentProvider());
			dialog.setAllowMultiple(false);
			dialog.setValidator(new ISelectionStatusValidator() {

				public IStatus validate(Object[] selection) {
					if (selection.length != 0 && EClass.class.isInstance(selection[0])) {

						return Status.OK_STATUS;
					}
					return new Status(IStatus.ERROR, org.eclipse.emf.ecp.view.editor.controls.Activator.PLUGIN_ID,
						"This is not an EClass.");
				}
			});
			dialog.setInput(getInput());
			dialog.setMessage("Select an EClass.");
			dialog.setTitle("Select an EClass.");
			dialog.setComparator(new ViewerComparator());
			final int result = dialog.open();
			if (Window.OK == result) {
				final Object selection = dialog.getFirstResult();
				if (EClass.class.isInstance(selection)) {
					final EClass selectedFeature = (EClass) selection;

					((VView) getFirstSetting().getEObject()).setRootEClass(selectedFeature);
				}
			}
			labelProvider.dispose();
		}

		private ITreeContentProvider getContentProvider() {
			return new ITreeContentProvider() {

				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					// TODO Auto-generated method stub

				}

				public void dispose() {
					// TODO Auto-generated method stub

				}

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

				public Object getParent(Object element) {
					if (EClass.class.isInstance(element)) {
						return ((EClass) element).eContainer();
					}
					return null;
				}

				public Object[] getElements(Object inputElement) {
					return getChildren(inputElement);
				}

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
	 * @return
	 */
	protected Object getInput() {
		return Registry.INSTANCE;
	}

}
