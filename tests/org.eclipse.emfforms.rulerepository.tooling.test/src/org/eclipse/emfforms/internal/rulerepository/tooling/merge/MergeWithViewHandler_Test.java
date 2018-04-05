/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.rulerepository.tooling.merge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.internal.rulerepository.tooling.merge.MergeWithViewHandler.MergeProvider;
import org.eclipse.emfforms.internal.rulerepository.tooling.merge.MergeWithViewHandler.MergedViewPathProvider;
import org.eclipse.emfforms.internal.rulerepository.tooling.merge.MergeWithViewHandler.OperationProvider;
import org.eclipse.emfforms.internal.rulerepository.tooling.merge.MergeWithViewHandler.ViewAndRepository;
import org.eclipse.emfforms.internal.rulerepository.tooling.merge.MergeWithViewHandler.ViewAndRepositoryProvider;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryFactory;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for {@link MergeWithViewHandler}.
 *
 * @author Lucas Koehler
 *
 */
public class MergeWithViewHandler_Test {

	private MergeWithViewHandler handler;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		handler = new MergeWithViewHandler();
	}

	/** Test that all given providers are used with the correct parameters. */
	@Test
	public void testProviderUsage() {
		final ResourceSet rs = mock(ResourceSet.class);
		final VRuleRepository repository = mock(VRuleRepository.class);
		final VView view = mock(VView.class);
		final VView mergedView = mock(VView.class);
		final ViewAndRepository viewAndRepository = new ViewAndRepository();
		final String mergedViewPath = "mergedviewpath";
		viewAndRepository.setRuleRepository(repository);
		viewAndRepository.setView(view);
		final ViewAndRepositoryProvider viewAndRepositoryProvider = mock(ViewAndRepositoryProvider.class);
		when(viewAndRepositoryProvider.getViewAndRepository(any(ResourceSet.class))).thenReturn(viewAndRepository);

		final OperationProvider operationProvider = mock(OperationProvider.class);
		final MergedViewPathProvider viewPathProvider = mock(MergedViewPathProvider.class);
		when(viewPathProvider.getPath(any(Shell.class), any(VView.class))).thenReturn(mergedViewPath);
		final MergeProvider mergeProvider = mock(MergeProvider.class);
		when(mergeProvider.merge(repository, view)).thenReturn(mergedView);

		final Shell shell = new Shell();
		handler.mergeRuleRepoWithView(
			shell,
			rs,
			viewAndRepositoryProvider,
			viewPathProvider,
			operationProvider,
			mergeProvider);

		verify(viewAndRepositoryProvider).getViewAndRepository(rs);
		verify(viewPathProvider).getPath(shell, view);
		verify(mergeProvider).merge(repository, view);
		verify(operationProvider).getOperation(eq(shell), eq(mergedViewPath), eq(mergedView));
	}

	/**
	 * Test that the VView and VRuleRepository are correctly read from a resource set and that the algorithm can deal
	 * with empty resources without causing an exception.
	 */
	@Test
	public void testExtractViewAndRepository() {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final Resource resource1 = resourceSet.createResource(URI.createURI("virtual-1"));
		final VRuleRepository repository = VRulerepositoryFactory.eINSTANCE.createRuleRepository();
		final VView view = VViewFactory.eINSTANCE.createView();

		final Resource resource2 = resourceSet.createResource(URI.createURI("virtual-2"));
		// Leave the fourth resource empty to check that this does not cause an exception
		resourceSet.createResource(URI.createURI("empty-resource"));

		resource1.getContents().add(repository);
		resource2.getContents().add(view);
		// Add unfitting EObject to resource
		final Resource resource3 = resourceSet.createResource(URI.createURI("virtual-3"));
		// Cannot mock any EObject here because the adding to the EList fails
		resource3.getContents().add(VViewFactory.eINSTANCE.createControl());

		final ViewAndRepository viewAndRepository = handler.extractViewAndRepository(resourceSet);
		assertEquals(view, viewAndRepository.getView());
		assertEquals(repository, viewAndRepository.getRuleRepository());

	}
}
