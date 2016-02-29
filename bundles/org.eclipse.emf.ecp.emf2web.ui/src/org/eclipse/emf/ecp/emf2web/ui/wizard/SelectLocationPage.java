/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.ui.wizard;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecp.emf2web.controller.GenerationInfo;
import org.eclipse.emf.ecp.emf2web.ui.messages.Messages;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * A page for a single generated file which's main purpose is to select a location for the file.
 *
 * @author Stefan Dirix
 *
 */
public class SelectLocationPage extends WizardPage {
	private DataBindingContext bindingContext;

	private final GenerationInfo generationInfo;
	private Text locationText;
	private Text generatedText;
	private Button btnWrap;
	private ControlDecoration requiredLocationDecoration;

	private boolean wasAlreadyVisible;

	/**
	 * Constructor.
	 *
	 * @param generationInfo The {@link GenerationInfo} for which this page is responsible.
	 */
	public SelectLocationPage(GenerationInfo generationInfo) {
		super("wizardPage"); //$NON-NLS-1$
		this.generationInfo = generationInfo;

		wasAlreadyVisible = false;

		setTitleAndDescription();
	}

	/**
	 * Sets title and description of this page according to the {@link GenerationInfo}.
	 */
	protected void setTitleAndDescription() {
		if (GenerationInfo.MODEL_TYPE.equals(generationInfo.getType())) {
			setTitle(Messages.getString("SelectLocationPage.Title_ModelType")); //$NON-NLS-1$
			setDescription(Messages.getString("SelectLocationPage.Description_ModelType")); //$NON-NLS-1$
		} else if (GenerationInfo.VIEW_TYPE.equals(generationInfo.getType())) {
			setTitle(Messages.getString("SelectLocationPage.Title_ViewType")); //$NON-NLS-1$
			setDescription(Messages.getString("SelectLocationPage.Description_ViewType")); //$NON-NLS-1$
		} else if (GenerationInfo.MODEL_AND_VIEW_TYPE.equals(generationInfo.getType())) {
			setTitle(Messages.getString("SelectLocationPage.Title_ModelAndViewType")); //$NON-NLS-1$
			setDescription(Messages.getString("SelectLocationPage.Description_ModelAndViewType")); //$NON-NLS-1$
		} else {
			setTitle(Messages.getString("SelectLocationPage.Title_OtherType") + generationInfo.getNameProposal()); //$NON-NLS-1$
			setDescription(Messages.getString("SelectLocationPage.Description_OtherType")); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the linked {@link GenerationInfo}.
	 *
	 * @return The {@link GenerationInfo}.
	 */
	public GenerationInfo getGenerationInfo() {
		return generationInfo;
	}

	/**
	 * Returns the bindingContext of this page.
	 *
	 * @return The {@link DataBindingContext}.
	 */
	public DataBindingContext getBindingContext() {
		return bindingContext;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(3, false));

		final Label lblLocation = new Label(container, SWT.NONE);
		lblLocation.setText(Messages.getString("SelectLocationPage.LocationLabel")); //$NON-NLS-1$
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		locationText = new Text(container, SWT.BORDER);
		locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		requiredLocationDecoration = new ControlDecoration(locationText, SWT.LEFT | SWT.TOP);
		final FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
			.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		requiredLocationDecoration.setImage(fieldDecoration.getImage());
		requiredLocationDecoration.setDescriptionText(Messages.getString("SelectLocationPage.RequiredDecorationText")); //$NON-NLS-1$
		requiredLocationDecoration.hide();

		final Button browseWorkspaceButton = new Button(container, SWT.NONE);
		browseWorkspaceButton.addSelectionListener(new BrowseWorkspaceButtonSelectionListener());
		browseWorkspaceButton.setText(Messages.getString("SelectLocationPage.BrowseWorkspaceButtonLabel")); //$NON-NLS-1$

		final Button browseFilesystemButton = new Button(container, SWT.NONE);
		browseFilesystemButton.addSelectionListener(new BrowseFilesystemButtonSelectionListener());
		browseFilesystemButton.setText(Messages.getString("SelectLocationPage.BrowseFilesystemButtonLabel")); //$NON-NLS-1$
		new Label(container, SWT.NONE);

		if (generationInfo.getWrapper() != null) {
			final Group grpWrapper = new Group(container, SWT.NONE);
			grpWrapper.setLayout(new GridLayout(1, false));
			grpWrapper.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
			grpWrapper.setText(Messages.getString("SelectLocationPage.OptionalSettingsGroupText")); //$NON-NLS-1$

			btnWrap = new Button(grpWrapper, SWT.CHECK);
			btnWrap.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
			final String buttonText = Messages.getString("SelectLocationPage.WrapButtonText") //$NON-NLS-1$
				+ ' ' + generationInfo.getWrapper().getName();
			btnWrap.setText(buttonText);
		}

		final Group grpPreview = new Group(container, SWT.NONE);
		grpPreview.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpPreview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		grpPreview.setText(Messages.getString("SelectLocationPage.ContentGroupText")); //$NON-NLS-1$

		generatedText = new Text(grpPreview, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		bindingContext = initDataBindings();

		WizardPageSupport.create(this, bindingContext);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && !wasAlreadyVisible) {
			wasAlreadyVisible = true;
		}
	}

	/**
	 * Indicates if this page was already shown to the user.
	 *
	 * @return
	 *         {@code true} if this page was already shown to the user, {@code false} if this page was never shown to
	 *         the user.
	 */
	public boolean wasAlreadyVisible() {
		return wasAlreadyVisible;
	}

	/**
	 * Selection Listener for the Browse Workspace button opening a WorkspaceResourceDialog.
	 */
	private class BrowseWorkspaceButtonSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(final SelectionEvent e) {
			final WorkspaceResourceDialog dialog = new WorkspaceResourceDialog(getShell(), new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
			dialog.setAllowMultiple(false);
			dialog.setTitle(Messages.getString("SelectLocationPage.SelectLocationDialogTitle")); //$NON-NLS-1$
			dialog.setShowNewFolderControl(true);
			dialog.setShowFileControl(true);

			// preselect if file exists in workspace
			if (generationInfo.getLocation() != null && generationInfo.getLocation().isPlatform()) {
				final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				final IResource resource = root.findMember(generationInfo.getLocation().toPlatformString(true));
				if (resource != null && resource.isAccessible()) {
					if (resource instanceof IContainer) {
						dialog.setInitialSelection(resource);
					} else {
						dialog.setInitialSelection(resource.getParent());
						dialog.setFileText(resource.getName());
					}
				}
			}

			dialog.loadContents();

			if (dialog.open() == Window.OK) {
				final IFile file = dialog.getFile();
				final URI newLocation = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
				setNewLocation(newLocation);
			}
		}
	}

	/**
	 * Selection Listener for the Browse Filesystem button opening a FileDialog.
	 */
	private class BrowseFilesystemButtonSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(final SelectionEvent e) {
			final FileDialog fileDialog = new FileDialog(getShell(), SWT.SINGLE | SWT.SAVE);
			final String result = fileDialog.open();
			if (result != null) {
				final URI newLocation = URI.createFileURI(result);
				setNewLocation(newLocation);
			}
		}
	}

	/**
	 * Handles the propagation of a new location.
	 *
	 * @param location
	 *            The new location for the generated file.
	 */
	private void setNewLocation(URI location) {
		generationInfo.setLocation(location);
		bindingContext.updateTargets();
		requiredLocationDecoration.hide();
	}

	/**
	 * Validates the given location.
	 */
	private class LocationValidator implements IValidator {
		@Override
		public IStatus validate(Object value) {
			if (value == null) {
				requiredLocationDecoration.show();
				return ValidationStatus.error(Messages.getString("SelectLocationPage.LocationError")); //$NON-NLS-1$
			}
			requiredLocationDecoration.hide();
			return ValidationStatus.ok();
		}
	}

	/**
	 * Converts a manually entered string to an {@link URI}.
	 */
	private class StringToURIConverter implements IConverter {
		@Override
		public Object getToType() {
			return URI.class;
		}

		@Override
		public Object getFromType() {
			return String.class;
		}

		@Override
		public Object convert(Object fromObject) {
			if (fromObject == null || "".equals(fromObject)) { //$NON-NLS-1$
				return null;
			}
			final String path = (String) fromObject;
			if (path.startsWith("platform:") || path.startsWith("file:")) { //$NON-NLS-1$ //$NON-NLS-2$
				return URI.createURI(path, false);
			}
			return URI.createFileURI(path);
		}
	}

	/**
	 * Converts an URI to a String.
	 */
	private class URItoStringConverter implements IConverter {
		@Override
		public Object getToType() {
			return String.class;
		}

		@Override
		public Object getFromType() {
			return URI.class;
		}

		@Override
		public Object convert(Object fromObject) {
			if (fromObject == null) {
				return ""; //$NON-NLS-1$
			}
			final URI path = (URI) fromObject;
			if (path.isFile()) {
				return path.toFileString();
			}
			return path.toString();
		}
	}

	/**
	 * Creates and initializes the used data bindings.
	 *
	 * @return The initialized {@link DataBindingContext}.
	 */
	protected DataBindingContext initDataBindings() {
		final DataBindingContext bindingContext = new DataBindingContext();

		final IObservableValue observeTextLocationTextObserveWidget = WidgetProperties.text(SWT.Modify)
			.observe(locationText);
		final IObservableValue locationGenerationInfoObserveValue = PojoProperties.value("location") //$NON-NLS-1$
			.observe(generationInfo);
		bindingContext.bindValue(
			observeTextLocationTextObserveWidget, locationGenerationInfoObserveValue, new UpdateValueStrategy()
				.setConverter(new StringToURIConverter()).setAfterConvertValidator(new LocationValidator()),
			new UpdateValueStrategy().setConverter(new URItoStringConverter()));

		final IObservableValue observeTextGeneratedTextObserveWidget = WidgetProperties.text(SWT.Modify)
			.observe(generatedText);
		final IObservableValue generatedStringGenerationInfoObserveValue = PojoProperties.value("generatedString") //$NON-NLS-1$
			.observe(generationInfo);
		bindingContext.bindValue(observeTextGeneratedTextObserveWidget, generatedStringGenerationInfoObserveValue, null,
			null);

		if (generationInfo.getWrapper() != null) {
			final IObservableValue observeSelectionBtnWrapObserveWidget = WidgetProperties.selection().observe(btnWrap);
			final IObservableValue wrapGenerationInfoObserveValue = PojoProperties.value("wrap") //$NON-NLS-1$
				.observe(generationInfo);
			bindingContext.bindValue(observeSelectionBtnWrapObserveWidget, wrapGenerationInfoObserveValue, null, null);
		}

		return bindingContext;
	}
}
