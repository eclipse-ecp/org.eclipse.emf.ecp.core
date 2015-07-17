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
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexandra Buzila
 *
 */
public class EcoreHelperTwoDependencies_PTest {

	private final Registry packageRegistry = EPackage.Registry.INSTANCE;
	private static String cEcorePath = "/TestEcoreHelperProjectResources/C.ecore";
	private static String aEcorePath = "/TestEcoreHelperProjectResources/A.ecore";
	private static String bEcorePath = "/TestEcoreHelperProjectResources/B.ecore";

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

	@Test
	public void testRegisterUnregister() throws IOException {

		// check initial state
		assertFalse("Package A is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertFalse("Package C is already in the registry!",
			packageRegistry.containsKey("c.nsuri"));

		// register C references B references A
		EcoreHelper.registerEcore(cEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertTrue("Package B not in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertTrue("Package C not in the registry!",
			packageRegistry.containsKey("c.nsuri"));

		EcoreHelper.unregisterEcore(cEcorePath);
		assertFalse("Package A is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertFalse("Package C is already in the registry!",
			packageRegistry.containsKey("c.nsuri"));
	}

	@Test
	public void testUnregisterMultipleUsage() throws IOException {
		// register
		EcoreHelper.registerEcore(aEcorePath);
		EcoreHelper.registerEcore(bEcorePath);
		EcoreHelper.registerEcore(cEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertTrue("Package B not in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertTrue("Package C not in the registry!",
			packageRegistry.containsKey("c.nsuri"));

		// unregister C references B
		EcoreHelper.unregisterEcore(cEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertTrue("Package B not in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertFalse("Package C is already in the registry!",
			packageRegistry.containsKey("c.nsuri"));

		// unregister B references A
		EcoreHelper.unregisterEcore(bEcorePath);
		assertTrue("Package A not in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertFalse("Package C is already in the registry!",
			packageRegistry.containsKey("c.nsuri"));

		// unregister A
		EcoreHelper.unregisterEcore(aEcorePath);
		assertFalse("Package A is already in the registry!",
			packageRegistry.containsKey("a.nsuri"));
		assertFalse("Package B is already in the registry!",
			packageRegistry.containsKey("b.nsuri"));
		assertFalse("Package C is already in the registry!",
			packageRegistry.containsKey("c.nsuri"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		EcoreHelper.unregisterEcore(cEcorePath);
		EcoreHelper.unregisterEcore(aEcorePath);
		EcoreHelper.unregisterEcore(bEcorePath);

	}

}
