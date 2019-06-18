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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.io.IOException;
import java.text.MessageFormat;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.view.control.multiattribute.MultiAttributeSWTRenderer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Custom Renderer for the EcorePath List of a View. This Renderer changes the behavior of the add and remove button so
 * that a user gets a Dialog to select an Ecore from the Workspace in the case of an add. In the case of a remove the
 * Ecore is deregistered.
 *
 * @author Eugen Neufeld
 *
 */
public class EcorePathsRenderer extends MultiAttributeSWTRenderer {

	private Composite parent;
	private IObservableList list;

	/**
	 * Default constructor.
	 *
	 * @param vElement The {@link VControl} of the Renderer
	 * @param viewContext The {@link ViewModelContext} of the Renderer
	 * @param reportService The {@link ReportService} to use for logging
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding} to use
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider} to use
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider} to use
	 * @param imageRegistryService The {@link ImageRegistryService} to use
	 */
	@Inject
	public EcorePathsRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			imageRegistryService);
	}

	@Override
	public void finalizeRendering(Composite parent) {
		super.finalizeRendering(parent);
		this.parent = parent;
	}

	@Override
	protected void initButtons(IObservableList list) {
		this.list = list;
		super.initButtons(list);
	}

	@Override
	protected Object getValueForNewRow(EAttribute attribute) {
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(parent.getShell(),
			new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.setAllowMultiple(false);
		dialog.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length == 1 && selection[0] instanceof IFile) {
					final IFile file = (IFile) selection[0];
					if (file.getType() == IResource.FILE && "ecore".equalsIgnoreCase(file.getFileExtension())) { //$NON-NLS-1$
						if (list.contains(file.getFullPath().toString())) {
							return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR,
								MessageFormat.format(LocalizationServiceHelper.getString(EcorePathsRenderer.class,
									"EcorePath_AddDialog_Status_AlreadyAdded"), file.getName()), //$NON-NLS-1$
								null);
						}
						return new Status(IStatus.OK, Activator.PLUGIN_ID, IStatus.OK, null, null);
					}
				}
				return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR,
					LocalizationServiceHelper.getString(EcorePathsRenderer.class,
						"EcorePath_AddDialog_Status_NotEcore"), //$NON-NLS-1$
					null);
			}
		});

		dialog.setTitle(LocalizationServiceHelper.getString(EcorePathsRenderer.class, "EcorePath_AddDialog_Title")); //$NON-NLS-1$
		dialog.setMessage(LocalizationServiceHelper.getString(EcorePathsRenderer.class, "EcorePath_AddDialog_Message")); //$NON-NLS-1$

		if (dialog.open() == Window.OK) {
			final String path = ((IFile) dialog.getFirstResult()).getFullPath().toString();
			try {
				EcoreHelper.registerEcore(path);
			} catch (final IOException ex) {
				getReportService().report(new AbstractReport(ex));
				return null;
			}
			return path;
		}
		return null;
	}

	@Override
	protected void postRemove(IStructuredSelection selection) {
		for (final Object toDelete : selection.toList()) {
			EcoreHelper.unregisterEcore(toDelete.toString());
		}
	}

	@Override
	protected void createUpDownButtons(Composite composite, IObservableList list) {
		// do nothing
	}

}
