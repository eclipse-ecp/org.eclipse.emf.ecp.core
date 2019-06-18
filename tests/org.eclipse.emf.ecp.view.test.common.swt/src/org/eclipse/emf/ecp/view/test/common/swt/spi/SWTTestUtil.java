/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.test.common.swt.spi;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * Util class for SWT Tests.
 *
 * @author jfaltermeier
 *
 */
public final class SWTTestUtil {

	private SWTTestUtil() {
		// util
	}

	/**
	 * <p>
	 * Iterates over the hierarchy of the given {@link Control} and looks for a {@link Composite} with a
	 * {@link StackLayout}. The index specifies which composite with a stack layout should be returned.
	 * </p>
	 * <p>
	 * This method uses a depth-first-search.
	 * </p>
	 *
	 * @param control the parent control
	 * @param index the index of the layout
	 * @return the layout
	 * @throws NoSuchElementException if no layout with the index can be found
	 */
	public static StackLayout findStackLayout(Control control, int index) throws NoSuchElementException {
		final Control result = find(control, index, new Counter(), new ControlTest() {
			@Override
			public boolean testCondition(Control control) {
				if (!Composite.class.isInstance(control)) {
					return false;
				}
				final Composite composite = Composite.class.cast(control);
				final Layout layout = composite.getLayout();
				if (layout == null) {
					return false;
				}
				if (StackLayout.class != layout.getClass()) {
					return false;
				}
				return true;
			}
		});
		if (result == null) {
			throw new NoSuchElementException("No composite with stack layout and index " + index + " found."); //$NON-NLS-1$//$NON-NLS-2$
		}
		final Composite composite = (Composite) result;
		return (StackLayout) composite.getLayout();
	}

	/**
	 * <p>
	 * Iterates over the hierarchy of the given {@link Control} and looks for a control of the given class. The index
	 * specifies which control should be returned.
	 * </p>
	 * <p>
	 * This method uses a depth-first-search.
	 * </p>
	 *
	 * @param control the parent control
	 * @param index the index of the control to find
	 * @param <T> the type of the control to find
	 * @param clazz the class of the control to find
	 * @return the control
	 * @throws NoSuchElementException if no control with the index can be found
	 */
	public static <T extends Control> T findControl(Control control, int index, final Class<T> clazz)
		throws NoSuchElementException {
		final Control result = find(control, index, new Counter(), new ControlTest() {
			@Override
			public boolean testCondition(Control control) {
				if (control.getClass() != clazz) {
					return false;
				}
				return true;
			}
		});
		if (result == null) {
			throw new NoSuchElementException("No control of type " + clazz.getName() + " with index " + index //$NON-NLS-1$ //$NON-NLS-2$
				+ " found."); //$NON-NLS-1$
		}
		return clazz.cast(result);
	}

	private static Control find(Control control, int index, Counter found, ControlTest test) {
		final boolean success = test.testCondition(control);
		if (success) {
			found.inc();
		}
		if (index + 1 == found.count) {
			return control;
		}
		if (!Composite.class.isInstance(control)) {
			return null;
		}
		final Composite composite = Composite.class.cast(control);
		for (final Control child : composite.getChildren()) {
			final Control childResult = find(child, index, found, test);
			if (childResult != null) {
				return childResult;
			}
		}
		return null;
	}

	/**
	 * Retrieves an element in the specified control by its unique Id, as defined in {@link SWTDataElementIdHelper}.
	 *
	 * @param control the parent control where the search control should be found.
	 * @param id the unique ID of the element to find
	 * @param clazz the class of control to find
	 * @param <T> the type of Control to find
	 * @return a optional referencing the control or an empty one if none was found.
	 */
	public static <T extends Control> T findControlById(Control control, final String id, final Class<T> clazz) {
		// only get the first index, assuming id is unique
		final Control result = find(control, 0, new Counter(), new ControlTest() {
			@Override
			public boolean testCondition(Control control) {
				if (control.getClass() != clazz) {
					return false;
				}

				final Object elementID = control.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY);
				if (elementID != null && id.equals(elementID)) {
					return true;
				}

				return false;
			}
		});
		return clazz.cast(result);
	}

	/**
	 * Waits for the ui thread to complete its work. Fails a testcase after 5 seconds.
	 */
	public static void waitForUIThread() {
		final long maxTime = System.currentTimeMillis() + 5000;
		while (Display.getDefault().readAndDispatch()) {
			if (System.currentTimeMillis() > maxTime) {
				fail("Timeout");
			}
		}
	}

	/**
	 * Simulates a click on the given {@link Button}.
	 *
	 * @param button the button to press
	 */
	public static void clickButton(Button button) {
		selectWidget(button);
	}

	/**
	 * Selects the given widget.
	 *
	 * @param widget the control to select.
	 */
	public static void selectWidget(Widget widget) {
		widget.notifyListeners(SWT.Selection, new Event());
	}

	/**
	 * Simulates a key down and key up event on the given {@link Control}.
	 *
	 * @param control the control
	 * @param keyCode the key code
	 */
	public static void pressAndReleaseKey(Control control, int keyCode) {
		pressAndReleaseKey(control, 0, keyCode);
	}

	/**
	 * Simulates a key down and key up event on the given {@link Control}.
	 *
	 * @param control the control
	 * @param stateMask the state of the keyboard modifier keys (e.g. M1/ctrl)
	 * @param keyCode the key code
	 */
	public static void pressAndReleaseKey(Control control, int stateMask, int keyCode) {
		final Event eventDown = new Event();
		eventDown.stateMask = stateMask;
		eventDown.keyCode = keyCode;
		control.notifyListeners(SWT.KeyDown, eventDown);
		final Event eventUp = new Event();
		eventUp.keyCode = keyCode;
		control.notifyListeners(SWT.KeyUp, eventDown);
	}

	/**
	 * Sets the given string on the {@link Text} and simulates a focus out event.
	 *
	 * @param text the text
	 * @param string the string to enter in the text
	 */
	public static void typeAndFocusOut(Text text, String string) {
		text.setText(string);
		text.notifyListeners(SWT.FocusOut, new Event());
	}

	/**
	 * Selects the index in the given tree and notifies the tree's listeners (e.g. a corresponding tree viewer) about
	 * the selection.
	 *
	 * @param tree The tree to select an element in
	 * @param index The index of the element to select
	 * @return the selected {@link TreeItem}
	 */
	public static TreeItem selectTreeItem(Tree tree, int index) {
		final TreeItem result = tree.getItem(index);
		selectTreeItem(result);
		return result;
	}

	/**
	 * Selects the given {@link TreeItem} in its parent {@link Tree} and notifies the tree's listeners.
	 *
	 * @param treeItem The {@link TreeItem} to select
	 */
	public static void selectTreeItem(TreeItem treeItem) {
		final Tree tree = treeItem.getParent();
		tree.setSelection(treeItem);
		final Event event = new Event();
		event.type = SWT.Selection;
		event.widget = tree;
		event.item = treeItem;
		tree.notifyListeners(SWT.Selection, event);
	}

	/**
	 * Selects the index in the given table and notifies the table's listeners (e.g. a corresponding table viewer) about
	 * the selection.
	 *
	 * @param table The table to select an element in
	 * @param index The index of the element to select
	 * @return the selected {@link TableItem}
	 */
	public static TableItem selectTableItem(Table table, int index) {
		table.setSelection(index);
		final Event event = new Event();
		event.type = SWT.Selection;
		event.widget = table;
		final TableItem result = table.getItem(index);
		event.item = result;
		table.notifyListeners(SWT.Selection, event);
		return result;
	}

	/**
	 * Interface for a tester used by the {@link SWTTestUtil#find(Control, int, Counter, ControlTest)} method.
	 *
	 * @author jfaltermeier
	 *
	 */
	private interface ControlTest {

		/**
		 * Tests a condition on the given control.
		 *
		 * @param control the control to test
		 * @return <code>true</code> if condition fulfilled, <code>false</code> otherwise
		 */
		boolean testCondition(Control control);
	}

	/**
	 * Simple counter class.
	 *
	 * @author jfaltermeier
	 *
	 */
	private static class Counter {
		private int count;

		/**
		 * Increases the counter.
		 */
		public void inc() {
			count = count + 1;
		}
	}
}
