package org.eclipse.emfforms.internal.rulerepository.tooling.merge;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.editor.helpers.ResourceSetHelpers;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class MergeWithViewHandler extends AbstractHandler {

	private static final String ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING = "org.eclipse.emfforms.rulerepository.tooling"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IFile selectedFile = (IFile) TreeSelection.class.cast(HandlerUtil.getCurrentSelection(event))
			.getFirstElement();
		final ResourceSet resourceSet = ResourceSetHelpers.loadResourceSetWithProxies(
			URI.createPlatformResourceURI(selectedFile.getFullPath().toOSString(), false),
			new BasicCommandStack());
		VView view = null;
		VRuleRepository ruleRepository = null;
		for (final Resource resource : resourceSet.getResources()) {
			final EObject eObject = resource.getContents().get(0);
			if (VView.class.isInstance(eObject)) {
				view = VView.class.cast(eObject);
			}
			if (VRuleRepository.class.isInstance(eObject)) {
				ruleRepository = VRuleRepository.class.cast(eObject);
			}
		}
		if (ruleRepository == null) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), "Missing Rule Repository", //$NON-NLS-1$
				"The file doesn't contain a rule repository!", //$NON-NLS-1$
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, "No Rule Repository!")); //$NON-NLS-1$
			return null;
		}
		if (view == null) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), "Missing View", //$NON-NLS-1$
				"You must link a view model first!", //$NON-NLS-1$
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, "No View model linked!")); //$NON-NLS-1$
			return null;
		}

		final SaveAsDialog sad = new SaveAsDialog(HandlerUtil.getActiveShell(event));
		final int result = sad.open();
		if (result == Window.CANCEL) {
			return null;
		}
		MergeHelper.merge(ruleRepository);
		final VView mergedView = EcoreUtil.copy(view);
		// Do the work within an operation.
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				// Create a resource set
				final ResourceSet resourceSet = new ResourceSetImpl();

				// Get the URI of the model file.
				final URI fileURI = URI.createPlatformResourceURI(sad.getResult().toString(), true);

				// Create a resource for this file.
				final Resource resource = resourceSet.createResource(fileURI);

				resource.getContents().add(mergedView);

				// Save the contents of the resource to the file system.
				// final Map<Object, Object> options = new HashMap<Object, Object>();
				// options.put(XMLResource.OPTION_ENCODING, "UTF-8");
				try {
					resource.save(null);
				} catch (final IOException ex) {
					ErrorDialog.openError(HandlerUtil.getActiveShell(event), "Error", //$NON-NLS-1$
						ex.getMessage(),
						new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, ex.getMessage(), ex));
					return;
				} finally {
					progressMonitor.done();
				}
			}
		};
		try {
			HandlerUtil.getActiveWorkbenchWindow(event).run(false, false, operation);
		} catch (final InvocationTargetException ex) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), "Error", //$NON-NLS-1$
				ex.getMessage(),
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, ex.getMessage(), ex));
		} catch (final InterruptedException ex) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), "Error", //$NON-NLS-1$
				ex.getMessage(),
				new Status(IStatus.ERROR, ORG_ECLIPSE_EMFFORMS_RULEREPOSITORY_TOOLING, ex.getMessage(), ex));
		}
		return null;
	}

}
