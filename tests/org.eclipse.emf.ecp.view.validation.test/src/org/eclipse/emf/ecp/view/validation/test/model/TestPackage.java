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
package org.eclipse.emf.ecp.view.validation.test.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 4;

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
	 * The number of structural features of the '<em>Writer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int WRITER_FEATURE_COUNT = 7;

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
	 * The number of structural features of the '<em>Computer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPUTER_FEATURE_COUNT = 3;

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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.PowerBlockImpl
	 * <em>Power Block</em>}' class.
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
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_MULTIPLICITY__CONTENT = 0;

	/**
	 * The number of structural features of the '<em>Table With Multiplicity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_MULTIPLICITY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentImpl
	 * <em>Table Content</em>}' class.
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
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithoutValidationImpl
	 * <em>Table Content Without Validation</em>}' class.
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
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithValidationImpl
	 * <em>Table Content With Validation</em>}' class.
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
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityImpl
	 * <em>Table Without Multiplicity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithoutMultiplicity()
	 * @generated
	 */
	int TABLE_WITHOUT_MULTIPLICITY = 13;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY__CONTENT = 0;

	/**
	 * The number of structural features of the '<em>Table Without Multiplicity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY_FEATURE_COUNT = 1;

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
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_UNIQUE__CONTENT = 0;

	/**
	 * The number of structural features of the '<em>Table With Unique</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITH_UNIQUE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChild2Impl
	 * <em>Table Content With Inner Child2</em>}' class.
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
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChildImpl
	 * <em>Table Content With Inner Child</em>}' class.
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
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityConcreteImpl
	 * <em>Table Without Multiplicity Concrete</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityConcreteImpl
	 * @see org.eclipse.emf.ecp.view.validation.test.model.impl.TestPackageImpl#getTableWithoutMultiplicityConcrete()
	 * @generated
	 */
	int TABLE_WITHOUT_MULTIPLICITY_CONCRETE = 17;

	/**
	 * The feature id for the '<em><b>Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY_CONCRETE__CONTENT = 0;

	/**
	 * The number of structural features of the '<em>Table Without Multiplicity Concrete</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int TABLE_WITHOUT_MULTIPLICITY_CONCRETE_FEATURE_COUNT = 1;

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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Library#getWriters <em>Writers</em>}'.
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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Library#getBooks <em>Books</em>}'.
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
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Library#getLibrarian <em>Librarian</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.Writer <em>Writer</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Writer</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.Writer
	 * @generated
	 */
	EClass getWriter();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getFirstName <em>First Name</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getLastName <em>Last Name</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getBirthDate <em>Birth Date</em>}'.
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
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getBooks <em>Books</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Writer#isPseudonym <em>Pseudonym</em>}'.
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
	 * Returns the meta object for the container reference '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Writer#getLibrary <em>Library</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Librarian#getName <em>Name</em>}'.
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
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Computer#getMainboard <em>Mainboard</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Computer#getName <em>Name</em>}'.
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
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Computer#getPowerBlock <em>Power Block</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Mainboard#getName <em>Name</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.PowerBlock
	 * <em>Power Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Power Block</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.PowerBlock
	 * @generated
	 */
	EClass getPowerBlock();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.PowerBlock#getName <em>Name</em>}'.
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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Container#getContents <em>Contents</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Content#getUniqueAttribute <em>Unique Attribute</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.Content#getSecondAttribute <em>Second Attribute</em>}'.
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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableWithMultiplicity#getContent <em>Content</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.validation.test.model.TableContent
	 * <em>Table Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Table Content</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContent
	 * @generated
	 */
	EClass getTableContent();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation
	 * <em>Table Content Without Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Table Content Without Validation</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation
	 * @generated
	 */
	EClass getTableContentWithoutValidation();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation#getName <em>Name</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation#getWeight <em>Weight</em>}'.
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
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation
	 * <em>Table Content With Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Table Content With Validation</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation
	 * @generated
	 */
	EClass getTableContentWithValidation();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation#getName <em>Name</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation#getWeight <em>Weight</em>}'.
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
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity
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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity#getContent <em>Content</em>}'.
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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableWithUnique#getContent <em>Content</em>}'.
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
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2
	 * <em>Table Content With Inner Child2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Table Content With Inner Child2</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2
	 * @generated
	 */
	EClass getTableContentWithInnerChild2();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2#getInnerChild
	 * <em>Inner Child</em>}'.
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
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild
	 * <em>Table Content With Inner Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Table Content With Inner Child</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild
	 * @generated
	 */
	EClass getTableContentWithInnerChild();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild#getInnerChild
	 * <em>Inner Child</em>}'.
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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild#getStuff <em>Stuff</em>}'.
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
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete
	 * <em>Table Without Multiplicity Concrete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Table Without Multiplicity Concrete</em>'.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete
	 * @generated
	 */
	EClass getTableWithoutMultiplicityConcrete();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete#getContent
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithMultiplicityImpl
		 * <em>Table With Multiplicity</em>}' class.
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithoutValidationImpl
		 * <em>Table Content Without Validation</em>}' class.
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithValidationImpl
		 * <em>Table Content With Validation</em>}' class.
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityImpl
		 * <em>Table Without Multiplicity</em>}' class.
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithUniqueImpl <em>Table With Unique</em>}'
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChild2Impl
		 * <em>Table Content With Inner Child2</em>}' class.
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableContentWithInnerChildImpl
		 * <em>Table Content With Inner Child</em>}' class.
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
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.validation.test.model.impl.TableWithoutMultiplicityConcreteImpl
		 * <em>Table Without Multiplicity Concrete</em>}' class.
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

	}

} // TestPackage
