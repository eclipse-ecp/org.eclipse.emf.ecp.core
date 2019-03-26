/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bug 533522
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.cachetree.AbstractCachedTree;
import org.eclipse.emf.ecp.common.spi.cachetree.CachedTreeNode;
import org.eclipse.emf.ecp.common.spi.cachetree.IExcludedObjectsCallback;

/**
 * Cache for diagnostic results.
 *
 * @author Johannes Faltermeier
 * @since 1.10
 *
 */
public class DiagnosticCache extends AbstractCachedTree<Diagnostic> {

	private final Set<ValidationListener> validationListeners = new CopyOnWriteArraySet<ValidationListener>();

	private ValidationChangeListener validationChangeListener;

	private Notifier input;

	private boolean initializing;

	/**
	 * Default constructor.
	 *
	 * @param input the input
	 */
	public DiagnosticCache(Notifier input) {
		super(new IExcludedObjectsCallback() {

			@Override
			public boolean isExcluded(Object object) {
				return false;
			}
		});

		init(input);

		final Set<EObject> rootObjects = new LinkedHashSet<>();
		if (ResourceSet.class.isInstance(input)) {
			final EList<Resource> resources = ResourceSet.class.cast(input).getResources();
			resources.stream().map(Resource::getContents).forEach(rootObjects::addAll);
		} else if (Resource.class.isInstance(input)) {
			rootObjects.addAll(Resource.class.cast(input).getContents());
		} else if (EObject.class.isInstance(input)) {
			rootObjects.add(EObject.class.cast(input));
		} else {
			return;
		}

		// The implementation builds the cache tree on initialization, but does not propagate validation results
		// of children to their parents. This means, the own diagnostic for every EObject in the containment history is
		// known but nodes do not know about the diagnostics of their children, yet. As a consequence, validation errors
		// can only be shown where they occur.
		// In order to show the errors at the parents, we walk down the containment hierarchy in a depth-first search
		// approach and propagate the validation results upwards. On the way up, each node propagates its aggregated
		// validation result which is the most severe diagnostic of itself and its children. With this we only need to
		// propagate the results one time from bottom to the top.
		rootObjects.forEach(this::depthFirstCacheUpdate);
	}

	private void init(Notifier input) {
		final boolean wasInitializing = initializing;
		initializing = true;

		try {
			this.input = input;
			validationChangeListener = new ValidationChangeListener(input);
			TreeIterator<Object> allContents;
			if (ResourceSet.class.isInstance(input)) {
				allContents = EcoreUtil.getAllContents(ResourceSet.class.cast(input), false);
			} else if (Resource.class.isInstance(input)) {
				allContents = EcoreUtil.getAllContents(Resource.class.cast(input), false);
			} else if (EObject.class.isInstance(input)) {
				allContents = EcoreUtil.getAllContents(Collections.singleton(EObject.class.cast(input)), false);
			} else {
				return;
			}

			while (allContents.hasNext()) {
				final Object next = allContents.next();
				if (!EObject.class.isInstance(next)) {
					continue;
				}
				updateCacheWithoutRefresh(EObject.class.cast(next), this);
			}
		} finally {
			initializing = wasInitializing;
		}
	}

	/**
	 * Recursively walks down the containment hierarchy of the given root object and puts the children's diagnostics
	 * into the cache of root's cached tree node. Afterwards, the most severe diagnostic of root's subtree (including
	 * root) is returned which allows caching it in root's parent.
	 *
	 * @param root The root object of the current depth first search
	 * @return The most severe Diagnostic in the subtree of the root object
	 */
	private Diagnostic depthFirstCacheUpdate(EObject root) {
		final CachedTreeNode<Diagnostic> treeNode = getNodes().get(root);
		if (treeNode == null) {
			return getDefaultValue();
		}
		root.eContents().stream()
			.forEach(c -> treeNode.putIntoCache(c, depthFirstCacheUpdate(c)));
		return treeNode.getDisplayValue();
	}

	/**
	 * Queries whether the cache is in the process of initializing itself. This is useful
	 * to avoid doing redundant work, such as back-tracking up an EMF containment tree
	 * to update parents that have already been covered during the initialization.
	 *
	 * @return whether the cache is in the process of initializations
	 *
	 * @since 1.17
	 */
	protected final boolean isInitializing() {
		return initializing;
	}

	@Override
	public Diagnostic getDefaultValue() {
		return Diagnostic.OK_INSTANCE;
	}

	@Override
	protected CachedTreeNode<Diagnostic> createdCachedTreeNode(Diagnostic value) {
		return new DiagnosticTreeNode(value);
	}

	/**
	 * @since 1.17
	 */
	@Override
	protected void updateParentNode(Object parent, Object object, Diagnostic value) {
		// In the initial walk over the contents, we will already have processed
		// the containment chain
		if (!isInitializing()) {
			super.updateParentNode(parent, object, value);
		}
	}

	/**
	 * @return the objects with cached values.
	 */
	public Set<Object> getObjects() {
		return Collections.unmodifiableSet(new LinkedHashSet<Object>(getNodes().keySet()));
	}

	/**
	 * @param o the object
	 * @return the objects diagnostic
	 */
	public Diagnostic getOwnValue(Object o) {
		if (o == null) {
			return getDefaultValue();
		}
		final CachedTreeNode<Diagnostic> treeNode = getNodes().get(o);
		if (treeNode == null) {
			/* there is no entry in the cache */
			return getDefaultValue();
		}
		return treeNode.getOwnValue();
	}

	/**
	 * Disposes this cache.
	 */
	public void dispose() {
		validationChangeListener.dispose();
	}

	/**
	 * Does a reinit of this cache <b>if</b> the given notifier is different than the current one.
	 *
	 * @param notifier the notifier
	 */
	public void reinit(Notifier notifier) {
		if (input == notifier) {
			return;
		}
		dispose();
		clear();
		init(notifier);
	}

	/**
	 * @param listener the {@link ValidationListener} to register
	 */
	public void registerValidationListener(ValidationListener listener) {
		validationListeners.add(listener);
	}

	/**
	 *
	 * @param listener the {@link ValidationListener} to deregister
	 */
	public void deregisterValidationListener(ValidationListener listener) {
		validationListeners.remove(listener);
	}

	/**
	 * UPdates the cache and notifies listeners that this was a potential structure change.
	 *
	 * @param element the changed element
	 * @param cache the cache
	 */
	protected void updateCache(EObject element, DiagnosticCache cache) {
		final Diagnostic diagnostic = getDiagnostic(element);
		final Set<EObject> update = cache.update(element, diagnostic);
		notifyValidationListeners(update, true);

	}

	private void handleRemove(EObject oldValue, DiagnosticCache cache) {
		final Set<EObject> toRemove = new LinkedHashSet<EObject>();
		toRemove.add(oldValue);
		final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(oldValue, false);
		while (iterator.hasNext()) {
			toRemove.add(iterator.next());
		}
		for (final EObject object : toRemove) {
			cache.remove(object);
		}
	}

	private static Diagnostic getDiagnostic(Object object) {
		if (!EObject.class.isInstance(object)) {
			return Diagnostic.OK_INSTANCE;
		}
		final EObject eObject = EObject.class.cast(object);
		EValidator validator = EValidator.Registry.INSTANCE.getEValidator(eObject.eClass().getEPackage());
		final BasicDiagnostic diagnostics = Diagnostician.INSTANCE.createDefaultDiagnostic(eObject);

		if (validator == null) {
			validator = new EObjectValidator();
		}
		final Map<Object, Object> context = new HashMap<Object, Object>();
		context.put(EValidator.SubstitutionLabelProvider.class, Diagnostician.INSTANCE);
		context.put(EValidator.class, validator);

		validator.validate(eObject, diagnostics, context);
		return diagnostics;
	}

	private void notifyValidationListeners(final Set<EObject> update, boolean struc) {
		for (final ValidationListener validationListener : validationListeners) {
			validationListener.revalidationOccurred(update, struc);
		}
	}

	/**
	 * Updates the cache and notifes listeners that this change was not a structure change.
	 *
	 * @param element the element
	 * @param cache the cache
	 */
	protected void updateCacheWithoutRefresh(EObject element, DiagnosticCache cache) {
		final Diagnostic diagnostic = getDiagnostic(element);
		final Set<EObject> update = cache.update(element, diagnostic);
		notifyValidationListeners(update, false);
		notifyValidationListeners(Collections.singleton(element), false);
	}

	/**
	 * Tree node for diagnostics.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private class DiagnosticTreeNode extends CachedTreeNode<Diagnostic> {

		private final Set<Diagnostic> diagnosticSet = new TreeSet<Diagnostic>(new Comparator<Diagnostic>() {

			@Override
			public int compare(Diagnostic o1, Diagnostic o2) {
				if (o1.getSeverity() == o2.getSeverity()) {
					if (o1 == o2) {
						return 0;
					}
					return 1;
				}
				return -1 * Integer.class.cast(o1.getSeverity()).compareTo(o2.getSeverity());
			}

		});

		DiagnosticTreeNode(Diagnostic initialValue) {
			super(initialValue);
		}

		@Override
		public void putIntoCache(Object key, Diagnostic value) {
			boolean updateRequired = true;

			if (getCache().containsKey(key)) {
				final Diagnostic diagnostic = getCache().get(key);
				if (diagnostic.getSeverity() == value.getSeverity()) {
					updateRequired = false;
				}
				diagnosticSet.remove(diagnostic);
			}
			getCache().put(key, value);
			diagnosticSet.add(value);

			if (updateRequired) {
				update();
			}
		}

		@Override
		public void update() {
			final Iterator<Diagnostic> iterator = diagnosticSet.iterator();
			if (iterator.hasNext()) {
				final Diagnostic mostSevereDiagnostic = iterator.next();
				setChildValue(mostSevereDiagnostic);
			} else {
				setChildValue(getDefaultValue());
			}
		}

		@Override
		public Diagnostic getDisplayValue() {
			if (getChildValue() == null) {
				return getOwnValue();
			}
			return getOwnValue().getSeverity() > getChildValue().getSeverity() ? getOwnValue() : getChildValue();
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.common.spi.cachetree.CachedTreeNode#removeFromCache(java.lang.Object)
		 */
		@Override
		public void removeFromCache(Object key) {
			final Diagnostic diagnostic = getCache().remove(key);
			if (diagnostic != null) {
				diagnosticSet.remove(diagnostic);
			}
			update();
		}
	}

	/**
	 *
	 * An adapter which will update the cache.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class ValidationChangeListener extends EContentAdapter {

		private final Notifier parent;

		ValidationChangeListener(Notifier parent) {
			this.parent = parent;
			parent.eAdapters().add(this);
		}

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (notification.isTouch() && notification.getEventType() < Notification.EVENT_TYPE_COUNT) {
				/* if touch and non-user-defined event type break */
				return;
			}
			handleStructuralChangeNotification(notification);
			if (!EObject.class.isInstance(notification.getNotifier())) {
				return;
			}
			updateCacheWithoutRefresh(EObject.class.cast(notification.getNotifier()), DiagnosticCache.this);
		}

		void dispose() {
			parent.eAdapters().remove(this);
		}

		private void handleStructuralChangeNotification(Notification notification) {
			switch (notification.getEventType()) {
			case Notification.REMOVE: {
				handleSingleRemove(notification);
				break;
			}
			case Notification.REMOVE_MANY: {
				@SuppressWarnings("unchecked")
				final List<Object> deleted = (List<Object>) notification.getOldValue();
				if (deleted.isEmpty() || !EObject.class.isInstance(deleted.get(0))) {
					break;
				}
				for (final Object oldValue : deleted) {
					handleRemove(EObject.class.cast(oldValue), DiagnosticCache.this);
				}
				break;
			}
			case Notification.ADD: {
				handleAdd(notification);
				break;
			}
			case Notification.ADD_MANY: {
				@SuppressWarnings("unchecked")
				final List<Object> added = (List<Object>) notification.getNewValue();
				if (added.isEmpty() || !EObject.class.isInstance(added.get(0))) {
					break;
				}
				for (final Object newValue : added) {
					final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(EObject.class.cast(newValue),
						false);
					while (iterator.hasNext()) {
						updateCacheWithoutRefresh(iterator.next(), DiagnosticCache.this);
					}
					updateCache(EObject.class.cast(newValue), DiagnosticCache.this);
				}
				break;

			}
			case Notification.SET: {
				if (!EReference.class.isInstance(notification.getFeature())
					|| !EReference.class.cast(notification.getFeature()).isContainment()) {
					break;
				}
				handleAdd(notification);

				break;
			}
			default:
				break;
			}
		}

		private void handleSingleRemove(Notification notification) {
			final Object oldValue = notification.getOldValue();
			if (!EObject.class.isInstance(oldValue)) {
				return;
			}
			handleRemove(EObject.class.cast(oldValue), DiagnosticCache.this);
		}

		private void handleAdd(Notification notification) {
			final Object newValue = notification.getNewValue();
			if (!EObject.class.isInstance(newValue)) {
				return;
			}
			final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(EObject.class.cast(newValue), false);
			while (iterator.hasNext()) {
				updateCacheWithoutRefresh(iterator.next(), DiagnosticCache.this);
			}
			updateCache(EObject.class.cast(newValue), DiagnosticCache.this);
		}
	}

	/**
	 * Notified when the validation changes.
	 *
	 */
	public interface ValidationListener {
		/**
		 * Called when a revalidation for the object has happened.
		 *
		 * @param object the object
		 * @param potentialStructuralChange whether this was caused by a structural change.
		 */
		void revalidationOccurred(Collection<EObject> object, boolean potentialStructuralChange);
	}

}
