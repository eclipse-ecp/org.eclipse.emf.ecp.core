/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 * Johannes Faltermeier - moved file dialog dependent code to helper class
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * A utility class which provides support for importing {@link EObject}s.
 *
 * @author David
 *
 */
public final class ECPImportHandlerHelper {

	private ECPImportHandlerHelper() {
	}

	/**
	 * These filter extensions are used to filter which files are displayed.
	 */
	public static final String[] FILTER_EXTS = { "*.xmi" }; //$NON-NLS-1$

	/**
	 * These filter names are used to filter which files are displayed.
	 */
	public static final String[] FILTER_NAMES = { "Model Files (*.xmi)" }; //$NON-NLS-1$

	private static boolean imported;

	/**
	 * Connects an {@link EObject} with another imported {@link EObject} which will be selected via a dialog.
	 *
	 * @param shell The {@link Shell} which should be used for the dialog
	 * @param eObject The {@link EObject} which should be connected with the imported {@link EObject}
	 */
	public static void importElement(Shell shell, EObject eObject) {
		importElement(shell, (Object) eObject);
	}

	/**
	 * Connects an {@link EObject} with another imported {@link EObject} which will be selected via a dialog.
	 *
	 * @param shell The {@link Shell} which should be used for the dialog
	 * @param ecpProject The {@link ECPProject} where the {@link EObject} should be imported into
	 */
	public static void importElement(Shell shell, ECPProject ecpProject) {
		importElement(shell, (Object) ecpProject);
	}

	private static void importElement(Shell shell, Object object) {

		// ECPModelContextProvider contextProvider =
		// (ECPModelContextProvider)((TreeView)HandlerUtil.getActivePart(event))
		// .getViewer().getContentProvider();
		// IStructuredSelection selection=(IStructuredSelection)HandlerUtil.getCurrentSelection(event);
		// final ECPProject project = (ECPProject)ECPUtil.getModelContext(contextProvider, selectedModelElement);

		if (object == null) {// project == null ||
			return;
		}

		final String fileName = getFileName(shell);
		if (fileName == null) {
			return;
		}

		final URI fileURI = URI.createFileURI(fileName);

		// create resource set and resource
		final ResourceSet resourceSet = new ResourceSetImpl();

		final Resource resource = resourceSet.getResource(fileURI, true);

		importFile(object, fileURI, resource, shell);
	}

	private static void importFile(final Object parentObject, final URI fileURI, final Resource resource,
		final Shell shell) {
		imported = false;
		final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);
		try {

			progressDialog.open();
			progressDialog.getProgressMonitor().beginTask("Import model...", 100); //$NON-NLS-1$

			// Set<EObject> importElements = validation(resource);
			final EObject eObjectImport = resource.getContents().get(0);

			if (parentObject instanceof EObject) {

				for (final EReference ref : ((EObject) parentObject).eClass().getEAllContainments()) {
					if (ref.getEReferenceType().isInstance(eObjectImport)) {
						final EditingDomain editingDomain = AdapterFactoryEditingDomain
							.getEditingDomainFor(parentObject);
						if (ref.isMany()) {
							editingDomain.getCommandStack().execute(
								new AddCommand(editingDomain, (EObject) parentObject, ref, EcoreUtil
									.copy(eObjectImport)));
						}
						else {
							editingDomain.getCommandStack().execute(
								new SetCommand(editingDomain, (EObject) parentObject, ref, EcoreUtil
									.copy(eObjectImport)));
						}
						imported = true;
						break;
					}
				}
			}
			else if (parentObject instanceof ECPProject) {
				final EditingDomain editingDomain = ((ECPProject) parentObject).getEditingDomain();
				editingDomain.getCommandStack().execute(new ChangeCommand(eObjectImport) {

					@Override
					protected void doExecute() {
						((ECPProject) parentObject).getContents().add(EcoreUtil.copy(eObjectImport));
						imported = true;
					}
				});
			}

			// if (importElements.size() > 0) {
			// for (EObject eObject : importElements) {
			// project.addModelElement(EcoreUtil.copy(eObject));
			// progressDialog.getProgressMonitor().worked(10);
			// }
			// }
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			Activator.log(e.getMessage(), e);
			// END SUPRESS CATCH EXCEPTION
		} finally {
			progressDialog.getProgressMonitor().done();
			progressDialog.close();
		}
		if (!imported) {
			MessageDialog
				.openWarning(shell, "No Imports", //$NON-NLS-1$
					"No Objects were imported, the model element you selected probably can't contain the element you try to import."); //$NON-NLS-1$
		}
	}

	// TODO ask jonas

	// Validates if the EObjects can be imported
	// private Set<EObject> validation(Resource resource) {
	// Set<EObject> childrenSet = new HashSet<EObject>();
	// Set<EObject> rootNodes = new HashSet<EObject>();
	//
	// EList<EObject> rootContent = resource.getContents();
	//
	// for (EObject rootNode : rootContent) {
	// TreeIterator<EObject> contents = rootNode.eAllContents();
	// // 1. Run: Put all children in set
	// while (contents.hasNext()) {
	// EObject content = contents.next();
	// if (!(content != null)) {
	// continue;
	// }
	// childrenSet.add(content);
	// }
	// }
	//
	// // 2. Run: Check if RootNodes are children -> set.contains(RootNode) -- no: RootNode in rootNode-Set -- yes:
	// // Drop RootNode, will be imported as a child
	// for (EObject rootNode : rootContent) {
	//
	// if (!(rootNode != null)) {
	// // No report to Console, because Run 1 will do this
	// continue;
	// }
	//
	// if (!childrenSet.contains(rootNode)) {
	// rootNodes.add(rootNode);
	// }
	// }
	//
	// // 3. Check if RootNodes are SelfContained -- yes: import -- no: error
	// Set<EObject> notSelfContained = new HashSet<EObject>();
	// for (EObject rootNode : rootNodes) {
	// if (!CommonUtil.isSelfContained(rootNode)) {
	// // TODO: Report to Console //System.out.println(rootNode + " is not selfcontained");
	// notSelfContained.add(rootNode);
	// }
	// }
	// rootNodes.removeAll(notSelfContained);
	//
	// return rootNodes;
	// }

	private static String getFileName(Shell shell) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(ECPExportHandlerHelper.class).getBundleContext();
		final ServiceReference<ECPFileDialogHelper> serviceReference = bundleContext
			.getServiceReference(ECPFileDialogHelper.class);
		final ECPFileDialogHelper fileDialogHelper = bundleContext.getService(serviceReference);
		final String result = fileDialogHelper.getPathForImport(shell);
		bundleContext.ungetService(serviceReference);
		return result;
	}
}
