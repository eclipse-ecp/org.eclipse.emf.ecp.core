/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.rulerepository.tooling.test;

import org.eclipse.emfforms.internal.rulerepository.tooling.merge.MergeWithViewHandler_Test;
import org.eclipse.emfforms.internal.rulerepository.tooling.wizard.EMFFormsNewRuleRepositoryWizardPage_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All unit tests for the org.eclipse.emfforms.rulerepository.tooling bundle
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ MergeWithViewHandler_Test.class, EMFFormsNewRuleRepositoryWizardPage_Test.class })
public class AllTests {

}
