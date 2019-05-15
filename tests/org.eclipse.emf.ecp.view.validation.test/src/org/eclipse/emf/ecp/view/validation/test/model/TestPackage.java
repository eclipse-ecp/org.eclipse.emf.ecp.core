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
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bug 543190
 *******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.validation.test.model.TestFactory
 * @model kind="package"
 * @generated
 */
public interface TestPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "htp://www.eclipse.org/emf/ecp/view/validation/test/model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.validation.test.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	TestPackage eINSTANCE = org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.LibraryImpl
	 * <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.LibraryImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Writers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY__WRITERS = 1;

	/**
	 * The feature id for the '<em><b>Books</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY__BOOKS = 2;

	/**
	 * The feature id for the '<em><b>Librarian</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY__LIBRARIAN = 3;

	/**
	 * The feature id for the '<em><b>Phone Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY__PHONE_NUMBER = 4;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl
	 * <em>Writer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getWriter()
	 * @generated
	 */
	int WRITER = 1;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__FIRST_NAME = 0;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__LAST_NAME = 1;

	/**
	 * The feature id for the '<em><b>EMail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__EMAIL = 2;

	/**
	 * The feature id for the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__BIRTH_DATE = 3;

	/**
	 * The feature id for the '<em><b>Books</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__BOOKS = 4;

	/**
	 * The feature id for the '<em><b>Pseudonym</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__PSEUDONYM = 5;

	/**
	 * The feature id for the '<em><b>Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__LIBRARY = 6;

	/**
	 * The feature id for the '<em><b>Initials</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__INITIALS = 7;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER__TITLE = 8;

	/**
	 * The number of structural features of the '<em>Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WRITER_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.BookImpl <em>Book</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.BookImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getBook()
	 * @generated
	 */
	int BOOK = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOK__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOK__PAGES = 1;

	/**
	 * The feature id for the '<em><b>Writers</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOK__WRITERS = 2;

	/**
	 * The number of structural features of the '<em>Book</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOK_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.LibrarianImpl
	 * <em>Librarian</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.LibrarianImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getLibrarian()
	 * @generated
	 */
	int LIBRARIAN = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARIAN__NAME = 0;

	/**
	 * The number of structural features of the '<em>Librarian</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARIAN_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ComputerImpl
	 * <em>Computer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ComputerImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getComputer()
	 * @generated
	 */
	int COMPUTER = 4;

	/**
	 * The feature id for the '<em><b>Mainboard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPUTER__MAINBOARD = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPUTER__NAME = 1;

	/**
	 * The feature id for the '<em><b>Power Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPUTER__POWER_BLOCK = 2;

	/**
	 * The feature id for the '<em><b>Colors</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPUTER__COLORS = 3;

	/**
	 * The number of structural features of the '<em>Computer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPUTER_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.MainboardImpl
	 * <em>Mainboard</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.MainboardImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getMainboard()
	 * @generated
	 */
	int MAINBOARD = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAINBOARD__NAME = 0;

	/**
	 * The number of structural features of the '<em>Mainboard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAINBOARD_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PowerBlockImpl <em>Power
	 * Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.PowerBlockImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getPowerBlock()
	 * @generated
	 */
	int POWER_BLOCK = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POWER_BLOCK__NAME = 0;

	/**
	 * The number of structural features of the '<em>Power Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POWER_BLOCK_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ContainerImpl
	 * <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ContainerImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 7;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__CONTENTS = 0;

	/**
	 * The number of structural features of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ContentImpl
	 * <em>Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ContentImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getContent()
	 * @generated
	 */
	int CONTENT = 8;

	/**
	 * The feature id for the '<em><b>Unique Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTENT__UNIQUE_ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Second Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTENT__SECOND_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableObjectImpl <em>Table
	 * Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableObjectImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableObject()
	 * @generated
	 */
	int TABLE_OBJECT = 22;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_OBJECT__LABEL = 0;

	/**
	 * The number of structural features of the '<em>Table Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithMultiplicityImpl
	 * <em>Table With Multiplicity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithMultiplicityImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithMultiplicity()
	 * @generated
	 */
	int TABLE_WITH_MULTIPLICITY = 9;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_MULTIPLICITY__LABEL = TABLE_OBJECT__LABEL;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_MULTIPLICITY__CONTENT = TABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Table With Multiplicity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_MULTIPLICITY_FEATURE_COUNT = TABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentImpl <em>Table
	 * Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContent()
	 * @generated
	 */
	int TABLE_CONTENT = 10;

	/**
	 * The number of structural features of the '<em>Table Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithoutValidationImpl <em>Table Content
	 * Without Validation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithoutValidationImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithoutValidation()
	 * @generated
	 */
	int TABLE_CONTENT_WITHOUT_VALIDATION = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITHOUT_VALIDATION__NAME = TABLE_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITHOUT_VALIDATION__WEIGHT = TABLE_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Table Content Without Validation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITHOUT_VALIDATION_FEATURE_COUNT = TABLE_CONTENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithValidationImpl <em>Table Content With
	 * Validation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithValidationImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithValidation()
	 * @generated
	 */
	int TABLE_CONTENT_WITH_VALIDATION = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_VALIDATION__NAME = TABLE_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_VALIDATION__WEIGHT = TABLE_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Table Content With Validation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_VALIDATION_FEATURE_COUNT = TABLE_CONTENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityImpl <em>Table Without
	 * Multiplicity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithoutMultiplicity()
	 * @generated
	 */
	int TABLE_WITHOUT_MULTIPLICITY = 13;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY__LABEL = TABLE_OBJECT__LABEL;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY__CONTENT = TABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Table Without Multiplicity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY_FEATURE_COUNT = TABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithUniqueImpl
	 * <em>Table With Unique</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithUniqueImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithUnique()
	 * @generated
	 */
	int TABLE_WITH_UNIQUE = 14;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_UNIQUE__LABEL = TABLE_OBJECT__LABEL;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_UNIQUE__CONTENT = TABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Table With Unique</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_UNIQUE_FEATURE_COUNT = TABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChild2Impl <em>Table Content
	 * With Inner Child2</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChild2Impl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithInnerChild2()
	 * @generated
	 */
	int TABLE_CONTENT_WITH_INNER_CHILD2 = 15;

	/**
	 * The feature id for the '<em><b>Inner Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_INNER_CHILD2__INNER_CHILD = TABLE_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Table Content With Inner Child2</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_INNER_CHILD2_FEATURE_COUNT = TABLE_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChildImpl <em>Table Content With
	 * Inner Child</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChildImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithInnerChild()
	 * @generated
	 */
	int TABLE_CONTENT_WITH_INNER_CHILD = 16;

	/**
	 * The feature id for the '<em><b>Inner Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_INNER_CHILD__INNER_CHILD = TABLE_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stuff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_INNER_CHILD__STUFF = TABLE_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Table Content With Inner Child</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTENT_WITH_INNER_CHILD_FEATURE_COUNT = TABLE_CONTENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityConcreteImpl <em>Table
	 * Without Multiplicity Concrete</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityConcreteImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithoutMultiplicityConcrete()
	 * @generated
	 */
	int TABLE_WITHOUT_MULTIPLICITY_CONCRETE = 17;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY_CONCRETE__LABEL = TABLE_OBJECT__LABEL;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY_CONCRETE__CONTENT = TABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Table Without Multiplicity Concrete</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY_CONCRETE_FEATURE_COUNT = TABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ReferencerImpl
	 * <em>Referencer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ReferencerImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getReferencer()
	 * @generated
	 */
	int REFERENCER = 18;

	/**
	 * The feature id for the '<em><b>Referenced Content</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REFERENCER__REFERENCED_CONTENT = 0;

	/**
	 * The number of structural features of the '<em>Referencer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REFERENCER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContainerImpl <em>Cross Reference
	 * Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContainerImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getCrossReferenceContainer()
	 * @generated
	 */
	int CROSS_REFERENCE_CONTAINER = 19;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CROSS_REFERENCE_CONTAINER__CONTENTS = 0;

	/**
	 * The feature id for the '<em><b>Single Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CROSS_REFERENCE_CONTAINER__SINGLE_CONTENT = 1;

	/**
	 * The number of structural features of the '<em>Cross Reference Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CROSS_REFERENCE_CONTAINER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContentImpl
	 * <em>Cross Reference Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContentImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getCrossReferenceContent()
	 * @generated
	 */
	int CROSS_REFERENCE_CONTENT = 20;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CROSS_REFERENCE_CONTENT__PARENT = 0;

	/**
	 * The feature id for the '<em><b>Single Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CROSS_REFERENCE_CONTENT__SINGLE_PARENT = 1;

	/**
	 * The number of structural features of the '<em>Cross Reference Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CROSS_REFERENCE_CONTENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl
	 * <em>Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getPerson()
	 * @generated
	 */
	int PERSON = 21;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PERSON__FIRST_NAME = 0;

	/**
	 * The feature id for the '<em><b>Gender</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PERSON__GENDER = 1;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PERSON__LAST_NAME = 2;

	/**
	 * The feature id for the '<em><b>Custom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PERSON__CUSTOM = 3;

	/**
	 * The feature id for the '<em><b>Age</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PERSON__AGE = 4;

	/**
	 * The number of structural features of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PERSON_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '<em>String With Max Length8</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getStringWithMaxLength8()
	 * @generated
	 */
	int STRING_WITH_MAX_LENGTH8 = 25;

	/**
	 * The meta object id for the '<em>Only Capitals</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getOnlyCapitals()
	 * @generated
	 */
	int ONLY_CAPITALS = 26;

	/**
	 * The meta object id for the '<em>Custom Data Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getCustomDataType()
	 * @generated
	 */
	int CUSTOM_DATA_TYPE = 27;

	/**
	 * The meta object id for the '<em>Phone Number</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getPhoneNumber()
	 * @generated
	 */
	int PHONE_NUMBER = 28;

	/**
	 * The meta object id for the '<em>Min Length Of3</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getMinLengthOf3()
	 * @generated
	 */
	int MIN_LENGTH_OF3 = 29;

	/**
	 * The meta object id for the '<em>Strict Min Length Of3</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getStrictMinLengthOf3()
	 * @generated
	 */
	int STRICT_MIN_LENGTH_OF3 = 30;

	/**
	 * The meta object id for the '<em>Age</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see java.lang.Integer
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getAge()
	 * @generated
	 */
	int AGE = 31;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.Gender <em>Gender</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Gender
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getGender()
	 * @generated
	 */
	int GENDER = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.Color <em>Color</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Color
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getColor()
	 * @generated
	 */
	int COLOR = 24;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Library
	 * <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Library</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Library#getName
	 * <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Library#getName()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Name();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Library#getWriters <em>Writers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Writers</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Library#getWriters()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Writers();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Library#getBooks <em>Books</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Books</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Library#getBooks()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Books();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Library#getLibrarian <em>Librarian</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Librarian</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Library#getLibrarian()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_Librarian();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Library#getPhoneNumber <em>Phone Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Phone Number</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Library#getPhoneNumber()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_PhoneNumber();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer
	 * <em>Writer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Writer</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer
	 * @generated
	 */
	EClass getWriter();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getFirstName()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_FirstName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getLastName()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_LastName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getEMail
	 * <em>EMail</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>EMail</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getEMail()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_EMail();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getBirthDate <em>Birth Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Birth Date</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getBirthDate()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_BirthDate();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getBooks <em>Books</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Books</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getBooks()
	 * @see #getWriter()
	 * @generated
	 */
	EReference getWriter_Books();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#isPseudonym <em>Pseudonym</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Pseudonym</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#isPseudonym()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_Pseudonym();

	/**
	 * Returns the meta object for the container reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Library</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getLibrary()
	 * @see #getWriter()
	 * @generated
	 */
	EReference getWriter_Library();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getInitials <em>Initials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Initials</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getInitials()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_Initials();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getTitle
	 * <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer#getTitle()
	 * @see #getWriter()
	 * @generated
	 */
	EAttribute getWriter_Title();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Book <em>Book</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Book</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Book
	 * @generated
	 */
	EClass getBook();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Book#getTitle
	 * <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Book#getTitle()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Book#getPages
	 * <em>Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Pages</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Book#getPages()
	 * @see #getBook()
	 * @generated
	 */
	EAttribute getBook_Pages();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.ecp.view.validation.test.model.Book#getWriters
	 * <em>Writers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Writers</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Book#getWriters()
	 * @see #getBook()
	 * @generated
	 */
	EReference getBook_Writers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Librarian
	 * <em>Librarian</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Librarian</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Librarian
	 * @generated
	 */
	EClass getLibrarian();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Librarian#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Librarian#getName()
	 * @see #getLibrarian()
	 * @generated
	 */
	EAttribute getLibrarian_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Computer
	 * <em>Computer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Computer</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Computer
	 * @generated
	 */
	EClass getComputer();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Computer#getMainboard <em>Mainboard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Mainboard</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Computer#getMainboard()
	 * @see #getComputer()
	 * @generated
	 */
	EReference getComputer_Mainboard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Computer#getName
	 * <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Computer#getName()
	 * @see #getComputer()
	 * @generated
	 */
	EAttribute getComputer_Name();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Computer#getPowerBlock <em>Power Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Power Block</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Computer#getPowerBlock()
	 * @see #getComputer()
	 * @generated
	 */
	EReference getComputer_PowerBlock();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Computer#getColors <em>Colors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Colors</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Computer#getColors()
	 * @see #getComputer()
	 * @generated
	 */
	EAttribute getComputer_Colors();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Mainboard
	 * <em>Mainboard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Mainboard</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Mainboard
	 * @generated
	 */
	EClass getMainboard();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Mainboard#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Mainboard#getName()
	 * @see #getMainboard()
	 * @generated
	 */
	EAttribute getMainboard_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.PowerBlock <em>Power
	 * Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Power Block</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.PowerBlock
	 * @generated
	 */
	EClass getPowerBlock();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.PowerBlock#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.PowerBlock#getName()
	 * @see #getPowerBlock()
	 * @generated
	 */
	EAttribute getPowerBlock_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Container
	 * <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Container#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Container#getContents()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_Contents();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Content
	 * <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Content
	 * @generated
	 */
	EClass getContent();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Content#getUniqueAttribute <em>Unique Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Unique Attribute</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Content#getUniqueAttribute()
	 * @see #getContent()
	 * @generated
	 */
	EAttribute getContent_UniqueAttribute();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Content#getSecondAttribute <em>Second Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Second Attribute</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Content#getSecondAttribute()
	 * @see #getContent()
	 * @generated
	 */
	EAttribute getContent_SecondAttribute();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithMultiplicity
	 * <em>Table With Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table With Multiplicity</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithMultiplicity
	 * @generated
	 */
	EClass getTableWithMultiplicity();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithMultiplicity#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithMultiplicity#getContent()
	 * @see #getTableWithMultiplicity()
	 * @generated
	 */
	EReference getTableWithMultiplicity_Content();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContent <em>Table
	 * Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContent
	 * @generated
	 */
	EClass getTableContent();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation <em>Table Content Without
	 * Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Content Without Validation</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation
	 * @generated
	 */
	EClass getTableContentWithoutValidation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation#getName()
	 * @see #getTableContentWithoutValidation()
	 * @generated
	 */
	EAttribute getTableContentWithoutValidation_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation#getWeight()
	 * @see #getTableContentWithoutValidation()
	 * @generated
	 */
	EAttribute getTableContentWithoutValidation_Weight();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation <em>Table Content With
	 * Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Content With Validation</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation
	 * @generated
	 */
	EClass getTableContentWithValidation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation#getName()
	 * @see #getTableContentWithValidation()
	 * @generated
	 */
	EAttribute getTableContentWithValidation_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation#getWeight()
	 * @see #getTableContentWithValidation()
	 * @generated
	 */
	EAttribute getTableContentWithValidation_Weight();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity
	 * <em>Table Without Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Without Multiplicity</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity
	 * @generated
	 */
	EClass getTableWithoutMultiplicity();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity#getContent()
	 * @see #getTableWithoutMultiplicity()
	 * @generated
	 */
	EReference getTableWithoutMultiplicity_Content();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithUnique
	 * <em>Table With Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table With Unique</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithUnique
	 * @generated
	 */
	EClass getTableWithUnique();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithUnique#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithUnique#getContent()
	 * @see #getTableWithUnique()
	 * @generated
	 */
	EReference getTableWithUnique_Content();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2 <em>Table Content With Inner
	 * Child2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Content With Inner Child2</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2
	 * @generated
	 */
	EClass getTableContentWithInnerChild2();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2#getInnerChild <em>Inner
	 * Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Inner Child</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2#getInnerChild()
	 * @see #getTableContentWithInnerChild2()
	 * @generated
	 */
	EReference getTableContentWithInnerChild2_InnerChild();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild <em>Table Content With Inner
	 * Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Content With Inner Child</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild
	 * @generated
	 */
	EClass getTableContentWithInnerChild();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild#getInnerChild <em>Inner
	 * Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Inner Child</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild#getInnerChild()
	 * @see #getTableContentWithInnerChild()
	 * @generated
	 */
	EReference getTableContentWithInnerChild_InnerChild();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild#getStuff <em>Stuff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Stuff</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild#getStuff()
	 * @see #getTableContentWithInnerChild()
	 * @generated
	 */
	EAttribute getTableContentWithInnerChild_Stuff();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete <em>Table Without
	 * Multiplicity Concrete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Table Without Multiplicity Concrete</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete
	 * @generated
	 */
	EClass getTableWithoutMultiplicityConcrete();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete#getContent
	 * <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete#getContent()
	 * @see #getTableWithoutMultiplicityConcrete()
	 * @generated
	 */
	EReference getTableWithoutMultiplicityConcrete_Content();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Referencer
	 * <em>Referencer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Referencer</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Referencer
	 * @generated
	 */
	EClass getReferencer();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Referencer#getReferencedContent <em>Referenced
	 * Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Referenced Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Referencer#getReferencedContent()
	 * @see #getReferencer()
	 * @generated
	 */
	EReference getReferencer_ReferencedContent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer
	 * <em>Cross Reference Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cross Reference Container</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer
	 * @generated
	 */
	EClass getCrossReferenceContainer();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer#getContents()
	 * @see #getCrossReferenceContainer()
	 * @generated
	 */
	EReference getCrossReferenceContainer_Contents();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer#getSingleContent <em>Single
	 * Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Single Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer#getSingleContent()
	 * @see #getCrossReferenceContainer()
	 * @generated
	 */
	EReference getCrossReferenceContainer_SingleContent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent
	 * <em>Cross Reference Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Cross Reference Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent
	 * @generated
	 */
	EClass getCrossReferenceContent();

	/**
	 * Returns the meta object for the container reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent#getParent()
	 * @see #getCrossReferenceContent()
	 * @generated
	 */
	EReference getCrossReferenceContent_Parent();

	/**
	 * Returns the meta object for the container reference
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent#getSingleParent <em>Single
	 * Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Single Parent</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent#getSingleParent()
	 * @see #getCrossReferenceContent()
	 * @generated
	 */
	EReference getCrossReferenceContent_SingleParent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Person
	 * <em>Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Person</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Person
	 * @generated
	 */
	EClass getPerson();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Person#getFirstName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_FirstName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getGender
	 * <em>Gender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Gender</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Person#getGender()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Gender();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Person#getLastName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_LastName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getCustom
	 * <em>Custom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Custom</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Person#getCustom()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Custom();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.validation.test.model.Person#getAge
	 * <em>Age</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Age</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Person#getAge()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Age();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.TableObject <em>Table
	 * Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Table Object</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableObject
	 * @generated
	 */
	EClass getTableObject();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.validation.test.model.TableObject#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableObject#getLabel()
	 * @see #getTableObject()
	 * @generated
	 */
	EAttribute getTableObject_Label();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>String With Max Length8</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>String With Max Length8</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="maxLength='8'"
	 * @generated
	 */
	EDataType getStringWithMaxLength8();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Only Capitals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Only Capitals</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="pattern='[A-Z]+'"
	 * @generated
	 */
	EDataType getOnlyCapitals();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Custom Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Custom Data Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getCustomDataType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Phone Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Phone Number</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="pattern='((\\+)?[a-c0-9*#]{1,20}){0,1}' loosePattern='[+0-9]+'"
	 * @generated
	 */
	EDataType getPhoneNumber();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Min Length Of3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Min Length Of3</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="minLength='3' looseMinLength='0'"
	 * @generated
	 */
	EDataType getMinLengthOf3();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Strict Min Length Of3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Strict Min Length Of3</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="minLength='3'"
	 * @generated
	 */
	EDataType getStrictMinLengthOf3();

	/**
	 * Returns the meta object for data type '{@link java.lang.Integer <em>Age</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Age</em>'.
	 * @see java.lang.Integer
	 * @model instanceClass="java.lang.Integer"
	 *        extendedMetaData="minInclusive='0' maxInclusive='100'"
	 * @generated
	 */
	EDataType getAge();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.validation.test.model.Gender <em>Gender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Gender</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Gender
	 * @generated
	 */
	EEnum getGender();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.validation.test.model.Color <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Color</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Color
	 * @generated
	 */
	EEnum getColor();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestFactory getTestFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.LibraryImpl
		 * <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.LibraryImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LIBRARY__NAME = eINSTANCE.getLibrary_Name();

		/**
		 * The meta object literal for the '<em><b>Writers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LIBRARY__WRITERS = eINSTANCE.getLibrary_Writers();

		/**
		 * The meta object literal for the '<em><b>Books</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LIBRARY__BOOKS = eINSTANCE.getLibrary_Books();

		/**
		 * The meta object literal for the '<em><b>Librarian</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LIBRARY__LIBRARIAN = eINSTANCE.getLibrary_Librarian();

		/**
		 * The meta object literal for the '<em><b>Phone Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LIBRARY__PHONE_NUMBER = eINSTANCE.getLibrary_PhoneNumber();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl
		 * <em>Writer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getWriter()
		 * @generated
		 */
		EClass WRITER = eINSTANCE.getWriter();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WRITER__FIRST_NAME = eINSTANCE.getWriter_FirstName();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WRITER__LAST_NAME = eINSTANCE.getWriter_LastName();

		/**
		 * The meta object literal for the '<em><b>EMail</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WRITER__EMAIL = eINSTANCE.getWriter_EMail();

		/**
		 * The meta object literal for the '<em><b>Birth Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WRITER__BIRTH_DATE = eINSTANCE.getWriter_BirthDate();

		/**
		 * The meta object literal for the '<em><b>Books</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WRITER__BOOKS = eINSTANCE.getWriter_Books();

		/**
		 * The meta object literal for the '<em><b>Pseudonym</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WRITER__PSEUDONYM = eINSTANCE.getWriter_Pseudonym();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WRITER__LIBRARY = eINSTANCE.getWriter_Library();

		/**
		 * The meta object literal for the '<em><b>Initials</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WRITER__INITIALS = eINSTANCE.getWriter_Initials();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WRITER__TITLE = eINSTANCE.getWriter_Title();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.BookImpl
		 * <em>Book</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.BookImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getBook()
		 * @generated
		 */
		EClass BOOK = eINSTANCE.getBook();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOK__TITLE = eINSTANCE.getBook_Title();

		/**
		 * The meta object literal for the '<em><b>Pages</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOK__PAGES = eINSTANCE.getBook_Pages();

		/**
		 * The meta object literal for the '<em><b>Writers</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BOOK__WRITERS = eINSTANCE.getBook_Writers();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.LibrarianImpl
		 * <em>Librarian</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.LibrarianImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getLibrarian()
		 * @generated
		 */
		EClass LIBRARIAN = eINSTANCE.getLibrarian();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LIBRARIAN__NAME = eINSTANCE.getLibrarian_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ComputerImpl
		 * <em>Computer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ComputerImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getComputer()
		 * @generated
		 */
		EClass COMPUTER = eINSTANCE.getComputer();

		/**
		 * The meta object literal for the '<em><b>Mainboard</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPUTER__MAINBOARD = eINSTANCE.getComputer_Mainboard();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COMPUTER__NAME = eINSTANCE.getComputer_Name();

		/**
		 * The meta object literal for the '<em><b>Power Block</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPUTER__POWER_BLOCK = eINSTANCE.getComputer_PowerBlock();

		/**
		 * The meta object literal for the '<em><b>Colors</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COMPUTER__COLORS = eINSTANCE.getComputer_Colors();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.MainboardImpl
		 * <em>Mainboard</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.MainboardImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getMainboard()
		 * @generated
		 */
		EClass MAINBOARD = eINSTANCE.getMainboard();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MAINBOARD__NAME = eINSTANCE.getMainboard_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PowerBlockImpl
		 * <em>Power Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.PowerBlockImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getPowerBlock()
		 * @generated
		 */
		EClass POWER_BLOCK = eINSTANCE.getPowerBlock();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POWER_BLOCK__NAME = eINSTANCE.getPowerBlock_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ContainerImpl
		 * <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ContainerImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTAINER__CONTENTS = eINSTANCE.getContainer_Contents();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ContentImpl
		 * <em>Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ContentImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getContent()
		 * @generated
		 */
		EClass CONTENT = eINSTANCE.getContent();

		/**
		 * The meta object literal for the '<em><b>Unique Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONTENT__UNIQUE_ATTRIBUTE = eINSTANCE.getContent_UniqueAttribute();

		/**
		 * The meta object literal for the '<em><b>Second Attribute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONTENT__SECOND_ATTRIBUTE = eINSTANCE.getContent_SecondAttribute();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithMultiplicityImpl <em>Table With
		 * Multiplicity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithMultiplicityImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithMultiplicity()
		 * @generated
		 */
		EClass TABLE_WITH_MULTIPLICITY = eINSTANCE.getTableWithMultiplicity();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_WITH_MULTIPLICITY__CONTENT = eINSTANCE.getTableWithMultiplicity_Content();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentImpl
		 * <em>Table Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContent()
		 * @generated
		 */
		EClass TABLE_CONTENT = eINSTANCE.getTableContent();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithoutValidationImpl <em>Table
		 * Content Without Validation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithoutValidationImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithoutValidation()
		 * @generated
		 */
		EClass TABLE_CONTENT_WITHOUT_VALIDATION = eINSTANCE.getTableContentWithoutValidation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTENT_WITHOUT_VALIDATION__NAME = eINSTANCE.getTableContentWithoutValidation_Name();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTENT_WITHOUT_VALIDATION__WEIGHT = eINSTANCE.getTableContentWithoutValidation_Weight();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithValidationImpl <em>Table Content
		 * With Validation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithValidationImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithValidation()
		 * @generated
		 */
		EClass TABLE_CONTENT_WITH_VALIDATION = eINSTANCE.getTableContentWithValidation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTENT_WITH_VALIDATION__NAME = eINSTANCE.getTableContentWithValidation_Name();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTENT_WITH_VALIDATION__WEIGHT = eINSTANCE.getTableContentWithValidation_Weight();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityImpl <em>Table Without
		 * Multiplicity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithoutMultiplicity()
		 * @generated
		 */
		EClass TABLE_WITHOUT_MULTIPLICITY = eINSTANCE.getTableWithoutMultiplicity();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_WITHOUT_MULTIPLICITY__CONTENT = eINSTANCE.getTableWithoutMultiplicity_Content();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithUniqueImpl <em>Table With Unique</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithUniqueImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithUnique()
		 * @generated
		 */
		EClass TABLE_WITH_UNIQUE = eINSTANCE.getTableWithUnique();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_WITH_UNIQUE__CONTENT = eINSTANCE.getTableWithUnique_Content();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChild2Impl <em>Table Content
		 * With Inner Child2</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChild2Impl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithInnerChild2()
		 * @generated
		 */
		EClass TABLE_CONTENT_WITH_INNER_CHILD2 = eINSTANCE.getTableContentWithInnerChild2();

		/**
		 * The meta object literal for the '<em><b>Inner Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CONTENT_WITH_INNER_CHILD2__INNER_CHILD = eINSTANCE.getTableContentWithInnerChild2_InnerChild();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChildImpl <em>Table Content
		 * With Inner Child</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChildImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableContentWithInnerChild()
		 * @generated
		 */
		EClass TABLE_CONTENT_WITH_INNER_CHILD = eINSTANCE.getTableContentWithInnerChild();

		/**
		 * The meta object literal for the '<em><b>Inner Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CONTENT_WITH_INNER_CHILD__INNER_CHILD = eINSTANCE.getTableContentWithInnerChild_InnerChild();

		/**
		 * The meta object literal for the '<em><b>Stuff</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTENT_WITH_INNER_CHILD__STUFF = eINSTANCE.getTableContentWithInnerChild_Stuff();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityConcreteImpl <em>Table
		 * Without Multiplicity Concrete</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityConcreteImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithoutMultiplicityConcrete()
		 * @generated
		 */
		EClass TABLE_WITHOUT_MULTIPLICITY_CONCRETE = eINSTANCE.getTableWithoutMultiplicityConcrete();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_WITHOUT_MULTIPLICITY_CONCRETE__CONTENT = eINSTANCE
			.getTableWithoutMultiplicityConcrete_Content();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.ReferencerImpl
		 * <em>Referencer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.ReferencerImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getReferencer()
		 * @generated
		 */
		EClass REFERENCER = eINSTANCE.getReferencer();

		/**
		 * The meta object literal for the '<em><b>Referenced Content</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REFERENCER__REFERENCED_CONTENT = eINSTANCE.getReferencer_ReferencedContent();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContainerImpl <em>Cross Reference
		 * Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContainerImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getCrossReferenceContainer()
		 * @generated
		 */
		EClass CROSS_REFERENCE_CONTAINER = eINSTANCE.getCrossReferenceContainer();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CROSS_REFERENCE_CONTAINER__CONTENTS = eINSTANCE.getCrossReferenceContainer_Contents();

		/**
		 * The meta object literal for the '<em><b>Single Content</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CROSS_REFERENCE_CONTAINER__SINGLE_CONTENT = eINSTANCE.getCrossReferenceContainer_SingleContent();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContentImpl <em>Cross Reference
		 * Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.CrossReferenceContentImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getCrossReferenceContent()
		 * @generated
		 */
		EClass CROSS_REFERENCE_CONTENT = eINSTANCE.getCrossReferenceContent();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CROSS_REFERENCE_CONTENT__PARENT = eINSTANCE.getCrossReferenceContent_Parent();

		/**
		 * The meta object literal for the '<em><b>Single Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CROSS_REFERENCE_CONTENT__SINGLE_PARENT = eINSTANCE.getCrossReferenceContent_SingleParent();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl
		 * <em>Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.PersonImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getPerson()
		 * @generated
		 */
		EClass PERSON = eINSTANCE.getPerson();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PERSON__FIRST_NAME = eINSTANCE.getPerson_FirstName();

		/**
		 * The meta object literal for the '<em><b>Gender</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PERSON__GENDER = eINSTANCE.getPerson_Gender();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PERSON__LAST_NAME = eINSTANCE.getPerson_LastName();

		/**
		 * The meta object literal for the '<em><b>Custom</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PERSON__CUSTOM = eINSTANCE.getPerson_Custom();

		/**
		 * The meta object literal for the '<em><b>Age</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PERSON__AGE = eINSTANCE.getPerson_Age();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableObjectImpl
		 * <em>Table Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableObjectImpl
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableObject()
		 * @generated
		 */
		EClass TABLE_OBJECT = eINSTANCE.getTableObject();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute TABLE_OBJECT__LABEL = eINSTANCE.getTableObject_Label();

		/**
		 * The meta object literal for the '<em>String With Max Length8</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getStringWithMaxLength8()
		 * @generated
		 */
		EDataType STRING_WITH_MAX_LENGTH8 = eINSTANCE.getStringWithMaxLength8();

		/**
		 * The meta object literal for the '<em>Only Capitals</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getOnlyCapitals()
		 * @generated
		 */
		EDataType ONLY_CAPITALS = eINSTANCE.getOnlyCapitals();

		/**
		 * The meta object literal for the '<em>Custom Data Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getCustomDataType()
		 * @generated
		 */
		EDataType CUSTOM_DATA_TYPE = eINSTANCE.getCustomDataType();

		/**
		 * The meta object literal for the '<em>Phone Number</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getPhoneNumber()
		 * @generated
		 */
		EDataType PHONE_NUMBER = eINSTANCE.getPhoneNumber();

		/**
		 * The meta object literal for the '<em>Min Length Of3</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getMinLengthOf3()
		 * @generated
		 */
		EDataType MIN_LENGTH_OF3 = eINSTANCE.getMinLengthOf3();

		/**
		 * The meta object literal for the '<em>Strict Min Length Of3</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getStrictMinLengthOf3()
		 * @generated
		 */
		EDataType STRICT_MIN_LENGTH_OF3 = eINSTANCE.getStrictMinLengthOf3();

		/**
		 * The meta object literal for the '<em>Age</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.Integer
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getAge()
		 * @generated
		 */
		EDataType AGE = eINSTANCE.getAge();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.Gender
		 * <em>Gender</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.Gender
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getGender()
		 * @generated
		 */
		EEnum GENDER = eINSTANCE.getGender();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.validation.test.model.Color <em>Color</em>}'
		 * enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.validation.test.model.Color
		 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getColor()
		 * @generated
		 */
		EEnum COLOR = eINSTANCE.getColor();

	}

} // TestPackage
