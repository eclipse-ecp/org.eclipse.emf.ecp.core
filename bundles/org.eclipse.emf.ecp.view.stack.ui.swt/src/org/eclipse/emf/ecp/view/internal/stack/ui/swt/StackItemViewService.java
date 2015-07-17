/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

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
	private ViewModelContext context;
	private VElement viewModel;
	private EObject domain;

	private Map<EObject, Map<EStructuralFeature, Set<VStackLayout>>> registry;
	private Set<StackItemDomainModelReferenceChangeListener> changeListener;

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
		changeListener = new LinkedHashSet<StackItemDomainModelReferenceChangeListener>();

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

		for (final VStackLayout stack : stacks) {
			final VDomainModelReference dmr = stack.getDomainModelReference();
			if (dmr == null) {
				continue;
			}
			dmr.init(domain);
			final Setting setting = addToRegistry(stack, dmr);
			if (setting == null) {
				// TODO JF how to handle?
				return;
			}
			dmr.getChangeListener().add(createDMRChangeListener(stack, setting));
			context.registerDomainChangeListener(dmr);
		}
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

	private DomainModelReferenceChangeListener createDMRChangeListener(VStackLayout stack, Setting oldSetting) {
		final StackItemDomainModelReferenceChangeListener listener = new StackItemDomainModelReferenceChangeListener(
			stack, oldSetting);
		changeListener.add(listener);
		return listener;
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

		for (final StackItemDomainModelReferenceChangeListener listener : changeListener) {
			listener.dispose();
		}
		changeListener.clear();
		changeListener = null;

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
	 * {@link DomainModelReferenceChangeListener} that updates the registry and reevaluates affected
	 * {@link VStackLayout VStackLayouts}.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class StackItemDomainModelReferenceChangeListener implements DomainModelReferenceChangeListener {

		private Setting setting;
		private final VStackLayout stack;

		/**
		 * Constructs a new {@link StackItemDomainModelReferenceChangeListener}.
		 *
		 * @param stack the affected {@link VStackLayout}.
		 * @param oldSetting the current registered {@link Setting}.
		 */
		public StackItemDomainModelReferenceChangeListener(VStackLayout stack, Setting oldSetting) {
			this.stack = stack;
			setting = oldSetting;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener#notifyChange()
		 */
		@Override
		public void notifyChange() {
			removeOutdatedEntriesFromRegistry();
			addCurrentEntriesToRegistry();
			evaluate(setting.getEObject(), setting.getEStructuralFeature());
		}

		private void removeOutdatedEntriesFromRegistry() {
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

		private void addCurrentEntriesToRegistry() {
			// TODO setting may be null. see TODOs above
			setting = addToRegistry(stack, stack.getDomainModelReference());
		}

		public void dispose() {
			stack.getDomainModelReference().getChangeListener().remove(this);
			context.unregisterDomainChangeListener(stack.getDomainModelReference());
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