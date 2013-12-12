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
import org.eclipse.emf.ecp.view.internal.rule.RuleService;
import org.eclipse.emf.ecp.view.internal.rule.RuleServiceHelperImpl;
import org.eclipse.emf.ecp.view.internal.unset.UnsetService;
import org.eclipse.emf.ecp.view.rule.test.CommonRuleTest;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Merchandise;
import org.eclipse.emf.emfstore.bowling.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jfaltermeier
 * 
 */
public class UnsetRuleIntegrationTest extends CommonRuleTest {

	private Fan fan;
	private Merchandise merchandise;

	private final EStructuralFeature merchandiseNameFeature = BowlingPackage.eINSTANCE.getMerchandise_Name();
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

	@Test
	public void testPriorities() {
		final RuleService rule = new RuleService();
		final UnsetService unset = new UnsetService();
		assertTrue(rule.getPriority() < unset.getPriority());
	}

	@Test
	public void testUnset() {
		final VControl control1 = addControlToView(merchandiseNameReferenceFromFan());
		addShowRule(control1, true, BowlingPackage.eINSTANCE.getFan_Name(), "foo");

		fan.setName("foo");
		merchandise.setName("bar");

		services(fan);

		fan.setName("quux");
		assertFalse(control1.isVisible());
		assertEquals(merchandiseNameFeature.getDefaultValue(), merchandise.getName());
	}

	@Test
	public void testMultiUnset() {
		final League league = BowlingFactory.eINSTANCE.createLeague();
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		league.getPlayers().add(player);

		view.setRootEClass(BowlingPackage.eINSTANCE.getLeague());

		final VControl control = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Players());
		control.setDomainModelReference(domainModelReference);

		view.getChildren().add(control);

		addShowRule(control, true, BowlingPackage.eINSTANCE.getLeague_Name(), "League");
		league.setName("League");
		services(league);
		assertEquals(1, league.getPlayers().size());
		league.setName("Liga");
		assertEquals(0, league.getPlayers().size());
	}

	@Test
	public void testInitUnset() {
		final VControl control1 = addControlToView(merchandiseNameReferenceFromFan());
		addShowRule(control1, true, BowlingPackage.eINSTANCE.getFan_Name(), "foo");

		merchandise.setName("bar");
		fan.setName("quux");
		services(fan);
		assertFalse(control1.isVisible());
		assertEquals(merchandiseNameFeature.getDefaultValue(), merchandise.getName());
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Factory methods
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void services(EObject domain) {
		context = ViewModelContextFactory.INSTANCE.createViewModelContext(view, domain);

		final UnsetService unsetService = new UnsetService();
		unsetService.instantiate(context);

		final RuleService ruleService = new RuleService();
		final RuleServiceHelperImpl ruleServiceHelper = new RuleServiceHelperImpl();
		ruleService.instantiate(context);
		ruleServiceHelper.instantiate(context);
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

	private VFeaturePathDomainModelReference merchandiseNameReferenceFromFan() {
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getMerchandise_Name());
		domainModelReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());
		return domainModelReference;
	}

}
