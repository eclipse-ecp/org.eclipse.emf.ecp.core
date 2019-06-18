/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Table Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#getMinimumHeight
 * <em>Minimum Height</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#getMaximumHeight
 * <em>Maximum Height</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#isShowValidationSummaryTooltip
 * <em>Show Validation Summary Tooltip</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#isEnableSorting
 * <em>Enable Sorting</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#getVisibleLines
 * <em>Visible Lines</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl#getRenderMode
 * <em>Render Mode</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VTTableStylePropertyImpl extends MinimalEObjectImpl.Container implements VTTableStyleProperty {
	/**
	 * The default value of the '{@link #getMinimumHeight() <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMinimumHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int MINIMUM_HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinimumHeight() <em>Minimum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMinimumHeight()
	 * @generated
	 * @ordered
	 */
	protected int minimumHeight = MINIMUM_HEIGHT_EDEFAULT;

	/**
	 * This is true if the Minimum Height attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean minimumHeightESet;

	/**
	 * The default value of the '{@link #getMaximumHeight() <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMaximumHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int MAXIMUM_HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaximumHeight() <em>Maximum Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMaximumHeight()
	 * @generated
	 * @ordered
	 */
	protected int maximumHeight = MAXIMUM_HEIGHT_EDEFAULT;

	/**
	 * This is true if the Maximum Height attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean maximumHeightESet;

	/**
	 * The default value of the '{@link #isShowValidationSummaryTooltip() <em>Show Validation Summary Tooltip</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isShowValidationSummaryTooltip()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_VALIDATION_SUMMARY_TOOLTIP_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowValidationSummaryTooltip() <em>Show Validation Summary Tooltip</em>}'
	 * attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isShowValidationSummaryTooltip()
	 * @generated
	 * @ordered
	 */
	protected boolean showValidationSummaryTooltip = SHOW_VALIDATION_SUMMARY_TOOLTIP_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnableSorting() <em>Enable Sorting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isEnableSorting()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_SORTING_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnableSorting() <em>Enable Sorting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isEnableSorting()
	 * @generated
	 * @ordered
	 */
	protected boolean enableSorting = ENABLE_SORTING_EDEFAULT;

	/**
	 * The default value of the '{@link #getVisibleLines() <em>Visible Lines</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @see #getVisibleLines()
	 * @generated
	 * @ordered
	 */
	protected static final int VISIBLE_LINES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getVisibleLines() <em>Visible Lines</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @see #getVisibleLines()
	 * @generated
	 * @ordered
	 */
	protected int visibleLines = VISIBLE_LINES_EDEFAULT;

	/**
	 * This is true if the Visible Lines attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean visibleLinesESet;

	/**
	 * The default value of the '{@link #getRenderMode() <em>Render Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRenderMode()
	 * @generated
	 * @ordered
	 */
	protected static final RenderMode RENDER_MODE_EDEFAULT = RenderMode.DEFAULT;

	/**
	 * The cached value of the '{@link #getRenderMode() <em>Render Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRenderMode()
	 * @generated
	 * @ordered
	 */
	protected RenderMode renderMode = RENDER_MODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTTableStylePropertyImpl() {
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
		return VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getMinimumHeight() {
		return minimumHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMinimumHeight(int newMinimumHeight) {
		final int oldMinimumHeight = minimumHeight;
		minimumHeight = newMinimumHeight;
		final boolean oldMinimumHeightESet = minimumHeightESet;
		minimumHeightESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT, oldMinimumHeight, minimumHeight,
				!oldMinimumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetMinimumHeight() {
		final int oldMinimumHeight = minimumHeight;
		final boolean oldMinimumHeightESet = minimumHeightESet;
		minimumHeight = MINIMUM_HEIGHT_EDEFAULT;
		minimumHeightESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT, oldMinimumHeight,
				MINIMUM_HEIGHT_EDEFAULT, oldMinimumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetMinimumHeight() {
		return minimumHeightESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getMaximumHeight() {
		return maximumHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMaximumHeight(int newMaximumHeight) {
		final int oldMaximumHeight = maximumHeight;
		maximumHeight = newMaximumHeight;
		final boolean oldMaximumHeightESet = maximumHeightESet;
		maximumHeightESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT, oldMaximumHeight, maximumHeight,
				!oldMaximumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetMaximumHeight() {
		final int oldMaximumHeight = maximumHeight;
		final boolean oldMaximumHeightESet = maximumHeightESet;
		maximumHeight = MAXIMUM_HEIGHT_EDEFAULT;
		maximumHeightESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT, oldMaximumHeight,
				MAXIMUM_HEIGHT_EDEFAULT, oldMaximumHeightESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetMaximumHeight() {
		return maximumHeightESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isShowValidationSummaryTooltip() {
		return showValidationSummaryTooltip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setShowValidationSummaryTooltip(boolean newShowValidationSummaryTooltip) {
		final boolean oldShowValidationSummaryTooltip = showValidationSummaryTooltip;
		showValidationSummaryTooltip = newShowValidationSummaryTooltip;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP,
				oldShowValidationSummaryTooltip, showValidationSummaryTooltip));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isEnableSorting() {
		return enableSorting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEnableSorting(boolean newEnableSorting) {
		final boolean oldEnableSorting = enableSorting;
		enableSorting = newEnableSorting;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__ENABLE_SORTING, oldEnableSorting, enableSorting));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getVisibleLines() {
		return visibleLines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setVisibleLines(int newVisibleLines) {
		final int oldVisibleLines = visibleLines;
		visibleLines = newVisibleLines;
		final boolean oldVisibleLinesESet = visibleLinesESet;
		visibleLinesESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__VISIBLE_LINES, oldVisibleLines, visibleLines,
				!oldVisibleLinesESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetVisibleLines() {
		final int oldVisibleLines = visibleLines;
		final boolean oldVisibleLinesESet = visibleLinesESet;
		visibleLines = VISIBLE_LINES_EDEFAULT;
		visibleLinesESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__VISIBLE_LINES, oldVisibleLines,
				VISIBLE_LINES_EDEFAULT, oldVisibleLinesESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetVisibleLines() {
		return visibleLinesESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RenderMode getRenderMode() {
		return renderMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRenderMode(RenderMode newRenderMode) {
		final RenderMode oldRenderMode = renderMode;
		renderMode = newRenderMode == null ? RENDER_MODE_EDEFAULT : newRenderMode;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__RENDER_MODE, oldRenderMode, renderMode));
		}
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
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			return getMinimumHeight();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			return getMaximumHeight();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP:
			return isShowValidationSummaryTooltip();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__ENABLE_SORTING:
			return isEnableSorting();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__VISIBLE_LINES:
			return getVisibleLines();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__RENDER_MODE:
			return getRenderMode();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			setMinimumHeight((Integer) newValue);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			setMaximumHeight((Integer) newValue);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP:
			setShowValidationSummaryTooltip((Boolean) newValue);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__ENABLE_SORTING:
			setEnableSorting((Boolean) newValue);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__VISIBLE_LINES:
			setVisibleLines((Integer) newValue);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__RENDER_MODE:
			setRenderMode((RenderMode) newValue);
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
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			unsetMinimumHeight();
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			unsetMaximumHeight();
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP:
			setShowValidationSummaryTooltip(SHOW_VALIDATION_SUMMARY_TOOLTIP_EDEFAULT);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__ENABLE_SORTING:
			setEnableSorting(ENABLE_SORTING_EDEFAULT);
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__VISIBLE_LINES:
			unsetVisibleLines();
			return;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__RENDER_MODE:
			setRenderMode(RENDER_MODE_EDEFAULT);
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
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
			return isSetMinimumHeight();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
			return isSetMaximumHeight();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP:
			return showValidationSummaryTooltip != SHOW_VALIDATION_SUMMARY_TOOLTIP_EDEFAULT;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__ENABLE_SORTING:
			return enableSorting != ENABLE_SORTING_EDEFAULT;
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__VISIBLE_LINES:
			return isSetVisibleLines();
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__RENDER_MODE:
			return renderMode != RENDER_MODE_EDEFAULT;
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
		result.append(" (minimumHeight: "); //$NON-NLS-1$
		if (minimumHeightESet) {
			result.append(minimumHeight);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(", maximumHeight: "); //$NON-NLS-1$
		if (maximumHeightESet) {
			result.append(maximumHeight);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(", showValidationSummaryTooltip: "); //$NON-NLS-1$
		result.append(showValidationSummaryTooltip);
		result.append(", enableSorting: "); //$NON-NLS-1$
		result.append(enableSorting);
		result.append(", visibleLines: "); //$NON-NLS-1$
		if (visibleLinesESet) {
			result.append(visibleLines);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(", renderMode: "); //$NON-NLS-1$
		result.append(renderMode);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		return EMFUtils.filteredEquals(this, styleProperty);
	}

} // VTTableStylePropertyImpl
