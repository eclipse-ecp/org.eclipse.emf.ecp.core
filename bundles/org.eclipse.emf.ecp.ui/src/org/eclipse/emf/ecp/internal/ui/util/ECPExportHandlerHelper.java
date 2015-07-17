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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.PreferenceHelper;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * A utility class which provides support for exporting model elements.
 *
 * @author David
 *
 */
public final class ECPExportHandlerHelper {

	private ECPExportHandlerHelper() {
	}

	private static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error in a ECP plugin occured."; //$NON-NLS-1$

	private static final String FILE_EXTENSION = "xmi"; //$NON-NLS-1$

	/**
	 * These filter names are used to filter which files are displayed.
	 */
	public static final String[] FILTER_NAMES = { "Model Files (*." + FILE_EXTENSION + ")" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * These filter extensions are used to filter which files are displayed.
	 */
	public static final String[] FILTER_EXTS = { "*." + FILE_EXTENSION }; //$NON-NLS-1$

	private static final String EXPORT_MODEL_PATH = "org.eclipse.emf.emfstore.client.ui.exportModelPath"; //$NON-NLS-1$

	/**
	 * Export a list of model elements.
	 *
	 * @param shell The shell which should be used for the file dialog
	 * @param eObjects The {@link EObject}s which should be exported
	 */
	public static void export(Shell shell, List<EObject> eObjects) {
		if (eObjects.size() > 0) {
			final String filePath = getFilePathByFileDialog(shell, getNameForModelElement(eObjects.get(0)));
			if (filePath == null) {
				return;
			}
			PreferenceHelper.setPreference(EXPORT_MODEL_PATH, new File(filePath).getParent());
			runCommand(eObjects, filePath, shell);
		}
	}

	/**
	 * Get the name of a model element.
	 *
	 * @param modelElement the model element
	 * @return the name for the model element
	 */
	private static String getNameForModelElement(EObject modelElement) {
		ComposedAdapterFactory adapterFactory = null;

		adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);

		final String text = labelProvider.getText(modelElement);
		adapterFactory.dispose();
		labelProvider.dispose();
		return text;
	}

	private static void runCommand(final List<EObject> exportModelElements, String filePath, Shell shell) {
		final File file = new File(filePath);

		final URI uri = URI.createFileURI(filePath);

		final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);

		progressDialog.open();
		progressDialog.getProgressMonitor().beginTask("Export modelelement...", 100); //$NON-NLS-1$
		progressDialog.getProgressMonitor().worked(10);

		try {
			saveEObjectToResource(exportModelElements, uri);
		} catch (final IOException e) {
			showExceptionDialog(e.getMessage(), e, shell);
		}
		progressDialog.getProgressMonitor().done();
		progressDialog.close();

		MessageDialog.openInformation(null, "Export", "Exported modelelement to file " + file.getName()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This method opens a standard error dialog displaying an exception to the user.
	 *
	 * @param cause the exception to be shown.
	 * @param message the message to be shown.
	 */
	private static void showExceptionDialog(String message, Exception cause, Shell shell) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(message);
		String title = "Error"; //$NON-NLS-1$
		if (cause != null) {
			stringBuilder.append(": "); //$NON-NLS-1$
			stringBuilder.append(cause.getMessage());
			title = cause.getClass().getName();
		}
		final String string = stringBuilder.toString();
		MessageDialog.openError(shell, title, string);
		Activator.log(UNEXPECTED_ERROR_MESSAGE, cause);
	}

	private static String getFilePathByFileDialog(Shell shell, String modelElementName) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(ECPExportHandlerHelper.class).getBundleContext();
		final ServiceReference<ECPFileDialogHelper> serviceReference = bundleContext
			.getServiceReference(ECPFileDialogHelper.class);
		final ECPFileDialogHelper fileDialogHelper = bundleContext.getService(serviceReference);
		final String result = fileDialogHelper.getPathForExport(shell, modelElementName);
		bundleContext.ungetService(serviceReference);
		return result;
	}

	/**
	 * Save a list of EObjects to the resource with the given URI.
	 *
	 * @param eObjects the EObjects to be saved
	 * @param resourceURI the URI of the resource, which should be used to save the EObjects
	 * @throws IOException if saving to the resource fails
	 */
	private static void saveEObjectToResource(List<? extends EObject> eObjects, URI resourceURI) throws IOException {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(resourceURI);
		final EList<EObject> contents = resource.getContents();

		for (final EObject eObject : eObjects) {
			contents.add(eObject);
		}

		contents.addAll(eObjects);
		resource.save(null);
	}

}
