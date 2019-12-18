/**
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 */
package org.eclipse.emfforms.datatemplate.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.eclipse.emfforms.datatemplate.Template;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test case for the model object
 * '<em><b>Template</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
@SuppressWarnings("nls")
public class TemplateTest extends TestCase {

	/**
	 * The fixture for this Template test case. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	protected Template fixture = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(TemplateTest.class);
	}

	/**
	 * Constructs a new Template test case with the given name. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TemplateTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Template test case. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(Template fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Template test case. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	protected Template getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DataTemplateFactory.eINSTANCE.createTemplate());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	static URL getResourceURL(Class<?> contextClass, String path) {
		URL result = contextClass.getResource(path);

		if (result == null) {
			// We're in Eclipse
			final String contextURL = contextClass.getResource(contextClass.getSimpleName() + ".class")
				.toExternalForm();

			try {
				if (path.startsWith("/")) {
					final String baseURL = contextURL.substring(0, contextURL.lastIndexOf("/bin/"));
					result = new URL(baseURL + path);
				} else {
					final String baseURL = contextURL.substring(0, contextURL.lastIndexOf("/") + 1);
					result = new URL(baseURL + path);
				}
			} catch (final MalformedURLException e) {
				e.printStackTrace();
				fail("Could not get resource '" + path + "': " + e.getMessage());
			}
		}

		return result;
	}

	/**
	 * Per bug 550814, dynamic instances of the template model must be supported.
	 * This test fails without the fix for 550814 because the EObject class in the
	 * dynamic implementation of Ecore has no relation to the static EObject class.
	 *
	 * @see <a href="http://eclip.se/550814">bug 550814</a>
	 */
	public void testDynamicInstance() throws IOException {
		// Load the dynamic Ecore model
		final ResourceSet rset = new ResourceSetImpl();
		final URIConverter converter = new ExtensibleURIConverterImpl();
		rset.setURIConverter(converter);
		rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		final URL modelURL = getResourceURL(DataTemplatePackage.class, "/model/datatemplate.ecore");
		URI physicalURI = URI.createURI(modelURL.toExternalForm(), true);
		final URI modelURI = URI
			.createPlatformPluginURI("org.eclipse.emfforms.datatemplate.model/model/datatemplate.ecore", true);
		converter.getURIMap().put(modelURI.trimSegments(2).appendSegment(""),
			physicalURI.trimSegments(2).appendSegment(""));
		final Resource model = rset.getResource(modelURI, true);

		// Give the dynamic model the dynamic Ecore that it expects
		final URL ecoreURL = getResourceURL(EcorePackage.class, "/model/Ecore.ecore");
		physicalURI = URI.createURI(ecoreURL.toExternalForm(), true);
		final URI ecoreURI = URI.createPlatformPluginURI("org.eclipse.emf.ecore/model/Ecore.ecore", true);
		converter.getURIMap().put(ecoreURI.trimSegments(2).appendSegment(""),
			physicalURI.trimSegments(2).appendSegment(""));
		rset.getResource(ecoreURI, true);

		// Resolve cross-references to Ecore
		EcoreUtil.resolveAll(model);

		final EPackage package_ = (EPackage) EcoreUtil.getObjectByType(model.getContents(),
			EcorePackage.Literals.EPACKAGE);
		final EClass templateClass = (EClass) package_.getEClassifier("Template");
		final EReference instance = (EReference) templateClass.getEStructuralFeature("instance");

		try {
			final EObject template = EcoreUtil.create(templateClass);
			final EOperation operation = EcoreFactory.eINSTANCE.createEOperation();

			// an EOperation is not an EObject in the Ecore model, but it is in the Java
			// implementation
			template.eSet(instance, operation);

			assertThat("Template instance not correctly set", template.eGet(instance), is(operation));
		} catch (final Exception e) {
			e.printStackTrace();
			fail("Failed to set template instance: " + e.getMessage());
		}
	}

} // TemplateTest
