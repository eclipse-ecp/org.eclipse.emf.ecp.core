/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.validation.test.model.Color;
import org.eclipse.emf.ecp.view.validation.test.model.Gender;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;
import org.eclipse.emfforms.internal.common.prevalidation.PreSetValidationServiceImpl;
import org.eclipse.emfforms.spi.common.validation.IFeatureConstraint;
import org.junit.Test;

public class PreSetValidationService_Test {

	private final PreSetValidationServiceImpl service = new PreSetValidationServiceImpl();

	@Test
	public void violateMaxLength() {
		final Diagnostic result = service.validate(
			TestPackage.eINSTANCE.getPerson_FirstName(),
			"more than 10 chars",
			null);
		assertEquals(result.getSeverity(), Diagnostic.ERROR);
	}

	@Test
	public void maxLength() {
		final Diagnostic result = service.validate(
			TestPackage.eINSTANCE.getPerson_FirstName(),
			"valid",
			null);
		assertEquals(result.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void enums() {
		final Diagnostic result = service.validate(
			TestPackage.eINSTANCE.getPerson_Gender(),
			Gender.MALE,
			null);
		assertEquals(result.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void pattern() {
		final Diagnostic result = service.validate(
			TestPackage.eINSTANCE.getPerson_LastName(), "VALID", null);
		assertEquals(result.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void invalidPattern() {
		final Diagnostic result = service.validate(
			TestPackage.eINSTANCE.getPerson_LastName(), "invalid", null);
		assertEquals(result.getSeverity(), Diagnostic.ERROR);
	}

	@Test
	public void validEnum() {
		final Diagnostic result = service.validate(TestPackage.eINSTANCE.getPerson_Gender(), "Male", null);
		assertEquals(result.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void invalidEnum() {
		final Diagnostic result = service.validate(TestPackage.eINSTANCE.getPerson_Gender(), "Mal", null);
		assertEquals(result.getSeverity(), Diagnostic.ERROR);
	}

	@Test
	public void custom() {
		final PreSetValidationServiceImpl s = new PreSetValidationServiceImpl();
		s.addConstraintValidator(TestPackage.eINSTANCE.getCustomDataType(), new IFeatureConstraint() {
			@Override
			public Diagnostic validate(EStructuralFeature eStructuralFeature, Object value,
				Map<Object, Object> context) {
				final EClassifier eType = eStructuralFeature.getEType();

				if (!TestPackage.eINSTANCE.getCustomDataType().isInstance(eType)
					|| value.equals("FOO")) {
					return new BasicDiagnostic();
				}

				return BasicDiagnostic.toDiagnostic(
					new Status(IStatus.ERROR, "", IStatus.ERROR, "Value is not FOO", null));
			}
		});

		final Diagnostic result = s.validate(
			TestPackage.eINSTANCE.getPerson_Custom(), "FOO", null);
		assertEquals(result.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void invalidCustom() {
		final PreSetValidationServiceImpl s = new PreSetValidationServiceImpl();
		s.addConstraintValidator(TestPackage.eINSTANCE.getCustomDataType(), new IFeatureConstraint() {
			@Override
			public Diagnostic validate(EStructuralFeature eStructuralFeature, Object value,
				Map<Object, Object> context) {

				if (value.equals("FOO")) {
					return new BasicDiagnostic();
				}

				return BasicDiagnostic.toDiagnostic(
					new Status(IStatus.ERROR, "test", IStatus.ERROR, "Value is not FOO", null));
			}
		});

		final Diagnostic result = s.validate(
			TestPackage.eINSTANCE.getPerson_Custom(), "BAR", null);
		assertEquals(result.getSeverity(), Diagnostic.ERROR);
	}

	@Test // regression test for bug #527891
	public void invalidCustomWithTwoValidators() {
		final PreSetValidationServiceImpl s = new PreSetValidationServiceImpl();

		final IFeatureConstraint constraint1 = new IFeatureConstraint() {
			@Override
			public Diagnostic validate(EStructuralFeature eStructuralFeature, Object value,
				Map<Object, Object> context) {

				if (value.equals("FOO")) {
					return new BasicDiagnostic();
				}

				return BasicDiagnostic.toDiagnostic(
					new Status(IStatus.ERROR, "test", IStatus.ERROR, "Value is not FOO", null));
			}
		};

		final IFeatureConstraint constraint2 = new IFeatureConstraint() {
			@Override
			public Diagnostic validate(EStructuralFeature eStructuralFeature, Object value,
				Map<Object, Object> context) {

				if (value.equals("FOO")) {
					return new BasicDiagnostic();
				}

				return BasicDiagnostic.toDiagnostic(
					new Status(IStatus.ERROR, "test", IStatus.ERROR, "Value is still not FOO", null));
			}
		};

		s.addConstraintValidator(TestPackage.eINSTANCE.getCustomDataType(), constraint1);
		s.addConstraintValidator(TestPackage.eINSTANCE.getCustomDataType(), constraint2);

		final Diagnostic result = s.validate(
			TestPackage.eINSTANCE.getPerson_Custom(), "BAR", null);

		assertEquals(result.getSeverity(), Diagnostic.ERROR);
		assertEquals(2, result.getChildren().size());
	}

	@Test
	public void loosePhoneNumberPattern() {
		final Diagnostic result = service.validateLoose(TestPackage.eINSTANCE.getLibrary_PhoneNumber(), "+");
		assertEquals(result.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void strictPhoneNumberPattern() {
		final Diagnostic invalid = service.validate(TestPackage.eINSTANCE.getLibrary_PhoneNumber(), "+", null);
		final Diagnostic valid = service.validate(TestPackage.eINSTANCE.getLibrary_PhoneNumber(), "+123", null);
		assertEquals(invalid.getSeverity(), Diagnostic.ERROR);
		assertEquals(valid.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void looseMinLength() {
		final Diagnostic result = service.validateLoose(TestPackage.eINSTANCE.getWriter_Initials(), "");
		assertEquals(result.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void strictMinLength() {
		// min length of three
		final Diagnostic invalid = service.validate(TestPackage.eINSTANCE.getWriter_Initials(), "", null);
		final Diagnostic valid = service.validate(TestPackage.eINSTANCE.getWriter_Initials(), "foo", null);
		assertEquals(invalid.getSeverity(), Diagnostic.ERROR);
		assertEquals(valid.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void looseMinLengthFallsBackToStrict() {
		// title has no looseMinLength set
		final Diagnostic invalid = service.validateLoose(TestPackage.eINSTANCE.getWriter_Title(), "");
		assertEquals(invalid.getSeverity(), Diagnostic.ERROR);
	}

	@Test
	public void strictMinInclusive() {
		// min length of three
		final Diagnostic invalid = service.validate(TestPackage.eINSTANCE.getPerson_Age(), Integer.valueOf(-1), null);
		final Diagnostic minValid = service.validate(TestPackage.eINSTANCE.getPerson_Age(), Integer.valueOf(0), null);
		final Diagnostic maxValid = service.validate(TestPackage.eINSTANCE.getPerson_Age(), Integer.valueOf(100), null);
		assertEquals(invalid.getSeverity(), Diagnostic.ERROR);
		assertEquals(minValid.getSeverity(), Diagnostic.OK);
		assertEquals(maxValid.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void looseMinInclusive() {
		// min length of three
		final Diagnostic invalid = service.validateLoose(TestPackage.eINSTANCE.getPerson_Age(), Integer.valueOf(-1));
		final Diagnostic minValid = service.validateLoose(TestPackage.eINSTANCE.getPerson_Age(), Integer.valueOf(0));
		final Diagnostic maxValid = service.validateLoose(TestPackage.eINSTANCE.getPerson_Age(), Integer.valueOf(100));
		assertEquals(invalid.getSeverity(), Diagnostic.ERROR);
		assertEquals(minValid.getSeverity(), Diagnostic.OK);
		assertEquals(maxValid.getSeverity(), Diagnostic.OK);
	}

	@Test
	public void multiEnum() {
		final Diagnostic valid = service.validate(TestPackage.eINSTANCE.getComputer_Colors(),
			Arrays.asList(Color.GREEN, Color.BLUE), null);
		assertEquals(valid.getSeverity(), Diagnostic.OK);
	}

	/**
	 * Bug 529514.
	 */
	@Test
	public void multiEnumAdditionalConstrains() {
		/* setup */
		final AtomicBoolean contraintCalled = new AtomicBoolean(false);
		service.addConstraintValidator(TestPackage.eINSTANCE.getColor(), new IFeatureConstraint() {

			@Override
			public Diagnostic validate(
				EStructuralFeature eStructuralFeature,
				Object value,
				Map<Object, Object> context) {
				contraintCalled.set(true);
				return new BasicDiagnostic();
			}
		});

		/* act */
		final Diagnostic valid = service.validate(TestPackage.eINSTANCE.getComputer_Colors(),
			Arrays.asList(Color.GREEN, Color.BLUE), null);

		/* assert */
		assertEquals(valid.getSeverity(), Diagnostic.OK);
		assertTrue(contraintCalled.get());

	}
}
