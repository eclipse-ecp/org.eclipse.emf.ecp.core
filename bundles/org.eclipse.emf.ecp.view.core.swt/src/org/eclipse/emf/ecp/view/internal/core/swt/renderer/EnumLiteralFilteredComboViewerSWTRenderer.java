/*******************************************************************************
 * Copyright (c) 2017-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Lucas Koehler - adapt matching for internationalization
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecp.view.internal.core.swt.MatchItemComboViewer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Renderer for enums that makes use of a {@link MatchItemComboViewer}.
 *
 */
public class EnumLiteralFilteredComboViewerSWTRenderer extends EnumComboViewerSWTRenderer {

	private MatchItemComboViewer viewer;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	@Inject
	public EnumLiteralFilteredComboViewerSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			emfFormsEditSupport);
	}

	@Override
	protected ComboViewer createComboViewer(final Composite parent, final EEnum eEnum) {
		final CCombo combo = new CCombo(parent, SWT.BORDER);
		combo.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(final Event argEvent) {
				combo.setText(combo.getText());
			}
		});
		combo.setEditable(true);

		viewer = new MatchItemComboViewer(combo) {
			@Override
			public void onEnter() {
				// Trigger databinding for current selection
				fireViewerSelectionEvents();
				combo.clearSelection();
			}
		};

		combo.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				fireViewerSelectionEvents();
				combo.clearSelection();
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		return viewer;
	}

	/**
	 * Triggers the combo viewer's selection events by re-setting its selection.
	 * <p>
	 * When typing in the match item viewer's combo box, the selection is updated correctly in the underlying combo.
	 * However, the databinding is not triggered because StructuredViewer#updateSelection does not fire post selection
	 * change events. To trigger the databinding, we manually set the selection to the viewer.
	 * We do not set empty selections because the user cannot set an empty value and the ccombo sometimes
	 * returns an empty selection when manually setting the cursor in the combo box (even though a value is
	 * shown in the CCombo).
	 */
	private void fireViewerSelectionEvents() {
		final IStructuredSelection selection = viewer.getStructuredSelection();
		if (!selection.isEmpty()) {
			viewer.setSelection(selection);
		}
	}
}
