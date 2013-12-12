/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.rule.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.rule.RuleService;
import org.eclipse.emf.ecp.view.internal.rule.RuleServiceHelperImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.EnableRule;
import org.eclipse.emf.ecp.view.spi.rule.model.ShowRule;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.ecp.view.test.common.GCCollectable;
import org.eclipse.emf.ecp.view.test.common.Tuple;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.junit.After;
import org.junit.Test;

public class RuleServiceGCTest extends CommonRuleTest {

	private ViewModelContext context;

	private Tuple<VView, League> createView() {
		final Player player = BowlingFactory.eINSTANCE.createPlayer();
		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.getPlayers().add(player);

		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(league.eClass());

		final VVerticalLayout parentColumn = VVerticalFactory.eINSTANCE.createVerticalLayout();
		view.getChildren().add(parentColumn);

		final VVerticalLayout column = VVerticalFactory.eINSTANCE.createVerticalLayout();
		parentColumn.getChildren().add(column);

		final VControl controlPName = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
		domainModelReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getLeague_Players());
		controlPName.setDomainModelReference(domainModelReference);

		column.getChildren().add(controlPName);
		return Tuple.create(view, league);
	}

	/**
	 * Tear down.
	 * 
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
		if (context != null) {
			context.dispose();
		}
	}

	/**
	 * Instantiate rule service.
	 * 
	 * @return the rule service
	 */
	private RuleService instantiateRuleService(VView view, final EObject domainModel) {
		final RuleService ruleService = new RuleService();
		final RuleServiceHelperImpl ruleServiceHelper = new RuleServiceHelperImpl();
		context = ViewModelContextFactory.INSTANCE.createViewModelContext(view, domainModel);
		ruleService.instantiate(context);
		ruleServiceHelper.instantiate(context);
		return ruleService;
	}

	@Test
	public void testRemoveShowRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		league.setName("foo");
		addShowRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final GCCollectable collectable = new GCCollectable(
			view.getChildren().get(0).getAttachments().get(0));
		instantiateRuleService(view, league);
		view.getChildren().get(0).getAttachments().remove(0);

		assertTrue(collectable.isCollectable());
		assertTrue(view.getChildren().get(0).isVisible());
	}

	@Test
	public void testRemoveShowRuleWithoutCondition() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addShowRule(view.getChildren().get(0), false);
		final GCCollectable collectable = new GCCollectable(
			view.getChildren().get(0).getAttachments().get(0));
		instantiateRuleService(view, league);
		view.getChildren().get(0).getAttachments().remove(0);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveEnableRuleWithoutCondition() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addEnableRule(view.getChildren().get(0), false);
		final GCCollectable collectable = new GCCollectable(
			view.getChildren().get(0).getAttachments().get(0));
		instantiateRuleService(view, league);
		view.getChildren().get(0).getAttachments().remove(0);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveEnableRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		league.setName("foo");
		addEnableRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final GCCollectable collectable = new GCCollectable(
			view.getChildren().get(0).getAttachments().get(0));
		instantiateRuleService(view, league);
		view.getChildren().get(0).getAttachments().remove(0);

		assertTrue(collectable.isCollectable());
		assertTrue(view.getChildren().get(0).isEnabled());
	}

	@Test
	public void testRemoveLeafConditionOfShowRuleReevaluate() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		league.setName("foo");
		addShowRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final GCCollectable collectable = new GCCollectable(
			((ShowRule) view.getChildren().get(0).getAttachments().get(0)).getCondition());
		instantiateRuleService(view, league);
		final ShowRule showRule = (ShowRule) view.getChildren().get(0).getAttachments().get(0);

		assertFalse(view.getChildren().get(0).isVisible());
		showRule.setCondition(null);
		assertTrue(collectable.isCollectable());
		assertTrue(view.getChildren().get(0).isVisible());
	}

	@Test
	public void testRemoveAndConditionOfShowRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addLeagueShowRuleWithAndCondition(view.getChildren().get(0), false,
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League"),
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League2"));

		final GCCollectable collectable = new GCCollectable(
			((ShowRule) view.getChildren().get(0).getAttachments().get(0)).getCondition());
		instantiateRuleService(view, league);
		final ShowRule showRule = (ShowRule) view.getChildren().get(0).getAttachments().get(0);
		showRule.setCondition(null);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveAndConditionOfEnableRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addLeagueEnableRuleWithAndCondition(view.getChildren().get(0), false,
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League"),
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League2"));

		final GCCollectable collectable = new GCCollectable(
			((EnableRule) view.getChildren().get(0).getAttachments().get(0)).getCondition());
		instantiateRuleService(view, league);
		final EnableRule enableRule = (EnableRule) view.getChildren().get(0).getAttachments().get(0);
		enableRule.setCondition(null);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveOrConditionOfEnableRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addLeagueShowRuleWithOrCondition(view.getChildren().get(0), false,
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League"),
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League2"));

		final GCCollectable collectable = new GCCollectable(
			((ShowRule) view.getChildren().get(0).getAttachments().get(0)).getCondition());
		instantiateRuleService(view, league);
		final ShowRule showRule = (ShowRule) view.getChildren().get(0).getAttachments().get(0);
		showRule.setCondition(null);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveLeafConditionOfEnableRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addEnableRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final EnableRule enableRule = (EnableRule) view.getChildren().get(0).getAttachments().get(0);
		final GCCollectable collectable = new GCCollectable(enableRule.getCondition());
		instantiateRuleService(view, league);
		enableRule.setCondition(null);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveLeafConditionOfShowRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addShowRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final ShowRule showRule = (ShowRule) view.getChildren().get(0).getAttachments().get(0);
		final GCCollectable collectable = new GCCollectable(showRule.getCondition());
		instantiateRuleService(view, league);
		showRule.setCondition(null);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveLeafConditionOfEnableRuleReevaluate() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		league.setName("foo");
		addEnableRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final GCCollectable collectable = new GCCollectable(
			((EnableRule) view.getChildren().get(0).getAttachments().get(0)).getCondition());
		instantiateRuleService(view, league);
		final EnableRule enableRule = (EnableRule) view.getChildren().get(0).getAttachments().get(0);

		assertFalse(view.getChildren().get(0).isEnabled());
		enableRule.setCondition(null);
		assertTrue(collectable.isCollectable());
		assertTrue(view.getChildren().get(0).isEnabled());
	}

	@Test
	public void testRemoveOrConditionOfShowRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();

		addLeagueShowRuleWithOrCondition(view.getChildren().get(0), false,
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League"),
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League2"));

		final GCCollectable collectable = new GCCollectable(
			((ShowRule) view.getChildren().get(0).getAttachments().get(0)).getCondition());
		instantiateRuleService(view, league);
		final ShowRule showRule = (ShowRule) view.getChildren().get(0).getAttachments().get(0);
		showRule.setCondition(null);
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveOrConditionBOfEnableRuleReevaluate() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		league.setName("foo");

		addLeagueEnableRuleWithOrCondition(view.getChildren().get(0), false,
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "foo"),
			createLeafCondition(BowlingPackage.eINSTANCE.getLeague_Name(), "League2"));

		final GCCollectable collectable = new GCCollectable(
			EnableRule.class.cast(view.getChildren().get(0).getAttachments().get(0)).getCondition());
		final GCCollectable ruleCollectable = new GCCollectable(
			view.getChildren().get(0).getAttachments().get(0));

		instantiateRuleService(view, league);
		final EnableRule enableRule = (EnableRule) view.getChildren().get(0).getAttachments().get(0);

		assertFalse(view.getChildren().get(0).isEnabled());
		enableRule.setCondition(null);
		assertTrue(collectable.isCollectable());
		assertFalse(ruleCollectable.isCollectable());
		assertTrue(view.getChildren().get(0).isEnabled());
	}

	@Test
	public void testRemoveRenderableWithShowRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addShowRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final GCCollectable collectable = new GCCollectable(
			view.getChildren().get(0).getAttachments().get(0));
		instantiateRuleService(view, league);
		view.getChildren().remove(view.getChildren().get(0));
		assertTrue(collectable.isCollectable());
	}

	@Test
	public void testRemoveRenderableWithEnableRule() {
		final Tuple<VView, League> tuple = createView();
		final VView view = tuple.first();
		final League league = tuple.second();
		addEnableRule(view.getChildren().get(0), false, BowlingPackage.eINSTANCE.getLeague_Name(), "foo");
		final GCCollectable collectable = new GCCollectable(
			view.getChildren().get(0).getAttachments().get(0));
		instantiateRuleService(view, league);
		view.getChildren().remove(view.getChildren().get(0));
		assertTrue(collectable.isCollectable());
	}

}
