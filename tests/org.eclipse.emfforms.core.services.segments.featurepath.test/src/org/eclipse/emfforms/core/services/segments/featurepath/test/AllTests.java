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

import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeaturePathDmrSegmentGenerator_Test;
import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureSegmentConverter_Test;
import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureSegmentExpander_Test;
import org.eclipse.emfforms.internal.core.services.segments.featurepath.FeatureSegmentStructuralChangeTester_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All unit tests for org.eclipse.emfforms.core.services.segments.featurepath
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ FeaturePathDmrSegmentGenerator_Test.class, FeatureSegmentConverter_Test.class,
	FeatureSegmentExpander_Test.class, FeatureSegmentStructuralChangeTester_Test.class })
public class AllTests {

}
