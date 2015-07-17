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

public class EcoreHelperCyclicDependencies_PTest {
	private final Registry packageRegistry = EPackage.Registry.INSTANCE;
	private static String dEcorePath = "/TestEcoreHelperProjectResources/D.ecore";
	private static String eEcorePath = "/TestEcoreHelperProjectResources/E.ecore";
	private static String fEcorePath = "/TestEcoreHelperProjectResources/F.ecore";

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
		assertFalse("Package D is already in the registry!",
			packageRegistry.containsKey("d.nsuri"));
		assertFalse("Package E is already in the registry!",
			packageRegistry.containsKey("e.nsuri"));
		assertFalse("Package F is already in the registry!",
			packageRegistry.containsKey("f.nsuri"));

		// register D references E references F references D
		EcoreHelper.registerEcore(dEcorePath);
		assertTrue("Package D not in the registry!",
			packageRegistry.containsKey("d.nsuri"));
		assertTrue("Package E not in the registry!",
			packageRegistry.containsKey("e.nsuri"));
		assertTrue("Package F not in the registry!",
			packageRegistry.containsKey("f.nsuri"));

		// unregister
		EcoreHelper.unregisterEcore(dEcorePath);
		assertFalse("Package D is still in the registry!",
			packageRegistry.containsKey("d.nsuri"));
		assertFalse("Package E is still in the registry!",
			packageRegistry.containsKey("e.nsuri"));
		assertFalse("Package F is still in the registry!",
			packageRegistry.containsKey("f.nsuri"));
	}

	@Test
	public void testUnregisterMultipleUsage() throws IOException {
		// setup
		EcoreHelper.registerEcore(dEcorePath);
		EcoreHelper.registerEcore(eEcorePath);
		EcoreHelper.registerEcore(fEcorePath);
		assertTrue("Package D not in the registry!",
			packageRegistry.containsKey("d.nsuri"));
		assertTrue("Package E not in the registry!",
			packageRegistry.containsKey("e.nsuri"));
		assertTrue("Package F not in the registry!",
			packageRegistry.containsKey("f.nsuri"));

		// unregister D references E
		EcoreHelper.unregisterEcore(dEcorePath);
		assertTrue("Package D not in the registry!",
			packageRegistry.containsKey("d.nsuri"));
		assertTrue("Package E not in the registry!",
			packageRegistry.containsKey("e.nsuri"));
		assertTrue("Package F not in the registry!",
			packageRegistry.containsKey("f.nsuri"));

		// unregister E references F
		EcoreHelper.unregisterEcore(eEcorePath);
		assertTrue("Package D not in the registry!",
			packageRegistry.containsKey("d.nsuri"));
		assertTrue("Package E not in the registry!",
			packageRegistry.containsKey("e.nsuri"));
		assertTrue("Package F not in the registry!",
			packageRegistry.containsKey("f.nsuri"));

		// unregister F references D
		EcoreHelper.unregisterEcore(fEcorePath);
		assertFalse("Package D is still in the registry!",
			packageRegistry.containsKey("d.nsuri"));
		assertFalse("Package E is still in the registry!",
			packageRegistry.containsKey("e.nsuri"));
		assertFalse("Package F is still in the registry!",
			packageRegistry.containsKey("f.nsuri"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		EcoreHelper.unregisterEcore(dEcorePath);
		EcoreHelper.unregisterEcore(eEcorePath);
		EcoreHelper.unregisterEcore(fEcorePath);
	}

}
