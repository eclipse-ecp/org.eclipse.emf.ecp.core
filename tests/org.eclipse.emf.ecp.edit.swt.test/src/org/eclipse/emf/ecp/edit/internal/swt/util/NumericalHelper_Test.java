/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.edit.internal.swt.controls.NumericalHelper;
import org.junit.Test;

public class NumericalHelper_Test {

	@Test
	public void testGetDefaultValueBytePrim() {
		assertEquals(0x0, NumericalHelper.getDefaultValue(byte.class));
	}

	@Test
	public void testGetDefaultValueByteObj() {
		assertEquals(null, NumericalHelper.getDefaultValue(Byte.class));
	}

	@Test
	public void testNumberToInstanceClassBytePrim() {
		final short number = 2;
		final byte byteValue = 2;
		final Object result = NumericalHelper.numberToInstanceClass(number, byte.class);
		assertEquals(byteValue, result);
	}

	@Test
	public void testNumberToInstanceClassByteObj() {
		final short number = 2;
		final byte byteValue = 2;
		final Object result = NumericalHelper.numberToInstanceClass(number, Byte.class);
		assertEquals(new Byte(byteValue), result);
	}

	@Test
	public void testNumberToInstanceClassBytePrimMinValue() {
		final short number = -1000;
		final byte byteValue = Byte.MIN_VALUE;
		final Object result = NumericalHelper.numberToInstanceClass(number, byte.class);
		assertEquals(byteValue, result);
	}

	@Test
	public void testNumberToInstanceClassByteObjMinValue() {
		final short number = -1000;
		final byte byteValue = Byte.MIN_VALUE;
		final Object result = NumericalHelper.numberToInstanceClass(number, Byte.class);
		assertEquals(new Byte(byteValue), result);
	}

	@Test
	public void testNumberToInstanceClassBytePrimMaxValue() {
		final short number = 1000;
		final byte byteValue = Byte.MAX_VALUE;
		final Object result = NumericalHelper.numberToInstanceClass(number, byte.class);
		assertEquals(byteValue, result);
	}

	@Test
	public void testNumberToInstanceClassByteObjMaxValue() {
		final short number = 1000;
		final byte byteValue = Byte.MAX_VALUE;
		final Object result = NumericalHelper.numberToInstanceClass(number, Byte.class);
		assertEquals(new Byte(byteValue), result);
	}

	@Test
	public void testIsIntegerByteByte() {
		assertTrue(NumericalHelper.isInteger(Byte.class));
		assertTrue(NumericalHelper.isInteger(byte.class));
		assertFalse(NumericalHelper.isDouble(Byte.class));
		assertFalse(NumericalHelper.isDouble(byte.class));
	}

}
