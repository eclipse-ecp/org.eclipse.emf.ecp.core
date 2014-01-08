/*******************************************************************************
 * Copyright (c) 2013 EclipseSource.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import org.eclipse.osgi.util.NLS;

public final class ControlMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.edit.internal.swt.controls.messages"; //$NON-NLS-1$

	public static String AbstractTextControl_InvalidInput;
	public static String AbstractTextControl_InvalidInputSpace;
	public static String AbstractTextControl_Unset;

	public static String AttributeMultiControl_NotSetClickToSet;
	public static String AttributeMultiControl_Unset;

	public static String BooleanControl_NoBooleanSetClickToSetBoolean;
	public static String BooleanControl_UnsetBoolean;

	public static String DateTimeControl_NoDateSetClickToSetDate;
	public static String DateTimeControl_UnsetDate;

	public static String EEnumControl_NoValueSetClickToSetValue;
	public static String EEnumControl_UnsetValue;

	public static String LinkControl_NoLinkSetClickToSetLink;
	public static String LinkControl_NotSet;
	public static String LinkControl_UnsetLink;

	public static String NumericalControl_FormatNumerical;
	public static String NumericalControl_FormatNumericalDecimal;
	public static String NumericalControl_InvalidNumber;
	public static String NumericalControl_InvalidNumberWillBeUnset;
	public static String NumericalControl_NoNumberClickToSetNumber;
	public static String NumericalControl_UnsetNumber;

	public static String ReferenceMultiControl_NotSetClickToSet;
	public static String ReferenceMultiControl_Unset;

	public static String StringControl_NoTextSetClickToSetText;
	public static String StringControl_UnsetText;

	public static String TableControl_AddInstanceOf;
	public static String TableControl_Delete;
	public static String TableControl_DeleteAreYouSure;
	public static String TableControl_NotSetClickToSet;
	public static String TableControl_RemoveSelected;
	public static String TableControl_Unset;
	public static String TableControl_ValidationStatusColumn;

	public static String XmlDateControlText_InvalidNumber;
	public static String XmlDateControlText_NoDateSetClickToSetDate;
	public static String XmlDateControlText_NumberInvalidValueWillBeUnset;
	public static String XmlDateControlText_UnsetDate;

	private ControlMessages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, ControlMessages.class);
	}

}
