/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.databinding.mapping.tests;

import org.eclipse.emfforms.internal.core.services.databinding.mapping.EMFMappingValueProperty_Test;
import org.eclipse.emfforms.internal.core.services.databinding.mapping.MappingDomainModelReferenceSegmentConverter_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All JUnit test cases for core.services.databinding.mapping
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFMappingValueProperty_Test.class, MappingDomainModelReferenceSegmentConverter_Test.class })
public class AllTests {

}
