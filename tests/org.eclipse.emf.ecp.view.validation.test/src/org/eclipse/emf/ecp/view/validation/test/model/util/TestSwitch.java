/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.validation.test.model.Book;
import org.eclipse.emf.ecp.view.validation.test.model.Computer;
import org.eclipse.emf.ecp.view.validation.test.model.Container;
import org.eclipse.emf.ecp.view.validation.test.model.Content;
import org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer;
import org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent;
import org.eclipse.emf.ecp.view.validation.test.model.Librarian;
import org.eclipse.emf.ecp.view.validation.test.model.Library;
import org.eclipse.emf.ecp.view.validation.test.model.Mainboard;
import org.eclipse.emf.ecp.view.validation.test.model.PowerBlock;
import org.eclipse.emf.ecp.view.validation.test.model.Referencer;
import org.eclipse.emf.ecp.view.validation.test.model.TableContent;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithMultiplicity;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithUnique;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;
import org.eclipse.emf.ecp.view.validation.test.model.Writer;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each
 * class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage
 * @generated
 */
public class TestSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static TestPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TestSwitch() {
		if (modelPackage == null) {
			modelPackage = TestPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case TestPackage.LIBRARY: {
			final Library library = (Library) theEObject;
			T result = caseLibrary(library);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.WRITER: {
			final Writer writer = (Writer) theEObject;
			T result = caseWriter(writer);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.BOOK: {
			final Book book = (Book) theEObject;
			T result = caseBook(book);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.LIBRARIAN: {
			final Librarian librarian = (Librarian) theEObject;
			T result = caseLibrarian(librarian);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.COMPUTER: {
			final Computer computer = (Computer) theEObject;
			T result = caseComputer(computer);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.MAINBOARD: {
			final Mainboard mainboard = (Mainboard) theEObject;
			T result = caseMainboard(mainboard);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.POWER_BLOCK: {
			final PowerBlock powerBlock = (PowerBlock) theEObject;
			T result = casePowerBlock(powerBlock);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.CONTAINER: {
			final Container container = (Container) theEObject;
			T result = caseContainer(container);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.CONTENT: {
			final Content content = (Content) theEObject;
			T result = caseContent(content);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_WITH_MULTIPLICITY: {
			final TableWithMultiplicity tableWithMultiplicity = (TableWithMultiplicity) theEObject;
			T result = caseTableWithMultiplicity(tableWithMultiplicity);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_CONTENT: {
			final TableContent tableContent = (TableContent) theEObject;
			T result = caseTableContent(tableContent);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_CONTENT_WITHOUT_VALIDATION: {
			final TableContentWithoutValidation tableContentWithoutValidation = (TableContentWithoutValidation) theEObject;
			T result = caseTableContentWithoutValidation(tableContentWithoutValidation);
			if (result == null) {
				result = caseTableContent(tableContentWithoutValidation);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_CONTENT_WITH_VALIDATION: {
			final TableContentWithValidation tableContentWithValidation = (TableContentWithValidation) theEObject;
			T result = caseTableContentWithValidation(tableContentWithValidation);
			if (result == null) {
				result = caseTableContent(tableContentWithValidation);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_WITHOUT_MULTIPLICITY: {
			final TableWithoutMultiplicity tableWithoutMultiplicity = (TableWithoutMultiplicity) theEObject;
			T result = caseTableWithoutMultiplicity(tableWithoutMultiplicity);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_WITH_UNIQUE: {
			final TableWithUnique tableWithUnique = (TableWithUnique) theEObject;
			T result = caseTableWithUnique(tableWithUnique);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_CONTENT_WITH_INNER_CHILD2: {
			final TableContentWithInnerChild2 tableContentWithInnerChild2 = (TableContentWithInnerChild2) theEObject;
			T result = caseTableContentWithInnerChild2(tableContentWithInnerChild2);
			if (result == null) {
				result = caseTableContent(tableContentWithInnerChild2);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_CONTENT_WITH_INNER_CHILD: {
			final TableContentWithInnerChild tableContentWithInnerChild = (TableContentWithInnerChild) theEObject;
			T result = caseTableContentWithInnerChild(tableContentWithInnerChild);
			if (result == null) {
				result = caseTableContent(tableContentWithInnerChild);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.TABLE_WITHOUT_MULTIPLICITY_CONCRETE: {
			final TableWithoutMultiplicityConcrete tableWithoutMultiplicityConcrete = (TableWithoutMultiplicityConcrete) theEObject;
			T result = caseTableWithoutMultiplicityConcrete(tableWithoutMultiplicityConcrete);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.REFERENCER: {
			final Referencer referencer = (Referencer) theEObject;
			T result = caseReferencer(referencer);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.CROSS_REFERENCE_CONTAINER: {
			final CrossReferenceContainer crossReferenceContainer = (CrossReferenceContainer) theEObject;
			T result = caseCrossReferenceContainer(crossReferenceContainer);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case TestPackage.CROSS_REFERENCE_CONTENT: {
			final CrossReferenceContent crossReferenceContent = (CrossReferenceContent) theEObject;
			T result = caseCrossReferenceContent(crossReferenceContent);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Library</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Library</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLibrary(Library object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Writer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Writer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWriter(Writer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Book</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Book</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBook(Book object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Librarian</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Librarian</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLibrarian(Librarian object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Computer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Computer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComputer(Computer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mainboard</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mainboard</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMainboard(Mainboard object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Power Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Power Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePowerBlock(PowerBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainer(Container object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Content</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContent(Content object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table With Multiplicity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table With Multiplicity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableWithMultiplicity(TableWithMultiplicity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Content</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableContent(TableContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Content Without Validation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Content Without Validation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableContentWithoutValidation(TableContentWithoutValidation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Content With Validation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Content With Validation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableContentWithValidation(TableContentWithValidation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Without Multiplicity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Without Multiplicity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableWithoutMultiplicity(TableWithoutMultiplicity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table With Unique</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table With Unique</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableWithUnique(TableWithUnique object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Content With Inner Child2</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Content With Inner Child2</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableContentWithInnerChild2(TableContentWithInnerChild2 object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Content With Inner Child</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Content With Inner Child</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableContentWithInnerChild(TableContentWithInnerChild object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Without Multiplicity Concrete</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Without Multiplicity Concrete</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableWithoutMultiplicityConcrete(TableWithoutMultiplicityConcrete object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Referencer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Referencer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferencer(Referencer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cross Reference Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cross Reference Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCrossReferenceContainer(CrossReferenceContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cross Reference Content</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cross Reference Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCrossReferenceContent(CrossReferenceContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // TestSwitch
