/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Lucas Koehler - add migration of view ecore namespace URI
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.tooling.editor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.spi.view.migrator.TemplateModelMigrationException;
import org.eclipse.emf.ecp.spi.view.migrator.TemplateModelMigratorUtil;
import org.eclipse.emf.ecp.spi.view.migrator.TemplateModelWorkspaceMigrator;
import org.eclipse.emf.ecp.spi.view.migrator.ViewNsMigrationUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.ecp.view.template.internal.tooling.util.MigrationDialogHelper;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emfforms.spi.core.services.segments.LegacyDmrToRootEClass;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrationException;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrator;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrator.PreReplaceProcessor;
import org.eclipse.emfforms.spi.ide.view.segments.ToolingModeUtil;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

/**
 * EditorPart for the Template Model Editor.
 *
 * @author Eugen Neufeld
 *
 */
public class TemplateModelEditorPart extends GenericEditor {

	private VTViewTemplate template;
	private TreeMasterDetailComposite treeMasterDetail;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.setSite(site);
		if (!(input instanceof FileEditorInput)) {
			throw new PartInitException(Messages.TemplateModelEditorPart_invalidEditorInput);
		}
		final FileEditorInput fei = (FileEditorInput) input;

		// resourceURI must be a platform resource URI
		try {
			if (!ViewNsMigrationUtil.checkMigration(fei.getPath().toFile())) {
				final boolean migrate = MessageDialog.openQuestion(site.getShell(),
					Messages.TemplateModelEditorPart_MigrationQuestion,
					Messages.TemplateModelEditorPart_MigrationDescription);
				if (migrate) {
					ViewNsMigrationUtil.migrateViewEcoreNsUri(fei.getPath().toFile());
					migrateWorkspaceModels(site.getShell());
					if (ToolingModeUtil.isSegmentToolingEnabled()) {
						migrateLegacyDmrs(site.getShell(), fei.getPath());
					}
				}
			} else if (ToolingModeUtil.isSegmentToolingEnabled()) {
				migrateLegacyDmrs(site.getShell(), fei.getPath());
			}

			final ResourceSet resourceSet = new ResourceSetImpl();
			final Resource resource = resourceSet.createResource(URI.createURI(fei.getURI().toURL().toExternalForm()));
			resource.load(null);
			final EList<EObject> resourceContents = resource.getContents();
			if (resourceContents.size() > 0 && VTViewTemplate.class.isInstance(resourceContents.get(0))) {
				final VTViewTemplate template = (VTViewTemplate) resourceContents.get(0);
				for (final String ecorePath : template.getReferencedEcores()) {
					EcoreHelper.registerEcore(ecorePath);
				}
			} else {
				throw new PartInitException(Messages.TemplateModelEditorPart_initError);
			}

		} catch (final IOException e) {
			Activator.log(e);
			throw new PartInitException(Messages.TemplateModelEditorPart_initError, e);
		}

		// super.init is called at the end because we need to check for necessary migrations and register referenced
		// ecores before the resource is finally loaded in GenericEditor#init
		super.init(site, input);
		super.setPartName(input.getName());
	}

	@Override
	protected Object modifyEditorInput(ResourceSet resourceSet) {
		// Make sure all proxies are resolved before showing the editor
		int rsSize = getResourceSet().getResources().size();
		EcoreUtil.resolveAll(getResourceSet());
		while (rsSize != getResourceSet().getResources().size()) {
			EcoreUtil.resolveAll(getResourceSet());
			rsSize = getResourceSet().getResources().size();
		}
		/* this access is save, otherwise we would have thrown a part init exception in init */
		template = VTViewTemplate.class.cast(resourceSet.getResources().get(0).getContents().get(0));
		return new RootObject(template);
	}

	@Override
	protected TreeMasterDetailComposite createTreeMasterDetail(Composite composite, Object editorInput,
		CreateElementCallback createElementCallback) {
		treeMasterDetail = super.createTreeMasterDetail(composite, editorInput, createElementCallback);
		return treeMasterDetail;
	}

	@Override
	public void dispose() {
		if (template != null) {
			for (final String ecorePath : template.getReferencedEcores()) {
				EcoreHelper.unregisterEcore(ecorePath);
			}
		}
		super.dispose();
	}

	/**
	 * Gives access to the template model which is the input of the editor.
	 *
	 * @return the {@link VTViewTemplate}
	 */
	public VTViewTemplate getTemplate() {
		return template;
	}

	/**
	 * The given element will be revealed in the tree of the editor.
	 *
	 * @param objectToReveal the object to reveal
	 */
	@Override
	public void reveal(EObject objectToReveal) {
		treeMasterDetail.getSelectionProvider().refresh();
		treeMasterDetail.getSelectionProvider().reveal(objectToReveal);
		treeMasterDetail.setSelection(new StructuredSelection(objectToReveal));
	}

	/**
	 * Checks whether the current view model contains any legacy DMRs. If yes, ask the user whether (s)he wants to
	 * migrate them to segment based DMRs and execute the migration if the user accepts.
	 *
	 * @param shell The shell to open UI dialogs on
	 * @param resourcePath the resource path of the template model to migrate
	 */
	private void migrateLegacyDmrs(Shell shell, final IPath resourcePath) {
		final DmrToSegmentsMigrator migrator = getEditorSite().getService(DmrToSegmentsMigrator.class);
		final URI resourceURI = URI.createFileURI(resourcePath.toFile().getAbsolutePath());
		if (migrator.needsMigration(resourceURI)) {
			final boolean migrate = MessageDialog.openQuestion(shell,
				Messages.TemplateModelEditorPart_LegacyMigrationQuestionTitle,
				Messages.TemplateModelEditorPart_LegacyMigrationQuestionMessage);
			if (migrate) {
				try {
					new ProgressMonitorDialog(shell).run(true, false, monitor -> {
						try {
							final LegacyDmrToRootEClass dmrToRootEClass = getEditorSite()
								.getService(LegacyDmrToRootEClass.class);
							migrator.performMigration(resourceURI, new DmrSelectorPreReplaceProcessor(dmrToRootEClass));
						} catch (final DmrToSegmentsMigrationException ex) {
							throw new InvocationTargetException(ex);
						}
					});
				} catch (InvocationTargetException | InterruptedException ex) {
					MessageDialog.openError(
						Display.getDefault().getActiveShell(),
						Messages.TemplateModelEditorPart_LegacyMigrationErrorTitle,
						Messages.TemplateModelEditorPart_LegacyMigrationErrorMessage);
					Activator.getDefault().getLog().log(
						new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.TemplateModelEditorPart_LegacyMigrationErrorTitle, ex));
				}
			}
		}
	}

	/**
	 * If there is a template migrator, prompt the user if (s)he wants to search the workspace for template models that
	 * need migration. Afterwards, let the user chose which models to migrate and execute the migration.
	 * <p>
	 *
	 * @param shell The {@link Shell} to create the dialogs for prompting the user on.
	 */
	// TODO This is (nearly) duplicated from the ViewEditor Part and should be refactored into a single source
	private void migrateWorkspaceModels(final Shell shell) {
		final TemplateModelWorkspaceMigrator templateMigrator = TemplateModelMigratorUtil
			.getTemplateModelWorkspaceMigrator();
		if (templateMigrator == null) {
			return;
		}
		// Prompt user to migrate template models in the workspace
		final boolean migrateTemplates = MessageDialog.openQuestion(shell,
			Messages.TemplateModelEditorPart_TemplateMigrationTitle,
			Messages.TemplateModelEditorPart_TemplateMigrationDescription);
		if (migrateTemplates) {
			final List<URI> templateModelsToMigrate = getTemplateModelWorkspaceURIsToMigrate();

			final IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
					try {
						for (final URI uri : templateModelsToMigrate) {
							templateMigrator.performMigration(uri);
						}
					} catch (final TemplateModelMigrationException ex) {
						throw new InvocationTargetException(ex);
					}
				}
			};

			try {
				new ProgressMonitorDialog(shell).run(true, false, runnable);
			} catch (final InvocationTargetException e) {
				MessageDialog.openError(
					Display.getDefault().getActiveShell(), Messages.TemplateModelEditorPart_TemplateMigrationErrorTitle,
					Messages.TemplateModelEditorPart_TemplateMigrationErrorMessage);
				Activator
					.getDefault().getLog().log(
						new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.TemplateModelEditorPart_TemplateMigrationErrorTitle, e));
			} catch (final InterruptedException e) {
				MessageDialog.openError(
					Display.getDefault().getActiveShell(), Messages.TemplateModelEditorPart_TemplateMigrationErrorTitle,
					Messages.TemplateModelEditorPart_TemplateMigrationErrorMessage);
				Activator.getDefault().getLog().log(
					new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						Messages.TemplateModelEditorPart_TemplateMigrationErrorTitle, e));
			}

		}
	}

	private List<URI> getTemplateModelWorkspaceURIsToMigrate() {
		final List<URI> uris = new LinkedList<URI>();

		final TemplateModelWorkspaceMigrator workspaceMigrator = TemplateModelMigratorUtil
			.getTemplateModelWorkspaceMigrator();
		if (workspaceMigrator == null) {
			return uris;
		}
		try {
			final List<URI> urIsToMigrate = workspaceMigrator.getURIsToMigrate();

			if (urIsToMigrate.size() > 0) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final ListSelectionDialog migrationDialog = MigrationDialogHelper
					.getTemplateModelListMigrationDialog(shell, urIsToMigrate);

				if (migrationDialog.open() == Window.OK) {
					final Object[] selectedURIs = migrationDialog.getResult();
					if (selectedURIs != null) {
						for (final Object selectedUri : selectedURIs) {
							uris.add((URI) selectedUri);
						}
					}
				}
			}
		} catch (final CoreException ex) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
		}
		return uris;
	}

	@Override
	protected boolean enableValidation() {
		return true;
	}

	/**
	 * {@link PreReplaceProcessor} for the legacy dmr migration which extracts the root EClass from a legacy dmr and
	 * sets it to its containing {@link VTDomainModelReferenceSelector}.
	 */
	protected static class DmrSelectorPreReplaceProcessor implements PreReplaceProcessor {

		private final LegacyDmrToRootEClass dmrToRootEClass;

		/**
		 * Default constructor.
		 *
		 * @param dmrToRootEClass The {@link LegacyDmrToRootEClass}
		 */
		public DmrSelectorPreReplaceProcessor(LegacyDmrToRootEClass dmrToRootEClass) {
			this.dmrToRootEClass = dmrToRootEClass;
		}

		@Override
		public void process(VDomainModelReference legacyDmr, VDomainModelReference segmentDmr) {
			if (legacyDmr.eContainer() instanceof VTDomainModelReferenceSelector) {
				final VTDomainModelReferenceSelector selector = (VTDomainModelReferenceSelector) legacyDmr.eContainer();
				final Optional<EClass> rootEClass = dmrToRootEClass.getRootEClass(legacyDmr);
				rootEClass.ifPresent(selector::setRootEClass);
			}
		}

	}
}
