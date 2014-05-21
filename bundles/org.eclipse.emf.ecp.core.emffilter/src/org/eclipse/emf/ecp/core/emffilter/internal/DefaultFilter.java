/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.core.emffilter.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecp.core.util.ECPFilterProvider;

/**
 * This class provides all EPackages that are per default in an Eclipse Modeling
 * Edition.
 * 
 * @author Eugen Neufeld
 * 
 */
public class DefaultFilter implements ECPFilterProvider {

	/**
	 * Convenient constructor.
	 */
	public DefaultFilter() {
	}

	/**
	 * This returns all package uris known in an default modeling edition including emfstore.
	 * 
	 * @return a {@link Set} of {@link String Strings} of the default packages in the modeling edition of eclipse
	 */
	@Override
	public Set<String> getHiddenPackages() {
		final Set<String> packages = new HashSet<String>();
		addE4Models(packages);
		addEMFStoreModels(packages);
		addEMFModels(packages);
		addCDOModels(packages);
		addOCLModels(packages);
		addUMLModels(packages);
		addEMFCompareModels(packages);
		addGMFModels(packages);
		addGraphitiModels(packages);
		addQ7Models1(packages);
		addQ7Models2(packages);
		addNet4jModels(packages);
		addViewModels(packages);

		// xml
		packages.add("http://www.eclipse.org/xsd/2002/XSD"); //$NON-NLS-1$
		packages.add("http://www.w3.org/XML/1998/namespace"); //$NON-NLS-1$
		// other default
		packages.add("http://www.eclipse.org/amalgamation/discovery/1.0"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/acceleo/profiler/3.0"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/acceleo/mtl/3.0"); //$NON-NLS-1$

		// ecl
		packages.add("http://www.eclipse.org/ecl/filesystem.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/interop.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/platform/commands.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/platform/objects.ecore"); //$NON-NLS-1$

		return packages;
	}

	/**
	 * @param packages
	 */
	private void addViewModels(Set<String> packages) {
		packages.add("http://org/eclipse/emf/ecp/view/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/custom/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/dynamictree/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/group/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/groupedgrid/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/horizontal/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/label/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/rule/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/separator/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/table/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/vertical/model"); //$NON-NLS-1$

		packages.add("http://org/eclipse/emf/ecp/view/categorization/model"); //$NON-NLS-1$
		packages.add("http://org/eclipse/emf/ecp/view/template/model"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/ecp/view/template/style/validation/model"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/ecp/view/template/style/alignment/model"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/ecp/view/template/selector/domainmodelreference/model"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/ecp/view/template/style/fontProperties/model"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/ecp/view/template/selector/viewmodelelement/model"); //$NON-NLS-1$

		packages.add("http://org/eclipse/emf/ecp/view/treemasterview/model"); //$NON-NLS-1$

		packages.add("http://org/eclipse/emf/ecp/view/ideconfig/model"); //$NON-NLS-1$
	}

	private void addQ7Models1(Set<String> packages) {
		packages.add("http://www.eclipse.org/ecl/ast.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/invoke/commands.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/debug/runtime/commands.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/data/commands.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/platform.ui/commands.ecore"); //$NON-NLS-1$
		packages.add("http:///com/xored/q7/ecl/context.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/x5/core.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/core.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/q7/debug"); //$NON-NLS-1$
		packages.add("http:///com/xored/tesla/core/diagram.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/tesla/diagram.ecore"); //$NON-NLS-1$
		packages.add("http:///com/xored/tesla/core/info.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/sherlock/jobs/1.0"); //$NON-NLS-1$
		packages.add("http://xored.com/q7/sherlock/model.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/tesla.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/q7/core/model.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/data/objects.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/platform.ui/objects.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/operations.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/parameters.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/perf.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/x5/data/eclipse/platform.ecore"); //$NON-NLS-1$
		packages.add("com.xored.q7.preferences"); //$NON-NLS-1$
		packages.add("http:///com/xored/tesla/core/protocol.ecore"); //$NON-NLS-1$
		packages.add("http:///com/xored/tesla/core/protocol/raw.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/sherlock/report/1.0"); //$NON-NLS-1$
		packages.add("http://xored.com/sherlock/report/1.0"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/reporting.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/scenario.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/sherlock/1.0"); //$NON-NLS-1$
		packages.add("http://xored.com/x5/dynamic/com.xored.q7.x5.startup"); //$NON-NLS-1$
		packages.add("http://xored.com/x5/data/system.ecore"); //$NON-NLS-1$
		packages.add("http:///com/xored/tesla/core/ui.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/x5/data/eclipse/workspace.ecore"); //$NON-NLS-1$
		packages.add("http:///com/xored/q7/workspace.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/filesystem.ecore"); //$NON-NLS-1$
		packages.add("http:///com/xored/q7/preferences.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/scenario.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/x5/data/eclipse/workspace.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/filesystem.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/interop.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/platform/commands.ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ecl/platform/objects.ecore"); //$NON-NLS-1$
		packages.add("com.xored.q7.scenario"); //$NON-NLS-1$
		packages.add("com.xored.x5.data.eclipse.workspace"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/filesystem.ecore"); //$NON-NLS-1$
		packages.add("http:///com/xored/q7/preferences.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/scenario.ecore"); //$NON-NLS-1$
		packages.add("http://xored.com/x5/data/eclipse/workspace.ecore"); //$NON-NLS-1$
	}

	private void addQ7Models2(Set<String> packages) {
		packages.add("http:///com/xored/q7/scenario.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/verifications/text.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/verifications/time.ecore"); //$NON-NLS-1$
		packages.add("http://com/xored/q7/verifications/tree.ecore");//$NON-NLS-1$
		packages.add("http://xored.com/sherlock/eclipse/workspace.ecore");//$NON-NLS-1$
		packages.add("http://www.eclipse.org/debug/runtime/model.ecore");//$NON-NLS-1$
		packages.add("http://com/xored/q7/verifications/status.ecore");//$NON-NLS-1$
		packages.add("http://xored.com/x5/data/eclipse/workspace.ecore");//$NON-NLS-1$
	}

	private void addGraphitiModels(Set<String> packages) {
		packages.add("http://eclipse.org/graphiti/examples/chess"); //$NON-NLS-1$
		packages.add("http://eclipse.org/graphiti/mm/algorithms/styles"); //$NON-NLS-1$
		packages.add("http://eclipse.org/graphiti/mm/pictograms"); //$NON-NLS-1$
		packages.add("http://eclipse.org/graphiti/mm/algorithms"); //$NON-NLS-1$
		packages.add("http://eclipse.org/graphiti/mm"); //$NON-NLS-1$
		packages.add("http:///org/eclipse/graphiti/examples/filesystem"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addGMFModels(Set<String> packages) {
		// gmf
		packages.add("http://www.eclipse.org/gmf/runtime/1.0.0/notation"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/gmf/runtime/1.0.2/notation"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/gmf/runtime/1.0.1/notation"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addEMFCompareModels(Set<String> packages) {
		// emf compare
		packages.add("http://www.eclipse.org/emf/compare"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/compare/match/1.1"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/compare/epatch/0.1"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/compare/diff/1.1"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addUMLModels(Set<String> packages) {
		// UML
		packages.add("http://www.eclipse.org/uml2/2.2.0/GenModel"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/1.1.0/GenModel"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/schemas/Standard/1"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/2.1.0/UML"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/3.0.0/UML"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/4.0.0/Types"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/4.0.0/UML/Profile/L3"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/4.0.0/UML/Profile/L2"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/2.0.0/UML"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/uml2/4.0.0/UML"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addOCLModels(Set<String> packages) {
		// OCL
		packages.add("http://www.eclipse.org/ocl/1.1.0/OCL/CST"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ocl/1.1.0/OCL/Expressions"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ocl/1.1.0/Ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ocl/1.1.0/UML"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ocl/1.1.0/OCL"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ocl/1.1.0/OCL/Types"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ocl/1.1.0/OCL/Utilities"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addCDOModels(Set<String> packages) {
		// CDO
		packages.add("http://www.eclipse.org/emf/CDO/Eresource/4.0.0"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/CDO/security/4.1.0"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/CDO/Etypes/4.0.0"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addEMFModels(Set<String> packages) {
		// emf
		packages.add("http://www.eclipse.org/emf/2002/Ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2002/Tree"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2003/Change"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2003/XMLType"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2004/Ecore2Ecore"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2009/Validation"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2002/Mapping"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2002/GenModel"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2005/Ecore2XML"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/emf/2002/XSD2Ecore"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addEMFStoreModels(Set<String> packages) {
		// emfstore
		packages.add("http://eclipse.org/emf/emfstore/client/model"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/common/model"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/roles"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/versioning"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/versioning/operations"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/versioning/events"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/versioning/events/server/"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/versioning/operations/semantic"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/url"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/server/model/accesscontrol"); //$NON-NLS-1$
		packages.add("http://eclipse.org/emf/emfstore/common/model"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addE4Models(Set<String> packages) {
		// e4
		packages.add("http://www.eclipse.org/ui/2010/UIModel/application/ui/menu"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ui/2010/UIModel/application/ui"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ui/2010/UIModel/fragment"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ui/2010/UIModel/application/ui/basic"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ui/2010/UIModel/application"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ui/2010/UIModel/application/ui/advanced"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ui/2010/UIModel/application/commands"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/ui/2010/UIModel/application/descriptor/basic"); //$NON-NLS-1$
	}

	/**
	 * @param packages
	 */
	private static void addNet4jModels(Set<String> packages) {
		// NET4J
		packages.add("http://www.eclipse.org/NET4J/defs/1.0.0"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/NET4J/ui/defs/1.0.0"); //$NON-NLS-1$
		packages.add("http://www.eclipse.org/NET4J/util/defs/1.0.0"); //$NON-NLS-1$
	}

}
