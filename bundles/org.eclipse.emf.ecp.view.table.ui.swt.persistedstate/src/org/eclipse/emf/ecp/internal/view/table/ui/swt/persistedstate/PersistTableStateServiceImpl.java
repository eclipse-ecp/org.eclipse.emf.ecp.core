/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.view.table.ui.swt.persistedstate;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.spi.view.table.ui.swt.persistedstate.PersistTableStateService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceImpl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;

/**
 * Implementation of the {@link PersistTableStateService}.
 *
 * @author Johannes Faltermeier
 *
 */
public class PersistTableStateServiceImpl implements PersistTableStateService {

	private String viewID;
	private Set<VTableControl> tables;
	private LinkedHashSet<VControl> additionalControls;
	private URI uri;
	private VViewResourceImpl resource;
	private ReportService reportService;

	/**
	 * Default constructor.
	 *
	 * @param context the view model context
	 */
	PersistTableStateServiceImpl(final ViewModelContext context) {
		reportService = context.getService(ReportService.class);
		context.registerDisposeListener(new ViewModelContextDisposeListener() {

			@Override
			public void contextDisposed(ViewModelContext viewModelContext) {
				if (context != viewModelContext) {
					return;
				}
				persist();
			}
		});

		final VElement viewModel = context.getViewModel();
		viewID = viewModel.getUuid();
		// TODO proper fix should be implemented with Bug 495113.
		if (viewID == null) {
			return;
		}

		tables = findTables(viewModel);
		additionalControls = new LinkedHashSet<VControl>();

		final String stateLocation = Activator.getInstance().getStateLocation().toFile().getAbsolutePath();
		uri = URI.createFileURI(stateLocation).appendSegment(viewID).appendFileExtension("xmi"); //$NON-NLS-1$
		final ResourceSet resourceSet = createResourceSet();
		if (!resourceSet.getURIConverter().exists(uri, null)) {
			return;
		}
		resource = (VViewResourceImpl) resourceSet.getResource(uri, true);
		for (final VTableControl realTable : tables) {
			final Map<VDomainModelReference, VWidthConfiguration> realColumnDMRToConfig = getDMRToConfig(realTable);
			final EObject persistedTable = resource.getIDToEObjectMap().get(realTable.getUuid());
			if (!VTableControl.class.isInstance(persistedTable)) {
				continue;
			}
			final VTableDomainModelReference realTableDMR = VTableDomainModelReference.class
				.cast(realTable.getDomainModelReference());
			final Map<VDomainModelReference, VWidthConfiguration> persistedDMRIDToConfig = getDMRToConfig(
				VTableControl.class.cast(persistedTable));
			for (final Entry<VDomainModelReference, VWidthConfiguration> entry : persistedDMRIDToConfig.entrySet()) {
				/* find matching real column dmr */
				VDomainModelReference realMatchingDMR = null;
				for (final VDomainModelReference realDMR : realTableDMR.getColumnDomainModelReferences()) {
					if (EcoreUtil.equals(entry.getKey(), realDMR)) {
						realMatchingDMR = realDMR;
						break;
					}
				}
				if (realMatchingDMR == null) {
					continue;
				}
				/* get real width config for real dmr */
				if (realColumnDMRToConfig.containsKey(realMatchingDMR)) {
					fillWidthConfig(realColumnDMRToConfig.get(realMatchingDMR), entry.getValue());
				} else {
					final VWidthConfiguration widthConfiguration = VTableFactory.eINSTANCE.createWidthConfiguration();
					fillWidthConfig(widthConfiguration, entry.getValue());
					widthConfiguration.setColumnDomainReference(realMatchingDMR);
					realTable.getColumnConfigurations().add(widthConfiguration);
				}
			}
		}

	}

	private static void fillWidthConfig(VWidthConfiguration toFill, VWidthConfiguration lookup) {
		toFill.setWeight(lookup.getWeight());
		toFill.setMinWidth(lookup.getMinWidth());
	}

	private static Map<VDomainModelReference, VWidthConfiguration> getDMRToConfig(VTableControl table) {
		final Map<VDomainModelReference, VWidthConfiguration> result = new LinkedHashMap<VDomainModelReference, VWidthConfiguration>();
		for (final VTableColumnConfiguration configuration : table.getColumnConfigurations()) {
			if (!VWidthConfiguration.class.isInstance(configuration)) {
				continue;
			}
			final VWidthConfiguration widthConfiguration = VWidthConfiguration.class.cast(configuration);
			result.put(widthConfiguration.getColumnDomainReference(), widthConfiguration);
		}
		return result;
	}

	private static ResourceSet createResourceSet() {
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Map<String, Object> extensionToFactoryMap = resourceSet.getResourceFactoryRegistry()
			.getExtensionToFactoryMap();
		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new VViewResourceFactoryImpl());
		return resourceSet;
	}

	private static Set<VTableControl> findTables(final VElement viewModel) {
		final Set<VTableControl> tables = new LinkedHashSet<VTableControl>();
		final TreeIterator<EObject> viewModelContents = viewModel.eAllContents();
		while (viewModelContents.hasNext()) {
			final EObject next = viewModelContents.next();
			if (!VTableControl.class.isInstance(next)) {
				continue;
			}
			tables.add((VTableControl) next);
		}
		return tables;
	}

	private void persist() {
		final ResourceSet resourceSet = createResourceSet();
		final VViewResourceImpl resource = (VViewResourceImpl) resourceSet.createResource(uri);
		for (final VTableControl tableControl : tables) {
			final String uuid = tableControl.getUuid();
			final VTableControl tableToPersist = EcoreUtil.copy(tableControl);
			resource.getContents().add(tableToPersist);
			resource.setID(tableToPersist, uuid);
		}
		for (final VControl control : additionalControls) {
			for (final VAttachment attachment : control.getAttachments()) {
				if (!PersistTableStateServiceVAttachment.class.isInstance(attachment)) {
					continue;
				}
				final String uuid = control.getUuid();
				final VAttachment attachmentToPersist = EcoreUtil.copy(attachment);
				resource.getContents().add(attachmentToPersist);
				resource.setID(attachmentToPersist, uuid);
				break;
			}
		}
		try {
			resource.save(null);
		} catch (final IOException ex) {
			reportService.report(new AbstractReport(ex));
		}
	}

	@Override
	public void registerAdditionalControls(VControl... controls) {
		for (final VControl control : controls) {
			if (tables.contains(control)) {
				continue;
			}
			additionalControls.add(control);
		}
	}

	@Override
	public Optional<VAttachment> getPersistedState(VControl control) {
		final String uuid = control.getUuid();
		final EObject eObject = resource.getIDToEObjectMap().get(uuid);
		if (!VAttachment.class.isInstance(eObject)) {
			return Optional.empty();
		}
		return Optional.of(VAttachment.class.cast(eObject));
	}

}
