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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrationException;
import org.eclipse.emfforms.spi.ide.view.segments.DmrToSegmentsMigrator;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Integration tests for {@link DmrToSegmentsMigratorImpl}. This tests a "realistic" migration like it would occur in
 * the tooling.
 *
 * @author Lucas Koehler
 *
 */
public class DmrToSegmentsMigratorImpl_ITest {

	private static final String PLUGIN_ID = "org.eclipse.emfforms.ide.view.segments.test"; //$NON-NLS-1$

	private static Bundle bundle;

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	private static BundleContext migratorBundleContext;
	private static ServiceReference<DmrToSegmentsMigrator> serviceReference;
	private static DmrToSegmentsMigrator segmentsMigrator;

	@BeforeClass
	public static void beforeClass() {
		bundle = Platform.getBundle(PLUGIN_ID);
		// Need to get service from its bundle because a fragment does not have a bundle context
		migratorBundleContext = FrameworkUtil.getBundle(DmrToSegmentsMigratorImpl.class).getBundleContext();
		serviceReference = migratorBundleContext.getServiceReference(DmrToSegmentsMigrator.class);
		segmentsMigrator = migratorBundleContext.getService(serviceReference);
	}

	@AfterClass
	public static void afterClass() {
		segmentsMigrator = null;
		migratorBundleContext.ungetService(serviceReference);
	}

	@Test
	public void smokeTest() throws IOException, DmrToSegmentsMigrationException {
		final File newFile = testFolder.newFile();
		newFile.deleteOnExit();
		final InputStream openStream = bundle.getEntry("data/Fan.view").openStream(); //$NON-NLS-1$
		copy(openStream, newFile);
		final URI resourceURI = URI.createFileURI(newFile.getAbsolutePath());

		final boolean needsMigration = segmentsMigrator.needsMigration(resourceURI);
		assertTrue("Fan.view should need migration.", needsMigration); //$NON-NLS-1$

		segmentsMigrator.performMigration(resourceURI);

		final Resource resource = loadViewResource(resourceURI);
		assertEquals(1, resource.getContents().size());

		final VView view = (VView) resource.getContents().get(0);
		assertEquals(2, view.getChildren().size());
		final VHorizontalLayout horizontal = (VHorizontalLayout) view.getChildren().get(0);
		assertEquals(2, horizontal.getChildren().size());

		// Check control dmr in group
		final VGroup group = (VGroup) horizontal.getChildren().get(0);
		assertEquals("test", group.getName()); //$NON-NLS-1$
		assertEquals(1, group.getChildren().size());
		final VControl groupControl = (VControl) group.getChildren().get(0);
		final VDomainModelReference groupControlDmr = groupControl.getDomainModelReference();
		assertNotNull(groupControlDmr);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, groupControlDmr.eClass());
		assertEquals(1, groupControlDmr.getSegments().size());
		final VFeatureDomainModelReferenceSegment groupControlDmrSegment = (VFeatureDomainModelReferenceSegment) groupControlDmr
			.getSegments().get(0);
		assertEquals("name", groupControlDmrSegment.getDomainModelFeature()); //$NON-NLS-1$

		// Check control dmr in vertical layout
		final VVerticalLayout vertical = (VVerticalLayout) horizontal.getChildren().get(1);
		assertEquals(1, vertical.getChildren().size());
		final VControl verticalControl = (VControl) vertical.getChildren().get(0);
		final VDomainModelReference verticalControlDmr = verticalControl.getDomainModelReference();
		assertNotNull(verticalControlDmr);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, verticalControlDmr.eClass());
		assertEquals(1, verticalControlDmr.getSegments().size());
		final VFeatureDomainModelReferenceSegment verticalControlDmrSegment = (VFeatureDomainModelReferenceSegment) verticalControlDmr
			.getSegments().get(0);
		assertEquals("dateOfBirth", verticalControlDmrSegment.getDomainModelFeature()); //$NON-NLS-1$

		// Check table control
		final VTableControl table = (VTableControl) view.getChildren().get(1);
		final VDomainModelReference tableDmr = table.getDomainModelReference();
		assertNotNull(tableDmr);
		assertSame(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE, tableDmr.eClass());
		assertEquals(1, tableDmr.getSegments().size());
		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) tableDmr
			.getSegments().get(0);
		assertEquals("visitedTournaments", multiSegment.getDomainModelFeature()); //$NON-NLS-1$
		assertEquals(3, multiSegment.getChildDomainModelReferences().size());

		// check child dmrs
		final VDomainModelReference childDmr1 = multiSegment.getChildDomainModelReferences().get(0);
		assertEquals(1, childDmr1.getSegments().size());
		assertEquals("type", //$NON-NLS-1$
			VFeatureDomainModelReferenceSegment.class.cast(childDmr1.getSegments().get(0)).getDomainModelFeature());
		final VDomainModelReference childDmr2 = multiSegment.getChildDomainModelReferences().get(1);
		assertEquals(1, childDmr2.getSegments().size());
		assertEquals("priceMoney", //$NON-NLS-1$
			VFeatureDomainModelReferenceSegment.class.cast(childDmr2.getSegments().get(0)).getDomainModelFeature());
		final VDomainModelReference childDmr3 = multiSegment.getChildDomainModelReferences().get(2);
		assertEquals(1, childDmr3.getSegments().size());
		assertEquals("receivesTrophy", //$NON-NLS-1$
			VFeatureDomainModelReferenceSegment.class.cast(childDmr3.getSegments().get(0)).getDomainModelFeature());

		// check column configurations
		assertEquals(2, table.getColumnConfigurations().size());
		final VReadOnlyColumnConfiguration readOnlyConfig = (VReadOnlyColumnConfiguration) table
			.getColumnConfigurations().get(0);
		assertEquals(2, readOnlyConfig.getColumnDomainReferences().size());
		assertSame(childDmr3, readOnlyConfig.getColumnDomainReferences().get(0));
		assertSame(childDmr2, readOnlyConfig.getColumnDomainReferences().get(1));
		final VWidthConfiguration widthConfig = (VWidthConfiguration) table.getColumnConfigurations().get(1);
		assertEquals(75, widthConfig.getMinWidth());
		assertSame(childDmr1, widthConfig.getColumnDomainReference());
	}

	private static Resource loadViewResource(URI resourceUri) throws IOException {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> extensionToFactoryMap = resourceSet
			.getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
			new VViewResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(VViewPackage.eNS_URI,
			VViewPackage.eINSTANCE);
		final Resource resource = resourceSet.createResource(resourceUri);
		resource.load(null);
		return resource;
	}

	private static void copy(InputStream in, File file) {
		try (OutputStream out = new FileOutputStream(file)) {
			final byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
		}
		// BEGIN SUPRESS CATCH EXCEPTION
		catch (final Exception e) {// END SUPRESS CATCH EXCEPTION
			e.printStackTrace();
		}
	}
}
