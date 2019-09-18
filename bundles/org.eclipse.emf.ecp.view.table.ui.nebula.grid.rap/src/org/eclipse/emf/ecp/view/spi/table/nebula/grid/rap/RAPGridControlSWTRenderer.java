/**
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid.rap;

import java.util.Arrays;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
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
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.table.TableControl;
import org.eclipse.emfforms.spi.swt.table.TableViewerCompositeBuilder;
import org.eclipse.emfforms.spi.swt.table.TableViewerCreator;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder;
import org.eclipse.emfforms.spi.swt.table.action.TableActionBar;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Widget;

/**
 * @author Lucas Koehler
 * @since 1.17
 *
 */
public class RAPGridControlSWTRenderer extends TableControlSWTRenderer {

	private final EStructuralFeatureValueConverterService converterService;
	private final EMFFormsLocalizationService localizationService;

	/**
	 * Custom Nebula Grid table viewer to expose getViewerRowFromItem() method.
	 *
	 * @author Mat Hansen
	 *
	 */
	public class CustomGridTableViewer extends GridTableViewer {

		/**
		 * Creates a grid viewer on a newly-created grid control under the given
		 * parent. The grid control is created using the given SWT style bits. The
		 * viewer has no input, no content provider, a default label provider, no
		 * sorter, and no filters.
		 *
		 * @param parent
		 *            the parent control
		 * @param style
		 *            the SWT style bits used to create the grid.
		 */
		public CustomGridTableViewer(Composite parent, int style) {
			super(parent, style);
		}

		// make method public
		@Override
		public ViewerRow getViewerRowFromItem(Widget item) {
			return super.getViewerRowFromItem(item);
		}
	}

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
	 * @param localizationService the {@link EMFFormsLocalizationService}
	 * @since 1.11
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public RAPGridControlSWTRenderer(VTableControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabindingEMF emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, ImageRegistryService imageRegistryService,
		EMFFormsEditSupport emfFormsEditSupport, EStructuralFeatureValueConverterService converterService,
		EMFFormsLocalizationService localizationService) {
		// CHECKSTYLE.ON: ParameterNumber
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			imageRegistryService, emfFormsEditSupport, localizationService);
		this.converterService = converterService;
		this.localizationService = localizationService;
	}

	/**
	 * {@link TableViewerCreator} for the table control swt renderer. It will create a GridTableViewer with the expected
	 * custom variant data and the correct style properties as defined in the template model.
	 *
	 */
	protected class GridTableControlSWTRendererTableViewerCreator implements TableViewerCreator<GridTableViewer> {

		@Override
		public GridTableViewer createTableViewer(Composite parent) {

			final GridTableViewer tableViewer = new CustomGridTableViewer(parent,
				SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
			tableViewer.getGrid().setData(CUSTOM_VARIANT, TABLE_CUSTOM_VARIANT);
			tableViewer.getGrid().setHeaderVisible(true);
			tableViewer.getGrid().setLinesVisible(true);
			tableViewer.getGrid().setFooterVisible(false);

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
	protected TableViewerSWTBuilder createTableViewerSWTBuilder(Composite parent, IObservableList list,
		IObservableValue labelText, IObservableValue labelTooltipText, TableViewerCompositeBuilder compositeBuilder,
		ObservableListContentProvider cp, ECPTableViewerComparator comparator,
		TableActionBar<? extends AbstractTableViewer> actionBar) {
		// CHECKSTYLE.ON: ParameterNumber
		return GridTableViewerFactory.fillDefaults(parent, SWT.NONE, list, labelText, labelTooltipText)
			.customizeCompositeStructure(compositeBuilder)
			.customizeActionBar(actionBar)
			.customizeTableViewerCreation(getTableViewerCreator())
			.customizeContentProvider(cp)
			.customizeComparator(comparator)
			.showHideColumns(true)
			.columnSubstringFilter(true);

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

	@Override
	protected int computeRequiredHeight(Integer visibleLines) {
		if (getTableViewer() == null || getTableViewerComposite() == null) {
			return SWT.DEFAULT;
		}
		final TableControl table = getTableViewerComposite().getTableControl();
		if (table == null) {
			return SWT.DEFAULT;
		}
		if (table.isDisposed()) {
			return SWT.DEFAULT;
		}
		final int itemHeight = table.getItemHeight() + 1;
		// show one empty row if table does not contain any items or visibleLines < 1
		int itemCount;
		if (visibleLines != null) {
			itemCount = Math.max(visibleLines, 1);
		} else {
			itemCount = Math.max(table.getItemCount(), 1);
		}
		final int headerHeight = table.getHeaderVisible() ? table.getHeaderHeight() : 0;

		final int tableHeight = itemHeight * itemCount + headerHeight;
		return tableHeight;
	}

	/**
	 *
	 * @return the {@link EStructuralFeatureValueConverterService}
	 */
	protected EStructuralFeatureValueConverterService getConverterService() {
		return converterService;
	}

	/**
	 *
	 * @return the {@link EMFFormsLocalizationService}
	 */
	protected EMFFormsLocalizationService getLocalizationService() {
		return localizationService;
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
				// TODO Does not work in RAP
				// gridTableViewer.applyEditorValue();
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
