/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.validation.ValidationService;
import org.eclipse.emf.ecp.view.internal.validation.ViewValidationListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.ecp.view.validation.test.model.Book;
import org.eclipse.emf.ecp.view.validation.test.model.Library;
import org.eclipse.emf.ecp.view.validation.test.model.TestFactory;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;
import org.eclipse.emf.ecp.view.validation.test.model.Writer;
import org.junit.Test;

/**
 * @author jfaltermeier
 * 
 */
public class ValidationServiceTest {

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Private configurable tests
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void testInitSingleControl(Writer writer, int severity) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(writer.eClass());

		final VControl control = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		control.setDomainModelReference(domainModelReference);

		view.getChildren().add(control);

		instantiateValidationService(view, writer);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals("Validation of severity " + severity + " was not propagated to control", severity,
			diagnostic.getHighestSeverity(), 0);
	}

	/**
	 * @param library
	 * @param severity expected severity
	 */
	private void testInitParentPropagationOneChild(Library library, int severity) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(library.eClass());

		final VVerticalLayout parentColumn = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(parentColumn);

		final VVerticalLayout column = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(column);

		final VControl controlWriter = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		domainModelReference.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Writers());
		controlWriter.setDomainModelReference(domainModelReference);

		column.getChildren().add(controlWriter);

		instantiateValidationService(view, library);

		final VDiagnostic diagnosticLibary = view.getDiagnostic();
		assertEquals("Validation result was not propagated to parent.", severity,
			diagnosticLibary.getHighestSeverity(),
			0);
	}

	/**
	 * @param severityWriter expected severity for writer
	 * @param severityBooks expected severity for books
	 */
	private void testInitParentPropagationTwoChildren(Library library, int severityWriter, int severityBooks) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(library.eClass());

		final VVerticalLayout parentColumn = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(parentColumn);

		// Writers //////////////////////////////////////
		final VVerticalLayout columnWriter = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(columnWriter);

		final VControl controlWriter = VViewFactory.eINSTANCE.createControl();
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		domainModelReference.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Writers());
		controlWriter.setDomainModelReference(domainModelReference);
		columnWriter.getChildren().add(controlWriter);

		// Books //////////////////////////////////////////
		final VVerticalLayout columnBooks = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(columnBooks);

		final VControl controlBooks = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference2.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Title());
		domainModelReference2.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Books());
		controlWriter.setDomainModelReference(domainModelReference2);

		columnBooks.getChildren().add(controlBooks);

		// Validation ///////////////////////////////////////
		instantiateValidationService(view, library);

		final VDiagnostic diagnosticWriter = columnWriter.getDiagnostic();
		assertEquals("Validation result of writers was not propagated to parent.", severityWriter,
			diagnosticWriter.getHighestSeverity(), 0);

		final VDiagnostic diagnosticBooks = columnBooks.getDiagnostic();
		assertEquals("Validation result of books was not propagated to parent.", severityBooks,
			diagnosticBooks.getHighestSeverity(), 0);

		final int severity = severityWriter > severityBooks ? severityWriter : severityBooks;
		final VDiagnostic diagnosticLibary = view.getDiagnostic();
		assertEquals("Validation result was not propagated to parent.", severity,
			diagnosticLibary.getHighestSeverity(), 0);
	}

	private void testChangeSingleControl(Writer writer, int changeToSeverity) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(writer.eClass());

		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		control.setDomainModelReference(domainModelReference);
		view.getChildren().add(control);

		instantiateValidationService(view, writer);

		changeWriter(writer, changeToSeverity);

		final VDiagnostic diagnostic = control.getDiagnostic();
		assertEquals("Validation upon change is wrong", changeToSeverity,
			diagnostic.getHighestSeverity(), 0);
	}

	/**
	 * It is expected that the library only contains one writer!
	 * 
	 * @param library
	 * @param severity expected severity
	 */
	private void testChangeParentPropagationOneChild(Library library, int changeToSeverity) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(library.eClass());

		final VVerticalLayout parentColumn = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(parentColumn);

		final VVerticalLayout column = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(column);

		final VControl controlWriter = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		domainModelReference.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Writers());
		controlWriter.setDomainModelReference(domainModelReference);

		column.getChildren().add(controlWriter);

		instantiateValidationService(view, library);

		changeWriter(library.getWriters().get(0), changeToSeverity);

		assertEquals(changeToSeverity, column.getDiagnostic().getHighestSeverity(), 0);
		assertEquals(changeToSeverity, parentColumn.getDiagnostic().getHighestSeverity(), 0);

		final VDiagnostic diagnosticLibary = view.getDiagnostic();
		assertEquals("Validation result was not propagated to parent.", changeToSeverity,
			diagnosticLibary.getHighestSeverity(), 0);
	}

	private void testAddAndRemoveChildHighestSeverity(Library library, int severityOfNewWriter) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(library.eClass());

		final VVerticalLayout parentColumn = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(parentColumn);

		final VVerticalLayout column = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(column);

		final VControl controlWriter = VViewFactory.eINSTANCE.createControl();
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		domainModelReference.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Writers());
		controlWriter.setDomainModelReference(domainModelReference);
		column.getChildren().add(controlWriter);

		instantiateValidationService(view, library);

		final int severityBefore = view.getDiagnostic().getHighestSeverity();

		// final Writer writer = changeWriter(TestFactory.eINSTANCE.createWriter(), severityOfNewWriter);
		// library.getWriters().add(writer);

		changeWriter(library.getWriters().get(0), severityOfNewWriter);

		final int severityAfterAdd = view.getDiagnostic().getHighestSeverity();

		if (severityBefore >= severityOfNewWriter) {
			assertEquals(severityBefore, severityAfterAdd, 0);
		} else {
			assertEquals(severityOfNewWriter, severityAfterAdd, 0);
		}

		// library.getWriters().remove(writer);
		changeWriter(library.getWriters().get(0), severityBefore);

		final int severityAfterRemove = view.getDiagnostic().getHighestSeverity();

		assertEquals(severityBefore, severityAfterRemove);
	}

	/**
	 * It is expected that library only has one writer.
	 * 
	 * @param newSeverityWriter expected severity for writer
	 * @param severityBooks expected severity for books
	 */
	private void testChangeParentPropagationTwoChildren(Library library, int newSeverityWriter, int severityBooks) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(library.eClass());

		final VVerticalLayout parentColumn = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(parentColumn);

		// Writers //////////////////////////////////////
		final VVerticalLayout columnWriter = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(columnWriter);

		final VControl controlWriter = VViewFactory.eINSTANCE.createControl();
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		domainModelReference.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Writers());
		controlWriter.setDomainModelReference(domainModelReference);
		columnWriter.getChildren().add(controlWriter);

		// Books //////////////////////////////////////////
		final VVerticalLayout columnBooks = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(columnBooks);

		final VControl controlBooks = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference2.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Title());
		domainModelReference2.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Books());
		controlBooks.setDomainModelReference(domainModelReference2);

		columnBooks.getChildren().add(controlBooks);

		// Validation ///////////////////////////////////////
		instantiateValidationService(view, library);

		final Writer writer = library.getWriters().get(0);
		changeWriter(writer, newSeverityWriter);

		assertEquals(newSeverityWriter, columnWriter.getDiagnostic().getHighestSeverity());

		final int expectedSeverityAll = newSeverityWriter > severityBooks ? newSeverityWriter
			: severityBooks;
		assertEquals(expectedSeverityAll, view.getDiagnostic().getHighestSeverity());
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Init tests
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testInitSingleControlOK() {
		testInitSingleControl(createOKWriter(), Diagnostic.OK);
	}

	@Test
	public void testInitSingleControlInfo() {
		testInitSingleControl(createInfoWriter(), Diagnostic.INFO);
	}

	@Test
	public void testInitSingleControlWarning() {
		testInitSingleControl(createWarningWriter(), Diagnostic.WARNING);
	}

	@Test
	public void testInitSingleControlError() {
		testInitSingleControl(createErrorWriter(), Diagnostic.ERROR);
	}

	@Test
	public void testInitSingleControlCancel() {
		testInitSingleControl(createCancelWriter(), Diagnostic.CANCEL);
	}

	@Test
	public void testInitParentPropagationOneControlOK() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.OK);
	}

	@Test
	public void testInitParentPropagationOneControlInfo() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testInitParentPropagationOneControlWarning() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testInitParentPropagationOneControlError() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitParentPropagationOneControlCancel() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsInfoCase1() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 0, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsWarningCase1() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 1, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsWarningCase2() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 1, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsWarningCase3() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 1, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsErrorCase1() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 1, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsErrorCase2() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 0, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsErrorCase3() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 1, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsErrorCase4() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsErrorCase5() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 1, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsErrorCase6() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsErrorCase7() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase1() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 1, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase2() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 1, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase3() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 0, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase4() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 1, 0, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase5() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 1, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase6() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 1, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase7() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase8() {
		testInitParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase9() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 1, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase10() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 1, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase11() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase12() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase13() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase14() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitHighestSeverityMultipleElementsCancelCase15() {
		testInitParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseOkOk() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0)), Diagnostic.OK,
			Diagnostic.OK);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseOkInfo() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(0, 1, 0, 0, 0)), Diagnostic.OK,
			Diagnostic.INFO);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseOkWarning() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(0, 0, 1, 0, 0)), Diagnostic.OK,
			Diagnostic.WARNING);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseOkError() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(0, 0, 0, 1, 0)), Diagnostic.OK,
			Diagnostic.ERROR);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseOkCancel() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(0, 0, 0, 0, 1)), Diagnostic.OK,
			Diagnostic.CANCEL);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseInfoInfo() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 1, 0, 0, 0), createBooks(0, 1, 0, 0, 0)), Diagnostic.INFO,
			Diagnostic.INFO);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseInfoWarning() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 1, 0, 0, 0), createBooks(0, 0, 1, 0, 0)), Diagnostic.INFO,
			Diagnostic.WARNING);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseInfoError() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 1, 0, 0, 0), createBooks(0, 0, 0, 1, 0)), Diagnostic.INFO,
			Diagnostic.ERROR);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseInfoCancel() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 1, 0, 0, 0), createBooks(0, 0, 0, 0, 1)), Diagnostic.INFO,
			Diagnostic.CANCEL);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseWarningWarning() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 0, 1, 0, 0), createBooks(0, 0, 1, 0, 0)), Diagnostic.WARNING,
			Diagnostic.WARNING);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseWarningError() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 0, 1, 0, 0), createBooks(0, 0, 0, 1, 0)), Diagnostic.WARNING,
			Diagnostic.ERROR);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseWarningCancel() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 0, 1, 0, 0), createBooks(0, 0, 0, 0, 1)), Diagnostic.WARNING,
			Diagnostic.CANCEL);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseErrorError() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 0, 0, 1, 0), createBooks(0, 0, 0, 1, 0)), Diagnostic.ERROR,
			Diagnostic.ERROR);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseErrorCancel() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 0, 0, 1, 0), createBooks(0, 0, 0, 0, 1)), Diagnostic.ERROR,
			Diagnostic.CANCEL);
	}

	@Test
	public void testInitParentPropagationTwoControlsCancelCaseCancelCancel() {
		testInitParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(0, 0, 0, 0, 1), createBooks(0, 0, 0, 0, 1)), Diagnostic.CANCEL,
			Diagnostic.CANCEL);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Validation upon change tests
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testChangeSingleControlOKToOk() {
		testChangeSingleControl(createOKWriter(), Diagnostic.OK);
	}

	@Test
	public void testChangeSingleControlOKToInfo() {
		testChangeSingleControl(createOKWriter(), Diagnostic.INFO);
	}

	@Test
	public void testChangeSingleControlOKToWarning() {
		testChangeSingleControl(createOKWriter(), Diagnostic.WARNING);
	}

	@Test
	public void testChangeSingleControlOKToError() {
		testChangeSingleControl(createOKWriter(), Diagnostic.ERROR);
	}

	@Test
	public void testChangeSingleControlOKToCancel() {
		testChangeSingleControl(createOKWriter(), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeSingleControlInfoToOk() {
		testChangeSingleControl(createInfoWriter(), Diagnostic.OK);
	}

	@Test
	public void testChangeSingleControlInfoToInfo() {
		testChangeSingleControl(createInfoWriter(), Diagnostic.INFO);
	}

	@Test
	public void testChangeSingleControlInfoToWarning() {
		testChangeSingleControl(createInfoWriter(), Diagnostic.WARNING);
	}

	@Test
	public void testChangeSingleControlInfoToError() {
		testChangeSingleControl(createInfoWriter(), Diagnostic.ERROR);
	}

	@Test
	public void testChangeSingleControlInfoToCancel() {
		testChangeSingleControl(createInfoWriter(), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeSingleControlWarningToOk() {
		testChangeSingleControl(createWarningWriter(), Diagnostic.OK);
	}

	@Test
	public void testChangeSingleControlWarningToInfo() {
		testChangeSingleControl(createWarningWriter(), Diagnostic.INFO);
	}

	@Test
	public void testChangeSingleControlWarningToWarning() {
		testChangeSingleControl(createWarningWriter(), Diagnostic.WARNING);
	}

	@Test
	public void testChangeSingleControlWarningToError() {
		testChangeSingleControl(createWarningWriter(), Diagnostic.ERROR);
	}

	@Test
	public void testChangeSingleControlWarningToCancel() {
		testChangeSingleControl(createWarningWriter(), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeSingleControlErrorToOk() {
		testChangeSingleControl(createErrorWriter(), Diagnostic.OK);
	}

	@Test
	public void testChangeSingleControlErrorToInfo() {
		testChangeSingleControl(createErrorWriter(), Diagnostic.INFO);
	}

	@Test
	public void testChangeSingleControlErrorToWarning() {
		testChangeSingleControl(createErrorWriter(), Diagnostic.WARNING);
	}

	@Test
	public void testChangeSingleControlErrorToError() {
		testChangeSingleControl(createErrorWriter(), Diagnostic.ERROR);
	}

	@Test
	public void testChangeSingleControlErrorToCancel() {
		testChangeSingleControl(createErrorWriter(), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeSingleControlCancelToOk() {
		testChangeSingleControl(createCancelWriter(), Diagnostic.OK);
	}

	@Test
	public void testChangeSingleControlCancelToInfo() {
		testChangeSingleControl(createCancelWriter(), Diagnostic.INFO);
	}

	@Test
	public void testChangeSingleControlCancelToWarning() {
		testChangeSingleControl(createCancelWriter(), Diagnostic.WARNING);
	}

	@Test
	public void testChangeSingleControlCancelToError() {
		testChangeSingleControl(createCancelWriter(), Diagnostic.ERROR);
	}

	@Test
	public void testChangeSingleControlCancelToCancel() {
		testChangeSingleControl(createCancelWriter(), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationOneControlOKToOk() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationOneControlOKToInfo() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationOneControlOKToWarning() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationOneControlOKToError() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationOneControlOKToCancel() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationOneControlInfoToOk() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 0, 0), Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationOneControlInfoToInfo() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationOneControlInfoToWarning() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationOneControlInfoToError() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 0, 0), Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationOneControlInfoToCancel() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 1, 0, 0, 0), Diagnostic.CANCEL);

	}

	@Test
	public void testChangeParentPropagationOneControlWarningToOk() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 0, 0), Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationOneControlWarningToInfo() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationOneControlWarningToWarning() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationOneControlWarningToError() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 0, 0), Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationOneControlWarningToCancel() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 1, 0, 0), Diagnostic.CANCEL);

	}

	@Test
	public void testChangeParentPropagationOneControlErrorToOk() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 1, 0), Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationOneControlErrorToInfo() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 1, 0), Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationOneControlErrorToWarning() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 1, 0), Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationOneControlErrorToError() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationOneControlErrorToCancel() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 1, 0), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationOneControlCancelToOk() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 0, 1), Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationOneControlCancelToInfo() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 0, 1), Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationOneControlCancelToWarning() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 0, 1), Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationOneControlCancelToError() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 0, 1), Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationOneControlCancelToCancel() {
		testChangeParentPropagationOneChild(createLibaryWithWriters(0, 0, 0, 0, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testAddAndRemoveSeverityNewOKinOK() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.OK);
	}

	@Test
	public void testAddAndRemoveSeverityNewOKinInfo() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 0, 0, 0), Diagnostic.OK);
	}

	@Test
	public void testAddAndRemoveSeverityNewOKinWarning() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 0, 0), Diagnostic.OK);
	}

	@Test
	public void testAddAndRemoveSeverityNewOKinError() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 0), Diagnostic.OK);
	}

	@Test
	public void testAddAndRemoveSeverityNewOKinCancel() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 1), Diagnostic.OK);
	}

	@Test
	public void testAddAndRemoveSeverityNewInfoinOK() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testAddAndRemoveSeverityNewInfoinInfo() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 0, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testAddAndRemoveSeverityNewInfoinWarning() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 0, 0), Diagnostic.INFO);
	}

	@Test
	public void testAddAndRemoveSeverityNewInfoinError() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 0), Diagnostic.INFO);
	}

	@Test
	public void testAddAndRemoveSeverityNewInfoinCancel() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 1), Diagnostic.INFO);
	}

	@Test
	public void testAddAndRemoveSeverityNewWarninginOK() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testAddAndRemoveSeverityNewWarninginInfo() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 0, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testAddAndRemoveSeverityNewWarninginWarning() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 0, 0), Diagnostic.WARNING);
	}

	@Test
	public void testAddAndRemoveSeverityNewWarninginError() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 0), Diagnostic.WARNING);
	}

	@Test
	public void testAddAndRemoveSeverityNewWarninginCancel() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 1), Diagnostic.WARNING);
	}

	@Test
	public void testAddAndRemoveSeverityNewErrorinOK() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.ERROR);
	}

	@Test
	public void testAddAndRemoveSeverityNewErrorinInfo() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 0, 0, 0), Diagnostic.ERROR);
	}

	@Test
	public void testAddAndRemoveSeverityNewErrorinWarning() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 0, 0), Diagnostic.ERROR);
	}

	@Test
	public void testAddAndRemoveSeverityNewErrorinError() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 0), Diagnostic.ERROR);
	}

	@Test
	public void testAddAndRemoveSeverityNewErrorinCancel() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 1), Diagnostic.ERROR);
	}

	@Test
	public void testAddAndRemoveSeverityNewCancelinOK() {

		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 0, 0, 0, 0), Diagnostic.CANCEL);
	}

	@Test
	public void testAddAndRemoveSeverityNewCancelinInfo() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 0, 0, 0), Diagnostic.CANCEL);
	}

	@Test
	public void testAddAndRemoveSeverityNewCancelinWarning() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 0, 0), Diagnostic.CANCEL);
	}

	@Test
	public void testAddAndRemoveSeverityNewCancelinError() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 0), Diagnostic.CANCEL);
	}

	@Test
	public void testAddAndRemoveSeverityNewCancelinCancel() {
		testAddAndRemoveChildHighestSeverity(createLibaryWithWriters(1, 1, 1, 1, 1), Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToOkversusOk() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0)), Diagnostic.OK,
			Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToInfoversusOk() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0)), Diagnostic.INFO,
			Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToWarningversusOk() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0)), Diagnostic.WARNING,
			Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToErrorversusOk() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0)), Diagnostic.ERROR,
			Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToCancelversusOk() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0)), Diagnostic.CANCEL,
			Diagnostic.OK);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToOkversusInfo() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 1, 0, 0, 0)), Diagnostic.OK,
			Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToInfoversusInfo() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 1, 0, 0, 0)), Diagnostic.INFO,
			Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToWarningversusInfo() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 1, 0, 0, 0)), Diagnostic.WARNING,
			Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToErrorversusInfo() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 1, 0, 0, 0)), Diagnostic.ERROR,
			Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToCancelversusInfo() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 1, 0, 0, 0)), Diagnostic.CANCEL,
			Diagnostic.INFO);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToOkversusWarning() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 0, 0)), Diagnostic.OK,
			Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToInfoversusWarning() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 0, 0)), Diagnostic.INFO,
			Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToWarningversusWarning() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 0, 0)), Diagnostic.WARNING,
			Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToErrorversusWarning() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 0, 0)), Diagnostic.ERROR,
			Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToCancelversusWarning() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 0, 0)), Diagnostic.CANCEL,
			Diagnostic.WARNING);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToOkversusError() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 1, 0)), Diagnostic.OK,
			Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToInfoversusError() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 1, 0)), Diagnostic.INFO,
			Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToWarningversusError() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 1, 0)), Diagnostic.WARNING,
			Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToErrorversusError() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 1, 0)), Diagnostic.ERROR,
			Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToCancelversusError() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 1, 1, 0)), Diagnostic.CANCEL,
			Diagnostic.ERROR);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToOkversusCancel() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 1)), Diagnostic.OK,
			Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToInfoversusCancel() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 1)), Diagnostic.INFO,
			Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToWarningversusCancel() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 1)), Diagnostic.WARNING,
			Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToErrorversusCancel() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 1)), Diagnostic.ERROR,
			Diagnostic.CANCEL);
	}

	@Test
	public void testChangeParentPropagationTwoControlsCaseOkToCancelversusCancel() {
		testChangeParentPropagationTwoChildren(
			addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 1)), Diagnostic.CANCEL,
			Diagnostic.CANCEL);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Test mapping features to controls
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testMappingDiagnosticResultToControls() {
		// Book with warning (no writer) and error (no title)
		final Book book = TestFactory.eINSTANCE.createBook();

		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(book.eClass());

		final VControl controlTitle = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Title());
		controlTitle.setDomainModelReference(domainModelReference);

		view.getChildren().add(controlTitle);

		final VControl controlWriter = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference2.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Writers());
		controlWriter.setDomainModelReference(domainModelReference2);

		view.getChildren().add(controlWriter);

		instantiateValidationService(view, book);

		VDiagnostic diagnosticTitle = controlTitle.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnosticTitle.getHighestSeverity());

		VDiagnostic diagnosticWriter = controlWriter.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticWriter.getHighestSeverity());

		VDiagnostic diagnosticBook = view.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnosticBook.getHighestSeverity());

		// change book title info
		book.setTitle("a");

		diagnosticTitle = controlTitle.getDiagnostic();
		assertEquals(Diagnostic.INFO, diagnosticTitle.getHighestSeverity());

		diagnosticWriter = controlWriter.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticWriter.getHighestSeverity());

		diagnosticBook = view.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticBook.getHighestSeverity());
	}

	@Test
	public void testMappingDiagnosticResultToControlsSameFeatureTwice() {
		// Book with warning (no writer) and error (no title)
		final Book book = TestFactory.eINSTANCE.createBook();

		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(book.eClass());

		final VControl controlTitle = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Title());
		controlTitle.setDomainModelReference(domainModelReference);

		view.getChildren().add(controlTitle);

		final VControl controlWriter1 = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference2.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Writers());
		controlWriter1.setDomainModelReference(domainModelReference2);

		view.getChildren().add(controlWriter1);

		final VControl controlWriter2 = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference3 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference3.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Writers());
		controlWriter2.setDomainModelReference(domainModelReference3);

		view.getChildren().add(controlWriter2);

		instantiateValidationService(view, book);

		VDiagnostic diagnosticTitle = controlTitle.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnosticTitle.getHighestSeverity());

		VDiagnostic diagnosticWriter1 = controlWriter1.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticWriter1.getHighestSeverity());

		VDiagnostic diagnosticWriter2 = controlWriter2.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticWriter2.getHighestSeverity());

		VDiagnostic diagnosticBook = view.getDiagnostic();
		assertEquals(Diagnostic.ERROR, diagnosticBook.getHighestSeverity());

		// change book title info
		book.setTitle("a");

		diagnosticTitle = controlTitle.getDiagnostic();
		assertEquals(Diagnostic.INFO, diagnosticTitle.getHighestSeverity());

		diagnosticWriter1 = controlWriter1.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticWriter1.getHighestSeverity());

		diagnosticWriter2 = controlWriter2.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticWriter2.getHighestSeverity());

		diagnosticBook = view.getDiagnostic();
		assertEquals(Diagnostic.WARNING, diagnosticBook.getHighestSeverity());
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Test registry
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	// @Test
	// public void testRegistry() {
	// final Library library = addBooksToLibrary(createLibaryWithWriters(1, 1, 1, 1, 1), createBooks(1, 1, 1, 1, 1));
	//
	// final View view = ViewFactory.eINSTANCE.createView();
	// view.setRootEClass(library.eClass());
	//
	// final Column parentColumn = ViewFactory.eINSTANCE.createColumn();
	// view.getChildren().add(parentColumn);
	//
	// // Writers //////////////////////////////////////
	// final Column columnWriter = ViewFactory.eINSTANCE.createColumn();
	// parentColumn.getComposites().add(columnWriter);
	//
	// final Control controlWriter = ViewFactory.eINSTANCE.createControl();
	// controlWriter.setTargetFeature(TestPackage.eINSTANCE.getWriter_FirstName());
	// controlWriter.getPathToFeature().add(TestPackage.eINSTANCE.getLibrary_Writers());
	// columnWriter.getComposites().add(controlWriter);
	//
	// // Books //////////////////////////////////////////
	// final Column columnBooks = ViewFactory.eINSTANCE.createColumn();
	// parentColumn.getComposites().add(columnBooks);
	//
	// final Control controlBooks = ViewFactory.eINSTANCE.createControl();
	// controlBooks.setTargetFeature(TestPackage.eINSTANCE.getBook_Title());
	// controlBooks.getPathToFeature().add(TestPackage.eINSTANCE.getLibrary_Books());
	// columnBooks.getComposites().add(controlBooks);
	//
	// // Test ///////////////////////////////////////////
	// final ValidationRegistry registry = new ValidationRegistry();
	// registry.register(library, view);
	//
	// final List<Renderable> librayRenderables = registry.getRenderablesForEObject(library);
	// // TODO why?
	// assertEquals("Library has false renderable count", 2, librayRenderables.size());
	// assertEquals("Wrong renderable is associated to library", parentColumn, librayRenderables.get(0));
	// assertEquals("Wrong renderable is associated to library", view, librayRenderables.get(1));
	//
	// for (final Writer writer : library.getWriters()) {
	// final List<Renderable> writerRenderables = registry.getRenderablesForEObject(writer);
	// assertEquals("Writer has false renderable count", 2, writerRenderables.size());
	// assertEquals("Wrong renderable is associated to writer", controlWriter, writerRenderables.get(0));
	// assertEquals("Wrong renderable is associated to writer", columnWriter, writerRenderables.get(1));
	// }
	//
	// for (final Book book : library.getBooks()) {
	// final List<Renderable> bookRenderables = registry.getRenderablesForEObject(book);
	// assertEquals("Book has false renderable count", 2, bookRenderables.size());
	// assertEquals("Wrong renderable is associated to book", controlBooks, bookRenderables.get(0));
	// assertEquals("Wrong renderable is associated to book", columnBooks, bookRenderables.get(1));
	// }
	//
	// final List<EObject> writerControlObjects = registry.getEObjectsForControl(controlWriter);
	// assertEquals("Number of books associated to book control is wrong.", 5, writerControlObjects.size(), 0);
	// for (final EObject o : writerControlObjects) {
	// assertTrue(library.getWriters().contains(o));
	// }
	//
	// final List<EObject> bookControlObjects = registry.getEObjectsForControl(controlBooks);
	// assertEquals("Number of books associated to book control is wrong.", 5, bookControlObjects.size(), 0);
	// for (final EObject o : bookControlObjects) {
	// assertTrue(library.getBooks().contains(o));
	// }
	//
	// // TODO remove after coverage
	// testRegistrySingleContainmentRef();
	// }

	// @Test
	// public void testRegistrySingleContainmentRef() {
	// final Library library = TestFactory.eINSTANCE.createLibrary();
	// final Writer writer = createOKWriter();
	// final Librarian librarian = TestFactory.eINSTANCE.createLibrarian();
	// library.getWriters().add(writer);
	// library.setLibrarian(librarian);
	//
	// final View view = ViewFactory.eINSTANCE.createView();
	// view.setRootEClass(library.eClass());
	//
	// final Column parentColumn = ViewFactory.eINSTANCE.createColumn();
	// view.getChildren().add(parentColumn);
	//
	// final Control controlWriterFirstName = ViewFactory.eINSTANCE.createControl();
	// controlWriterFirstName.setTargetFeature(TestPackage.eINSTANCE.getWriter_FirstName());
	// controlWriterFirstName.getPathToFeature().add(TestPackage.eINSTANCE.getLibrary_Writers());
	// parentColumn.getComposites().add(controlWriterFirstName);
	//
	// final Control controlLibrarianName = ViewFactory.eINSTANCE.createControl();
	// controlLibrarianName.setTargetFeature(TestPackage.eINSTANCE.getLibrarian_Name());
	// controlLibrarianName.getPathToFeature().add(TestPackage.eINSTANCE.getLibrary_Librarian());
	// parentColumn.getComposites().add(controlLibrarianName);
	//
	// // Test ///////////////////////////////////////////
	// final ValidationRegistry registry = new ValidationRegistry();
	// registry.register(library, view);
	//
	// final List<Renderable> librayRenderables = registry.getRenderablesForEObject(library);
	// assertEquals("Library has false renderable count", 2, librayRenderables.size());
	// assertEquals("Wrong renderable is associated to library", parentColumn, librayRenderables.get(0));
	// assertEquals("Wrong renderable is associated to library", view, librayRenderables.get(1));
	//
	// final List<Renderable> writerRenderables = registry.getRenderablesForEObject(writer);
	// assertEquals(1, writerRenderables.size(), 0);
	// assertTrue(writerRenderables.contains(controlWriterFirstName));
	//
	// final List<Renderable> librarianRenderables = registry.getRenderablesForEObject(librarian);
	// assertEquals(1, librarianRenderables.size(), 0);
	// assertTrue(librarianRenderables.contains(controlLibrarianName));
	//
	// final List<EObject> librarianObjects = registry.getEObjectsForControl(controlLibrarianName);
	// assertEquals(1, librarianObjects.size(), 0);
	// assertTrue(librarianObjects.contains(librarian));
	//
	// final List<EObject> writerNameObjects = registry.getEObjectsForControl(controlWriterFirstName);
	// assertEquals(1, writerNameObjects.size(), 0);
	// assertTrue(writerNameObjects.contains(writer));
	// }

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Test validation listener
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ValidationService setupViewModelAndRegisterListeners(VView view, Library library,
		List<ViewValidationListener> listener) {

		view.setRootEClass(library.eClass());

		final VVerticalLayout parentColumn = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(parentColumn);

		// Writers //////////////////////////////////////
		final VVerticalLayout columnWriter = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(columnWriter);

		final VControl controlWriter = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference3 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference3.setDomainModelEFeature(TestPackage.eINSTANCE.getWriter_FirstName());
		domainModelReference3.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Writers());
		controlWriter.setDomainModelReference(domainModelReference3);

		columnWriter.getChildren().add(controlWriter);

		// Books //////////////////////////////////////////
		final VVerticalLayout columnBooks = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(columnBooks);

		final VControl controlBooks = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference2.setDomainModelEFeature(TestPackage.eINSTANCE.getBook_Title());
		domainModelReference2.getDomainModelEReferencePath().add(TestPackage.eINSTANCE.getLibrary_Books());
		controlBooks.setDomainModelReference(domainModelReference2);

		columnBooks.getChildren().add(controlBooks);

		// Validation Service ///////////////////////////////
		final ValidationService validationService = new ValidationService();

		for (final ViewValidationListener l : listener) {
			validationService.registerValidationListener(l);
		}

		return validationService;
	}

	@Test
	public void testSingleListenerInitError() throws InterruptedException {
		final Library library = addBooksToLibrary(createLibaryWithWriters(0, 0, 0, 1, 0), createBooks(0, 0, 0, 0, 1));
		final VView view = VViewFactory.eINSTANCE.createView();

		final Set<Diagnostic> diagnostics = new HashSet<Diagnostic>();

		final ViewValidationListener listener = new ViewValidationListener() {
			public void onNewValidation(Set<Diagnostic> validationResults) {
				diagnostics.addAll(validationResults);
			}
		};
		final List<ViewValidationListener> listeners = new ArrayList<ViewValidationListener>();
		listeners.add(listener);

		final ValidationService validationService = setupViewModelAndRegisterListeners(view, library, listeners);

		// Validation ///////////////////////////////////////
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				validationService.instantiate(ViewModelContextFactory.INSTANCE.createViewModelContext(view, library));
			}
		});
		thread.start();
		thread.join(1000);

		assertTrue("Listener was not called or timeout reached.", diagnostics.size() > 0);
		assertEquals(2, diagnostics.size(), 0);
		int highestSeverity = Diagnostic.OK;
		for (final Diagnostic d : diagnostics) {
			highestSeverity = d.getSeverity() > highestSeverity ? d.getSeverity() : highestSeverity;
		}
		assertTrue(highestSeverity > Diagnostic.OK);
		assertEquals(Diagnostic.CANCEL, highestSeverity, 0);
	}

	@Test
	public void testSingleListenerInitOK() throws InterruptedException {
		final Library library = addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0));
		final VView view = VViewFactory.eINSTANCE.createView();

		final Set<Diagnostic> diagnostics = null;

		final ViewValidationListener listener = new ViewValidationListener() {
			@SuppressWarnings("null")
			public void onNewValidation(Set<Diagnostic> validationResults) {
				// this should not be called. fail because of NPE
				diagnostics.addAll(validationResults);
			}
		};
		final List<ViewValidationListener> listeners = new ArrayList<ViewValidationListener>();
		listeners.add(listener);

		final ValidationService validationService = setupViewModelAndRegisterListeners(view, library, listeners);

		// Validation ///////////////////////////////////////
		try {
			validationService.instantiate(ViewModelContextFactory.INSTANCE.createViewModelContext(view, library));
		} catch (final NullPointerException ex) {
			fail("Listener was called when not expected to be called");
		}
	}

	@Test
	public void testSingleListenerRegisterAfterInstantiate() throws InterruptedException {
		final Library library = addBooksToLibrary(createLibaryWithWriters(1, 0, 0, 0, 0), createBooks(1, 0, 0, 0, 0));
		final VView view = VViewFactory.eINSTANCE.createView();
		final List<ViewValidationListener> listeners = new ArrayList<ViewValidationListener>();

		final ValidationService service = setupViewModelAndRegisterListeners(view, library, listeners);
		service.instantiate(ViewModelContextFactory.INSTANCE.createViewModelContext(view, library));

		final Set<Diagnostic> diagnostics = new HashSet<Diagnostic>();

		final ViewValidationListener listener = new ViewValidationListener() {
			public void onNewValidation(Set<Diagnostic> validationResults) {
				diagnostics.addAll(validationResults);
			}
		};

		service.registerValidationListener(listener);

		final Thread thread = new Thread(new Runnable() {
			public void run() {
				changeWriter(library.getWriters().get(0), Diagnostic.WARNING);
			}
		});
		thread.start();
		thread.join(1000);

		assertTrue("Listener was not called or timeout reached.", diagnostics.size() > 0);
		assertEquals(1, diagnostics.size(), 0);
		assertEquals(Diagnostic.WARNING, diagnostics.iterator().next().getSeverity(), 0);

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Util from here
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void instantiateValidationService(VView view, final EObject domainModel) {
		ViewModelContextFactory.INSTANCE.createViewModelContext(view, domainModel);
	}

	/**
	 * Creates a library with the given count of writers and severities.
	 */
	private Library createLibaryWithWriters(int ok, int info, int warning, int error, int cancel) {
		final Library library = TestFactory.eINSTANCE.createLibrary();
		final Set<Writer> writers = new HashSet<Writer>();
		for (int i = 0; i < ok; i++) {
			writers.add(createOKWriter());
		}
		for (int i = 0; i < info; i++) {
			writers.add(createInfoWriter());
		}
		for (int i = 0; i < warning; i++) {
			writers.add(createWarningWriter());
		}
		for (int i = 0; i < error; i++) {
			writers.add(createErrorWriter());
		}
		for (int i = 0; i < cancel; i++) {
			writers.add(createCancelWriter());
		}
		library.getWriters().addAll(writers);
		return library;
	}

	/**
	 * Creates a set of books with the given count of severities.
	 */
	private Set<Book> createBooks(int ok, int info, int warning, int error, int cancel) {
		final Set<Book> books = new HashSet<Book>();
		for (int i = 0; i < ok; i++) {
			books.add(createOKBook());
		}
		for (int i = 0; i < info; i++) {
			books.add(createInfoBook());
		}
		for (int i = 0; i < warning; i++) {
			books.add(createWarningBook());
		}
		for (int i = 0; i < error; i++) {
			books.add(createErrorBook());
		}
		for (int i = 0; i < cancel; i++) {
			books.add(createCancelBook());
		}
		return books;
	}

	private Library addBooksToLibrary(Library library, Set<Book> books) {
		library.getBooks().addAll(books);
		return library;
	}

	private Writer createOKWriter() {
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		return changeWriter(writer, Diagnostic.OK);
	}

	private Writer createInfoWriter() {
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		return changeWriter(writer, Diagnostic.INFO);
	}

	private Writer createWarningWriter() {
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		return changeWriter(writer, Diagnostic.WARNING);
	}

	private Writer createErrorWriter() {
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		return changeWriter(writer, Diagnostic.ERROR);
	}

	private Writer createCancelWriter() {
		final Writer writer = TestFactory.eINSTANCE.createWriter();
		return changeWriter(writer, Diagnostic.CANCEL);
	}

	private Writer changeWriter(Writer writer, int changeToSeverity) {
		switch (changeToSeverity) {
		case Diagnostic.OK:
			writer.setFirstName("Hans");
			// writer.setLastName("Meyer");
			break;
		case Diagnostic.INFO:
			writer.setFirstName("Ha");
			// writer.setLastName("M");
			break;
		case Diagnostic.WARNING:
			writer.setFirstName("H");
			// writer.setLastName("Hans");
			break;
		case Diagnostic.ERROR:
			writer.setFirstName("");
			// writer.setLastName("");
			break;
		case Diagnostic.CANCEL:
			writer.setFirstName("Offensive");
			// writer.setLastName("Offensive");
			break;
		default:
			org.junit.Assert.fail("No Diagnostic value like " + changeToSeverity + "found.");
		}
		return writer;
	}

	private Book createOKBook() {
		final Writer writer = createOKWriter();
		final Book book = TestFactory.eINSTANCE.createBook();
		book.setTitle("Eine Chronik");
		book.setWriters(writer);
		return book;
	}

	private Book createInfoBook() {
		final Writer writer = createOKWriter();
		final Book book = TestFactory.eINSTANCE.createBook();
		book.setTitle("C");
		book.setWriters(writer);
		return book;
	}

	private Book createWarningBook() {
		final Book book = TestFactory.eINSTANCE.createBook();
		book.setTitle("Warning");
		return book;
	}

	private Book createErrorBook() {
		final Writer writer = createOKWriter();
		final Book book = TestFactory.eINSTANCE.createBook();
		book.setWriters(writer);
		return book;
	}

	private Book createCancelBook() {
		final Writer writer = createOKWriter();
		final Book book = TestFactory.eINSTANCE.createBook();
		book.setTitle("Offensive");
		book.setWriters(writer);
		return book;
	}
}
