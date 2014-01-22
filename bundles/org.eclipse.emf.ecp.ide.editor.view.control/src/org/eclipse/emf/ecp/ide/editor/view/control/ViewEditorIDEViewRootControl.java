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
package org.eclipse.emf.ecp.ide.editor.view.control;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.editor.controls.ControlRootEClassControl;
import org.eclipse.emf.ecp.view.ideconfig.model.IDEConfig;
import org.eclipse.emf.ecp.view.ideconfig.model.IdeconfigFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * The IDE specific View Model Control.
 * 
 * @author Eugen Neufeld
 * 
 */
public class ViewEditorIDEViewRootControl extends ControlRootEClassControl {

	private static final String PLUGIN_ID = "org.eclipse.emf.ecp.ide.editor.view.control"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.editor.controls.ControlRootEClassControl#getInput()
	 */
	@Override
	protected Object getInput() {
		return selectECore();
	}

	private Object selectECore() {
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault()
			.getActiveShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.setAllowMultiple(false);
		dialog.setValidator(new ISelectionStatusValidator() {

			public IStatus validate(Object[] selection) {
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						final IFile file = (IFile) selection[0];
						if (file.getType() == IResource.FILE) {
							return new Status(IStatus.OK, PLUGIN_ID, IStatus.OK, null, null);
						}
					}
				}
				return new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, "Please Select a File",
					null);
			}
		});
		dialog.setTitle("Select XMI");

		if (dialog.open() == Window.OK) {
			final VView view = (VView) getFirstSetting().getEObject();
			if (view.getRootEClass() != null) {
				Activator.getViewModelRegistry().unregister(view.getRootEClass().eResource().getURI().toString(), view);
			}
			if (dialog.getFirstResult() instanceof IFile) {
				final IFile file = (IFile) dialog.getFirstResult();
				final ResourceSet resourceSet = new ResourceSetImpl();
				final Resource resource = resourceSet.createResource(URI.createPlatformResourceURI(file.getFullPath()
					.toString(), true));

				Activator.getViewModelRegistry().register(resource.getURI().toString(),
					(VView) getFirstSetting().getEObject().eResource().getContents().get(0));

				try {
					resource.load(null);
					final EPackage ePackage = (EPackage) resource.getContents().get(0);
					EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
					persistSelectedEcore(file);
					return ePackage;
				} catch (final IOException ex) {
					MessageDialog.openError(Display.getDefault()
						.getActiveShell(), "Error", "Error parsing XMI-File!");
				}
			}
		}
		return null;
	}

	private void persistSelectedEcore(IFile file) {
		final ResourceSet resourceSet = new ResourceSetImpl();

		final String viewModelPath = getFirstSetting().getEObject().eResource().getURI().toString();
		final String newModelPath = viewModelPath.substring(0, viewModelPath.lastIndexOf(".")) + ".ideconfig";
		final Resource resource = resourceSet.createResource(URI.createURI(newModelPath, true));
		final IDEConfig config = IdeconfigFactory.eINSTANCE.createIDEConfig();
		config.setEcorePath(file.getFullPath().toString());

		resource.getContents().add(config);
		try {
			resource.save(null);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
