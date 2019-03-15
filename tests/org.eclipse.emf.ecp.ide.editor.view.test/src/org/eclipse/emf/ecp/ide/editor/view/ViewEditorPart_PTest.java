/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 * Christian W. Damus - bugs 543376, 545460
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.editor.view;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecp.ide.editor.view.messages.Messages;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.keyboard.Keyboard;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardFactory;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.ErrorEditorPart;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.junit.After;
import org.junit.Test;

/**
 * Tests for ViewEditorPart.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class ViewEditorPart_PTest {

	private static final String USER_VIEW_MODEL = "View-WellFormed.view";

	private Clipboard clipboard;

	@Test
	public void openView_NoRootEClass() {
		final String expectedErrorMessage = Messages.ViewEditorPart_invalidVView_noRootEClass;

		final ErrorEditorPart error = openError("View-Without-RootEClass.view");
		assertErrorMessage(error, expectedErrorMessage);
	}

	@Test
	public void openView_PackageNotRegistered() {
		final String expectedErrorMessage = MessageFormat.format(
			Messages.ViewEditorPart_invalidVView_rootEClassPackageNotResolved,
			new Object[] { "User", "http://this/is/not/registered" });

		final ErrorEditorPart error = openError("View-PackageNotExisting.view");
		assertErrorMessage(error, expectedErrorMessage);
	}

	@Test
	public void openView_PackageRegisteredButDoesNotContainRootEClass() {
		final String expectedErrorMessage = MessageFormat.format(Messages.ViewEditorPart_ViewCannotBeDisplayed,
			MessageFormat.format(Messages.ViewEditorPart_invalidVView_rootEClassNotInPackage,
				new Object[] { "NonExistingClass", "task", "http://eclipse/org/emf/ecp/makeithappen/model/task" }));

		final ErrorEditorPart error = openError("View-RootEClassNotExisting.view");
		assertErrorMessage(error, expectedErrorMessage);
	}

	@Test
	public void getEditingDomain() {
		final IEditorPart editor = open(USER_VIEW_MODEL, IEditorPart.class);
		assertThat("Editor does not provide an editing domain", editor, instanceOf(IEditingDomainProvider.class));
		final IEditingDomainProvider provider = (IEditingDomainProvider) editor;
		assertThat("No editing domain provided", provider.getEditingDomain(), notNullValue());
	}

	@Test
	public void selectionTrackingAndUndo() {
		final IEditorPart editor = open(USER_VIEW_MODEL, IEditorPart.class);
		assumeThat("Editor does not provide an editing domain", editor, instanceOf(IEditingDomainProvider.class));
		final IEditingDomainProvider provider = (IEditingDomainProvider) editor;
		assumeThat("No editing domain provided", provider.getEditingDomain(), notNullValue());

		final ISelectionProvider selectionProvider = editor.getSite().getSelectionProvider();
		assertThat("No selection provider for the editor site", selectionProvider, notNullValue());

		final EditorActionBarContributor contributor = (EditorActionBarContributor) editor.getEditorSite()
			.getActionBarContributor();
		final IAction delete = contributor.getActionBars().getGlobalActionHandler(ActionFactory.DELETE.getId());
		final IAction undo = contributor.getActionBars().getGlobalActionHandler(ActionFactory.UNDO.getId());

		// Find a control to select
		VControl control = null;
		for (final Iterator<?> iter = provider.getEditingDomain().getResourceSet().getAllContents(); control == null
			&& iter.hasNext();) {
			final Object next = iter.next();
			if (next instanceof VControl) {
				control = (VControl) next;
			}
		}

		assumeThat("Could not find a control in the view model", control, notNullValue());

		selectionProvider.setSelection(new StructuredSelection(control));

		final ISelection selection = selectionProvider.getSelection();
		assertThat(selection, instanceOf(IStructuredSelection.class));
		assertThat("Control not selected", ((IStructuredSelection) selection).getFirstElement(), is(control));

		// Action enablement updates are deferred
		SWTTestUtil.waitForUIThread();

		// Delete the selected control
		assertThat("Delete command not enabled", delete.isEnabled(), is(true));
		delete.run();

		assertThat("Control not deleted", control.eContainer(), nullValue());
		assertThat("Editor not dirty", editor.isDirty(), is(true));

		// Action enablement updates are deferred
		SWTTestUtil.waitForUIThread();

		// Undo the delete
		assertThat("Undo command not enabled", undo.isEnabled(), is(true));
		undo.run();

		assertThat("Deletion not undone", control.eContainer(), notNullValue());
		assertThat("Editor still dirty", editor.isDirty(), is(false));
	}

	@Test
	public void copyPaste_Tree() throws WidgetNotFoundException, ParseException, InterruptedException {
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		final Keyboard keyboard = KeyboardFactory.getAWTKeyboard();

		final ViewEditorPart editor = open(USER_VIEW_MODEL, ViewEditorPart.class);
		final Composite parent = getEditorParentComposite(editor);
		final Tree tree = SWTTestUtil.findControl(parent, 0, Tree.class);
		final TreeItem viewItem = tree.getItem(0);
		final TreeItem controlItem0 = viewItem.getItem(0);
		SWTTestUtil.selectTreeItem(controlItem0); // select the VControl

		// Manually force focus to increase test stability because sometimes selecting a tree item programmatically
		// doesn't set focus or at least not fast enough
		focus(tree);
		keystroke(keyboard, tree, SWT.MOD1, 'c');

		SWTTestUtil.selectTreeItem(viewItem);
		SWTTestUtil.waitForUIThread();

		focus(tree);
		keystroke(keyboard, tree, SWT.MOD1, 'v');
		tree.update();
		SWTTestUtil.waitForUIThread();

		// At the beginning of the test the view had one child control. After copy and pasting, it should have 2
		assertEquals("Number of VControls in the VView object", 2, editor.getView().getChildren().size());
		assertEquals("Number of VControls in the tree", 2, viewItem.getItemCount());
	}

	@Test
	public void copyPaste_Detail_TextControl() {
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		final Keyboard keyboard = KeyboardFactory.getAWTKeyboard();

		final ViewEditorPart editor = open(USER_VIEW_MODEL, ViewEditorPart.class);
		clipboard = new Clipboard(Display.getCurrent());
		clipboard.clearContents();
		final Composite parent = getEditorParentComposite(editor);
		final Tree tree = SWTTestUtil.findControl(parent, 0, Tree.class);
		final TreeItem viewItem = tree.getItem(0);
		final TreeItem controlItem0 = viewItem.getItem(0);
		focus(tree);
		SWTTestUtil.selectTreeItem(controlItem0); // select the VControl
		SWTTestUtil.waitForUIThread();

		// Get the text field of a VControl's name
		final Text text = SWTTestUtil.findControl(parent, 0, Text.class);
		final String string = "TEST";
		text.setText(string);
		text.update();
		SWTTestUtil.waitForUIThread();

		final SWTBot bot = new SWTBot(parent);
		final SWTBotText botText = bot.text(string);
		botText.selectAll();
		SWTTestUtil.waitForUIThread();
		focus(text);
		// Press multiple times to increase test stability
		for (int i = 0; i < 10; i++) {
			keystroke(keyboard, text, SWT.MOD1, 'c');
		}
		text.setSelection(0);

		if (Platform.getWS().equals(Platform.WS_COCOA)) {
			// Can only test that we did not copy in the tree because the native keyboard
			// copy/paste support in the Cocoa text widget cannot be automated via SWT
			final EditingDomain domain = editor.getEditingDomain();
			assertThat("Copied in the tree", domain.getClipboard(), nullValue());
			return;
		}

		// Check that copy to clip board worked
		final Object contents = clipboard.getContents(TextTransfer.getInstance());
		assertNotNull(contents);
		assertEquals(string, contents);

		focus(text);
		// Press multiple times to increase test stability
		for (int i = 0; i < 10; i++) {
			keystroke(keyboard, text, SWT.MOD1, 'v');
		}
		text.update();
		SWTTestUtil.waitForUIThread();

		assertTrue(text.getText().contains(string + string));
	}

	@Test
	public void shortcuts_delete_resetSelectionToRoot() {
		final ViewEditorPart editor = open(USER_VIEW_MODEL, ViewEditorPart.class);
		final Composite parent = getEditorParentComposite(editor);
		final SWTBot bot = new SWTBot(parent);
		final SWTBotTree tree = bot.tree();
		SWTTestUtil.selectTreeItem(tree.widget.getItem(0).getItem(0));
		SWTTestUtil.waitForUIThread();

		if (Platform.getWS().equals(Platform.WS_COCOA)) {
			SWTTestUtil.pressAndReleaseKey(tree.widget, SWT.DEL);
		} else {
			tree.pressShortcut(Keystrokes.DELETE);
		}
		SWTTestUtil.waitForUIThread();

		final TreeItem[] selection = tree.widget.getSelection();
		assertTrue("Root node was not selected automatically!",
			selection.length == 1 && selection[0].getData() instanceof VView);
	}

	@Test
	public void contextMenu_delete_resetSelectionToRoot() {
		final ViewEditorPart editor = open(USER_VIEW_MODEL, ViewEditorPart.class);
		final Composite parent = getEditorParentComposite(editor);
		final SWTBot bot = new SWTBot(parent);
		final SWTBotTree tree = bot.tree();
		SWTTestUtil.selectTreeItem(tree.widget.getItem(0).getItem(0));
		SWTTestUtil.waitForUIThread();

		tree.contextMenu("Delete").click();
		SWTTestUtil.waitForUIThread();

		final TreeItem[] selection = tree.widget.getSelection();
		assertTrue("Root node was not selected automatically!",
			selection.length == 1 && selection[0].getData() instanceof VView);
	}

	@After
	public void disposeClipboard() {
		if (clipboard != null) {
			clipboard.dispose();
		}
	}

	@After
	public void closeAllEditors() {
		getActivePage().closeAllEditors(false);
	}

	// Test Infrastructure to open editors

	<P extends IEditorPart> P open(String resourcePath, Class<P> expectedType) {
		final URI resourceURI = testResourceURI(resourcePath);
		final URIEditorInput input = new URIEditorInput(resourceURI);

		return open(input, expectedType);
	}

	<P extends IEditorPart> P open(IEditorInput input, Class<P> expectedType) {
		IEditorPart result = null;

		try {
			result = getActivePage().openEditor(input, "org.eclipse.emf.ecp.ide.editor.view.editor");
		} catch (final PartInitException e) {
			// It's okay if we are expecting it
			result = getEditor(input);
			// assertThat("Unexpected open failure", result, instanceOf(expectedType));
		}

		// assertThat("Unexpected editor type", result, instanceOf(expectedType));
		return expectedType.cast(result);
	}

	static URI testResourceURI(String resourcePath) {
		return URI.createURI(
			String.format("platform:/fragment/org.eclipse.emf.ecp.ide.editor.view.test/resources/%s", resourcePath),
			true);
	}

	static IWorkbenchPage getActivePage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}

	static IEditorPart getEditor(IEditorInput input) {
		final IEditorReference[] editorReferences = getActivePage().getEditorReferences();
		for (final IEditorReference editorReference : editorReferences) {
			if (input.equals(safeGetEditorInput(editorReference))) {
				final IEditorPart editor = editorReference.getEditor(false);
				if (editor != null) {
					return editor;
				}
			}
		}
		return null;
	}

	static IEditorInput safeGetEditorInput(IEditorReference reference) {
		try {
			return reference.getEditorInput();
		} catch (final PartInitException e) {
			fail("Could not get editor input from editor reference: " + e.getMessage());
			return null; // Unreachable
		}
	}

	ErrorEditorPart openError(String resourcePath) {
		return open(resourcePath, ErrorEditorPart.class);
	}

	void assertErrorMessage(ErrorEditorPart editor, String substring) {
		Field statusField = null;
		final Field[] declaredFields = editor.getClass().getDeclaredFields();
		for (final Field field : declaredFields) {
			if (IStatus.class.isAssignableFrom(field.getType())) {
				statusField = field;
				break;
			}
		}
		assertNotNull("Cannot find status field in error editor", statusField);
		statusField.setAccessible(true);

		try {
			final IStatus status = (IStatus) statusField.get(editor);
			assertTrue(status.getMessage().contains(substring));
		} catch (final IllegalAccessException e) {
			fail("Cannot access status field of error editor: " + e.getMessage());
		} catch (final IllegalArgumentException e) {
			fail("Cannot access status field of error editor: " + e.getMessage());
		}
	}

	Composite getEditorParentComposite(ViewEditorPart editor) {
		Field parentField;
		try {
			parentField = ViewEditorPart.class.getDeclaredField("parent");
			parentField.setAccessible(true);
			final Object parentFieldValue = parentField.get(editor);
			assertTrue(parentFieldValue instanceof Composite);
			return (Composite) parentFieldValue;
		} catch (NoSuchFieldException | SecurityException | IllegalAccessException ex) {
			fail("Could not get parent composite of the view editor: " + ex.getMessage());
			return null; // Never happens
		}
	}

	void keystroke(Keyboard keyboard, Control control, int modifier, char key) {
		if (Platform.getWS().equals(Platform.WS_COCOA)) {
			SWTTestUtil.pressAndReleaseKey(control, modifier, key);
		} else {
			keyboard.pressShortcut(modifier, key);
		}
		SWTTestUtil.waitForUIThread();
	}

	void focus(Control control) {
		control.setFocus();
		SWTTestUtil.waitForUIThread();
	}

}
