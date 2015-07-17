/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl#isItalic
 * <em>Italic</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl#isBold
 * <em>Bold</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl#getColorHEX
 * <em>Color HEX</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl#getHeight
 * <em>Height</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl#getFontName
 * <em>Font Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTFontPropertiesStylePropertyImpl extends MinimalEObjectImpl.Container implements
	VTFontPropertiesStyleProperty {
	/**
	 * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isItalic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ITALIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isItalic()
	 * @generated
	 * @ordered
	 */
	protected boolean italic = ITALIC_EDEFAULT;

	/**
	 * The default value of the '{@link #isBold() <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isBold()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOLD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBold() <em>Bold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isBold()
	 * @generated
	 * @ordered
	 */
	protected boolean bold = BOLD_EDEFAULT;

	/**
	 * The default value of the '{@link #getColorHEX() <em>Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColorHEX()
	 * @generated
	 * @ordered
	 */
	protected static final String COLOR_HEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColorHEX() <em>Color HEX</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColorHEX()
	 * @generated
	 * @ordered
	 */
	protected String colorHEX = COLOR_HEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected int height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getFontName() <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFontName()
	 * @generated
	 * @ordered
	 */
	protected static final String FONT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFontName() <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFontName()
	 * @generated
	 * @ordered
	 */
	protected String fontName = FONT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTFontPropertiesStylePropertyImpl() {
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
		return VTFontPropertiesPackage.Literals.FONT_PROPERTIES_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isItalic() {
		return italic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setItalic(boolean newItalic) {
		final boolean oldItalic = italic;
		italic = newItalic;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__ITALIC, oldItalic, italic));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isBold() {
		return bold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBold(boolean newBold) {
		final boolean oldBold = bold;
		bold = newBold;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__BOLD, oldBold, bold));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getColorHEX() {
		return colorHEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setColorHEX(String newColorHEX) {
		final String oldColorHEX = colorHEX;
		colorHEX = newColorHEX;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX, oldColorHEX, colorHEX));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setHeight(int newHeight) {
		final int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT, oldHeight, height));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getFontName() {
		return fontName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFontName(String newFontName) {
		final String oldFontName = fontName;
		fontName = newFontName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME, oldFontName, fontName));
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
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__ITALIC:
			return isItalic();
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__BOLD:
			return isBold();
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX:
			return getColorHEX();
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT:
			return getHeight();
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME:
			return getFontName();
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
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__ITALIC:
			setItalic((Boolean) newValue);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__BOLD:
			setBold((Boolean) newValue);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX:
			setColorHEX((String) newValue);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT:
			setHeight((Integer) newValue);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME:
			setFontName((String) newValue);
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
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__ITALIC:
			setItalic(ITALIC_EDEFAULT);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__BOLD:
			setBold(BOLD_EDEFAULT);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX:
			setColorHEX(COLOR_HEX_EDEFAULT);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT:
			setHeight(HEIGHT_EDEFAULT);
			return;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME:
			setFontName(FONT_NAME_EDEFAULT);
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
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__ITALIC:
			return italic != ITALIC_EDEFAULT;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__BOLD:
			return bold != BOLD_EDEFAULT;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX:
			return COLOR_HEX_EDEFAULT == null ? colorHEX != null : !COLOR_HEX_EDEFAULT.equals(colorHEX);
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT:
			return height != HEIGHT_EDEFAULT;
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME:
			return FONT_NAME_EDEFAULT == null ? fontName != null : !FONT_NAME_EDEFAULT.equals(fontName);
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
		result.append(" (italic: "); //$NON-NLS-1$
		result.append(italic);
		result.append(", bold: "); //$NON-NLS-1$
		result.append(bold);
		result.append(", colorHEX: "); //$NON-NLS-1$
		result.append(colorHEX);
		result.append(", height: "); //$NON-NLS-1$
		result.append(height);
		result.append(", fontName: "); //$NON-NLS-1$
		result.append(fontName);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleProperty#equalStyles(org.eclipse.emf.ecp.view.template.model.VTStyleProperty)
	 */
	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		if (VTFontPropertiesStyleProperty.class.equals(styleProperty.getClass())) {
			return false;
		}
		final VTFontPropertiesStyleProperty fontStyleProperty = VTFontPropertiesStyleProperty.class.cast(styleProperty);
		return getColorHEX() == fontStyleProperty.getColorHEX()
			&& getFontName() == fontStyleProperty.getFontName()
			&& getHeight() == fontStyleProperty.getHeight()
			&& isBold() == fontStyleProperty.isBold()
			&& isItalic() == fontStyleProperty.isItalic();

	}

} // VTFontPropertiesStylePropertyImpl
