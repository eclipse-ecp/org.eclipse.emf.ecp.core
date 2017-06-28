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
package org.eclipse.emf.ecp.view.validation.test.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.validation.test.model.Book;
import org.eclipse.emf.ecp.view.validation.test.model.Color;
import org.eclipse.emf.ecp.view.validation.test.model.Computer;
import org.eclipse.emf.ecp.view.validation.test.model.Content;
import org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer;
import org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent;
import org.eclipse.emf.ecp.view.validation.test.model.Gender;
import org.eclipse.emf.ecp.view.validation.test.model.Librarian;
import org.eclipse.emf.ecp.view.validation.test.model.Library;
import org.eclipse.emf.ecp.view.validation.test.model.Mainboard;
import org.eclipse.emf.ecp.view.validation.test.model.Person;
import org.eclipse.emf.ecp.view.validation.test.model.PowerBlock;
import org.eclipse.emf.ecp.view.validation.test.model.Referencer;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild2;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithValidation;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithoutValidation;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithMultiplicity;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithUnique;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicity;
import org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete;
import org.eclipse.emf.ecp.view.validation.test.model.TestFactory;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;
import org.eclipse.emf.ecp.view.validation.test.model.Writer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TestFactoryImpl extends EFactoryImpl implements TestFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static TestFactory init() {
		try {
			final TestFactory theTestFactory = (TestFactory) EPackage.Registry.INSTANCE
				.getEFactory(TestPackage.eNS_URI);
			if (theTestFactory != null) {
				return theTestFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TestFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TestFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case TestPackage.LIBRARY:
			return createLibrary();
		case TestPackage.WRITER:
			return createWriter();
		case TestPackage.BOOK:
			return createBook();
		case TestPackage.LIBRARIAN:
			return createLibrarian();
		case TestPackage.COMPUTER:
			return createComputer();
		case TestPackage.MAINBOARD:
			return createMainboard();
		case TestPackage.POWER_BLOCK:
			return createPowerBlock();
		case TestPackage.CONTAINER:
			return createContainer();
		case TestPackage.CONTENT:
			return createContent();
		case TestPackage.TABLE_WITH_MULTIPLICITY:
			return createTableWithMultiplicity();
		case TestPackage.TABLE_CONTENT_WITHOUT_VALIDATION:
			return createTableContentWithoutValidation();
		case TestPackage.TABLE_CONTENT_WITH_VALIDATION:
			return createTableContentWithValidation();
		case TestPackage.TABLE_WITHOUT_MULTIPLICITY:
			return createTableWithoutMultiplicity();
		case TestPackage.TABLE_WITH_UNIQUE:
			return createTableWithUnique();
		case TestPackage.TABLE_CONTENT_WITH_INNER_CHILD2:
			return createTableContentWithInnerChild2();
		case TestPackage.TABLE_CONTENT_WITH_INNER_CHILD:
			return createTableContentWithInnerChild();
		case TestPackage.TABLE_WITHOUT_MULTIPLICITY_CONCRETE:
			return createTableWithoutMultiplicityConcrete();
		case TestPackage.REFERENCER:
			return createReferencer();
		case TestPackage.CROSS_REFERENCE_CONTAINER:
			return createCrossReferenceContainer();
		case TestPackage.CROSS_REFERENCE_CONTENT:
			return createCrossReferenceContent();
		case TestPackage.PERSON:
			return createPerson();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case TestPackage.GENDER:
			return createGenderFromString(eDataType, initialValue);
		case TestPackage.COLOR:
			return createColorFromString(eDataType, initialValue);
		case TestPackage.STRING_WITH_MAX_LENGTH8:
			return createStringWithMaxLength8FromString(eDataType, initialValue);
		case TestPackage.ONLY_CAPITALS:
			return createOnlyCapitalsFromString(eDataType, initialValue);
		case TestPackage.CUSTOM_DATA_TYPE:
			return createCustomDataTypeFromString(eDataType, initialValue);
		case TestPackage.PHONE_NUMBER:
			return createPhoneNumberFromString(eDataType, initialValue);
		case TestPackage.MIN_LENGTH_OF3:
			return createMinLengthOf3FromString(eDataType, initialValue);
		case TestPackage.STRICT_MIN_LENGTH_OF3:
			return createStrictMinLengthOf3FromString(eDataType, initialValue);
		case TestPackage.AGE:
			return createAgeFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case TestPackage.GENDER:
			return convertGenderToString(eDataType, instanceValue);
		case TestPackage.COLOR:
			return convertColorToString(eDataType, instanceValue);
		case TestPackage.STRING_WITH_MAX_LENGTH8:
			return convertStringWithMaxLength8ToString(eDataType, instanceValue);
		case TestPackage.ONLY_CAPITALS:
			return convertOnlyCapitalsToString(eDataType, instanceValue);
		case TestPackage.CUSTOM_DATA_TYPE:
			return convertCustomDataTypeToString(eDataType, instanceValue);
		case TestPackage.PHONE_NUMBER:
			return convertPhoneNumberToString(eDataType, instanceValue);
		case TestPackage.MIN_LENGTH_OF3:
			return convertMinLengthOf3ToString(eDataType, instanceValue);
		case TestPackage.STRICT_MIN_LENGTH_OF3:
			return convertStrictMinLengthOf3ToString(eDataType, instanceValue);
		case TestPackage.AGE:
			return convertAgeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Library createLibrary() {
		final LibraryImpl library = new LibraryImpl();
		return library;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Writer createWriter() {
		final WriterImpl writer = new WriterImpl();
		return writer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Book createBook() {
		final BookImpl book = new BookImpl();
		return book;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Librarian createLibrarian() {
		final LibrarianImpl librarian = new LibrarianImpl();
		return librarian;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Computer createComputer() {
		final ComputerImpl computer = new ComputerImpl();
		return computer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Mainboard createMainboard() {
		final MainboardImpl mainboard = new MainboardImpl();
		return mainboard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public PowerBlock createPowerBlock() {
		final PowerBlockImpl powerBlock = new PowerBlockImpl();
		return powerBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public org.eclipse.emf.ecp.view.validation.test.model.Container createContainer() {
		final ContainerImpl container = new ContainerImpl();
		return container;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Content createContent() {
		final ContentImpl content = new ContentImpl();
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableWithMultiplicity createTableWithMultiplicity() {
		final TableWithMultiplicityImpl tableWithMultiplicity = new TableWithMultiplicityImpl();
		return tableWithMultiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableContentWithoutValidation createTableContentWithoutValidation() {
		final TableContentWithoutValidationImpl tableContentWithoutValidation = new TableContentWithoutValidationImpl();
		return tableContentWithoutValidation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableContentWithValidation createTableContentWithValidation() {
		final TableContentWithValidationImpl tableContentWithValidation = new TableContentWithValidationImpl();
		return tableContentWithValidation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableWithoutMultiplicity createTableWithoutMultiplicity() {
		final TableWithoutMultiplicityImpl tableWithoutMultiplicity = new TableWithoutMultiplicityImpl();
		return tableWithoutMultiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableWithUnique createTableWithUnique() {
		final TableWithUniqueImpl tableWithUnique = new TableWithUniqueImpl();
		return tableWithUnique;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableContentWithInnerChild2 createTableContentWithInnerChild2() {
		final TableContentWithInnerChild2Impl tableContentWithInnerChild2 = new TableContentWithInnerChild2Impl();
		return tableContentWithInnerChild2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableContentWithInnerChild createTableContentWithInnerChild() {
		final TableContentWithInnerChildImpl tableContentWithInnerChild = new TableContentWithInnerChildImpl();
		return tableContentWithInnerChild;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TableWithoutMultiplicityConcrete createTableWithoutMultiplicityConcrete() {
		final TableWithoutMultiplicityConcreteImpl tableWithoutMultiplicityConcrete = new TableWithoutMultiplicityConcreteImpl();
		return tableWithoutMultiplicityConcrete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Referencer createReferencer() {
		final ReferencerImpl referencer = new ReferencerImpl();
		return referencer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CrossReferenceContainer createCrossReferenceContainer() {
		final CrossReferenceContainerImpl crossReferenceContainer = new CrossReferenceContainerImpl();
		return crossReferenceContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CrossReferenceContent createCrossReferenceContent() {
		final CrossReferenceContentImpl crossReferenceContent = new CrossReferenceContentImpl();
		return crossReferenceContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Person createPerson() {
		final PersonImpl person = new PersonImpl();
		return person;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String createStringWithMaxLength8FromString(EDataType eDataType, String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertStringWithMaxLength8ToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String createOnlyCapitalsFromString(EDataType eDataType, String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertOnlyCapitalsToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String createCustomDataTypeFromString(EDataType eDataType, String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertCustomDataTypeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String createPhoneNumberFromString(EDataType eDataType, String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertPhoneNumberToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String createMinLengthOf3FromString(EDataType eDataType, String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertMinLengthOf3ToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String createStrictMinLengthOf3FromString(EDataType eDataType, String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertStrictMinLengthOf3ToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Integer createAgeFromString(EDataType eDataType, String initialValue) {
		return (Integer) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertAgeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Gender createGenderFromString(EDataType eDataType, String initialValue) {
		final Gender result = Gender.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertGenderToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Color createColorFromString(EDataType eDataType, String initialValue) {
		final Color result = Color.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertColorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TestPackage getTestPackage() {
		return (TestPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TestPackage getPackage() {
		return TestPackage.eINSTANCE;
	}

} // TestFactoryImpl
