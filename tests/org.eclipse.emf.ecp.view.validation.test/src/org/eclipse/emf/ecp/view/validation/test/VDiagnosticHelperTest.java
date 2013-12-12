/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecp.view.internal.validation.VDiagnosticHelper;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.validation.test.model.Library;
import org.eclipse.emf.ecp.view.validation.test.model.TestFactory;
import org.eclipse.emf.ecp.view.validation.test.model.Writer;
import org.junit.Test;

/**
 * Test cases for the {@link VDiagnosticHelper}.
 * 
 * @author emueller
 * 
 */
public class VDiagnosticHelperTest {

	public Diagnostic getDiagnosticForEObject(EObject object) {
		final EValidator validator = EValidator.Registry.INSTANCE.getEValidator(object.eClass().getEPackage());
		final BasicDiagnostic diagnostics = Diagnostician.INSTANCE.createDefaultDiagnostic(object);

		@SuppressWarnings("serial")
		final Map<Object, Object> context =
			new LinkedHashMap<Object, Object>() {
				{
					put(EValidator.SubstitutionLabelProvider.class, Diagnostician.INSTANCE);
					put(EValidator.class, validator);
				}
			};

		validator.validate(object, diagnostics, context);

		return diagnostics;
	}

	@Test
	public void testFirstArgIsNull() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		library.setName("warning");
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		final Diagnostic diagnostic = getDiagnosticForEObject(library);
		vDiagnostic.getDiagnostics().add(diagnostic);
		assertFalse(VDiagnosticHelper.isEqual(null, vDiagnostic));
	}

	@Test
	public void testSecondArgIsNull() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		library.setName("warning");
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		final Diagnostic diagnostic = getDiagnosticForEObject(library);
		vDiagnostic.getDiagnostics().add(diagnostic);
		assertFalse(VDiagnosticHelper.isEqual(vDiagnostic, null));
	}

	@Test
	public void testBothArgsNull() {
		assertTrue(VDiagnosticHelper.isEqual(null, null));
	}

	@Test
	public void testDifferentSeverities() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		library.setName("warning");
		library.getWriters().add(writer);
		final Diagnostic libDiagnostic = getDiagnosticForEObject(library);
		final Diagnostic writerDiagnostic = getDiagnosticForEObject(writer);
		final VDiagnostic vDiagnostic1 = VViewFactory.eINSTANCE.createDiagnostic();
		final VDiagnostic vDiagnostic2 = VViewFactory.eINSTANCE.createDiagnostic();
		vDiagnostic1.getDiagnostics().add(libDiagnostic);
		vDiagnostic2.getDiagnostics().add(writerDiagnostic);

		assertEquals(Diagnostic.WARNING, libDiagnostic.getSeverity());
		assertEquals(Diagnostic.ERROR, writerDiagnostic.getSeverity());
		assertFalse(VDiagnosticHelper.isEqual(vDiagnostic1, vDiagnostic2));
	}

	@Test
	public void testDifferentMessages() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		final Diagnostic libDiagnostic = getDiagnosticForEObject(library);
		final Diagnostic writerDiagnostic = getDiagnosticForEObject(writer);
		final VDiagnostic vDiagnostic1 = VViewFactory.eINSTANCE.createDiagnostic();
		final VDiagnostic vDiagnostic2 = VViewFactory.eINSTANCE.createDiagnostic();
		vDiagnostic1.getDiagnostics().add(libDiagnostic);
		vDiagnostic2.getDiagnostics().add(libDiagnostic);
		vDiagnostic2.getDiagnostics().add(writerDiagnostic);

		assertFalse(VDiagnosticHelper.isEqual(vDiagnostic1, vDiagnostic2));
	}

	@Test
	public void testEqual() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		library.setName("warning");
		final VDiagnostic vDiagnostic1 = VViewFactory.eINSTANCE.createDiagnostic();
		final VDiagnostic vDiagnostic2 = VViewFactory.eINSTANCE.createDiagnostic();
		final Diagnostic diagnostic = getDiagnosticForEObject(library);
		vDiagnostic1.getDiagnostics().add(diagnostic);
		vDiagnostic2.getDiagnostics().add(diagnostic);
		assertTrue(VDiagnosticHelper.isEqual(vDiagnostic1, vDiagnostic2));
	}

	@Test
	public void testEqualDifferentDiagnosticsSameResult() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		library.setName("warning");
		final VDiagnostic vDiagnostic1 = VViewFactory.eINSTANCE.createDiagnostic();
		final VDiagnostic vDiagnostic2 = VViewFactory.eINSTANCE.createDiagnostic();
		final Diagnostic diagnostic = getDiagnosticForEObject(library);
		final Diagnostic diagnostic2 = getDiagnosticForEObject(library);
		vDiagnostic1.getDiagnostics().add(diagnostic);
		vDiagnostic2.getDiagnostics().add(diagnostic2);
		assertTrue(VDiagnosticHelper.isEqual(vDiagnostic1, vDiagnostic2));
	}

	@Test
	public void testEqualDifferentChildren() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		library.setName("warning");
		final Diagnostic diagnostic = getDiagnosticForEObject(library);
		library.setName("warningWithouFeature");
		final Diagnostic diagnostic2 = getDiagnosticForEObject(library);

		final VDiagnostic vDiagnostic1 = VViewFactory.eINSTANCE.createDiagnostic();
		final VDiagnostic vDiagnostic2 = VViewFactory.eINSTANCE.createDiagnostic();
		vDiagnostic1.getDiagnostics().add(diagnostic);
		vDiagnostic2.getDiagnostics().add(diagnostic2);

		assertFalse(VDiagnosticHelper.isEqual(vDiagnostic1, vDiagnostic2));
	}

	@Test
	public void testEqualDifferentData() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		library.setName("lib");
		library.getWriters().add(writer);

		final Diagnostic libDiagnostic = getDiagnosticForEObject(library);
		final Diagnostic writerDiagnostic = getDiagnosticForEObject(writer);
		writer.setFirstName("HB");
		final Diagnostic libDiagnostic2 = getDiagnosticForEObject(library);
		final Diagnostic writerDiagnostic2 = getDiagnosticForEObject(writer);

		final VDiagnostic vDiagnostic1 = VViewFactory.eINSTANCE.createDiagnostic();
		final VDiagnostic vDiagnostic2 = VViewFactory.eINSTANCE.createDiagnostic();
		vDiagnostic1.getDiagnostics().add(libDiagnostic);
		vDiagnostic1.getDiagnostics().add(writerDiagnostic);
		vDiagnostic2.getDiagnostics().add(libDiagnostic2);
		vDiagnostic2.getDiagnostics().add(writerDiagnostic2);

		assertFalse(VDiagnosticHelper.isEqual(vDiagnostic1, vDiagnostic2));
	}

	@Test
	public void testEqualMultipleDiagnostics() {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		final Diagnostic libDiagnostic = getDiagnosticForEObject(library);
		final Diagnostic writerDiagnostic = getDiagnosticForEObject(writer);
		final VDiagnostic vDiagnostic1 = VViewFactory.eINSTANCE.createDiagnostic();
		final VDiagnostic vDiagnostic2 = VViewFactory.eINSTANCE.createDiagnostic();

		vDiagnostic1.getDiagnostics().add(libDiagnostic);
		vDiagnostic1.getDiagnostics().add(writerDiagnostic);
		vDiagnostic2.getDiagnostics().add(libDiagnostic);
		vDiagnostic2.getDiagnostics().add(writerDiagnostic);

		assertTrue(VDiagnosticHelper.isEqual(vDiagnostic1, vDiagnostic2));
	}

}
