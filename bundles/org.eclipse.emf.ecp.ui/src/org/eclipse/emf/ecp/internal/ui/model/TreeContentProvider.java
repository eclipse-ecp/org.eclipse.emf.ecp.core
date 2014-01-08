/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.core.util.ChildrenListImpl;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * @author Eike Stepper
 * @param <INPUT> The type of input (root of the tree)
 */
public abstract class TreeContentProvider<INPUT> extends StructuredContentProvider<INPUT> implements
	ITreeContentProvider {
	private static final Object[] NO_CHILDREN = new Object[0];

	private final Map<Object, Object> parentsCache = new WeakHashMap<Object, Object>();

	private final Map<Object, InternalChildrenList> slowLists = new HashMap<Object, InternalChildrenList>();

	@Override
	public TreeViewer getViewer() {
		return (TreeViewer) super.getViewer();
	}

	/** {@inheritDoc} */
	public final Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	/** {@inheritDoc} */
	public final boolean hasChildren(Object parent) {
		if (parent instanceof SyntheticElement || ECPUtil.isDisposed(parent) || ECPUtil.isClosed(parent)) {
			return false;
		}

		final InternalChildrenList childrenList = getChildrenList(parent);
		synchronized (childrenList) {
			if (!childrenList.isComplete()) {
				return true;
			}

			return childrenList.hasChildren();
		}
	}

	/** {@inheritDoc} */
	public final Object[] getChildren(Object parent) {
		if (parent instanceof SyntheticElement || ECPUtil.isDisposed(parent) || ECPUtil.isClosed(parent)) {
			return NO_CHILDREN;
		}

		Object[] result;
		boolean complete;

		final InternalChildrenList childrenList = getChildrenList(parent);
		synchronized (childrenList) {
			result = childrenList.getChildren();
			complete = childrenList.isComplete();
		}

		for (int i = 0; i < result.length; i++) {
			final Object child = result[i];
			parentsCache.put(child, parent);
		}

		if (!complete) {
			final Object[] withPending = new Object[result.length + 1];
			System.arraycopy(result, 0, withPending, 0, result.length);
			withPending[result.length] = new SlowElement(parent);
			result = withPending;
		}

		return result;
	}

	/** {@inheritDoc} */
	public final Object getParent(Object child) {
		if (child instanceof SyntheticElement) {
			return ((SyntheticElement) child).getParent();
		}

		Object result = parentsCache.get(child);
		if (result == null && EObject.class.isInstance(child)) {
			final EObject childEObject = (EObject) child;
			result = childEObject.eContainer();
			if (result != null && parentsCache.containsKey(result)) {
				return result;

			}
		}
		return result;
	}

	public final void refreshViewer(final boolean isStructuralChange, final Object... objects) {
		if (objects.length == 0) {
			return;
		}

		final TreeViewer viewer = getViewer();
		final Control control = viewer.getControl();
		if (!control.isDisposed()) {
			final Display display = control.getDisplay();
			final ECPProject ecpProject = ECPUtil.getECPProjectManager()
				.getProject(objects[0]);
			boolean isThreadSafe = true;
			if (ecpProject != null) {
				final InternalProvider provider = (InternalProvider) ecpProject.getProvider();
				isThreadSafe = provider.isThreadSafe();
			}
			if (display.getSyncThread() != Thread.currentThread()) {
				final Runnable refreshRunnable = createRefreshRunnable(isStructuralChange, viewer, objects);
				if (Boolean.getBoolean("enableDisplaySync") && !isThreadSafe) {
					display.syncExec(refreshRunnable);
				} else {
					display.asyncExec(refreshRunnable);
				}
			} else {
				if (isStructuralChange) {
					refresh(viewer, objects);
				} else {
					update(viewer, objects);
				}
			}
		}
	}

	private Runnable createRefreshRunnable(final boolean isStructuralChange, final TreeViewer viewer,
		final Object... objects) {
		return new Runnable() {
			public void run() {
				if (isStructuralChange) {
					refresh(viewer, objects);
				} else {
					update(viewer, objects);
				}
			}
		};
	}

	protected boolean isSlow(Object parent) {
		return false;
	}

	protected InternalChildrenList getChildrenList(Object parent) {
		InternalChildrenList childrenList;
		if (isSlow(parent)) {
			SlowChildrenList newList = null;
			synchronized (slowLists) {
				childrenList = slowLists.get(parent);
				if (childrenList == null) {
					newList = new SlowChildrenList(parent);
					childrenList = newList;
					slowLists.put(parent, childrenList);
				}
			}

			if (newList != null) {
				newList.startThread();
			}
		} else {
			childrenList = new ChildrenListImpl(parent);
			fillChildrenDetectError(parent, childrenList);
		}

		return childrenList;
	}

	protected void fillChildrenDetectError(Object parent, InternalChildrenList childrenList) {
		try {
			fillChildren(parent, childrenList);
		} catch (final Throwable t) {
			Activator.log(t);
			final ErrorElement errorElement = new ErrorElement(parent, t);
			childrenList.addChildWithoutRefresh(errorElement);
		}
	}

	protected abstract void fillChildren(Object parent, InternalChildrenList childrenList);

	public static void refresh(TreeViewer viewer, Object... objects) {
		if (!viewer.getControl().isDisposed()) {
			for (final Object object : objects) {
				viewer.refresh(object);
			}
		}
	}

	public static void update(TreeViewer viewer, Object... objects) {
		if (!viewer.getControl().isDisposed()) {
			for (final Object object : objects) {
				if (object != null) {
					viewer.update(object, null);
				}
			}
		}
	}

	/**
	 * @author Eike Stepper
	 */
	public static class SyntheticElement {
		private final Object parent;

		public SyntheticElement(Object parent) {
			this.parent = parent;
		}

		public final Object getParent() {
			return parent;
		}
	}

	/**
	 * @author Eike Stepper
	 */
	public static final class ErrorElement extends SyntheticElement {
		private final Throwable cause;

		public ErrorElement(Object parent, Throwable cause) {
			super(parent);
			this.cause = cause;
		}

		public Throwable getCause() {
			return cause;
		}

		@Override
		public String toString() {
			return Messages.TreeContentProvider_ErrorElement_Error;
		}
	}

	/**
	 * @author Eike Stepper
	 */
	public static final class SlowElement extends SyntheticElement {
		public SlowElement(Object parent) {
			super(parent);
		}

		@Override
		public String toString() {
			return Messages.TreeContentProvider_SlowElement_Pending;
		}
	}

	/**
	 * @author Eike Stepper
	 */
	private final class SlowChildrenList extends ChildrenListImpl implements Runnable {
		private static final long serialVersionUID = 1L;

		private boolean complete;

		public SlowChildrenList(Object parent) {
			super(parent);
		}

		public void startThread() {
			final Thread thread = new Thread(this, "SlowChildrenList"); //$NON-NLS-1$
			thread.setDaemon(true);
			thread.start();
		}

		public void run() {
			fillChildrenDetectError(getParent(), this);
			setComplete();
		}

		@Override
		public boolean isSlow() {
			return true;
		}

		@Override
		public boolean isComplete() {
			return complete;
		}

		@Override
		public void setComplete() {
			if (!complete) {
				try {
					complete = true;
					childrenAdded();
				} finally {
					synchronized (slowLists) {
						slowLists.remove(getParent());
					}
				}
			}
		}

		@Override
		protected void childrenAdded() {
			final TreeViewer viewer = getViewer();
			final Control control = viewer.getControl();
			if (!control.isDisposed()) {
				final Display display = control.getDisplay();

				// asyncExec() would lead to infinite recursion in setComplete()
				display.syncExec(new Runnable() {
					public void run() {
						if (!control.isDisposed()) {
							refresh(viewer, getParent());
						}
					}
				});
			}
		}

		// private void dumpStack(String msg)
		// {
		// try
		// {
		// Object parent = getParent();
		// throw new RuntimeException(msg + " for " + parent + " (" + System.identityHashCode(parent) + ")");
		// }
		// catch (Exception ex)
		// {
		// ex.printStackTrace();
		// }
		// }
	}
}
