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

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.ChangeFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.emfforms.internal.editor.ecore.referenceservices.EcoreAttachmentStrategyProvider;
import org.eclipse.emfforms.internal.editor.ecore.referenceservices.EcoreEObjectSelectionStrategyProvider;
import org.eclipse.emfforms.internal.editor.ecore.referenceservices.EcoreOpenInNewContextStrategyProvider;
import org.eclipse.emfforms.internal.editor.ecore.referenceservices.EcoreReferenceStrategyProvider;
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
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.osgi.framework.InvalidSyntaxException;

/**
 * Integration test cases for the Ecore Editor's reference service strategy provider classes.
 */
@SuppressWarnings({ "nls", "restriction" })
public class EcoreReferenceServiceCustomizationProviders_ITest {

	@Rule
	public final BazaarRule bazaar = new BazaarRule();

	private EPackage testPackage;

	/**
	 * Initializes me.
	 */
	public EcoreReferenceServiceCustomizationProviders_ITest() {
		super();
	}

	@ProviderType(EcoreReferenceStrategyProvider.class)
	@Test
	public void setEOppositeReference() throws InvalidSyntaxException {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClass abstrakt = (EClass) testPackage.getEClassifier("Abstract");
		final EReference ref = (EReference) foo.getEStructuralFeature("ref");
		final EReference opposite = EcoreFactory.eINSTANCE.createEReference();
		abstrakt.getEStructuralFeatures().add(opposite);

		final ReferenceStrategy strategy = bazaar.getStrategy(foo, EcorePackage.Literals.EREFERENCE__EOPPOSITE);

		strategy.addElementsToReference(ref, EcorePackage.Literals.EREFERENCE__EOPPOSITE, singleton(opposite));

		assertThat("Reference's opposite not set", ref.getEOpposite(), is(opposite));
		assertThat("Opposite's opposite not set", opposite.getEOpposite(), is(ref));
		assertThat("Opposite's type not set", opposite.getEReferenceType(), is(foo));
		assertThat("Reference's type not set", ref.getEReferenceType(), is(abstrakt));
	}

	@ProviderType(EcoreAttachmentStrategyProvider.class)
	@Test
	public void attachOppositeReference() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClass abstrakt = (EClass) testPackage.getEClassifier("Abstract");
		final EReference ref = (EReference) foo.getEStructuralFeature("ref");
		ref.setEType(abstrakt);
		final EReference opposite = EcoreFactory.eINSTANCE.createEReference();

		final AttachmentStrategy strategy = bazaar.getStrategy(foo, EcorePackage.Literals.EREFERENCE__EOPPOSITE);

		strategy.addElementToModel(ref, EcorePackage.Literals.EREFERENCE__EOPPOSITE, opposite);

		assertThat("Opposite not added to its owner", opposite.getEContainingClass(), is(abstrakt));
		assertThat("Opposite's opposite not set", opposite.getEOpposite(), is(ref));
		assertThat("Opposite's type not set", opposite.getEReferenceType(), is(foo));
		assertThat("Opposite's name not set", opposite.getName(), notNullValue());
	}

	@ProviderType(EcoreEObjectSelectionStrategyProvider.class)
	@Test
	public void selectExistingEOpposite_refUntyped() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClass abstrakt = (EClass) testPackage.getEClassifier("Abstract");
		final EReference ref = (EReference) foo.getEStructuralFeature("ref");
		final EReference other = EcoreFactory.eINSTANCE.createEReference();
		other.setName("other");
		other.setEType(foo);
		foo.getEStructuralFeatures().add(other);
		final EReference opposite = EcoreFactory.eINSTANCE.createEReference();
		abstrakt.getEStructuralFeatures().add(opposite);

		final EObjectSelectionStrategy strategy = bazaar.getStrategy(foo, EcorePackage.Literals.EREFERENCE__EOPPOSITE);

		Collection<EObject> selection = everything();
		selection = strategy.collectExistingObjects(ref, EcorePackage.Literals.EREFERENCE__EOPPOSITE, selection);

		assertThat(selection, everyItem(CoreMatchers.<EObject> instanceOf(EReference.class)));
		assertThat(selection, hasItem(opposite));
		assertThat(selection, not(hasItem(ref)));
		assertThat(selection, hasItem(other)); // Could be an opposite, in theory
	}

	@ProviderType(EcoreEObjectSelectionStrategyProvider.class)
	@Test
	public void selectExistingEOpposite_refHasType() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClass abstrakt = (EClass) testPackage.getEClassifier("Abstract");
		final EReference ref = (EReference) foo.getEStructuralFeature("ref");
		final EReference other = EcoreFactory.eINSTANCE.createEReference();
		other.setName("other");
		other.setEType(foo);
		foo.getEStructuralFeatures().add(other);
		ref.setEType(abstrakt); // This determines the type that may contain opposites
		final EReference opposite = EcoreFactory.eINSTANCE.createEReference();
		abstrakt.getEStructuralFeatures().add(opposite);

		final EObjectSelectionStrategy strategy = bazaar.getStrategy(foo, EcorePackage.Literals.EREFERENCE__EOPPOSITE);

		Collection<EObject> selection = everything();
		selection = strategy.collectExistingObjects(ref, EcorePackage.Literals.EREFERENCE__EOPPOSITE, selection);

		assertThat(selection, everyItem(CoreMatchers.<EObject> instanceOf(EReference.class)));
		assertThat(selection, hasItem(opposite));
		assertThat(selection, not(hasItem(ref)));
		assertThat(selection, not(hasItem(other))); // Not in the ref's type
	}

	@ProviderType(EcoreEObjectSelectionStrategyProvider.class)
	@Test
	public void selectExistingSupertype() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClass abstrakt = (EClass) testPackage.getEClassifier("Abstract");
		final EClass subclass = EcoreFactory.eINSTANCE.createEClass();
		subclass.setName("Subtype");
		subclass.getESuperTypes().add(foo);
		testPackage.getEClassifiers().add(subclass);

		final EObjectSelectionStrategy strategy = bazaar.getStrategy(foo, EcorePackage.Literals.ECLASS__ESUPER_TYPES);

		Collection<EObject> selection = everything();
		selection = strategy.collectExistingObjects(foo, EcorePackage.Literals.ECLASS__ESUPER_TYPES, selection);

		assertThat(selection, everyItem(CoreMatchers.<EObject> instanceOf(EClass.class)));
		assertThat(selection, hasItem(abstrakt));
		assertThat(selection, not(hasItem(subclass))); // Would cause cycle
	}

	@ProviderType(EcoreEObjectSelectionStrategyProvider.class)
	@Test
	public void selectExistingAttributeType() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClassifier intType = testPackage.getEClassifier("int");
		final EStructuralFeature attr = foo.getEStructuralFeature("attr");

		final EObjectSelectionStrategy strategy = bazaar.getStrategy(attr, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE);

		Collection<EObject> selection = everything();
		selection = strategy.collectExistingObjects(attr, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE, selection);

		assertThat(selection, everyItem(CoreMatchers.<EObject> instanceOf(EDataType.class)));
		assertThat(selection, hasItem(intType));
	}

	@ProviderType(EcoreEObjectSelectionStrategyProvider.class)
	@Test
	public void selectExistingAnnotationReference() {
		final EClass foo = (EClass) testPackage.getEClassifier("Foo");
		final EClassifier intType = testPackage.getEClassifier("int");
		final EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource("test");
		foo.getEAnnotations().add(annotation);
		final EObjectSelectionStrategy strategy = bazaar.getStrategy(annotation,
			EcorePackage.Literals.EANNOTATION__REFERENCES);

		final ChangeDescription change = ChangeFactory.eINSTANCE.createChangeDescription();
		foo.eResource().getContents().add(change);

		Collection<EObject> selection = everything();
		Assume.assumeThat(selection, hasItem(change));
		selection = strategy.collectExistingObjects(annotation, EcorePackage.Literals.EANNOTATION__REFERENCES,
			selection);

		assertThat(selection, everyItem(CoreMatchers.<EObject> instanceOf(ENamedElement.class)));
		assertThat(selection, hasItem(intType));
		assertThat(selection, not(hasItem(change)));
	}

	@ProviderType(EcoreOpenInNewContextStrategyProvider.class)
	@Test
	public void openInNewContext() {
		final EClassifier foo = testPackage.getEClassifier("Foo");
		final OpenInNewContextStrategy strategy = bazaar.getStrategy(foo, foo.eContainmentFeature());
		assertThat(strategy, notNullValue());

		final Set<Shell> expectedShells = new HashSet<Shell>(Arrays.asList(Display.getCurrent().getShells()));

		// If a dialog appears close it
		final boolean[] doIt = { true };
		final boolean[] sawDialog = { false };
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (!doIt[0]) {
					return;
				}

				for (final Shell next : Display.getCurrent().getShells()) {
					if (!expectedShells.contains(next)) {
						sawDialog[0] = true;
						next.close();
					}
				}
			}
		});

		strategy.openInNewContext(foo.eContainer(), foo.eContainmentFeature(), foo);

		// If the dialog didn't appear, don't try anything after the test suite has moved on
		doIt[0] = false;

		assertThat("Open strategy presented a dialog", sawDialog[0], is(false));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack());

		testPackage = createTestPackage();
		createResource(domain.getResourceSet(), testPackage);
	}

	@After
	public void destroyFixture() {
		testPackage = null;
	}

	EPackage createTestPackage() {
		final EPackage result = createPackage("test");
		final EClass foo = EcoreFactory.eINSTANCE.createEClass();
		foo.setName("Foo");
		result.getEClassifiers().add(foo);
		final EAttribute state = EcoreFactory.eINSTANCE.createEAttribute();
		state.setName("attr");
		foo.getEStructuralFeatures().add(state);
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setName("ref");
		foo.getEStructuralFeatures().add(ref);
		final EClass abstrakt = EcoreFactory.eINSTANCE.createEClass();
		abstrakt.setAbstract(true);
		abstrakt.setName("Abstract");
		result.getEClassifiers().add(abstrakt);
		final EDataType intType = EcoreFactory.eINSTANCE.createEDataType();
		intType.setName("int");
		intType.setInstanceTypeName("int");
		result.getEClassifiers().add(intType);
		return result;
	}

	void createResource(ResourceSet rset, EPackage ePackage) {
		final Resource resource = rset.createResource(URI.createURI(ePackage.getNsURI()).appendFileExtension("ecore"));
		resource.getContents().add(ePackage);
	}

	EPackage createPackage(String name) {
		final EPackage result = EcoreFactory.eINSTANCE.createEPackage();
		result.setName(name);
		result.setNsURI(String.format("http://test/%s.ecore", name));
		result.setNsPrefix(name);
		return result;
	}

	Collection<EObject> everything() {
		final Collection<EObject> result = new HashSet<EObject>();

		final ResourceSet rset = testPackage.eResource().getResourceSet();
		for (final Iterator<?> all = rset.getAllContents(); all.hasNext();) {
			final Object next = all.next();
			if (next instanceof EObject) {
				result.add((EObject) next);
			}
		}

		return result;
	}

	Runnable selectFirstElementInDialog(final boolean[] doIt) {
		return new Runnable() {

			@Override
			public void run() {
				if (doIt[0]) {
					Shell shell = Display.getCurrent().getActiveShell();
					if (shell == null) {
						final Shell[] all = Display.getCurrent().getShells();
						// Get the last opened
						if (all.length > 0) {
							shell = all[all.length - 1];
						}
					}
					if (shell != null) {
						selectFirstElement(shell);
						finishWizard(shell);
					}
				}
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

	/**
	 * Annotates a test method with the strategy provider class to inject as
	 * a vendor in the {@linkplain BazaarRule bazaar fixture}.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface ProviderType {
		Class<? extends Vendor<?>> value();
	}

	/**
	 * A test rule that creates a bazaar with the single vendor specified by the
	 * test's {@link ProviderType} annotation.
	 */
	static class BazaarRule implements TestRule {
		private Bazaar<?> bazaar;

		@Override
		public Statement apply(final Statement base, final Description description) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					starting(description);

					try {
						base.evaluate();
					} finally {
						finished(description);
					}
				}
			};
		}

		protected void starting(Description description) throws InstantiationException, IllegalAccessException {
			final Class<? extends Vendor<?>> strategyType = description
				.getAnnotation(ProviderType.class).value();

			final Vendor<?> provider = strategyType.newInstance();

			final Bazaar.Builder<?> builder = Bazaar.Builder.with(singleton(provider));
			bazaar = builder.build();
		}

		protected void finished(Description description) {
			bazaar = null;
		}

		<S> S getStrategy(EObject owner, EReference reference) {
			return getStrategy(owner, reference, null, null);
		}

		<S> S getStrategy(EObject owner, EReference reference, Object value) {
			return getStrategy(owner, reference, value == null ? null : value.getClass().getName(), value);
		}

		@SuppressWarnings("unchecked")
		<S> S getStrategy(EObject owner, EReference reference, String key,
			Object value) {
			final BazaarContext.Builder context = BazaarContext.Builder.empty();
			context.put(EObject.class, owner).put(EReference.class, reference);
			if (key != null) {
				context.put(key, value);
			}

			return (S) bazaar.createProduct(context.build());
		}
	}
}
