package org.eclipse.emf.ecp.ide.util.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	EcoreHelperDefaultPackageRegistryContents_PTest.class,
	EcoreHelperNoDependencies_PTest.class,
	EcoreHelperOneDependency_PTest.class,
	EcoreHelperTwoDependencies_PTest.class,
	EcoreHelperCyclicDependencies_PTest.class,
	EcoreHelperSubpackages_PTest.class })
public class AllTests {

}
