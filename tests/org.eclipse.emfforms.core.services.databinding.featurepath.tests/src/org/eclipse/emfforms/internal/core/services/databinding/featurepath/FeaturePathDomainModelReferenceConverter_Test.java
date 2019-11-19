/*******************************************************************************
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
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 553224
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.featurepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * JUnit test for {@link FeaturePathDomainModelReferenceConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class FeaturePathDomainModelReferenceConverter_Test {

	private FeaturePathDomainModelReferenceConverter converter;
	private static EObject validEObject;

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void setupClass() {
		validEObject = createValidEObject();
	}

	private static EObject createValidEObject() {
		final Resource resource = createVirtualResource();
		final EObject domainObject = EcoreFactory.eINSTANCE.createEObject();
		if (resource != null) {
			resource.getContents().add(domainObject);
		}
		return domainObject;
	}

	private static Resource createVirtualResource() {
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl()); //$NON-NLS-1$
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI.xmi")); //$NON-NLS-1$
		return resource;
	}

	private static EObject createValidEObject(EClass eClass) {
		final Resource resource = createVirtualResource();
		final EObject domainObject = EcoreUtil.create(eClass);
		if (resource != null) {
			resource.getContents().add(domainObject);
		}
		return domainObject;
	}

	private static EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}

	private static VFeaturePathDomainModelReference createValidDMR() {
		final Resource resource = createVirtualResource();
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl control = VViewFactory.eINSTANCE.createControl();
		view.getChildren().add(control);
		resource.getContents().add(view);
		final VFeaturePathDomainModelReference result = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control.setDomainModelReference(result);
		((XMLResource) resource).setID(result, "theDMR"); //$NON-NLS-1$
		return result;
	}

	static Matcher<String> find(String regex) {
		return new CustomTypeSafeMatcher<String>("matches '" + regex + "'") { //$NON-NLS-1$//$NON-NLS-2$
			@Override
			protected boolean matchesSafely(String item) {
				final java.util.regex.Matcher m = Pattern.compile(regex).matcher(item);
				return m.find();
			}
		};
	}

	/**
	 * Set up that is executed before every test.
	 */
	@Before
	public void setUp() {
		converter = new FeaturePathDomainModelReferenceConverter();

	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)}
	 * .
	 */
	@Test
	public void testIsApplicable() {
		// The FeaturePathDomainModelReferenceConverter is the standard converter for VFeaturePathDomainModelReference
		// with a low priority.
		assertEquals(0.0, converter.isApplicable(mock(VFeaturePathDomainModelReference.class)), 0d);

		// The FeaturePathDomainModelReferenceConverter is not applicable other references than
		// VFeaturePathDomainModelReferences
		assertEquals(DomainModelReferenceConverter.NOT_APPLICABLE,
			converter.isApplicable(mock(VDomainModelReference.class)), 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)}
	 * .
	 */
	@Test
	public void testIsApplicableNull() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("must not be null"); //$NON-NLS-1$

		converter.isApplicable(null);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToValueProperty() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the attribute
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.eINSTANCE.getA_B());
		referencePath.add(TestPackage.eINSTANCE.getB_C());
		referencePath.add(TestPackage.eINSTANCE.getC_D());

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_X();

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(feature);

		final IValueProperty valueProperty = converter.convertToValueProperty(pathReference, validEObject);

		// The converter should return an IEMFValueProperty
		assertTrue(valueProperty instanceof IEMFValueProperty);

		final IEMFValueProperty emfProperty = (IEMFValueProperty) valueProperty;

		// Check EStructuralFeature of the property.
		assertEquals(feature, emfProperty.getStructuralFeature());

		// Check correct path.
		final String expected = "A.b<B> => B.c<C> => C.d<D> => D.x<EString>"; //$NON-NLS-1$
		assertEquals(expected, emfProperty.toString());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToValuePropertyNoReferencePath() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_X();
		pathReference.setDomainModelEFeature(feature);

		final IValueProperty valueProperty = converter.convertToValueProperty(pathReference, validEObject);

		// The converter should return an IEMFValueProperty
		assertTrue(valueProperty instanceof IEMFValueProperty);

		final IEMFValueProperty emfProperty = (IEMFValueProperty) valueProperty;

		// Check EStructuralFeature of the property.
		assertEquals(feature, emfProperty.getStructuralFeature());

		// Check correct path.
		final String expected = "D.x<EString>"; //$NON-NLS-1$
		assertEquals(expected, emfProperty.toString());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToValuePropertyNoFeature() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("field domainModelEFeature"); //$NON-NLS-1$
		thrown.expectMessage("must not be null"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		converter.convertToValueProperty(pathReference, validEObject);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToValuePropertyNull() throws DatabindingFailedException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("must not be null"); //$NON-NLS-1$

		converter.convertToValueProperty(null, validEObject);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToValuePropertyWrongReferenceType() throws DatabindingFailedException {
		final VDomainModelReference dmr = mock(VDomainModelReference.class);

		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("DomainModelReference " + dmr.toString()); //$NON-NLS-1$
		thrown.expectMessage("is not an instance of VFeaturePathDomainModelReference"); //$NON-NLS-1$

		converter.convertToValueProperty(dmr, validEObject);
	}

	@Test
	public void testConvertToValuePropertyListReferenceFirstInPath() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("not a single reference: A.bList (test)"); //$NON-NLS-1$
		thrown.expectMessage("DMR is VFeaturePathDomainModelReferenceImpl@"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the attribute
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.Literals.A__BLIST);
		referencePath.add(TestPackage.Literals.B__C);
		referencePath.add(TestPackage.Literals.C__D);

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(TestPackage.Literals.D__X);

		converter.convertToValueProperty(pathReference, validEObject);
	}

	@Test
	public void testConvertToValuePropertyListReferenceInPath() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("not a single reference: B.cList (test)"); //$NON-NLS-1$
		thrown.expectMessage("DMR is VFeaturePathDomainModelReferenceImpl@"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the attribute
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.Literals.A__B);
		referencePath.add(TestPackage.Literals.B__CLIST);
		referencePath.add(TestPackage.Literals.C__D);

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(TestPackage.Literals.D__X);

		converter.convertToValueProperty(pathReference, validEObject);
	}

	@Test
	public void convertToValueProperty_editingDomain() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the attribute
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.eINSTANCE.getA_B());
		referencePath.add(TestPackage.eINSTANCE.getB_C());
		referencePath.add(TestPackage.eINSTANCE.getC_D());
		final EObject domain = createValidEObject(TestPackage.Literals.A);

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_X();

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(feature);

		final IValueProperty valueProperty = converter.convertToValueProperty(pathReference, TestPackage.Literals.A,
			getEditingDomain(domain));

		// The converter should return an IEMFValueProperty
		assertTrue(valueProperty instanceof IEMFValueProperty);

		final IEMFValueProperty emfProperty = (IEMFValueProperty) valueProperty;

		// Check EStructuralFeature of the property.
		assertEquals(feature, emfProperty.getStructuralFeature());

		// Check correct path.
		final String expected = "A.b<B> => B.c<C> => C.d<D> => D.x<EString>"; //$NON-NLS-1$
		assertEquals(expected, emfProperty.toString());
	}

	@Test
	public void convertToValueProperty_editingDomain_NoReferencePath() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_X();
		pathReference.setDomainModelEFeature(feature);
		final EObject domain = createValidEObject(TestPackage.Literals.D);

		final IValueProperty valueProperty = converter.convertToValueProperty(pathReference, TestPackage.Literals.D,
			getEditingDomain(domain));

		// The converter should return an IEMFValueProperty
		assertTrue(valueProperty instanceof IEMFValueProperty);

		final IEMFValueProperty emfProperty = (IEMFValueProperty) valueProperty;

		// Check EStructuralFeature of the property.
		assertEquals(feature, emfProperty.getStructuralFeature());

		// Check correct path.
		final String expected = "D.x<EString>"; //$NON-NLS-1$
		assertEquals(expected, emfProperty.toString());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToListProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToListProperty() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the list
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.eINSTANCE.getA_B());
		referencePath.add(TestPackage.eINSTANCE.getB_C());
		referencePath.add(TestPackage.eINSTANCE.getC_D());

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_YList();

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(feature);

		final IListProperty listProperty = converter.convertToListProperty(pathReference, validEObject);

		// The converter should return an IEMFListProperty
		assertTrue(listProperty instanceof IEMFListProperty);

		final IEMFListProperty emfProperty = (IEMFListProperty) listProperty;

		// Check EStructuralFeature of the property.
		assertEquals(feature, emfProperty.getStructuralFeature());

		// Check correct path.
		final String expected = "A.b<B> => B.c<C> => C.d<D> => D.yList[]<EInt>"; //$NON-NLS-1$
		assertEquals(expected, emfProperty.toString());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToListProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToListPropertyNoFeature() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("field domainModelEFeature"); //$NON-NLS-1$
		thrown.expectMessage("must not be null"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		converter.convertToListProperty(pathReference, validEObject);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToListProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToListPropertyNoReferencePath() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_YList();
		pathReference.setDomainModelEFeature(feature);

		final IListProperty listProperty = converter.convertToListProperty(pathReference, validEObject);

		// The converter should return an IEMFListProperty
		assertTrue(listProperty instanceof IEMFListProperty);

		final IEMFListProperty emfProperty = (IEMFListProperty) listProperty;

		// Check EStructuralFeature of the property.
		assertEquals(feature, emfProperty.getStructuralFeature());

		// Check correct path.
		final String expected = "D.yList[]<EInt>"; //$NON-NLS-1$
		assertEquals(expected, emfProperty.toString());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToListProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToListPropertyNull() throws DatabindingFailedException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("must not be null"); //$NON-NLS-1$

		converter.convertToListProperty(null, validEObject);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.core.services.databinding.featurepath.FeaturePathDomainModelReferenceConverter#convertToListProperty(VDomainModelReference,EObject)}
	 * .
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testConvertToListPropertyWrongReferenceType() throws DatabindingFailedException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("not an instance of VFeaturePathDomainModelReference"); //$NON-NLS-1$

		converter.convertToListProperty(mock(VDomainModelReference.class), validEObject);
	}

	@Test
	public void testGetSetting() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the attribute
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.eINSTANCE.getA_B());
		referencePath.add(TestPackage.eINSTANCE.getB_C());
		referencePath.add(TestPackage.eINSTANCE.getC_D());

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_X();

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(feature);

		final A a = TestFactory.eINSTANCE.createA();
		final B b = TestFactory.eINSTANCE.createB();
		final C c = TestFactory.eINSTANCE.createC();
		final D d = TestFactory.eINSTANCE.createD();

		final String expected = "My Value"; //$NON-NLS-1$

		a.setB(b);
		b.setC(c);
		c.setD(d);
		d.setX(expected);

		final Setting setting = converter.getSetting(pathReference, a);

		// Check value.
		assertEquals(expected, setting.get(true));
	}

	@Test
	public void testGetSettingNoReferencePath() throws DatabindingFailedException {
		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();

		final EStructuralFeature feature = TestPackage.eINSTANCE.getD_X();
		pathReference.setDomainModelEFeature(feature);

		final D d = TestFactory.eINSTANCE.createD();
		final String expected = "My Value"; //$NON-NLS-1$
		d.setX(expected);

		final Setting setting = converter.getSetting(pathReference, d);

		// Check value.
		assertEquals(expected, setting.get(true));
	}

	@Test
	public void testGetSettingNoFeature() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("field domainModelEFeature"); //$NON-NLS-1$
		thrown.expectMessage("must not be null"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		converter.getSetting(pathReference, validEObject);
	}

	@Test
	public void testGetSettingNull() throws DatabindingFailedException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("must not be null"); //$NON-NLS-1$

		converter.getSetting(null, validEObject);
	}

	@Test
	public void testGetSettingWrongReferenceType() throws DatabindingFailedException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("not an instance of VFeaturePathDomainModelReference"); //$NON-NLS-1$

		converter.getSetting(mock(VDomainModelReference.class), validEObject);
	}

	@Test
	public void testGetSettingInvalidFeatureInPath() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("EClass D (test) has no such feature DExtended.a (test)"); //$NON-NLS-1$
		thrown.expectMessage(find(
			"The DMR is VFeaturePathDomainModelReferenceImpl@\\p{XDigit}+ \\(changeListener: null\\)<VIRTUAL_URI\\.xmi#theDMR>")); //$NON-NLS-1$
		thrown.expectMessage(
			find("resolved EObject is DImpl@\\p{XDigit}+ \\(x: null, yList: null\\)<VIRTUAL_URI\\.xmi#/>")); //$NON-NLS-1$

		final D d = (D) createValidEObject(TestPackage.Literals.D);
		final VFeaturePathDomainModelReference reference = createValidDMR();
		reference.getDomainModelEReferencePath().add(TestPackage.Literals.DEXTENDED__A);
		reference.setDomainModelEFeature(TestPackage.Literals.A__B);
		converter.getSetting(reference, d);
	}

	@Test
	public void testGetSettingUnresolvedFeature() throws DatabindingFailedException {
		final EPackage pkg = EcoreFactory.eINSTANCE.createEPackage();
		pkg.setName("gone"); //$NON-NLS-1$
		pkg.setNsPrefix("gone"); //$NON-NLS-1$
		pkg.setNsURI("http://www.eclipse.org/ecp/test/gone"); //$NON-NLS-1$
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("Gone"); //$NON-NLS-1$
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setName("ref"); //$NON-NLS-1$
		ref.setEType(eClass);
		eClass.getEStructuralFeatures().add(ref);
		pkg.getEClassifiers().add(eClass);
		final Resource resource = new EcoreResourceFactoryImpl().createResource(URI.createURI(pkg.getNsURI()));
		resource.getContents().add(pkg);

		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("domainModelEFeature Gone.ref (http://www.eclipse.org/ecp/test/gone)"); //$NON-NLS-1$
		thrown.expectMessage(find(
			"DMR VFeaturePathDomainModelReferenceImpl@\\p{XDigit}+ \\(changeListener: null\\)<VIRTUAL_URI\\.xmi#theDMR>")); //$NON-NLS-1$
		thrown.expectMessage("is a proxy"); //$NON-NLS-1$

		final D d = (D) createValidEObject(TestPackage.Literals.D);
		final VFeaturePathDomainModelReference reference = createValidDMR();
		reference.getDomainModelEReferencePath().add(TestPackage.Literals.DEXTENDED__A);
		reference.setDomainModelEFeature(ref);

		// Unload the package to proxify everything
		resource.unload();

		converter.getSetting(reference, d);
	}

	@Test
	public void testGetSettingUnresolvedReferenceInPath() throws DatabindingFailedException {
		final EPackage pkg = EcoreFactory.eINSTANCE.createEPackage();
		pkg.setName("gone"); //$NON-NLS-1$
		pkg.setNsPrefix("gone"); //$NON-NLS-1$
		pkg.setNsURI("http://www.eclipse.org/ecp/test/gone"); //$NON-NLS-1$
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("Gone"); //$NON-NLS-1$
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setName("ref"); //$NON-NLS-1$
		ref.setEType(eClass);
		eClass.getEStructuralFeatures().add(ref);
		pkg.getEClassifiers().add(eClass);
		final Resource resource = new EcoreResourceFactoryImpl().createResource(URI.createURI(pkg.getNsURI()));
		resource.getContents().add(pkg);

		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("path reference Gone.ref (http://www.eclipse.org/ecp/test/gone)"); //$NON-NLS-1$
		thrown.expectMessage(find(
			"DMR VFeaturePathDomainModelReferenceImpl@\\p{XDigit}+ \\(changeListener: null\\)<VIRTUAL_URI\\.xmi#theDMR>")); //$NON-NLS-1$
		thrown.expectMessage("is a proxy"); //$NON-NLS-1$

		final D d = (D) createValidEObject(TestPackage.Literals.D);
		final VFeaturePathDomainModelReference reference = createValidDMR();
		reference.getDomainModelEReferencePath().add(ref);
		reference.setDomainModelEFeature(TestPackage.Literals.A__B);

		// Unload the package to proxify everything
		resource.unload();

		converter.getSetting(reference, d);
	}

	@Test
	public void testGetSettingListReferenceInPath() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("not a single reference: B.cList (test)"); //$NON-NLS-1$
		thrown.expectMessage("DMR is VFeaturePathDomainModelReferenceImpl@"); //$NON-NLS-1$
		thrown.expectMessage(
			find("resolved EObject is BImpl@\\p{XDigit}+<VIRTUAL_URI\\.xmi#//@b>")); //$NON-NLS-1$

		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the attribute
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.Literals.A__B);
		referencePath.add(TestPackage.Literals.B__CLIST);
		referencePath.add(TestPackage.Literals.C__D);

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(TestPackage.Literals.D__X);

		final A a = (A) createValidEObject(TestPackage.Literals.A);
		final B b = TestFactory.eINSTANCE.createB();
		a.setB(b);
		converter.getSetting(pathReference, a);
	}

	@Test
	public void testGetSettingMissingObjectInPath() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("DMR is VFeaturePathDomainModelReferenceImpl@"); //$NON-NLS-1$
		thrown.expectMessage(
			find("resolved EObject is BImpl@\\p{XDigit}+<VIRTUAL_URI\\.xmi#//@b>")); //$NON-NLS-1$
		thrown.expectMessage("Reference being resolved is B.c (test)"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference pathReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		// create reference path to the attribute
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		referencePath.add(TestPackage.Literals.A__B);
		referencePath.add(TestPackage.Literals.B__C);
		referencePath.add(TestPackage.Literals.C__D);

		pathReference.getDomainModelEReferencePath().addAll(referencePath);
		pathReference.setDomainModelEFeature(TestPackage.Literals.D__X);

		final A a = (A) createValidEObject(TestPackage.Literals.A);
		final B b = TestFactory.eINSTANCE.createB();
		a.setB(b);
		converter.getSetting(pathReference, a);
	}

	@Test
	public void testGetSettingFeatureHasNoType() throws DatabindingFailedException {
		final EPackage pkg = EcoreFactory.eINSTANCE.createEPackage();
		pkg.setName("untyped"); //$NON-NLS-1$
		pkg.setNsPrefix("ut"); //$NON-NLS-1$
		pkg.setNsURI("http://www.eclipse.org/ecp/test/untyped"); //$NON-NLS-1$
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("Untyped"); //$NON-NLS-1$
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setName("untyped"); //$NON-NLS-1$
		// Don't set a type (that's the point)
		eClass.getEStructuralFeatures().add(ref);
		pkg.getEClassifiers().add(eClass);
		final Resource resource = new EcoreResourceFactoryImpl().createResource(URI.createURI(pkg.getNsURI()));
		resource.getContents().add(pkg);

		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage("in DMR VFeaturePathDomainModelReferenceImpl@"); //$NON-NLS-1$
		thrown.expectMessage("eType of the feature Untyped.untyped (http://www.eclipse.org/ecp/test/untyped) is null"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference dmr = createValidDMR();
		dmr.setDomainModelEFeature(ref);

		final EObject untyped = createValidEObject(eClass);
		converter.getSetting(dmr, untyped);
	}

	@Test
	public void testGetSettingObjectDoesNotHaveFeature() throws DatabindingFailedException {
		thrown.expect(DatabindingFailedException.class);
		thrown.expectMessage(find("resolved EObject BImpl@\\p{XDigit}+<VIRTUAL_URI.xmi#/>")); //$NON-NLS-1$
		thrown.expectMessage("doesn't have the feature C.d (test)"); //$NON-NLS-1$

		final VFeaturePathDomainModelReference dmr = createValidDMR();
		dmr.setDomainModelEFeature(TestPackage.Literals.C__D);

		final EObject b = createValidEObject(TestPackage.Literals.B);
		converter.getSetting(dmr, b);
	}

}
