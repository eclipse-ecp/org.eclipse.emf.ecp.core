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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.model.test;

import org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationSelectorImpl_Test;
import org.eclipse.emf.ecp.view.template.selector.bool.model.impl.VTAndSelectorImpl_Test;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl.VTHierarchySelectorImpl_Test;
import org.eclipse.emf.ecp.view.template.style.alignment.model.impl.VTControlLabelAlignmentStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.background.model.impl.VTBackgroundStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl.VTFontPropertiesStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.impl.VTLabelWidthStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferenceStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.tab.model.impl.VTTabStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl.VTTableStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.validation.model.impl.VTValidationStylePropertyImpl_Test;
import org.eclipse.emf.ecp.view.template.style.wrap.model.impl.VTLabelWrapStylePropertyImpl_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit test cases for the org.eclipse.emf.ecp.view.template.model.test bundle.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ VTAnnotationSelectorImpl_Test.class, VTAndSelectorImpl_Test.class, VTHierarchySelectorImpl_Test.class,
	VTControlLabelAlignmentStylePropertyImpl_Test.class, VTBackgroundStylePropertyImpl_Test.class,
	VTFontPropertiesStylePropertyImpl_Test.class, VTLabelWidthStylePropertyImpl_Test.class,
	VTMandatoryStylePropertyImpl_Test.class, VTTabStylePropertyImpl_Test.class, VTTableStylePropertyImpl_Test.class,
	VTTextControlEnablementStylePropertyImpl_Test.class, VTUnsettableStylePropertyImpl_Test.class,
	VTValidationStylePropertyImpl_Test.class, VTLabelWrapStylePropertyImpl_Test.class,
	VTReferenceStylePropertyImpl_Test.class })
public class AllTests {

}
