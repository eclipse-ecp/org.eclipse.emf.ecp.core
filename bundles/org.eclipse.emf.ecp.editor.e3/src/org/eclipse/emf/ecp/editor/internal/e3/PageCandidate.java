/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.editor.internal.e3;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Represents a page candidate to be showsn in meeditor.
 *
 * @author helming
 */
public final class PageCandidate {

	// By which pages this page is about to be replaced
	private final List<PageCandidate> replacedBy = new LinkedList<PageCandidate>();

	// Which pages follow after this page
	private final List<PageCandidate> successors = new LinkedList<PageCandidate>();

	// The wrapped configuration element
	private final IConfigurationElement page;

	// If this page is already in the resulting list
	private boolean enqueued;

	// The mapping from name to page candidate to store all candidates
	private static Map<String, PageCandidate> candidates = new LinkedHashMap<String, PageCandidate>();

	/**
	 * This method gets an input array of MEEditor pages and performs the tasks "after" and "replace" on them. That
	 * means, pages replaced by others will be omitted and pages with the after attribute set to a valid page name will
	 * be sorted so they appear after that page. If a page that has elements "after" it is replaced, the elements will
	 * instead be after the replacing page.
	 *
	 * @param input A list of MEEditor page candidates (directly from reading the extensions for the pages extension
	 *            point)
	 * @return A properly ordered list of all pages to be added to the MEEditor.
	 */
	public static List<IConfigurationElement> getPages(IConfigurationElement[] input) {

		// Create Page Candidates and store them in the map
		for (final IConfigurationElement i : input) {
			new PageCandidate(i);
		}

		// Link them together (build page Tree)
		linkPages();

		// Determine order
		final List<PageCandidate> orderedPages = determineOrder();

		// The hashmap content is no longer needed
		candidates.clear();

		// Unwrap ordered Pages and return them.
		final List<IConfigurationElement> result = new LinkedList<IConfigurationElement>();
		for (final PageCandidate p : orderedPages) {
			result.add(p.page);
		}

		return result;
	}

	/**
	 * Constructor of a page candidate.
	 *
	 * @param page the wrapped page
	 */
	private PageCandidate(IConfigurationElement page) {
		super();
		this.page = page;
		final String name = page.getAttribute("name"); //$NON-NLS-1$

		// Entry the page into the candidates map. Log an exception if there are colliding names
		if (candidates.containsKey(name)) {
			Activator.logException(new Exception("Two pages to be added to the MEEditor have the same name (" + name //$NON-NLS-1$
				+ ")! One of them will not be visible.")); //$NON-NLS-1$
		}
		candidates.put(name, this);
	}

	/**
	 * This function links the pages by connecting them via the after and replace attributes. After this, the pages are
	 * linked in a treelike manner which can be used to order them.
	 */
	private static void linkPages() {
		// Build the page tree...
		for (final Entry<String, PageCandidate> curEntry : candidates.entrySet()) {
			final PageCandidate cur = curEntry.getValue();
			// Set replacements if this page has any
			final String r = cur.page.getAttribute("replace"); //$NON-NLS-1$
			if (r != null) {
				final String[] replacements = r.split(","); //$NON-NLS-1$

				// Loop over replacements and set them, if they exist
				for (final String s : replacements) {
					final PageCandidate replaced = candidates.get(s.trim());
					if (replaced != null) {
						// Replaced page does exist, link them
						replaced.replacedBy.add(cur);
					}
				}
			}

			// Build the after fields
			final String after = cur.page.getAttribute("after"); //$NON-NLS-1$
			if (after != null) {
				final PageCandidate before = candidates.get(after);
				if (before != null) {
					before.successors.add(cur);
				}
			}
		}

	}

	/**
	 * This function enqueues this page candidate into the List l, unless it is replaced by another page or is already
	 * in the list.
	 *
	 * @param l The list to enqueue this page
	 */
	private void enqueue(List<PageCandidate> l) {

		// Already enqueued? Skip!
		if (enqueued) {
			return;
		}

		// Was this page replaced?
		if (replacedBy.size() != 0) {

			// Enqueue all pages that replaced this page
			for (final PageCandidate c : replacedBy) {
				c.enqueue(l);
			}

		} else {
			// This page was not replaced, enqueue it.
			l.add(this);
			enqueued = true;
		}

		// Were some pages tagged "after" this page? Enqueue them!
		for (final PageCandidate c : successors) {
			c.enqueue(l);
		}
	}

	/**
	 * This method determines the ordered resulting page list. Replaced pages will be omitted and all other pages will
	 * be in the desired order
	 *
	 * @return A properly ordered list of all non-replaced page candidates.
	 */
	private static List<PageCandidate> determineOrder() {
		final List<PageCandidate> result = new LinkedList<PageCandidate>();

		// Loop over all pages and enqueue pages that are not after a specific page
		// and also do not replace a page. (These pages get enqueued recursively)
		for (final Entry<String, PageCandidate> e : candidates.entrySet()) {
			final PageCandidate p = e.getValue();
			if (p.successors.size() == 0 && p.replacedBy.size() == 0) {
				p.enqueue(result);
			}
		}

		return result;
	}
}
