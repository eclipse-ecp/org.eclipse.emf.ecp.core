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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditFactory;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link KeyListenerUtil}.
 *
 * @author Lucas Koehler
 *
 */
public class KeyListenerUtil_PTest {

	private static final String TEST = "TEST";
	private AdapterFactoryEditingDomain editingDomain;
	private RegisteredUser eObject;
	private EMFFormsDatabindingEMF databinding;

	@Before
	public void setUp() {
		// Add the EObject to a resource with editing domain
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$

		// We cannot mock the eObject because a mocked object cannot be added to a resource
		eObject = AuditFactory.eINSTANCE.createRegisteredUser();
		resource.getContents().add(eObject);

		databinding = mock(EMFFormsDatabindingEMF.class);
	}

	/** Tests that the unset is wrapped in an undoable command. */
	@Test
	public void unsetFeature() throws DatabindingFailedException {
		final Setting setting = mock(Setting.class);
		when(databinding.getSetting(any(VDomainModelReference.class), same(eObject))).thenReturn(setting);
		when(setting.getEObject()).thenReturn(eObject);
		when(setting.getEStructuralFeature()).thenReturn(AuditPackage.Literals.REGISTERED_USER__LOGIN);
		eObject.setLogin(TEST);
		editingDomain.getCommandStack().flush();

		KeyListenerUtil.unsetFeature(databinding, mock(VDomainModelReference.class), eObject);

		assertNull(eObject.getLogin());
		assertTrue(editingDomain.getCommandStack().canUndo());
		assertTrue(editingDomain.getCommandStack().getMostRecentCommand() instanceof SetCommand);

		editingDomain.getCommandStack().undo();

		assertEquals(TEST, eObject.getLogin());
	}

}
