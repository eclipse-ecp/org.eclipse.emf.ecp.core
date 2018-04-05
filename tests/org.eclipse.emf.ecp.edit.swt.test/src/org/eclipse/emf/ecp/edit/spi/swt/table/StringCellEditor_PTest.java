/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.model.testData.TestDataFactory;
import org.eclipse.emf.ecp.edit.internal.model.testData.TestDataPackage;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RunWith(Parameterized.class)
public class StringCellEditor_PTest {

	@Parameters(name = "Feature:{0} Expected Value:{2} isValid:{3}")
	public static Object[] parameters() {
		return new Object[][] {
			{ TestDataPackage.eINSTANCE.getTestData_String(), TestDataFactory.eINSTANCE.createTestData(), "foo", true //$NON-NLS-1$
			},
			{ TestDataPackage.eINSTANCE.getTestData_Boolean(), TestDataFactory.eINSTANCE.createTestData(),
				Boolean.TRUE,
				true },
			{ TestDataPackage.eINSTANCE.getTestData_Integer(), TestDataFactory.eINSTANCE.createTestData(), 2, true },
			{ TestDataPackage.eINSTANCE.getTestData_Long(), TestDataFactory.eINSTANCE.createTestData(), 2L, true },
			{ TestDataPackage.eINSTANCE.getTestData_Double(), TestDataFactory.eINSTANCE.createTestData(), 85.5d, true
			},
			{ TestDataPackage.eINSTANCE.getTestData_Float(), TestDataFactory.eINSTANCE.createTestData(), 85.5f, true
			},
			{ TestDataPackage.eINSTANCE.getTestData_StringMax8(), TestDataFactory.eINSTANCE.createTestData(),
				"extra long invalid string", false }, //$NON-NLS-1$
		};
	}

	private IObservableValue target;
	private IObservableValue model;
	private EMFDataBindingContext dbc;
	private StringCellEditor editor;
	private final EStructuralFeature feature;
	private final Object newValue;
	private final EObject eObject;
	private final boolean valid;

	public StringCellEditor_PTest(EStructuralFeature feature, EObject eObject, Object newValue, boolean valid) {
		this.feature = feature;
		this.eObject = eObject;
		this.newValue = newValue;
		this.valid = valid;

	}

	@Before
	public void setup() {
		final DefaultRealm realm = new DefaultRealm();
		dbc = new EMFDataBindingContext(realm);
		model = EMFProperties.value(feature).observe(realm, eObject);
		final Shell shell = new Shell();
		editor = new StringCellEditor(shell);
		editor.instantiate(feature, null);
		target = editor.getValueProperty().observe(editor);
		dbc.bindValue(target, model, editor.getTargetToModelStrategy(dbc), editor.getModelToTargetStrategy(dbc));
	}

	@After
	public void destroy() {
		dbc.dispose();
		model.dispose();
		target.dispose();
		editor.dispose();
	}

	@Test
	public void setValue() {
		target.setValue(newValue.toString());
		assertEquals(newValue.toString(), ((Text) editor.getControl()).getText());
		if (valid) {
			assertEquals(newValue, model.getValue());
			assertEquals(target.getValue(), model.getValue().toString());
		} else {
			assertNull(model.getValue());
		}
	}
}
