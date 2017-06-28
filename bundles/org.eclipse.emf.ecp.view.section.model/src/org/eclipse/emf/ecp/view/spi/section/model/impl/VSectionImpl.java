/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.section.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.VHasTooltip;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.impl.VContainerImpl;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Section</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionImpl#getTooltip <em>Tooltip</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionImpl#getChildItems <em>Child Items</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionImpl#isCollapsed <em>Collapsed</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VSectionImpl extends VContainerImpl implements VSection {
	/**
	 * The default value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 * @since 1.13
	 */
	protected static final String TOOLTIP_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 * @since 1.13
	 */
	protected String tooltip = TOOLTIP_EDEFAULT;
	/**
	 * The cached value of the '{@link #getChildItems() <em>Child Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChildItems()
	 * @generated
	 * @ordered
	 */
	protected EList<VSection> childItems;
	/**
	 * The default value of the '{@link #isCollapsed() <em>Collapsed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isCollapsed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COLLAPSED_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isCollapsed() <em>Collapsed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isCollapsed()
	 * @generated
	 * @ordered
	 */
	protected boolean collapsed = COLLAPSED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VSectionImpl() {
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
		return VSectionPackage.Literals.SECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.13
	 */
	@Override
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.13
	 */
	@Override
	public void setTooltip(String newTooltip) {
		final String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, VSectionPackage.SECTION__TOOLTIP, oldTooltip, tooltip));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VSection> getChildItems() {
		if (childItems == null) {
			childItems = new EObjectContainmentEList<VSection>(VSection.class, this,
				VSectionPackage.SECTION__CHILD_ITEMS);
		}
		return childItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isCollapsed() {
		return collapsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCollapsed(boolean newCollapsed) {
		final boolean oldCollapsed = collapsed;
		collapsed = newCollapsed;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VSectionPackage.SECTION__COLLAPSED, oldCollapsed,
				collapsed));
		}
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
		case VSectionPackage.SECTION__CHILD_ITEMS:
			return ((InternalEList<?>) getChildItems()).basicRemove(otherEnd, msgs);
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
		case VSectionPackage.SECTION__TOOLTIP:
			return getTooltip();
		case VSectionPackage.SECTION__CHILD_ITEMS:
			return getChildItems();
		case VSectionPackage.SECTION__COLLAPSED:
			return isCollapsed();
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
		case VSectionPackage.SECTION__TOOLTIP:
			setTooltip((String) newValue);
			return;
		case VSectionPackage.SECTION__CHILD_ITEMS:
			getChildItems().clear();
			getChildItems().addAll((Collection<? extends VSection>) newValue);
			return;
		case VSectionPackage.SECTION__COLLAPSED:
			setCollapsed((Boolean) newValue);
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
		case VSectionPackage.SECTION__TOOLTIP:
			setTooltip(TOOLTIP_EDEFAULT);
			return;
		case VSectionPackage.SECTION__CHILD_ITEMS:
			getChildItems().clear();
			return;
		case VSectionPackage.SECTION__COLLAPSED:
			setCollapsed(COLLAPSED_EDEFAULT);
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
		case VSectionPackage.SECTION__TOOLTIP:
			return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
		case VSectionPackage.SECTION__CHILD_ITEMS:
			return childItems != null && !childItems.isEmpty();
		case VSectionPackage.SECTION__COLLAPSED:
			return collapsed != COLLAPSED_EDEFAULT;
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == VHasTooltip.class) {
			switch (derivedFeatureID) {
			case VSectionPackage.SECTION__TOOLTIP:
				return VViewPackage.HAS_TOOLTIP__TOOLTIP;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == VHasTooltip.class) {
			switch (baseFeatureID) {
			case VViewPackage.HAS_TOOLTIP__TOOLTIP:
				return VSectionPackage.SECTION__TOOLTIP;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (tooltip: "); //$NON-NLS-1$
		result.append(tooltip);
		result.append(", collapsed: "); //$NON-NLS-1$
		result.append(collapsed);
		result.append(')');
		return result.toString();
	}

} // VSectionImpl
