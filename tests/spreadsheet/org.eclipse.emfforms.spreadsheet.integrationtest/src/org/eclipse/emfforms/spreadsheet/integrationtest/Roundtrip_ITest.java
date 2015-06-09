/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spreadsheet.integrationtest;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.makeithappen.model.task.Nationality;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskFactory;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskPackage;
import org.eclipse.emf.ecp.makeithappen.model.task.User;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetExporter;
import org.eclipse.emfforms.spi.spreadsheet.core.importer.EMFFormsSpreadsheetImporter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class Roundtrip_ITest {
	private DefaultRealm realm;

	@Before
	public void setup() {
		realm = new DefaultRealm();
	}

	@After
	public void tearDown() {
		realm.dispose();
	}

	@Test
	public void test() throws DatatypeConfigurationException {
		// write data
		EMFFormsSpreadsheetExporter viewRenderer = EMFFormsSpreadsheetExporter.INSTANCE;
		File targetFile = new File("export.xls");
		EObject user = getDomainModel();
		Map<String, Object> context=new LinkedHashMap<String, Object>();
		context.put("root", true);
		context.put("detail", true);
		viewRenderer.render(targetFile.getAbsolutePath(), user,
				ViewProviderHelper.getView(user, context));

		// read data
		final EMFFormsSpreadsheetImporter spreadsheetImport = EMFFormsSpreadsheetImporter.INSTANCE;
		final Collection<EObject> users = spreadsheetImport.importSpreadsheet(
				targetFile.getAbsolutePath(), TaskPackage.eINSTANCE.getUser());
		for (final EObject eObject : users) {
			EcoreUtil.equals(eObject, user);
		}

	}

	private EObject getDomainModel() {
		User user=TaskFactory.eINSTANCE.createUser();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("john.doe@mail.com");
		user.setWeight(1.1);
		user.setHeigth(1);
		user.setNationality(Nationality.ITALIAN);
		return user;
	}

}
