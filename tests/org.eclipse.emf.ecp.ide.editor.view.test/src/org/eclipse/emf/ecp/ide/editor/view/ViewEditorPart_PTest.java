/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.editor.view;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ErrorEditorPart;
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

	@Test
	public void openView_NoRootEClass() {
		final ErrorEditorPart error = openError("View-Without-RootEClass.view");
		assertErrorMessageContains(error, "The Root EClass of the VView is not set.");
	}

	@Test
	public void openView_PackageNotRegistered() {
		final ErrorEditorPart error = openError("View-PackageNotExisting.view");
		assertErrorMessageContains(error, "Root EClass \"User\" of the VView could not be resolved");
		assertErrorMessageContains(error,
			"register the Ecore providing the EPackage with namespace URI \"http://this/is/not/registered\"");
	}

	@Test
	public void openView_PackageRegisteredButDoesNotContainRootEClass() {
		final ErrorEditorPart error = openError("View-RootEClassNotExisting.view");
		assertErrorMessageContains(error, "Root EClass \"NonExistingClass\" of the VView could not be resolved");
		assertErrorMessageContains(error,
			"The registered EPackage \"task\" with namespace URI \"http://eclipse/org/emf/ecp/makeithappen/model/task\" does not contain the Root EClass");
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

	void assertErrorMessageContains(ErrorEditorPart editor, String substring) {
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
}
