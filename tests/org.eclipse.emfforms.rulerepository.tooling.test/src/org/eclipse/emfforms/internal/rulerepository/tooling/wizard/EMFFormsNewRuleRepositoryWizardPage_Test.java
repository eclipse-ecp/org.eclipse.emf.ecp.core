/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.rulerepository.tooling.wizard;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IResource;
import org.junit.Test;

/**
 * @author Jonas Helming
 *
 */
public class EMFFormsNewRuleRepositoryWizardPage_Test {

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.rulerepository.tooling.wizard.EMFFormsNewRuleRepositoryWizardPage#getContainerErrorMessage(org.eclipse.core.resources.IResource)}.
	 */
	@Test
	public void testGetContainerErrorMessageContainerNull() {
		final String errorMessage = EMFFormsNewRuleRepositoryWizardPage.getContainerErrorMessage(null);
		assertEquals(Messages.EMFFormsRuleRepositoryWizardPage_errorContainerNotExists, errorMessage);
	}

	@Test
	public void testGetContainerErrorMessageNoType() {
		final IResource mockResource = mock(IResource.class);
		final String errorMessage = EMFFormsNewRuleRepositoryWizardPage.getContainerErrorMessage(mockResource);
		assertEquals(Messages.EMFFormsRuleRepositoryWizardPage_errorContainerNotExists, errorMessage);
	}

	@Test
	public void testGetContainerErrorMessageWrongType() {
		final IResource mockResource = mock(IResource.class);
		when(mockResource.getType()).thenReturn(IResource.FILE);
		final String errorMessage = EMFFormsNewRuleRepositoryWizardPage.getContainerErrorMessage(mockResource);
		assertEquals(Messages.EMFFormsRuleRepositoryWizardPage_errorContainerNotExists, errorMessage);
	}

	@Test
	public void testGetContainerErrorMessageFolderNotWritable() {
		final IResource mockResource = mock(IResource.class);
		when(mockResource.getType()).thenReturn(IResource.FOLDER);
		when(mockResource.isAccessible()).thenReturn(false);
		final String errorMessage = EMFFormsNewRuleRepositoryWizardPage.getContainerErrorMessage(mockResource);
		assertEquals(Messages.EMFFormsRuleRepositoryWizardPage_errorProjectReadOnly, errorMessage);
	}

	@Test
	public void testGetContainerErrorMessageProjectWritable() {
		final IResource mockResource = mock(IResource.class);
		when(mockResource.getType()).thenReturn(IResource.PROJECT);
		when(mockResource.isAccessible()).thenReturn(true);
		final String errorMessage = EMFFormsNewRuleRepositoryWizardPage.getContainerErrorMessage(mockResource);
		assertEquals(null, errorMessage);
	}

}
