/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.presentation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Wizard page for selecting an Ecore.
 */
public class SelectEcorePage extends WizardPage {
	private Text text1;
	private Composite container;
	private Button browseWorkspaceBtn;
	private final String pluginId;
	private Button browsePackageRegistryBtn;
	/** The container of the EClass. It can either be an IFile or an EPackage. */
	private Object selectedContainer;

	/**
	 * Creates a new Ecore selection wizard page.
	 *
	 * @param pageName the name of the page
	 */
	public SelectEcorePage(String pageName) {
		super("Select Model"); //$NON-NLS-1$
		setTitle("Select Model"); //$NON-NLS-1$
		setDescription("Select a model file for the new View Model"); //$NON-NLS-1$
		pluginId = pageName;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		final Label label1 = new Label(container, SWT.NONE);
		label1.setText("Selected Ecore:"); //$NON-NLS-1$

		text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		text1.setText(""); //$NON-NLS-1$

		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		text1.setLayoutData(gd);

		@SuppressWarnings("unused")
		final Composite dummy = new Composite(container, SWT.None);

		final Composite btnsComposite = new Composite(container, SWT.NONE);
		final GridLayout gridLayout = GridLayoutFactory.fillDefaults().create();
		gridLayout.numColumns = 2;
		btnsComposite.setLayout(gridLayout);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(btnsComposite);

		browseWorkspaceBtn = new Button(btnsComposite, SWT.PUSH);
		browseWorkspaceBtn.setText("Browse Workspace"); //$NON-NLS-1$
		browseWorkspaceBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final ElementTreeSelectionDialog dialog = getWorkspaceEcoreSelectionDialog();

				if (dialog.open() == Window.OK) {
					// get Ecore
					final IFile selectedEcore = (IFile) dialog.getFirstResult();
					text1.setText(selectedEcore.getFullPath().toString());
					selectedContainer = selectedEcore;
					setPageComplete(selectedContainer != null);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		});

		browsePackageRegistryBtn = new Button(btnsComposite, SWT.PUSH);
		browsePackageRegistryBtn.setText("Browse Package Registry"); //$NON-NLS-1$
		browsePackageRegistryBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final ElementListSelectionDialog selectRegisteredPackageDialog = getPackageSelectionDialog();
				selectRegisteredPackageDialog.open();
				final Object[] result = selectRegisteredPackageDialog.getResult();
				if (result == null) {
					return;
				}
				final String selectedEPackageURI = (String) result[0];
				selectedContainer = EPackage.Registry.INSTANCE.getEPackage(selectedEPackageURI);
				text1.setText(selectedEPackageURI);
				setPageComplete(selectedContainer != null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		});

		setControl(container);
		setPageComplete(false);
	}

	private ElementListSelectionDialog getPackageSelectionDialog() {
		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return ExtendedImageRegistry.getInstance()
					.getImage(EcoreEditPlugin.INSTANCE.getImage("full/obj16/EPackage")); //$NON-NLS-1$
			}
		});
		dialog.setMultipleSelection(false);
		dialog.setTitle("Package Selection"); //$NON-NLS-1$
		dialog.setMessage("Select a package:"); //$NON-NLS-1$
		dialog.setElements(EcoreHelper.getDefaultPackageRegistryContents());
		return dialog;
	}

	private ElementTreeSelectionDialog getWorkspaceEcoreSelectionDialog() {
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault()
			.getActiveShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.setAllowMultiple(false);
		dialog.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						final IFile file = (IFile) selection[0];
						if (file.getFileExtension().equals("ecore")) { //$NON-NLS-1$
							return new Status(IStatus.OK, pluginId, IStatus.OK, null, null);
						}
					}
				}
				return new Status(IStatus.ERROR, pluginId, IStatus.ERROR, "Please Select a Model file", //$NON-NLS-1$
					null);
			}
		});
		dialog.setTitle("Select Model"); //$NON-NLS-1$
		return dialog;
	}

	/**
	 * Returns the path of the selected Ecore as specified in the text field of the page.
	 *
	 * @return the path as a String
	 */
	public String getText1() {
		return text1.getText();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		// if (visible) {
		// selectedEClass = null;
		// }
	}

	/**
	 * Sets the selected Ecore container.
	 *
	 * @param object the container
	 */
	public void setSelectedContainer(Object object) {
		selectedContainer = object;
		setPageComplete(object != null);
		text1.setText(getContainerName());
	}

	/**
	 * @return the container of the EClass. It can either be an IFile or an EPackage.
	 */
	public Object getSelectedContainer() {
		return selectedContainer;
	}

	/**
	 * @return
	 */
	private String getContainerName() {
		String containerName = ""; //$NON-NLS-1$
		if (selectedContainer != null) {
			if (EPackage.class.isInstance(selectedContainer)) {
				containerName = EPackage.class.cast(selectedContainer).getName();
			} else if (IFile.class.isInstance(selectedContainer)) {
				containerName = ((IFile) selectedContainer).getFullPath().toString();
			}
		}
		return containerName;
	}

}
