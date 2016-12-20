/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecp.view.internal.table.nebula.grid.GridClearKeyListener;
import org.eclipse.emf.ecp.view.internal.table.nebula.grid.GridCopyKeyListener;
import org.eclipse.emf.ecp.view.internal.table.nebula.grid.GridCutKeyListener;
import org.eclipse.emf.ecp.view.internal.table.nebula.grid.GridPasteKeyListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundStyleProperty;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverterService;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCreator;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.ScrollBar;

/**
 * @author Jonas Helming
 * @since 1.10
 *
 */
public class GridControlSWTRenderer extends TableControlSWTRenderer {

	private final EStructuralFeatureValueConverterService converterService;

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
	 * @param converterService the {@link EStructuralFeatureValueConverterService}
	 * @since 1.11
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public GridControlSWTRenderer(VTableControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport, EStructuralFeatureValueConverterService converterService) {
		// CHECKSTYLE.ON: ParameterNumber
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			imageRegistryService, emfFormsEditSupport);
		this.converterService = converterService;
	}

	/**
	 * {@link TableViewerCreator} for the table control swt renderer. It will create a GridTableViewer with the expected
	 * custom variant data and the correct style properties as defined in the template model.
	 *
	 */
	protected final class GridTableControlSWTRendererTableViewerCreator implements TableViewerCreator<GridTableViewer> {

		@Override
		public GridTableViewer createTableViewer(Composite parent) {

			final GridTableViewer tableViewer = new GridTableViewer(parent,
				SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
			tableViewer.getGrid().setData(CUSTOM_VARIANT, TABLE_CUSTOM_VARIANT);
			tableViewer.getGrid().setHeaderVisible(true);
			tableViewer.getGrid().setLinesVisible(true);
			tableViewer.getGrid().setCellSelectionEnabled(true);
			tableViewer.getGrid().setFooterVisible(false);

			tableViewer.getGrid().addKeyListener(new GridCopyKeyListener(tableViewer.getGrid().getDisplay()));
			tableViewer.getGrid()
				.addKeyListener(new GridPasteKeyListener(tableViewer.getGrid().getDisplay(), getVElement(),
					getEMFFormsDatabinding(), converterService, true));
			tableViewer.getGrid().addKeyListener(new GridClearKeyListener(getVElement(), getEMFFormsDatabinding()));
			tableViewer.getGrid().addKeyListener(
				new GridCutKeyListener(tableViewer.getGrid().getDisplay(), getVElement(), getEMFFormsDatabinding()));
			// TODO MS
			// tableViewer.getGrid().addKeyListener(new GridNewLineKeyListener() {
			//
			// @Override
			// public void appendNewRow() {
			//
			// try {
			// final Setting setting = getEMFFormsDatabinding().getSetting(getDMRToMultiReference(),
			// getViewModelContext().getDomainModel());
			// final EObject eObject = setting.getEObject();
			// final EStructuralFeature structuralFeature = setting.getEStructuralFeature();
			// final EClass clazz = ((EReference) structuralFeature).getEReferenceType();
			//
			// addRow(clazz, eObject, structuralFeature);
			// } catch (final DatabindingFailedException ex) {
			// // nothing to do
			// }
			//
			// }
			//
			// });

			/* Set background color */
			final VTBackgroundStyleProperty backgroundStyleProperty = getBackgroundStyleProperty();
			if (backgroundStyleProperty.getColor() != null) {
				tableViewer.getGrid().setBackground(getSWTColor(backgroundStyleProperty.getColor()));
			}

			/* Set foreground color */
			final VTFontPropertiesStyleProperty fontPropertiesStyleProperty = getFontPropertiesStyleProperty();
			if (fontPropertiesStyleProperty.getColorHEX() != null) {
				tableViewer.getGrid()
					.setForeground(getSWTColor(fontPropertiesStyleProperty.getColorHEX()));
			}

			tableViewer.getGrid().setData(FIXED_COLUMNS, new Integer(1));

			/* manage editing support activation */
			createTableViewerEditor(tableViewer);

			return tableViewer;
		}

		/**
		 * This method creates and initialises a {@link GridViewerEditor} for the given {@link GridTableViewer}.
		 *
		 * @param gridTableViewer the table viewer
		 */
		protected void createTableViewerEditor(final GridTableViewer gridTableViewer) {
			// TODO Grid
			// final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tableViewer,
			// new org.eclipse.emf.ecp.edit.internal.swt.controls.ECPFocusCellDrawHighlighter(tableViewer));

			// final TableViewer tableViewer = (TableViewer) getTableViewer();
			// final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(
			// (TableViewer) gridTableViewer,
			// new CustomFocusCellHighlighter(gridTableViewer);

			final ColumnViewerEditorActivationStrategy actSupport = new GridColumnViewerEditorActivationStrategy(
				gridTableViewer);
			actSupport.setEnableEditorActivationWithKeyboard(true);
			GridViewerEditor.create(
				gridTableViewer,
				actSupport,
				ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
					| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		}
	}

	@Override
	protected TableViewerCreator<GridTableViewer> getTableViewerCreator() {
		return new GridTableControlSWTRendererTableViewerCreator();
	}

	@Override
	// CHECKSTYLE.OFF: ParameterNumber
	protected TableViewerSWTBuilder getTableViewerSWTBuilder(Composite parent, IObservableList list,
		IObservableValue labelText, IObservableValue labelTooltipText, TableViewerCompositeBuilder compositeBuilder,
		ObservableListContentProvider cp, ECPTableViewerComparator comparator,
		TableControlSWTRendererButtonBarBuilder tableControlSWTRendererButtonBarBuilder) {
		// CHECKSTYLE.ON: ParameterNumber
		return GridTableViewerFactory.fillDefaults(parent, SWT.NONE, list, labelText, labelTooltipText)
			.customizeCompositeStructure(compositeBuilder)
			.customizeButtons(tableControlSWTRendererButtonBarBuilder)
			.customizeTableViewerCreation(getTableViewerCreator())
			.customizeContentProvider(cp)
			.customizeComparator(comparator);

	}

	@Override
	protected int getSelectionIndex() {
		return ((GridTableViewer) getTableViewer()).getGrid().getSelectionIndex();
	}

	@Override
	protected Item[] getColumns() {
		return ((GridTableViewer) getTableViewer()).getGrid().getColumns();
	}

	@Override
	protected ScrollBar getHorizontalBar() {
		return ((GridTableViewer) getTableViewer()).getGrid().getHorizontalBar();
	}

	@Override
	protected ScrollBar getVerticalBar() {
		return ((GridTableViewer) getTableViewer()).getGrid().getVerticalBar();
	}

	/**
	 * EditorActivationStrategy for GridColumns.
	 *
	 * @author Stefan Dirix
	 */
	private class GridColumnViewerEditorActivationStrategy extends ColumnViewerEditorActivationStrategy {

		private final GridTableViewer gridTableViewer;

		/**
		 * Constructor.
		 *
		 * @param viewer the {@link GridTableViewer}.
		 */
		GridColumnViewerEditorActivationStrategy(GridTableViewer gridTableViewer) {
			super(gridTableViewer);
			this.gridTableViewer = gridTableViewer;
		}

		@Override
		protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
			if (event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
				|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
				|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC) {
				return true;
			}
			if (event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
				&& gridTableViewer.isCellEditorActive()) {
				gridTableViewer.applyEditorValue();
			}
			if (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED) {
				for (final int keyCode : Arrays.asList(SWT.CTRL, SWT.ALT, SWT.SHIFT)) {
					if ((event.keyCode & keyCode) != 0 || (event.stateMask & keyCode) != 0) {
						return false;
					}
				}
				return !isDoNotEnterEditorCode(event.keyCode);
			}
			return false;
		}

		private boolean isDoNotEnterEditorCode(int keyCode) {
			// BEGIN COMPLEX CODE
			return keyCode == SWT.ARROW_UP || keyCode == SWT.ARROW_DOWN
				|| keyCode == SWT.ARROW_LEFT || keyCode == SWT.ARROW_RIGHT
				|| keyCode == SWT.TAB || keyCode == SWT.DEL;
			// END COMPLEX CODE
		}
	}

}
