/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.common.test.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.common.test.model.Base;
import org.eclipse.emf.ecp.common.test.model.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getSingleAttribute <em>Single Attribute</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getSingleAttributeUnsettable
 * <em>Single Attribute Unsettable</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getMultiAttribute <em>Multi Attribute</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getMultiAttributeUnsettable
 * <em>Multi Attribute Unsettable</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getChild <em>Child</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getChildUnsettable <em>Child Unsettable</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getChildren <em>Children</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.common.test.model.impl.BaseImpl#getChildrenUnsettable <em>Children Unsettable</em>}
 * </li>
 * </ul>
 *
 * @generated
 */
public class BaseImpl extends MinimalEObjectImpl.Container implements Base {
	/**
	 * The default value of the '{@link #getSingleAttribute() <em>Single Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSingleAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String SINGLE_ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSingleAttribute() <em>Single Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSingleAttribute()
	 * @generated
	 * @ordered
	 */
	protected String singleAttribute = SINGLE_ATTRIBUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSingleAttributeUnsettable() <em>Single Attribute Unsettable</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSingleAttributeUnsettable()
	 * @generated
	 * @ordered
	 */
	protected static final String SINGLE_ATTRIBUTE_UNSETTABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSingleAttributeUnsettable() <em>Single Attribute Unsettable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSingleAttributeUnsettable()
	 * @generated
	 * @ordered
	 */
	protected String singleAttributeUnsettable = SINGLE_ATTRIBUTE_UNSETTABLE_EDEFAULT;

	/**
	 * This is true if the Single Attribute Unsettable attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean singleAttributeUnsettableESet;

	/**
	 * The cached value of the '{@link #getMultiAttribute() <em>Multi Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMultiAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<String> multiAttribute;

	/**
	 * The cached value of the '{@link #getMultiAttributeUnsettable() <em>Multi Attribute Unsettable</em>}' attribute
	 * list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMultiAttributeUnsettable()
	 * @generated
	 * @ordered
	 */
	protected EList<String> multiAttributeUnsettable;

	/**
	 * The cached value of the '{@link #getChild() <em>Child</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChild()
	 * @generated
	 * @ordered
	 */
	protected Base child;

	/**
	 * The cached value of the '{@link #getChildUnsettable() <em>Child Unsettable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChildUnsettable()
	 * @generated
	 * @ordered
	 */
	protected Base childUnsettable;

	/**
	 * This is true if the Child Unsettable containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean childUnsettableESet;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Base> children;

	/**
	 * The cached value of the '{@link #getChildrenUnsettable() <em>Children Unsettable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChildrenUnsettable()
	 * @generated
	 * @ordered
	 */
	protected Base childrenUnsettable;

	/**
	 * This is true if the Children Unsettable containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean childrenUnsettableESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected BaseImpl() {
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
		return TestPackage.Literals.BASE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getSingleAttribute() {
		return singleAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSingleAttribute(String newSingleAttribute) {
		final String oldSingleAttribute = singleAttribute;
		singleAttribute = newSingleAttribute;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.BASE__SINGLE_ATTRIBUTE,
				oldSingleAttribute, singleAttribute));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getSingleAttributeUnsettable() {
		return singleAttributeUnsettable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSingleAttributeUnsettable(String newSingleAttributeUnsettable) {
		final String oldSingleAttributeUnsettable = singleAttributeUnsettable;
		singleAttributeUnsettable = newSingleAttributeUnsettable;
		final boolean oldSingleAttributeUnsettableESet = singleAttributeUnsettableESet;
		singleAttributeUnsettableESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.BASE__SINGLE_ATTRIBUTE_UNSETTABLE,
				oldSingleAttributeUnsettable, singleAttributeUnsettable, !oldSingleAttributeUnsettableESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetSingleAttributeUnsettable() {
		final String oldSingleAttributeUnsettable = singleAttributeUnsettable;
		final boolean oldSingleAttributeUnsettableESet = singleAttributeUnsettableESet;
		singleAttributeUnsettable = SINGLE_ATTRIBUTE_UNSETTABLE_EDEFAULT;
		singleAttributeUnsettableESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, TestPackage.BASE__SINGLE_ATTRIBUTE_UNSETTABLE,
				oldSingleAttributeUnsettable, SINGLE_ATTRIBUTE_UNSETTABLE_EDEFAULT, oldSingleAttributeUnsettableESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetSingleAttributeUnsettable() {
		return singleAttributeUnsettableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<String> getMultiAttribute() {
		if (multiAttribute == null) {
			multiAttribute = new EDataTypeUniqueEList<String>(String.class, this, TestPackage.BASE__MULTI_ATTRIBUTE);
		}
		return multiAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<String> getMultiAttributeUnsettable() {
		if (multiAttributeUnsettable == null) {
			multiAttributeUnsettable = new EDataTypeUniqueEList.Unsettable<String>(String.class, this,
				TestPackage.BASE__MULTI_ATTRIBUTE_UNSETTABLE);
		}
		return multiAttributeUnsettable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetMultiAttributeUnsettable() {
		if (multiAttributeUnsettable != null) {
			((InternalEList.Unsettable<?>) multiAttributeUnsettable).unset();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetMultiAttributeUnsettable() {
		return multiAttributeUnsettable != null && ((InternalEList.Unsettable<?>) multiAttributeUnsettable).isSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Base getChild() {
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetChild(Base newChild, NotificationChain msgs) {
		final Base oldChild = child;
		child = newChild;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				TestPackage.BASE__CHILD,
				oldChild, newChild);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setChild(Base newChild) {
		if (newChild != child) {
			NotificationChain msgs = null;
			if (child != null) {
				msgs = ((InternalEObject) child).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILD,
					null, msgs);
			}
			if (newChild != null) {
				msgs = ((InternalEObject) newChild).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILD,
					null, msgs);
			}
			msgs = basicSetChild(newChild, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.BASE__CHILD, newChild, newChild));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Base getChildUnsettable() {
		return childUnsettable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetChildUnsettable(Base newChildUnsettable, NotificationChain msgs) {
		final Base oldChildUnsettable = childUnsettable;
		childUnsettable = newChildUnsettable;
		final boolean oldChildUnsettableESet = childUnsettableESet;
		childUnsettableESet = true;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				TestPackage.BASE__CHILD_UNSETTABLE, oldChildUnsettable, newChildUnsettable, !oldChildUnsettableESet);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setChildUnsettable(Base newChildUnsettable) {
		if (newChildUnsettable != childUnsettable) {
			NotificationChain msgs = null;
			if (childUnsettable != null) {
				msgs = ((InternalEObject) childUnsettable).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILD_UNSETTABLE, null, msgs);
			}
			if (newChildUnsettable != null) {
				msgs = ((InternalEObject) newChildUnsettable).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILD_UNSETTABLE, null, msgs);
			}
			msgs = basicSetChildUnsettable(newChildUnsettable, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else {
			final boolean oldChildUnsettableESet = childUnsettableESet;
			childUnsettableESet = true;
			if (eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.BASE__CHILD_UNSETTABLE,
					newChildUnsettable, newChildUnsettable, !oldChildUnsettableESet));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicUnsetChildUnsettable(NotificationChain msgs) {
		final Base oldChildUnsettable = childUnsettable;
		childUnsettable = null;
		final boolean oldChildUnsettableESet = childUnsettableESet;
		childUnsettableESet = false;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
				TestPackage.BASE__CHILD_UNSETTABLE, oldChildUnsettable, null, oldChildUnsettableESet);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetChildUnsettable() {
		if (childUnsettable != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject) childUnsettable).eInverseRemove(this,
				EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILD_UNSETTABLE, null, msgs);
			msgs = basicUnsetChildUnsettable(msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else {
			final boolean oldChildUnsettableESet = childUnsettableESet;
			childUnsettableESet = false;
			if (eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.UNSET, TestPackage.BASE__CHILD_UNSETTABLE, null, null,
					oldChildUnsettableESet));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetChildUnsettable() {
		return childUnsettableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Base> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<Base>(Base.class, this, TestPackage.BASE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Base getChildrenUnsettable() {
		return childrenUnsettable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetChildrenUnsettable(Base newChildrenUnsettable, NotificationChain msgs) {
		final Base oldChildrenUnsettable = childrenUnsettable;
		childrenUnsettable = newChildrenUnsettable;
		final boolean oldChildrenUnsettableESet = childrenUnsettableESet;
		childrenUnsettableESet = true;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				TestPackage.BASE__CHILDREN_UNSETTABLE, oldChildrenUnsettable, newChildrenUnsettable,
				!oldChildrenUnsettableESet);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setChildrenUnsettable(Base newChildrenUnsettable) {
		if (newChildrenUnsettable != childrenUnsettable) {
			NotificationChain msgs = null;
			if (childrenUnsettable != null) {
				msgs = ((InternalEObject) childrenUnsettable).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILDREN_UNSETTABLE, null, msgs);
			}
			if (newChildrenUnsettable != null) {
				msgs = ((InternalEObject) newChildrenUnsettable).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILDREN_UNSETTABLE, null, msgs);
			}
			msgs = basicSetChildrenUnsettable(newChildrenUnsettable, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else {
			final boolean oldChildrenUnsettableESet = childrenUnsettableESet;
			childrenUnsettableESet = true;
			if (eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.BASE__CHILDREN_UNSETTABLE,
					newChildrenUnsettable, newChildrenUnsettable, !oldChildrenUnsettableESet));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicUnsetChildrenUnsettable(NotificationChain msgs) {
		final Base oldChildrenUnsettable = childrenUnsettable;
		childrenUnsettable = null;
		final boolean oldChildrenUnsettableESet = childrenUnsettableESet;
		childrenUnsettableESet = false;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET,
				TestPackage.BASE__CHILDREN_UNSETTABLE, oldChildrenUnsettable, null, oldChildrenUnsettableESet);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetChildrenUnsettable() {
		if (childrenUnsettable != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject) childrenUnsettable).eInverseRemove(this,
				EOPPOSITE_FEATURE_BASE - TestPackage.BASE__CHILDREN_UNSETTABLE, null, msgs);
			msgs = basicUnsetChildrenUnsettable(msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else {
			final boolean oldChildrenUnsettableESet = childrenUnsettableESet;
			childrenUnsettableESet = false;
			if (eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.UNSET, TestPackage.BASE__CHILDREN_UNSETTABLE, null,
					null, oldChildrenUnsettableESet));
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetChildrenUnsettable() {
		return childrenUnsettableESet;
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
		case TestPackage.BASE__CHILD:
			return basicSetChild(null, msgs);
		case TestPackage.BASE__CHILD_UNSETTABLE:
			return basicUnsetChildUnsettable(msgs);
		case TestPackage.BASE__CHILDREN:
			return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
		case TestPackage.BASE__CHILDREN_UNSETTABLE:
			return basicUnsetChildrenUnsettable(msgs);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TestPackage.BASE__SINGLE_ATTRIBUTE:
			return getSingleAttribute();
		case TestPackage.BASE__SINGLE_ATTRIBUTE_UNSETTABLE:
			return getSingleAttributeUnsettable();
		case TestPackage.BASE__MULTI_ATTRIBUTE:
			return getMultiAttribute();
		case TestPackage.BASE__MULTI_ATTRIBUTE_UNSETTABLE:
			return getMultiAttributeUnsettable();
		case TestPackage.BASE__CHILD:
			return getChild();
		case TestPackage.BASE__CHILD_UNSETTABLE:
			return getChildUnsettable();
		case TestPackage.BASE__CHILDREN:
			return getChildren();
		case TestPackage.BASE__CHILDREN_UNSETTABLE:
			return getChildrenUnsettable();
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
		case TestPackage.BASE__SINGLE_ATTRIBUTE:
			setSingleAttribute((String) newValue);
			return;
		case TestPackage.BASE__SINGLE_ATTRIBUTE_UNSETTABLE:
			setSingleAttributeUnsettable((String) newValue);
			return;
		case TestPackage.BASE__MULTI_ATTRIBUTE:
			getMultiAttribute().clear();
			getMultiAttribute().addAll((Collection<? extends String>) newValue);
			return;
		case TestPackage.BASE__MULTI_ATTRIBUTE_UNSETTABLE:
			getMultiAttributeUnsettable().clear();
			getMultiAttributeUnsettable().addAll((Collection<? extends String>) newValue);
			return;
		case TestPackage.BASE__CHILD:
			setChild((Base) newValue);
			return;
		case TestPackage.BASE__CHILD_UNSETTABLE:
			setChildUnsettable((Base) newValue);
			return;
		case TestPackage.BASE__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends Base>) newValue);
			return;
		case TestPackage.BASE__CHILDREN_UNSETTABLE:
			setChildrenUnsettable((Base) newValue);
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
		case TestPackage.BASE__SINGLE_ATTRIBUTE:
			setSingleAttribute(SINGLE_ATTRIBUTE_EDEFAULT);
			return;
		case TestPackage.BASE__SINGLE_ATTRIBUTE_UNSETTABLE:
			unsetSingleAttributeUnsettable();
			return;
		case TestPackage.BASE__MULTI_ATTRIBUTE:
			getMultiAttribute().clear();
			return;
		case TestPackage.BASE__MULTI_ATTRIBUTE_UNSETTABLE:
			unsetMultiAttributeUnsettable();
			return;
		case TestPackage.BASE__CHILD:
			setChild((Base) null);
			return;
		case TestPackage.BASE__CHILD_UNSETTABLE:
			unsetChildUnsettable();
			return;
		case TestPackage.BASE__CHILDREN:
			getChildren().clear();
			return;
		case TestPackage.BASE__CHILDREN_UNSETTABLE:
			unsetChildrenUnsettable();
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
		case TestPackage.BASE__SINGLE_ATTRIBUTE:
			return SINGLE_ATTRIBUTE_EDEFAULT == null ? singleAttribute != null
				: !SINGLE_ATTRIBUTE_EDEFAULT.equals(singleAttribute);
		case TestPackage.BASE__SINGLE_ATTRIBUTE_UNSETTABLE:
			return isSetSingleAttributeUnsettable();
		case TestPackage.BASE__MULTI_ATTRIBUTE:
			return multiAttribute != null && !multiAttribute.isEmpty();
		case TestPackage.BASE__MULTI_ATTRIBUTE_UNSETTABLE:
			return isSetMultiAttributeUnsettable();
		case TestPackage.BASE__CHILD:
			return child != null;
		case TestPackage.BASE__CHILD_UNSETTABLE:
			return isSetChildUnsettable();
		case TestPackage.BASE__CHILDREN:
			return children != null && !children.isEmpty();
		case TestPackage.BASE__CHILDREN_UNSETTABLE:
			return isSetChildrenUnsettable();
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
		result.append(" (singleAttribute: "); //$NON-NLS-1$
		result.append(singleAttribute);
		result.append(", singleAttributeUnsettable: "); //$NON-NLS-1$
		if (singleAttributeUnsettableESet) {
			result.append(singleAttributeUnsettable);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(", multiAttribute: "); //$NON-NLS-1$
		result.append(multiAttribute);
		result.append(", multiAttributeUnsettable: "); //$NON-NLS-1$
		result.append(multiAttributeUnsettable);
		result.append(')');
		return result.toString();
	}

} // BaseImpl
