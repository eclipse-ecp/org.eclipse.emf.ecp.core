/**
 *
 */
package org.eclipse.emf.ecp.ide.util.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexandra Buzila
 *
 */
public class EcoreHelperNoDependencies_PTest {
	private final Registry packageRegistry = EPackage.Registry.INSTANCE;
	private static String aEcorePath = "/TestEcoreHelperProjectResources/A.ecore";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject project = root.getProject("TestEcoreHelperProjectResources");
		// create resources to register and unregister
		if (!project.exists()) {
			installResourcesProject();
		}
	}

	private static void installResourcesProject() throws Exception {
		final ProjectInstallerWizard wiz = new ProjectInstallerWizard();
		wiz.installExample(new NullProgressMonitor());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// make sure none of the packages exists in the registry
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		EcoreHelper.unregisterEcore(aEcorePath);
	}

	/**
	 * Test method for {@link EcoreHelper#registerEcore(String)}
	 *
	 * @throws IOException
	 */
	@Test
	public void testRegisterUnregisterIndependentEcore() throws IOException {

		// check initial setup
		assertFalse("Package is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));

		// register
		EcoreHelper.registerEcore(aEcorePath);
		assertTrue("Package not in the registry!",
			packageRegistry.containsKey("a.nsuri"));

		// unregister
		EcoreHelper.unregisterEcore(aEcorePath);
		assertFalse("Package is still in the registry!",
			packageRegistry.containsKey("a.nsuri"));

	}

	/**
	 * Test method for {@link EcoreHelper#registerEcore(String)}
	 *
	 * @throws IOException
	 */
	@Test
	public void testUnregisterPackageNotInRegitry() throws IOException {
		// check initial setup
		assertFalse("Package is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		// unregister
		EcoreHelper.unregisterEcore(aEcorePath);
		EcoreHelper.unregisterEcore(aEcorePath);
		assertFalse("Package is still in the registry!",
			packageRegistry.containsKey("a.nsuri"));
	}

}
