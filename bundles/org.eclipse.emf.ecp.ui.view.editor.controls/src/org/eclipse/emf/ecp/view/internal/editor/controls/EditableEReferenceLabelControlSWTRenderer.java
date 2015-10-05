/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Eugen Neufeld
 *
 */
public abstract class EditableEReferenceLabelControlSWTRenderer extends EReferenceLabelControlSWTRenderer {

	private final EMFDataBindingContext viewModelDBC;

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 */
	public EditableEReferenceLabelControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
		viewModelDBC = new EMFDataBindingContext();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.ControlRootEClassControl2SWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createSWTControl(final Composite parent2) throws DatabindingFailedException {
		final Composite composite = (Composite) super.createSWTControl(parent2);

		GridLayoutFactory.fillDefaults().numColumns(3).spacing(0, 0).equalWidth(false).applyTo(composite);

		final EMFFormsLabelProvider labelProvider = Activator.getDefault().getEMFFormsLabelProvider();
		final Button selectClass = new Button(composite, SWT.PUSH);
		try {
			final IObservableValue labelText = labelProvider.getDisplayName(getVElement().getDomainModelReference(),
				getViewModelContext().getDomainModel());
			final IObservableValue tooltip = labelProvider.getDescription(getVElement().getDomainModelReference(),
				getViewModelContext().getDomainModel());

			viewModelDBC.bindValue(SWTObservables.observeText(selectClass), labelText, null, new UpdateValueStrategy() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
				 */
				@Override
				public Object convert(Object value) {
					final String result = (String) super.convert(value);
					return "Link " + result; //$NON-NLS-1$
				}

			});
			viewModelDBC.bindValue(SWTObservables.observeTooltipText(selectClass), tooltip, null,
				new UpdateValueStrategy() {

					/**
					 * {@inheritDoc}
					 *
					 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
					 */
					@Override
					public Object convert(Object value) {
						final String result = (String) super.convert(value);
						return "Link " + result; //$NON-NLS-1$
					}

				});
		} catch (final NoLabelFoundException e) {
			// FIXME Expectation?
			getReportService().report(new RenderingFailedReport(e));
			selectClass.setText("Link "); //$NON-NLS-1$
			selectClass.setToolTipText("Link "); //$NON-NLS-1$
		}
		selectClass.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				linkValue(composite.getShell());
				composite.layout(true, true);
			}

		});

		final Button unset = new Button(composite, SWT.PUSH);
		unset.setText("Unset"); //$NON-NLS-1$
		unset.setToolTipText("Unset"); //$NON-NLS-1$
		final IObservableValue observableValue = Activator.getDefault().getEMFFormsDatabinding()
			.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		unset.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				eObject.eUnset(structuralFeature);
				composite.layout(true, true);
			}

		});

		return composite;
	}

	/**
	 * This method should be overwritten to provide a correct selection mechanism.
	 *
	 * @param shell the Shell
	 */
	protected abstract void linkValue(Shell shell);

	/**
	 * Shows an error message dialog indicating a failed value link due to an exception.
	 *
	 * @param shell The parent {@link Shell} of the message dialog
	 * @param ex The {@link Exception} causing the failure
	 */
	protected void showLinkValueFailedMessageDialog(Shell shell, final Exception ex) {
		final MessageDialog dialog = new MessageDialog(
			shell, "Link Value Failed", null, //$NON-NLS-1$
			"The value could not be linked due to an exception: " + ex.getMessage(), MessageDialog.ERROR, new String[] { //$NON-NLS-1$
				JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) },
			0);

		new ECPDialogExecutor(dialog) {
			@Override
			public void handleResult(int codeResult) {
				// no op
			}
		}.execute();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.editor.controls.EReferenceLabelControlSWTRenderer#dispose()
	 */
	@Override
	public void dispose() {
		viewModelDBC.dispose();
		super.dispose();
	}
}
