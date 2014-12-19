/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.model.util;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.impl.Activator;
import org.eclipse.emf.ecp.view.spi.model.reporting.DomainModelReferenceResolutionFailedReport;

/**
 * This Util class provides common methods used often when working with the view model.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public final class ViewModelUtil {

	private static final String ORG_ECLIPSE_EMF_ECP_VIEW_SPI_LABEL_MODEL_V_LABEL = "org.eclipse.emf.ecp.view.spi.label.model.VLabel"; //$NON-NLS-1$
	private static Boolean debugMode;
	private static boolean isDebugInitialized;

	private ViewModelUtil() {

	}

	/**
	 * Resolves all {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReferences} which are
	 * contained under the provided
	 * renderable using the provided domain model.
	 *
	 * @param renderable the renderable to analyze for {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 *            VDomainModelReferences}
	 * @param domainModelRoot the domain model to use for resolving
	 * @since 1.3
	 */
	public static void resolveDomainReferences(EObject renderable,
		EObject domainModelRoot) {
		checkAndResolve(renderable, domainModelRoot);
		final TreeIterator<EObject> eAllContents = renderable.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			checkAndResolve(eObject, domainModelRoot);
		}
	}

	private static void checkAndResolve(EObject renderable, EObject domainModelRoot) {
		if (VControl.class.isInstance(renderable)) {
			final VControl control = (VControl) renderable;
			if (control.getDomainModelReference() == null) {
				return;
			}
			final boolean init = control.getDomainModelReference().init(domainModelRoot);
			if (!init && ViewModelUtil.isDebugMode()) {
				// report only in debug mode
				Activator.getDefault()
					.getReportService()
					.report(
						new DomainModelReferenceResolutionFailedReport(
							control.getDomainModelReference(),
							control));
			}
		}
		// XXX get rid of the following code as soon as possible!
		else if (VContainedElement.class.isInstance(renderable)) {
			final VContainedElement element = VContainedElement.class.cast(renderable);
			if (!ORG_ECLIPSE_EMF_ECP_VIEW_SPI_LABEL_MODEL_V_LABEL.equals(element.eClass().getInstanceClassName())) {
				return;
			}
			for (final EObject object : element.eContents()) {
				if (VDomainModelReference.class.isInstance(object)) {
					final boolean init = VDomainModelReference.class.cast(object).init(domainModelRoot);
					if (!init && ViewModelUtil.isDebugMode()) {
						// report only in debug mode
						Activator.getDefault()
							.getReportService()
							.report(
								new DomainModelReferenceResolutionFailedReport(
									VDomainModelReference.class.cast(object),
									element));
					}
				}
			}
		}
	}

	/**
	 * Whether EMF Forms has been started with the debug flag <code>-debugEMFForms</code>.
	 *
	 * @return <code>true</code> if EMF Forms has been started with the debug flag, <code>false</code> otherwise
	 *
	 * @since 1.5
	 */
	public static boolean isDebugMode() {

		if (!isDebugInitialized) {
			debugMode = Boolean.FALSE;
			final String[] commandLineArgs = Platform.getCommandLineArgs();
			for (int i = 0; i < commandLineArgs.length; i++) {
				final String arg = commandLineArgs[i];
				if ("-debugEMFForms".equalsIgnoreCase(arg)) { //$NON-NLS-1$
					debugMode = Boolean.TRUE;
				}
			}
			isDebugInitialized = true;
		}

		return debugMode;
	}
}
