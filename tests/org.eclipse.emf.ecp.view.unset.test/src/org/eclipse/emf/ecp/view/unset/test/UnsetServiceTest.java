/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.unset.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.unset.UnsetService;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationFactory;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumn;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jfaltermeier
 * 
 */
public class UnsetServiceTest {

	private Fan fan;
	private Merchandise merchandise;

	private final EStructuralFeature merchandisePriceFeature = BowlingPackage.eINSTANCE.getMerchandise_Price();
	private final EStructuralFeature merchandiseNameFeature = BowlingPackage.eINSTANCE.getMerchandise_Name();
	private final EStructuralFeature fanNameFeature = BowlingPackage.eINSTANCE.getFan_Name();
	private final BigDecimal price = new BigDecimal(19.84);
	private final String mercName = "Wimpel";
	private final String fanName = "Max Morlock";

	private VView view;

	private ViewModelContext context;

	@Before
	public void before() {
		fan = BowlingFactory.eINSTANCE.createFan();
		merchandise = BowlingFactory.eINSTANCE.createMerchandise();
		merchandise.setPrice(price);
		merchandise.setName(mercName);
		fan.setFavouriteMerchandise(merchandise);
		fan.setName(fanName);

		view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(fan.eClass());
	}

	@After
	public void after() {
		if (context != null) {
			context.dispose();
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Public methods
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testInstantiate() {
		final UnsetService unsetService = new UnsetService();
		final ViewModelContextStub contextStub = new ViewModelContextStub();
		unsetService.instantiate(contextStub);
		assertTrue(contextStub.hasRegisteredViewListener);
		assertFalse(contextStub.hasRegisteredDomainListener);
	}

	@Test(expected = IllegalStateException.class)
	public void testInstantiateWithNullDomainModel() {
		final UnsetService unsetService = new UnsetService();
		final ViewModelContextStub contextStub = new ViewModelContextStub() {
			@Override
			public EObject getDomainModel() {
				return null;
			}
		};
		unsetService.instantiate(contextStub);
	}

	@Test(expected = IllegalStateException.class)
	public void testInstantiateWithNullViewModel() {
		final UnsetService unsetService = new UnsetService();
		final ViewModelContextStub contextStub = new ViewModelContextStub() {
			@Override
			public VElement getViewModel() {
				return null;
			}
		};
		unsetService.instantiate(contextStub);
	}

	@Test
	public void testDispose() {
		final UnsetService unsetService = new UnsetService();
		final ViewModelContextStub contextStub = new ViewModelContextStub();
		unsetService.instantiate(contextStub);
		assertTrue(contextStub.hasRegisteredViewListener);
		assertFalse(contextStub.hasRegisteredDomainListener);
		unsetService.dispose();
		assertFalse(contextStub.hasRegisteredViewListener);
		assertFalse(contextStub.hasRegisteredDomainListener);
	}

	@Test
	public void testGetPriority() {
		assertEquals(5, unsetService().getPriority());
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Init
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testInitSingleControlInViewAllVisible() {
		addControlToView(merchandisePriceReferenceFromFan());
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitSingleControlInViewWithHiddenControl() {
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitSingleControlInViewWithHiddenView() {
		addControlToView(merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitSingleControlInContainerAllVisible() {
		addControlToContainer(addVerticalLayoutToView(), merchandisePriceReferenceFromFan());
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitSingleControlInContainerWithHiddenControl() {
		addControlToContainer(addVerticalLayoutToView(), merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitSingleControlInContainerWithHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitSingleControlInContainerWithHiddenView() {
		addControlToContainer(addVerticalLayoutToView(), merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInViewAllVisible() {
		addControlToView(merchandisePriceReferenceFromFan());
		addControlToView(merchandisePriceReferenceFromFan());
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInViewWithOneHiddenControl() {
		addControlToView(merchandisePriceReferenceFromFan());
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInViewWithTwoHiddenControls() {
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInViewWithHiddenView() {
		addControlToView(merchandisePriceReferenceFromFan());
		addControlToView(merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInOneContainerAllVisible() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInOneContainerWithOneHiddenControl() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInOneContainerWithTwoHiddenControls() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInOneContainerWithHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInOneContainerWithHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInTwoContainersAllVisible() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInTwoContainersWithOneHiddenControl() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInTwoContainersWithOneHiddenContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		unsetService();
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInTwoContainersWithTwoHiddenControls() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInTwoContainersWithTwoHiddenContainers() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		container2.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitTwoControlsInTwoContainersWithHiddenView() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Dynamic
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testChangeSingleControlInViewAllVisibleToHiddenControl() {
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInViewAllVisibleToHiddenView() {
		addControlToView(merchandisePriceReferenceFromFan());
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInViewWithHiddenControlToVisibleControl() {
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);
		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInViewWithHiddenControlToHiddenView() {
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInViewWithHiddenViewToHiddenControl() {
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInViewWithHiddenViewToVisibleView() {
		addControlToView(merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		view.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerAllVisibleToHiddenControl() {
		final VControl control = addControlToContainer(addVerticalLayoutToView(), merchandisePriceReferenceFromFan());
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerAllVisibleToHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		unsetService();
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerAllVisibleToHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenControlToHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenControlToHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenControlToVisibleControl() {
		final VContainer container = addVerticalLayoutToView();
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenContainerToHiddenControl() {
		final VContainer container = addVerticalLayoutToView();
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenContainerToHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenContainerToVisibleContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		container.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenViewToHiddenControl() {
		final VControl control = addControlToContainer(addVerticalLayoutToView(), merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenViewToHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeSingleControlInContainerWithHiddenViewToVisibleView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		view.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewAllVisibleToOneHiddenControl() {
		addControlToView(merchandisePriceReferenceFromFan());
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		unsetService();
		control.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewAllVisibleToOneHiddenView() {
		addControlToView(merchandisePriceReferenceFromFan());
		addControlToView(merchandisePriceReferenceFromFan());
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewWithOneHiddenControlToTwoVisibleControls() {
		addControlToView(merchandisePriceReferenceFromFan());
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewWithOneHiddenControlToTwoHiddenControls() {
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewWithOneHiddenControlToHiddenView() {
		addControlToView(merchandisePriceReferenceFromFan());
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewWithTwoHiddenControlsToHiddenView() {
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewWithTwoHiddenControlsToOneVisibleControl() {
		addControlToView(merchandisePriceReferenceFromFan()).setVisible(false);
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewWithHiddenViewToHiddenControl() {
		addControlToView(merchandisePriceReferenceFromFan());
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInViewWithHiddenViewToVisibleView() {
		addControlToView(merchandisePriceReferenceFromFan());
		addControlToView(merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		view.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerAllVisibleToOneHiddenControl() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		unsetService();
		control.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerAllVisibleToHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		unsetService();
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerAllVisibleToHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithOneHiddenControlToTwoVisibleControls() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithOneHiddenControlToTwoHiddenControls() {
		final VContainer container = addVerticalLayoutToView();
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithOneHiddenControlToHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithOneHiddenControlToHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithTwoHiddenControlsToHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithTwoHiddenControlsToHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithTwoHiddenControlsToOneVisibleControl() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan()).setVisible(false);
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithHiddenContainerToOneHiddenControl() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithHiddenContainerToHiddenView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithHiddenContainerToVisibleContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		container.setVisible(false);
		unsetService();
		container.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithHiddenViewToOneHiddenControl() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithHiddenViewToHiddenContainer() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		container.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInOneContainerWithHiddenViewToVisibleView() {
		final VContainer container = addVerticalLayoutToView();
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		addControlToContainer(container, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		view.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersAllVisibleToOneHiddenControl() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container2, merchandisePriceReferenceFromFan());
		unsetService();
		control.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersAllVisibleToOneHiddenContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		unsetService();
		container1.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersAllVisibleToHiddenView() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenControlToTwoVisibleControls() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container2, merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenControlToHiddenContainerOfHiddenControl() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		container2.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenControlToTwoHiddenControls() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		final VControl control = addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenControlToHiddenContainerOfVisibleControl() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		container1.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenControlToHiddenView() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenContainerToHiddenControlInHiddenContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		final VControl control = addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenContainerToTwoVisibleContainers() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		unsetService();
		container1.setVisible(true);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenContainerToHiddenControlInVisibleContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenContainerToTwoHiddenContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		unsetService();
		container2.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithOneHiddenContainerToHiddenView() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithTwoHiddenControlsToOneHiddenContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		container1.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithTwoHiddenControlsToHiddenView() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToContainer(container2, merchandisePriceReferenceFromFan()).setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithTwoHiddenControlsToOneVisibleControl() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan()).setVisible(false);
		final VControl control = addControlToContainer(container2, merchandisePriceReferenceFromFan());
		control.setVisible(false);
		unsetService();
		control.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithTwoHiddenContainersToOneHiddenControl() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		container2.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithTwoHiddenContainersToHiddenView() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		container2.setVisible(false);
		unsetService();
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithTwoHiddenContainersToOneVisibleContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		container1.setVisible(false);
		container2.setVisible(false);
		unsetService();
		container1.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		container1.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithHiddenViewToOneHiddenControl() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		final VControl control = addControlToContainer(container2, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		control.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithHiddenViewToOneHiddenContainer() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		container1.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testChangeTwoControlsInTwoContainersWithHiddenViewToVisibleView() {
		final VContainer container1 = addVerticalLayoutToView();
		final VContainer container2 = addVerticalLayoutToView();
		addControlToContainer(container1, merchandisePriceReferenceFromFan());
		addControlToContainer(container2, merchandisePriceReferenceFromFan());
		view.setVisible(false);
		unsetService();
		view.setVisible(true);

		// we dont change the state of the domain model when shown again. if this changes assertions can be added here
		merchandise.setPrice(price);
		view.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// More specific test
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testChangeOtherViewFeatures() {
		final VControl control = addControlToView(merchandisePriceReferenceFromFan());
		unsetService();
		for (final EStructuralFeature feature : control.eClass().getEAllStructuralFeatures()) {
			if (feature == VViewPackage.eINSTANCE.getElement_Visible()) {
				continue;
			}
			if (!feature.isMany()) {
				control.eSet(feature, feature.getDefaultValue());
			}
		}
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
	}

	@Test
	public void testInitDifferentControls() {
		addControlToContainer(addVerticalLayoutToView(), merchandisePriceReferenceFromFan()).setVisible(false);
		addControlToContainer(addVerticalLayoutToView(), merchandiseNameReferenceFromFan());
		unsetService();
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(mercName, merchandise.getName());
		assertTrue(merchandise.eIsSet(merchandiseNameFeature));
	}

	@Test
	public void testChangeDifferentControls() {
		final VControl mercControl = addControlToContainer(addVerticalLayoutToView(),
			merchandisePriceReferenceFromFan());
		final VControl fanControl = addControlToContainer(addVerticalLayoutToView(), fanNameReference());
		unsetService();
		mercControl.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(fanName, fan.getName());
		assertTrue(fan.eIsSet(fanNameFeature));
		fanControl.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(fanNameFeature.getDefaultValue(), fan.getName());
		assertFalse(fan.eIsSet(fanNameFeature));
	}

	@Test
	public void testComplexCategorization() {
		final VCategorizationElement categorizationElement = VCategorizationFactory.eINSTANCE
			.createCategorizationElement();

		final VCategorization categorization1 = VCategorizationFactory.eINSTANCE.createCategorization();
		final VCategorization categorization1a = VCategorizationFactory.eINSTANCE.createCategorization();
		final VCategorization categorization2 = VCategorizationFactory.eINSTANCE.createCategorization();

		final VCategory category11 = VCategorizationFactory.eINSTANCE.createCategory();
		final VControl control11 = addControlToCategory(category11, merchandisePriceReferenceFromFan());
		final VCategory category12 = VCategorizationFactory.eINSTANCE.createCategory();
		final VControl control12 = addControlToCategory(category12, merchandisePriceReferenceFromFan());

		final VCategory category1a1 = VCategorizationFactory.eINSTANCE.createCategory();
		addControlToCategory(category1a1, merchandisePriceReferenceFromFan());
		final VCategory category1a2 = VCategorizationFactory.eINSTANCE.createCategory();
		final VControl control1a2 = addControlToCategory(category1a2, merchandiseNameReferenceFromFan());

		final VCategory category21 = VCategorizationFactory.eINSTANCE.createCategory();
		addControlToCategory(category21, merchandisePriceReferenceFromFan());
		final VCategory category22 = VCategorizationFactory.eINSTANCE.createCategory();
		addControlToCategory(category22, fanNameReference());

		categorization1.getCategorizations().add(category11);
		categorization1.getCategorizations().add(category12);

		categorization1a.getCategorizations().add(category1a1);
		categorization1a.getCategorizations().add(category1a2);

		categorization2.getCategorizations().add(category21);
		categorization2.getCategorizations().add(category22);

		categorization1.getCategorizations().add(categorization1a);
		categorizationElement.getCategorizations().add(categorization1);
		categorizationElement.getCategorizations().add(categorization2);
		view.getChildren().add(categorizationElement);

		unsetService();

		doLogicForComplexCategorizationTest(categorization2, control11, control12, category1a1, control1a2);

	}

	private void doLogicForComplexCategorizationTest(final VCategorization categorization2,
		final VControl control11, final VControl control12, final VCategory category1a1, final VControl control1a2) {

		control11.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(mercName, merchandise.getName());
		assertTrue(merchandise.eIsSet(merchandiseNameFeature));
		assertEquals(fanName, fan.getName());
		assertTrue(fan.eIsSet(fanNameFeature));

		control1a2.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(merchandiseNameFeature.getDefaultValue(), merchandise.getName());
		assertFalse(merchandise.eIsSet(merchandiseNameFeature));
		assertEquals(fanName, fan.getName());
		assertTrue(fan.eIsSet(fanNameFeature));

		categorization2.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(merchandiseNameFeature.getDefaultValue(), merchandise.getName());
		assertFalse(merchandise.eIsSet(merchandiseNameFeature));
		assertEquals(fanNameFeature.getDefaultValue(), fan.getName());
		assertFalse(fan.eIsSet(fanNameFeature));

		category1a1.setVisible(false);
		assertEquals(price, merchandise.getPrice());
		assertTrue(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(merchandiseNameFeature.getDefaultValue(), merchandise.getName());
		assertFalse(merchandise.eIsSet(merchandiseNameFeature));
		assertEquals(fanNameFeature.getDefaultValue(), fan.getName());
		assertFalse(fan.eIsSet(fanNameFeature));

		control12.setVisible(false);
		assertEquals(merchandisePriceFeature.getDefaultValue(), merchandise.getPrice());
		assertFalse(merchandise.eIsSet(merchandisePriceFeature));
		assertEquals(merchandiseNameFeature.getDefaultValue(), merchandise.getName());
		assertFalse(merchandise.eIsSet(merchandiseNameFeature));
		assertEquals(fanNameFeature.getDefaultValue(), fan.getName());
		assertFalse(fan.eIsSet(fanNameFeature));
	}

	/**
	 * Test if exception occurs when control has no setting.
	 */
	@Test
	public void testControlWithoutSetting() {
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		final VControl control = addControlToView(domainModelReference);
		assertFalse(control.getDomainModelReference().getIterator().hasNext());
		unsetService();
	}

	@Test
	public void testTable() {
		final Merchandise merc = BowlingFactory.eINSTANCE.createMerchandise();
		merc.setName("Foo");
		fan.getFanMerchandise().add(merc);

		final VTableControl table = VTableFactory.eINSTANCE.createTableControl();
		final VTableDomainModelReference tableDomainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		tableDomainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getFan_FanMerchandise());
		table.setDomainModelReference(tableDomainModelReference);

		final VTableColumn nameCol = VTableFactory.eINSTANCE.createTableColumn();
		nameCol.setAttribute(BowlingPackage.eINSTANCE.getMerchandise_Name());
		final VTableColumn priceCol = VTableFactory.eINSTANCE.createTableColumn();
		priceCol.setAttribute(BowlingPackage.eINSTANCE.getMerchandise_Price());
		table.getColumns().add(nameCol);
		table.getColumns().add(priceCol);

		view.getChildren().add(table);

		unsetService();
		assertEquals(1, fan.getFanMerchandise().size());
		assertEquals(merc, fan.getFanMerchandise().get(0));
		assertTrue(fan.eIsSet(BowlingPackage.eINSTANCE.getFan_FanMerchandise()));

		table.setVisible(false);

		assertEquals(0, fan.getFanMerchandise().size());
		assertFalse(fan.eIsSet(BowlingPackage.eINSTANCE.getFan_FanMerchandise()));
		assertEquals("Foo", merc.getName());
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Factory methods
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates the unset service.
	 * 
	 * @return
	 */
	private UnsetService unsetService() {
		final UnsetService unsetService = new UnsetService();
		context = ViewModelContextFactory.INSTANCE.createViewModelContext(view, fan);
		unsetService.instantiate(context);
		return unsetService;
	}

	/**
	 * Adds a control with the given feature path domain model reference as a direct child of the view.
	 * 
	 * @param domainModelReference
	 * @return the created control
	 */
	private VControl addControlToView(VFeaturePathDomainModelReference domainModelReference) {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(domainModelReference);
		view.getChildren().add(control);
		return control;
	}

	/**
	 * Adds a control with the given feature path domain model reference as a direct child of the container.
	 * 
	 * @param domainModelReference
	 * @return the created control
	 */
	private VControl addControlToContainer(VContainer container, VFeaturePathDomainModelReference domainModelReference) {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(domainModelReference);
		container.getChildren().add(control);
		return control;
	}

	private VControl addControlToCategory(VCategory category, VFeaturePathDomainModelReference domainModelReference) {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		control.setDomainModelReference(domainModelReference);
		category.setComposite(control);
		return control;
	}

	/**
	 * Adds a vertical layout as a direct child of the view.
	 * 
	 * @return the created layout
	 */
	private VVerticalLayout addVerticalLayoutToView() {
		final VVerticalLayout layout = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(layout);
		return layout;
	}

	/**
	 * References the player name from a league object.
	 * 
	 * @return
	 */
	private VFeaturePathDomainModelReference merchandisePriceReferenceFromFan() {
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Price());
		domainModelReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());
		return domainModelReference;
	}

	private VFeaturePathDomainModelReference merchandiseNameReferenceFromFan() {
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		domainModelReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());
		return domainModelReference;
	}

	private VFeaturePathDomainModelReference fanNameReference() {
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getFan_Name());
		return domainModelReference;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Helper- & stub classes
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @author Jonas
	 * 
	 */
	private class ViewModelContextStub implements ViewModelContext {

		private boolean hasRegisteredViewListener;
		private boolean hasRegisteredDomainListener;

		public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
			hasRegisteredViewListener = false;
		}

		public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {
			hasRegisteredDomainListener = false;
		}

		public void registerViewChangeListener(ModelChangeListener modelChangeListener) {
			hasRegisteredViewListener = true;
		}

		public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {
			hasRegisteredDomainListener = true;
		}

		public VElement getViewModel() {
			return view;
		}

		public EObject getDomainModel() {
			return fan;
		}

		public void dispose() {
		}

		public <T> boolean hasService(Class<T> serviceType) {
			return false;
		}

		public <T> T getService(Class<T> serviceType) {
			return null;
		}
	}

}
