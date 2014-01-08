package org.eclipse.emf.ecp.ide.editor.view.control;

import org.eclipse.emf.ecp.ide.view.service.IDEViewModelRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public static IDEViewModelRegistry getViewModelRegistry(){
		ServiceReference<IDEViewModelRegistry> serviceReference = context.getServiceReference(IDEViewModelRegistry.class);
		if(serviceReference==null)
			return null;
		return context.getService(serviceReference);
	}
	
}
