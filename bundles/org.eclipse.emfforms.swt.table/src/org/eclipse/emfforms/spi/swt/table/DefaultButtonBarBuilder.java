/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emfforms.internal.swt.table.messages.MessageKeys;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Default implementation of the {@link ButtonBarBuilder}. Clients may extend this class.
 *
 * @author Johannes Faltermeier
 *
 */
public final class DefaultButtonBarBuilder implements ButtonBarBuilder {
	private NewElementCreator<Object, Button> creator;

	@Override
	public void fillButtonComposite(Composite buttonComposite, AbstractTableViewer viewer) {
		addLayoutToButtonComposite(buttonComposite);
		createAddButton(buttonComposite, viewer);
		createRemoveButton(buttonComposite, viewer);
	}

	/**
	 * Add a column grid layout to the composite.
	 *
	 * @param buttonComposite the button {@link Composite}
	 */
	protected void addLayoutToButtonComposite(Composite buttonComposite) {
		GridLayoutFactory.fillDefaults().numColumns(getNumberOfColumns()).equalWidth(false).applyTo(buttonComposite);
	}

	/**
	 * Called by {@link #addLayoutToButtonComposite(Composite)} to identify the number of columns.
	 *
	 * @return the number of columns
	 */
	protected int getNumberOfColumns() {
		return 2;
	}

	/**
	 * Creates the remove button.
	 *
	 * @param buttonComposite the parent
	 * @param viewer the viewer
	 */
	protected void createRemoveButton(Composite buttonComposite, final AbstractTableViewer viewer) {
		final Button removeButton = new Button(buttonComposite, SWT.PUSH);
		removeButton.setText(LocalizationServiceHelper.getString(DefaultButtonBarBuilder.class,
			MessageKeys.DefaultButtonBarBuilder_RemoveButtonText));
		removeButton.addSelectionListener(new SelectionAdapter() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Object input = viewer.getInput();
				if (Collection.class.isInstance(input)) {
					final Collection collection = Collection.class.cast(input);

					final IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
					if (selection == null || selection.getFirstElement() == null) {
						return;
					}

					final List deletionList = new ArrayList();
					final Iterator<?> iterator = selection.iterator();
					while (iterator.hasNext()) {
						deletionList.add(iterator.next());
					}

					collection.removeAll(deletionList);
				}

			}
		});
	}

	/**
	 * Creates the add button.
	 *
	 * @param buttonComposite the parent
	 * @param viewer the viewer
	 */
	protected void createAddButton(Composite buttonComposite, final AbstractTableViewer viewer) {
		final Button addButton = new Button(buttonComposite, SWT.PUSH);
		addButton.setText(LocalizationServiceHelper.getString(DefaultButtonBarBuilder.class,
			MessageKeys.DefaultButtonBarBuilder_AddButtonText));
		addButton.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Object input = viewer.getInput();
				if (Collection.class.isInstance(input)) {
					final Collection collection = Collection.class.cast(input);
					final Object newElement = createNewElement(addButton);
					if (newElement == null) {
						return;
					}
					collection.add(newElement);
				}
			}
		});
		if (creator == null) {
			addButton.setEnabled(false);
			addButton.setToolTipText(LocalizationServiceHelper.getString(DefaultButtonBarBuilder.class,
				MessageKeys.DefaultButtonBarBuilder_AddButtonTooltipNoCreator));
		}
	}

	@Override
	public Object createNewElement(Button button) {
		if (creator == null) {
			return null;
		}
		return creator.createNewElement(button);
	}

	/**
	 * @param creator the {@link NewElementCreator}
	 */
	public void setCreator(NewElementCreator<Object, Button> creator) {
		this.creator = creator;
	}
}