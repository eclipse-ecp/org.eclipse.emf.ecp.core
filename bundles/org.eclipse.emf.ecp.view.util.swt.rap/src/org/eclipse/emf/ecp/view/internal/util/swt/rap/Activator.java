package org.eclipse.emf.ecp.view.internal.util.swt.rap;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

public class Activator extends Plugin {

	@Override
	public void start(BundleContext context) throws Exception {
		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 1000);
		context.registerService(ImageRegistryService.class, new RAPImageRegistryService(), dictionary);

		super.start(context);
	}
}
