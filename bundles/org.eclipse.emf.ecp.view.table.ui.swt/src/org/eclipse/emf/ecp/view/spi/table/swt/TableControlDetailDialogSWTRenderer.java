/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import java.util.Collections;

import javax.inject.Inject;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * Render for a {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl VTableControl} with a detail editing
 * dialog.
 *
 * @author jfaltermeier
 *
 */
public class TableControlDetailDialogSWTRenderer extends TableControlSWTRenderer {

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
	 * @since 1.6
	 */
	@Inject
	public TableControlDetailDialogSWTRenderer(VTableControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			imageRegistryService, emfFormsEditSupport);
	}

	private Button detailEditButton;
	private VView view;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer#addButtonsToButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected int addButtonsToButtonBar(Composite buttonComposite) {
		createDetailEditButton(buttonComposite);
		return 1;
	}

	private void createDetailEditButton(final Composite buttonComposite) {
		detailEditButton = new Button(buttonComposite, SWT.PUSH);
		// detailEditButton.setText("Edit in Detail");
		detailEditButton.setImage(getImage("icons/detailEdit.png")); //$NON-NLS-1$
		detailEditButton.setEnabled(false);
		detailEditButton.addSelectionListener(new DetailEditButtonSelectionAdapter(buttonComposite.getShell()));
	}

	private VView getView() {
		if (view == null) {
			VView detailView = getVElement().getDetailView();
			if (detailView == null) {
				IValueProperty valueProperty;
				try {
					valueProperty = getEMFFormsDatabinding()
						.getValueProperty(getVElement().getDomainModelReference(),
							getViewModelContext().getDomainModel());
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
					return null; // possible because the only caller is null safe.
				}
				final EReference reference = (EReference) valueProperty.getValueType();
				detailView = ViewProviderHelper.getView(EcoreUtil.create(reference.getEReferenceType()),
					Collections.<String, Object> emptyMap());
			}
			view = detailView;
		}
		return EcoreUtil.copy(view);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer#viewerSelectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	protected void viewerSelectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			if (detailEditButton != null) {
				detailEditButton.setEnabled(false);
			}
		}
		else {
			if (detailEditButton != null && IStructuredSelection.class.cast(event.getSelection()).size() == 1) {
				detailEditButton.setEnabled(true);
			}
		}
		super.viewerSelectionChanged(event);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		detailEditButton = null;
		super.dispose();
	}

	/**
	 * {@link SelectionAdapter} used for the detail edit button.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class DetailEditButtonSelectionAdapter extends SelectionAdapter {

		private final Shell shell;

		public DetailEditButtonSelectionAdapter(Shell shell) {
			this.shell = shell;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);

			final Dialog dialog = createDialog();

			new ECPDialogExecutor(dialog) {
				@Override
				public void handleResult(int codeResult) {
					// no op
				}
			}.execute();
		}

		/**
		 * @param buttonComposite
		 * @return
		 */
		private Dialog createDialog() {
			Dialog dialog;
			if (getTableViewer().getSelection().isEmpty()) {
				dialog = new MessageDialog(shell, "No Table Selection", null, //$NON-NLS-1$
					"You must select one element in order to edit it.", MessageDialog.WARNING, new String[] { //$NON-NLS-1$
					JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

			} else if (getView() == null) {
				dialog = new MessageDialog(
					shell,
					"No View Model", null, //$NON-NLS-1$
					"Detail editing is not possible since there is no UI description for the selection.", MessageDialog.ERROR, new String[] { //$NON-NLS-1$
					JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);
			} else {
				dialog = new DetailDialog(shell, (EObject) IStructuredSelection.class.cast(
					getTableViewer().getSelection()).getFirstElement(), getVElement(), getView());
			}
			return dialog;
		}
	}

}
