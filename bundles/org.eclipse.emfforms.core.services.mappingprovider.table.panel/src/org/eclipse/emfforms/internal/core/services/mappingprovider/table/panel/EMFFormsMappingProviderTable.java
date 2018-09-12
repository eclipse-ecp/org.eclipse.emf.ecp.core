/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.mappingprovider.table.panel;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.EMFFormsViewService;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * An {@link EMFFormsMappingProvider} implementation for
 * {@link VTableDomainModelReference} and especially for Tables with a detail panel.
 *
 * @author Eugen Neufeld
 *
 */
@Component(name = "EMFFormsMappingProviderTable")
public class EMFFormsMappingProviderTable implements EMFFormsMappingProvider {

	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private ReportService reportService;
	private EMFFormsViewService emfFormsViewService;

	/**
	 * Sets the {@link EMFFormsDatabindingEMF} service.
	 *
	 * @param emfFormsDatabinding
	 *            The databinding service
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsDatabinding(EMFFormsDatabindingEMF emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * Sets the {@link ReportService}.
	 *
	 * @param reportService
	 *            The {@link ReportService}
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Sets the {@link EMFFormsViewService} service.
	 *
	 * @param emfFormsViewService
	 *            The view service
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsViewService(EMFFormsViewService emfFormsViewService) {
		this.emfFormsViewService = emfFormsViewService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<UniqueSetting> getMappingFor(VDomainModelReference domainModelReference, EObject domainObject) {
		Assert.create(domainModelReference).notNull();
		Assert.create(domainObject).notNull();

		EObject parent = domainModelReference.eContainer();
		while (!(parent instanceof VView)) {
			parent = parent.eContainer();
		}

		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) domainModelReference;
		Setting tableSetting;
		try {
			tableSetting = emfFormsDatabinding.getSetting(tableDMR, domainObject);
		} catch (final DatabindingFailedException ex) {
			reportService.report(new DatabindingFailedReport(ex));
			return Collections.<UniqueSetting> emptySet();
		}

		final Set<UniqueSetting> settingsMap = new LinkedHashSet<UniqueSetting>();
		settingsMap.add(UniqueSetting.createSetting(tableSetting));

		for (final EObject eObject : (List<EObject>) tableSetting.get(true)) {
			// table columns
			for (final VDomainModelReference columnDMR : tableDMR.getColumnDomainModelReferences()) {
				try {
					final Setting columnSetting = emfFormsDatabinding.getSetting(columnDMR, eObject);
					settingsMap.add(UniqueSetting.createSetting(columnSetting));
				} catch (final DatabindingFailedException ex) {
					// ignoring this for now, as we explicitly allow abstract types (i.e. within tables)
					// reportService.report(new DatabindingFailedReport(ex));
				}
			}
			// everything else
			for (final VDomainModelReference columnDMR : getView((VView) parent, eObject)) {
				try {
					final Setting columnSetting = emfFormsDatabinding.getSetting(columnDMR, eObject);
					settingsMap.add(UniqueSetting.createSetting(columnSetting));
				} catch (final DatabindingFailedException ex) {
					// ignoring this for now, as we explicitly allow abstract types (i.e. within
					// tables)
				}
			}
		}
		return settingsMap;
	}

	private Collection<VDomainModelReference> getView(VView view, EObject newEntry) {
		final Set<VDomainModelReference> result = new LinkedHashSet<VDomainModelReference>();
		final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(view);
		final VView detailView = emfFormsViewService.getView(newEntry, properties);
		final TreeIterator<EObject> eAllContents = detailView.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject next = eAllContents.next();
			// we only need dmrs of controls, everything else won't be resolvable anyway
			if (next instanceof VDomainModelReference
				&& next.eContainingFeature() == VViewPackage.eINSTANCE.getControl_DomainModelReference()) {
				result.add((VDomainModelReference) next);
			}
		}
		return result;
	}

	@Override
	public double isApplicable(VDomainModelReference domainModelReference, EObject domainObject) {
		if (domainModelReference == null) {
			reportService.report(new AbstractReport("Warning: The given VDomainModelReference was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (domainObject == null) {
			reportService.report(new AbstractReport("Warning: The given domain object was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}

		if (VTableDomainModelReference.class.isInstance(domainModelReference)
			&& VTableControl.class.isInstance(domainModelReference.eContainer())
			&& VTableControl.class.cast(domainModelReference.eContainer())
				.getDetailEditing() == DetailEditing.WITH_PANEL) {
			return 10d;
		}

		return NOT_APPLICABLE;
	}
}
