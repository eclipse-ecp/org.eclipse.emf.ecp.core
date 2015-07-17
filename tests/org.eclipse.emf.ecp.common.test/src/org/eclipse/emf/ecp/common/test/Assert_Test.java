/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Alexandra Buzila
 *
 */
public class Assert_Test {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testCreateNullObject() {
		assertNotNull(Assert.create(null));
	}

	@Test
	public void testCreateNotNullObject() {
		assertNotNull(Assert.create(new Object()));
	}

	@Test
	public void testNotNullWithNull() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Object must not be null."); //$NON-NLS-1$
		Assert.create(null).notNull();
	}

	@Test
	public void testNotNullWithNotNull() {
		assertNotNull(Assert.create(new Object()).notNull());
	}

	@Test
	public void testOfClassWithNullObjectAndNullClass() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Class must not be null."); //$NON-NLS-1$
		Assert.create(null).ofClass(null);
	}

	@Test
	public void testOfClassWithNullObjectAndNotNullClass() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("null is not of type " + Object.class.getName()); //$NON-NLS-1$
		Assert.create(null).ofClass(Object.class);
	}

	@Test
	public void testOfClassWithNotNull() {
		assertNotNull(Assert.create(new Object()).ofClass(Object.class));
	}

	@Test
	public void testOfClassWithNotNullAndSuperclass() {
		assertNotNull(Assert.create(new String()).ofClass(Object.class));
	}

	@Test
	public void testOfClassWithNotNullAndWrongClass() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(Object.class.getName()
			+ " is not of type " + String.class.getName()); //$NON-NLS-1$
		Assert.create(new Object()).ofClass(String.class);
	}

	@Test
	public void testOfClassWithNotNullObjectAndNullClass() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Class must not be null."); //$NON-NLS-1$
		Assert.create(new Object()).ofClass(null);
	}

	@Test
	public void testCheckWithNullObject() {
		final Object object = null;
		final Object check = Assert.create(object).check();
		assertSame(object, check);
	}

	@Test
	public void testCheckWithNotNullObject() {
		final Object object = new Object();
		final Object check = Assert.create(object).check();
		assertSame(object, check);
	}

	@Test
	public void testCallOrderNotNullCheck() {
		final Object object = new Object();
		final Object check = Assert.create(object).notNull().check();
		assertSame(object, check);
	}

	@Test
	public void testCallOrderOfClassCheck() {
		final Object object = new Object();
		final Object check = Assert.create(object).ofClass(object.getClass()).check();
		assertSame(object, check);
	}

	@Test
	public void testCallOrderNotNullOfClassCheck() {
		final Object object = new Object();
		final Object check = Assert.create(object).notNull().ofClass(object.getClass()).check();
		assertSame(object, check);
	}

	@Test
	public void testCallOrderOfClassNotNullCheck() {
		final Object object = new Object();
		final Object check = Assert.create(object).ofClass(object.getClass()).notNull().check();
		assertSame(object, check);
	}

	@Test
	public void testCallOrderNotNullOfClassCheckWithNullObject() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Object must not be null."); //$NON-NLS-1$
		Assert.create(null).notNull().ofClass(Object.class).check();
	}

	@Test
	public void testCallOrderNotNullOfClassCheckWithWrongClass() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(Object.class.getName()
			+ " is not of type " + String.class.getName()); //$NON-NLS-1$
		Assert.create(new Object()).notNull().ofClass(String.class).check();
	}

	@Test
	public void testCallOrderOfClassNotNullCheckWithWrongClass() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(Object.class.getName()
			+ " is not of type " + String.class.getName()); //$NON-NLS-1$
		Assert.create(new Object()).ofClass(String.class).notNull().check();
	}
}
