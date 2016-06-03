/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.controlmapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * A {@link ModelChangeListener} that listens to the view model of a {@link EMFFormsViewContext} and updates a
 * {@link EMFFormsSettingToControlMapper} whenever the view model was changed in a way that is important to its
 * {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
public class ViewModelListener implements ModelChangeListener {

	private boolean isDisposed;
	// private final EMFFormsDomainExpander domainExpander;
	// private final ServiceReference<EMFFormsDomainExpander> domainExpanderServiceReference;
	private final EMFFormsStructuralChangeTester structuralChangeTester;
	private final ServiceReference<EMFFormsStructuralChangeTester> structuralChangeTesterServiceReference;
	private final EMFFormsViewContext viewModelContext;
	private final EMFFormsSettingToControlMapper settingToControlMapper;
	private final Set<VControl> vControls = new LinkedHashSet<VControl>();
	// private final ServiceReference<ReportService> reportServiceReference;
	// private final ReportService reportService;

	/**
	 * Creates a new instance of {@link ViewModelListener}.
	 *
	 * @param context The {@link EMFFormsViewContext} this {@link ViewModelListener} listens to
	 * @param mapper The {@link EMFFormsSettingToControlMapper} to keep updated
	 */
	public ViewModelListener(EMFFormsViewContext context, EMFFormsSettingToControlMapper mapper) {
		isDisposed = false;
		viewModelContext = context;
		settingToControlMapper = mapper;

		// Get needed services
		final BundleContext bundleContext = FrameworkUtil.getBundle(ViewModelListener.class).getBundleContext();
		// domainExpanderServiceReference = bundleContext.getServiceReference(EMFFormsDomainExpander.class);
		// domainExpander = bundleContext.getService(domainExpanderServiceReference);
		// reportServiceReference = bundleContext.getServiceReference(ReportService.class);
		// reportService = bundleContext.getService(reportServiceReference);
		structuralChangeTesterServiceReference = bundleContext
			.getServiceReference(EMFFormsStructuralChangeTester.class);
		structuralChangeTester = bundleContext.getService(structuralChangeTesterServiceReference);

		final TreeIterator<EObject> eAllContents = viewModelContext.getViewModel().eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			if (VControl.class.isInstance(eObject)) {
				final VControl vControl = VControl.class.cast(eObject);
				vControls.add(vControl);
			}
		}
		viewModelContext.registerDomainChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@Override
	public void notifyChange(ModelChangeNotification notification) {
		for (final VControl control : vControls) {
			// TODO: table detail views might have a different root.

			final VDomainModelReference reference = control.getDomainModelReference();
			if (reference == null) {
				/*
				 * don't call structural change testers, because they have Asserts that the reference is not null ->
				 * avoid IAE
				 * TODO what is the expected behaviour here?
				 */
				continue;
			}
			if (structuralChangeTester.isStructureChanged(reference, viewModelContext.getDomainModel(), notification)) {
				SettingToControlExpandHelper.resolveDomainReferences(control, viewModelContext.getDomainModel(),
					viewModelContext);
				settingToControlMapper.updateControlMapping(control);

				// TODO move it somewhere eg to the controlMapper?
				// clean diagnostic
				if (!notification.getStructuralFeature().isMany()) {
					control.setDiagnostic(null);
				} else if (control.getDiagnostic() != null) {
					final List<Diagnostic> diagnostics = control.getDiagnostic()
						.getDiagnostic(notification.getNotifier(), notification.getStructuralFeature());
					control.getDiagnostic().getDiagnostics().removeAll(diagnostics);
				}

				// re-resolve DMR
				// TODO discuss this
				// try {
				// domainExpander.prepareDomainObject(reference,
				// viewModelContext.getDomainModel());
				// } catch (final EMFFormsExpandingFailedException ex) {
				// reportService.report(new AbstractReport(ex,
				// "The DMR " + reference + " could not be re-resolved!")); //$NON-NLS-1$//$NON-NLS-2$
				// }
			}
		}
	}

	/**
	 * Adds a {@link VControl} to this {@link ViewModelListener} in order to check the control's
	 * {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference} for changes to the domain
	 * model.
	 *
	 * @param control The VControl to add
	 * @return true if this {@link ViewModelListener} didn't already know the control and it was added, false otherwise.
	 */
	public boolean addVControl(VControl control) {
		return vControls.add(control);
	}

	/**
	 * Remove a {@link VControl} from this {@link ViewModelListener}. It's
	 * {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference VDomainModelReference} will the no longer be
	 * checked for changes to the domain model.
	 *
	 * @param control The VControl to remove.
	 * @return true if the {@link ViewModelListener} knew this {@link VControl} and it was removed, false otherwise.
	 */
	public boolean removeVControl(VControl control) {
		return vControls.remove(control);
	}

	/**
	 * Dispose this {@link ViewModelListener}.
	 */
	public void dispose() {
		if (isDisposed) {
			return;
		}

		viewModelContext.unregisterDomainChangeListener(this);
		vControls.clear();

		final BundleContext bundleContext = FrameworkUtil.getBundle(ViewModelListener.class).getBundleContext();
		// bundleContext.ungetService(domainExpanderServiceReference);
		// bundleContext.ungetService(reportServiceReference);
		bundleContext.ungetService(structuralChangeTesterServiceReference);

		isDisposed = true;
	}

	/**
	 * Returns whether this {@link ViewModelListener} is disposed.
	 *
	 * @return true if this {@link ViewModelListener} is disposed, false otherwise
	 */
	public boolean isDisposed() {
		return isDisposed;
	}

}
