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
 ******************************************************************************/
package org.eclipse.emfforms.internal.ide.view.segments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrationException;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrator.PreReplaceProcessor;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link DmrToSegmentsMigratorImpl}. These tests test the migration behavior based on a mocked
 * {@link EMFFormsSegmentGenerator} and with an already loaded Resource.
 * <p>
 * For a "realistic" integration test see {@link DmrToSegmentsMigratorImpl_ITest}.
 *
 * @author Lucas Koehler
 *
 */
public class DmrToSegmentsMigratorImpl_Test {

	private DmrToSegmentsMigratorImpl migrator;
	private ReportService reportService;
	private EMFFormsSegmentGenerator segmentGenerator;
	private Resource resource;

	@Before
	public void setUp() throws Exception {
		reportService = mock(ReportService.class);
		segmentGenerator = mock(EMFFormsSegmentGenerator.class);
		migrator = new DmrToSegmentsMigratorImpl();
		migrator.setReportService(reportService);
		migrator.setEMFFormsSegmentGenerator(segmentGenerator);
		resource = createViewResource();
	}

	@Test
	public void performMigration() throws DmrToSegmentsMigrationException {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl control1 = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference featureDmr1 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control1.setDomainModelReference(featureDmr1);
		view.getChildren().add(control1);

		final VControl control2 = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference featureDmr2 = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		control2.setDomainModelReference(featureDmr2);
		final VGroup group = VGroupFactory.eINSTANCE.createGroup();
		group.getChildren().add(control2);
		view.getChildren().add(group);

		// mock segment generation
		final VFeatureDomainModelReferenceSegment segment11 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final VFeatureDomainModelReferenceSegment segment12 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		when(segmentGenerator.generateSegments(featureDmr1)).thenReturn(Arrays.asList(segment11, segment12));
		final VFeatureDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		when(segmentGenerator.generateSegments(featureDmr2)).thenReturn(Arrays.asList(segment2));

		resource.getContents().add(view);

		migrator.performMigration(resource);

		assertEquals(1, resource.getContents().size());
		assertSame(view, resource.getContents().get(0));
		assertEquals(2, view.getChildren().size());
		assertSame(control1, view.getChildren().get(0));

		// check migrated dmr of control1
		final VDomainModelReference migratedDmr1 = control1.getDomainModelReference();
		assertNotNull(migratedDmr1);
		assertNotSame(featureDmr1, migratedDmr1);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, migratedDmr1.eClass());
		assertEquals(2, migratedDmr1.getSegments().size());
		assertSame(segment11, migratedDmr1.getSegments().get(0));
		assertSame(segment12, migratedDmr1.getSegments().get(1));

		// check layout and migrated dmr of control2
		assertSame(group, view.getChildren().get(1));
		assertEquals(1, group.getChildren().size());
		assertSame(control2, group.getChildren().get(0));
		final VDomainModelReference migratedDmr2 = control2.getDomainModelReference();
		assertNotNull(migratedDmr2);
		assertNotSame(featureDmr2, migratedDmr2);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, migratedDmr2.eClass());
		assertEquals(1, migratedDmr2.getSegments().size());
		assertSame(segment2, migratedDmr2.getSegments().get(0));

		verify(segmentGenerator, times(2)).generateSegments(any());
		verify(reportService, never()).report(any());
	}

	@Test
	public void performMigration_tableWithCrossReference() throws DmrToSegmentsMigrationException {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VTableControl table = VTableFactory.eINSTANCE.createTableControl();
		view.getChildren().add(table);
		final VTableDomainModelReference tableDmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
		table.setDomainModelReference(tableDmr);

		final VFeaturePathDomainModelReference columnDmr1 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		final VFeaturePathDomainModelReference columnDmr2 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		tableDmr.getColumnDomainModelReferences().add(columnDmr1);
		tableDmr.getColumnDomainModelReferences().add(columnDmr2);

		final VWidthConfiguration widthConfig = VTableFactory.eINSTANCE.createWidthConfiguration();
		widthConfig.setMinWidth(100);
		widthConfig.setColumnDomainReference(columnDmr2);
		table.getColumnConfigurations().add(widthConfig);

		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		final VDomainModelReference childDmr1 = VViewFactory.eINSTANCE.createDomainModelReference();
		final VDomainModelReference childDmr2 = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(childDmr1);
		multiSegment.getChildDomainModelReferences().add(childDmr2);

		when(segmentGenerator.generateSegments(tableDmr)).thenReturn(Collections.singletonList(multiSegment));

		resource.getContents().add(view);
		migrator.performMigration(resource);

		assertSame(resource, table.eResource());
		final VDomainModelReference newTableDmr = table.getDomainModelReference();
		assertNotNull(newTableDmr);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, newTableDmr.eClass());
		assertEquals(1, newTableDmr.getSegments().size());
		assertSame(multiSegment, newTableDmr.getSegments().get(0));
		assertSame(widthConfig, table.getColumnConfigurations().get(0));
		assertSame(childDmr2, widthConfig.getColumnDomainReference());
		assertEquals(100, widthConfig.getMinWidth());
	}

	/**
	 * Tests migration for a view with a legacy and a segment dmr.
	 *
	 * @throws DmrToSegmentsMigrationException
	 */
	@Test
	public void performMigration_mixed() throws DmrToSegmentsMigrationException {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl legacyControl = VViewFactory.eINSTANCE.createControl();
		final VControl segmentControl = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference legacyDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		legacyControl.setDomainModelReference(legacyDmr);
		final VDomainModelReference segmentDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		segmentControl.setDomainModelReference(segmentDmr);

		view.getChildren().add(legacyControl);
		view.getChildren().add(segmentControl);

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		when(segmentGenerator.generateSegments(legacyDmr)).thenReturn(Collections.singletonList(featureSegment));

		resource.getContents().add(view);
		migrator.performMigration(resource);

		assertSame(resource, legacyControl.eResource());
		final VDomainModelReference dmr = legacyControl.getDomainModelReference();
		assertNotNull(dmr);
		assertNotSame(legacyDmr, dmr);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, dmr.eClass());
		assertEquals(1, dmr.getSegments().size());

		assertSame(featureSegment, dmr.getSegments().get(0));

		assertSame(resource, segmentControl.eResource());
		assertSame(segmentDmr, segmentControl.getDomainModelReference());
		verify(reportService, never()).report(any());
		verify(segmentGenerator, times(1)).generateSegments(any());
	}

	/**
	 * Tests that the migration applies given {@link PreReplaceProcessor}s and does so in the correct order.
	 * 
	 * @throws DmrToSegmentsMigrationException
	 */
	@Test
	public void performMigration_preReplaceProcessor() throws DmrToSegmentsMigrationException {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl legacyControl = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference legacyDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		legacyControl.setDomainModelReference(legacyDmr);

		view.getChildren().add(legacyControl);
		resource.getContents().add(view);

		final VFeatureDomainModelReferenceSegment featureSegment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		when(segmentGenerator.generateSegments(legacyDmr)).thenReturn(Collections.singletonList(featureSegment));

		// mock pre replace processors
		final VDomainModelReferenceSegment addedByProcessor1 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment addedByProcessor2 = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		final PreReplaceProcessor processor1 = (legacy, segment) -> {
			segment.getSegments().add(addedByProcessor1);
		};
		final PreReplaceProcessor processor2 = (legacy, segment) -> {
			segment.getSegments().add(addedByProcessor2);
		};

		migrator.performMigration(resource, processor1, processor2);

		assertSame(resource, legacyControl.eResource());
		final VDomainModelReference dmr = legacyControl.getDomainModelReference();
		assertNotNull(dmr);
		assertNotSame(legacyDmr, dmr);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, dmr.eClass());
		assertEquals(3, dmr.getSegments().size());

		assertSame(featureSegment, dmr.getSegments().get(0));
		assertSame(addedByProcessor1, dmr.getSegments().get(1));
		assertSame(addedByProcessor2, dmr.getSegments().get(2));

		verify(reportService, never()).report(any());
		verify(segmentGenerator, times(1)).generateSegments(any());
	}

	@Test
	public void performMigration_emptyResource() throws DmrToSegmentsMigrationException {
		migrator.performMigration(resource);

		assertTrue(resource.getContents().isEmpty());
	}

	@Test
	public void needsMigration_mixed() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl legacyControl = VViewFactory.eINSTANCE.createControl();
		final VControl segmentControl = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference legacyDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		legacyControl.setDomainModelReference(legacyDmr);
		final VDomainModelReference segmentDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		segmentControl.setDomainModelReference(segmentDmr);

		view.getChildren().add(legacyControl);
		view.getChildren().add(segmentControl);

		resource.getContents().add(view);
		assertTrue(migrator.needsMigration(resource));
	}

	@Test
	public void needsMigration_legacyOnly() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl legacyControl = VViewFactory.eINSTANCE.createControl();

		final VFeaturePathDomainModelReference legacyDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		legacyControl.setDomainModelReference(legacyDmr);

		view.getChildren().add(legacyControl);

		resource.getContents().add(view);
		assertTrue(migrator.needsMigration(resource));
	}

	@Test
	public void needsMigration_segmentDmrsOnly() {
		final VView view = VViewFactory.eINSTANCE.createView();
		final VControl segmentControl = VViewFactory.eINSTANCE.createControl();

		final VDomainModelReference segmentDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		segmentControl.setDomainModelReference(segmentDmr);

		view.getChildren().add(segmentControl);

		resource.getContents().add(view);
		assertFalse(migrator.needsMigration(resource));
	}

	private Resource createViewResource() {
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new VViewResourceFactoryImpl());
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL-RESOURCE")); //$NON-NLS-1$
		return resource;
	}
}
