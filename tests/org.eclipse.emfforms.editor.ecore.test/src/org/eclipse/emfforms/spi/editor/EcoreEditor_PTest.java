/*******************************************************************************
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
 * Alexandra Buzila - initial API and implementation
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emfforms.internal.editor.ecore.EcoreEditor;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

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
@RunWith(MockitoJUnitRunner.class)
public class EcoreEditor_PTest {
	private static final String EMFFORMS_EDITOR_TEST_PROJECT_NAME = "org.eclipse.emfforms.ecore.editor.test"; //$NON-NLS-1$
	private static final String LOCAL_TEST_DATA = "/data/"; //$NON-NLS-1$
	private static final String GENMODEL_FILENAME = "test.genmodel"; //$NON-NLS-1$
	private static final String ECORE_FILENAME = "test.ecore"; //$NON-NLS-1$
	private IFile ecoreFile;
	private EcoreEditor editor;
	private IFile genModelFile;

	@Mock(answer = Answers.RETURNS_MOCKS)
	private IEditorSite site;
	private ISelectionProvider selectionProvider;

	@Mock
	private EMFFormsViewServiceFactory<EMFFormsRevealService> revealServiceFactory;
	@Mock
	private EMFFormsRevealService revealService;
	private ServiceRegistration<?> revealServiceFactoryReg;

	@Before
	public void setup() throws IOException, CoreException, URISyntaxException {
		final BundleContext bundleContext = FrameworkUtil.getBundle(EcoreEditor_PTest.class).getBundleContext();
		when(revealServiceFactory.getPolicy()).thenReturn(EMFFormsViewServicePolicy.IMMEDIATE);
		when(revealServiceFactory.getScope()).thenReturn(EMFFormsViewServiceScope.GLOBAL);
		when(revealServiceFactory.getPriority()).thenReturn(Double.MAX_VALUE);
		when(revealServiceFactory.getType()).thenReturn(EMFFormsRevealService.class);
		when(revealServiceFactory.createService(any(EMFFormsViewContext.class))).thenReturn(revealService);
		revealServiceFactoryReg = bundleContext.registerService(EMFFormsViewServiceFactory.class, revealServiceFactory,
			new Hashtable<>());

		doReturn(EclipseContextFactory.createServiceContext(bundleContext)).when(site)
			.getService(IEclipseContext.class);
		doAnswer(invocation -> selectionProvider).when(site).getSelectionProvider();
		doAnswer(invocation -> selectionProvider = (ISelectionProvider) invocation.getArguments()[0])
			.when(site).setSelectionProvider(any(ISelectionProvider.class));
		createEcoreAndGenModelFiles();

		final IURIEditorInput input = mock(IURIEditorInput.class);
		when(input.getName()).thenReturn("Editor Title"); //$NON-NLS-1$

		when(input.getURI())
			.thenReturn(new java.net.URI(URI.createFileURI(ecoreFile.getLocation().toString()).toString()));
		when(input.getAdapter(IFile.class)).thenReturn(ecoreFile);

		editor = new EcoreEditor();
		editor.init(site, input);
		editor.createPartControl(new Shell());

		assumeThat("No selection provider set", selectionProvider, notNullValue()); //$NON-NLS-1$
	}

	@After
	public void tearDown() {
		revealServiceFactoryReg.unregister();
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

	@Test
	public void testNavigateGenericEditorStyleMarker() {
		final Resource res = editor.getResourceSet().getResources().get(0);
		final URI resourceURI = res.getURI();
		final String fragment = "//Element/diagnostic"; //$NON-NLS-1$
		final URI featureURI = EcoreUtil.getURI(EcorePackage.Literals.ENAMED_ELEMENT__NAME);

		final IMarker marker = mockMarker("RESOURCE_URI", resourceURI.toString(), //$NON-NLS-1$
			"FRAGMENT_URI" /* (sic) */, fragment, //$NON-NLS-1$
			"FEATURE_URI", featureURI.toString()); //$NON-NLS-1$

		testNavigate(marker);
	}

	private void testNavigate(IMarker marker) {
		editor.gotoMarker(marker);

		SWTTestUtil.waitForUIThread();

		final ISelection selection = selectionProvider.getSelection();
		assertThat(selection, instanceOf(IStructuredSelection.class));
		final Object selected = ((IStructuredSelection) selection).getFirstElement();
		assertThat(selected, instanceOf(EReference.class));
		final EReference reference = (EReference) selected;
		assertThat(reference.getName(), is("diagnostic")); //$NON-NLS-1$

		verify(revealService).reveal(reference, EcorePackage.Literals.ENAMED_ELEMENT__NAME);
	}

	@Test
	public void testNavigateEMFStyleMarker() {
		final Resource res = editor.getResourceSet().getResources().get(0);
		final URI objectURI = res.getURI().appendFragment("//Element/diagnostic"); //$NON-NLS-1$
		final URI featureURI = EcoreUtil.getURI(EcorePackage.Literals.ENAMED_ELEMENT__NAME);

		final IMarker marker = mockMarker(
			EValidator.URI_ATTRIBUTE, objectURI.toString(),
			EValidator.RELATED_URIS_ATTRIBUTE, URI.encodeFragment(featureURI.toString(), false));

		testNavigate(marker);
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

	IMarker mockMarker(String key, String value, String... more) {
		assert more.length % 2 == 0 : "odd number of strings for attributes"; //$NON-NLS-1$
		final Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(key, value);
		for (int i = 0; i < more.length; i = i + 2) {
			attributes.put(more[i], more[i + 1]);
		}

		final IMarker result = mock(IMarker.class);

		try {
			when(result.getAttribute(any(String.class)))
				.then(invocation -> attributes.get(invocation.getArguments()[0]));
			when(result.getAttribute(any(String.class), any(String.class))).then(
				invocation -> attributes.getOrDefault(invocation.getArguments()[0],
					(String) invocation.getArguments()[1]));
		} catch (final CoreException e) {
			e.printStackTrace();
			fail("Mock threw during stubbing"); //$NON-NLS-1$
		}

		return result;
	}

}
