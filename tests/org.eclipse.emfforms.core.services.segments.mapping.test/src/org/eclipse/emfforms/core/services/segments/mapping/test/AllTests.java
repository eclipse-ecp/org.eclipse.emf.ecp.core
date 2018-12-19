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

import org.eclipse.emfforms.internal.core.services.segments.mapping.EMFMappingValueProperty_Test;
import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingDmrSegmentGenerator_Test;
import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingSegmentConverter_Test;
import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingSegmentExpander_Test;
import org.eclipse.emfforms.internal.core.services.segments.mapping.MappingSegmentStructuralChangeTester_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Unit test cases for bundle org.eclipse.emfforms.core.services.segments.mapping.
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFMappingValueProperty_Test.class, MappingDmrSegmentGenerator_Test.class,
	MappingSegmentConverter_Test.class, MappingSegmentExpander_Test.class,
	MappingSegmentStructuralChangeTester_Test.class })
public class AllTests {

}
