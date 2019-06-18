/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 * Christian W. Damus - bug 533522
 ******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.validation.ValidationProvider;
import org.eclipse.emf.ecp.view.spi.validation.ValidationService;
import org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContainer;
import org.eclipse.emf.ecp.view.validation.test.model.CrossReferenceContent;
import org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild;
import org.eclipse.emf.ecp.view.validation.test.model.TestFactory;
import org.eclipse.emf.ecp.view.validation.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Stefan Dirix
 *
 */
@SuppressWarnings("restriction")
public class ValidationService_PTest {

	private DefaultRealm defaultRealm;

	private VControl control;
	private ValidationService validationService;

	private CrossReferenceContainer container;
	private CrossReferenceContent content;

	private CrossReferenceContainer otherContainer;

	private BundleContext bundleContext;

	private ServiceReference<ReportService> reportServiceReference;

	private ReportService reportService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		defaultRealm = new DefaultRealm();

		bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		reportServiceReference = bundleContext.getServiceReference(ReportService.class);
		reportService = bundleContext.getService(reportServiceReference);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		defaultRealm.dispose();
		bundleContext.ungetService(reportServiceReference);
	}

	private void setupContent() {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(TestPackage.eINSTANCE.getContent());

		control = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		domainModelReference.setDomainModelEFeature(TestPackage.eINSTANCE.getCrossReferenceContent_Parent());
		control.setDomainModelReference(domainModelReference);

		view.getChildren().add(control);

		container = TestFactory.eINSTANCE.createCrossReferenceContainer();
		otherContainer = TestFactory.eINSTANCE.createCrossReferenceContainer();
		content = TestFactory.eINSTANCE.createCrossReferenceContent();
		final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(view, content);
		validationService = vmc.getService(ValidationService.class);
	}

	/**
	 * There should be no validation on an unset crossreferenced element.
	 */
	@Test
	public void testNoValidationOnUnsetElements() {
		setupContent();
		container.setSingleContent(content);
		final List<Boolean> called = new ArrayList<Boolean>(1);
		called.add(false);
		validationService.addValidationProvider(new ValidationProvider() {
			@Override
			public List<Diagnostic> validate(EObject eObject) {
				if (content == eObject) {
					called.set(0, true);
				}
				return Collections.emptyList();
			}
		});
		called.set(0, false);
		container.setSingleContent(null);

		assertFalse(called.get(0));
	}

	/**
	 * There should be a validation on a set crossreferenced element.
	 */
	@Test
	public void testValidationOnSetElements() {
		setupContent();
		container.setSingleContent(content);
		final List<Boolean> called = new ArrayList<Boolean>(1);
		called.add(false);
		validationService.addValidationProvider(new ValidationProvider() {
			@Override
			public List<Diagnostic> validate(EObject eObject) {
				if (content == eObject) {
					called.set(0, true);
				}
				return Collections.emptyList();
			}
		});
		called.set(0, false);
		otherContainer.setSingleContent(content);

		assertTrue(called.get(0));
	}

	/**
	 * There should be no validation on a removed crossreferenced element.
	 */
	@Test
	public void testNoValidationOnRemovedElements() {
		setupContent();
		container.getContents().add(content);

		final List<Boolean> called = new ArrayList<Boolean>(1);
		called.add(false);
		validationService.addValidationProvider(new ValidationProvider() {
			@Override
			public List<Diagnostic> validate(EObject eObject) {
				if (content == eObject) {
					called.set(0, true);
				}
				return Collections.emptyList();
			}
		});
		called.set(0, false);
		container.getContents().remove(content);

		assertFalse(called.get(0));
	}

	/**
	 * There should be a validation on a moved crossreferenced element.
	 */
	@Test
	public void testValidationOnMovedElements() {
		setupContent();
		container.getContents().add(content);

		final List<Boolean> called = new ArrayList<Boolean>(1);
		called.add(false);
		validationService.addValidationProvider(new ValidationProvider() {
			@Override
			public List<Diagnostic> validate(EObject eObject) {
				if (content == eObject) {
					called.set(0, true);
				}
				return Collections.emptyList();
			}
		});
		called.set(0, false);
		otherContainer.getContents().add(content);

		assertTrue(called.get(0));
	}

	@Test
	public void testValidationTimeoutReport2000() {
		setupContent();
		container.getContents().add(content);

		final List<Boolean> called = new ArrayList<Boolean>(1);
		called.add(false);
		final ReportServiceConsumer reportServiceConsumer = new ReportServiceConsumer() {

			@Override
			public void reported(AbstractReport reportEntity) {
				assertTrue(reportEntity.getMessage().startsWith("Validation took longer than expected for"));
				called.set(0, true);
			}
		};
		reportService.addConsumer(reportServiceConsumer);
		validationService.addValidationProvider(new ValidationProvider() {
			@Override
			public List<Diagnostic> validate(EObject eObject) {
				try {
					Thread.sleep(3000);
				} catch (final InterruptedException ex) {
				}
				return Collections.emptyList();
			}
		});

		validationService.validate(Arrays.asList(content.eContainer()));
		assertTrue("Validation report missing", called.get(0));
		reportService.removeConsumer(reportServiceConsumer);
	}

	@Test
	public void testValidationTimeoutReport1000() {
		setupContent();
		container.getContents().add(content);

		final List<Boolean> called = new ArrayList<Boolean>(1);
		called.add(false);
		final ReportServiceConsumer reportServiceConsumer = new ReportServiceConsumer() {

			@Override
			public void reported(AbstractReport reportEntity) {
				assertTrue("Real Message:" + reportEntity.getMessage(),
					reportEntity.getMessage().startsWith("Validation took longer than expected for"));
				called.set(0, true);

			}
		};
		reportService.addConsumer(reportServiceConsumer);
		validationService.addValidationProvider(new ValidationProvider() {
			@Override
			public List<Diagnostic> validate(EObject eObject) {
				try {
					Thread.sleep(2000);
				} catch (final InterruptedException ex) {
				}
				return Collections.emptyList();
			}
		});

		validationService.validate(Arrays.asList(content.eContainer()));
		assertTrue("Validation report missing", called.get(0));
		reportService.removeConsumer(reportServiceConsumer);
	}

	@Test
	public void testValidationTimeoutReportNoDelay() {
		setupContent();
		container.getContents().add(content);

		final List<Boolean> called = new ArrayList<Boolean>(1);
		called.add(false);
		final ReportServiceConsumer reportServiceConsumer = new ReportServiceConsumer() {

			@Override
			public void reported(AbstractReport reportEntity) {
				assertTrue(reportEntity.getMessage().startsWith("Validation took longer than expected for"));
				called.set(0, true);

			}
		};
		reportService.addConsumer(reportServiceConsumer);
		validationService.addValidationProvider(new ValidationProvider() {
			@Override
			public List<Diagnostic> validate(EObject eObject) {
				return Collections.emptyList();
			}
		});

		validationService.validate(Arrays.asList(content.eContainer()));
		assertFalse("Validation report present", called.get(0));

		reportService.removeConsumer(reportServiceConsumer);
	}

	@Test
	public void testViewModelChangeListenerCutOffDMR() {
		/* setup domain */
		final TableContentWithInnerChild parent = TestFactory.eINSTANCE.createTableContentWithInnerChild();
		final TableContentWithInnerChild middle = TestFactory.eINSTANCE.createTableContentWithInnerChild();
		final TableContentWithInnerChild child = TestFactory.eINSTANCE.createTableContentWithInnerChild();
		parent.setInnerChild(middle);
		middle.setInnerChild(child);

		/* setup view */
		final VView view = VViewFactory.eINSTANCE.createView();

		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		view.getChildren().add(vControl);
		vControl.setDomainModelReference(
			TestPackage.eINSTANCE.getTableContentWithInnerChild_Stuff(),
			Arrays.asList(
				TestPackage.eINSTANCE.getTableContentWithInnerChild_InnerChild(),
				TestPackage.eINSTANCE.getTableContentWithInnerChild_InnerChild()));

		/* setup rendering */
		ViewModelContextFactory.INSTANCE.createViewModelContext(view, parent);

		/* act */
		parent.setInnerChild(null); /* cut off */
		vControl.setEnabled(false); /* produce view notifications */
		vControl.setEnabled(true);/* produce view notifications */

		/* assert */
		/* cutting of should not produce NPEs as this is legit */
	}

	@Test
	public void testBug529403controlAdded() {
		/* setup domain */
		final TableContentWithInnerChild parent = TestFactory.eINSTANCE.createTableContentWithInnerChild();
		final TableContentWithInnerChild middle = TestFactory.eINSTANCE.createTableContentWithInnerChild();
		final TableContentWithInnerChild child = TestFactory.eINSTANCE.createTableContentWithInnerChild();
		parent.setInnerChild(middle);
		middle.setInnerChild(child);

		/* setup view */
		final VView view = VViewFactory.eINSTANCE.createView();

		/* setup control to add */
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		vControl.setDomainModelReference(
			TestPackage.eINSTANCE.getTableContentWithInnerChild_Stuff(),
			Arrays.asList(
				TestPackage.eINSTANCE.getTableContentWithInnerChild_InnerChild(),
				TestPackage.eINSTANCE.getTableContentWithInnerChild_InnerChild()));

		/*
		 * construct a situation where the observed object is null. This is valid according to
		 * org.eclipse.core.databinding.observable.IObserving.getObserved() javadoc, so we have to prepare for this.
		 * Easiest way to achieve this for testing is to mock the mapping provider
		 */
		new ViewModelContextImpl(view, parent) {

			@SuppressWarnings("unchecked")
			@Override
			public <T> T getService(Class<T> serviceType) {
				if (EMFFormsMappingProviderManager.class == serviceType) {
					final EMFFormsMappingProviderManager mock = Mockito.mock(EMFFormsMappingProviderManager.class);
					Mockito.doReturn(Collections.singleton(UniqueSetting.createSetting(null, null))).when(mock)
						.getAllSettingsFor(vControl.getDomainModelReference(), parent);
					return (T) mock;
				}
				return super.getServiceWithoutLog(serviceType);
			}
		};
		ViewModelContextFactory.INSTANCE.createViewModelContext(view, parent);

		/* act */
		view.getChildren().add(vControl);

		/* assert no NPE */
	}

}
