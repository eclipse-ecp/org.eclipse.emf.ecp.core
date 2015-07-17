package org.eclipse.emf.ecp.controls.internal.fx;

import java.io.IOException;
import java.net.URL;

import javafx.scene.image.Image;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public final class Activator extends Plugin {
	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.controls.fx"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	// END SUPRESS CATCH EXCEPTION

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Logs exception.
	 *
	 * @param e
	 *            the {@link Exception} to log
	 */
	public static void logException(Exception e) {
		getDefault().getLog().log(
			new Status(IStatus.ERROR, Activator.getDefault().getBundle()
				.getSymbolicName(), e.getMessage(), e));
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
