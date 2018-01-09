/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.rule.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.internal.rule.ConditionServiceManager;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.verification.VerificationMode;

/**
 * Framework for condition tests.
 *
 * @author Christian W. Damus
 */
public abstract class AbstractConditionTest<C extends Condition> {

	private static ReportServiceConsumer reportConsumer;
	private static ViewModelContext context;
	private static ConditionServiceManager manager;

	private C fixture;

	private EPackage model;

	/**
	 * Initializes me.
	 */
	public AbstractConditionTest() {
		super();
	}

	//
	// Test framework
	//

	@BeforeClass
	public static void getServices() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final EObject model = EcoreFactory.eINSTANCE.createEObject();

		context = ViewModelContextFactory.INSTANCE.createViewModelContext(view, model);
		manager = context.getService(ConditionServiceManager.class);

		reportConsumer = mock(ReportServiceConsumer.class);
		context.getService(ReportService.class).addConsumer(reportConsumer);
	}

	@Before
	public void createFixture() {
		fixture = createCondition();
		model = createModel();
	}

	@After
	public void destroyFixture() {
		model = null;
		fixture = null;

		verifyNoMoreInteractions(reportConsumer);
	}

	protected void assertReports(VerificationMode assertion) {
		verify(reportConsumer, assertion).reported(any(AbstractReport.class));
	}

	@AfterClass
	public static void ungetServices() {
		context.getService(ReportService.class).removeConsumer(reportConsumer);

		manager = null;

		context.dispose();
		context = null;
	}

	protected abstract C createCondition();

	protected final C getFixture() {
		return fixture;
	}

	protected EPackage createModel() {
		return EcoreFactory.eINSTANCE.createEPackage();
	}

	protected final EPackage getModel() {
		return model;
	}

	protected Set<UniqueSetting> getConditionSettings(EObject domainModel) {
		return getConditionSettings(getFixture(), domainModel);
	}

	protected Set<UniqueSetting> getConditionSettings(Condition condition, EObject domainModel) {
		return manager.getConditionSettings(condition, domainModel);
	}

	protected boolean evaluate(EObject domainModel) {
		return evaluate(getFixture(), domainModel);
	}

	protected boolean evaluate(Condition condition, EObject domainModel) {
		return manager.evaluate(condition, domainModel);
	}

	protected boolean evaluateChangedValues(EObject domainModel, Map<Setting, Object> possibleNewValues) {
		return evaluateChangedValues(getFixture(), domainModel, possibleNewValues);
	}

	protected boolean evaluateChangedValues(Condition condition, EObject domainModel,
		Map<Setting, Object> possibleNewValues) {
		return manager.evaluateChangedValues(condition, domainModel, possibleNewValues);
	}

	protected Set<VDomainModelReference> getDomainModelReferences() {
		return getDomainModelReferences(getFixture());
	}

	protected Set<VDomainModelReference> getDomainModelReferences(Condition condition) {
		return manager.getDomainModelReferences(condition);
	}

	protected EStructuralFeature.Setting nameSetting(ENamedElement element) {
		return ((InternalEObject) element).eSetting(EcorePackage.Literals.ENAMED_ELEMENT__NAME);
	}
}
