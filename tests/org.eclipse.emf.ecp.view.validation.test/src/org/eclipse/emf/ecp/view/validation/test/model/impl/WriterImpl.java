/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
 *******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test.model.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.validation.test.model.Book;
import org.eclipse.emf.ecp.view.validation.test.model.Library;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;
import org.eclipse.emf.ecp.view.validation.test.model.Writer;
import org.eclipse.emf.ecp.view.validation.test.model.util.TestValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Writer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getFirstName <em>First Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getLastName <em>Last Name</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getEMail <em>EMail</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getBirthDate <em>Birth Date</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getBooks <em>Books</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#isPseudonym <em>Pseudonym</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getLibrary <em>Library</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getInitials <em>Initials</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.validation.test.model.impl.WriterImpl#getTitle <em>Title</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WriterImpl extends EObjectImpl implements Writer {
	/**
	 * The default value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected static final String FIRST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected String firstName = FIRST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected String lastName = LAST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEMail() <em>EMail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEMail()
	 * @generated
	 * @ordered
	 */
	protected static final String EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEMail() <em>EMail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEMail()
	 * @generated
	 * @ordered
	 */
	protected String eMail = EMAIL_EDEFAULT;

	/**
	 * The default value of the '{@link #getBirthDate() <em>Birth Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBirthDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date BIRTH_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBirthDate() <em>Birth Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBirthDate()
	 * @generated
	 * @ordered
	 */
	protected Date birthDate = BIRTH_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBooks() <em>Books</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBooks()
	 * @generated
	 * @ordered
	 */
	protected EList<Book> books;

	/**
	 * The default value of the '{@link #isPseudonym() <em>Pseudonym</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isPseudonym()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PSEUDONYM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPseudonym() <em>Pseudonym</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isPseudonym()
	 * @generated
	 * @ordered
	 */
	protected boolean pseudonym = PSEUDONYM_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitials() <em>Initials</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInitials()
	 * @generated
	 * @ordered
	 */
	protected static final String INITIALS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitials() <em>Initials</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInitials()
	 * @generated
	 * @ordered
	 */
	protected String initials = INITIALS_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WriterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestPackage.Literals.WRITER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFirstName(String newFirstName) {
		final String oldFirstName = firstName;
		firstName = newFirstName;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__FIRST_NAME, oldFirstName, firstName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setLastName(String newLastName) {
		final String oldLastName = lastName;
		lastName = newLastName;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__LAST_NAME, oldLastName, lastName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getEMail() {
		return eMail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEMail(String newEMail) {
		final String oldEMail = eMail;
		eMail = newEMail;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__EMAIL, oldEMail, eMail));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBirthDate(Date newBirthDate) {
		final Date oldBirthDate = birthDate;
		birthDate = newBirthDate;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__BIRTH_DATE, oldBirthDate, birthDate));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Book> getBooks() {
		if (books == null) {
			books = new EObjectWithInverseResolvingEList<Book>(Book.class, this, TestPackage.WRITER__BOOKS,
				TestPackage.BOOK__WRITERS);
		}
		return books;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isPseudonym() {
		return pseudonym;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setPseudonym(boolean newPseudonym) {
		final boolean oldPseudonym = pseudonym;
		pseudonym = newPseudonym;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__PSEUDONYM, oldPseudonym, pseudonym));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Library getLibrary() {
		if (eContainerFeatureID() != TestPackage.WRITER__LIBRARY) {
			return null;
		}
		return (Library) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetLibrary(Library newLibrary, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newLibrary, TestPackage.WRITER__LIBRARY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setLibrary(Library newLibrary) {
		if (newLibrary != eInternalContainer()
			|| eContainerFeatureID() != TestPackage.WRITER__LIBRARY && newLibrary != null) {
			if (EcoreUtil.isAncestor(this, newLibrary)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newLibrary != null) {
				msgs = ((InternalEObject) newLibrary).eInverseAdd(this, TestPackage.LIBRARY__WRITERS, Library.class,
					msgs);
			}
			msgs = basicSetLibrary(newLibrary, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__LIBRARY, newLibrary, newLibrary));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getInitials() {
		return initials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInitials(String newInitials) {
		final String oldInitials = initials;
		initials = newInitials;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__INITIALS, oldInitials, initials));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setTitle(String newTitle) {
		final String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.WRITER__TITLE, oldTitle, title));
		}
	}

	/**
	 * Validates the writer. Only adds one diagnostic item to the chain.
	 *
	 * @generated NOT
	 */
	// BEGIN COMPLEX CODE
	@Override
	public boolean validate(DiagnosticChain chain, Map<Object, Object> context) {
		boolean valid = true;
		// cancel if name is offensive
		if (getFirstName() != null && getFirstName().equals("Offensive")
		// || getLastName() != null && getLastName().equals("Offensive")
		) {
			if (chain != null) {
				chain.add(new BasicDiagnostic(Diagnostic.CANCEL,
					TestValidator.DIAGNOSTIC_SOURCE,
					TestValidator.WRITER__VALIDATE,
					"Name is too offensive to even validate.",
					new Object[] { this, TestPackage.eINSTANCE.getWriter_FirstName()
					// ,TestPackage.eINSTANCE.getWriter_LastName()
					}));
			}
			valid = false;
		}

		// error when no first or last name
		else if (getFirstName() == null || getFirstName().equals("")
		// || getLastName() == null || getLastName().equals("")
		) {
			if (chain != null) {
				chain.add(new BasicDiagnostic(Diagnostic.ERROR,
					TestValidator.DIAGNOSTIC_SOURCE,
					TestValidator.WRITER__VALIDATE,
					"Writers need to have a first name",
					new Object[] { this, TestPackage.eINSTANCE.getWriter_FirstName()
					// , TestPackage.eINSTANCE.getWriter_LastName()
					}));
			}
			valid = false;
		}

		// warning when firstname equals lastname
		// else if (getFirstName() != null && getLastName() != null && getLastName().equals(getFirstName())) {
		else if (getFirstName() != null && getFirstName().equals("H")) {
			if (chain != null) {
				chain.add(new BasicDiagnostic(Diagnostic.WARNING,
					TestValidator.DIAGNOSTIC_SOURCE,
					TestValidator.WRITER__VALIDATE,
					"First name is the same as last name",
					new Object[] { this, TestPackage.eINSTANCE.getWriter_FirstName()
					// , TestPackage.eINSTANCE.getWriter_LastName()
					}));
			}
			valid = false;
		}

		// info when first or last name is very short
		// else if (getFirstName().length() == 1 || getLastName().length() == 1) {
		else if (getFirstName().equals("Ha")) {
			if (chain != null) {
				chain.add(new BasicDiagnostic(Diagnostic.INFO,
					TestValidator.DIAGNOSTIC_SOURCE,
					TestValidator.WRITER__VALIDATE,
					"First or last name is very short. Is this correct?",
					new Object[] { this, TestPackage.eINSTANCE.getWriter_FirstName()
					// , TestPackage.eINSTANCE.getWriter_LastName()
					}));
			}
			valid = false;
		}

		if (getLastName() != null && getLastName().equals("foo")) {
			if (chain != null) {
				chain.add(new BasicDiagnostic(Diagnostic.ERROR,
					TestValidator.DIAGNOSTIC_SOURCE,
					TestValidator.WRITER__VALIDATE,
					"Last name must not be 'foo'.",
					new Object[] { this, TestPackage.eINSTANCE.getWriter_LastName()
					}));
			}
			valid = false;
		}

		return valid;
	}

	// END COMPLEX CODE

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TestPackage.WRITER__BOOKS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getBooks()).basicAdd(otherEnd, msgs);
		case TestPackage.WRITER__LIBRARY:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetLibrary((Library) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TestPackage.WRITER__BOOKS:
			return ((InternalEList<?>) getBooks()).basicRemove(otherEnd, msgs);
		case TestPackage.WRITER__LIBRARY:
			return basicSetLibrary(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case TestPackage.WRITER__LIBRARY:
			return eInternalContainer().eInverseRemove(this, TestPackage.LIBRARY__WRITERS, Library.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TestPackage.WRITER__FIRST_NAME:
			return getFirstName();
		case TestPackage.WRITER__LAST_NAME:
			return getLastName();
		case TestPackage.WRITER__EMAIL:
			return getEMail();
		case TestPackage.WRITER__BIRTH_DATE:
			return getBirthDate();
		case TestPackage.WRITER__BOOKS:
			return getBooks();
		case TestPackage.WRITER__PSEUDONYM:
			return isPseudonym();
		case TestPackage.WRITER__LIBRARY:
			return getLibrary();
		case TestPackage.WRITER__INITIALS:
			return getInitials();
		case TestPackage.WRITER__TITLE:
			return getTitle();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case TestPackage.WRITER__FIRST_NAME:
			setFirstName((String) newValue);
			return;
		case TestPackage.WRITER__LAST_NAME:
			setLastName((String) newValue);
			return;
		case TestPackage.WRITER__EMAIL:
			setEMail((String) newValue);
			return;
		case TestPackage.WRITER__BIRTH_DATE:
			setBirthDate((Date) newValue);
			return;
		case TestPackage.WRITER__BOOKS:
			getBooks().clear();
			getBooks().addAll((Collection<? extends Book>) newValue);
			return;
		case TestPackage.WRITER__PSEUDONYM:
			setPseudonym((Boolean) newValue);
			return;
		case TestPackage.WRITER__LIBRARY:
			setLibrary((Library) newValue);
			return;
		case TestPackage.WRITER__INITIALS:
			setInitials((String) newValue);
			return;
		case TestPackage.WRITER__TITLE:
			setTitle((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case TestPackage.WRITER__FIRST_NAME:
			setFirstName(FIRST_NAME_EDEFAULT);
			return;
		case TestPackage.WRITER__LAST_NAME:
			setLastName(LAST_NAME_EDEFAULT);
			return;
		case TestPackage.WRITER__EMAIL:
			setEMail(EMAIL_EDEFAULT);
			return;
		case TestPackage.WRITER__BIRTH_DATE:
			setBirthDate(BIRTH_DATE_EDEFAULT);
			return;
		case TestPackage.WRITER__BOOKS:
			getBooks().clear();
			return;
		case TestPackage.WRITER__PSEUDONYM:
			setPseudonym(PSEUDONYM_EDEFAULT);
			return;
		case TestPackage.WRITER__LIBRARY:
			setLibrary((Library) null);
			return;
		case TestPackage.WRITER__INITIALS:
			setInitials(INITIALS_EDEFAULT);
			return;
		case TestPackage.WRITER__TITLE:
			setTitle(TITLE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case TestPackage.WRITER__FIRST_NAME:
			return FIRST_NAME_EDEFAULT == null ? firstName != null : !FIRST_NAME_EDEFAULT.equals(firstName);
		case TestPackage.WRITER__LAST_NAME:
			return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
		case TestPackage.WRITER__EMAIL:
			return EMAIL_EDEFAULT == null ? eMail != null : !EMAIL_EDEFAULT.equals(eMail);
		case TestPackage.WRITER__BIRTH_DATE:
			return BIRTH_DATE_EDEFAULT == null ? birthDate != null : !BIRTH_DATE_EDEFAULT.equals(birthDate);
		case TestPackage.WRITER__BOOKS:
			return books != null && !books.isEmpty();
		case TestPackage.WRITER__PSEUDONYM:
			return pseudonym != PSEUDONYM_EDEFAULT;
		case TestPackage.WRITER__LIBRARY:
			return getLibrary() != null;
		case TestPackage.WRITER__INITIALS:
			return INITIALS_EDEFAULT == null ? initials != null : !INITIALS_EDEFAULT.equals(initials);
		case TestPackage.WRITER__TITLE:
			return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (firstName: ");
		result.append(firstName);
		result.append(", lastName: ");
		result.append(lastName);
		result.append(", EMail: ");
		result.append(eMail);
		result.append(", BirthDate: ");
		result.append(birthDate);
		result.append(", Pseudonym: ");
		result.append(pseudonym);
		result.append(", initials: ");
		result.append(initials);
		result.append(", title: ");
		result.append(title);
		result.append(')');
		return result.toString();
	}

} // WriterImpl
