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
package org.eclipse.emf.ecp.ui.view.swt;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

/**
 * Test cases for the {@link DefaultReferenceService} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultReferenceService_PTest {

	private final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

	@Mock
	private ViewModelContext context;

	@Mock
	private OpenInNewContextStrategy openStrategy;

	private List<ServiceRegistration<?>> registrations;

	private DefaultReferenceServiceFactory factory;
	private DefaultReferenceService fixture;

	private EPackage testPackage;
	private EPackage typesPackage;

	/**
	 * Initializes me.
	 */
	public DefaultReferenceService_PTest() {
		super();
	}

	@Test
	public void addExistingModelElements() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClass abstrakt = (EClass) testPackage.getEClassifier("Abstract");

		// When the dialog appears, select the first element and okay
		final AtomicBoolean doIt = new AtomicBoolean(true);
		final Runnable selectRunnable = selectFirstElementInDialog(Display.getCurrent(), getShells(), doIt);
		exec.scheduleAtFixedRate(selectRunnable, 100L, 100L, TimeUnit.MILLISECONDS);

		fixture.addExistingModelElements(foo, EcorePackage.Literals.ECLASS__ESUPER_TYPES);

		// If the dialog didn't appear, don't try anything after the test suite has moved on
		doIt.set(false);

		// The reference was set to the only eligible choice
		assertThat(foo.getESuperTypes(), hasItem(abstrakt));

		// The open strategy is not involved
		verifyNoMoreInteractions(openStrategy);
	}

	@Test
	public void addExistingModelElements2() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EStructuralFeature state = foo.getEStructuralFeature("state");
		final EClassifier intType = typesPackage.getEClassifier("int");

		// A canary
		final EObjectSelectionStrategy canary = mock(EObjectSelectionStrategy.class);
		when(canary.collectExistingObjects(any(EObject.class), any(EReference.class), anyCollectionOf(EObject.class)))
			.then(new Answer<Collection<EObject>>() {
				@SuppressWarnings("unchecked")
				@Override
				public Collection<EObject> answer(InvocationOnMock invocation) throws Throwable {
					return (Collection<EObject>) invocation.getArguments()[2];
				}
			});
		register(vendor(canary));

		// When the dialog appears, select the first element and okay
		final AtomicBoolean doIt = new AtomicBoolean(true);
		final Runnable selectRunnable = selectFirstElementInDialog(Display.getCurrent(), getShells(), doIt);
		exec.scheduleAtFixedRate(selectRunnable, 100L, 100L, TimeUnit.MILLISECONDS);

		fixture.addExistingModelElements(state, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE);

		// If the dialog didn't appear, don't try anything after the test suite has moved on
		doIt.set(false);

		// Check the canary
		verify(canary).collectExistingObjects(eq(state), eq(EcorePackage.Literals.ETYPED_ELEMENT__ETYPE),
			anyCollectionOf(EObject.class));

		assertThat(state.getEType(), is(intType));
		assertThat(state.getLowerBound(), is(1)); // Our strategy's side-effect

		// The open strategy is not involved
		verifyNoMoreInteractions(openStrategy);
	}

	@Test
	public void addNewModelElements() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EStructuralFeature state = foo.getEStructuralFeature("state");

		// A canary
		final EClassSelectionStrategy canary = mock(EClassSelectionStrategy.class);
		when(canary.collectEClasses(any(EObject.class), any(EReference.class), anyCollectionOf(EClass.class)))
			.then(new Answer<Collection<EClass>>() {
				@SuppressWarnings("unchecked")
				@Override
				public Collection<EClass> answer(InvocationOnMock invocation) throws Throwable {
					return (Collection<EClass>) invocation.getArguments()[2];
				}
			});
		register(vendor(canary));

		fixture.addNewModelElements(state, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE);
		assertThat(state.getEType(), instanceOf(EEnum.class));
		final EEnum type = (EEnum) state.getEType();

		// Check the canary
		verify(canary).collectEClasses(eq(state), eq(EcorePackage.Literals.ETYPED_ELEMENT__ETYPE),
			anyCollectionOf(EClass.class));

		// It was created in the types package, not the package containing "foo"
		assertThat(type.getEPackage(), is(typesPackage));

		// The reference service "opened" this new object
		verify(openStrategy).openInNewContext(typesPackage, EcorePackage.Literals.EPACKAGE__ECLASSIFIERS, type);
	}

	@Test
	public void attachDefaultCase_inOwner() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		fixture.addNewModelElements(foo, EcorePackage.Literals.ECLASS__EOPERATIONS);
		assertThat(foo.getEOperations(), CoreMatchers.<EOperation> hasItem(anything()));
		final EOperation op = foo.getEOperations().get(0);

		assertThat(op.getEContainingClass(), is(foo));

		// The reference service "opened" this new object
		verify(openStrategy).openInNewContext(foo, EcorePackage.Literals.ECLASS__EOPERATIONS, op);
	}

	@Test
	public void attachDefaultCase_inAncestorOfOwner() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		fixture.addNewModelElements(foo, EcorePackage.Literals.ECLASS__ESUPER_TYPES);
		assertThat(foo.getESuperTypes(), CoreMatchers.<EClass> hasItem(anything()));
		final EClass supertype = foo.getESuperTypes().get(0);

		assertThat(supertype.getEPackage(), is(testPackage));

		// The reference service "opened" this new object
		verify(openStrategy).openInNewContext(testPackage, EcorePackage.Literals.EPACKAGE__ECLASSIFIERS, supertype);
	}

	@Test
	public void attachDefaultCase_inResource() {
		// Same as above, except there's no package: the class is directly in the resource
		final Resource resource = testPackage.eResource();
		resource.getContents().remove(testPackage);
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		testPackage.getEClassifiers().remove(foo);
		resource.getContents().add(foo);

		fixture.addNewModelElements(foo, EcorePackage.Literals.ECLASS__ESUPER_TYPES);
		assertThat(foo.getESuperTypes(), CoreMatchers.<EClass> hasItem(anything()));
		final EClass supertype = foo.getESuperTypes().get(0);

		assertThat(((InternalEObject) supertype).eDirectResource(), is(resource));

		// The reference service "opened" this new object
		verify(openStrategy).openInNewContext(null, null, supertype);
	}

	@Test
	public void openInNewContext() {
		fixture.openInNewContext(testPackage);

		// The reference service "opened" this "new" object, but it's in a resource
		verify(openStrategy).openInNewContext(null, null, testPackage);
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		// Ensure that we don't fall back to the default, which opens a dialog
		when(openStrategy.openInNewContext(any(EObject.class), any(EReference.class), any(EObject.class)))
			.thenReturn(true);

		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack());

		testPackage = createTestPackage();
		createResource(domain.getResourceSet(), testPackage);

		typesPackage = createTypesPackage();
		createResource(domain.getResourceSet(), typesPackage);

		when(context.getDomainModel()).thenReturn(testPackage);

		final ComponentContext componentContext = mock(ComponentContext.class);
		when(componentContext.getBundleContext()).thenReturn(FrameworkUtil.getBundle(getClass()).getBundleContext());
		when(componentContext.getProperties()).thenReturn(new Hashtable<String, Object>());

		factory = new DefaultReferenceServiceFactory();
		factory.activate(componentContext);
		registerReferenceCustomizations();

		final ReferenceService service = factory.createService(context);
		assertThat("Factory created wrong service implementation", service,
			instanceOf(DefaultReferenceService.class));

		fixture = (DefaultReferenceService) service;
	}

	@After
	public void destroyFixture() {
		exec.shutdown();

		fixture.dispose();
		factory.deactivate();
		unregisterReferenceCustomizations();
	}

	EPackage createTestPackage() {
		final EPackage result = createPackage("test");
		final EClass foo = EcoreFactory.eINSTANCE.createEClass();
		foo.setName("Foo");
		result.getEClassifiers().add(foo);
		final EAttribute state = EcoreFactory.eINSTANCE.createEAttribute();
		state.setName("state");
		foo.getEStructuralFeatures().add(state);
		final EClass abstrakt = EcoreFactory.eINSTANCE.createEClass();
		abstrakt.setAbstract(true);
		abstrakt.setName("Abstract");
		result.getEClassifiers().add(abstrakt);
		return result;
	}

	void createResource(ResourceSet rset, EPackage ePackage) {
		final Resource resource = rset.createResource(URI.createURI(ePackage.getNsURI()).appendFileExtension("ecore"));
		resource.getContents().add(ePackage);
	}

	EPackage createTypesPackage() {
		final EPackage result = createPackage("types");
		final EDataType intType = EcoreFactory.eINSTANCE.createEDataType();
		intType.setName("int");
		result.getEClassifiers().add(intType);
		return result;
	}

	EPackage createPackage(String name) {
		final EPackage result = EcoreFactory.eINSTANCE.createEPackage();
		result.setName(name);
		result.setNsURI(String.format("http://test/%s.ecore", name));
		result.setNsPrefix(name);
		return result;
	}

	/**
	 * Get all of the shells currently open at this instant.
	 *
	 * @return the currently open shells
	 */
	Set<Shell> getShells() {
		return new HashSet<Shell>(Arrays.asList(Display.getCurrent().getShells()));
	}

	/**
	 * Obtain a runnable that async-execs on the given {@code display} to find the first
	 * currently open shell that is not amongst the given {@code initialShells}, to
	 * make a selection in it and close it, so long as {@link doIt} remains {@code true}.
	 *
	 * @param display the display to which the resulting runnable should async-exec its action
	 * @param initialShells the shells that are/were open before starting the thread that
	 *            runs the resulting runnable
	 * @param doIt a flag that will turn off when the test no longer needs to look for the dialog
	 * 
	 * @return the dialog selection runnable
	 */
	Runnable selectFirstElementInDialog(final Display display, final Set<Shell> initialShells,
		final AtomicBoolean doIt) {
		final Runnable onUI = new Runnable() {

			@Override
			public void run() {
				if (doIt.get()) {
					Shell dialog = null;
					final Shell[] all = display.getShells();
					for (final Shell next : all) {
						if (!initialShells.contains(next)) {
							// This is the newly opened dialog
							dialog = next;
							break;
						}
					}

					if (dialog != null) {
						selectFirstElement(dialog);
						finishWizard(dialog);
					}
				}
			}
		};

		return new Runnable() {

			@Override
			public void run() {
				display.asyncExec(onUI);
			}
		};
	}

	static void selectFirstElement(Shell shell) {
		Widget notify = null;
		Item selected = null;

		final Table table = findControl(shell, Table.class);
		if (table != null) {
			table.setSelection(0);
			notify = table;
			selected = table.getItem(0);
		} else {
			final Tree tree = findControl(shell, Tree.class);
			if (tree != null) {
				tree.setSelection(tree.getItem(0));
				notify = tree;
				selected = tree.getItem(0);
			} else {
				throw new IllegalStateException("no table nor tree to select");
			}
		}

		// Have to fire notification for JFace viewer to pick up
		final Event event = new Event();
		event.type = SWT.Selection;
		event.widget = notify;
		event.item = selected;
		notify.notifyListeners(SWT.Selection, event);
	}

	static <C extends Control> C findControl(Composite composite, Class<C> type) {
		C result = null;

		if (type.isInstance(composite)) {
			result = type.cast(composite);
		} else {
			for (final Control child : composite.getChildren()) {
				if (child instanceof Composite) {
					result = findControl((Composite) child, type);
				} else if (type.isInstance(child)) {
					result = type.cast(child);
				}

				if (result != null) {
					break;
				}
			}
		}

		return result;
	}

	static void finishWizard(Shell shell) {
		final Dialog dialog = (Dialog) shell.getData();

		try {
			final Method buttonPressed = Dialog.class.getDeclaredMethod("buttonPressed", int.class);
			buttonPressed.setAccessible(true);
			buttonPressed.invoke(dialog, IDialogConstants.FINISH_ID);
			// BEGIN COMPLEX CODE
		} catch (final Exception e) {
			// END COMPLEX CODE
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void registerReferenceCustomizations() {

		registrations = Arrays.asList(
			register(new Attachment()),
			register(new Reference()),
			register(new EClassSelection()),
			register(new EObjectSelection()),
			register(new OpenInNewContext()));
	}

	void unregisterReferenceCustomizations() {
		for (final ServiceRegistration<?> reg : registrations) {
			reg.unregister();
		}
	}

	ServiceRegistration<?> register(ReferenceServiceCustomizationVendor<?> vendor) {
		final BundleContext ctx = FrameworkUtil.getBundle(getClass()).getBundleContext();

		final Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Constants.SERVICE_RANKING, Integer.MAX_VALUE);

		inject(factory, vendor);

		return ctx.registerService(Vendor.class, vendor, properties);
	}

	private void inject(Object target, Object dependency) {
		final Class<?> toInject = dependency.getClass();

		try {
			for (final Method next : target.getClass().getDeclaredMethods()) {
				if (next.getName().startsWith("add")) {
					final Class<?>[] parms = next.getParameterTypes();
					if (parms.length == 1 && parms[0].isAssignableFrom(toInject)) {
						next.setAccessible(true);
						next.invoke(target, dependency);
						return;
					}
				}
			}
			fail(String.format("No suitable injection of %s into %s", dependency.getClass().getSimpleName(),
				target.getClass().getSimpleName()));
		} catch (final IllegalAccessException e) {
			fail("Illegal access in injection");
		} catch (final InvocationTargetException e) {
			e.getTargetException().printStackTrace();
			fail("Injection failed");
		}
	}

	/**
	 * Obtain an attachment strategy that adds classifiers to the given package instead of
	 * the one in which contextx they are being created.
	 *
	 * @param ePackage the package in which to attach classifiers
	 * @return the attachment strategy
	 */
	final AttachmentStrategy createIn(final EPackage ePackage) {
		return new AttachmentStrategy() {

			@Override
			public boolean addElementToModel(EObject owner, EReference reference, EObject object) {
				if (object instanceof EClassifier) {
					ePackage.getEClassifiers().add((EClassifier) object);
					return true;
				}

				return false;
			}
		};
	}

	/**
	 * Obtain a reference strategy that sets an attribute's typed lower bound to one.
	 *
	 * @param ePackage the package in which to attach classifiers
	 * @return the attachment strategy
	 */
	ReferenceStrategy setTypedElementRequired() {
		return new ReferenceStrategy() {

			@Override
			public boolean addElementsToReference(EObject owner, EReference reference, Set<? extends EObject> objects) {
				if (reference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE && owner instanceof EAttribute) {
					((ETypedElement) owner).setLowerBound(1);
					DEFAULT.addElementsToReference(owner, reference, objects);
					return true;
				}

				return false;
			}

		};
	}

	/**
	 * Obtain a strategy that allows instantiation only of the given class, if any.
	 *
	 * @param eClass the only class permitted to be instantiated
	 * @return the strategy
	 */
	EClassSelectionStrategy onlyCreate(final EClass eClass) {
		return new EClassSelectionStrategy() {

			@Override
			public Collection<EClass> collectEClasses(EObject owner, EReference reference,
				Collection<EClass> eclasses) {
				eclasses.retainAll(Collections.singleton(eClass));
				return eclasses;
			}
		};
	}

	/**
	 * Obtain a strategy that allows selection only of objects contained within the given {@code ancestor}.
	 *
	 * @param ancestor an object in which to find the eligible existing objects to reference
	 * @return the strategy
	 */
	EObjectSelectionStrategy containedIn(final EObject ancestor) {
		return new EObjectSelectionStrategy() {

			@Override
			public Collection<EObject> collectExistingObjects(EObject owner, EReference reference,
				Collection<EObject> existingObjects) {
				for (final Iterator<EObject> iter = existingObjects.iterator(); iter.hasNext();) {
					if (!EcoreUtil.isAncestor(ancestor, iter.next())) {
						iter.remove();
					}
				}
				return existingObjects;
			}
		};
	}

	/**
	 * Obtain a strategy that allows selection only of abstract classes.
	 *
	 * @return the strategy
	 */
	EObjectSelectionStrategy onlyAbstractEClasses() {
		return new EObjectSelectionStrategy() {

			@Override
			public Collection<EObject> collectExistingObjects(EObject owner, EReference reference,
				Collection<EObject> existingObjects) {
				for (final Iterator<EObject> iter = existingObjects.iterator(); iter.hasNext();) {
					final EObject next = iter.next();
					if (!(next instanceof EClass) || !((EClass) next).isAbstract()) {
						iter.remove();
					}
				}
				return existingObjects;
			}
		};
	}

	ReferenceServiceCustomizationVendor<EClassSelectionStrategy> vendor(final EClassSelectionStrategy strategy) {
		class StaticProvider extends ReferenceServiceCustomizationVendor<EClassSelectionStrategy>
			implements EClassSelectionStrategy.Provider {
			@Create
			public EClassSelectionStrategy create() {
				return strategy;
			}
		}

		return new StaticProvider();
	}

	ReferenceServiceCustomizationVendor<EObjectSelectionStrategy> vendor(final EObjectSelectionStrategy strategy) {
		class StaticProvider extends ReferenceServiceCustomizationVendor<EObjectSelectionStrategy>
			implements EObjectSelectionStrategy.Provider {
			@Create
			public EObjectSelectionStrategy create() {
				return strategy;
			}
		}

		return new StaticProvider();
	}

	//
	// Nested types
	//

	private class Attachment extends ReferenceServiceCustomizationVendor<AttachmentStrategy>
		implements AttachmentStrategy.Provider {

		@Override
		protected boolean handles(EObject owner, EReference reference) {
			return reference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE && owner instanceof EAttribute;
		}

		@Create
		public AttachmentStrategy create() {
			return createIn(typesPackage);
		}
	}

	private class Reference extends ReferenceServiceCustomizationVendor<ReferenceStrategy>
		implements ReferenceStrategy.Provider {

		@Override
		protected boolean handles(EObject owner, EReference reference) {
			return reference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE && owner instanceof EAttribute;
		}

		@Create
		public ReferenceStrategy create() {
			return setTypedElementRequired();
		}
	}

	private class EClassSelection extends ReferenceServiceCustomizationVendor<EClassSelectionStrategy>
		implements EClassSelectionStrategy.Provider {

		@Override
		protected boolean handles(EObject owner, EReference reference) {
			return reference == EcorePackage.Literals.ECLASS__ESUPER_TYPES
				|| reference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE && owner instanceof EAttribute;
		}

		@Create
		public EClassSelectionStrategy create(EObject owner, EReference reference) {
			EClassSelectionStrategy result = null;

			if (reference == EcorePackage.Literals.ECLASS__ESUPER_TYPES) {
				result = onlyCreate(EcorePackage.Literals.ECLASS);
			} else if (reference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE
				&& owner instanceof EAttribute) {

				result = onlyCreate(EcorePackage.Literals.EENUM);
			}

			return result;
		}
	}

	private class EObjectSelection extends ReferenceServiceCustomizationVendor<EObjectSelectionStrategy>
		implements EObjectSelectionStrategy.Provider {

		@Override
		protected boolean handles(EObject owner, EReference reference) {
			return reference == EcorePackage.Literals.ECLASS__ESUPER_TYPES
				|| reference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE && owner instanceof EAttribute;
		}

		@Create
		public EObjectSelectionStrategy create(EObject owner, EReference reference) {
			EObjectSelectionStrategy result = null;

			if (reference == EcorePackage.Literals.ECLASS__ESUPER_TYPES) {
				result = onlyAbstractEClasses();
			} else if (reference == EcorePackage.Literals.ETYPED_ELEMENT__ETYPE
				&& owner instanceof EAttribute) {

				result = containedIn(typesPackage);
			}

			return result;
		}
	}

	private class OpenInNewContext extends ReferenceServiceCustomizationVendor<OpenInNewContextStrategy>
		implements OpenInNewContextStrategy.Provider {

		@Create
		public OpenInNewContextStrategy create() {
			return openStrategy;
		}
	}
}
