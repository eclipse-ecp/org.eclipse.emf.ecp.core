/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.view.template.selector.domainmodelreference.model.edapt;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.internal.view.model.edapt._190to200.DmrSelectorMigrationPostProcessor;
import org.eclipse.emf.ecp.view.edapt.MigrationPostProcessor;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.osgi.service.component.annotations.Component;

/**
 * This {@link MigrationPostProcessor} is used to set the dmrRootEClass feature added to the
 * VTDomainModelReferenceSelector after the Edapt migration finished.
 * This is done because the migration cannot set the EClass because no Instance of the EClass can be created in Edapt.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "DmrSelectorMigrationPostProcessorImpl")
public class DmrSelectorMigrationPostProcessorImpl implements DmrSelectorMigrationPostProcessor {

	/**
	 * Key: the UUID identifying the DomainModelReferenceSelector.<br/>
	 * Value: the EClass to set as the selector's dmrRootEClass.
	 */
	private final Map<String, EClass> selectorToRootEClassMap = new LinkedHashMap<String, EClass>();

	/**
	 * {@inheritDoc}
	 * <p>
	 * <strong>Note:</strong> The packages containg the root EClasses of the DMR Selectors to process must be registered
	 * to the package registry. Otherwise this service won't be able to find them.
	 * The registration can for instance be done by the {@link DmrSelectorMigrationPreProcessor}.
	 */
	@Override
	public void process(Resource migratedModel) throws MigrationException {
		if (selectorToRootEClassMap.isEmpty()) {
			return;
		}

		final EList<EObject> contents = migratedModel.getContents();
		if (contents.isEmpty()) {
			throw new MigrationException(
				new IllegalArgumentException(String.format("The resource %s is empty", migratedModel))); //$NON-NLS-1$
		}

		final EObject eObject = contents.get(0);
		if (!VTViewTemplate.class.isInstance(eObject)) {
			return;
		}

		// get dmr selectors by their registered IDs and set the dmrRootEClass
		for (final String id : selectorToRootEClassMap.keySet()) {
			final EObject selector = migratedModel.getEObject(id);
			if (selector == null) {
				throw new MigrationException(
					new IllegalStateException(String
						.format("The DMR Selector with id '%s' is not present in the migrated model in resource %s.", //$NON-NLS-1$
							id, migratedModel)));
			}
			if (!VTDomainModelReferenceSelector.class.isInstance(selector)) {
				throw new MigrationException(
					new IllegalStateException(String
						.format("The EObject with id '%s' is not a DMR Selector (migrated model in resource %s)", id, //$NON-NLS-1$
							migratedModel)));
			}
			final VTDomainModelReferenceSelector dmrSelector = VTDomainModelReferenceSelector.class.cast(selector);
			dmrSelector.setDmrRootEClass(selectorToRootEClassMap.get(id));
		}

		// clear after execution to not store old mappings after a post processing was executed
		selectorToRootEClassMap.clear();
	}

	@Override
	public void addSelectorToRootEClassMapping(String uuid, EClass dmrRootEClass) {
		/*
		 * The given dmrRootEClass is not the "real" one but the equivalent in the migration metamodel.
		 * Thus, we need to load to get the real EPackage and EClass by getting the EPackage from the registry.
		 */
		final EPackage migrationPackage = (EPackage) dmrRootEClass.eContainer();
		final EPackage realPackage = EPackage.Registry.INSTANCE.getEPackage(migrationPackage.getNsURI());
		final EClass realEClass = (EClass) realPackage.getEClassifier(dmrRootEClass.getName());

		selectorToRootEClassMap.put(uuid, realEClass);
	}
}
