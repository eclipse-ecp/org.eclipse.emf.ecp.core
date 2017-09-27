/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jonas Helming
 *
 */
public class MultiStringCellEditor_PTest {

	private MultiStringCellEditor cellEditor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cellEditor = new MultiStringCellEditor(new Shell());
	}

	/**
	 * Test method for {@link org.eclipse.emf.ecp.edit.spi.swt.table.MultiStringCellEditor#getSeparator()}.
	 */
	@Test
	public void testGetSeparator() {
		assertEquals(";", cellEditor.getSeparator()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link org.eclipse.emf.ecp.edit.spi.swt.table.MultiStringCellEditor#getColumnWidthWeight()}.
	 */
	@Test
	public void testGetColumnWidthWeight() {
		assertEquals(50, cellEditor.getColumnWidthWeight());
	}

	/**
	 * Test method for {@link org.eclipse.emf.ecp.edit.spi.swt.table.MultiStringCellEditor#setEditable(boolean)}.
	 */
	@Test
	public void testSetEditable() {
		assertEquals(true, cellEditor.getText().getEditable());
		cellEditor.setEditable(false);
		assertEquals(false, cellEditor.getText().getEditable());
	}

	/**
	 * Test method for {@link org.eclipse.emf.ecp.edit.spi.swt.table.MultiStringCellEditor#getImage(java.lang.Object)}.
	 */
	@Test
	public void testGetImage() {
		assertEquals(null, cellEditor.getImage("")); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link org.eclipse.emf.ecp.edit.spi.swt.table.MultiStringCellEditor#getMinWidth()}.
	 */
	@Test
	public void testGetMinWidth() {
		assertEquals(0, cellEditor.getMinWidth());
	}

	@Test
	public void testGetFormatedStringWithEmptyBeginning() {
		final ArrayList<String> list = new ArrayList<String>();
		list.add(""); //$NON-NLS-1$
		list.add("test1"); //$NON-NLS-1$

		final String formatedString = cellEditor.getFormatedString(list);
		assertEquals(";test1", formatedString); //$NON-NLS-1$
	}

	@Test
	public void testGetFormatedStringWithEmptyEnd() {
		final ArrayList<String> list = new ArrayList<String>();
		list.add("test1"); //$NON-NLS-1$
		list.add(""); //$NON-NLS-1$

		final String formatedString = cellEditor.getFormatedString(list);
		assertEquals("test1;", formatedString); //$NON-NLS-1$
	}

	@Test
	public void testGetFormatedStringWithEmptyMiddle() {
		final ArrayList<String> list = new ArrayList<String>();
		list.add("test1"); //$NON-NLS-1$
		list.add(""); //$NON-NLS-1$
		list.add("test2"); //$NON-NLS-1$

		final String formatedString = cellEditor.getFormatedString(list);
		assertEquals("test1;;test2", formatedString); //$NON-NLS-1$
	}

	@Test
	public void testGetFormatedString() {
		final ArrayList<String> list = new ArrayList<String>();
		list.add("test0"); //$NON-NLS-1$
		list.add("test1"); //$NON-NLS-1$

		final String formatedString = cellEditor.getFormatedString(list);
		assertEquals("test0;test1", formatedString); //$NON-NLS-1$
	}

	@Test
	public void testConvertStringToList() {

		final List<String> result = cellEditor.convertStringToList("test0;test1"); //$NON-NLS-1$

		final ArrayList<String> list = new ArrayList<String>();
		list.add("test0"); //$NON-NLS-1$
		list.add("test1"); //$NON-NLS-1$

		assertEquals(list, result);
	}

	@Test
	public void testConvertStringToListWithEmptyBeginning() {
		final List<String> result = cellEditor.convertStringToList(";test1"); //$NON-NLS-1$

		final ArrayList<String> list = new ArrayList<String>();
		list.add(""); //$NON-NLS-1$
		list.add("test1"); //$NON-NLS-1$

		assertEquals(list, result);
	}

	@Test
	public void testConvertStringToListWithEmptyEnd() {
		final List<String> result = cellEditor.convertStringToList("test1;"); //$NON-NLS-1$
		final ArrayList<String> list = new ArrayList<String>();
		list.add("test1"); //$NON-NLS-1$
		list.add(""); //$NON-NLS-1$

		assertEquals(list, result);
	}

	@Test
	public void testConvertStringToListWithEmptyMiddle() {
		final List<String> result = cellEditor.convertStringToList("test1;;test2"); //$NON-NLS-1$
		final ArrayList<String> list = new ArrayList<String>();
		list.add("test1"); //$NON-NLS-1$
		list.add(""); //$NON-NLS-1$
		list.add("test2"); //$NON-NLS-1$

		assertEquals(list, result);
	}

}
