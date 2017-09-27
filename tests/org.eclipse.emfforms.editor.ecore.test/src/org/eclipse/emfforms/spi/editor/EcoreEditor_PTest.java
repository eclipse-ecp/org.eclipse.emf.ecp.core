/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emfforms.internal.editor.ecore.EcoreEditor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.contexts.IContextService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Tests for the {@link EcoreEditor}.
 * This plugin is a fragment of the <b>org.eclipse.emfforms.editor</b> bundle, containing the {@link GenericEditor}
 * class, although we are testing content from the <b>org.eclipse.emfforms.editor.ecore</b> bundle. The reason for
 * this is that we need to call protected methods from the {@link GenericEditor}, which would not be possible otherwise.
 *
 * @since 1.14
 *
 */
@SuppressWarnings("restriction")
public class EcoreEditor_PTest {
	private static final String EMFFORMS_EDITOR_TEST_PROJECT_NAME = "org.eclipse.emfforms.ecore.editor.test"; //$NON-NLS-1$
	private static final String LOCAL_TEST_DATA = "/data/"; //$NON-NLS-1$
	private static final String GENMODEL_FILENAME = "test.genmodel"; //$NON-NLS-1$
	private static final String ECORE_FILENAME = "test.ecore"; //$NON-NLS-1$
	private IFile ecoreFile;
	private EcoreEditor editor;
	private IFile genModelFile;

	@Before
	public void setup() throws IOException, CoreException, URISyntaxException {
		editor = new EcoreEditor() {
		};
		final IEditorSite site = mock(IEditorSite.class);
		when(site.getService(IContextService.class)).thenAnswer(new Answer<IContextService>() {

			@Override
			public IContextService answer(InvocationOnMock invocation) throws Throwable {
				final IContextService contextService = mock(IContextService.class);
				when(contextService.activateContext(anyString())).thenReturn(null);
				return contextService;
			}
		});
		when(site.getPage()).thenAnswer(new Answer<IWorkbenchPage>() {

			@Override
			public IWorkbenchPage answer(InvocationOnMock invocation) throws Throwable {
				final IWorkbenchPage page = mock(IWorkbenchPage.class);
				return page;
			}
		});
		createEcoreAndGenModelFiles();

		final IURIEditorInput input = mock(IURIEditorInput.class);
		when(input.getName()).thenReturn("Editor Title"); //$NON-NLS-1$

		when(input.getURI())
			.thenReturn(new java.net.URI(URI.createFileURI(ecoreFile.getLocation().toString()).toString()));
		when(input.getAdapter(IFile.class)).thenReturn(ecoreFile);

		editor.init(site, input);
		editor.createPartControl(new Shell());
	}

	/**
	 * Test that the contents of the Ecore Editor are saved.
	 *
	 * @throws IOException if anything went wrong during the execution
	 * @throws CoreException if anything went wrong during the execution
	 */
	@Test
	public void testSave() throws IOException, CoreException {
		final Resource ecoreResource = editor.getResourceSet().getResources().get(0);
		EPackage ePackage = (EPackage) ecoreResource.getContents().get(0);
		final EClass class1 = EcoreFactory.eINSTANCE.createEClass();
		final String className = "class1"; //$NON-NLS-1$
		class1.setName(className);
		editor.getCommandStack().execute(new AddCommand(editor.getEditingDomain(), ePackage.getEClassifiers(), class1));
		assertTrue(editor.isDirty());
		editor.doSave(null);
		assertFalse(editor.isDirty());

		// re-load resource and test if new contents are present
		ecoreResource.unload();
		ecoreResource.load(null);
		ePackage = (EPackage) ecoreResource.getContents().get(0);
		EObject addedClass = null;
		for (final EClassifier classifier : ePackage.getEClassifiers()) {
			if (classifier.getName().equals(className)) {
				addedClass = classifier;
				break;
			}
		}
		assertNotNull(addedClass);

	}

	private void createEcoreAndGenModelFiles() throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(EMFFORMS_EDITOR_TEST_PROJECT_NAME);
		if (!project.exists()) {
			project.create(null);
			project.open(null);
		}
		ecoreFile = project.getFile(ECORE_FILENAME);
		if (!ecoreFile.exists()) {
			ecoreFile.create(this.getClass().getResourceAsStream(LOCAL_TEST_DATA + ECORE_FILENAME), IResource.NONE,
				null);
		}
		genModelFile = project.getFile(GENMODEL_FILENAME);
		if (!genModelFile.exists()) {
			genModelFile.create(this.getClass().getResourceAsStream(LOCAL_TEST_DATA + GENMODEL_FILENAME),
				IResource.NONE, null);
		}
	}
}
