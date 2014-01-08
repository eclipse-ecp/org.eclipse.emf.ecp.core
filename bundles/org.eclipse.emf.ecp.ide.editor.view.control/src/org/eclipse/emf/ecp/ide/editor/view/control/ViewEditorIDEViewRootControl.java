package org.eclipse.emf.ecp.ide.editor.view.control;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class ViewEditorIDEViewRootControl extends ControlRootEClassControl{

	public ViewEditorIDEViewRootControl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.ecp.view.editor.controls.ControlRootEClassControl#getInput()
	 */
	@Override
	protected Object getInput() {
		return selectECore();
	}
	
	private Object selectECore(){
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault()
			.getActiveShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.setAllowMultiple(false);
		dialog.setValidator(new ISelectionStatusValidator() {

			public IStatus validate(Object[] selection) {
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						IFile file = (IFile) selection[0];
						if (file.getType() == IFile.FILE) {
							return new Status(IStatus.OK, "org.eclipse.emf.ecp.ide.editor.view.control", IStatus.OK, null, null);
						}
					}
				}
				return new Status(IStatus.ERROR, "org.eclipse.emf.ecp.ide.editor.view.control", IStatus.ERROR, "Please Select a File",
					null);
			}
		});
		dialog.setTitle("Select XMI");

		if (dialog.open() == Dialog.OK) {
			VView view=(VView) getFirstSetting().getEObject();
			Activator.getViewModelRegistry().unregister(view.getRootEClass().eResource().getURI().toString(), view);
			if (dialog.getFirstResult() instanceof IFile) {
				IFile file = (IFile) dialog.getFirstResult();
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = resourceSet.createResource(URI.createPlatformResourceURI(file.getFullPath()
					.toString(), true));
				
				Activator.getViewModelRegistry().register(resource.getURI().toString(), (VView) getFirstSetting().getEObject().eResource().getContents().get(0));
				
				try {
					resource.load(null);
					EPackage ePackage= (EPackage) resource.getContents().get(0);
					EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
					persistSelectedEcore(file);
					return ePackage;
				} catch (IOException ex) {
					MessageDialog.openError(Display.getDefault()
						.getActiveShell(), "Error", "Error parsing XMI-File!");
				}
			}
		}
		return null;
	}

	private void persistSelectedEcore(IFile file) {
		ResourceSet resourceSet = new ResourceSetImpl();
		
		String viewModelPath=getFirstSetting().getEObject().eResource().getURI().toString();
		String newModelPath=viewModelPath.substring(0, viewModelPath.lastIndexOf("."))+".ideconfig";
		Resource resource = resourceSet.createResource(URI.createURI(newModelPath, true));
		IDEConfig config = IdeconfigFactory.eINSTANCE.createIDEConfig();
		config.setEcorePath(file.getFullPath().toString());
		
		
		resource.getContents().add(config);
		try {
			resource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
