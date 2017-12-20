/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverterService;
import org.eclipse.emfforms.spi.common.validation.PreSetValidationService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.table.TableConfiguration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * {@link KeyListener} for the paste action on a {@link Grid} control.
 *
 * @author Alexandra Buzila
 * @author Mathias Schaefer
 * @since 1.10
 *
 */
public class GridPasteKeyListener implements KeyListener {

	private static final String TAB = "\t"; //$NON-NLS-1$
	private static final String NT = "\n\t"; //$NON-NLS-1$
	private static final String IS_INPUTTABLE = "isInputtable"; //$NON-NLS-1$
	private final Clipboard clipboard;
	private final EMFFormsDatabindingEMF dataBinding;
	private final EStructuralFeatureValueConverterService converterService;
	private final VControl vControl;

	private boolean selectPastedCells = true;
	private boolean alreadyPasted;
	private final PreSetValidationService preSetValidationService;
	private final Display display;
	private final EMFFormsLocalizationService localizationService;

	/**
	 * Constructor.
	 *
	 * @param display the {@link Display} on which to allocate this command's {@link Clipboard}.
	 * @param vControl the {@link VTableControl}.
	 * @param dataBinding {@link EMFFormsDatabindingEMF}
	 * @param converterService {@link EStructuralFeatureValueConverterService}
	 * @param localizationService {@link EMFFormsLocalizationService}
	 * @param selectPastedCells whether to select the pasted cells
	 */
	public GridPasteKeyListener(Display display, VControl vControl, EMFFormsDatabindingEMF dataBinding,
		EStructuralFeatureValueConverterService converterService, EMFFormsLocalizationService localizationService,
		boolean selectPastedCells) {
		this.display = display;
		this.localizationService = localizationService;
		clipboard = new Clipboard(display);
		this.vControl = vControl;
		this.dataBinding = dataBinding;
		this.converterService = converterService;
		this.selectPastedCells = selectPastedCells;

		final BundleContext bundleContext = FrameworkUtil
			.getBundle(getClass())
			.getBundleContext();

		final ServiceReference<PreSetValidationService> serviceReference = bundleContext
			.getServiceReference(PreSetValidationService.class);

		preSetValidationService = serviceReference != null ? bundleContext.getService(serviceReference) : null;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.stateMask & SWT.CTRL) != 0 && e.keyCode == 'v') {
			if (!alreadyPasted) {
				final Grid grid = (Grid) e.widget;
				final Object contents = clipboard.getContents(TextTransfer.getInstance());
				if (contents instanceof String) {
					pasteSelection(grid, (String) contents);
				}
				alreadyPasted = true;
			}
		} else {
			alreadyPasted = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/* no op */
	}

	/**
	 * Pastes the given contents in the grid.
	 *
	 * @param grid the target {@link Grid}
	 * @param contents the contents to paste
	 */
	public void pasteSelection(Grid grid, String contents) {

		if (grid.getCellSelection().length == 0 || !getControl().isEnabled() || getControl().isReadonly()) {
			return;
		}

		final List<Point> pastedCells = new ArrayList<Point>();
		if (grid.getCellSelection().length > 1 /* more than one cell selected */ &&
			new StringTokenizer(contents, NT, false).countTokens() == 1 /* contents are on one line */ &&
			fillSelectionWithMultipleCopies(grid.getCellSelection(), contents)) {

			// fill selection
			for (final Point startItem : getFillStartPoints(grid.getCellSelection())) {
				pastedCells.addAll(pasteContents(startItem, grid, contents));
			}

		} else {

			// expand selection
			final Point startItem = grid.getCellSelection()[0];
			pastedCells.addAll(pasteContents(startItem, grid, contents));
		}

		if (selectPastedCells && !pastedCells.isEmpty() && grid.isCellSelectionEnabled()) {
			grid.setCellSelection(pastedCells.toArray(new Point[] {}));
		}

	}

	/**
	 * Extract the start points for filling paste from the grid's cell selection.
	 *
	 * @param cellSelection the cell selection
	 * @return the start points
	 */
	static Point[] getFillStartPoints(Point[] cellSelection) {
		final Map<Integer, Set<Integer>> rowToSelectedColumns = createSelectionMap(cellSelection);
		final Point[] result = new Point[rowToSelectedColumns.size()];

		final Set<Integer> columns = rowToSelectedColumns.values().iterator().next();
		final int startColumn = Collections.min(columns);

		int i = 0;
		for (final Integer startRow : rowToSelectedColumns.keySet()) {
			result[i++] = new Point(startColumn, startRow);
		}

		return result;
	}

	/**
	 * Whether the paste logic which will paste the same contents in every selected row is applicable.
	 * Precondition is that multiple cells are selected and contents does not contain a new line.
	 *
	 * @param cellSelection the selected cells
	 * @param contents the contents to paste
	 * @return <code>true</code> if paste should happen, <code>false</code> otherwise
	 */
	static boolean fillSelectionWithMultipleCopies(Point[] cellSelection, String contents) {
		/* build up data struc to analyse selection */
		final Map<Integer, Set<Integer>> rowToSelectedColumns = createSelectionMap(cellSelection);

		/* multiple rows have to be selected */
		if (rowToSelectedColumns.size() < 2) {
			return false;
		}

		/* column selection has to be uniform */
		final Iterator<Set<Integer>> columnSetInterator = rowToSelectedColumns.values().iterator();
		final Set<Integer> referenceSet = columnSetInterator.next();
		while (columnSetInterator.hasNext()) {
			final Set<Integer> next = columnSetInterator.next();
			if (!referenceSet.equals(next)) {
				return false;
			}
		}

		/* if only one column selected, we are fine */
		if (referenceSet.size() == 1) {
			return true;
		}

		/* otherwise selected column count and pasted column count has to match */
		if (contents.split(TAB).length != referenceSet.size()) {
			return false;
		}

		/* and selected columns have to lie next to each other */
		final ArrayList<Integer> selectedColumnIndices = new ArrayList<Integer>(referenceSet);
		Collections.sort(selectedColumnIndices);
		final Iterator<Integer> selectedColumnIndicesIterator = selectedColumnIndices.iterator();
		Integer ref = selectedColumnIndicesIterator.next();
		while (selectedColumnIndicesIterator.hasNext()) {
			if (++ref != selectedColumnIndicesIterator.next()) {
				return false;
			}
		}

		/* all fine */
		return true;
	}

	private static Map<Integer, Set<Integer>> createSelectionMap(Point[] cellSelection) {
		final Map<Integer, Set<Integer>> rowToSelectedColumns = new LinkedHashMap<Integer, Set<Integer>>();
		for (final Point point : cellSelection) {
			if (!rowToSelectedColumns.containsKey(point.y)) {
				rowToSelectedColumns.put(point.y, new LinkedHashSet<Integer>());
			}
			rowToSelectedColumns.get(point.y).add(point.x);
		}
		return rowToSelectedColumns;
	}

	/**
	 * Performs the paste operation.
	 *
	 * @param startItem the start item
	 * @param grid the grid
	 * @param contents the pasted contents
	 * @return the pasted cells
	 */
	// BEGIN COMPLEX CODE
	@SuppressWarnings("restriction")
	public List<Point> pasteContents(Point startItem, Grid grid, String contents) {
		final int startColumn = startItem.x;
		final int startRow = startItem.y;

		final List<Point> pastedCells = new ArrayList<Point>();
		final List<String> invalidValues = new ArrayList<String>();
		int relativeRow = 0;
		final String[] rows = contents.split("\r\n|\n", -1); //$NON-NLS-1$

		prePasteContents();

		try {
			for (final String row : rows) {

				int relativeColumn = 0;

				for (final String cellValueSplit : row.split(TAB, -1)) {

					final String cellValue = modifyCellValue(cellValueSplit);

					final int insertionColumnIndex = startColumn + relativeColumn;
					final int insertionRowIndex = startRow + relativeRow;

					if (insertionColumnIndex >= grid.getColumnCount()) {
						relativeColumn++;
						continue;
					}

					final VDomainModelReference dmr = (VDomainModelReference) grid.getColumn(insertionColumnIndex)
						.getData(TableConfiguration.DMR);

					if (dmr == null || getControl() instanceof VTableControl
						&& org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper
							.isReadOnly((VTableControl) getControl(), dmr)) {
						relativeColumn++;
						continue;
					}

					if (insertionRowIndex < grid.getItemCount()) {

						final EObject eObject = (EObject) grid.getItem(insertionRowIndex).getData();

						if (isEObjectReadOnly(eObject)) {
							continue;
						}

						IObservableValue value = null;
						try {
							value = dataBinding.getObservableValue(dmr, eObject);
							final EStructuralFeature feature = (EStructuralFeature) value.getValueType();
							final Object convertedValue = getConverterService().convertToModelValue(eObject,
								feature, cellValue);

							if (isSettingReadOnly(eObject, feature, convertedValue)) {
								continue;
							}

							boolean valid = convertedValue != null;

							if (preSetValidationService != null) {
								final Map<Object, Object> context = new LinkedHashMap<Object, Object>();
								context.put("rootEObject", IObserving.class.cast(value).getObserved());//$NON-NLS-1$
								final Diagnostic diag = preSetValidationService.validate(
									feature, valid ? convertedValue : cellValue, context);
								valid = diag.getSeverity() == Diagnostic.OK;
								if (!valid) {
									invalidValues.add(extractDiagnosticMessage(diag, feature, cellValue));
								}
							}

							final EObject observedEobject = (EObject) ((IObserving) value).getObserved();
							final Setting setting = ((InternalEObject) observedEobject).eSetting(feature);
							if (!canBePasted(feature, cellValue, eObject, setting)) {
								invalidValues.add(cellValue);
							} else if (valid) {
								setValue(value, convertedValue);
								pastedCells.add(new Point(insertionColumnIndex, insertionRowIndex));
							}
						}
						// BEGIN SUPRESS CATCH EXCEPTION
						catch (final Exception ex) {// END SUPRESS CATCH EXCEPTION
							// silently ignore this
						} finally {
							if (value != null) {
								value.dispose();
							}
						}

					}
					relativeColumn++;
				}
				relativeRow++;
			}
		} finally {
			postPasteContents();
		}

		showErrors(invalidValues);

		return pastedCells;
	}
	// END COMPLEX CODE

	/**
	 * This method gets called by {@link #pasteContents(Point, Grid, String)} before it will start to loop over the to
	 * be pasted values and begins the pasting.
	 * Clients may use this to notify the user about the paste, setting global variables, showing progress, etc.
	 */
	protected void prePasteContents() {
		/* default implementation does nothing */
	}

	/**
	 * This method get called by {@link #pasteContents(Point, Grid, String)} directely after the paste is done but
	 * before {@link #showErrors(List)} is called. Please note that the call comes from a finally block meaning that the
	 * paste process may have been stoped by an unhandled exception beforehand.
	 * Clients may use this clean up things done in {@link #prePasteContents()} for example.
	 */
	protected void postPasteContents() {
		/* default implementation does nothing */
	}

	/**
	 * This method is called by {@link #pasteContents(Point, Grid, String)} with the determined value for a cell.
	 * Clients may override this method in order to trim the string or change its formatting.
	 *
	 * @param cellValueSplit the cell value determined from the clipboard for the cell.
	 * @return the string to use
	 */
	protected String modifyCellValue(String cellValueSplit) {
		return cellValueSplit;
	}

	/**
	 * Called by {@link #pasteContents(Point, Grid, String)} to determine whether the values of an EObject should be
	 * changed at all.
	 *
	 * @param eObject the EObject in question
	 * @return <code>true</code> if paste for this object should be skipped, <code>false</code> otherwise
	 */
	protected boolean isEObjectReadOnly(EObject eObject) {
		return false;
	}

	/**
	 * Called by {@link #pasteContents(Point, Grid, String)} to determine whether a setting should be changed at all.
	 *
	 * @param eObject the EObject in question
	 * @param feature the Feature in question
	 * @param convertedValue the converted cell value
	 * @return <code>true</code> if paste for this setting should be skipped, <code>false</code> otherwise
	 */
	protected boolean isSettingReadOnly(EObject eObject, EStructuralFeature feature, Object convertedValue) {
		return false;
	}

	/**
	 * Shows the errors to the user.
	 *
	 * @param msgs the collected messages, may be empty if no errors
	 */
	protected void showErrors(List<String> msgs) {
		if (!msgs.isEmpty()) {
			showDialog(
				display.getActiveShell(),
				localizationService.getString(FrameworkUtil.getBundle(getClass()), "InvalidPaste.Title"), //$NON-NLS-1$
				localizationService.getString(FrameworkUtil.getBundle(getClass()), "InvalidPaste.Message"), //$NON-NLS-1$
				msgs);
		}
	}

	/**
	 * Checks whether a given value may be pasted.
	 *
	 * @param feature the feature of the {@code eObject}
	 * @param cellValue the cell value to be pasted
	 * @param eObject the parent {@link EObject}
	 * @param setting {@link Setting}
	 * @return {@code true}, if the value may be pasted, {@code false} otherwise
	 */
	protected boolean canBePasted(EStructuralFeature feature, String cellValue,
		EObject eObject, Setting setting) {

		if (!EEnum.class.isInstance(feature.getEType())) {
			return true;
		}

		final EEnum eEnum = (EEnum) feature.getEType();
		for (final EEnumLiteral literal : eEnum.getELiterals()) {
			final String isInputtable = EcoreUtil.getAnnotation(literal, VViewPackage.eNS_URI,
				IS_INPUTTABLE);

			if (literal.getLiteral().equals(cellValue) && isInputtable != null) {
				return Boolean.getBoolean(isInputtable);
			}
		}

		return true;
	}

	/**
	 * Sets the given converted value on the observable value.
	 *
	 * @param value the observable value
	 * @param convertedValue the converted value
	 */
	protected void setValue(IObservableValue value, final Object convertedValue) {
		value.setValue(convertedValue);
	}

	/**
	 * Creates the message for the given {@link Diagnostic} which will be displayed to the user.
	 *
	 * @param diag the diagnostic with the original error message
	 * @param feature the validated feature
	 * @param value the validated value
	 * @return the display string
	 */
	protected String extractDiagnosticMessage(Diagnostic diag, EStructuralFeature feature, String value) {
		return diag.getChildren().get(0).getMessage();
	}

	private static void showDialog(Shell shell, String title, String msg, List<String> warnings) {
		final StringBuilder builder = new StringBuilder();
		builder.append(msg);
		for (final String warning : warnings) {
			builder.append("- " + warning) //$NON-NLS-1$
				.append("\n"); //$NON-NLS-1$
		}

		MessageDialog.openWarning(shell, title, builder.toString());
	}

	/**
	 *
	 * @return the {@link EStructuralFeatureValueConverterService}
	 */
	protected EStructuralFeatureValueConverterService getConverterService() {
		return converterService;
	}

	/**
	 * @return the {@link VControl}
	 */
	protected VControl getControl() {
		return vControl;
	}
}
