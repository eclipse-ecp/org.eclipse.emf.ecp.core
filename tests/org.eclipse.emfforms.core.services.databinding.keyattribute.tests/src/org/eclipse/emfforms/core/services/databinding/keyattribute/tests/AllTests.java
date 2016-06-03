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
package org.eclipse.emfforms.core.services.databinding.keyattribute.tests;

import org.eclipse.emfforms.internal.core.services.databinding.keyattribute.EMFKeyAttributeValueProperty_Test;
import org.eclipse.emfforms.internal.core.services.databinding.keyattribute.KeyAttributeDomainModelReferenceConverter_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All JUnit test cases for org.eclipse.emfforms.core.services.databinding.keyattribute.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFKeyAttributeValueProperty_Test.class, KeyAttributeDomainModelReferenceConverter_Test.class })
public class AllTests {

}
