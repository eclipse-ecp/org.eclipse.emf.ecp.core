/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.stack.ui.swt;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;

/**
 * {@link ViewModelService} evaluating changes on the {@link VDomainModelReference} of the {@link VStackLayout} based on
 * the given value in the available {@link VStackItem VStackItems}. Sets the top element of the VStackLayout
 * accordingly.
 *
 * @author jfaltermeier
 *
 */
public class StackItemViewService implements ViewModelService {

	private ModelChangeListener domainListener;
	private ModelChangeListener stackItemsDomainListener;
	private ViewModelContext context;
	private VElement viewModel;
	private EObject domain;

	private Map<EObject, Map<EStructuralFeature, Set<VStackLayout>>> registry;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		viewModel = context.getViewModel();
		domain = context.getDomainModel();

		registry = new LinkedHashMap<EObject, Map<EStructuralFeature, Set<VStackLayout>>>();

		initRegistry(viewModel);
		evaluateRegistry();
		domainListener = createDomainListener();
		context.registerDomainChangeListener(domainListener);
	}

	private void initRegistry(VElement viewModel) {
		final Set<VStackLayout> stacks = new LinkedHashSet<VStackLayout>();

		final TreeIterator<EObject> iterator = viewModel.eAllContents();
		while (iterator.hasNext()) {
			final EObject current = iterator.next();
			if (VStackLayout.class.isInstance(current)) {
				stacks.add(VStackLayout.class.cast(current));
			}
		}

		final Map<VStackLayout, Setting> stackToSetting = new LinkedHashMap<VStackLayout, Setting>();
		for (final VStackLayout stack : stacks) {
			final VDomainModelReference dmr = stack.getDomainModelReference();
			if (dmr == null) {
				continue;
			}
			final Setting setting = addToRegistry(stack, dmr);
			if (setting == null) {
				// TODO JF how to handle?
				return;
			}
			stackToSetting.put(stack, setting);
		}
		stackItemsDomainListener = new StackItemsModelChangeListener(stackToSetting);
		context.registerDomainChangeListener(stackItemsDomainListener);
	}

	private Setting addToRegistry(VStackLayout stack, VDomainModelReference dmr) {
		IObservableValue observableValue;
		try {
			observableValue = Activator.getDefault().getEMFFormsDatabinding().getObservableValue(dmr, domain);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			// TODO JF how to handle?
			return null;
		}
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();
		if (!eObject.eClass().getEAllStructuralFeatures().contains(structuralFeature)) {
			return null;
		}

		addToRegistry(eObject, structuralFeature, stack);
		return ((InternalEObject) eObject).eSetting(structuralFeature);
	}

	private void addToRegistry(EObject object, final EStructuralFeature domainModelEFeature, final VStackLayout stack) {
		if (!registry.containsKey(object)) {
			registry.put(object, new LinkedHashMap<EStructuralFeature, Set<VStackLayout>>());
		}
		final Map<EStructuralFeature, Set<VStackLayout>> featureToLayoutMap = registry.get(object);
		if (!featureToLayoutMap.containsKey(domainModelEFeature)) {
			featureToLayoutMap.put(domainModelEFeature, new LinkedHashSet<VStackLayout>());
		}
		featureToLayoutMap.get(domainModelEFeature).add(stack);
	}

	private boolean doesRegistryContain(EObject object, final EStructuralFeature domainModelEFeature) {
		if (!registry.containsKey(object)) {
			return false;
		}
		final Map<EStructuralFeature, Set<VStackLayout>> featureToStackMap = registry.get(object);
		return featureToStackMap.containsKey(domainModelEFeature);
	}

	private void evaluateRegistry() {
		for (final EObject object : registry.keySet()) {
			final Map<EStructuralFeature, Set<VStackLayout>> featureToStacksMap = registry.get(object);
			for (final EStructuralFeature feature : featureToStacksMap.keySet()) {
				evaluate(object, feature);
			}
		}
	}

	private void evaluate(EObject object, final EStructuralFeature domainModelEFeature) {
		final Object currentValue = object.eGet(domainModelEFeature);
		final Set<VStackLayout> stacks = registry.get(object).get(domainModelEFeature);
		for (final VStackLayout stack : stacks) {
			boolean topElementSet = false;
			for (final VStackItem item : stack.getStackItems()) {
				if (currentValue == null) {
					if (currentValue == item.getValue()) {
						stack.setTopElement(item);
						topElementSet = true;
						break;
					}
				} else {
					if (EcorePackage.eINSTANCE.getEEnum().isInstance(domainModelEFeature.getEType())) {
						if (Enumerator.class.cast(currentValue).getLiteral().equals(item.getValue())) {
							stack.setTopElement(item);
							topElementSet = true;
							break;
						}
					}
					if (currentValue.equals(item.getValue())) {
						stack.setTopElement(item);
						topElementSet = true;
						break;
					}
				}
			}
			if (!topElementSet) {
				stack.setTopElement(null);
			}
		}
	}

	private ModelChangeListener createDomainListener() {
		return new StackDomainChangeListener();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		viewModel = null;
		domain = null;

		registry.clear();
		registry = null;

		context.unregisterDomainChangeListener(stackItemsDomainListener);
		stackItemsDomainListener = null;

		context.unregisterDomainChangeListener(domainListener);
		domainListener = null;
		context = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 7;
	}

	/**
	 * {@link ModelChangeListener} that updates the registry and reevaluates affected {@link VStackLayout VStackLayouts}
	 * when the domain model is changed.
	 *
	 * @author Lucas Koehler
	 *
	 */
	private class StackItemsModelChangeListener implements ModelChangeListener {
		private final Map<VStackLayout, Setting> stackToSetting;

		/**
		 * Constructs a new {@link StackItemsModelChangeListener}.
		 *
		 * @param stackToSetting The map of stacks to their current registered settings
		 */
		StackItemsModelChangeListener(Map<VStackLayout, Setting> stackToSetting) {
			this.stackToSetting = stackToSetting;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
		 */
		@Override
		public void notifyChange(ModelChangeNotification notification) {
			final EMFFormsStructuralChangeTester tester = context.getService(EMFFormsStructuralChangeTester.class);
			for (final VStackLayout stack : stackToSetting.keySet()) {
				if (tester.isStructureChanged(stack.getDomainModelReference(), context.getDomainModel(),
					notification)) {
					removeOutdatedEntriesFromRegistry(stack);
					addCurrentEntriesToRegistry(stack);
					final Setting setting = stackToSetting.get(stack);
					evaluate(setting.getEObject(), setting.getEStructuralFeature());
				}
			}

		}

		private void removeOutdatedEntriesFromRegistry(VStackLayout stack) {
			final Setting setting = stackToSetting.get(stack);
			final Map<EStructuralFeature, Set<VStackLayout>> featureToStackMap = registry.get(setting.getEObject());
			final Set<VStackLayout> stacks = featureToStackMap.get(setting.getEStructuralFeature());
			stacks.remove(stack);
			if (!stacks.isEmpty()) {
				return;
			}
			featureToStackMap.remove(setting.getEStructuralFeature());
			if (!featureToStackMap.isEmpty()) {
				return;
			}
			registry.remove(setting.getEObject());
		}

		private void addCurrentEntriesToRegistry(VStackLayout stack) {
			// TODO setting may be null. see TODOs above
			final Setting setting = addToRegistry(stack, stack.getDomainModelReference());
			stackToSetting.put(stack, setting);
		}
	}

	/**
	 * {@link ModelChangeListener} reacting on changes in the domain.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class StackDomainChangeListener implements ModelChangeListener {
		@Override
		public void notifyChange(ModelChangeNotification notification) {
			final EObject notifier = notification.getNotifier();
			final EStructuralFeature feature = notification.getStructuralFeature();
			if (!doesRegistryContain(notifier, feature)) {
				return;
			}
			evaluate(notifier, feature);
		}
	}

}