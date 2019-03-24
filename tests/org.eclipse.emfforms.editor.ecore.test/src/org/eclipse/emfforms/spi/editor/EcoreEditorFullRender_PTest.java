/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emfforms.internal.editor.ecore.EcoreEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for the {@link EcoreEditor}.
 * This plugin is a fragment of the <b>org.eclipse.emfforms.editor</b> bundle, containing the {@link GenericEditor}
 * class, although we are testing content from the <b>org.eclipse.emfforms.editor.ecore</b> bundle. The reason for
 * this is that we need to call protected methods from the {@link GenericEditor}, which would not be possible otherwise.
 * <p>
 * In contrast to {@link EcoreEditor_PTest}, these tests fully render the editor without mocking parts of it or the IDE
 * environment.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
@RunWith(SWTBotJunit4ClassRunner.class)
public class EcoreEditorFullRender_PTest {

	@After
	public void closeAllEditors() {
		assertTrue(getActivePage().closeAllEditors(false));
		SWTTestUtil.waitForUIThread();
	}

	@Test
	public void shortcuts_tree_delete() {
		final EcoreEditor editor = open("shortcut_delete.ecore", EcoreEditor.class); //$NON-NLS-1$
		SWTTestUtil.waitForUIThread();
		final Composite parent = editor.getRootView();
		final Tree tree = SWTTestUtil.findControl(parent, 0, Tree.class);
		final TreeItem packageItem = tree.getItem(0).getItem(0);
		final TreeItem toDeleteItem = packageItem.getItem(0);
		final SWTBot bot = new SWTBot(parent);

		SWTTestUtil.selectTreeItem(toDeleteItem);
		SWTTestUtil.waitForUIThread();
		bot.tree().pressShortcut(Keystrokes.DELETE);
		SWTTestUtil.waitForUIThread();
		tree.update();
		SWTTestUtil.waitForUIThread();

		assertEquals("EPackage tree node children after delete", 0, packageItem.getItemCount()); //$NON-NLS-1$
		final Resource ecoreResource = editor.getResourceSet().getResources().get(0);
		final EPackage ePackage = (EPackage) ecoreResource.getContents().get(0);
		assertEquals("EPackage children after delete", 0, ePackage.getEClassifiers().size()); //$NON-NLS-1$
	}

	/** Expectation: When the focus is on the detail view, no tree node must be deleted if the DEL key is pressed. */
	@Test
	public void shortcuts_detail_noDelete() {
		final EcoreEditor editor = open("shortcut_delete.ecore", EcoreEditor.class); //$NON-NLS-1$
		SWTTestUtil.waitForUIThread();
		final Composite parent = editor.getRootView();
		final Tree tree = SWTTestUtil.findControl(parent, 0, Tree.class);
		final TreeItem packageItem = tree.getItem(0).getItem(0);
		final TreeItem toNotDeleteItem = packageItem.getItem(0);
		final SWTBot bot = new SWTBot(parent);

		tree.setFocus();
		SWTTestUtil.selectTreeItem(toNotDeleteItem);
		SWTTestUtil.waitForUIThread();
		// Make sure the detail is re-rendered
		SWTTestUtil.pressAndReleaseKey(tree, SWT.LF);
		parent.update();
		SWTTestUtil.waitForUIThread();

		bot.checkBox().pressShortcut(Keystrokes.DELETE);
		tree.update();
		SWTTestUtil.waitForUIThread();

		assertEquals("EPackage tree node children after pressing DEL in the detail", 1, packageItem.getItemCount()); //$NON-NLS-1$
		final Resource ecoreResource = editor.getResourceSet().getResources().get(0);
		final EPackage ePackage = (EPackage) ecoreResource.getContents().get(0);
		assertEquals("EPackage children after pressing DEL in the detail", 1, ePackage.getEClassifiers().size()); //$NON-NLS-1$
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
			result = getActivePage().openEditor(input, "EcoreEditor"); //$NON-NLS-1$
		} catch (final PartInitException e) {
			// It's okay if we are expecting it
			result = getEditor(input);
		}

		return expectedType.cast(result);
	}

	static URI testResourceURI(String resourcePath) {
		return URI.createURI(
			String.format("platform:/fragment/org.eclipse.emfforms.editor.ecore.test/data/%s", resourcePath), //$NON-NLS-1$
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
			fail("Could not get editor input from editor reference: " + e.getMessage()); //$NON-NLS-1$
			return null; // Unreachable
		}
	}
}
