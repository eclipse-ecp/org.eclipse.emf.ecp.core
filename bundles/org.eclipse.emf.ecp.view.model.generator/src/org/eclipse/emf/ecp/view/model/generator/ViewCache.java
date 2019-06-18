/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar - initial API and implementation
 * Christian W. Damus - bug 547271
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.generator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * A cache of the generated {@link VView} for an {@code EObject}.
 * It is assumed that the valid features of an {@link EClass} are the same for all instances
 * of that class and will not change during its lifetime, in keeping with standard EMF
 * assumptions and the actual behaviour of generated models.
 */
final class ViewCache {

	private static final int CACHE_SIZE = 100;
	private static final Random RANDOMIZER = new Random();

	@SuppressWarnings("serial")
	private final Map<EClass, ViewRecord> views = new LinkedHashMap<EClass, ViewRecord>() {
		@Override
		protected boolean removeEldestEntry(java.util.Map.Entry<EClass, ViewRecord> eldest) {
			return size() > CACHE_SIZE;
		}
	};
	private final AdapterFactoryItemDelegator delegator;

	/**
	 * Initializes me.
	 */
	ViewCache() {
		super();

		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		composedAdapterFactory.insertAdapterFactory(new CustomReflectiveItemProviderAdapterFactory());

		delegator = new AdapterFactoryItemDelegator(composedAdapterFactory);
	}

	/**
	 * Get the generated view for an {@code object}.
	 *
	 * @param object an object for which to get the generated mutable view model
	 * @return the generated view model for the {@code object}
	 */
	VView getView(EObject object) {
		final EClass eClass = object.eClass();
		ViewRecord prototype = views.get(eClass);
		if (prototype == null) {
			prototype = generatePrototype(eClass);
			views.put(eClass, prototype);
		}

		final VView result = prototype.instantiate(object);
		return result;
	}

	private ViewRecord generatePrototype(EClass eClass) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setUuid(generateId(eClass, null));
		final ViewRecord result = new ViewRecord(view);

		final EObject example = EcoreUtil.create(eClass);
		final Predicate<EStructuralFeature> isValidFeature = feature -> isValidFeature(feature, example);
		eClass.getEAllStructuralFeatures().stream().filter(isValidFeature)
			.forEach(feature -> {
				final VControl control;
				if (isTableFeature(feature)) {
					control = VTableFactory.eINSTANCE.createTableControl();
					final VTableDomainModelReference tableDmr = VTableFactory.eINSTANCE
						.createTableDomainModelReference();
					tableDmr.setDomainModelReference(createModelReference(feature));
					control.setDomainModelReference(tableDmr);
				} else {
					control = VViewFactory.eINSTANCE.createControl();
					control.setDomainModelReference(createModelReference(feature));
				}
				control.setUuid(result.generateID(eClass, feature));
				view.getChildren().add(control);

				// If it was valid, then it had a property descriptor
				final IItemPropertyDescriptor propertyDescriptor = delegator.getPropertyDescriptor(example, feature);
				result.add(control, feature, propertyDescriptor);
			});

		// Let the adapter factory not leak the example instance
		example.eAdapters().clear();

		view.setRootEClass(eClass);

		return result;
	}

	private VDomainModelReference createModelReference(final EStructuralFeature feature) {
		final VFeaturePathDomainModelReference modelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		modelReference.setDomainModelEFeature(feature);
		return modelReference;
	}

	private boolean isTableFeature(EStructuralFeature feature) {
		if (feature instanceof EReference) {
			final EReference ref = (EReference) feature;
			return ref.isMany() && ref.isContainment();
		}
		return false;
	}

	private boolean isValidFeature(EStructuralFeature feature, EObject owner) {
		boolean result = !isInvalidFeature(feature);

		if (result) {
			// Further, check that there's a property descriptor
			result = delegator.getPropertyDescriptor(owner, feature) != null;
		}

		return result;
	}

	private boolean isInvalidFeature(EStructuralFeature feature) {
		return isContainerReference(feature) || isTransient(feature) || isVolatile(feature);
	}

	private boolean isContainerReference(EStructuralFeature feature) {
		if (feature instanceof EReference) {
			final EReference reference = (EReference) feature;
			if (reference.isContainer()) {
				return true;
			}
		}

		return false;
	}

	private boolean isTransient(EStructuralFeature feature) {
		return feature.isTransient();
	}

	private boolean isVolatile(EStructuralFeature feature) {
		return feature.isVolatile();
	}

	// this is not unique, because of the use of hashCode, so it needs to be post-processed
	private static String generateId(EClass eClass, EStructuralFeature feature) {
		final StringBuilder stringBuilder = new StringBuilder();
		final EPackage ePackage = eClass.getEPackage();
		if (ePackage != null) {
			/* might be null with dynamic emf */
			stringBuilder.append(ePackage.getNsURI());
		}
		stringBuilder.append("#"); //$NON-NLS-1$
		stringBuilder.append(eClass.getName());
		if (feature != null) {
			stringBuilder.append("#"); //$NON-NLS-1$
			stringBuilder.append(feature.getName());
		}
		return Integer.toHexString(stringBuilder.toString().hashCode());
	}

	//
	// Nested types
	//

	/**
	 * Internal tracking of a generated view model with metadata for calculation
	 * of enablement of individual controls according to the EMF.Edit property
	 * descriptor for each control as driven by a particular object in the editor.
	 */
	private static final class ViewRecord {
		private final Map<String, ControlRecord> controls = new HashMap<>();
		private final VView view;

		private final ViewCopier copier = new ViewCopier();

		ViewRecord(VView view) {
			super();

			this.view = view;
		}

		/**
		 * Generate an ID that is guaranteed to be unique within my view.
		 *
		 * @param eClass the owner class for which to generate the ID
		 * @param feature the feature for which to generate the ID
		 *
		 * @return the unique generated ID
		 */
		String generateID(EClass eClass, EStructuralFeature feature) {
			String result = ViewCache.generateId(eClass, feature);

			while (controls.containsKey(result)) {
				// mangle it
				int value = Integer.parseInt(result, 16);
				value = value ^ RANDOMIZER.nextInt();
				result = Integer.toHexString(value);
			}

			return result;
		}

		void add(VControl control, EStructuralFeature feature, IItemPropertyDescriptor propertyDescriptor) {
			controls.put(control.getUuid(), new ControlRecord(feature, propertyDescriptor));
		}

		VView instantiate(EObject object) {
			copier.setOwner(object);
			final VView result = (VView) copier.copy(view);
			copier.copyReferences();
			copier.clear();
			return result;
		}

		//
		// Nested types
		//

		/**
		 * A specialized copier that sets control enablement computed from
		 * the EMF.Edit property source for the control.
		 */
		@SuppressWarnings("serial")
		private final class ViewCopier extends EcoreUtil.Copier {

			private EObject owner;

			ViewCopier() {
				super();
			}

			@Override
			public void clear() {
				owner = null;
				super.clear();
			}

			@Override
			protected void copyAttribute(EAttribute eAttribute, EObject eObject, EObject copyEObject) {
				// Don't copy the read-only attribute; we calculate it
				if (eAttribute != VViewPackage.Literals.ELEMENT__READONLY) {
					super.copyAttribute(eAttribute, eObject, copyEObject);
				}

				if (eAttribute == VViewPackage.Literals.ELEMENT__UUID) {
					// We now have the UUID, so can compute enablement override
					if (copyEObject instanceof VControl) {
						final VControl control = (VControl) copyEObject;
						final ControlRecord record = controls.get(control.getUuid());
						if (record != null) {
							control.setReadonly(!record.isEditable(owner));
						}
					}
				}
			}

			/**
			 * Set the object for which we are copying the view model, to edit it.
			 *
			 * @param owner the owner object of the features to be edited
			 */
			void setOwner(EObject owner) {
				this.owner = owner;
			}
		}

	}

	/**
	 * A record tracking metadata about the structural feature edited by a control,
	 * in particular for determination of enablement according to its EMF.Edit item
	 * provider.
	 */
	private static final class ControlRecord {
		private final IItemPropertyDescriptor propertyDescriptor;
		private final boolean changeable;

		ControlRecord(EStructuralFeature feature, IItemPropertyDescriptor propertyDescriptor) {
			super();

			this.propertyDescriptor = propertyDescriptor;
			changeable = feature.isChangeable();
		}

		boolean isEditable(EObject owner) {
			return changeable && propertyDescriptor.canSetProperty(owner);
		}
	}

}
