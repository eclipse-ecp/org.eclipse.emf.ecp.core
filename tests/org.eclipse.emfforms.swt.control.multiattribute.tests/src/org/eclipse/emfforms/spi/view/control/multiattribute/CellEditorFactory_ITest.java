/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.view.control.multiattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.internal.swt.table.DateCellEditor;
import org.eclipse.emf.ecp.edit.internal.swt.table.NumberCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPEnumCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.StringCellEditor;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.internal.view.control.multiattribute.celleditor.CellEditorFactory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Integration tests for the multi-attribute editor's {@link CellEditorFactory}.
 */
@RunWith(Parameterized.class)
@SuppressWarnings("nls")
public class CellEditorFactory_ITest {

	private static EPackage ePackage;
	private static EClass eClass;

	private DefaultRealm realm;
	private Shell shell;

	private Table table;

	private final EAttribute attribute;
	private final EObject owner;
	private final Class<? extends CellEditor> expectedEditorType;

	@Mock
	private ViewModelContext viewModelContext;

	/**
	 * Initializes me with my test parameters.
	 *
	 * @param attributeName the attribute name for which to create a cell editor
	 * @param expectedEditorType the kind of cell editor expected to be created
	 */
	public CellEditorFactory_ITest(String attributeName, Class<? extends CellEditor> expectedEditorType) {
		super();

		attribute = (EAttribute) getEClass().getEStructuralFeature(attributeName);
		owner = EcoreUtil.create(getEClass());
		this.expectedEditorType = expectedEditorType;
	}

	@Test
	public void createCellEditor() {
		final CellEditor editor = CellEditorFactory.INSTANCE.createCellEditor(attribute, owner, table,
			viewModelContext);

		assertThat("No cell editor created", editor, notNullValue());
		assertThat("Wrong cell editor created", editor, instanceOf(expectedEditorType));
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{ "stringAttr", StringCellEditor.class },
			{ "intAttr", NumberCellEditor.class },
			{ "dateAttr", DateCellEditor.class },
			{ "enumAttr", ECPEnumCellEditor.class },
		});
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Before
	public void initUI() {
		realm = new DefaultRealm();
		shell = new Shell();
		table = new Table(shell, SWT.BORDER);
	}

	@After
	public void destroyUI() {
		shell.dispose();
		realm.dispose();
	}

	private static EClass getEClass() {
		if (eClass == null) {
			ePackage = EcoreFactory.eINSTANCE.createEPackage();
			ePackage.setName("test");
			ePackage.setNsURI("http://test/test");
			ePackage.setNsPrefix("test");

			eClass = eClass("Foo");
			final EEnum flavourEnum = eEnum("Flavour", "sweet", "salty", "sour", "bitter");

			attr("stringAttr", EcorePackage.Literals.ESTRING);
			attr("intAttr", EcorePackage.Literals.EINT);
			attr("dateAttr", EcorePackage.Literals.EDATE);
			attr("enumAttr", flavourEnum);
		}

		return eClass;
	}

	private static <T extends EClassifier> T eClassifier(EClass metaclass, String name) {
		@SuppressWarnings("unchecked")
		final T result = (T) EcoreFactory.eINSTANCE.create(metaclass);
		result.setName(name);
		ePackage.getEClassifiers().add(result);
		return result;
	}

	private static EClass eClass(String name) {
		return eClassifier(EcorePackage.Literals.ECLASS, name);
	}

	private static EEnum eEnum(String name, String... literal) {
		final EEnum result = eClassifier(EcorePackage.Literals.EENUM, name);
		for (final String next : literal) {
			final EEnumLiteral eel = EcoreFactory.eINSTANCE.createEEnumLiteral();
			eel.setName(next);
			result.getELiterals().add(eel);
		}
		return result;
	}

	private static EAttribute attr(String name, EDataType type) {
		final EAttribute result = EcoreFactory.eINSTANCE.createEAttribute();
		result.setName(name);
		result.setUpperBound(-1);
		result.setEType(type);
		eClass.getEStructuralFeatures().add(result);
		return result;
	}

}
