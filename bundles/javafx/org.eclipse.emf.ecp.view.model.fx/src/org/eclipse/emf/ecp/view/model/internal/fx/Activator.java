package org.eclipse.emf.ecp.view.model.internal.fx;

import java.io.IOException;
import java.net.URL;

import javafx.scene.image.Image;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

	private static BundleContext context;
	private static Activator plugin;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * @param path the relative path to the resource in this bundle.
	 * @return the URL which represents the absolute path to the resource.
	 */

	public static URL getResourceUrl(String path) {
		try {
			return FileLocator.resolve(getDefault().getBundle().getResource(path));
		} catch (final IOException ex) {
			ex.printStackTrace();
			// TODO log exception properly
			return null;
		}
	}

	/**
	 *
	 * @param path the relative path to the image in this bundle.
	 * @return the image
	 */
	public static Image getImage(String path) {
		return new Image(getResourceUrl(path).toString());
	}
}
