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
package org.eclipse.emf.ecp.workspace.internal.ui;

import java.lang.reflect.Field;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.internal.ide.misc.ContainerSelectionGroup;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * The Class NewXMIFileWizardPage.
 *
 * @author Tobias Verhoeven
 */
public class NewXMIFileWizardPage extends WizardNewFileCreationPage {

	private TreeViewer memTreeViewer;

	/**
	 * Instantiates a new new XMI file wizard page.
	 */
	public NewXMIFileWizardPage() {
		super("Create XMI File", new StructuredSelection(ResourcesPlugin.getWorkspace().getRoot()));
		setTitle("Create new XMI File");
		setDescription("Select the root Container and provide a name, you may also create a new project.");
		setFileName("*.xmi");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		retrieveTreeViewer(parent);
	}

	/**
	 * @param parent
	 */
	private void retrieveTreeViewer(Composite parent) {
		// ...
		Composite composite = (Composite) parent.getChildren()[0];
		composite = (Composite) composite.getChildren()[0];
		final ContainerSelectionGroup csg = (ContainerSelectionGroup) composite.getChildren()[0];

		try {

			final Field field = ContainerSelectionGroup.class.getDeclaredField("treeViewer");
			field.setAccessible(true);
			memTreeViewer = (TreeViewer) field.get(csg);

		} catch (final IllegalArgumentException ex) {
		} catch (final IllegalAccessException ex) {
		} catch (final NoSuchFieldException ex) {
		} catch (final SecurityException ex) {
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createAdvancedControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createAdvancedControls(final Composite parent) {

		final Button btnCreateNewProject = new Button(parent, SWT.NONE);
		btnCreateNewProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCreateNewProject.setText("Create New Project ...");

		btnCreateNewProject.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final BasicNewProjectResourceWizard bd = new BasicNewProjectResourceWizard();
				bd.init(PlatformUI.getWorkbench(), null);
				final WizardDialog wizardDialog = new WizardDialog(parent.getShell(), bd);
				if (wizardDialog.open() == Window.OK) {

				} else {

				}
				if (memTreeViewer != null) {
					memTreeViewer.refresh();
				}

			}
		});

		super.createAdvancedControls(parent);
	}

}
