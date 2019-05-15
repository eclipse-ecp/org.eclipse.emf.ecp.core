/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.ui.rcp;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlDetailPanelRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * {@link TableControlDetailPanelRenderer} which also supports cut/copy/paste.
 *
 * @author Johannes Faltermeier
 *
 */
public class TableControlDetailPanelRCPRenderer extends TableControlDetailPanelRenderer {

	private CutCopyPasteListener cutCopyPasteListener;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param imageRegistryService The {@link ImageRegistryService}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 * @since 1.8
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public TableControlDetailPanelRCPRenderer(
		VTableControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider,
		ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport) {
		// CHECKSTYLE.ON: ParameterNumber
		super(
			vElement,
			viewContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			vtViewTemplateProvider,
			imageRegistryService,
			emfFormsEditSupport);
	}

	@Override
	protected Control renderTableControl(SWTGridCell gridCell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Control control = super.renderTableControl(gridCell, parent);
		if (AbstractTableViewerComposite.class.isInstance(control)) {
			try {
				final AbstractTableViewer tableViewer = AbstractTableViewerComposite.class.cast(control)
					.getTableViewer();
				final EditingDomain editingDomain = getEditingDomain(getViewModelContext().getDomainModel());
				final Setting setting = getEMFFormsDatabinding().getSetting(getDMRToMultiReference(),
					getViewModelContext().getDomainModel());
				cutCopyPasteListener = TableControlRCPRenderer.enableCutCopyPaste(tableViewer, editingDomain, setting);
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new AbstractReport(ex, "Enabling Cut Copy Paste failed")); //$NON-NLS-1$
			}
		}
		return control;
	}

	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		super.rootDomainModelChanged();
		final Setting setting = getEMFFormsDatabinding().getSetting(getDMRToMultiReference(),
			getViewModelContext().getDomainModel());
		cutCopyPasteListener.rootDomainModelChanged(setting);
	}
}
