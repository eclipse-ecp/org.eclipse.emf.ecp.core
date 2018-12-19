/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.IOWrappedException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EMOFResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emfforms.spi.editor.helpers.ResourceSetHelpers;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@SuppressWarnings("nls")
public class ResourceSetHelper_PTest {

	private final List<Resource> cleanupList = new ArrayList<Resource>();

	@After
	public void cleanup() {
		cleanupList.stream().forEach(new Consumer<Resource>() {
			@Override
			public void accept(Resource resource) {
				try {
					resource.delete(null);
				} catch (final IOException ex) {
					// do nothing
				}
			}
		});
		cleanupList.clear();
	}

	@Test
	public void saveResourceWithNoOptions() throws IOException, SAXException, ParserConfigurationException {
		// setup
		final EClass class1 = setupClass("package1", "class1");
		final URI resourceURI = URI.createURI("MyResource.xmi");
		final XMIResource resource = createResource(resourceURI);

		resource.getContents().add(class1);
		resource.save(null);

		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResources().add(resource);

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("MyResource.xmi");
		assertEquals(document.getDocumentElement().getAttribute("name"), "class1");

		// modify resource contents
		class1.setName("test");

		// save
		ResourceSetHelpers.save(resourceSet, null);

		// verify
		document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("MyResource.xmi");
		assertEquals(document.getDocumentElement().getAttribute("name"), "test");
	}

	@Test
	public void saveResourceWithOptions() throws IOException, SAXException, ParserConfigurationException {
		// setup
		final URI resourceURI = URI.createURI("MyResource.xmi");
		final XMIResource resource = createResource(resourceURI);
		resource.save(null);
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResources().add(resource);

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("MyResource.xmi");
		assertEquals(document.getXmlEncoding(), "ASCII"); // default encoding

		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-16");

		// save
		ResourceSetHelpers.save(resourceSet, saveOptions);

		// verify
		document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("MyResource.xmi");
		assertEquals(document.getXmlEncoding(), "UTF-16");
	}

	@Test
	public void loadResourceWithProxies() throws IOException, SAXException, ParserConfigurationException {
		// setup
		final EClass classA = setupClass("package1", "classA");
		final EReference refB = EcoreFactory.eINSTANCE.createEReference();
		classA.getEStructuralFeatures().add(refB);

		final EClass classB = setupClass("package2", "classB");
		final EReference refC = EcoreFactory.eINSTANCE.createEReference();
		classB.getEStructuralFeatures().add(refC);

		final EClass classC = setupClass("package3", "classC");

		refB.setEType(classB);
		refC.setEType(classC);

		final URI cURI = URI.createURI("c.xmi");
		XMIResource resource = createResource(cURI);
		cleanupList.add(resource);
		resource.getContents().add(classC);
		resource.save(null);

		final URI bURI = URI.createURI("b.xmi");
		resource = createResource(bURI);
		resource.getContents().add(classB);
		resource.save(null);

		final URI aURI = URI.createURI("a.xmi");
		resource = createResource(aURI);
		resource.getContents().add(classA);
		resource.save(null);

		final ResourceSet resourceSet = new ResourceSetImpl();

		// load A
		ResourceSetHelpers.loadResourceWithProxies(aURI, resourceSet, null);

		// verify
		assertEquals(3, resourceSet.getResources().size());

	}

	@Test(expected = IOWrappedException.class)
	public void loadResourceWithNoOptions() throws IOException {
		// setup
		final EClass classA = setupClass("package1", "classA");

		final URI modelURI = URI.createURI("model.xmi");
		final Resource resource = createResource(modelURI);
		resource.getContents().add(classA);

		final EObject instance = EcoreUtil.create(classA);
		final URI instanceURI = URI.createURI("instance.xmi");
		final XMIResource instanceResource = createResource(instanceURI);
		instanceResource.getContents().add(instance);
		instanceResource.save(null);

		final ResourceSet instanceResourceSet = new ResourceSetImpl();
		// this will throw an exception:
		ResourceSetHelpers.loadResourceWithProxies(instanceURI, instanceResourceSet, null);
	}

	@Test
	public void loadResourceWithOptions() throws IOException {
		// setup
		final EClass classA = setupClass("package1", "classA");

		final URI modelURI = URI.createURI("model.xmi");
		final Resource resource = createResource(modelURI);
		resource.getContents().add(classA);

		final EObject instance = EcoreUtil.create(classA);
		final URI instanceURI = URI.createURI("instance.xmi");
		final XMIResource instanceResource = createResource(instanceURI);
		instanceResource.getContents().add(instance);
		instanceResource.save(null);

		final XMLResource.MissingPackageHandler handler = mock(XMLResource.MissingPackageHandler.class);
		when(handler.getPackage(any(String.class))).thenReturn(classA.getEPackage());

		// load instance
		final Map<Object, Object> loadOptions = new HashMap<Object, Object>();
		loadOptions.put(XMLResource.OPTION_MISSING_PACKAGE_HANDLER, handler);

		final ResourceSet instanceResourceSet = new ResourceSetImpl();
		ResourceSetHelpers.loadResourceWithProxies(instanceURI, instanceResourceSet, loadOptions);

		verify(handler, Mockito.atLeastOnce()).getPackage(any(String.class));
	}

	@Test
	public void loadResourceSetWithOptions() throws IOException {
		// setup
		final EClass classA = setupClass("package1", "classA");

		final URI modelURI = URI.createURI("model.xmi");
		final Resource resource = createResource(modelURI);
		resource.getContents().add(classA);

		final EObject instance = EcoreUtil.create(classA);
		final URI instanceURI = URI.createURI("instance.xmi");
		final XMIResource instanceResource = createResource(instanceURI);
		instanceResource.getContents().add(instance);
		instanceResource.save(null);

		final XMLResource.MissingPackageHandler handler = mock(XMLResource.MissingPackageHandler.class);
		when(handler.getPackage(any(String.class))).thenReturn(classA.getEPackage());

		// load instance
		final Map<Object, Object> loadOptions = new HashMap<Object, Object>();
		loadOptions.put(XMLResource.OPTION_MISSING_PACKAGE_HANDLER, handler);

		final ResourceSet resourceSet = ResourceSetHelpers.loadResourceSetWithProxies(instanceURI,
			new BasicCommandStack(), loadOptions);
		verify(handler, Mockito.atLeastOnce()).getPackage(any(String.class));
		assertNotNull(resourceSet);
	}

	@Test
	public void addResourceToSet() throws IOException {
		// setup
		final EClass classA = setupClass("package1", "classA");

		final URI modelURI = URI.createURI("model.xmi");
		final Resource resource = createResource(modelURI);
		resource.getContents().add(classA);

		final EObject instance = EcoreUtil.create(classA);
		final URI instanceURI = URI.createURI("instance.xmi");
		final XMIResource instanceResource = createResource(instanceURI);
		instanceResource.getContents().add(instance);
		instanceResource.save(null);

		final XMLResource.MissingPackageHandler handler = mock(XMLResource.MissingPackageHandler.class);
		when(handler.getPackage(any(String.class))).thenReturn(classA.getEPackage());
		final Map<Object, Object> loadOptions = new HashMap<Object, Object>();
		loadOptions.put(XMLResource.OPTION_MISSING_PACKAGE_HANDLER, handler);

		final ResourceSet resourceSet = new ResourceSetImpl();

		assertTrue(ResourceSetHelpers.addResourceToSet(resourceSet, instanceURI, loadOptions));
		verify(handler, Mockito.atLeastOnce()).getPackage(any(String.class));
		assertEquals(1, resourceSet.getResources().size());
	}

	/**
	 * @see <a href="https://bugs.eclipse.org/527110">Bug 527110 - Issue with serialisation code in Generic Editor</a>
	 */
	@Test
	public void extendedMetadataTestXMIResource() throws IOException, SAXException, ParserConfigurationException {
		// setup
		final EClass class1 = setupClassWithMetadata();
		final EObject instance = EcoreUtil.create(class1);
		final EStructuralFeature attribute = class1.getEStructuralFeature("attribute1");
		instance.eSet(attribute, "nnn");

		final URI instanceResourceURI = URI.createURI("MyInstance.xmi");
		final XMIResource instanceResource = createResource(instanceResourceURI);
		instanceResource.save(null);
		final ResourceSet resourceSet = ResourceSetHelpers.loadResourceSetWithProxies(instanceResourceURI,
			new BasicCommandStack(), null);
		// get resource loaded into resource set
		final Resource resource = resourceSet.getResource(instanceResourceURI, true);
		resource.getContents().add(instance);
		ResourceSetHelpers.save(resourceSet, null);

		// verify - metadata will NOT be used
		final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("MyInstance.xmi");
		assertTrue(document.getDocumentElement().getAttribute("attr1").isEmpty());
		assertEquals(document.getDocumentElement().getAttribute("attribute1"), "nnn");
	}

	/**
	 * @see <a href="https://bugs.eclipse.org/527110">Bug 527110 - Issue with serialisation code in Generic Editor</a>
	 */
	@Test
	public void extendedMetadataTestXMLResourceWithMetadata()
		throws IOException, SAXException, ParserConfigurationException {
		final EClass class1 = setupClassWithMetadata();
		final EObject instance = EcoreUtil.create(class1);
		final EStructuralFeature attribute = class1.getEStructuralFeature("attribute1");
		instance.eSet(attribute, "nnn");

		// the EMOF resource factory will set the XMLResource.OPTION_EXTENDED_META_DATA to true
		final URI instanceResourceURI = URI.createURI("MyInstance.emof");

		final EMOFResourceImpl instanceResource = new EMOFResourceImpl(instanceResourceURI);
		cleanupList.add(instanceResource);
		instanceResource.save(null);

		final ResourceSet resourceSet = ResourceSetHelpers.loadResourceSetWithProxies(instanceResourceURI,
			new BasicCommandStack(), null);
		// get resource loaded into resource set
		final Resource resource = resourceSet.getResource(instanceResourceURI, true);
		resource.getContents().add(instance);
		ResourceSetHelpers.save(resourceSet, null);

		// verify - metadata will be used
		final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("MyInstance.emof");
		assertEquals(document.getDocumentElement().getAttribute("attr1"), "nnn");
		assertTrue(document.getDocumentElement().getAttribute("attribute1").isEmpty());
	}

	/**
	 * @see <a href="https://bugs.eclipse.org/527110">Bug 527110 - Issue with serialisation code in Generic Editor</a>
	 */
	@Test
	public void extendedMetadataTestXMLResourceNoMetadata()
		throws IOException, SAXException, ParserConfigurationException {
		final EClass class1 = setupClassWithMetadata();
		final EObject instance = EcoreUtil.create(class1);
		final EStructuralFeature attribute = class1.getEStructuralFeature("attribute1");
		instance.eSet(attribute, "nnn");

		// the EMOF resource factory will set the XMLResource.OPTION_EXTENDED_META_DATA to true
		final URI instanceResourceURI = URI.createURI("MyInstance.xml");

		final EMOFResourceImpl instanceResource = new EMOFResourceImpl(instanceResourceURI);
		cleanupList.add(instanceResource);
		instanceResource.save(null);

		final ResourceSet resourceSet = ResourceSetHelpers.loadResourceSetWithProxies(instanceResourceURI,
			new BasicCommandStack(), null);
		// get resource loaded into resource set
		final Resource resource = resourceSet.getResource(instanceResourceURI, true);
		resource.getContents().add(instance);
		ResourceSetHelpers.save(resourceSet, null);

		// verify - metadata will NOT be used
		final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
			.parse("MyInstance.xml");
		assertTrue(document.getDocumentElement().getAttribute("attr1").isEmpty());
		assertEquals(document.getDocumentElement().getAttribute("attribute1"), "nnn");
	}

	private XMIResource createResource(URI resourceURI) {
		final XMIResource resource = new XMIResourceImpl(resourceURI);
		cleanupList.add(resource);
		return resource;
	}

	private EClass setupClassWithMetadata() {
		final EClass class1 = setupClass("package1", "class1");

		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName("attribute1");
		attribute.setEType(EcorePackage.eINSTANCE.getEString());

		final EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource("http:///org/eclipse/emf/ecore/util/ExtendedMetaData");
		annotation.getDetails().put("name", "attr1");
		annotation.getDetails().put("kind", "attribute");
		attribute.getEAnnotations().add(annotation);

		class1.getEStructuralFeatures().add(attribute);
		return class1;
	}

	private EClass setupClass(String packageName, String className) {
		final EPackage package1 = EcoreFactory.eINSTANCE.createEPackage();
		package1.setName(packageName);
		package1.setNsURI(packageName);

		final EClass class1 = EcoreFactory.eINSTANCE.createEClass();
		class1.setName(className);
		package1.getEClassifiers().add(class1);

		return class1;
	}
}
