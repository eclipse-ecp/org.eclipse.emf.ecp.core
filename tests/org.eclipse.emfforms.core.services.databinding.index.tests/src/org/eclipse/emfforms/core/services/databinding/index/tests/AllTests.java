/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.databinding.index.tests;

import org.eclipse.emfforms.internal.core.services.databinding.index.EMFIndexedValueProperty_Test;
import org.eclipse.emfforms.internal.core.services.databinding.index.IndexDomainModelReferenceConverter_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All JUnit tests for core.services.databinding.index
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ IndexDomainModelReferenceConverter_Test.class, EMFIndexedValueProperty_Test.class })
public class AllTests {

}
