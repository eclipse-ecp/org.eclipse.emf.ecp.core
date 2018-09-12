/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MultiReferenceTooltipModifier_PTest {

	private MultiReferenceTooltipModifier multiReferenceTooltipModifier;

	@Before
	public void setup() {
		multiReferenceTooltipModifier = new MultiReferenceTooltipModifier();
	}

	@Test
	public void testModifyString() {
		final String text = "Foo";
		assertEquals(text, multiReferenceTooltipModifier.modifyString(text, null));

		final Setting attributeSetting = Mockito.mock(Setting.class);
		Mockito.when(attributeSetting.getEStructuralFeature())
			.thenReturn(EcorePackage.eINSTANCE.getENamedElement_Name());
		assertEquals(text, multiReferenceTooltipModifier.modifyString(text, attributeSetting));

		final Setting singleRefSetting = Mockito.mock(Setting.class);
		Mockito.when(singleRefSetting.getEStructuralFeature())
			.thenReturn(EcorePackage.eINSTANCE.getEClassifier_EPackage());
		assertEquals(text, multiReferenceTooltipModifier.modifyString(text, singleRefSetting));

		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			adapterFactory,
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$

		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		final EClass eClass1 = EcoreFactory.eINSTANCE.createEClass();
		eClass1.setName("1");
		ePackage.getEClassifiers().add(eClass1);
		final EClass eClass2 = EcoreFactory.eINSTANCE.createEClass();
		eClass2.setName("2");
		ePackage.getEClassifiers().add(eClass2);

		resource.getContents().add(ePackage);

		final Setting setting = ((InternalEObject) ePackage)
			.eSetting(EcorePackage.eINSTANCE.getEPackage_EClassifiers());
		assertEquals("1, 2", multiReferenceTooltipModifier.modifyString(text, setting));
	}

	@Test
	public void testGetPriority() {
		assertEquals(20, multiReferenceTooltipModifier.getPriority(), 0);
	}

}
