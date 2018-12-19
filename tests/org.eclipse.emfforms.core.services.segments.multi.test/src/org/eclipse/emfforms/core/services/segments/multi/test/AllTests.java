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
package org.eclipse.emfforms.core.services.segments.multi.test;

import org.eclipse.emfforms.internal.core.services.segments.multi.EMFFormsMappingProviderMulti_Test;
import org.eclipse.emfforms.internal.core.services.segments.multi.MultiSegmentConverter_Test;
import org.eclipse.emfforms.internal.core.services.segments.multi.MultiSegmentExpander_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All JUnit tests for bundle org.eclipse.emfforms.core.services.segments.multi.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFFormsMappingProviderMulti_Test.class, MultiSegmentConverter_Test.class,
	MultiSegmentExpander_Test.class })
public class AllTests {

}
