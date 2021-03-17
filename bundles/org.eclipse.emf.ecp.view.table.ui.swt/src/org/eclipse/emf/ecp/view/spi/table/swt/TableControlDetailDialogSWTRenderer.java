/*******************************************************************************
 * Copyright (c) 2011-2020 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation, Bug 566073
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import javax.inject.Inject;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.table.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.action.TableActionIconButton;
import org.eclipse.emf.ecp.view.spi.table.swt.action.TableRendererAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.TableRendererViewerActionContext;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfiguration;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfigurationBuilder;
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
	 * @param emfFormsDatabinding The {@link EMFFormsDatabindingEMF}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param imageRegistryService The {@link ImageRegistryService}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 * @since 1.8
	 */
	@Deprecated
	// BEGIN COMPLEX CODE
	public TableControlDetailDialogSWTRenderer(
		VTableControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider,
		ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport) {
		// END COMPLEX CODE
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
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 * @since 1.26
	 */
	@Inject
	// BEGIN COMPLEX CODE
	public TableControlDetailDialogSWTRenderer(
		VTableControl vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider,
		ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport,
		EMFFormsLocalizationService localizationService) {
		// END COMPLEX CODE
		super(vElement,
			viewContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			vtViewTemplateProvider,
			imageRegistryService,
			emfFormsEditSupport,
			localizationService);
	}

	private Button detailEditButton;
	private VView view;

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
				IValueProperty<?, ?> valueProperty;
				try {
					valueProperty = getEMFFormsDatabinding()
						.getValueProperty(getVElement().getDomainModelReference(),
							getViewModelContext().getDomainModel());
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
					return null; // possible because the only caller is null safe.
				}
				final EReference reference = (EReference) valueProperty.getValueType();
				final VElement viewModel = getViewModelContext().getViewModel();
				final VViewModelProperties properties = ViewModelPropertiesHelper
					.getInhertitedPropertiesOrEmpty(viewModel);
				detailView = ViewProviderHelper.getView(EcoreUtil.create(reference.getEReferenceType()),
					properties);
			}
			view = detailView;
		}
		final VView copy = EcoreUtil.copy(view);
		copy.setReadonly(
			!getVElement().isEffectivelyEnabled() || getVElement().isEffectivelyReadonly() || copy.isReadonly());
		return copy;
	}

	@Override
	protected void viewerSelectionChanged(SelectionChangedEvent event) {
		if (event.getSelection().isEmpty()) {
			if (detailEditButton != null) {
				detailEditButton.setEnabled(false);
			}
		} else {
			if (detailEditButton != null && IStructuredSelection.class.cast(event.getSelection()).size() == 1) {
				detailEditButton.setEnabled(true);
			}
		}
		super.viewerSelectionChanged(event);
	}

	@Override
	protected ActionConfiguration configureActions(TableRendererViewerActionContext actionContext) {
		final ActionConfigurationBuilder actionConfigBuilder = ActionConfigurationBuilder
			.usingConfiguration(super.configureActions(actionContext));

		final DetailEditAction detailEditAction = new DetailEditAction(actionContext);
		final Setting setting = actionContext.getSetting();
		final EClass eClass = ((EReference) setting.getEStructuralFeature()).getEReferenceType();

		actionConfigBuilder
			.addAction(detailEditAction)
			.addControlFor(detailEditAction, new TableActionIconButton(
				formatTooltipText(eClass, MessageKeys.TableControl_DetailEdit), getImage("icons/detailEdit.png"))); //$NON-NLS-1$

		return actionConfigBuilder.build();
	}

	@Override
	protected void dispose() {
		detailEditButton = null;
		super.dispose();
	}

	private Dialog createDialog(Shell shell) {
		Dialog dialog;
		if (getTableViewer().getSelection().isEmpty()) {
			dialog = new MessageDialog(shell, "No Table Selection", null, //$NON-NLS-1$
				"You must select one element in order to edit it.", MessageDialog.WARNING, new String[] { //$NON-NLS-1$
					JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) },
				0);

		} else if (getView() == null) {
			dialog = new MessageDialog(
				shell,
				"No View Model", null, //$NON-NLS-1$
				"Detail editing is not possible since there is no UI description for the selection.", //$NON-NLS-1$
				MessageDialog.ERROR, new String[] { JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) },
				0);
		} else {
			dialog = new DetailDialog(shell, (EObject) IStructuredSelection.class.cast(
				getTableViewer().getSelection()).getFirstElement(), getVElement(), getView());
		}
		return dialog;
	}

	private class DetailEditAction extends TableRendererAction {

		/**
		 * Default constructor.
		 *
		 * @param actionContext the {@link TableRendererViewerActionContext}.
		 */
		DetailEditAction(TableRendererViewerActionContext actionContext) {
			super(actionContext);
		}

		/**
		 * The ID of this action.
		 */
		public static final String ACTION_ID = PREFIX + "tablecontrol.dialog_edit"; //$NON-NLS-1$

		@Override
		public String getId() {
			return ACTION_ID;
		}

		@Override
		public void execute() {
			final Dialog dialog = createDialog(getTableViewer().getControl().getShell());

			new ECPDialogExecutor(dialog) {
				@Override
				public void handleResult(int codeResult) {
					// no op
				}
			}.execute();
		}

		@Override
		public boolean canExecute() {
			return !getTableViewer().getSelection().isEmpty();
		}

	}

	/**
	 * {@link SelectionAdapter} used for the detail edit button.
	 *
	 * @author jfaltermeier
	 *
	 */
	@Deprecated
	private class DetailEditButtonSelectionAdapter extends SelectionAdapter {

		private final Shell shell;

		DetailEditButtonSelectionAdapter(Shell shell) {
			this.shell = shell;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);

			final Dialog dialog = createDialog(shell);

			new ECPDialogExecutor(dialog) {
				@Override
				public void handleResult(int codeResult) {
					// no op
				}
			}.execute();
		}

	}

}
