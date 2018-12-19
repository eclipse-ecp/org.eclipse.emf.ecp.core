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
package org.eclipse.emfforms.core.services.segments.mapping.test;

import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingDmrSegmentGenerator_ITest;
import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingSegmentConverter_ITest;
import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingSegmentExpander_ITest;
import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingSegmentStructuralChangeTester_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All integration test cases for bundle org.eclipse.emfforms.core.services.segments.mapping.
 */
@RunWith(Suite.class)
@SuiteClasses({ MappingDmrSegmentGenerator_ITest.class, MappingSegmentConverter_ITest.class,
	MappingSegmentExpander_ITest.class, MappingSegmentStructuralChangeTester_ITest.class })
public class AllIntegrationTests {

}
