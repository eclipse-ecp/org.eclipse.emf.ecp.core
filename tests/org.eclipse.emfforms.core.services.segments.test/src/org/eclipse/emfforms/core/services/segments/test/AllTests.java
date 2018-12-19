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
package org.eclipse.emfforms.core.services.segments.test;

import org.eclipse.emfforms.internal.core.services.segments.DmrToSegmentsViewService_Test;
import org.eclipse.emfforms.internal.core.services.segments.SegmentGeneratorService_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All JUnit tests for org.eclipse.emfforms.core.services.segments.
 *
 * @author Lucas Koehlerr
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ DmrToSegmentsViewService_Test.class, SegmentGeneratorService_Test.class })
public class AllTests {

}
