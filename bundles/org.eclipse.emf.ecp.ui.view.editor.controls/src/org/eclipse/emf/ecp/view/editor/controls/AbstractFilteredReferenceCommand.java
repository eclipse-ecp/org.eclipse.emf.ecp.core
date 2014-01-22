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
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.view.editor.controls;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * A ReferenceCommand allowing to select an {@link EStructuralFeature} using a dialog.
 * 
 * @author Eugen Neufeld
 * 
 * @param <T> type of the {@link EStructuralFeature} which can be selected
 */
public abstract class AbstractFilteredReferenceCommand<T extends EStructuralFeature> extends ChangeCommand {

	private final Shell shell;
	private final ComposedAdapterFactory composedAdapterFactory;
	private final EClass rootClass;
	private final ISelectionStatusValidator validator;
	private final boolean allowMultiReferences;

	/**
	 * Constructor for the AbstractFilteredReferenceCommand.
	 * 
	 * @param notifier the Notifier for the {@link ChangeCommand}
	 * @param composedAdapterFactory the {@link ComposedAdapterFactory} for the LabelProvider
	 * @param shell the {@link Shell} to use in the dialog
	 * @param rootClass the {@link EClass} which is the root of the view
	 * @param validator the {@link ISelectionStatusValidator} to use when a selection was done
	 * @param allowMultiReferences whether multi references are allowed during the selection
	 */
	public AbstractFilteredReferenceCommand(Notifier notifier, ComposedAdapterFactory composedAdapterFactory,
		Shell shell, EClass rootClass, ISelectionStatusValidator validator, boolean allowMultiReferences) {
		super(notifier);
		this.shell = shell;
		this.composedAdapterFactory = composedAdapterFactory;

		this.rootClass = rootClass;
		this.validator = validator;
		this.allowMultiReferences = allowMultiReferences;
	}

	private Class<?> returnedClass() {
		final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<?>) parameterizedType.getActualTypeArguments()[0];
	}

	@Override
	protected void doExecute() {
		if (rootClass == null) {
			return;
		}
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
		final ECPViewEditorTreeSelectionDialog dialog = new ECPViewEditorTreeSelectionDialog(shell, labelProvider,
			getContentProvider(rootClass));
		dialog.setAllowMultiple(false);
		dialog.setValidator(validator);
		dialog.setInput(rootClass);
		dialog.setMessage("Select a " + returnedClass().getSimpleName());
		dialog.setTitle("Select a " + returnedClass().getSimpleName());
		final int result = dialog.open();
		if (Window.OK == result) {
			final Object selection = dialog.getFirstResult();
			if (returnedClass().isInstance(selection)) {
				final T selectedFeature = (T) selection;

				final List<EReference> bottomUpPath = new ArrayList<EReference>();
				final TreePath path = dialog.getTreePath();

				for (int i = 0; i < path.getSegmentCount() - 1; i++) {
					final Object o = path.getSegment(i);
					if (EReference.class.isInstance(o)) {
						bottomUpPath.add((EReference) o);
					}
				}

				setSelectedValues(selectedFeature, bottomUpPath);
			}
		}
		labelProvider.dispose();
	}

	/**
	 * Template method which is called so that the selected element can be saved.
	 * 
	 * @param selectedFeature the {@link EStructuralFeature} to set
	 * @param bottomUpPath the path to the feature
	 */
	protected abstract void setSelectedValues(T selectedFeature, List<EReference> bottomUpPath);

	private ITreeContentProvider getContentProvider(EClass rootClass) {
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
				if (EClass.class.isInstance(element)) {
					final EClass eClass = (EClass) element;
					final boolean hasReferences = !eClass.getEAllReferences().isEmpty();
					final boolean hasAttributes = !eClass.getEAllAttributes().isEmpty();
					return hasReferences || hasAttributes;

				}
				if (EReference.class.isInstance(element)) {
					final EReference eReference = (EReference) element;

					return eReference.isMany() && !allowMultiReferences ? false : hasChildren(eReference
						.getEReferenceType());
				}
				return false;
			}

			public Object getParent(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

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

	/**
	 * ElementTreeSelectionDialog which also stores the selection along the tree.
	 * 
	 * @author Eugen Neufeld
	 * 
	 */
	private class ECPViewEditorTreeSelectionDialog extends ElementTreeSelectionDialog {

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
			return treePath;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.dialogs.ElementTreeSelectionDialog#computeResult()
		 */
		@Override
		protected void computeResult() {
			treePath = ((TreeSelection) getTreeViewer().getSelection()).getPaths()[0];

			super.computeResult();
		}

	}

}
