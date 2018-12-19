/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.common.callback.ViewModelPropertiesUpdateCallback;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.validation.ValidationProvider;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.editor.ecore.EcoreEditor;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Performance tests for the <em>EMF Forms GenericEditor</em>, using the Ecore editor
 * as test subject.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings({ "nls", "restriction" })
public class EcoreEditorPerformance_PTest {

	private static final char[] NAME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_"
		.toCharArray();
	private static final char[] NAME_CHARS_NO_DIGITS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_"
		.toCharArray();

	private static final String SMALL_FILE_NAME = "SmallScale.ecore";
	private static final String LARGE_FILE_NAME = "LargeScale.ecore";

	private static final int SMALL_SCALE = 50;
	private static final int LARGE_SCALE = 3000;

	private static final int ITERATIONS = 10;

	// Something about the Linux environment seems to add to the cost (window manager?)
	// even in a virtual machine on a Mac host. So, considering that the large model has
	// 60 times the number of elements as the small model, a worst-case factor of 30
	// could be considered generous for an expected linear scaling of performance (even
	// though experiments on a local Mac are shown to be much better than this)
	private static final double WORST_CASE_MULTIPLIER = Platform.WS_GTK.equals(Platform.getWS())
		? 30.0
		: 10.0;

	private static Random random = new Random(System.currentTimeMillis());

	@Rule
	public final ProjectRule project = new ProjectRule();

	/**
	 * Initializes me.
	 */
	public EcoreEditorPerformance_PTest() {
		super();
	}

	/**
	 * Regression test for <a href="http://eclip.se/533568">bug 533568</a> in which the
	 * {@link GenericEditor} leaks an instance of the {@link TreeMasterDetailComposite} and
	 * its attendant "limbo" shell after the editor is closed, until workbench shutdown.
	 */
	@Test
	@TestResource
	public void treeMasterDetailCompositeDoesNotLeak() {
		final int expectedShellCount = Display.getDefault().getShells().length;

		final GenericEditor editor = open("test.ecore");
		close(editor);

		final int actualShellCount = Display.getDefault().getShells().length;
		assertThat("Limbo shell remains", actualShellCount, is(expectedShellCount));
	}

	@Test
	@TestResource(value = { "template.ecore", "template.ecore" }, //
		generator = { EcoreGen.class, EcoreGen.class })
	public void addElement() {
		test(new Experiment() {

			@Override
			public void test(String filePath) {
				final GenericEditor editor = open(filePath);

				final EClassifier newEClassifier = addEClassifier(editor);
				reveal(editor, newEClassifier);

				close(editor);
			}
		});
	}

	@Test
	@TestResource(value = { "template.ecore", "template.ecore" }, //
		generator = { EcoreGen.class, EcoreGen.class })
	public void validation() {
		Validation.active = true;

		test(new Experiment() {

			@Override
			public void test(String filePath) {
				final GenericEditor editor = open(filePath);

				final EClassifier lastEClassifier = getLastEClassifier(editor);
				reveal(editor, lastEClassifier);

				close(editor);
			}
		});
	}

	//
	// Test framework
	//

	/**
	 * Import the test models indicated by the annotations on the {@code test} into the {@code project}.
	 */
	void importTestModels(Description test, IProject project, IProgressMonitor monitor)
		throws CoreException, IOException {

		final String[] paths = getTestResources(test);
		final TestResource.Generator[] generators = getGenerators(test);

		final SubMonitor sub = SubMonitor.convert(monitor, paths.length);
		final ResourceSet rset = new ResourceSetImpl();

		for (int i = 0; i < paths.length; i++) {
			final String path = paths[i];
			final TestResource.Generator generator = generators.length > i ? generators[i] : null;

			final URI uri = URI.createURI(
				"platform:/fragment/org.eclipse.emfforms.editor.ecore.test/data/" + path);

			final Resource resource = rset.getResource(uri, true);
			resource
				.setURI(URI.createPlatformResourceURI(String.format("%s/%s", project.getName(), path), true));
			if (generator != null) {
				generator.generate(resource, i, sub.newChild(1));
			}
			resource.save(null);

			if (generator == null) {
				// Didn't have a generator to advance the progress
				sub.worked(1);
			}
		}

		for (final Resource next : rset.getResources()) {
			next.unload();
		}
		rset.getResources().clear();
		rset.eAdapters().clear();
	}

	/**
	 * Get the test resources indicated by the annotation on the given {@code test}.
	 *
	 * @param test a test
	 * @return its test resources
	 */
	static String[] getTestResources(Description test) {
		final TestResource testResource = test.getAnnotation(TestResource.class);
		return testResource.value();
	}

	/**
	 * Get the test data generators indicated by the annotation on the given {@code test}.
	 *
	 * @param test a test
	 * @return its test generators, or an empty array if none
	 */
	static TestResource.Generator[] getGenerators(Description test) {
		final TestResource testResource = test.getAnnotation(TestResource.class);
		final Class<? extends TestResource.Generator>[] generatorClasses = testResource.generator();
		final TestResource.Generator[] result = new TestResource.Generator[generatorClasses.length];

		// BEGIN COMPLEX CODE
		for (int i = 0; i < generatorClasses.length; i++) {
			try {
				result[i] = generatorClasses[i].newInstance();
			} catch (final Exception e) {
				e.printStackTrace();
				fail("Failed to create test resource generator: " + e.getMessage());
			}
		}
		// END COMPLEX CODE

		return result;
	}

	@BeforeClass
	public static void closeIntroView() {
		final IViewPart introView = getActivePage().findView("org.eclipse.ui.internal.introview");
		if (introView != null) {
			introView.getSite().getPage().hideView(introView);
		}
	}

	@After
	public void closeAllEditors() {
		getActivePage().closeAllEditors(false);
	}

	@After
	public void ensureNoValidation() {
		Validation.active = false;
	}

	/**
	 * Run an {@code experiment} on both the small- and the large-scale test model, measuring
	 * the performance of each, and compare the performance to verify that it's not worse
	 * than the expected worst case multiplier.
	 *
	 * @param experiment the experiment to run at each scale of model
	 */
	final void test(Experiment experiment) {
		class RunnableExperiment implements Runnable {
			private final String filePath;
			private final Experiment experiment;

			RunnableExperiment(String filePath, Experiment experiment) {
				super();

				this.filePath = filePath;
				this.experiment = experiment;
			}

			@Override
			public void run() {
				experiment.test(filePath);
			}
		}

		final Measure smallScale = time(ITERATIONS, new RunnableExperiment(SMALL_FILE_NAME, experiment));
		System.out.println("Small scale: " + smallScale);
		final Measure largeScale = time(ITERATIONS, new RunnableExperiment(LARGE_FILE_NAME, experiment));
		System.out.println("Large scale: " + largeScale);

		if (largeScale.average > WORST_CASE_MULTIPLIER * smallScale.average) {
			fail(String.format("Performance does not scale: %s ≫ %s", largeScale, smallScale));
		}
	}

	/**
	 * Run an {@code experiment} several times to compute the average elapsed time
	 * with standard deviation.
	 */
	Measure time(int iterations, Runnable experiment) {
		final int count = Math.max(7, iterations); // We toss high and low so need at least five
		final int n = count - 2;

		final double[] samples = new double[count];

		for (int i = 0; i < count; i++) {
			final long start = System.nanoTime();
			experiment.run();
			final long end = System.nanoTime();
			samples[i] = (end - start) / 1000000d;
		}

		Arrays.sort(samples);

		final int last = count - 1;
		double sum = 0.0;
		for (int i = 1; i < last; i++) {
			sum = sum + samples[i];
		}

		final double average = sum / n;
		double sumdev = 0.0;
		for (int i = 1; i < last; i++) {
			final double dev = samples[i] - average;
			sumdev = sumdev + dev * dev;
		}
		final double stddev = Math.sqrt(sumdev / (n - 1));

		return new Measure(average, stddev);
	}

	GenericEditor open(String fileName) {
		return open(project.getProject().getFile(fileName));
	}

	GenericEditor open(IFile file) {
		try {
			final IEditorPart result = IDE.openEditor(getActivePage(), file,
				"org.eclipse.emfforms.editor.ecore.test.Editor");
			return (GenericEditor) result;
		} catch (final PartInitException e) {
			e.printStackTrace();
			fail("Failed to open editor: " + e.getMessage());
			return null; // Unreachable
		} finally {
			flushUIEvents();
		}
	}

	void close(IEditorPart editor) {
		editor.getSite().getPage().closeEditor(editor, false);

		flushUIEvents();
	}

	static IWorkbenchPage getActivePage() {
		final IWorkbench bench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = bench.getActiveWorkbenchWindow();
		if (window == null) {
			window = bench.getWorkbenchWindows()[0];
		}
		return window.getActivePage();
	}

	/**
	 * Add an {@link EClassifier} in an {@code editor}.
	 *
	 * @return the newly added classifier
	 */
	EClassifier addEClassifier(GenericEditor editor) {
		final EditingDomain domain = editor.getEditingDomain();
		final EPackage ePackage = (EPackage) EcoreUtil.getObjectByType(
			domain.getResourceSet().getResources().get(0).getContents(),
			EcorePackage.Literals.EPACKAGE);
		final EClassifier result = EcoreFactory.eINSTANCE.createEClass();
		result.setName("NewClass");
		final Command command = AddCommand.create(domain, ePackage, EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
			result);
		domain.getCommandStack().execute(command);

		flushUIEvents();

		return result;
	}

	/**
	 * Get the last {@link EClassifier} in an {@code editor}.
	 *
	 * @return the last classifier
	 */
	EClassifier getLastEClassifier(GenericEditor editor) {
		final EditingDomain domain = editor.getEditingDomain();
		final EPackage ePackage = (EPackage) EcoreUtil.getObjectByType(
			domain.getResourceSet().getResources().get(0).getContents(),
			EcorePackage.Literals.EPACKAGE);

		final List<EClassifier> classifiers = ePackage.getEClassifiers();
		return classifiers.get(classifiers.size() - 1);
	}

	/**
	 * Reveal an {@code object} in the {@code editor}.
	 */
	void reveal(GenericEditor editor, EObject object) {
		editor.reveal(object);
		flushUIEvents();
	}

	/**
	 * Generate EClassifiers in an Ecore model.
	 */
	static void generateEClassifiers(Resource resource, String name, int size, boolean forValidation,
		IProgressMonitor monitor)
		throws CoreException, IOException {

		final SubMonitor sub = SubMonitor.convert(monitor, size);

		final EPackage ePackage = (EPackage) EcoreUtil.getObjectByType(resource.getContents(),
			EcorePackage.Literals.EPACKAGE);
		resource.setURI(resource.getURI().trimSegments(1).appendSegment(name));
		generateClassifiers(ePackage, size, forValidation, sub.newChild(size));
	}

	static void generateClassifiers(EPackage ePackage, int count, boolean forValidation, IProgressMonitor monitor) {
		monitor.beginTask("Generating station content...", count);

		final List<EClassifier> classifiers = ePackage.getEClassifiers();
		for (int i = 0; i < count; i++) {
			EClassifier classifier;
			if (random.nextDouble() >= 0.8) {
				// Relatively smaller proportion of data types
				classifier = random.nextBoolean() ? EcoreFactory.eINSTANCE.createEEnum()
					: EcoreFactory.eINSTANCE.createEDataType();

				if (classifier instanceof EEnum) {
					// It must have at least one literal
					final EEnumLiteral literal = EcoreFactory.eINSTANCE.createEEnumLiteral();
					literal.setName("literal");
					((EEnum) classifier).getELiterals().add(literal);
				} else {
					// It must have an instance class
					classifier.setInstanceClass(Void.class);
				}
			} else {
				final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
				eClass.setAbstract(random.nextBoolean());
				classifier = eClass;
			}

			// Only allow digits in tests with validation, so that otherwise we won't
			// waste effort on presenting validation errors for EClassifiers whose names
			// start with numeric digits (which makes for invalid Java names)
			classifier.setName(randomName(forValidation));
			classifiers.add(classifier);
		}
	}

	static String randomName(boolean withDigits) {
		// We have a constraint that checks for even number of characters in strings in abstract classes
		final int length = random.nextBoolean() ? 9 : 10;
		final char[] result = new char[length];

		final char[] charExtent = withDigits ? NAME_CHARS : NAME_CHARS_NO_DIGITS;

		for (int i = 0; i < length; i++) {
			result[i] = charExtent[random.nextInt(charExtent.length)];
		}

		return new String(result);
	}

	static void flushUIEvents() {
		final Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
			// Nothing to do
		}
	}

	//
	// Nested types
	//

	/**
	 * Annotates a test with the resources that it needs to import into the test project.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface TestResource {
		/**
		 * Paths within the {@code data/} folder of resources to import into the test project.
		 */
		String[] value() default { "test.ecore" };

		/**
		 * Optional generators to run on each test resource. If specified, there must be
		 * exactly one value per resource in the {@link #value()}.
		 *
		 * @return resource generators
		 */
		Class<? extends Generator>[] generator() default {};

		interface Generator {
			void generate(Resource resource, int index, IProgressMonitor monitor) throws IOException, CoreException;
		}
	}

	public final class ProjectRule extends TestWatcher {
		private IProject project;

		private List<IFile> files;

		/**
		 * @return the project
		 */
		public IProject getProject() {
			return project;
		}

		/**
		 * @return the files
		 */
		public List<IFile> getFiles() {
			return files;
		}

		@Override
		protected void starting(final Description description) {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getMethodName());

			try {
				if (project.exists()) {
					flushUIEvents();
					project.delete(true, null);
				}
				flushUIEvents();
				project.create(null);
				project.open(null);

				project.getWorkspace().run(new IWorkspaceRunnable() {

					@Override
					public void run(IProgressMonitor monitor) throws CoreException {
						try {
							importTestModels(description, project, monitor);
						} catch (final IOException e) {
							final Bundle bundle = FrameworkUtil.getBundle(getClass());
							String message = e.getMessage();
							if (message == null || message.isEmpty()) {
								message = "Unknown I/O exception.";
							}
							throw new CoreException(
								new Status(IStatus.ERROR, bundle.getSymbolicName(), message, e));
						}
					}
				}, new NullProgressMonitor());

				flushUIEvents();

				// Generators can change the file names
				files = new ArrayList<IFile>(getTestResources(description).length);
				for (final IResource next : project.members()) {
					if (next.getType() == IResource.FILE) {
						files.add((IFile) next);
					}
				}
			} catch (final CoreException e) {
				e.printStackTrace();
				fail("Failed to create test project: " + e.getStatus().getMessage());
			}
		}

		@Override
		protected void finished(Description description) {
			flushUIEvents();
			try {
				project.delete(true, null);
			} catch (final CoreException e) {
				e.printStackTrace();
			}
			flushUIEvents();
		}
	}

	/**
	 * Protocol for a measured (timed) experiment.
	 *
	 * @author Christian W. Damus
	 */
	interface Experiment {
		void test(String filePath);
	}

	static final class Measure {
		// BEGIN COMPLEX CODE
		final double average;
		final double stddev;
		// END COMPLEX CODE

		Measure(double average, double stddev) {
			super();

			this.average = average;
			this.stddev = stddev;
		}

		@Override
		public String toString() {
			return String.format("%.1f ms (σ = %.2f ms)", average, stddev);
		}
	}

	/**
	 * Subclass of the <em>Ecore Editor</em> that injects view-model properties to select our
	 * custom {@link EPackage} view.
	 */
	public static final class Editor extends EcoreEditor {
		@Override
		protected TreeMasterDetailComposite createTreeMasterDetail(Composite composite, Object editorInput,
			CreateElementCallback createElementCallback) {

			final TreeMasterDetailComposite result = super.createTreeMasterDetail(composite, editorInput,
				createElementCallback);
			result.addViewModelPropertiesUpdateCallback(new ViewModelPropertiesUpdateCallback() {

				@Override
				public void updateViewModelProperties(VViewModelProperties properties) {
					// Pick our special views
					properties.addNonInheritableProperty("perftest", true);
				}
			});
			return result;
		}

	}

	/**
	 * A dummy validation provider that complains about an {@link EClass} that is
	 * a {@link EClass#isAbstract() abstract} and has a {@link ENamedElement#getName() name}
	 * that has an odd number of characters.
	 */
	public static final class Validation implements ValidationProvider {
		private static boolean active;

		@Override
		public List<Diagnostic> validate(EObject eObject) {
			if (!active || !(eObject instanceof EClass)) {
				return Collections.emptyList();
			}

			Diagnostic result;

			final EClass eClass = (EClass) eObject;
			if (!eClass.isAbstract() || !hasOddLength(eClass.getName())) {
				result = Diagnostic.OK_INSTANCE;
			} else {
				result = new BasicDiagnostic(Diagnostic.ERROR, "test", 1,
					"Name has odd length.", new Object[] { eClass, EcorePackage.Literals.ENAMED_ELEMENT__NAME });
			}

			return Collections.singletonList(result);
		}

		private boolean hasOddLength(String string) {
			return string != null && string.length() % 2 != 0;
		}
	}

	/**
	 * Generates from the template resource two Ecore models: a small-scale model and a large-scale model.
	 */
	static final class EcoreGen implements TestResource.Generator {

		@Override
		public void generate(Resource resource, int index, IProgressMonitor monitor) throws IOException, CoreException {
			// Is it a validation test?
			final boolean forValidation = resource.getURI().segmentsList().contains("validation");
			if (index == 0) {
				generateEClassifiers(resource, SMALL_FILE_NAME, SMALL_SCALE, forValidation, monitor);
			} else {
				generateEClassifiers(resource, LARGE_FILE_NAME, LARGE_SCALE, forValidation, monitor);
			}
		}

	}
}
