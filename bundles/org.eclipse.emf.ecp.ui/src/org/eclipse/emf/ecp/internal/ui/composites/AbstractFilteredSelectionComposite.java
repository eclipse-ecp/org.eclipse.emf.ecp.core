/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.internal.ui.composites;

import org.eclipse.emf.ecp.internal.ui.ECPViewerFilter;
import org.eclipse.emf.ecp.internal.ui.messages.Messages;
import org.eclipse.emf.ecp.ui.common.CompositeProvider;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This {@link CompositeProvider} provides Composite containing a {@link Text} widget and a viewer.
 * The contents of the viewer can be filtered by typing a text into the Text widget.
 * 
 * @author Eugen Neufeld
 * 
 * @param <T> the type of the Viewer. This must extend a {@link StructuredViewer}
 */
public abstract class AbstractFilteredSelectionComposite<T extends StructuredViewer> implements CompositeProvider {

	private T viewer;

	private Object[] selection;

	/**
	 * Default Constructor.
	 */
	public AbstractFilteredSelectionComposite() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public Composite createUI(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);

		Label filterLabel = new Label(composite, SWT.LEFT);
		filterLabel.setText(Messages.AbstractModelElementHelper_FilterLabel);
		final Text filterInput = new Text(composite, SWT.SEARCH);
		filterInput.setMessage(Messages.AbstractModelElementHelper_FilterText);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(filterInput);

		viewer = createViewer(composite);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).minSize(0, 150).span(2, 1)
			.applyTo(viewer.getControl());

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				selection = ((IStructuredSelection) viewer.getSelection()).toArray();
			}
		});
		viewer.addFilter(getFilter());

		filterInput.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = filterInput.getText();
				getFilter().setSearchTerm(text);
				expandViewer();
				if (text != null && text.length() == 0) {
					collapsViewer();
				}
				viewer.refresh();
			}
		});

		return composite;
	}

	/**
	 * Subclasses can redefine the expand behavior of the viewer.
	 */
	protected void expandViewer() {

	}

	/**
	 * Subclasses can redefine the collaps behavior of the viewer.
	 */
	protected void collapsViewer() {
	}

	/**
	 * Returns the used {@link StructuredViewer}.
	 * 
	 * @return the viewer
	 */
	public T getViewer() {
		return viewer;
	}

	/**
	 * Returns the selected objects.
	 * 
	 * @return the selection
	 */
	public Object[] getSelection() {
		return selection;
	}

	/**
	 * Creates a {@link StructuredViewer} on top of the provided {@link Composite}. The result is the created
	 * {@link StructuredViewer}.
	 * 
	 * @param composite the {@link Composite} to create the viewer on
	 * @return the created {@link StructuredViewer}
	 */
	protected abstract T createViewer(Composite composite);

	/**
	 * Returns a Filter to use.
	 * 
	 * @return the {@link ECPViewerFilter} to use
	 */
	protected abstract ECPViewerFilter getFilter();
}
