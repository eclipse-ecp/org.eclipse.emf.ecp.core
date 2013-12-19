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

import java.io.IOException;
import java.util.HashSet;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.wizards.SelectModelElementWizard;
import org.eclipse.emf.ecp.spi.ui.CompositeStateObserver;
import org.eclipse.emf.ecp.ui.common.CompositeFactory;
import org.eclipse.emf.ecp.ui.common.SelectionComposite;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * The NewWorkspaceProjectComposite allows a user to provide the information needed for the
 * creation of a WorkspaceProject.
 * 
 * @author Tobias Verhoeven
 */
public class NewWorkspaceProjectComposite extends Composite {

	/**
	 * @author Jonas
	 *
	 */
	private final class ImportWorkspaceAdapter extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault()
				.getActiveShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
			dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
			dialog.setAllowMultiple(false);
			dialog.setValidator(new ISelectionStatusValidator() {

				public IStatus validate(Object[] selection) {
					if (selection.length == 1) {
						if (selection[0] instanceof File) {
							File file = (File) selection[0];
							if (file.getType() == File.FILE) {
								return new Status(IStatus.OK, Activator.PLUGIN_ID, IStatus.OK, null, null);
							}
						}
					}
					return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, "Please Select a File",
						null);
				}
			});
			dialog.setTitle("Select XMI");

			if (dialog.open() == Dialog.OK) {
				if (dialog.getFirstResult() instanceof File) {
					File file = (File) dialog.getFirstResult();
					ResourceSet resourceSet = new ResourceSetImpl();
					Resource resource = resourceSet.createResource(URI.createPlatformResourceURI(file.getFullPath()
						.toString(), true));
					try {
						resource.load(null);
						importFileText.setText(URI.createPlatformResourceURI(file.getFullPath().toString(), true)
							.toString());
					} catch (IOException ex) {
						MessageDialog.openError(getShell(), "Error", "Error parsing XMI-File!");
					}
				}
			}
		}
	}

	private CompositeStateObserver compositeStateObserver;
	private ECPProperties properties;
	private boolean complete;

	private Text newFileText;
	private Text rootClassText;
	private Text importFileText;
	private Button createButton;
	private Button importButton;
	private StackLayout providerStackLayout;
	private Composite providerStackComposite;
	private Composite newProjectComposite;
	private Composite importProjectComposite;

	private Resource resource;
	private EClass eClass;
	private Composite grpDoYouWant;
	private Button newWorkspaceButton;
	private Button importWorkspaceButton;
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

		createButton = new Button(grpDoYouWant, SWT.RADIO);
		createButton.setText("Create empty project");

		createButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				providerStackLayout.topControl = newProjectComposite;
				checkStatusChanged();
				providerStackComposite.layout();
			}
		});

		createButton.setSelection(true);
		importButton = new Button(grpDoYouWant, SWT.RADIO);
		importButton.setText("Import existing XMI-File");

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

		createNewWorkspaceProjectComposite(providerStackComposite);
		createImportWorkspaceProjectComposite(providerStackComposite);

		providerStackLayout.topControl = newProjectComposite;

		Button rootClassButton = new Button(newProjectComposite, SWT.ARROW);
		rootClassButton.setText("Choose Root Class \u2026");
		rootClassButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		rootClassButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				eClass = openClassSelectionDialog(newProjectComposite);
				fillResource();
				if (eClass != null) {
					rootClassText.setText(eClass.getEPackage().getNsURI() + "/" + eClass.getName());
				}
			}
		});
	}

	private void createImportWorkspaceProjectComposite(final Composite composite) {

		importProjectComposite = new Composite(composite, SWT.BORDER);
		importProjectComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		importProjectComposite.setLayout(new GridLayout(3, false));

		final Label selectFileLabel = new Label(importProjectComposite, SWT.NONE);
		selectFileLabel.setText("Select File: ");//$NON-NLS-1$

		importFileText = new Text(importProjectComposite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		importFileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		importFileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkStatusChanged();
			}
		});
		new Label(importProjectComposite, SWT.NONE);

		Button importFileButton = new Button(importProjectComposite, SWT.NONE);
		importFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(composite.getShell(), SWT.OPEN);
				fileDialog.setText("Open");
				fileDialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());

				String path = fileDialog.open();
				if (path != null) {
					ResourceSet resourceSet = new ResourceSetImpl();
					Resource resource = resourceSet.createResource(URI.createFileURI(path));
					try {
						resource.load(null);
						importFileText.setText(URI.createFileURI(path).toString());
					} catch (IOException ex) {
						MessageDialog.openError(getShell(), "Error", "Error parsing XMI-File!");
					}

				}
			}
		});
		importFileButton.setText("Browse File System ...");

		importWorkspaceButton = new Button(importProjectComposite, SWT.NONE);
		importWorkspaceButton.addSelectionListener(new ImportWorkspaceAdapter());
		importWorkspaceButton.setText("Browse Workspace ...");
	}

	/**
	 * @param composite
	 */
	private void createNewWorkspaceProjectComposite(final Composite composite) {

		newProjectComposite = new Composite(composite, SWT.BORDER);
		newProjectComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		newProjectComposite.setLayout(new GridLayout(4, false));

		final Label filenameLabel = new Label(newProjectComposite, SWT.NONE);
		filenameLabel.setText("Filename:");//$NON-NLS-1$

		newFileText = new Text(newProjectComposite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		newFileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkStatusChanged();
			}
		});
		newFileText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(newProjectComposite, SWT.NONE);

		Button newFileButton = new Button(newProjectComposite, SWT.NONE);
		newFileButton.setText("Browse File System ...");

		newFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(composite.getShell(), SWT.SAVE);
				fileDialog.setText("Open");
				fileDialog.setOverwrite(true);
				fileDialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
				String path = fileDialog.open();

				if (path != null) {
					URI pathURI = URI.createFileURI(path);
					ResourceSet resourceSet = new ResourceSetImpl();
					resource = resourceSet.createResource(pathURI);

					fillResource();
					newFileText.setText(pathURI.toString());
				}
			}
		});

		newWorkspaceButton = new Button(newProjectComposite, SWT.NONE);
		newWorkspaceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				NewXMIFileWizard newXMIFileWizard = new NewXMIFileWizard();
				WizardDialog wizardDialog = new WizardDialog(composite.getShell(), newXMIFileWizard);
				if (wizardDialog.open() == Window.OK) {
					ResourceSet resourceSet = new ResourceSetImpl();
					resource = resourceSet.createResource(newXMIFileWizard.getFileURI());
					fillResource();
					newFileText.setText(newXMIFileWizard.getFileURI().toString());
				} else {

				}
			}
		});

		newWorkspaceButton.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		newWorkspaceButton.setText("Browse Workspace ...");
		new Label(newProjectComposite, SWT.NONE);

		label = new Label(newProjectComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 4, 1));

		final Label rootClassLabel = new Label(newProjectComposite, SWT.NONE);
		rootClassLabel.setText("Root Class:");//$NON-NLS-1$

		rootClassText = new Text(newProjectComposite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		rootClassText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkStatusChanged();
			}
		});
		rootClassText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
	}

	private EClass openClassSelectionDialog(Composite composite) {

		SelectionComposite helper = CompositeFactory.getSelectModelClassComposite(new HashSet<EPackage>(),
			ECPUtil.getAllRegisteredEPackages(), new HashSet<EClass>());

		SelectModelElementWizard wizard = new SelectModelElementWizard("Choose Root Class", "Choose Root Class",
			"Choose Root Class", "Select the class that will be used as the root of the your new project.");
		wizard.setCompositeProvider(helper);

		WizardDialog wd = new WizardDialog(composite.getShell(), wizard);

		int wizardResult = wd.open();
		if (wizardResult == WizardDialog.OK) {
			Object[] selection = helper.getSelection();
			if (selection == null || selection.length == 0) {
				return null;
			}
			return (EClass) selection[0];
		}
		return null;
	}

	private void notifyObserver() {
		if (compositeStateObserver != null) {
			compositeStateObserver.compositeChangedState(this, complete, properties);
		}
	}

	private void checkStatusChanged() {

		properties.addProperty(WorkspaceProvider.PROP_ROOT_URI, createButton.getSelection() ? newFileText.getText()
			: importFileText.getText());

		boolean pendingStatus = createButton.getSelection() && newFileTextStatus() && rootClassTextStatus()
			|| importButton.getSelection() && importFileTextStatus();

		if (pendingStatus != complete) {
			complete = pendingStatus;
			notifyObserver();
		}
	}

	private boolean newFileTextStatus() {
		return nonEmptyString(newFileText.getText());
	}

	private boolean importFileTextStatus() {
		return nonEmptyString(importFileText.getText());
	}

	private boolean rootClassTextStatus() {
		return nonEmptyString(rootClassText.getText());
	}

	private boolean nonEmptyString(String string) {
		return string != null && string.length() > 0 && string.trim().length() == string.length();
	}

	private void fillResource() {
		if (resource != null && eClass != null) {
			EObject root = EcoreUtil.create(eClass);

			resource.getContents().clear();

			resource.getContents().add(root);
			try {
				resource.save(null);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
}
