/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecp.view.internal.core.swt.ComboUtil;
import org.eclipse.emf.ecp.view.internal.core.swt.MatchItemComboViewer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.internal.core.swt.renderer.EnumComboViewerSWTRenderer#createComboViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected ComboViewer createComboViewer(final Composite parent, final EEnum eEnum) {
		final CCombo combo = new CCombo(parent, SWT.BORDER);
		combo.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(final Event argEvent) {
				combo.setText(combo.getText());
			}
		});
		combo.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				combo.clearSelection();
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		combo.setEditable(true);

		final MatchItemComboViewer viewer = new MatchItemComboViewer(combo) {
			@Override
			public void onEnter() {
				final int selectedIndex = ComboUtil.getClosestMatchIndex(getCCombo().getItems(),
					getBuffer().asString());
				if (!getBuffer().isEmpty() && selectedIndex > -1) {
					final String closestMatch = getCCombo().getItems()[selectedIndex];
					final Optional<EEnumLiteral> findLiteral = findLiteral(eEnum.getELiterals(), closestMatch);
					if (findLiteral.isPresent()) {
						setSelection(new StructuredSelection(findLiteral.get().getInstance()));
					}
				} else {
					setClosestMatch(getCCombo().getText());
				}
				combo.clearSelection();
			}
		};

		return viewer;
	}

	/**
	 * Search the given collection of {@link org.eclipse.emf.common.util.Enumerator Enumerator}s for the given literal.
	 *
	 * @param enumerators a collection of {@link org.eclipse.emf.common.util.Enumerator Enumerator}s to be searched
	 * @param literal the literal to be searched for as a string
	 * @return an {@link Optional} containing the matched literal
	 */
	private static Optional<EEnumLiteral> findLiteral(
		List<EEnumLiteral> enumerators, String literal) {

		for (final EEnumLiteral e : enumerators) {
			if (e.getLiteral().equals(literal)) {
				return Optional.of(e);
			}
		}

		return Optional.empty();
	}
}
