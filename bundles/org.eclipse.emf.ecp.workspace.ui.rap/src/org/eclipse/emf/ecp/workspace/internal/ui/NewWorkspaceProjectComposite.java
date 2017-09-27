/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Neil Mackenzie - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.workspace.internal.ui;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.ui.CompositeStateObserver;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The NewWorkspaceProjectComposite for RAP allows a user to provide the
 * information needed for the creation of a WorkspaceProject from an
 * imported XMI file.
 *
 *
 * @author Neil Mackenzie
 */
public class NewWorkspaceProjectComposite extends Composite {

	private final CompositeStateObserver compositeStateObserver;
	private final ECPProperties properties;
	private boolean complete;

	private Text newFileText;
	private Text rootClassText;
	private Text importFileText;

	private Button importButton;
	private StackLayout providerStackLayout;
	private Composite providerStackComposite;
	private Composite newProjectComposite;
	private Composite importProjectComposite;

	private Resource resource;
	private EClass eClass;
	private Composite grpDoYouWant;

	private Label label;

	/**
	 * Instantiates a new new workspace project composite.
	 *
	 * @param parent a widget which will be the parent of the new instance (cannot be null)
	 * @param observer the observer
	 * @param projectProperties the project properties
	 */
	public NewWorkspaceProjectComposite(Composite parent, CompositeStateObserver observer,
		ECPProperties projectProperties) {
		super(parent, SWT.NONE);
		properties = projectProperties;
		compositeStateObserver = observer;
		setLayout(new GridLayout(1, false));
		createStackComposite(parent);
		notifyObserver();
	}

	private void createStackComposite(Composite parent) {

		providerStackLayout = new StackLayout();

		grpDoYouWant = new Composite(this, SWT.BORDER);
		grpDoYouWant.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpDoYouWant.setLayout(new GridLayout(2, false));

		importButton = new Button(grpDoYouWant, SWT.RADIO);
		importButton.setText(Messages.NewWorkspaceProjectComposite_IMPORT_XMI_FILE);

		importButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				providerStackLayout.topControl = importProjectComposite;
				checkStatusChanged();
				providerStackComposite.layout();
			}
		});

		providerStackComposite = new Composite(this, SWT.NONE);
		providerStackComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		providerStackComposite.setLayout(providerStackLayout);

		createImportWorkspaceProjectComposite(providerStackComposite);

		providerStackLayout.topControl = newProjectComposite;

	}

	private void createImportWorkspaceProjectComposite(final Composite composite) {

		importProjectComposite = new Composite(composite, SWT.BORDER);
		importProjectComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		importProjectComposite.setLayout(new GridLayout(3, false));

		final Label selectFileLabel = new Label(importProjectComposite, SWT.NONE);
		selectFileLabel.setText(Messages.NewWorkspaceProjectComposite_SELECT_FILE);

		importFileText = new Text(importProjectComposite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		importFileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		importFileText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkStatusChanged();
			}
		});
		new Label(importProjectComposite, SWT.NONE);

		final Button importFileButton = new Button(importProjectComposite, SWT.NONE);
		importFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final FileDialog fileDialog = new FileDialog(composite.getShell(), SWT.OPEN);
				fileDialog.setText(Messages.NewWorkspaceProjectComposite_OPEN);
				// fileDialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());

				final String path = fileDialog.open();
				if (path != null) {
					final ResourceSet resourceSet = new ResourceSetImpl();
					final Resource resource = resourceSet.createResource(URI.createFileURI(path));
					try {
						resource.load(null);
						importFileText.setText(URI.createFileURI(path).toString());
					} catch (final IOException ex) {
						MessageDialog.openError(getShell(), Messages.NewWorkspaceProjectComposite_ERROR,
							Messages.NewWorkspaceProjectComposite_ERROR_PARSINGXMIFILE);
					}

				}
			}
		});
		importFileButton.setText(Messages.NewWorkspaceProjectComposite_BROWSE_FILE_SYSTEM);

	}

	private void notifyObserver() {
		if (compositeStateObserver != null) {
			compositeStateObserver.compositeChangedState(this, complete, properties);
		}
	}

	private void checkStatusChanged() {

		properties.addProperty(WorkspaceProvider.PROP_ROOT_URI, importFileText.getText());

		final boolean pendingStatus = importButton.getSelection() && importFileTextStatus();

		if (pendingStatus != complete) {
			complete = pendingStatus;
			notifyObserver();
		}
	}

	private boolean importFileTextStatus() {
		return nonEmptyString(importFileText.getText());
	}

	private boolean nonEmptyString(String string) {
		return string != null && string.length() > 0 && string.trim().length() == string.length();
	}

	private void fillResource() {
		if (resource != null && eClass != null) {
			final EObject root = EcoreUtil.create(eClass);

			resource.getContents().clear();

			resource.getContents().add(root);
			try {
				resource.save(null);
			} catch (final IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
}
