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
package org.eclipse.emfforms.core.services.segments.featurepath.test;

import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureSegmentConverter_ITest;
import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureSegmentExpander_ITest;
import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureSegmentStructuralChangeTester_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FeatureSegmentConverter_ITest.class, FeatureSegmentExpander_ITest.class,
	FeatureSegmentStructuralChangeTester_ITest.class })
public class AllIntegrationTests {

}
