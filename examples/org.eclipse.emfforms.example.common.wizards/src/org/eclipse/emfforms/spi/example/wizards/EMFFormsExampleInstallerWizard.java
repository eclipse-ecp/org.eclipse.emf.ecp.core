/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler- Initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.example.wizards;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.wizard.AbstractExampleInstallerWizard;
import org.eclipse.emf.common.ui.wizard.ExampleInstallerWizard;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emfforms.internal.example.wizards.ExampleWizardsPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard to create example projects in the workspace. This implementation uses a generic page title and description for
 * the project overview page. To customize these, the following methods can be overwritten:
 * <ul>
 * <li>{@link #getProjectPageTitle()}</li>
 * <li>{@link #getProjectPageDescription()}</li>
 * </ul>
 *
 * @author Lucas Koehler
 * @since 1.18
 */
public class EMFFormsExampleInstallerWizard extends ExampleInstallerWizard {

	private ExampleInstallerPage projectPage;

	/**
	 * The project page for the EMFFormsExampleInstallerWizard.
	 *
	 * @author Lucas Koehler
	 */
	public class ExampleInstallerPage extends AbstractExampleInstallerWizard.ProjectPage {

		private final EMFFormsExampleInstallerWizard wizard;

		/**
		 * Creates a example installation wizard page.
		 *
		 * @param exampleWizard the wizard
		 * @param pageName the name of the page
		 * @param title the displayed title of the page
		 * @param titleImage the displayed image of the page
		 */
		public ExampleInstallerPage(EMFFormsExampleInstallerWizard exampleWizard, String pageName, String title,
			ImageDescriptor titleImage) {
			super(pageName, title, titleImage);
			wizard = exampleWizard;
		}

		@Override
		public void createControl(Composite parent) {
			final SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
			sashForm.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL));

			projectList = new org.eclipse.swt.widgets.List(sashForm, SWT.SINGLE | SWT.BORDER);
			projectList.setLayoutData(new GridData(GridData.FILL_BOTH));
			projectList.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					itemSelected();
				}
			});
			projectList.setFocus();

			final Composite composite = new Composite(sashForm, SWT.NONE);
			{
				final GridLayout layout = new GridLayout(2, false);
				final int margin = -5;
				final int spacing = 3;
				layout.marginTop = margin;
				layout.marginLeft = margin;
				layout.marginRight = margin;
				layout.marginBottom = margin;
				layout.horizontalSpacing = spacing;
				layout.verticalSpacing = spacing;
				composite.setLayout(layout);
			}

			descriptionText = new Text(composite, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
			{
				final GridData gridData = new GridData(GridData.FILL_BOTH);
				gridData.heightHint = convertHeightInCharsToPixels(2);
				gridData.grabExcessVerticalSpace = true;
				descriptionText.setLayoutData(gridData);
			}

			final Composite buttonComposite = new Composite(composite, SWT.NONE);
			buttonComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING
				| GridData.HORIZONTAL_ALIGN_END));
			buttonComposite.setLayout(new GridLayout());
			{
				final GridLayout layout = new GridLayout();
				final int margin = -5;
				final int spacing = 3;
				layout.marginTop = margin;
				layout.marginLeft = margin;
				layout.marginRight = margin;
				layout.marginBottom = margin;
				layout.horizontalSpacing = spacing;
				layout.verticalSpacing = spacing;
				buttonComposite.setLayout(layout);
			}

			refresh();
			sashForm.setWeights(new int[] { 70, 30 });
			setControl(sashForm);
		}

		@Override
		public void refresh() {
			if (wizard.getProjectDescriptors().isEmpty()) {
				setErrorMessage(ExampleWizardsPlugin.INSTANCE.getString("_UI_NoProjectError_message")); //$NON-NLS-1$
				setPageComplete(false);
			} else {
				setErrorMessage(null);

				int selectionIndex = projectList.getSelectionIndex();
				if (selectionIndex < 0) {
					selectionIndex = 0;
				}

				projectList.removeAll();
				for (final ProjectDescriptor projectDescriptor : wizard.getProjectDescriptors()) {
					final String name = projectDescriptor.getName();
					final boolean exists = projectDescriptor.getProject().exists();
					final String item = exists
						? ExampleWizardsPlugin.INSTANCE.getString(
							"_UI_ExistingProjectName_message", new String[] { name }) //$NON-NLS-1$
						: name;
					projectList.add(item);

					projectList.setData(item, projectDescriptor);
				}

				if (getControl() != null) {
					projectList.setSelection(selectionIndex);
					itemSelected();
				}

				setPageComplete(true);
			}
		}

		@Override
		public void setVisible(boolean visible) {
			if (visible &&
				projectList.getItemCount() > 0 &&
				projectList != null &&
				projectList.getSelectionCount() == 0) {
				int index = 0;
				int count = 0;
				for (final ProjectDescriptor projectDescriptor : wizard.getProjectDescriptors()) {
					if (projectDescriptor.getProject().exists()) {
						index = count;
						break;
					}
					count++;
				}
				projectList.select(index);
				refresh();
			}
			super.setVisible(visible);
		}

		@Override
		protected ProjectDescriptor getSelectedProjectDescriptor() {
			return projectList.getSelectionCount() == 0 ? null
				: (ProjectDescriptor) projectList.getData(projectList.getSelection()[0]);
		}

		@Override
		protected void itemSelected() {
			final ProjectDescriptor projectDescriptor = getSelectedProjectDescriptor();
			if (projectDescriptor != null) {
				final boolean exists = projectDescriptor.getProject().exists();

				String description = projectDescriptor.getDescription() != null ? projectDescriptor.getDescription()
					: ""; //$NON-NLS-1$
				if (exists) {
					final String existsMessage = ExampleWizardsPlugin.INSTANCE
						.getString("_UI_ProjectExistsAlready_message"); //$NON-NLS-1$
					description = description == "" ? //$NON-NLS-1$
						existsMessage
						: ExampleWizardsPlugin.INSTANCE.getString(
							"_UI_ProjectDescriptionAndExists_message", new String[] { //$NON-NLS-1$
								description, existsMessage });
				}
				descriptionText.setText(description);
			}
		}

	}

	@Override
	public void addPages() {
		projectPage = new ExampleInstallerPage(this, "projectPage", getProjectPageTitle(), null); //$NON-NLS-1$
		projectPage.setDescription(getProjectPageDescription());
		addPage(projectPage);
	}

	@Override
	public void dispose() {
		projectPage = null;
		super.dispose();
	}

	/**
	 * Returns the title for the project page that shows the projects that are to be created in the workspace.
	 * <p>
	 * Override this method to provide a custom page title.
	 *
	 * @return The project page's title
	 */
	protected String getProjectPageTitle() {
		return ExampleWizardsPlugin.INSTANCE.getString("_UI_ProjectPage_title"); //$NON-NLS-1$
	}

	/**
	 * Returns the description for the project page that shows the projects that are to be created in the workspace.
	 * <p>
	 * Override this method to provide a custom page description.
	 *
	 * @return The project page's description
	 */
	protected String getProjectPageDescription() {
		return ExampleWizardsPlugin.INSTANCE.getString("_UI_ProjectPage_description"); //$NON-NLS-1$
	}

	/**
	 * The EMFFormsExampleInstallerWizard doesn't delete any projects.
	 *
	 * @param monitor progress monitor
	 * @return OK
	 */
	@Override
	protected Diagnostic deleteExistingProjects(IProgressMonitor monitor) {
		return Diagnostic.OK_INSTANCE;
	}

	/**
	 * In contrast to the {@link AbstractExampleInstallerWizard}, the EMFFormsExampleInstallerWizard only installs
	 * projects if
	 * they do not already exist in the workspace.
	 *
	 * @param progressMonitor the progress monitor
	 * @throws ExecutionException if something goes wrong while installing the example.
	 * @see org.eclipse.emf.common.ui.wizard.AbstractExampleInstallerWizard#installExample(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void installExample(IProgressMonitor progressMonitor) throws ExecutionException {
		final List<ProjectDescriptor> projectDescriptors = getProjectDescriptors();
		progressMonitor.beginTask(ExampleWizardsPlugin.INSTANCE.getString("_UI_CreatingProjects_message"), //$NON-NLS-1$
			2 * projectDescriptors.size());
		for (final ProjectDescriptor projectDescriptor : projectDescriptors) {
			if (!projectDescriptor.getProject().exists()) {
				try {
					installProject(projectDescriptor, progressMonitor);
				}
				// BEGIN SUPRESS CATCH EXCEPTION
				catch (final Exception ex) {
					throw new ExecutionException("The project '" + projectDescriptor.getName() //$NON-NLS-1$
						+ "' could not be installed.", ex); //$NON-NLS-1$
				}
				// END SUPRESS CATCH EXCEPTION
			}
		}
		progressMonitor.done();
	}
}
