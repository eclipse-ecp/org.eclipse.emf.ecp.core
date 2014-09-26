/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui.composites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecp.internal.common.ui.Messages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This class provides a list with {@link org.eclipse.emf.ecore.EStructuralFeature EStructuralFeature}s that can be
 * selected with a checkbox.
 *
 * @author jfaltermeier
 *
 */
public class CheckedEStructuralFeatureCompositeImpl extends SelectModelElementCompositeImpl implements
CheckedEStructuralFeatureComposite {

	private final Object[] input;
	private FilteredViewerContentProvider provider;
	private final Map<Object, Boolean> objectToCheckedMap;

	/**
	 * Constructor.
	 *
	 * @param input the input for the viewer
	 */
	public CheckedEStructuralFeatureCompositeImpl(Object input) {
		super(input, true);
		this.input = (Object[]) input;
		objectToCheckedMap = new LinkedHashMap<Object, Boolean>();
	}

	/**
	 * {@inheritDoc}.
	 *
	 * @see org.eclipse.emf.ecp.spi.common.ui.composites.AbstractFilteredSelectionComposite#createUI(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Composite createUI(Composite parent) {
		final Composite composite = super.createUI(parent);
		final Composite buttons = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(buttons);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).span(2, 1).applyTo(buttons);

		final Button buttonAll = new Button(buttons, SWT.PUSH);
		buttonAll.setText(Messages.CheckedModelElementHelper_SelectAllLabel);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(buttonAll);
		buttonAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				getViewer().setCheckedElements(input);
				provider.setCheckStateForAll(true);
			}
		});

		final Button buttonNone = new Button(buttons, SWT.PUSH);
		buttonNone.setText(Messages.CheckedModelElementHelper_DeselectAllLabel);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(buttonNone);
		buttonNone.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				getViewer().setCheckedElements(new Object[0]);
				provider.setCheckStateForAll(false);
			}
		});

		return composite;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.common.ui.composites.SelectModelElementCompositeImpl#createViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected CheckboxTableViewer createViewer(Composite composite) {
		final CheckboxTableViewer tableViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER | SWT.MULTI
			| SWT.FULL_SELECTION);
		tableViewer.setLabelProvider(getLabelProvider());
		provider = new FilteredViewerContentProvider(tableViewer);
		tableViewer.setContentProvider(provider);
		tableViewer.setCheckStateProvider(provider);
		tableViewer.setInput(getInput());
		return tableViewer;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.common.ui.composites.AbstractFilteredSelectionComposite#getViewer()
	 */
	@Override
	public CheckboxTableViewer getViewer() {
		return (CheckboxTableViewer) super.getViewer();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.common.ui.composites.AbstractFilteredSelectionComposite#getSelection()
	 */
	@Override
	public Object[] getSelection() {
		final List<Object> result = new ArrayList<Object>();
		for (int i = 0; i < input.length; i++) {
			final Object cur = input[i];
			if (objectToCheckedMap.containsKey(cur) && objectToCheckedMap.get(cur)) {
				result.add(cur);
			}
		}
		return result.toArray();
	}

	/**
	 * {@link IStructuredContentProvider} that also handles the checked state of a viewer with a filter.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class FilteredViewerContentProvider implements IStructuredContentProvider, ICheckStateProvider {

		public FilteredViewerContentProvider(CheckboxTableViewer viewer) {
			viewer.addCheckStateListener(new ICheckStateListener() {
				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					objectToCheckedMap.put(event.getElement(), event.getChecked());
				}
			});
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.ICheckStateProvider#isChecked(java.lang.Object)
		 */
		@Override
		public boolean isChecked(Object element) {
			return getCheckedState(element);
		}

		private boolean getCheckedState(Object element) {
			if (!objectToCheckedMap.containsKey(element)) {
				objectToCheckedMap.put(element, Boolean.FALSE);
			}
			return objectToCheckedMap.get(element);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.ICheckStateProvider#isGrayed(java.lang.Object)
		 */
		@Override
		public boolean isGrayed(Object element) {
			return false;
		}

		public void setCheckStateForAll(boolean state) {
			final Set<Object> keySet = objectToCheckedMap.keySet();
			for (final Object o : keySet) {
				objectToCheckedMap.put(o, state);
			}
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
			// nothing to dispose
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// ignore until actually needed =)
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		@Override
		@SuppressWarnings("rawtypes")
		public Object[] getElements(Object inputElement) {
			// Copied from ArrayContentProvider since its illegal to extend it
			if (inputElement instanceof Object[]) {
				return (Object[]) inputElement;
			}
			if (inputElement instanceof Collection) {
				return ((Collection) inputElement).toArray();
			}
			return new Object[0];
		}

	}

}
