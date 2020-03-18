/*******************************************************************************
 * Copyright (c) 2020 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailSWTBuilder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for the {@link GenericEditor} class.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("nls")
public class GenericEditor_PTest {

	private GenericEditor editor;

	private final Composite parentComposite = new Shell();

	@Mock(answer = Answers.RETURNS_MOCKS)
	private IEditorSite editorSite;

	@Mock(answer = Answers.RETURNS_MOCKS)
	private IEditorInput editorInput;

	private TreeMasterDetailSWTBuilder builder;

	private ViewModelContext detailContext;

	/**
	 * Initializes me.
	 */
	public GenericEditor_PTest() {
		super();
	}

	@Test
	public void customizeTree() {
		editor.createPartControl(parentComposite);

		assertThat("Tree builder not customized", builder, notNullValue());
	}

	@Test
	public void handleDetailActivated() {
		editor.createPartControl(parentComposite);

		while (Display.getCurrent().readAndDispatch()) {
			// pass
		}

		assertThat("Detail context not activated", detailContext, notNullValue());
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() throws PartInitException {
		editor = new GenericEditor() {
			@Override
			protected boolean enableValidation() {
				return false;
			}

			@Override
			protected ResourceSet loadResource(IEditorInput editorInput) throws PartInitException {
				final ResourceSet result = new ResourceSetImpl();
				final Resource resource = new XMIResourceImpl(URI.createURI("platform:/resource/test/resource.xmi"));
				resource.getContents().add(EcoreFactory.eINSTANCE.createEObject());
				result.getResources().add(resource);
				return result;
			}

			@Override
			protected TreeMasterDetailSWTBuilder customizeTree(TreeMasterDetailSWTBuilder builder) {
				GenericEditor_PTest.this.builder = super.customizeTree(builder);
				return builder;
			}

			@Override
			protected void handleDetailActivated(ViewModelContext detailContext) {
				GenericEditor_PTest.this.detailContext = detailContext;
			}
		};

		editor.init(editorSite, editorInput);
	}

	@After
	public void dispose() {
		parentComposite.dispose();
	}

}
