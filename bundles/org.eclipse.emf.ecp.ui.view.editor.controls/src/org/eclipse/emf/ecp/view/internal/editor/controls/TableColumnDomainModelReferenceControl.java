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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.view.spi.editor.controls.AbstractFilteredReferenceAction;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class TableColumnDomainModelReferenceControl extends LinkControl {

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
		buttons[1] = createButtonForAction(new FilteredReferenceAction(
			getEditingDomain(setting), setting,
			getItemPropertyDescriptor(setting), composite.getShell()), composite);
		return buttons;
	}

	private class FilteredReferenceAction extends
		AbstractFilteredReferenceAction {

		public FilteredReferenceAction(EditingDomain editingDomain, Setting setting,
			IItemPropertyDescriptor descriptor, Shell shell) {
			super(editingDomain, setting, descriptor, shell);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			getEditingDomain()
				.getCommandStack()
				.execute(
					new FilteredReferenceCommand(
						getSetting().getEObject(),
						getShell()));
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
			final EReference eref = (EReference) ((VFeaturePathDomainModelReference) getFirstSetting()
				.getEObject()
				.eContainer()).getDomainModelEFeature();

			final ECPViewEditorTreeSelectionDialog dialog = new ECPViewEditorTreeSelectionDialog(shell, labelProvider,
				getContentProvider(eref.getEReferenceType()));
			dialog.setValidator(new ECPSelectionStatusValidator() {

				@Override
				public IStatus validate(Object[] selection) {

					if (selection.length != 0 && EStructuralFeature.class.isInstance(selection[0])) {
						final TreePath treePath = dialog.getTreePath();
						if (!EAttribute.class.isInstance(selection[0])) {
							return new Status(IStatus.ERROR,
								org.eclipse.emf.ecp.view.internal.editor.controls.Activator.PLUGIN_ID,
								"This is not an " + EAttribute.class.getSimpleName() + "."); //$NON-NLS-1$ //$NON-NLS-2$
						}
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
			});
			dialog.setInput(eref.getEReferenceType());
			dialog.setTitle("Select Attribute for TableColumn"); //$NON-NLS-1$

			final int result = dialog.open();
			if (Window.OK == result) {
				final Object selection = dialog.getResult()[0];
				if (EAttribute.class.isInstance(selection) || EReference.class.isInstance(selection)
					&& EReference.class.cast(selection).isContainment()) {
					final EStructuralFeature selectedFeature = (EStructuralFeature) selection;
					final VFeaturePathDomainModelReference dmr = (VFeaturePathDomainModelReference) getFirstSetting()
						.getEObject();
					dmr.setDomainModelEFeature(selectedFeature);
					final List<EReference> bottomUpPath = new ArrayList<EReference>();
					final TreePath path = dialog.getTreePath();

					for (int i = 0; i < path.getSegmentCount() - 1; i++) {
						final Object o = path.getSegment(i);
						if (EReference.class.isInstance(o)) {
							bottomUpPath.add((EReference) o);
						}
					}
					dmr.getDomainModelEReferencePath().clear();
					dmr.getDomainModelEReferencePath().addAll(bottomUpPath);
				}
			}
			labelProvider.dispose();
		}

		/**
		 * @param eReferenceType
		 * @return
		 */
		private ITreeContentProvider getContentProvider(EClass rootClass) {
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
					if (EClass.class.isInstance(element)) {
						final EClass eClass = (EClass) element;
						final boolean hasReferences = !eClass.getEAllReferences().isEmpty();
						final boolean hasAttributes = !eClass.getEAllAttributes().isEmpty();
						return hasReferences || hasAttributes;

					}
					if (EReference.class.isInstance(element)) {
						final EReference eReference = (EReference) element;
						if (!eReference.isContainment()) {
							return false;
						}
						return hasChildren(eReference.getEReferenceType());
					}
					return false;
				}

				@Override
				public Object getParent(Object element) {
					// TODO Auto-generated method stub
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
						final Set<Object> result = getElementsForEClass(eClass);
						return result.toArray();
					}
					if (EReference.class.isInstance(parentElement)) {
						final EReference eReference = (EReference) parentElement;
						final Set<Object> result = getElementsForEClass(eReference.getEReferenceType());
						return result.toArray();
					}
					if (EPackage.Registry.class.isInstance(parentElement)) {
						return EPackage.Registry.class.cast(parentElement).values().toArray();
					}
					if (EPackage.class.isInstance(parentElement)) {
						final Set<Object> children = new LinkedHashSet<Object>();
						children.addAll(EPackage.class.cast(parentElement).getESubpackages());
						children.addAll(EPackage.class.cast(parentElement).getEClassifiers());
						return children.toArray();
					}
					return null;
				}

				private Set<Object> getElementsForEClass(EClass eClass) {
					final Set<Object> result = new LinkedHashSet<Object>();
					if (eClass.isAbstract() || eClass.isInterface()) {
						// find eClasses which are not abstract
						for (final EClassifier eClassifier : eClass.getEPackage().getEClassifiers()) {
							if (eClass != eClassifier && EClass.class.isInstance(eClassifier)
								&& eClass.isSuperTypeOf((EClass) eClassifier)) {
								result.add(eClassifier);
							}
						}
					}
					else {
						result.addAll(eClass.getEAllReferences());
						result.addAll(eClass.getEAllAttributes());
					}
					return result;
				}
			};
		}
	}

	/**
	 * ElementTreeSelectionDialog which also stores the selection along the tree.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private static class ECPViewEditorTreeSelectionDialog extends ElementTreeSelectionDialog {

		private TreePath treePath;

		/**
		 * Default constructor.
		 *
		 * @param parent the {@link Shell} for creating the dialog
		 * @param labelProvider the {@link ILabelProvider} for the tree
		 * @param contentProvider the {@link ITreeContentProvider} for the tree
		 */
		public ECPViewEditorTreeSelectionDialog(Shell parent, ILabelProvider labelProvider,
			ITreeContentProvider contentProvider) {
			super(parent, labelProvider, contentProvider);
		}

		public TreePath getTreePath() {
			if (getTreeViewer() != null) {
				final TreeSelection selection = (TreeSelection) getTreeViewer().getSelection();
				if (!selection.isEmpty()) {
					treePath = selection.getPaths()[0];
				}
			}
			return treePath;
		}
	}
}
