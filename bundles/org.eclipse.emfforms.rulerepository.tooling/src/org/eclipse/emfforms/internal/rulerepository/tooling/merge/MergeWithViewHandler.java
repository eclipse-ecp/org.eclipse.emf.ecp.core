/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.rulerepository.tooling.merge;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.editor.helpers.ResourceSetHelpers;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The Handler that gets triggered when the merge of rule repository and view is triggered.
 *
 * @author Eugen Neufeld
 *
 */
public class MergeWithViewHandler extends AbstractHandler {

	private static final String ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING = "org.eclipse.emfforms.rulerepository.tooling"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		final Shell activeShell = HandlerUtil.getActiveShell(event);
		final IFile selectedFile = (IFile) TreeSelection.class.cast(currentSelection).getFirstElement();
		final WorkspaceModifyOperation operation = mergeRuleRepoWithView(activeShell, selectedFile);
		try {
			if (operation != null) {
				HandlerUtil.getActiveWorkbenchWindow(event).run(false, false, operation);
			}
		} catch (final InvocationTargetException | InterruptedException ex) {
			ErrorDialog.openError(activeShell, "Error", //$NON-NLS-1$
				ex.getMessage(),
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, ex.getMessage(), ex));
		}
		return null;
	}

	private WorkspaceModifyOperation mergeRuleRepoWithView(final Shell activeShell, final IFile selectedFile) {
		ResourceSet resourceSet;
		try {
			resourceSet = ResourceSetHelpers.loadResourceSetWithProxies(
				URI.createPlatformResourceURI(selectedFile.getFullPath().toOSString(), false),
				new BasicCommandStack(), null);
		} catch (final IOException ex) {
			ErrorDialog.openError(activeShell, "Error", //$NON-NLS-1$
				ex.getMessage(),
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, ex.getMessage(), ex));
			return null;
		}

		// FIXME Improve with Java8: Use method pointers
		return this.mergeRuleRepoWithView(activeShell, resourceSet,
			new ViewAndRepositoryProvider() {
				@Override
				public ViewAndRepository getViewAndRepository(ResourceSet resourceSet) {
					return extractViewAndRepository(resourceSet);
				}
			},
			new MergedViewPathProvider() {
				@Override
				public String getPath(Shell activeShell, VView view) {
					return getMergedViewPath(activeShell, view);
				}
			},
			new OperationProvider() {
				@Override
				public WorkspaceModifyOperation getOperation(Shell activeShell, String mergedViewPath,
					VView mergedView) {
					return getWorkspaceOperation(activeShell, mergedViewPath, mergedView);
				}
			},
			new MergeProvider() {
				@Override
				public VView merge(VRuleRepository repository, VView view) {
					return mergeRuleRepository(repository, view);
				}

			});
	}

	/** Provides a method to get the save path of the merged view. */
	public interface MergedViewPathProvider {
		/**
		 *
		 * @param activeShell The Shell
		 * @param view The base view
		 * @return The save path of the merged view
		 */
		String getPath(Shell activeShell, VView view);
	}

	/** Provides a method to create the {@link WorkspaceModifyOperation}. */
	public interface OperationProvider {
		/**
		 * Get the {@link WorkspaceModifyOperation} that saved the merged view.
		 *
		 * @param activeShell The Shell
		 * @param mergedViewPath The save path
		 * @param mergedView The merged view
		 * @return The {@link WorkspaceModifyOperation}
		 */
		WorkspaceModifyOperation getOperation(Shell activeShell, String mergedViewPath, VView mergedView);
	}

	/** Provides a method to get the {@link VView} and {@link VRuleRepository} from a {@link ResourceSet}. */
	public interface ViewAndRepositoryProvider {
		/**
		 *
		 * @param resourceSet The ResourceSet
		 * @return The RuleRepository and View
		 */
		ViewAndRepository getViewAndRepository(ResourceSet resourceSet);
	}

	/** Provides a method to merge the RuleRepository. */
	public interface MergeProvider {
		/**
		 * Merges the RuleRepository in the View.
		 *
		 * @param repository The {@link VRuleRepository}
		 * @param view The {@link VView}
		 * @return The merged View
		 */
		VView merge(VRuleRepository repository, VView view);
	}

	/**
	 * Merges a RuleRepository into its associated VView. Thereby, multiple providers are used to execute each step of
	 * the algorithm.
	 *
	 * @param activeShell The {@link Shell}
	 * @param resourceSet The {@link ResourceSet} containing the Rule Repository and the View
	 * @param viewAndRepositoryProvider The {@link ViewAndRepositoryProvider}
	 * @param pathProvider The {@link MergedViewPathProvider}
	 * @param operationProvider The {@link OperationProvider}
	 * @param mergeProvider The {@link MergeProvider}
	 * @return The {@link WorkspaceModifyOperation} saving the merged View
	 */
	WorkspaceModifyOperation mergeRuleRepoWithView(final Shell activeShell, final ResourceSet resourceSet,
		ViewAndRepositoryProvider viewAndRepositoryProvider, MergedViewPathProvider pathProvider,
		OperationProvider operationProvider, MergeProvider mergeProvider) {

		final ViewAndRepository vur = viewAndRepositoryProvider.getViewAndRepository(resourceSet);

		if (vur.getRuleRepository() == null) {
			ErrorDialog.openError(activeShell, "Missing Rule Repository", //$NON-NLS-1$
				"The file doesn't contain a rule repository!", //$NON-NLS-1$
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, "No Rule Repository!")); //$NON-NLS-1$
			return null;
		}
		if (vur.getView() == null) {
			ErrorDialog.openError(activeShell, "Missing View", //$NON-NLS-1$
				"You must link a view model first!", //$NON-NLS-1$
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, "No View model linked!")); //$NON-NLS-1$
			return null;
		}

		final String mergedViewPath = pathProvider.getPath(activeShell, vur.getView());

		final VView mergedView = mergeProvider.merge(vur.getRuleRepository(), vur.getView());

		// Do the work within an operation.
		return operationProvider.getOperation(activeShell, mergedViewPath, mergedView);
	}

	/**
	 * Merges the given rule repository into its linked VView.
	 *
	 * @param repository The {@link VRuleRepository}
	 * @param view The VView linked in the {@link VRuleRepository}
	 * @return The merged {@link VView}
	 */
	VView mergeRuleRepository(VRuleRepository repository, VView view) {
		MergeHelper.merge(repository);
		return EcoreUtil.copy(view);
	}

	/**
	 * Opens a file save dialog to determine the save path of the merged view.
	 *
	 * @param activeShell The active Shell
	 * @param view The View object
	 * @return The save path
	 */
	String getMergedViewPath(final Shell activeShell, VView view) {
		final SaveAsDialog sad = new SaveAsDialog(activeShell);
		final URI viewURI = view.eResource().getURI();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final String mergedViewPath = viewURI.trimFileExtension().toPlatformString(false).substring(1) + "_merged." //$NON-NLS-1$
			+ viewURI.fileExtension();

		final IFile file = workspace.getRoot().getFile(new Path(mergedViewPath));

		sad.setOriginalFile(file);
		final int result = sad.open();
		if (result == Window.CANCEL) {
			return null;
		}
		return sad.getResult().toString();
	}

	/**
	 * Creates a {@link WorkspaceModifyOperation} to save the merged view model to the specified file.
	 *
	 * @param activeShell The active Shell
	 * @param mergedViewPath The path where the merged view is saved
	 * @param mergedView The merged view
	 * @return The {@link WorkspaceModifyOperation}
	 */
	WorkspaceModifyOperation getWorkspaceOperation(final Shell activeShell, final String mergedViewPath,
		final VView mergedView) {
		return new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				// Create a resource set
				final ResourceSet resourceSet = new ResourceSetImpl();

				// Get the URI of the model file.
				final URI fileURI = URI.createPlatformResourceURI(mergedViewPath, true);

				// Create a resource for this file.
				final Resource resource = resourceSet.createResource(fileURI);

				resource.getContents().add(mergedView);

				// Save the contents of the resource to the file system.
				try {
					resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8")); //$NON-NLS-1$
				} catch (final IOException ex) {
					ErrorDialog.openError(activeShell, "Error", //$NON-NLS-1$
						ex.getMessage(),
						new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, ex.getMessage(), ex));
					return;
				} finally {
					progressMonitor.done();
				}
			}
		};
	}

	/**
	 * Gets the VView and VRuleRepository from the given {@link ResourceSet}.
	 *
	 * @param resourceSet The resource set containing the view and rule repository
	 * @return The {@link ViewAndRepository} container
	 */
	ViewAndRepository extractViewAndRepository(ResourceSet resourceSet) {
		final ViewAndRepository result = new ViewAndRepository();
		for (final Resource resource : resourceSet.getResources()) {
			if (resource.getContents().isEmpty()) {
				continue;
			}
			final EObject eObject = resource.getContents().get(0);
			if (VView.class.isInstance(eObject)) {
				result.setView(VView.class.cast(eObject));
			}
			if (VRuleRepository.class.isInstance(eObject)) {
				result.setRuleRepository(VRuleRepository.class.cast(eObject));
			}
		}
		return result;
	}

	/** Wrapper class containing a VView and a VRuleRepository. */
	public static class ViewAndRepository {
		private VView view;
		private VRuleRepository ruleRepository;

		/**
		 * @return the view
		 */
		public VView getView() {
			return view;
		}

		/**
		 * @param view the view to set
		 */
		public void setView(VView view) {
			this.view = view;
		}

		/**
		 * @return the ruleRepository
		 */
		public VRuleRepository getRuleRepository() {
			return ruleRepository;
		}

		/**
		 * @param ruleRepository the ruleRepository to set
		 */
		public void setRuleRepository(VRuleRepository ruleRepository) {
			this.ruleRepository = ruleRepository;
		}
	}
}
