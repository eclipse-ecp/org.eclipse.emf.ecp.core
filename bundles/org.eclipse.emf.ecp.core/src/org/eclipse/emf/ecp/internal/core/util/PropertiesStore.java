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
package org.eclipse.emf.ecp.internal.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecp.core.util.ECPElement;
import org.eclipse.emf.ecp.core.util.observer.ECPObserver;
import org.eclipse.emf.ecp.internal.core.Activator;
import org.eclipse.emf.ecp.internal.core.util.PropertiesStore.StorableElement;
import org.eclipse.net4j.util.io.IOUtil;

/**
 * @author Eike Stepper
 * @param <ELEMENT>
 * @param <OBSERVER>
 */
public abstract class PropertiesStore<ELEMENT extends StorableElement, OBSERVER extends ECPObserver> extends
	ElementRegistry<ELEMENT, OBSERVER> {
	private File folder;

	/**
	 * Default constructor.
	 */
	public PropertiesStore() {
	}

	/**
	 * 
	 * @return The folder, where this property store stores its properties as a {@link File}
	 */
	public final File getFolder() {
		return folder;
	}

	public final void setFolder(File folder) {
		checkInactive();
		this.folder = folder;
	}

	@Override
	protected void doActivate() throws Exception {
		super.doActivate();
		if (folder == null) {
			throw new IllegalStateException("Folder is null");
		}

		if (folder.exists()) {
			if (!folder.isDirectory()) {
				throw new IllegalStateException("Not a folder: " + folder);
			}
		} else {
			folder.mkdirs();
		}

		load();
	}

	protected void load() {
		// TODO Trace properly
		System.out.println("Loading " + getClass().getSimpleName() + " from " + folder.getAbsolutePath());

		final Set<ELEMENT> elements = new HashSet<ELEMENT>();
		for (final File file : folder.listFiles()) {
			try {
				if (isLoadableElement(file)) {
					InputStream stream = null;

					try {
						stream = new FileInputStream(file);
						final ObjectInputStream in = new ObjectInputStream(stream);
						final ELEMENT element = loadElement(in);
						// ELEMENT existingElement = getElement(element.getName());
						// if (existingElement != null)
						// {
						// if (element instanceof ECPDisposable)
						// {
						// ECPDisposable disposable = (ECPDisposable)element;
						// disposable.dispose();
						// }
						// }
						// else
						{
							elements.add(element);
						}
					} finally {
						IOUtil.close(stream);
					}
				}
			} catch (final IOException ex) {
				Activator.log(ex);
			}
		}

		doChangeElements(null, elements);
	}

	protected boolean isLoadableElement(File file) {
		return file.isFile();
	}

	/**
	 * Loads an element.
	 * 
	 * @param in an {@link ObjectInput} to load the element from
	 * @return the element
	 * @throws IOException if the element cannot be loaded correctly
	 */
	protected abstract ELEMENT loadElement(ObjectInput in) throws IOException;

	@Override
	protected void elementsChanged(Collection<ELEMENT> oldElements, Collection<ELEMENT> newElements) {
		if (isDisposingElement()) {
			return;
		}

		if (isActive()) {
			for (final ELEMENT element : InternalUtil.getRemovedElements(oldElements, newElements)) {
				try {
					final File file = getFile(element);
					file.delete();
				} catch (final Exception ex) {
					Activator.log(ex);
				}
			}

			for (final ELEMENT element : InternalUtil.getAddedElements(oldElements, newElements)) {
				if (element.isStorable()) {
					storeElement(element);
				}
			}
		}
	}

	public void storeElement(ELEMENT element) {
		final File file = getFile(element);
		final File temp = new File(file.getParentFile(), file.getName() + ".tmp");
		if (temp.isFile()) {
			temp.delete();
		}

		OutputStream stream = null;

		try {
			try {
				stream = new FileOutputStream(temp);
				final ObjectOutputStream out = new ObjectOutputStream(stream);
				element.write(out);
				out.flush();
			} finally {
				IOUtil.close(stream);
			}

			file.delete();
			temp.renameTo(file);
		} catch (final Exception ex) {
			temp.delete();
			Activator.log(ex);
		}
	}

	protected File getFile(ELEMENT element) {
		return new File(folder, element.getName());
	}

	/**
	 * @author Eike Stepper
	 */
	public interface StorableElement extends ECPElement {
		boolean isStorable();

		void write(ObjectOutput out) throws IOException;
	}
}
