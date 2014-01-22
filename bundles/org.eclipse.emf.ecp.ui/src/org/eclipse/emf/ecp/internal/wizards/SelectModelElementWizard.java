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

package org.eclipse.emf.ecp.internal.wizards;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.ui.common.SelectionComposite;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * This is implementation of New Model Element wizard. This wizard is show through
 * "Add new model element..." command in context menu of Navigator (only on right click on LeafSection). The
 * wizard shows a tree of model packages and their classes. The user can select a Model Element type in this
 * tree and on finish the model element is created, added to Leaf- or CompositeSection and opend for editing.
 * 
 * @author Eugen Neufeld
 */
public class SelectModelElementWizard extends ECPWizard<SelectionComposite<? extends ColumnViewer>> {

	/**
	 * @author Jonas
	 *
	 */
	private final class WizardPageExtension extends WizardPage {
		/**
		 * @param pageName
		 */
		private WizardPageExtension(String pageName) {
			super(pageName);
		}

		public void createControl(Composite parent) {
			final Composite composite = getCompositeProvider().createUI(parent);
			getCompositeProvider().getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

				public void selectionChanged(SelectionChangedEvent event) {
					final IStructuredSelection sel = (IStructuredSelection) getCompositeProvider().getViewer()
						.getSelection();

					if (sel != null && classtoSelect.isAssignableFrom(sel.getFirstElement().getClass())) {
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
			});
			getCompositeProvider().getViewer().addDoubleClickListener(new IDoubleClickListener() {

				public void doubleClick(DoubleClickEvent event) {
					if (isPageComplete() && performFinish()) {
						((WizardDialog) getContainer()).close();
					}

				}

			});
			setPageComplete(false);
			setControl(composite);
		}
	}

	private final String pageName;
	private final String description;
	private final String pageTitle;
	private final Class<?> classtoSelect;

	/**
	 * Constructor to select an EClass.
	 * 
	 * @param windowTitle The window title
	 * @param pageName the name of the page
	 * @param pageTitle the title of the page
	 * @param description the description
	 */
	public SelectModelElementWizard(String windowTitle, String pageName, String pageTitle, String description) {
		this(windowTitle, pageName, pageTitle, description, EClass.class);
	}

	/**
	 * Constructor to select an class to be specified.
	 * 
	 * @param windowTitle The window title
	 * @param pageName the name of the page
	 * @param pageTitle the title of the page
	 * @param description the description
	 * @param classtoSelect the class which can be selected
	 */
	public SelectModelElementWizard(String windowTitle, String pageName, String pageTitle, String description,
		Class<?> classtoSelect) {
		this.classtoSelect = classtoSelect;
		setWindowTitle(windowTitle);
		this.pageName = pageName;
		this.description = description;
		this.pageTitle = pageTitle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		super.addPages();
		final WizardPage wp = new WizardPageExtension(pageName);
		addPage(wp);
		wp.setTitle(pageTitle);
		wp.setDescription(description);

	}

	/**
	 * . ({@inheritDoc}) This method creates a model element instance from selected type, adds it to Leaf- or
	 * CompositeSection, and opens it.
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

	@Override
	public void dispose() {
		getCompositeProvider().dispose();
		super.dispose();
	}
}
