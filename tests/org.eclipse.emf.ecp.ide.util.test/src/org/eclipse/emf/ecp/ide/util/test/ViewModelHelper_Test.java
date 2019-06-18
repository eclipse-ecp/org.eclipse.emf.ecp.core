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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper.ViewLoader;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Unit tests for the {@link ViewModelHelper}.
 *
 * @author Lucas Koehler
 *
 */
public class ViewModelHelper_Test {

	private static final String PATH_1 = "/test.1/model/model.ecore";
	private static final String PATH_2 = "/test.2/model/model.ecore";

	@Test
	public void getEcorePaths_pre1170() {
		final Resource resource = loadResource("resources/TestViewModelHelperResources/ecorePaths_pre1170.view");
		final List<String> ecorePaths = ViewModelHelper.getEcorePaths(resource);
		assertEquals("Incorrected number of extracted ecore paths.", 1, ecorePaths.size());
		assertEquals("Incorrect extracted ecore path.", PATH_1, ecorePaths.get(0));
	}

	@Test
	public void getEcorePaths_1170_singlePath() {
		final Resource resource = loadResource("resources/TestViewModelHelperResources/ecorePaths_1170_single.view");
		final List<String> ecorePaths = ViewModelHelper.getEcorePaths(resource);
		assertEquals("Incorrected number of extracted ecore paths.", 1, ecorePaths.size());
		assertEquals("Incorrect extracted ecore path.", PATH_1, ecorePaths.get(0));
	}

	@Test
	public void getEcorePaths_1170_multiplePaths() {
		final Resource resource = loadResource("resources/TestViewModelHelperResources/ecorePaths_1170_multiple.view");
		final List<String> ecorePaths = ViewModelHelper.getEcorePaths(resource);
		assertEquals("Incorrected number of extracted ecore paths.", 2, ecorePaths.size());
		assertEquals("Incorrect extracted ecore path.", PATH_1, ecorePaths.get(0));
		assertEquals("Incorrect extracted ecore path.", PATH_2, ecorePaths.get(1));
	}

	private Resource loadResource(String filePath) {
		final ResourceSetImpl rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final Resource resource = rs.createResource(URI.createFileURI(filePath));
		try {
			resource.load(Collections.singletonMap(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE));
			return resource;
		} catch (final IOException ex) {
			fail("Could not load resource at: " + filePath);
		}
		return null; // Never happens
	}

	@Test
	public void loadView_ecorePathDoesNotExist() throws IOException {
		final List<AbstractReport> reports = new LinkedList<>();
		final List<String> registeredEcores = new LinkedList<>();
		createViewLoader(false, reports).loadView(Mockito.mock(IFile.class), registeredEcores);
		assertEquals("There should be a report for a non-existent ecore path", 1, reports.size());
		assertEquals("There should be a warning for a non-existent ecore path", IStatus.WARNING,
			reports.get(0).getSeverity());
	}

	@Test
	public void loadView_ecorePathDoesExist() throws IOException {
		final List<AbstractReport> reports = new LinkedList<>();
		final List<String> registeredEcores = new LinkedList<>();
		createViewLoader(true, reports).loadView(Mockito.mock(IFile.class), registeredEcores);
		assertEquals("There should be no report for non-existent ecore paths", 0, reports.size());
		assertEquals("There should be an entry in registered ecores", 1, registeredEcores.size());
	}

	private ViewLoader createViewLoader(boolean ecoreExists, List<AbstractReport> reports) {
		return new ViewLoader() {
			@Override
			protected String getPath(IFile file) {
				return "test.path";
			}

			@Override
			protected VView loadView(String path) {
				final VView view = Mockito.mock(VView.class);
				Mockito.when(view.getRootEClass()).thenReturn(Mockito.mock(EClass.class));
				Mockito.when(view.getEcorePaths()).thenReturn(ECollections.singletonEList("ecorePath"));
				return view;
			}

			@Override
			protected boolean ecoreExistsInWorkspace(String ecorePath) {
				return ecoreExists;
			}

			@Override
			protected ReportService getReportService() {
				final ReportService reportService = Mockito.mock(ReportService.class);
				Mockito.doAnswer(inv -> reports.add(AbstractReport.class.cast(inv.getArguments()[0])))
					.when(reportService).report(Matchers.any(AbstractReport.class));
				return reportService;
			}

			@Override
			protected void registerEcore(String ecorePath) throws IOException {
				// Do nothing
			}
		};
	}
}
