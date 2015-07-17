package org.eclipse.emf.ecp.view.template.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ViewTemplateService_Test.class, DomainModelReferenceSelector_Test.class, AlignmentStyle_Test.class,
	FontPropertiesStyle_Test.class, ViewModelElementSelector_Test.class })
public class AllTests {

}
