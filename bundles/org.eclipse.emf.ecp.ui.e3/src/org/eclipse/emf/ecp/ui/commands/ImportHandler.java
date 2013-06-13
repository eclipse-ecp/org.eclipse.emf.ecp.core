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
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.commands;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.ui.platform.Activator;
import org.eclipse.emf.ecp.ui.platform.PreferenceHelper;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class ImportHandler extends AbstractHandler {

	/**
	 * These filter extensions are used to filter which files are displayed.
	 */
	public static final String[] FILTER_EXTS = { "*.xmi"};

	/**
	 * These filter names are used to filter which files are displayed.
	 */
	public static final String[] FILTER_NAMES = { "Model Files (*.xmi)" };

	private static final String IMPORT_MODEL_PATH = "org.eclipse.emf.emfstore.client.ui.importModelPath";

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final EObject selectedModelElement = (EObject) ((IStructuredSelection)HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		
//		ECPModelContextProvider contextProvider = (ECPModelContextProvider)((TreeView)HandlerUtil.getActivePart(event))
//	        .getViewer().getContentProvider();
//		IStructuredSelection selection=(IStructuredSelection)HandlerUtil.getCurrentSelection(event);
//		final ECPProject project = (ECPProject)ECPUtil.getModelContext(contextProvider, selectedModelElement);
		

		if (selectedModelElement == null) {//project == null || 
			return null;
		}

		final String fileName = getFileName();
		if (fileName == null) {
			return null;
		}

		final URI fileURI = URI.createFileURI(fileName);

		// create resource set and resource
		ResourceSet resourceSet = new ResourceSetImpl();

		final Resource resource = resourceSet.getResource(fileURI, true);

		

		
				importFile(selectedModelElement, fileURI, resource, HandlerUtil.getActiveShell(event));
		

		return null;
	}

	private void importFile(EObject parentObject, final URI fileURI, final Resource resource,
		final Shell shell) {
		boolean imported=false;
		final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);
		try {
			
			progressDialog.open();
			progressDialog.getProgressMonitor().beginTask("Import model...", 100);

//			Set<EObject> importElements = validation(resource);
			EObject eObjectImport = resource.getContents().get(0);
			
			for(EReference ref:parentObject.eClass().getEAllContainments()){
				if(ref.getEReferenceType().isInstance(eObjectImport)){
					EditingDomain editingDomain=AdapterFactoryEditingDomain.getEditingDomainFor(parentObject);
					if(ref.isMany()){
						editingDomain.getCommandStack().execute(new AddCommand(editingDomain, parentObject, ref, eObjectImport));
					}
					else{
						editingDomain.getCommandStack().execute(new SetCommand(editingDomain, parentObject, ref, eObjectImport));
					}
					imported=true;
					break;
				}
			}
			
//			if (importElements.size() > 0) {
//				for (EObject eObject : importElements) {
//					project.addModelElement(EcoreUtil.copy(eObject));
//					progressDialog.getProgressMonitor().worked(10);
//				}
//			}
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			Activator.log(e.getMessage(), e);
			// END SUPRESS CATCH EXCEPTION
		} finally {
			progressDialog.getProgressMonitor().done();
			progressDialog.close();
		}
		if(!imported){
			MessageDialog.openWarning(shell	, "No Imports", "No Objects were imported, the model element you selected probably can't contain the element you try to import.");
		}
	}

	//TODO ask jonas
	
	// Validates if the EObjects can be imported
//	private Set<EObject> validation(Resource resource) {
//		Set<EObject> childrenSet = new HashSet<EObject>();
//		Set<EObject> rootNodes = new HashSet<EObject>();
//
//		EList<EObject> rootContent = resource.getContents();
//
//		for (EObject rootNode : rootContent) {
//			TreeIterator<EObject> contents = rootNode.eAllContents();
//			// 1. Run: Put all children in set
//			while (contents.hasNext()) {
//				EObject content = contents.next();
//				if (!(content != null)) {
//					continue;
//				}
//				childrenSet.add(content);
//			}
//		}
//
//		// 2. Run: Check if RootNodes are children -> set.contains(RootNode) -- no: RootNode in rootNode-Set -- yes:
//		// Drop RootNode, will be imported as a child
//		for (EObject rootNode : rootContent) {
//
//			if (!(rootNode != null)) {
//				// No report to Console, because Run 1 will do this
//				continue;
//			}
//
//			if (!childrenSet.contains(rootNode)) {
//				rootNodes.add(rootNode);
//			}
//		}
//
//		// 3. Check if RootNodes are SelfContained -- yes: import -- no: error
//		Set<EObject> notSelfContained = new HashSet<EObject>();
//		for (EObject rootNode : rootNodes) {
//			if (!CommonUtil.isSelfContained(rootNode)) {
//				// TODO: Report to Console //System.out.println(rootNode + " is not selfcontained");
//				notSelfContained.add(rootNode);
//			}
//		}
//		rootNodes.removeAll(notSelfContained);
//
//		return rootNodes;
//	}

	private String getFileName() {

//		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
//		dialog.setFilterNames(FILTER_NAMES);
//		dialog.setFilterExtensions(FILTER_EXTS);
//		String initialPath = PreferenceHelper.getPreference(IMPORT_MODEL_PATH, System.getProperty("user.home"));
//		dialog.setFilterPath(initialPath);
//
//		String fileName = dialog.open();
//
//		if (fileName == null) {
//			return null;
//		}
//
//		final File file = new File(dialog.getFilterPath(), dialog.getFileName());
//
//		PreferenceHelper.setPreference(IMPORT_MODEL_PATH, file.getParent());
//
//		return file.getAbsolutePath();
		return null;
	}

}
