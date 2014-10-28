/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.impl.EFactoryImpl;

/**
 * {@link org.eclipse.emf.ecore.EFactory EFactory} implementation for creating objects for the custom data types of the
 * {@link org.eclipse.emf.ecore.EcoreFactory EcoreFactory}.
 *
 * Since edapt uses dynamic EMF the {@link org.eclipse.emf.ecore.EcoreFactory EcoreFactory} is not used when loading a
 * model, we need a custom implementation. Moreover the switch should be based on the data type names, since the ids
 * might differ as well.
 *
 * @author jfaltermeier
 *
 */
public class DynamicEcoreFactory extends EFactoryImpl {

	private static final String E_BIG_DECIMAL = "EBigDecimal";//$NON-NLS-1$
	private static final String E_BIG_INTEGER = "EBigInteger";//$NON-NLS-1$
	private static final String E_BOOLEAN = "EBoolean";//$NON-NLS-1$
	private static final String E_BOOLEAN_OBJECT = "EBooleanObject";//$NON-NLS-1$
	private static final String E_BYTE = "EByte";//$NON-NLS-1$
	private static final String E_BYTE_ARRAY = "EByteArray";//$NON-NLS-1$
	private static final String E_BYTE_OBJECT = "EByteObject";//$NON-NLS-1$
	private static final String E_CHAR = "EChar";//$NON-NLS-1$
	private static final String E_CHARACTER_OBJECT = "ECharacterObject";//$NON-NLS-1$
	private static final String E_DATE = "EDate";//$NON-NLS-1$
	private static final String E_DOUBLE = "EDouble";//$NON-NLS-1$
	private static final String E_DOUBLE_OBJECT = "EDoubleObject";//$NON-NLS-1$
	private static final String E_FLOAT = "EFloat";//$NON-NLS-1$
	private static final String E_FLOAT_OBJECT = "EFloatObject";//$NON-NLS-1$
	private static final String E_INT = "EInt";//$NON-NLS-1$
	private static final String E_INTEGER_OBJECT = "EIntegerObject";//$NON-NLS-1$
	private static final String E_JAVA_CLASS = "EJavaClass";//$NON-NLS-1$
	private static final String E_JAVA_OBJECT = "EJavaObject";//$NON-NLS-1$
	private static final String E_LONG = "ELong";//$NON-NLS-1$
	private static final String E_LONG_OBJECT = "ELongObject";//$NON-NLS-1$
	private static final String E_SHORT = "EShort";//$NON-NLS-1$
	private static final String E_SHORT_OBJECT = "EShortObject";//$NON-NLS-1$
	private static final String E_STRING = "EString";//$NON-NLS-1$

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.impl.EFactoryImpl#createFromString(org.eclipse.emf.ecore.EDataType, java.lang.String)
	 */
	// BEGIN COMPLEX CODE
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		final String id = eDataType.getName();

		if (E_BIG_DECIMAL.equals(id)) {
			return createEBigDecimalFromString(eDataType, initialValue);
		} else if (E_BIG_INTEGER.equals(id)) {
			return createEBigIntegerFromString(eDataType, initialValue);
		} else if (E_BOOLEAN.equals(id)) {
			return createEBooleanFromString(eDataType, initialValue);
		} else if (E_BOOLEAN_OBJECT.equals(id)) {
			return createEBooleanObjectFromString(eDataType, initialValue);
		} else if (E_BYTE.equals(id)) {
			return createEByteFromString(eDataType, initialValue);
		} else if (E_BYTE_ARRAY.equals(id)) {
			return createEByteArrayFromString(eDataType, initialValue);
		} else if (E_BYTE_OBJECT.equals(id)) {
			return createEByteObjectFromString(eDataType, initialValue);
		} else if (E_CHAR.equals(id)) {
			return createECharFromString(eDataType, initialValue);
		} else if (E_CHARACTER_OBJECT.equals(id)) {
			return createECharacterObjectFromString(eDataType, initialValue);
		} else if (E_DATE.equals(id)) {
			return createEDateFromString(eDataType, initialValue);
		} else if (E_DOUBLE.equals(id)) {
			return createEDoubleFromString(eDataType, initialValue);
		} else if (E_DOUBLE_OBJECT.equals(id)) {
			return createEDoubleObjectFromString(eDataType, initialValue);
		} else if (E_FLOAT.equals(id)) {
			return createEFloatFromString(eDataType, initialValue);
		} else if (E_FLOAT_OBJECT.equals(id)) {
			return createEFloatObjectFromString(eDataType, initialValue);
		} else if (E_INT.equals(id)) {
			return createEIntFromString(eDataType, initialValue);
		} else if (E_INTEGER_OBJECT.equals(id)) {
			return createEIntegerObjectFromString(eDataType, initialValue);
		} else if (E_JAVA_CLASS.equals(id)) {
			return createEJavaClassFromString(eDataType, initialValue);
		} else if (E_JAVA_OBJECT.equals(id)) {
			return createEJavaObjectFromString(eDataType, initialValue);
		} else if (E_LONG.equals(id)) {
			return createELongFromString(eDataType, initialValue);
		} else if (E_LONG_OBJECT.equals(id)) {
			return createELongObjectFromString(eDataType, initialValue);
		} else if (E_SHORT.equals(id)) {
			return createEShortFromString(eDataType, initialValue);
		} else if (E_SHORT_OBJECT.equals(id)) {
			return createEShortObjectFromString(eDataType, initialValue);
		} else if (E_STRING.equals(id)) {
			return createEStringFromString(eDataType, initialValue);
		}
		return super.createFromString(eDataType, initialValue);
	}

	// END COMPLEX CODE

	private Boolean booleanValueOf(String initialValue) {
		if ("true".equalsIgnoreCase(initialValue)) { //$NON-NLS-1$
			return Boolean.TRUE;
		} else if ("false".equalsIgnoreCase(initialValue)) { //$NON-NLS-1$
			return Boolean.FALSE;
		} else {
			throw new IllegalArgumentException("Expecting true or false"); //$NON-NLS-1$
		}
	}

	private Boolean createEBooleanObjectFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : booleanValueOf(initialValue);
	}

	private Character createECharacterObjectFromString(EDataType metaObject,
		String initialValue) {
		if (initialValue == null) {
			return null;
		}

		char charValue = 0;
		try {
			charValue = (char) Integer.parseInt(initialValue);
		} catch (final NumberFormatException e) {
			final char[] carray = initialValue.toCharArray();
			charValue = carray[0];
		}
		return charValue;
	}

	private Date createEDateFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) {
			return null;
		}

		Exception exception = null;
		for (int i = 0; i < EDATE_FORMATS.length; ++i) {
			try {
				return EDATE_FORMATS[i].parse(initialValue);
			} catch (final ParseException parseException) {
				exception = parseException;
			}
		}
		throw new WrappedException(exception);
	}

	private Double createEDoubleObjectFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Double.valueOf(initialValue);
	}

	private Float createEFloatObjectFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Float.valueOf(initialValue);
	}

	private Integer createEIntegerObjectFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Integer.valueOf(initialValue);
	}

	private BigDecimal createEBigDecimalFromString(EDataType eDataType,
		String initialValue) {
		return initialValue == null ? null : new BigDecimal(initialValue);
	}

	private BigInteger createEBigIntegerFromString(EDataType eDataType,
		String initialValue) {
		return initialValue == null ? null : new BigInteger(initialValue);
	}

	private String createEStringFromString(EDataType metaObject,
		String initialValue) {
		return initialValue;
	}

	private Integer createEIntFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Integer.valueOf(initialValue);
	}

	private Boolean createEBooleanFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : booleanValueOf(initialValue);
	}

	private Byte createEByteObjectFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Byte.valueOf(initialValue);
	}

	private Float createEFloatFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Float.valueOf(initialValue);
	}

	private Character createECharFromString(EDataType metaObject,
		String initialValue) {
		if (initialValue == null) {
			return null;
		}
		char charValue = 0;
		try {
			charValue = (char) Integer.parseInt(initialValue);
		} catch (final NumberFormatException e) {
			final char[] carray = initialValue.toCharArray();
			charValue = carray[0];
		}
		return charValue;
	}

	private Long createELongFromString(EDataType metaObject, String initialValue) {
		return initialValue == null ? null : Long.valueOf(initialValue);
	}

	private Double createEDoubleFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Double.valueOf(initialValue);
	}

	private Byte createEByteFromString(EDataType metaObject, String initialValue) {
		return initialValue == null ? null : Byte.valueOf(initialValue);
	}

	private Short createEShortFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Short.valueOf(initialValue);
	}

	private Class<?> createEJavaClassFromString(EDataType metaObject,
		String initialValue) {
		try {
			if (initialValue == null) {
				return null;
			} else if ("boolean".equals(initialValue)) { //$NON-NLS-1$
				return boolean.class;
			} else if ("byte".equals(initialValue)) { //$NON-NLS-1$
				return byte.class;
			} else if ("char".equals(initialValue)) { //$NON-NLS-1$
				return char.class;
			} else if ("double".equals(initialValue)) { //$NON-NLS-1$
				return double.class;
			} else if ("float".equals(initialValue)) { //$NON-NLS-1$
				return float.class;
			} else if ("int".equals(initialValue)) { //$NON-NLS-1$
				return int.class;
			} else if ("long".equals(initialValue)) { //$NON-NLS-1$
				return long.class;
			} else if ("short".equals(initialValue)) { //$NON-NLS-1$
				return short.class;
			} else {
				return Class.forName(initialValue);
			}
		} catch (final ClassNotFoundException e) {
			throw new WrappedException(e);
		}
	}

	private Object createEJavaObjectFromString(EDataType eDataType,
		String initialValue) {
		return createFromString(initialValue);
	}

	private Long createELongObjectFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Long.valueOf(initialValue);
	}

	private Short createEShortObjectFromString(EDataType metaObject,
		String initialValue) {
		return initialValue == null ? null : Short.valueOf(initialValue);
	}

	private byte[] createEByteArrayFromString(EDataType eDataType,
		String initialValue) {
		return hexStringToByteArray(initialValue);
	}

	private byte[] hexStringToByteArray(String initialValue) {
		if (initialValue == null) {
			return null;
		}

		final int size = initialValue.length();
		int limit = (size + 1) / 2;
		final byte[] result = new byte[limit];
		if (size % 2 != 0) {
			result[--limit] = hexCharToByte(initialValue.charAt(size - 1));
		}

		for (int i = 0, j = 0; i < limit; ++i) {
			final byte high = hexCharToByte(initialValue.charAt(j++));
			final byte low = hexCharToByte(initialValue.charAt(j++));
			result[i] = (byte) (high << 4 | low);
		}
		return result;
	}

	// BEGIN COMPLEX CODE
	private byte hexCharToByte(char character) {
		switch (character) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9': {
			return (byte) (character - '0');
		}
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f': {
			return (byte) (character - 'a' + 10);
		}
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F': {
			return (byte) (character - 'A' + 10);
		}
		default: {
			throw new NumberFormatException("Invalid hexadecimal"); //$NON-NLS-1$
		}
		}
	}
	// END COMPLEX CODE

}
